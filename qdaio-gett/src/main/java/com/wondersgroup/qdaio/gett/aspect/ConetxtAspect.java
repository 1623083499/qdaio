package com.wondersgroup.qdaio.gett.aspect;

import com.alibaba.fastjson.JSON;
import com.wondersgroup.qdaio.gett.context.ContextUtils;
import com.wondersgroup.qdaio.gett.context.ResultType;
import com.wondersgroup.qdaio.gett.dto.ContextDto;
import com.wondersgroup.qdaio.gett.dto.ParamOutDto;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConetxtAspect {
    private static Logger logger= LoggerFactory.getLogger(ConetxtAspect.class);

    /**
     * 请求进入前
     * @param pjp
     */
    public  void beforeCheck(JoinPoint pjp){
        Object[] args= pjp.getArgs();
        for (Object object:args){
          if(object instanceof ContextDto){
              ContextUtils.setContext((ContextDto) object);
              logger.info("REQUEST:{}",object.toString());
          }
        }

    }

    /**
     * 异常处理
     * @param throwable
     */
    public void exceptionCatch(Throwable throwable){
        logger.info(throwable.getMessage());
    }

    /**
     * 请求结果处理
     * @param joinPoint
     * @return
     */
    public String arround(ProceedingJoinPoint joinPoint){
        String retMsg;
        try {
            retMsg=(String)joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            ParamOutDto paramOutDto =new ParamOutDto();
            paramOutDto.setFlag(ResultType.ERROR);
            paramOutDto.setErrorMsg(throwable.getMessage());
            retMsg= JSON.toJSONStringWithDateFormat(paramOutDto, "yyyy-MM-dd HH:mm:ss.SSS");
        }
        logger.info("RESPONSE:{}",retMsg);
        return retMsg;
    }
}
