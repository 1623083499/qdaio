package com.wondersgroup.qdaio.gett.context;

import com.wondersgroup.qdaio.gett.bo.GettUser;
import com.wondersgroup.qdaio.gett.dto.GettUserDto;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户缓存器
 */
public class UsersCache {
    private static Map<String, GettUserDto> users = new HashMap<String, GettUserDto>();

    /**
     * 添加用户进缓存
     *
     * @param gettUserDto
     */
    public static void put(GettUserDto gettUserDto) {
        if (gettUserDto == null || gettUserDto.getAppid() == null) {
            return;
        }
        users.put(gettUserDto.getAppid(), gettUserDto);
    }

    /**
     * 删除用户缓存
     *
     * @param appid
     */
    public static void remove(String appid) {
        users.remove(appid);
    }

    /**
     * 删除用户缓存
     *
     * @param appid
     */
    public static GettUserDto get(String appid) {
        return users.get(appid);
    }

    /**
     * 更新用户口令
     *
     * @param appid
     * @param accessToken
     */
    public static void updateAccessToken(String appid, String accessToken) {
        GettUserDto gettUserDto;
        if ((gettUserDto = users.get(appid)) != null) {
            gettUserDto.setAccess_token(accessToken);
        }
    }
}
