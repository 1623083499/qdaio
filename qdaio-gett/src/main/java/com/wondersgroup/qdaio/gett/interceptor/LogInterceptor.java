package com.wondersgroup.qdaio.gett.interceptor;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wondersgroup.qdaio.gett.utils.LogUtils;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 日志拦截器
 * @author yfb
 */
public class LogInterceptor implements HandlerInterceptor {
	private FilterChain chain;
	private static final ThreadLocal<Long> startTimeThreadLocal =
			new NamedThreadLocal<Long>("LogInterceptor StartTime");
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler) throws Exception {
		// 1、开始时间
		long beginTime = System.currentTimeMillis();
		// 线程绑定变量（该数据只有当前请求的线程可见）
		startTimeThreadLocal.set(beginTime);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, 
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
								Object handler, Exception ex) throws Exception {
		// 得到线程绑定的局部变量（开始时间）
		long beginTime = startTimeThreadLocal.get();
		// 2、结束时间
		long endTime = System.currentTimeMillis();
		// 3、获取执行时间
		long executeTime = endTime - beginTime;
		// 用完之后销毁线程变量数据
		startTimeThreadLocal.remove();
		// 保存日志
		LogUtils.saveLog(request, handler, ex, beginTime, executeTime);
	}

}
