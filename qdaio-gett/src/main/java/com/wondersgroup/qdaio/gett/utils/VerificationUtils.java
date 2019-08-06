package com.wondersgroup.qdaio.gett.utils;

import com.wondersgroup.framwork.dao.CommonJdbcUtils;
import com.wondersgroup.qdaio.gett.dto.PublicGettDto;
import com.wondersgroup.qdaio.gett.dto.Zq04DTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 规则校验
 */
public class VerificationUtils {
    private static final String STRING = "string";
    private static final String DATE = "date";
    private static final String LONG = "long";
    private static final String DOUBLE = "double";
    private static final String INTEGER = "integer";
    private static final String BIGDECIMAL = "bigdecimal";
    /**
     * 参数校验
     */
    public static String paramsRule(PublicGettDto dto){
        Map<String,String> result = new HashMap<String, String>();
        if (dto == null) {
            result.put("message","对象为空");
            return JsonUtils.toJson(result);
        }
        String busiid = dto.getBusiid();
        if (StringUtils.isBlank(busiid)) {
            result.put("message","busiid为空");
            return JsonUtils.toJson(result);
        }
        //获取规则
        String ruleSql = "select * from zq04 where aae100='1' and aaa121=?";
        List<Zq04DTO> zq04DTOS = CommonJdbcUtils.queryList(ruleSql, Zq04DTO.class, busiid);
        if (CollectionUtils.isEmpty(zq04DTOS)) {
//            result.put("msg","该接口不存在校验规则");
            return null;
        }
        //需要把对象转成Class对象,使用反射来判断
        Class<? extends PublicGettDto> aClass = dto.getClass();
        try {
            for (Zq04DTO zq04DTO : zq04DTOS) {
                //判断是否是必填项
                if ("1".equals(zq04DTO.getZqa015())) {
                    //获取属性
                    Field field = aClass.getDeclaredField(zq04DTO.getZqa013());
                    field.setAccessible(true);
                    String value = String.valueOf(field.get(dto));
                    //为空直接返回false
                    if (StringUtils.isBlank(value) || "null".equals(value)) {
                        result.put("message",zq04DTO.getZqa014()+"为空");
                        return JsonUtils.toJson(result);
                    }
                    //判断参数类型/参数长度
                    if (!isParamType(zq04DTO,value)) {
                        result.put("message",zq04DTO.getZqa014()+"字段超长");
                        return JsonUtils.toJson(result);
                    }
                }
            }
        }catch (Exception e){
            result.put("message",e.getMessage());
            return JsonUtils.toJson(result);
        }
        return null;
    }

    /**
     * 参数类型
     */
    private static boolean isParamType(Zq04DTO zq04DTO,String value) throws Exception{
        String javaType = replaceJavaType(zq04DTO.getZqa017());//获取对应的java类型
        try {
            if(STRING.equals(javaType)){
                return value.getBytes().length <= zq04DTO.getZqa018();
            }else if(DATE.equals(javaType)){

            }else if(LONG.equals(javaType)){
                Long.valueOf(value);
            }else if(DOUBLE.equals(javaType)){
                Double.valueOf(value);
            }else if(INTEGER.equals(javaType)){
                Integer.valueOf(value);
            }else if(BIGDECIMAL.equals(javaType)){
                Double.valueOf(value);
            }
        }catch (Exception e){
            throw new Exception(zq04DTO.getZqa014()+"类型错误");
        }
        return true;
    }

    /**
     * java类型
     * @param typeName
     * @return
     */
    private static String replaceJavaType(String typeName) {
        //BINARY VARBINARY LONGVARBINARY尚未添加
        typeName = typeName.toUpperCase();
        if ("CHAR".equals(typeName)) {
            return STRING;
        }
        if ("VARCHAR".equals(typeName)) {
            return STRING;
        }
        if ("LONGVARCHAR".equals(typeName)) {
            return STRING;
        }
        if ("ENUM".equals(typeName)) {
            return STRING;
        }
        if ("TEXT".equals(typeName)) {
            return STRING;
        }
        if ("NUMERIC".equals(typeName)) {
            return BIGDECIMAL;
        }
        if ("DECIMAL".equals(typeName)) {
            return BIGDECIMAL;
        }
        if ("BIGDECIMAL".equals(typeName)) {
            return BIGDECIMAL;
        }
        if ("DATE".equals(typeName)) {
            return DATE;
        }
        if ("DATETIME".equals(typeName)) {
            return DATE;
        }
        if ("TIMESTAMP".equals(typeName)) {
            return DATE;
        }
        if ("TINYINT".equals(typeName)) {
            return INTEGER;
        }
        if ("SMALLINT".equals(typeName)) {
            return INTEGER;
        }
        if ("BIT".equals(typeName)) {
            return INTEGER;
        }
        if ("INT".equals(typeName)) {
            return INTEGER;
        }
        if ("BIGINT".equals(typeName)) {
            return LONG;
        }
        if ("REAL".equals(typeName)) {
            return DOUBLE;
        }
        if ("FLOAT".equals(typeName)) {
            return DOUBLE;
        }
        if ("DOUBLE".equals(typeName)) {
            return DOUBLE;
        }
        //oracle
        if ("NUMBER".equals(typeName)) {
            return LONG;
        }
        if ("VARCHAR2".equals(typeName)) {
            return STRING;
        }
        return null;
    }
}
