package com.wondersgroup.qdaio.gett.validate;

import com.wondersgroup.framwork.dao.CommonJdbcUtils;
import com.wondersgroup.qdaio.gett.bo.GettUser;
import com.wondersgroup.qdaio.gett.context.*;
import com.wondersgroup.qdaio.gett.dto.GettUserDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 参数验证
 */
@Service
public class ParamsValidate extends AbstractValidate {
    @Override
    public boolean validate() {
        return true;
    }

    /**
     * 验证用户名密码
     *
     * @return
     */
    public boolean validatePassword() {
        String sql = ContextHolder.getProperty("sql.user.query");
        String pass = CommonJdbcUtils.queryObject(sql, String.class, ContextUtils.getLoginname());
        if (pass == null || !pass.equals(ContextUtils.getPassword())) {
            throw new BusinessException("用户名或者密码不正确");
        }
        return true;
    }

    /**
     * 验证用户是否有效
     *
     * @return
     */
    public boolean validateUser() {
        //验证用户有效性
        String sql = "SELECT appid FROM gett_user WHERE appid=? and ENABLED='1' AND nvl(expdate,99991231)>=to_char(SYSDATE,'yyyymmdd')";
        List<GettUser> gettUserList = CommonJdbcUtils.queryList(sql, GettUser.class, ContextUtils.getAppid());
        if (gettUserList.size() == 0) {
            throw new BusinessException(ResultMsg.USER_INVALIDATE);
        }
        return true;
    }

    /**
     * 验证令牌有效性
     *
     * @return
     */
    public boolean validateAccessToken() {
        String accessToken = ContextUtils.getAccess_token();
        String appid = ContextUtils.getAppid();
        String busiid = ContextUtils.getBusiid();
        if (StringUtils.isBlank(accessToken)) {
            throw new BusinessException(ResultMsg.TOKEN_NULL);
        }
        if (StringUtils.isBlank(appid)) {
            throw new BusinessException(ResultMsg.APPID_NULL);
        }

        GettUserDto gettUserDto = UsersCache.get(appid);

        if (gettUserDto == null) {
            throw new BusinessException(ResultMsg.USER_INVALIDATE);
        }

        if (!accessToken.equals(gettUserDto.getAccess_token())) {
            throw new BusinessException(ResultMsg.TOKEN_WRONG);
        }

        ContextUtils.getContext().setAppid(appid);
        ContextUtils.getContext().setLoginname(gettUserDto.getUserid());
        ContextUtils.getContext().setAccess_token(accessToken);
        ContextUtils.getContext().setKey(gettUserDto.getKey());
        ContextUtils.getContext().setBusiid(busiid);
        return true;

    }

    /**
     * 验证令牌有效性
     *
     * @return
     */
    public String validateAccessTokenExt() {
        String accessToken = ContextUtils.getAccess_token();
        String appid = ContextUtils.getAppid();
        if (StringUtils.isBlank(accessToken)) {
            return ResultMsg.TOKEN_NULL;
        }
        if (StringUtils.isBlank(appid)) {
            return ResultMsg.APPID_NULL;
        }
        GettUserDto gettUserDto = UsersCache.get(appid);
        if (gettUserDto == null) {
            return ResultMsg.USER_INVALIDATE;
        }
        if (!accessToken.equals(gettUserDto.getAccess_token())) {
            return ResultMsg.TOKEN_WRONG;
        }
        ContextUtils.getContext().setAppid(appid);
        ContextUtils.getContext().setLoginname(gettUserDto.getUserid());
        ContextUtils.getContext().setAccess_token(accessToken);
        ContextUtils.getContext().setKey(gettUserDto.getKey());
        return null;

    }
}
