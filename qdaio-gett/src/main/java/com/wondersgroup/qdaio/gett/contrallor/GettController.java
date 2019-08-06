package com.wondersgroup.qdaio.gett.contrallor;

import com.alibaba.fastjson.JSON;
import com.wondersgroup.qdaio.gett.dto.ContextDto;
import com.wondersgroup.qdaio.gett.dto.ParamOutDto;
import com.wondersgroup.qdaio.gett.service.GettService;
import com.wondersgroup.qdaio.gett.utils.CommonUtils;
import com.wondersgroup.qdaio.gett.utils.ResultUtils;
import com.wondersgroup.qdaio.gett.validate.ParamsValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/case")
public class GettController {
    @Autowired
    private GettService gettService;

    @Autowired
    private ParamsValidate paramsValidate;

    @RequestMapping(value = "/saveApply", produces = "text/html;charset=utf-8")
    public String saveApply(HttpServletRequest request, ContextDto contextDto) {
        String result = null;
        String message = null;
        try {
            // 验证
            paramsValidate.validateAccessToken();
            // 解密
            CommonUtils.decryParams(contextDto);
            // 人员参保
            message = this.gettService.saveApply();
            // 返回结果
            result = ResultUtils.toJSONString(request, message, contextDto.getKey());
        } catch (Exception e) {
            message = e.getMessage();
            result = ResultUtils.toJSONString(request, message, contextDto.getKey());
        }
        return result;
    }
}
