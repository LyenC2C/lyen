import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by lyen on 16-11-6.
 */
public class RetriveConf {

    private static final Logger log = LoggerFactory.getLogger(RetriveConf.class);
    static Properties props = null;

    public static Properties loadProperties(String fileName) {
        try {
            InputStream in = new FileInputStream(fileName);
            props = new Properties();
            props.load(in);
        } catch (Exception e) {
            log.info("can not find the config file: " + fileName);
        }
        return props;
    }

}
