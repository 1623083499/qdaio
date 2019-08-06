/*
 * Copyright(c) 2017-2100 万达信息股份有限公司 版权所有
 * WONDERS INFORMATION CO., LTD. ALL RIGHTS RESERVED.
 */

package com.wondersgroup.qdaio.gett.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Jackson转换器，提供默认值的设定
 * @author dingjiejun
 * @version: 1.0
 * @date: 2017/3/23
 */
public class JacksonObjectMapper extends ObjectMapper {

	private static final long serialVersionUID = 1L;

    private DecimalFormat df = new DecimalFormat("0.######");

    public JacksonObjectMapper() {
        super();
        //禁止日期转换成时间long值
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //设置默认的时间格式
        setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        //忽略未匹配的属性
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        df.setRoundingMode(RoundingMode.HALF_UP);

        SimpleModule module = new SimpleModule();
        module.addSerializer(Double.class, new JsonSerializer<Double>() {
            @Override
            public void serialize(Double value, JsonGenerator jgen,
                                  SerializerProvider arg2) throws IOException {
                if(value==null) {
                    jgen.writeNull();
                } else {
                    jgen.writeNumber(df.format(value));
                }
            }
        });
        module.addSerializer(Float.class, new JsonSerializer<Float>() {
            @Override
            public void serialize(Float value, JsonGenerator jgen,
                                  SerializerProvider arg2) throws IOException {
                if(value==null) {
                    jgen.writeNull();
                } else {
                    jgen.writeNumber(df.format(value));
                }
            }
        });

        module.addDeserializer(Date.class, new JsonDeserializer<Date>(){
            @Override
            public Date deserialize(JsonParser p, DeserializationContext ctxt)
                    throws IOException {
                return DateUtils.parseDate(p.getValueAsString());
            }
        });
        this.registerModule(module);
    }
	
}
