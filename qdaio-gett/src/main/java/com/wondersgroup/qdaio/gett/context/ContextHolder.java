package com.wondersgroup.qdaio.gett.context;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.stereotype.Repository;
import org.springframework.util.PropertyPlaceholderHelper;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class ContextHolder {
    private static Properties properties;
    private static String fileNames;
    public ContextHolder(String fileNames) {
        ContextHolder.fileNames=fileNames;
    }
    @PostConstruct
    private void init(){
        properties=new Properties();
        String[] files=fileNames.split(";");
        for(String fileName:files){
            readFile(fileName);
        }
    }
    private static void readFile(String fileName){
        String name = ContextHolder.class.getResource("/").getPath();
        String path = name + fileName;
        File file = new File(path);
        if(file.exists())
        {
            try{
                InputStream fis = new FileInputStream(file);
                properties.load(fis);
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static String getProperty(String key){
        return properties.getProperty(key);
    }
    public static Properties getProperties(){
        return properties;
    }
}
