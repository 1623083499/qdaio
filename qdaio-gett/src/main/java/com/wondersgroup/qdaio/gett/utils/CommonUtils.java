package com.wondersgroup.qdaio.gett.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wondersgroup.qdaio.gett.context.BusinessException;
import com.wondersgroup.qdaio.gett.context.ContextHolder;
import com.wondersgroup.qdaio.gett.context.ContextUtils;
import com.wondersgroup.qdaio.gett.context.ResultMsg;
import com.wondersgroup.qdaio.gett.dto.ContextDto;
import com.wondersgroup.utils.SecureUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 公共工具类
 *
 * @author yfb
 */
public class CommonUtils {

    /**
     * 解密入参,转换成json对象
     *
     * @param contextDto
     * @return
     */
    public static JSONObject decryParams(ContextDto contextDto) {
        if (StringUtils.isBlank(contextDto.getParams())) {
            throw new BusinessException("params 为空");
        }
        String cleartext = decryStr(contextDto.getParams(), contextDto.getKey());
        ContextUtils.getContext().setParamInClearText(cleartext);
        JSONObject object = JSONObject.parseObject(cleartext);
        return object;
    }

    /**
     * 解密
     *
     * @param cipherText
     * @param key
     * @return
     */
    public static String decryStr(String cipherText, String key) {
        String cleartext;
        try {
            cleartext = SecureUtils.decryStr(cipherText, key);
        } catch (Exception e) {
            throw new BusinessException(ResultMsg.DECRY_ERROR);
        }
        return cleartext;
    }

    /**
     * 获取客户IP地址
     *
     * @param request
     * @return
     */
    public static String getRemoteAddr(HttpServletRequest request) {
        String remoteAddr = request.getHeader("X-Real-IP");
        if (StringUtils.isNotBlank(remoteAddr)) {
            remoteAddr = request.getHeader("X-Forwarded-For");
        } else if (StringUtils.isNotBlank(remoteAddr)) {
            remoteAddr = request.getHeader("Proxy-Client-IP");
        } else if (StringUtils.isNotBlank(remoteAddr)) {
            remoteAddr = request.getHeader("WL-Proxy-Client-IP");
        }
        return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
    }

    /**
     * 获取http请求表头
     * @param request
     * @return
     */
    public static String getHeadersInfo(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return JSON.toJSONString(map);
    }

    /**
     * 获取请求参数
     * @param request
     * @return
     */
    public static Map<String, String> getParameterMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        Map paramMap = request.getParameterMap();
        Iterator entries = paramMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            String key = (String)entry.getKey();
            String[] value = (String[]) entry.getValue();
            if (!ArrayUtils.isEmpty(value)) {
                map.put(key, value[0]);
            }
        }
        return map;
    }

    /**
     * 将Map对象转为String
     * @param paramMap
     * @return
     */
    public static String getParams(Map paramMap) {
        if (paramMap == null) {
            return null;
        }
        StringBuilder params = new StringBuilder();
        for (Map.Entry<String, String[]> param : ((Map<String, String[]>) paramMap).entrySet()) {
            params.append(("".equals(params.toString()) ? "" : "&") + param.getKey() + "=");
            String paramValue = (param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : "");
            params.append(abbr(StringUtils.endsWithIgnoreCase(param.getKey(), "password") ? "" : paramValue, 100));
        }
        return params.toString();
    }

    public static String abbr(String str, int length) {
        if (str == null) {
            return "";
        } else {
            try {
                StringBuilder sb = new StringBuilder();
                int currentLength = 0;
                char[] var7;
                int var6 = (var7 = replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray()).length;

                for (int var5 = 0; var5 < var6; ++var5) {
                    char c = var7[var5];
                    currentLength += String.valueOf(c).getBytes("GBK").length;
                    if (currentLength > length - 3) {
                        sb.append("...");
                        break;
                    }
                    sb.append(c);
                }
                return sb.toString();
            } catch (UnsupportedEncodingException var8) {
                var8.printStackTrace();
                return "";
            }
        }
    }

    public static String replaceHtml(String html) {
        if (StringUtils.isBlank(html)) {
            return "";
        } else {
            String regEx = "<.+?>";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(html);
            String s = m.replaceAll("");
            return s;
        }
    }

}
