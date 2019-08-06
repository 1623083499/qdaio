package com.wondersgroup.qdaio.gett.contrallor;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wondersgroup.qdaio.gett.bo.GettUser;
import com.wondersgroup.qdaio.gett.context.*;
import com.wondersgroup.qdaio.gett.dto.AccessTokenDto;
import com.wondersgroup.qdaio.gett.dto.ContextDto;
import com.wondersgroup.qdaio.gett.dto.GettUserDto;
import com.wondersgroup.qdaio.gett.dto.ParamOutDto;
import com.wondersgroup.qdaio.gett.service.GettService;
import com.wondersgroup.qdaio.gett.validate.ParamsValidate;
import com.wondersgroup.utils.SecureUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 政企直通主入口
 */
@RestController
public class MainContrallor {

    @Autowired
    private GettService gettService;

    @Autowired
    private ParamsValidate paramsValidate;

    /**
     * 获取接入口令
     *
     * @param contextDto 时间戳“YYYYMMDDHHMMSS”,如“20190101161059”
     * @return
     */
    @GetMapping(value = "/getAccessToken", produces = "application/json; charset=utf-8")
    public String getAccessToken(ContextDto contextDto) {
        // 验签
        paramsValidate.validateSign();
        // 用户密码验证
        paramsValidate.validatePassword();
        // 用户有效性验证
        paramsValidate.validateUser();
        // 获得口令
        GettUserDto gettUserDto = gettService.refreshAccessToken(contextDto.getAppid());
        // 刷新缓存
        UsersCache.put(gettUserDto);
        return JSON.toJSONString(new ParamOutDto().put("access_token", gettUserDto.getAccess_token()));
    }

    /**
     * 上传附件
     *
     * @param contextDto
     * @return
     */
    @RequestMapping(value = "/uploadFile", produces = "text/html; charset=utf-8")
    public String uploadFile(@RequestParam MultipartFile fileData, ContextDto contextDto) {
        // 验证令牌
        paramsValidate.validateAccessToken();
        // 解密
        decryParams(contextDto);
        // 上传
        String result = gettService.uploadAttachment(fileData);
        JSONObject object = JSONObject.parseObject(result);
        if (!"E00".equals(object.get("rtnCode"))) {
            throw new BusinessException("文件上传失败[" + object.get("rtnMsg") + "]");
        }
        // 返回结果
        return JSON.toJSONString(new ParamOutDto().put("pageId", object.getString("pageID"))
                .put("filePath", object.getString("origiFilePath")));
    }

    /**
     * 解密入参,转换成json对象
     *
     * @param contextDto
     * @return
     */
    public JSONObject decryParams(ContextDto contextDto) {
        if (StringUtils.isBlank(contextDto.getParams())) {
            throw new BusinessException("params 为空");
        }
        String cleartext = this.gettService.decryStr(contextDto.getParams(), ContextUtils.getContext().getKey());
        ContextUtils.getContext().setParamInClearText(cleartext);
        JSONObject object = JSONObject.parseObject(cleartext);
        return object;
    }
}
