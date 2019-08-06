package com.wondersgroup.qdaio.gett.contrallor;

import com.alibaba.fastjson.JSONObject;
import com.wondersgroup.qdaio.gett.context.ContextHolder;
import com.wondersgroup.qdaio.gett.dto.ContextDto;
import com.wondersgroup.qdaio.gett.service.GettServiceImpl;
import com.wondersgroup.qdaio.gett.service.QdzhrsService;
import com.wondersgroup.qdaio.gett.utils.CommonUtils;
import com.wondersgroup.qdaio.gett.utils.ResultUtils;
import com.wondersgroup.qdaio.gett.validate.ParamsValidate;
import com.wondersgroup.utils.HttpClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
@RestController
@RequestMapping("/qdzhrs/case")
public class QdzhrsController {
    @Autowired
    private QdzhrsService qdzhrsService;
    @Autowired
    private ParamsValidate paramsValidate;

    private static Logger logger = LoggerFactory.getLogger(QdzhrsController.class);
    public QdzhrsService getQdzhrsService() {
        return qdzhrsService;
    }

    public void setQdzhrsService(QdzhrsService qdzhrsService) {
        this.qdzhrsService = qdzhrsService;
    }

    @RequestMapping(value = "/test", produces = "text/html;charset=utf-8")
    public String saveApply(HttpServletRequest request, ContextDto contextDto) {
        String result = null;
        String message = null;
        try {
            // 验证
            paramsValidate.validateAccessToken();
            // 解密 获取的所有的params的数据
          JSONObject jsonObject= CommonUtils.decryParams(contextDto);
            // 人员参保
           message = qdzhrsService.test(jsonObject.toString());
            // 返回结果
            result = ResultUtils.toJSONString(request, message, contextDto.getKey());
        } catch (Exception e) {
            message = e.getMessage();
            result = ResultUtils.toJSONString(request, message, contextDto.getKey());
        }
        return result;
    }
}
