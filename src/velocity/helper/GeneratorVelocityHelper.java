package velocity.helper;

import velocity.entity.GriditemParam;
import velocity.entity.OtherMethod;
import velocity.utils.EntityUtils;
import velocity.utils.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.util.*;
import java.util.Map.Entry;

/**
 * 模版velocity生成器集合类.
 * <p>
 * 该模版velocity生成器集合类提供了以下几个方法.<br>
 * 依照模版文件生成对应代码文件并且输出.<br>
 * 初始化模版对应配置参数.<br>
 * 构造表格参数列表.<br>
 * 构造表格其他参数.
 * 
 * @see File
 * @see FileOutputStream
 * @see OutputStreamWriter
 * @see StringWriter
 * @see Connection
 * @see HashMap
 * @see Iterator
 * @see List
 * @see Map
 * @see Properties
 * @see Entry
 * @see org.apache.velocity.VelocityContext
 * @see org.apache.velocity.app.VelocityEngine
 * @see org.jdom.Document
 * @see org.jdom.Element
 * @see org.jdom.input.SAXBuilder
 * @see com.velocity.entity.GriditemParam
 * @see com.velocity.entity.OtherMethod
 * @see com.velocity.utils.EntityUtils
 * @see com.velocity.utils.StringUtils
 */
@SuppressWarnings("unchecked")
public class GeneratorVelocityHelper {

	/**
	 * 存放模版配置参数内容变量.
	 */
	@SuppressWarnings("rawtypes")
	public static Map xzcontext = new HashMap();

	/**
	 * 静态初始化存放模版配置参数的相关内容.
	 */
	static {
		if (xzcontext.isEmpty()) {
			initContext(Thread.currentThread().getContextClassLoader().getResource("").toString().replace("file:/", "") + "buildCodeConfig.xml");
		}
	}

	/**
	 * 私有构造方法，不允许外部类直接new创建对象.
	 */
	private GeneratorVelocityHelper() {
	}

	/**
	 * 依照模版文件生成对应代码文件并且输出.
	 *
	 * @param inPath
	 *            源文件路径地址
	 * @param outPath
	 *            结果文件输出地址
	 *
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static void outPutFile(String inPath, String outPath) throws Exception {
		Properties properties = new Properties();
		properties.setProperty("resource.loader", "class");// 设置velocity资源加载方式为class
		properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader"); // 设置velocity资源加载方式为file时的处理类
		VelocityEngine velocityEngine = new VelocityEngine(properties); // 实例化一个VelocityEngine对象
		VelocityContext context = new VelocityContext();// 实例化一个VelocityContext
		Iterator ite = xzcontext.entrySet().iterator();
		while (ite.hasNext()) {
			Entry entry = (Entry) ite.next();
			String key = StringUtils.trim(entry.getKey());
			String val = StringUtils.trim(entry.getValue());
			context.put(key, val);
		}
		String url = StringUtils.trim(xzcontext.get("jdbc_url"));
		String username = StringUtils.trim(xzcontext.get("jdbc_username"));
		String password = StringUtils.trim(xzcontext.get("jdbc_password"));
		String dataBaseName = StringUtils.trim(xzcontext.get("dataBaseName"));
		Connection conn = DataBaseHelper.getConnection(url, username, password);
		// 向VelocityContext中放入键值
		context.put("entityList", EntityUtils.getCloumnToDTO(conn, dataBaseName, StringUtils.trim(xzcontext.get("tableName"))));
		// 实例化一个StringWriter
		StringWriter writer = new StringWriter();
		velocityEngine.mergeTemplate(inPath, "UTF-8", context, writer);
		OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(new File(outPath), true), "UTF-8");
		fw.append(writer.toString());
		fw.flush();
		fw.close();
	}

	/**
	 * 初始化模版对应配置参数.
	 * 
	 * @param xmlpath
	 *            模版对应配置参数XML文件路径地址
	 */
	@SuppressWarnings("rawtypes")
	private static void initContext(String xmlpath) {
		try {
			SAXBuilder saxbuilder = new SAXBuilder();
			Document document = saxbuilder.build(new File(xmlpath));
			Element rootElement = document.getRootElement();
			List list = rootElement.getChildren();
			for (int i = 0; i < list.size(); i++) {
				Element tempEle = (Element) list.get(i);
				tempEle.getText();
				String key = StringUtils.trim(tempEle.getAttributeValue("key"));
				String value = StringUtils.trim(tempEle.getText());
				xzcontext.put(key, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 构造表格参数列表.
	 * 
	 * @param gridparams
	 *            结果集合
	 * @param key
	 *            参数名
	 * @param val
	 *            参数值
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	private static void bulidGridparams(List gridparams, String key, String val) {
		if ("grid_colnames".equals(key) || "grid_names".equals(key) || "grid_coltypes".equals(key)) {
			if (gridparams != null && gridparams.size() > 0) {
				for (int i = 0; i < gridparams.size(); i++) {
					GriditemParam gp = (GriditemParam) gridparams.get(i);
					if ("grid_colnames".equals(key)) {
						String vals[] = val.split(",");
						gp.setColname(vals[i]);
					} else if ("grid_names".equals(key)) {
						String vals[] = val.split(",");
						gp.setName(vals[i]);
					} else if ("grid_coltypes".equals(key)) {
						String vals[] = val.split(",");
						gp.setColtype(vals[i]);
					}
				}
			} else {
				String vals[] = val.split(",");
				if (vals != null && vals.length > 0) {
					for (int i = 0; i < vals.length; i++) {
						GriditemParam gp = new GriditemParam();
						if ("grid_colnames".equals(key)) {
							gp.setColname(vals[i]);
						} else if ("grid_names".equals(key)) {
							gp.setName(vals[i]);
						} else if ("grid_coltypes".equals(key)) {
							gp.setColtype(vals[i]);
						}
						gridparams.add(gp);
					}
				}

			}
		}
	}

	/**
	 * 构造表格其他参数.
	 * 
	 * @param othermethods
	 *            结果集合
	 * @param key
	 *            参数名
	 * @param val
	 *            参数值
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	private static void bulidOtherMethods(List othermethods, String key, String val) {
		if ("otherTableName".equals(key) || "otherPkName".equals(key) || "otherMethodSuffix".equals(key) || "otherMethodName".equals(key)) {
			if (othermethods != null && othermethods.size() > 0) {
				for (int i = 0; i < othermethods.size(); i++) {
					OtherMethod om = (OtherMethod) othermethods.get(i);
					if ("otherTableName".equals(key)) {
						String vals[] = val.split(",");
						om.setOtherTableName(vals[i]);
					} else if ("otherPkName".equals(key)) {
						String vals[] = val.split(",");
						om.setOtherPkName(vals[i]);
					} else if ("otherMethodSuffix".equals(key)) {
						String vals[] = val.split(",");
						om.setOtherMethodSuffix(vals[i]);
					} else if ("otherMethodName".equals(key)) {
						String vals[] = val.split(",");
						om.setOtherMethodName(vals[i]);
					}
				}
			} else {
				String vals[] = val.split(",");
				if (vals != null && vals.length > 0) {
					for (int i = 0; i < vals.length; i++) {
						OtherMethod om = new OtherMethod();
						if ("otherTableName".equals(key)) {
							om.setOtherTableName(vals[i]);
						} else if ("otherPkName".equals(key)) {
							om.setOtherPkName(vals[i]);
						} else if ("otherMethodSuffix".equals(key)) {
							om.setOtherMethodSuffix(vals[i]);
						} else if ("otherMethodName".equals(key)) {
							om.setOtherMethodName(vals[i]);
						}
						othermethods.add(om);
					}
				}

			}
		}
	}
}
