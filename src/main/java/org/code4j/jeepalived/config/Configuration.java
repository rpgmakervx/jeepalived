package org.code4j.jeepalived.config;

import com.alibaba.fastjson.JSONObject;

import java.io.*;

/**
 * Description :
 * Created by code4j on 2016/6/7 0007
 * 16:01
 */
class Configuration {

    private JSONObject params;
    private static Configuration configuration;

//    public static Configuration getConfiguration(String configpath){
//        synchronized (Configuration.class){
//            if (configuration == null){
//                configuration = new Configuration(configpath);
//            }
//        }
//        return configuration;
//    }
    public Configuration(String configpath){
        InputStream is = null;
        if (configpath == null||configpath.isEmpty()){
            configpath = "/config.json";
            is = Configuration.class.getResourceAsStream(configpath);
        }else{
            try {
                is = new FileInputStream(configpath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuffer sb = new StringBuffer();
        String str = "";
        try {
            while ((str = br.readLine()) != null){
                sb.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                is.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String json = sb.toString();
        params = JSONObject.parseObject(json);
    }

    public Integer getIntValue(String key){
        return params.getIntValue(key);
    }
    public String getStrValue(String key){
        return params.getString(key);
    }
}
