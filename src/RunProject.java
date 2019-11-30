import velocity.entity.FilePath;
import velocity.helper.FileHelper;

import java.io.File;

/**
 */
public class RunProject {

    /**
     * 执行这里生成代码
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("开始自动生成模板 ...");

        File f = new File(RunProject.class.getResource("/").getPath() + File.separator + "template_app" + File.separator);
        System.out.println("正在执行中 ..." + f);
        FilePath fp = new FilePath();
        FileHelper.buildCode(f, 1, fp);
        System.out.println("自动生成模板完成 ...");

    }

    //private static void getaVoid() {
   //     f1();
   // }

//    public static void f1() {
//        System.out.println("noe");
//        System.out.println("two");
//        System.out.println("three");
//    }
//
//    public static void f2() {
//        for (char c = 0; c < 123; c++) {
//            if (Character.isLowerCase(c)) {
//                System.out.println("value:" + (int) c + "character" + c);
//            }
//        }
//        getaVoid();
//    }
}
