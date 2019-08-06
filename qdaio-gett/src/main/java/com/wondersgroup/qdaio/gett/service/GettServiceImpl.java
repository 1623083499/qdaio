package com.wondersgroup.qdaio.gett.service;

import com.alibaba.fastjson.JSON;
import com.wondersgroup.framwork.dao.CommonJdbcUtils;
import com.wondersgroup.qdaio.gett.bo.GettUser;
import com.wondersgroup.qdaio.gett.context.BusinessException;
import com.wondersgroup.qdaio.gett.context.ContextHolder;
import com.wondersgroup.qdaio.gett.context.ContextUtils;
import com.wondersgroup.qdaio.gett.context.ResultMsg;
import com.wondersgroup.qdaio.gett.dto.ContextDto;
import com.wondersgroup.qdaio.gett.dto.GettUserDto;
import com.wondersgroup.qdaio.gett.dto.PublicGettDto;
import com.wondersgroup.qdaio.gett.utils.JsonUtils;
import com.wondersgroup.qdaio.gett.utils.VerificationUtils;
import com.wondersgroup.utils.HttpClientUtils;
import com.wondersgroup.utils.SecureUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 政企直通主服务
 */
@Service
public class GettServiceImpl implements GettService {

    private static Logger logger = LoggerFactory.getLogger(GettServiceImpl.class);

    /**
     * 解密
     *
     * @param cipherText
     * @param key
     * @return
     */
    @Override
    public String decryStr(String cipherText, String key) {
        String cleartext;
        try {
            cleartext = SecureUtils.decryStr(cipherText, key);
        } catch (Exception e) {
            throw new BusinessException(ResultMsg.DECRY_ERROR);
        }
        return cleartext;
    }

    /**
     * 添加用户
     *
     * @param gettUserDto
     * @return
     */
    @Override
    public GettUser addUser(GettUserDto gettUserDto) {
        GettUser gettUser = new GettUser();
        BeanUtils.copyProperties(gettUserDto, gettUser);

        String appid = SecureUtils.generateRandomKey(10);
        appid = generateAppid(appid);

        gettUser.setCtime(new Date());
        gettUser.setEnabled("1");
        gettUser.setAppid(appid);
        gettUser.setKey(SecureUtils.generateRandomKey(16));
        CommonJdbcUtils.insert(gettUser);
        logger.info("添加用户{}成功！", gettUserDto.getName());
        return gettUser;
    }

    /**
     * 封锁用户
     *
     * @param appid
     * @return
     */
    @Override
    public GettUserDto lockUser(String appid) {
        if (appid == null) {
            return null;
        }
        CommonJdbcUtils.execute("update gett_user set enabled='0' where appid=?", appid);
        return queryUserByAppid(appid);
    }

    /**
     * 解锁用户
     *
     * @param appid
     * @return
     */
    @Override
    public GettUserDto unlockUser(String appid) {
        if (StringUtils.isBlank(appid)) {
            throw new BusinessException(ResultMsg.APPID_NULL);
        }
        CommonJdbcUtils.execute("update gett_user set enabled='1' where appid=?", appid);
        return queryUserByAppid(appid);
    }

    /**
     * 生成用户唯一ID
     *
     * @param appid
     * @return
     */
    public String generateAppid(String appid) {
        GettUser gettUser = queryUserByAppid(appid);
        if (gettUser != null) {
            return generateAppid(appid);
        } else {
            return appid;
        }
    }

    /**
     * 更新用户
     *
     * @param gettUserDto
     * @return
     */
    @Transactional
    @Override
    public GettUser updateSelectUser(GettUserDto gettUserDto) {
        GettUser gettUser = new GettUser();
        BeanUtils.copyProperties(gettUserDto, gettUser);
        CommonJdbcUtils.updateSelect(gettUser);
        return gettUser;
    }

    /**
     * 根据appid查找用户
     *
     * @param appid
     * @return
     */
    @Override
    public GettUserDto queryUserByAppid(String appid) {
        return CommonJdbcUtils.queryFirst("select * from gett_user where appid=?", GettUserDto.class, appid);
    }

    /**
     * 查找所有用户
     *
     * @param gettUserDto
     * @return
     */
    @Override
    public List<GettUserDto> queryAllUser(GettUserDto gettUserDto) {
        StringBuffer sb = new StringBuffer("select * from gett_user where 1=1");
        List<String> args = new ArrayList<String>();
        if (gettUserDto.getEnabled() != null) {
            sb.append(" and enabled=?");
            args.add(gettUserDto.getEnabled());
        }
        if (gettUserDto.getUserid() != null) {
            sb.append(" and userid=?");
            args.add(gettUserDto.getUserid());
        }
        return CommonJdbcUtils.queryList(sb.toString(), GettUserDto.class, args.toArray());
    }

    /**
     * 刷新用户接入口令
     *
     * @param appid
     * @return
     */
    @Transactional
    @Override
    public GettUserDto refreshAccessToken(String appid) {
        String access_token = SecureUtils.generateRandomKey(32);
        updateAccessToken(access_token, appid);
        return queryUserByAppid(appid);
    }

    /**
     * 更新用户令牌
     *
     * @param accessToken
     * @param appid
     */
    @Override
    public int updateAccessToken(String accessToken, String appid) {
        String sql = "UPDATE gett_user SET  access_token=? WHERE appid=?";
        int i = CommonJdbcUtils.execute(sql, accessToken, appid);
        if (i < 1) {
            throw new BusinessException("更新令牌失败！");
        }
        return i;
    }

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @Override
    public String uploadAttachment(MultipartFile file) {
        String result = "";
        //获取参数
        String clearText = ContextUtils.getContext().getParamInClearText();
        if (clearText == null) {
            return null;
        }
        Map map = JSON.parseObject(clearText);
        map.put("authCode", ContextHolder.getProperty("file.upload.authCode"));
        File fileBin = new File(file.getOriginalFilename());

        try {
            HttpClientUtils.inputStreamToFile(file.getInputStream(), fileBin);
            result = HttpClientUtils.doUploadAsc(ContextHolder.getProperty("file.upload.url"), fileBin, "fileData", map);
            logger.info("file upload success:{}", result);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("上传文件失败");
        }
    }

    /**
     * 人员新参保
     *
     * @return
     */
    @Override
    public String saveApply() {
        //获取参数
        ContextDto contextDto = ContextUtils.getContext();
        String clearText = contextDto.getParamInClearText();
        if (clearText == null) {
            return null;
        }
        PublicGettDto gettData = JsonUtils.toBean(clearText, PublicGettDto.class);
        gettData.setAccess_token(contextDto.getAccess_token());
        gettData.setAppid(contextDto.getAppid());
        gettData.setBusiid(contextDto.getBusiid());
        //校验参数
        String result = VerificationUtils.paramsRule(gettData);
        if (StringUtils.isNoneBlank(result)) {
            return result;
        }
        clearText = JsonUtils.toJson(gettData);
//        result = HttpClientUtils.doPostJson(ContextHolder.getProperty("insured.persons.url"), clearText);
        return result;
    }
}
