package conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by lyen on 16-11-6.
 */
public class SparkProperties {
    /**
     * 在使用此类的地方建立resources资源文件夹并创建spark.properries文件
     */

    private static final Logger log = LoggerFactory.getLogger(SparkProperties.class);
    private static final String confFile = "spark.properties";
    static Properties props = null;

    static {
        props = new Properties();
        try {
            InputStream in = SparkProperties.class.getClassLoader().getResourceAsStream(confFile);
            props.load(in);
        } catch (Exception e) {
            log.info("can not find the spark config file: spark.properties");
        }
    }

    public String getProperties(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }
    /*
    public static void main(String[] args) {
        System.out.println(new SparkProperties().getProperties("spark.mast",null));
    }
    */
}
