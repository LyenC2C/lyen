package conf;

import com.sun.xml.internal.bind.v2.model.core.EnumConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by lyen on 16-11-6.
 */
public class RetriveConf {

    private static final Logger log = LoggerFactory.getLogger(RetriveConf.class);
    static Properties props = null;
    static InputStream in = null;
    public static void loadProperties(String fileName) {
        try {
            in = new FileInputStream(fileName);
            props = new Properties();
            props.load(in);
        } catch (Exception e) {
            log.info("can not find the config file: " + fileName);
        }finally {
            try {
                in.close();
            }catch (Exception e){
            }
        }
        Enumeration en = props.keys();
        while (en.hasMoreElements()) {
            String keyName = en.nextElement().toString();
        }
    }


}
