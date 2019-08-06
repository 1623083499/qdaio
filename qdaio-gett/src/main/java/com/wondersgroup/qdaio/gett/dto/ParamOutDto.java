package com.wondersgroup.qdaio.gett.dto;

import com.wondersgroup.qdaio.gett.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanMap;

import java.util.HashMap;
import java.util.Map;

public class ParamOutDto {
    private String flag;
    private String errorMsg;
    private Map<String, String> result;

    public ParamOutDto(String flag, String error, Map<String, String> result) {
        this.flag = flag;
        this.errorMsg = error;
        this.result = result;
    }

    public ParamOutDto() {
        this.flag = "0";
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Map<String, String> getResult() {
        return result;
    }

    public void setResult(Map<String, String> result) {
        this.result = result;
    }

    public ParamOutDto put(String key, String value) {
        if (this.getResult() == null) {
            this.setResult(new HashMap<String, String>());
        }
        this.getResult().put(key, value);
        return this;
    }

    public ParamOutDto setMap(Object bean) {
        if (bean != null) {
            this.result = BeanMap.create(bean);
        }
        return this;
    }

    public ParamOutDto setMapExt(String values) {
        if (StringUtils.isNotBlank(values)) {
            Map<String, String> map = null;
            try {
                map = JsonUtils.toMap(values, String.class);
            } catch (Exception e) {
                map = new HashMap<String, String>();
                map.put("params", values);
            }
            this.result = map;
        }
        return this;
    }

    public ParamOutDto delete(String key) {
        if (this.getResult() == null) {
            return this;
        }
        this.getResult().remove(key);
        return this;
    }
}
