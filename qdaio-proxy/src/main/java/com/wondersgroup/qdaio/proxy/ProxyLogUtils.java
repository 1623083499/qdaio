package com.wondersgroup.qdaio.proxy;

import com.wb.jdbcutils.CommonJdbcUtils;
import com.wondersgroup.qdaio.proxy.bo.NetProxyLog;

import java.util.ArrayList;
import java.util.List;

public class ProxyLogUtils {

    private static int maxLogPoolChache=5;
    public static List<NetProxyLog> getNetProxyLogList() {
        return netProxyLogList;
    }
    public static void setNetProxyLogList(List<NetProxyLog> netProxyLogList) {
        ProxyLogUtils.netProxyLogList = netProxyLogList;
    }
    private static List<NetProxyLog> netProxyLogList=new ArrayList<NetProxyLog>();

    public ProxyLogUtils(ConnectionPropertis connectionPropertis) {
        if (connectionPropertis.getLog_pool_cache()!=null)
        maxLogPoolChache=connectionPropertis.getLog_pool_cache();
    }
    public static synchronized void add(NetProxyLog netProxyLog){
        netProxyLogList.add(netProxyLog);
        if (netProxyLogList.size()>=maxLogPoolChache){
            CommonJdbcUtils.save(netProxyLogList);
            netProxyLogList.clear();
        }
    }
}
