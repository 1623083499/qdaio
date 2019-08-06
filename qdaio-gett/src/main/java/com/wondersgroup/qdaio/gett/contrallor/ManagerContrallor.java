package com.wondersgroup.qdaio.gett.contrallor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wondersgroup.qdaio.gett.bo.GettUser;
import com.wondersgroup.qdaio.gett.context.BusinessException;
import com.wondersgroup.qdaio.gett.context.ContextHolder;
import com.wondersgroup.qdaio.gett.context.UsersCache;
import com.wondersgroup.qdaio.gett.dto.ContextDto;
import com.wondersgroup.qdaio.gett.dto.GettUserDto;
import com.wondersgroup.qdaio.gett.dto.ParamOutDto;
import com.wondersgroup.qdaio.gett.service.GettService;
import com.wondersgroup.qdaio.gett.validate.ParamsValidate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 政企直通后台管理主入口
 */
@RestController
@RequestMapping("/manager")
public class ManagerContrallor {

    @Autowired
    private GettService gettService;

    @Autowired
    private ParamsValidate paramsValidate;


    /**
     * 用户注册，内部调用，传入用户账号loginname和用户名name,使用内部密钥解密,扩展字段ext1，ext2，ext3
     *
     * @param contextDto
     * @return
     */
    @RequestMapping(value = "/registerUser", produces = "text/html;charset=utf-8")
    public String registerUser(ContextDto contextDto) {
        // 解密
        String cleartext = this.gettService.decryStr(contextDto.getParams(), ContextHolder.getProperty("key.inner"));
        // 解析对象
        GettUserDto gettUserDto = JSONObject.parseObject(cleartext, GettUserDto.class);
        // 添加用户
        GettUser gettUser = this.gettService.addUser(gettUserDto);
        // 加入缓存
        BeanUtils.copyProperties(gettUser, gettUserDto);
        UsersCache.put(gettUserDto);

        return JSON.toJSONString(new ParamOutDto().setMap(gettUser));
    }

    /**
     * 用户注册，外部调用，传入用户账号loginname和用户名name
     *
     * @param gettUserDto
     * @return
     */
    @RequestMapping(value = "/register", produces = "text/html;charset=utf-8")
    public String register(GettUserDto gettUserDto) {
        // 添加用户
        GettUser gettUser = this.gettService.addUser(gettUserDto);
        // 加入缓存
        BeanUtils.copyProperties(gettUser, gettUserDto);
        UsersCache.put(gettUserDto);

        return JSON.toJSONString(new ParamOutDto().setMap(gettUser));
    }

    /**
     * 刷新access_token,传入appid
     *
     * @param contextDto
     * @return
     */
    @RequestMapping(value = "/refreshAccessToken", produces = "text/html;charset=utf-8")
    public String refreshAccessToken(ContextDto contextDto) {
        // 解密
        JSONObject object = decryParams(contextDto);
        GettUserDto gettUserDto = this.gettService.refreshAccessToken(object.getString("appid"));
        // 加入缓存
        UsersCache.put(gettUserDto);

        return JSON.toJSONString(new ParamOutDto().setMap(gettUserDto));
    }

    /**
     * 封锁用户,传入appid
     *
     * @param contextDto
     * @return
     */
    @RequestMapping(value = "/lockUser", produces = "text/html;charset=utf-8")
    public String lockUser(ContextDto contextDto) {
        // 解密
        JSONObject object = decryParams(contextDto);
        // 封锁
        String appid = object.getString("appid");
        GettUserDto gettUserDto = this.gettService.lockUser(appid);
        // 加入缓存
        UsersCache.put(gettUserDto);

        return JSON.toJSONString(new ParamOutDto().setMap(gettUserDto));
    }

    /**
     * 解锁用户,传入appid
     *
     * @param contextDto
     * @return
     */
    @RequestMapping(value = "/unLockUser", produces = "text/html;charset=utf-8")
    public String unlockUser(ContextDto contextDto) {
        // 解密
        JSONObject object = decryParams(contextDto);
        // 解锁
        String appid = object.getString("appid");
        GettUserDto gettUserDto = this.gettService.unlockUser(appid);
        // 加入缓存
        UsersCache.put(gettUserDto);

        return JSON.toJSONString(new ParamOutDto().setMap(gettUserDto));
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
        String cleartext = this.gettService.decryStr(contextDto.getParams(), ContextHolder.getProperty("key.inner"));
        JSONObject object = JSONObject.parseObject(cleartext);
        return object;
    }

}
