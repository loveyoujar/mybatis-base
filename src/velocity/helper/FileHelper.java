package velocity.helper;

import velocity.entity.FilePath;
import velocity.utils.StringUtils;

import java.io.File;
import java.util.UUID;

/**
 * 文档帮助类集合.
 * <p>
 * 该文档帮助类集合中提供了以下几个方法.<br>
 * 解析对应目录的文档的设置参数.<br>
 * 递归创建文件夹.
 * 
 * @see File
 * @see UUID
 */
public class FileHelper {

	/**
	 * 存放输出文件的路径变量
	 */
	private static String outputPath = "";

	/**
	 * 定义是否进行替换模版开关，默认为打开
	 */
	private static boolean IS_OPEN = true;

	/**
	 * 私有构造的方法，不允许外部类直接new出对象
	 */
	private FileHelper() {
	}

	/**
	 * 解析对应目录的文档的设置参数.
	 * 
	 * @param f
	 *            要递归的目录结构File对象
	 * @param leval
	 *            目录深度记录器
	 * @param fp
	 *            目录路径对象
	 */
	@SuppressWarnings("unused")
	public static void buildCode(File f, int leval, FilePath fp) {
		String preStr = "";
		for (int i = 0; i < leval; i++) {
			preStr += "        ";
		}
		File[] childs = f.listFiles();
		if (childs == null || childs.length <= 0) {
			System.out.println("this project running error ... ");
		}
		for (int i = 0; i < childs.length; i++) {
			UUID uuid = UUID.randomUUID();
			fp.setLocal_file_code(uuid.toString());
			if (leval == 1) {
				fp.setLeval1(childs[i].getName());
				fp.setLeval2("");
				fp.setLeval3("");
				fp.setLeval4("");
				fp.setLeval5("");
				fp.setLeval6("");
			} else if (leval == 2) {
				fp.setLeval2(childs[i].getName());
				fp.setLeval3("");
				fp.setLeval4("");
				fp.setLeval5("");
				fp.setLeval6("");
			} else if (leval == 3) {
				fp.setLeval3(childs[i].getName());
				fp.setLeval4("");
				fp.setLeval5("");
				fp.setLeval6("");
			} else if (leval == 4) {
				fp.setLeval4(childs[i].getName());
				fp.setLeval5("");
				fp.setLeval6("");
			} else if (leval == 5) {
				fp.setLeval5(childs[i].getName());
				fp.setLeval6("");
			} else if (leval == 6) {
				fp.setLeval6(childs[i].getName());
			}
			if (!childs[i].isDirectory()) {
				if (fp.getLeval1().equals(childs[i].getName())) {
					fp.setLeval1("");
					fp.setLeval6(childs[i].getName());
				} else if (fp.getLeval2().equals(childs[i].getName())) {
					fp.setLeval2("");
					fp.setLeval6(childs[i].getName());
				} else if (fp.getLeval3().equals(childs[i].getName())) {
					fp.setLeval3("");
					fp.setLeval6(childs[i].getName());
				} else if (fp.getLeval4().equals(childs[i].getName())) {
					fp.setLeval4("");
					fp.setLeval6(childs[i].getName());
				} else if (fp.getLeval5().equals(childs[i].getName())) {
					fp.setLeval5("");
					fp.setLeval6(childs[i].getName());
				}
				StringBuilder relativePath = new StringBuilder();
				if (!"".equals(fp.getLeval1())) {
					relativePath.append("/");
					relativePath.append(fp.getLeval1());
				}
				if (!"".equals(fp.getLeval2())) {
					relativePath.append("/");
					relativePath.append(fp.getLeval2());
				}
				if (!"".equals(fp.getLeval3())) {
					relativePath.append("/");
					relativePath.append(fp.getLeval3());
				}
				if (!"".equals(fp.getLeval4())) {
					relativePath.append("/");
					relativePath.append(fp.getLeval4());
				}
				if (!"".equals(fp.getLeval5())) {
					relativePath.append("/");
					relativePath.append(fp.getLeval5());
				}
				fp.setFilepath(relativePath.toString());
				String sourceTempeletePath = "template_app/" + fp.getLeval1() + "/" + fp.getLeval2() + "/" + fp.getLeval3() + "/" + fp.getLeval4() + "" + fp.getLeval5() + "" + fp.getLeval6();
				String outRoot = outputPath + "generator-output/" + fp.getLeval1() + "/" + fp.getLeval2() + "/" + fp.getLeval3() + "/" + fp.getLeval4() + "" + fp.getLeval5();
				if (IS_OPEN) {
					outRoot = outRoot.replace("${basepackage_dir}", StringUtils.trim(GeneratorVelocityHelper.xzcontext.get("basepackage_dir")));
					outRoot = outRoot.replace("${basepackage}", StringUtils.trim(GeneratorVelocityHelper.xzcontext.get("basepackage")));
					outRoot = outRoot.replace("${modelName}", StringUtils.trim(GeneratorVelocityHelper.xzcontext.get("modelName")));
					outRoot = outRoot.replace("${className}", StringUtils.trim(GeneratorVelocityHelper.xzcontext.get("className")));
					outRoot = outRoot.replace("${injectClassName}", StringUtils.trim(GeneratorVelocityHelper.xzcontext.get("injectClassName")));
					outRoot = outRoot.replace(".", "/");
					CreateFileServer2(outRoot);
					try {
						String fileName = fp.getLeval6();
						fileName = fileName.replace("${modelName}", StringUtils.trim(GeneratorVelocityHelper.xzcontext.get("modelName")));
						fileName = fileName.replace("${className}", StringUtils.trim(GeneratorVelocityHelper.xzcontext.get("className")));
						fileName = fileName.replace("${injectClassName}", StringUtils.trim(GeneratorVelocityHelper.xzcontext.get("injectClassName")));
						fileName = fileName.replace("vm", "java");
						GeneratorVelocityHelper.outPutFile(sourceTempeletePath, outRoot + fileName);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
			if (childs[i].isDirectory()) {
				buildCode(childs[i], leval + 1, fp);
			}
		}
	}

	/**
	 * 递归创建文件夹.
	 * 
	 * @param path
	 *            文件的当前路径
	 * @return boolean
	 */
	public static boolean CreateFileServer2(String path) {
		StringBuffer returnStr = new StringBuffer();
		Boolean bool = false;
		String[] paths = path.split("/"); // 根据符号"/"来分隔路径
		int length = paths.length;
		for (int i = 0; i < length; i++) {
			returnStr.append(paths[i]);
			File file = new File(returnStr.toString());
			if (!file.isDirectory()) {
				bool = file.mkdir();
			}
			returnStr.append("/");
		}
		return bool;
	}
}