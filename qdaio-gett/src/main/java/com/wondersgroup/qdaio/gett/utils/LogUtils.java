package com.wondersgroup.qdaio.gett.utils;

import javax.servlet.http.HttpServletRequest;

import com.wondersgroup.framwork.dao.CommonJdbcUtils;
import com.wondersgroup.qdaio.gett.bo.GettLog;
import com.wondersgroup.qdaio.gett.bo.GettUser;
import com.wondersgroup.qdaio.gett.context.UsersCache;
import com.wondersgroup.qdaio.gett.dto.ContextDto;
import com.wondersgroup.qdaio.gett.dto.GettUserDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.util.Date;
import java.util.Map;

/**
 * 日志工具类
 *
 * @author yfb
 */
public class LogUtils {

    /**
     * 参数名获取工具（尝试获取标注为@ModelAttribute注解的方法，第一个参数名一般为主键名）
     */
    private static ParameterNameDiscoverer pnd = new DefaultParameterNameDiscoverer();

    /**
     * 保存日志
     * @param request
     */
    public static void saveLog(HttpServletRequest request) {
        saveLog(request, null, null, 0, 0);
    }

    /**
     * 保存日志
     * @param request
     * @param handler
     * @param ex
     * @param executeTime
     */
    public static void saveLog(HttpServletRequest request, Object handler, Exception ex, long beginTime, long executeTime) {
        GettLog log = new GettLog();
        ContextDto contextDto = new ContextDto();
        String header = CommonUtils.getHeadersInfo(request);
        String ip = CommonUtils.getRemoteAddr(request);
        String url = request.getRequestURI();
        Map<String, String> paramMap = CommonUtils.getParameterMap(request);
        if (null != paramMap) {
            contextDto.setAppid(paramMap.get("appid"));
            contextDto.setAccess_token(paramMap.get("access_token"));
            contextDto.setBusiid(paramMap.get("busiid"));
            contextDto.setParams(paramMap.get("params"));
        }
        String params = JsonUtils.toJson(paramMap);
        log.setAppid(contextDto.getAppid());
        log.setBusiid(contextDto.getBusiid());
        if (StringUtils.isNotBlank(contextDto.getAppid())) {
            GettUserDto gettUserDto = UsersCache.get(contextDto.getAppid());
            if (null != gettUserDto) {
                String cleartext = CommonUtils.decryStr(contextDto.getParams(), gettUserDto.getKey());
                paramMap.put("params", cleartext);
                log.setUserid(gettUserDto.getUserid());
                log.setKey(gettUserDto.getKey());
            }
        }
        String clearParams = JsonUtils.toJson(paramMap);
        log.setUrl(url);
        log.setParams(params);
        log.setClearparams(clearParams);
        log.setHeaders(header);
        log.setIp(ip);
        log.setCdate(new Date(beginTime));
        log.setUsetime(executeTime);
        Object obj = request.getAttribute("paramOutClearText");
        Object obj2 = request.getAttribute("paramOutCipherText");
        if (null != obj) {
            log.setPremessage(obj.toString());
        }
        if (null != obj2) {
            log.setMessage(obj2.toString());
        }
        // 获取异常对象
        Throwable throwable = null;
        if (ex != null) {
            throwable = ExceptionUtils.getThrowable(request);
        }
        // 异步保存日志
        new SaveLogThread(log, handler, request.getContextPath(), throwable).start();
    }

    /**
     * 保存日志线程
     */
    public static class SaveLogThread extends Thread {
        private GettLog log;
        private Object handler;
        private String contextPath;
        private Throwable throwable;

        public SaveLogThread(GettLog log, Object handler, String contextPath, Throwable throwable) {
            super(SaveLogThread.class.getSimpleName());
            this.log = log;
            this.handler = handler;
            this.contextPath = contextPath;
            this.throwable = throwable;
        }

        @Override
        public void run() {
            // 如果有异常，设置异常信息（将异常对象转换为字符串）
            // 如果无地址并无异常日志，则不保存信息
            if (StringUtils.isBlank(log.getUrl())) {
                return;
            }
            CommonJdbcUtils.insert(log);
        }
    }

}
