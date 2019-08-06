package com.wondersgroup.qdaio.gett.service;

import com.wondersgroup.qdaio.gett.context.ContextHolder;
import com.wondersgroup.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QdzhrsServiceImpl implements QdzhrsService{
    @Override
    public String test(String param) {
        String result = HttpClientUtils.doPostJson(ContextHolder.getProperty("qdzhrs.people.insured"), param);
        System.out.println(result+"数据");
        return result;
    }
}
