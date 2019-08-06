/*
 * Copyright(c) 2017-2100 万达信息股份有限公司 版权所有
 * WONDERS INFORMATION CO., LTD. ALL RIGHTS RESERVED.
 */

package com.wondersgroup.qdaio.gett.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Json格式转换
 * @author: dingjiejun
 * @version: 1.0
 * @date: 2017/3/23
 */
public class JsonUtils {

    private static ObjectMapper objectmapper = new JacksonObjectMapper();

    /**
     * 将对象转为JSON
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        try {
            String json = objectmapper.writeValueAsString(obj);
            return json;
        } catch (Exception e) {
            throw new RuntimeException("对象序列化失败",e);
        }
    }

    /**
     * 将JSON转为对象
     * @param json
     * @param clzz
     * @return
     */
    public static <T> T toBean(String json, Class<T> clzz) {
        try {
            T bean = objectmapper.readValue(json, clzz);
            return bean;
        }  catch (Exception e) {
            throw new RuntimeException("Json反序列化失败",e);
        }
    }

    /**
     * 将JSON转为List
     *
     * @param json
     * @param clzz
     * @return
     */
    public static <T> List<T> toList(String json, Class<T> clzz) {
        try {
            JavaType javaType = objectmapper.getTypeFactory().constructCollectionType(List.class, clzz);
            List<T> list = objectmapper.readValue(json, javaType);
            return list;
        } catch (Exception e) {
            throw new RuntimeException("Json反序列化失败",e);
        }
    }

    /**
     * 将JSON转为Array
     *
     * @param json
     * @param clzz
     * @return
     */
    public static <T> T[] toArray(String json, Class<T> clzz) {
        try {
            JavaType javaType = objectmapper.getTypeFactory().constructArrayType(clzz);
            T[] array = objectmapper.readValue(json, javaType);
            return array;
        } catch (Exception e) {
            throw new RuntimeException("Json反序列化失败",e);
        }
    }

    /**
     * 将JSON转为Map<String,?>
     *
     * @param json
     * @param clzz
     * @return
     */
    public static <T> Map<String, T> toMap(String json, Class<T> clzz) {
        try {
            JavaType javaType = objectmapper.getTypeFactory().constructMapType(HashMap.class, String.class, clzz);
            Map<String, T> map = objectmapper.readValue(json, javaType);
            return map;
        } catch (Exception e) {
            throw new RuntimeException("Json反序列化失败",e);
        }
    }

}
