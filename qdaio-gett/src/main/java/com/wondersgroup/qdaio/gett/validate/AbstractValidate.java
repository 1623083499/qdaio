package com.wondersgroup.qdaio.gett.validate;

import com.wondersgroup.qdaio.gett.context.BusinessException;
import com.wondersgroup.qdaio.gett.context.ContextUtils;
import com.wondersgroup.qdaio.gett.context.ResultMsg;
import com.wondersgroup.qdaio.gett.dto.ContextDto;
import com.wondersgroup.utils.SecureUtils;
import org.apache.commons.lang3.StringUtils;

public abstract class AbstractValidate implements Validate {
    @Override
    public abstract boolean validate();

    /**
     * 获取token入参验证
     */
    public void validateParamsForGetAccessToken() {
        ContextDto contextDto = ContextUtils.getContext();
        if (StringUtils.isBlank(contextDto.getAppid())) {
            throw new BusinessException(ResultMsg.APPID_NULL);
        }
        if (StringUtils.isBlank(contextDto.getLoginname())) {
            throw new BusinessException(ResultMsg.LOGINNAME_NULL);
        }
        if (StringUtils.isBlank(contextDto.getPassword())) {
            throw new BusinessException(ResultMsg.PASSWORD_NULL);
        }
        if (StringUtils.isBlank(contextDto.getTime())) {
            throw new BusinessException(ResultMsg.TIME_NULL);
        }
        if (StringUtils.isBlank(contextDto.getSign())) {
            throw new BusinessException(ResultMsg.SIGN_NULL);
        }
    }

    /**
     * 验签
     *
     * @return
     */
    public void validateSign() {
        validateParamsForGetAccessToken();
        String str = ContextUtils.getAppid() + ContextUtils.getLoginname() + ContextUtils.getPassword();
        String rSign = SecureUtils.signToString(str, ContextUtils.getTime());
        if (!rSign.equals(ContextUtils.getSign())) {
            // 验签未通过
            throw new BusinessException(ResultMsg.SIGN_ERROR);
        }
    }
}
