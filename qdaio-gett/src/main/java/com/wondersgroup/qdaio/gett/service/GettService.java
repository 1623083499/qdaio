package com.wondersgroup.qdaio.gett.service;

import com.wondersgroup.qdaio.gett.bo.GettUser;
import com.wondersgroup.qdaio.gett.dto.GettUserDto;
import org.springframework.http.HttpRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GettService {
    String decryStr(String cipherText, String key);

    GettUser addUser(GettUserDto gettUserDto);

    GettUserDto lockUser(String appid);

    GettUserDto unlockUser(String appid);

    GettUser updateSelectUser(GettUserDto gettUserDto);

    GettUserDto queryUserByAppid(String appid);

    List<GettUserDto> queryAllUser(GettUserDto gettUserDto);

    GettUserDto refreshAccessToken(String appid);

    int updateAccessToken(String accessToken, String appid);

    String uploadAttachment(MultipartFile file);

    String saveApply();
}
