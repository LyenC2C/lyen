package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.util.*;

/**
 * Created by qf on 15/8/5.
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    static Logger log = LoggerFactory.getLogger(StringUtils.class);

    private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};


    public static String fromByteBuffer(ByteBuffer buffer) {

        Charset charset = null;
        CharsetDecoder decoder = null;
        CharBuffer charBuffer = null;
        try {
            charset = Charset.forName("UTF-8");
            decoder = charset.newDecoder();
            charBuffer = decoder.decode(buffer.asReadOnlyBuffer());
            return charBuffer.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    /**
     * 将输入对象转换成String，输入对象为null时 返回空字符串 ""
     */
    public static String toStringNotNull(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    /**
     * 取得拆分后的最后一个字符串 substringAfterLast("foo.bar.baz", ".") == "baz"
     *
     * @param str   要拆分的字符串
     * @param split 拆分字符
     * @return 最后一个字符串
     */
    public static String substringAfterLast(String str, String split) {
        String[] strs = str.split(split);
        if (strs.length == 0) {
            return "";
        }
        return strs[strs.length - 1];
    }

    public static String substringBeforeLast(String str, String separator) {
        if (isEmpty(str) || isEmpty(separator)) {
            return str;
        }
        int pos = str.lastIndexOf(separator);
        if (pos == -1) {
            return str;
        }
        return str.substring(0, pos);
    }

    /**
     * 连接字符串数组，将其连成一个字符串。
     *
     * @param str 要连接的字符串数组
     * @return 连接后的字符串
     */
    public static String connect(Object[] str) {
        return connect(str, "\n");
    }

    /**
     * 连接字符串数组，将其连成一个字符串。
     *
     * @param str       要连接的字符串数组
     * @param separator 两个字符串之间的分割符号
     * @return 连接后的字符串
     */
    public static String connect(Object[] str, String separator) {
        if (str == null) {
            return null;
        }
        if (str.length == 0) {
            return "";
        }

        StringBuffer result = new StringBuffer("");
        for (int i = 0; i < str.length - 1; i++) {
            result.append(str[i]);
            result.append(separator);
        }
        result.append(str[str.length - 1]);

        return result.toString();
    }

    /**
     * 统计文本中 关键字 key 出现的次数 <br/>
     * 例：count("111", "11") == 2
     *
     * @param text 要统计的文本
     * @param key  在文本中查找的关键字
     * @return 关键字出现的次数
     */
    public static int count(String text, String key) {
        int count = 0;
        int offset = text.indexOf(key);
        while (offset >= 0) {
            count++;
            offset = text.indexOf(key, offset + 1);
        }
        return count;
    }

    /**
     * 将匈牙利命名方式转成 驼式命名
     *
     * @param text
     * @return
     */
    public static String nameToUnderline(final String text) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (Character.isUpperCase(ch)) {
                sb.append("_");
            }
            sb.append(Character.toUpperCase(ch));
        }
        return sb.toString();
    }

    /**
     * 产生随机字符串,主要用于密码MD5字段校验
     *
     * @param length 字符串长度
     * @return 随机字符串
     */
    public static String randomStr(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            String charOrNum = (random.nextInt(2)+1) % 2 == 0 ? "char" : "num";//输出字母还是数字
            if (charOrNum.equalsIgnoreCase("char")) {// 字符串
                int choice = (random.nextInt(2)+1) % 2 == 0 ? 65 : 97;//取得大写字母还是小写字母
                val += (char) (choice + random.nextInt(26));
            } else if (charOrNum.equalsIgnoreCase("num")) {//数字
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    /**
     * 指定长度的随机纯数字字符串
     * 修正出现小数点的问题
     *
     * @param len
     * @return
     */
    public static String randomNumberStr(int len) {
        Random rm = new Random(System.currentTimeMillis());
        Double pross = (1 + rm.nextDouble()) * Math.pow(10, len);
        String fixLenthString = String.valueOf(pross.intValue());
        return fixLenthString.substring(0, Math.min(len, fixLenthString.length()));
    }

    /**
     * 指定长度的随机纯数字字符串
     * 修正出现小数点的问题
     *
     * @return
     */
    public static Integer randomCodeNumber() {
        Random rm = new Random(System.currentTimeMillis());
        return rm.nextInt(899999) + 100000;
    }

    /**
     * 将String元素集合拼接成字符串
     *
     * @param list      元素集合
     * @param separator 元素之间的分隔符
     * @return
     */
    public static String list2String(List<String> list, String separator) {
        StringBuilder sb = new StringBuilder();
        for (String string : list) {
            sb.append(string).append(separator);
        }
        return sb.toString();
    }

    public static Map<String, String> stringToMap(String str, String keyValueSpilter, String itemSpilter) {

        String[] values = str.split(itemSpilter);
        Map<String, String> map = new HashMap<String, String>();
        for (String value : values) {
            if (isBlank(value))
                continue;
            String[] keyValue = value.split(keyValueSpilter);
            if (keyValue.length < 2) {
                map.put(keyValue[0], keyValue[0]);
            } else {
                map.put(keyValue[0], keyValue[1]);
            }
        }
        return map;
    }

    public static String formatStr(String str, Map<String, String> data) {

        if (data == null || isBlank(str)) return str;

        for (String key : data.keySet()) {
            String value = data.get(key) == null ? "" : data.get(key);
            str = str.replace("[:" + key + "]", value);
        }
        return str;
    }

    /**
     * @param value
     * @param length
     * @return
     */
    public static String formatLong(Long value, int length) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        nf.setMaximumIntegerDigits(length);
        nf.setMinimumIntegerDigits(length);
        return nf.format(value);
    }

    public static String getString(String str, int repeatNum) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < repeatNum; i++) {
            sb.append(str);
        }
        return sb.toString();
    }

    public static String sha1Hex(String data) {
        if (data == null) {
            throw new IllegalArgumentException("data must not be null");
        }

        byte[] bytes = digest("SHA1", data);

        return toHexString(bytes);
    }

    private static byte[] digest(String algorithm, String data) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        return digest.digest(data.getBytes());
    }

    private static String toHexString(byte[] bytes) {
        int l = bytes.length;

        char[] out = new char[l << 1];

        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS[(0xF0 & bytes[i]) >>> 4];
            out[j++] = DIGITS[0x0F & bytes[i]];
        }

        return new String(out);
    }

    public static String toString(Map data) {

        StringBuilder sb = new StringBuilder();
        for (Object key : data.keySet()) {
            sb.append(key);
            sb.append(":");
            sb.append(data.get(key));
            sb.append(",");
        }
        return sb.toString();
    }
}

