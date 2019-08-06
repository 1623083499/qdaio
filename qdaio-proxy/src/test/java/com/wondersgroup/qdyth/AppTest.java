package com.wondersgroup.qdyth;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.wondersgroup.common.utils.HttpClientUtils;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest  {

	@Test
	public void testURLDecoder() throws IOException {
		String url="http://localhost:8080/qdythWS/wi/app/queryPsnInfo2";
		HttpClientUtils httpClientUtil = new HttpClientUtils();
        Map<String,String> createMap = new HashMap<String,String>();  
        createMap.put("idCard","372922198607031436");  
        createMap.put("name","薛凤彪");  
        String httpOrgCreateTestRtn = HttpClientUtils.doPost(url,createMap);
        System.out.println(httpOrgCreateTestRtn);
	}
	
}
