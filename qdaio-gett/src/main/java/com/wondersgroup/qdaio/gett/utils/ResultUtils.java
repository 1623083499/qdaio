package com.wondersgroup.qdaio.gett.utils;

import com.wondersgroup.qdaio.gett.dto.ParamOutDto;
import com.wondersgroup.utils.SecureUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 返回参数公共处理类
 * @author yfb
 */
public class ResultUtils {

    /**
     * 返回信息处理（ParamOutDto方式返回）
     * @param request
     * @param message
     * @param key
     * @return
     */
    public static String toJSON(HttpServletRequest request, String message, String key) {
        String result = null;
        ParamOutDto paramOutDto = new ParamOutDto();
        request.setAttribute("paramOutClearText", JsonUtils.toJson(paramOutDto.setMapExt(message)));
        if (StringUtils.isNotBlank(key)) {
            message = SecureUtils.encryStr(message, key);
            result = JsonUtils.toJson(paramOutDto.setMapExt(message));
            request.setAttribute("paramOutCipherText", result);
        }
        return result;
    }

    /**
     * 返回信息处理（直接返回）
     * @param request
     * @param key
     * @return
     */
    public static String toJSONString(HttpServletRequest request, String result, String key) {
        request.setAttribute("paramOutClearText", result);
        if (StringUtils.isNotBlank(key)) {
            result = SecureUtils.encryStr(result, key);
            request.setAttribute("paramOutCipherText", result);
        }
        return result;
    }
}
