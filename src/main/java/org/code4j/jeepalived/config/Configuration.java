package org.code4j.jeepalived.config;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Description :
 * Created by code4j on 2016/6/7 0007
 * 16:01
 */
class Configuration {

    private Properties properties;

    public Configuration(String configpath){
        InputStream is = null;
        properties = new Properties();
        try {
            if (properties == null||properties.isEmpty()){
                configpath = "/config.properties";
                is = Configuration.class.getResourceAsStream(configpath);
            }else{
                is = new FileInputStream(configpath);
            }
            properties.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        BufferedReader br = new BufferedReader(new InputStreamReader(is));
//        StringBuffer sb = new StringBuffer();
//        String str = "";
//        try {
//            while ((str = br.readLine()) != null){
//                sb.append(str);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            try {
//                is.close();
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//        }finally {
//            try {
//                is.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        String json = sb.toString();
//        params = JSONObject.parseObject(json);
    }

    public Integer getIntValue(String key){
        return Integer.valueOf(properties.getProperty(key));
    }
    public String getStrValue(String key){
        return properties.getProperty(key);
    }
}
