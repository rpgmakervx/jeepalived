package org.code4j.jeepalived.config;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Description :
 * Created by code4j on 2016/6/7 0007
 * 16:01
 */
class Configuration {

    private JSONObject params;
    private static Configuration configuration;

    public static Configuration getConfiguration(){
        synchronized (Configuration.class){
            if (configuration == null){
                configuration = new Configuration();
            }
        }
        return configuration;
    }
    private Configuration(){
        InputStream is = Configuration.class.getResourceAsStream("/config.json");
        System.out.println(is);
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
