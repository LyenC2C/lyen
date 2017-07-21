import org.apache.commons.lang3.StringUtils;

/**
 * Created by lyen on 16-9-9.
 */
public class Test {


    public static void main(String[] args) {
        /*
        String str=null;
        str=String.format("Hi, %s", "林计钦"); // 格式化字符串
        System.out.println(str); // 输出字符串变量str的内容
        System.out.printf("3>7的结果是：%b %n", 3>7);
        System.out.printf("100的一半是：%d %n", 100/2);
        System.out.printf("50元的书打8.5折扣是：%f 元%n", 50*0.85);
        System.out.printf("上面的折扣是%d%% %n", 85);
        */
        String[] ss = {"asd", "qwe", "axbgfff"};
        String[] sss = {"asd", "qwe", "axbgfff"};
        int j = 0;
        for (String field : ss) {
            ss[j++] = "`" + field + "`";
        }
        for (int i = 0; i < sss.length; i++) {
            sss[i] = "?";
        }
        String s = String.format("insert into %s (%s) values (%s)", "lyen", StringUtils.join(ss, ","), StringUtils.join(sss, ","));
        System.out.println(s);
    }
}
