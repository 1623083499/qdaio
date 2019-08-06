package com.wondersgroup.qdaio;

import com.alibaba.fastjson.JSONObject;
import com.wondersgroup.utils.HttpClientUtils;
import com.wondersgroup.utils.SecureUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RequestTest {
    // 获取接入令牌
    private static String URL_GET_TOKEN = "http://localhost:8090/qdaio-gett/getAccessToken";
    // 上传文件
    private static String URL_UPLOAD_FILE = "http://localhost:8090/qdaio-gett/uploadFile";
    // 注册用户
    private static String URL_USER_REGISTER = "http://localhost:8090/qdaio-gett/manager/registerUser";
    // 刷新口令
    private static String URL_USER_REFRESHTOKEN = "http://localhost:8090/qdaio-gett/manager/refreshAccessToken";
    // 封索用户
    private static String URL_USER_LOCK = "http://localhost:8090/qdaio-gett/manager/lockUser";
    // 解索用户
    private static String URL_USER_UNLOCK = "http://localhost:8090/qdaio-gett/manager/unLockUser";
    // 人员参保
    private static String URL_SAVE_APPLY = "http://localhost:8084/qdaio-gett/case/saveApply";

    @Test
    public void testGetAccessToken() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("loginname", "3702422013");
        map.put("password", "96e79218965eb72c92a549dd5a330112");
        map.put("appid", "F9CCnc3sUf");
        map.put("time", "20190222110901");
        map.put("sign", "6bd36df2e95a7f18b24c7cb1e65fb603");
        String result = HttpClientUtils.doGet(URL_GET_TOKEN, map);
        System.out.println(result);
    }

    @Test
    public void testAddUser() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userid", "7989541");
        jsonObject.put("name", "万达信息股份有限公司");
        jsonObject.put("ext1", "3702422013");
        String params = jsonObject.toJSONString();
        String chiperText = SecureUtils.encryStr(params, "vDG5p7z8lYSBgch5");

        Map<String, String> map = new HashMap<String, String>();
        map.put("params", chiperText);
        String result = HttpClientUtils.doGet(URL_USER_REGISTER, map);
        System.out.println(result);
    }

    @Test
    public void testRefreshAccessToken() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("appid", "F9CCnc3sUf");
        String params = jsonObject.toJSONString();
        String chiperText = SecureUtils.encryStr(params, "vDG5p7z8lYSBgch5");

        Map<String, String> map = new HashMap<String, String>();
        map.put("params", chiperText);
        String result = HttpClientUtils.doGet(URL_USER_REFRESHTOKEN, map);
        System.out.println(result);
    }

    @Test
    public void testLockUser() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("appid", "F9CCnc3sUf");
        String params = jsonObject.toJSONString();
        String chiperText = SecureUtils.encryStr(params, "vDG5p7z8lYSBgch5");

        Map<String, String> map = new HashMap<String, String>();
        map.put("params", chiperText);
        String result = HttpClientUtils.doGet(URL_USER_LOCK, map);
        System.out.println(result);
    }

    @Test
    public void testunLockUser() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("appid", "F9CCnc3sUf");
        String params = jsonObject.toJSONString();
        String chiperText = SecureUtils.encryStr(params, "vDG5p7z8lYSBgch5");

        Map<String, String> map = new HashMap<String, String>();
        map.put("params", chiperText);
        String result = HttpClientUtils.doGet(URL_USER_UNLOCK, map);
        System.out.println(result);
    }

    @Test
    public void testUploadFile() {
        //定义参数
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("objID", "万达信息股份有限公司");
        jsonObject.put("objNam", "3702422013");
        jsonObject.put("bizProcCod", "A0201");
        jsonObject.put("bizProcNam", "新参保");
        jsonObject.put("fileTypeCod", "F01");
        jsonObject.put("fileTypeNam", "身份证");
        jsonObject.put("fileName", "xue.jpg");
        String params = jsonObject.toJSONString();
        //加密
        String chiperText = SecureUtils.encryStr(params, "6doaYiXQkc9Vrdtv");
        //上传
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String result = null;
        try {
            File file = new File("d://login.txt");
            httpClient = HttpClients.createDefault();
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(200000).setSocketTimeout(200000000).build();
            HttpPost httpPost = new HttpPost(URL_UPLOAD_FILE);
            HttpEntity reqEntity = MultipartEntityBuilder
                    .create()
                    .addPart("fileData", new FileBody(file))
                    .addTextBody("appid", "F9CCnc3sUf")
                    .addTextBody("params", chiperText)
                    .addTextBody("access_token", "VcwYVDaZRnNgq44RmBk6GkoPV475R7X5").build();
            httpPost.setConfig(requestConfig);
            httpPost.setEntity(reqEntity);
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(response.getEntity(), "utf-8");
                System.out.println(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testCanBao() {
        //定义参数
        JSONObject jsonObject = new JSONObject();
        // 交易流水号
        jsonObject.put("lshid", "37020219930307863X");
        // 单位编号
        jsonObject.put("data01", "3702422013");
        // 国别
        jsonObject.put("data02", "156");
        // 证件类型
        jsonObject.put("data03", "01");
        // 证件号码
        jsonObject.put("data04", "37020219930307863X");
        // 姓名
        jsonObject.put("data05", "李立");
        // 性别
        jsonObject.put("data06", "1");
        // 出生日期
        jsonObject.put("data07", "19930307");
        // 民族
        jsonObject.put("data08", "01");
        // 文化程度
        jsonObject.put("data12", "21");
        // 移动电话号码
        jsonObject.put("data17", "15698789878");
        // 户籍所在地的省份代码
        jsonObject.put("data18", "37");
        // 户籍所在地的市级代码
        jsonObject.put("data19", "370202");
        // 户籍所在地的区代码
        jsonObject.put("data20", "370202");
        // 青岛市户籍所属街道
        jsonObject.put("data21", "37020201");
        // 户籍地地址
        jsonObject.put("data22", "市南区");
        // 联系地址
        jsonObject.put("data23", "市南软件园");
        // 户口性质
        jsonObject.put("data24", "11");
        // 本次参保开始年月
        jsonObject.put("data25", "201904");
        // 工资
        jsonObject.put("data26", "9000");
        // 上年工资
        jsonObject.put("data27", "8000");
        // 缴费档次
        jsonObject.put("data28", "1");
        // 参加工作日期
        jsonObject.put("data29", "201612");
        // 灵活性就业人员是否参加医疗保险
        jsonObject.put("data30", "1");
        // 是否参加就业登记
        jsonObject.put("data31", "1");
        // 工种代码
        jsonObject.put("data32", "105010500");
        // 劳动合同类型
        jsonObject.put("data33", "101");
        // 合同签订日
        jsonObject.put("data34", "20190401");
        // 就业人员类别
        jsonObject.put("data35", "12");
        // 合同开始日期
        jsonObject.put("data36", "20190401");
        // 合同终止日期
        jsonObject.put("data37", "20201201");
        String params = jsonObject.toJSONString();
        //加密
        String chiperText = SecureUtils.encryStr(params, "7DS7IlWxfvm69weC");

        Map<String, String> map = new HashMap<String, String>();
        map.put("params", chiperText);
        map.put("appid", "0cmEdAGkGp");
        map.put("busiid", "ZQ3101");
        map.put("access_token", "DUDbsDKkBG");
        String result = HttpClientUtils.doGet(URL_SAVE_APPLY, map);
        System.out.println(result);
    }

    @Test
    public void testTingBao() {
        //定义参数
        JSONObject jsonObject = new JSONObject();
        // 交易流水号
        jsonObject.put("lshid", "89900521");
        // 单位编号
        jsonObject.put("data01", "3702422013");
        // 证件类型
        jsonObject.put("data02", "01");
        // 证件号码
        jsonObject.put("data03", "370202199303077418");
        // 个人编号
        jsonObject.put("data04", "89900521");
        // 姓名
        jsonObject.put("data05", "李立");
        // 本次停保开始年月
        jsonObject.put("data06", "201905");
        // 变更原因
        jsonObject.put("data07", "6599");
        // 是否就业解聘
        jsonObject.put("data08", "0");
        String params = jsonObject.toJSONString();
        //加密
        String chiperText = SecureUtils.encryStr(params, "6doaYiXQkc9Vrdtv");

        Map<String, String> map = new HashMap<String, String>();
        map.put("params", chiperText);
        map.put("appid", "F9CCnc3sUf");
        map.put("busiid", "ZQ3103");
        map.put("access_token", "VcwYVDaZRnNgq44RmBk6GkoPV475R7X5");
        String result = HttpClientUtils.doGet(URL_SAVE_APPLY, map);
        System.out.println(result);
    }

    @Test
    public void testXuBao() {
        //定义参数
        JSONObject jsonObject = new JSONObject();
        // 交易流水号
        jsonObject.put("lshid", "89900521");
        // 单位编号
        jsonObject.put("data01", "3702422013");
        // 国别
        jsonObject.put("data02", "156");
        // 证件类型
        jsonObject.put("data03", "01");
        // 证件号码
        jsonObject.put("data04", "370202199303077418");
        // 个人编号
        jsonObject.put("data05", "89900521");
        // 姓名
        jsonObject.put("data06", "李立");
        // data07
        jsonObject.put("data07", "14");
        // 移动电话号码
        jsonObject.put("data12", "15698789878");
        // 户籍所在地的省份代码
        jsonObject.put("data13", "37");
        // 户籍所在地的市级代码
        jsonObject.put("data14", "370200");
        // 户籍所在地的区代码
        jsonObject.put("data15", "370202");
        // 青岛市户籍所属街道
        jsonObject.put("data16", "37020201");
        // 户籍地地址
        jsonObject.put("data17", "市南区");
        // 户籍地地址
        jsonObject.put("data18", "市南软件园");
        // 户口性质
        jsonObject.put("data19", "21");
        // 本次参保开始年月
        jsonObject.put("data20", "201905");
        // 变更原因
        jsonObject.put("data21", "6501");
        // 工资
        jsonObject.put("data22", "9000");
        // 上年工资
        jsonObject.put("data23", "8000");
        // 缴费档次
        jsonObject.put("data24", "1");
        // 灵活性就业人员是否参加医疗保险
        jsonObject.put("data25", "1");
        // 是否参加就业登记
        jsonObject.put("data26", "0");
        String params = jsonObject.toJSONString();
        //加密
        String chiperText = SecureUtils.encryStr(params, "6doaYiXQkc9Vrdtv");

        Map<String, String> map = new HashMap<String, String>();
        map.put("params", chiperText);
        map.put("appid", "F9CCnc3sUf");
        map.put("busiid", "ZQ3102");
        map.put("access_token", "VcwYVDaZRnNgq44RmBk6GkoPV475R7X5");
        String result = HttpClientUtils.doGet(URL_SAVE_APPLY, map);
        System.out.println(result);
    }

    @Test
    public void testBushou() {
        //定义参数
        JSONObject jsonObject = new JSONObject();
        // 交易流水号
        jsonObject.put("lshid", "89900521");
        // 单位编号
        jsonObject.put("data01", "3702422013");
        // 证件类型
        jsonObject.put("data02", "01");
        // 证件号码
        jsonObject.put("data03", "370202199303077418");
        // 个人编号
        jsonObject.put("data04", "89900521");
        // 姓名
        jsonObject.put("data05", "李立");
        // 补收开始年月
        jsonObject.put("data06", "201805");
        // 补收终止年月
        jsonObject.put("data07", "201905");
        // 上年工资
        jsonObject.put("data08", "7000");
        // 工资
        jsonObject.put("data09", "8000");
        String params = jsonObject.toJSONString();
        //加密
        String chiperText = SecureUtils.encryStr(params, "6doaYiXQkc9Vrdtv");

        Map<String, String> map = new HashMap<String, String>();
        map.put("params", chiperText);
        map.put("appid", "F9CCnc3sUf");
        map.put("busiid", "ZQ3104");
        map.put("access_token", "VcwYVDaZRnNgq44RmBk6GkoPV475R7X5");
        String result = HttpClientUtils.doGet(URL_SAVE_APPLY, map);
        System.out.println(result);
    }

    @Test
    public void testQfcx() {
        //定义参数
        JSONObject jsonObject = new JSONObject();
        // 交易流水号
        jsonObject.put("lshid", "89900521");
        // 单位编号
        jsonObject.put("data01", "3702422013");
        // 证件类型
        jsonObject.put("data02", "01");
        // 证件号码
        jsonObject.put("data03", "370202199303077418");
        String params = jsonObject.toJSONString();
        //加密
        String chiperText = SecureUtils.encryStr(params, "6doaYiXQkc9Vrdtv");

        Map<String, String> map = new HashMap<String, String>();
        map.put("params", chiperText);
        map.put("appid", "F9CCnc3sUf");
        map.put("busiid", "ZQ3105");
        map.put("access_token", "VcwYVDaZRnNgq44RmBk6GkoPV475R7X5");
        String result = HttpClientUtils.doGet(URL_SAVE_APPLY, map);
        System.out.println(result);
    }

    @Test
    public void testDypd() {
        //定义参数
        JSONObject jsonObject = new JSONObject();
        // 交易流水号
        jsonObject.put("lshid", "89900521");
        // 单位编号
        jsonObject.put("data01", "3702422013");
        // 缴费类型（欠费信息查询接口中反馈的“欠费信息列表”中的“缴费类型”）
        jsonObject.put("data02", "");
        // 缴费交易流水号（欠费信息查询接口中反馈的“欠费信息列表”中的“缴费交易流水号”）
        jsonObject.put("data03", "");
        String params = jsonObject.toJSONString();
        //加密
        String chiperText = SecureUtils.encryStr(params, "6doaYiXQkc9Vrdtv");

        Map<String, String> map = new HashMap<String, String>();
        map.put("params", chiperText);
        map.put("appid", "F9CCnc3sUf");
        map.put("busiid", "ZQ3106");
        map.put("access_token", "VcwYVDaZRnNgq44RmBk6GkoPV475R7X5");
        String result = HttpClientUtils.doGet(URL_SAVE_APPLY, map);
        System.out.println(result);
    }

    @Test
    public void testJfmxxz() {
        //定义参数
        JSONObject jsonObject = new JSONObject();
        // 交易流水号
        jsonObject.put("lshid", "89900521");
        // 单位编号
        jsonObject.put("data01", "3702422013");
        // 缴费月份
        jsonObject.put("data02", "");
        String params = jsonObject.toJSONString();
        //加密
        String chiperText = SecureUtils.encryStr(params, "6doaYiXQkc9Vrdtv");

        Map<String, String> map = new HashMap<String, String>();
        map.put("params", chiperText);
        map.put("appid", "F9CCnc3sUf");
        map.put("busiid", "ZQ3107");
        map.put("access_token", "VcwYVDaZRnNgq44RmBk6GkoPV475R7X5");
        String result = HttpClientUtils.doGet(URL_SAVE_APPLY, map);
        System.out.println(result);
    }

    @Test
    public void testCes() {
        String sss = "12346";
        String sss1 = StringUtils.substring(sss,0, 6);
        System.out.println(sss1);

        Long num = 5001L;
        System.out.println(Math.ceil(num/500));
        num = 5000L;
        System.out.println(Math.ceil(num/500));
        num = 4999L;
        System.out.println(Math.ceil(num/500));
        for (int i=0; i<Math.ceil(num/500); i++) {
            System.out.println(i);
        }
    }
}
