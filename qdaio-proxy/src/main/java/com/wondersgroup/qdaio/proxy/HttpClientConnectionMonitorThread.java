package com.wondersgroup.qdaio.proxy;


import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.log4j.Logger;

import java.util.concurrent.TimeUnit;

/**
 * <p>Description: 使用管理器，管理HTTP连接池 无效链接定期清理功能</p>
 * @author andy 2017年8月28日
 */
public class HttpClientConnectionMonitorThread extends Thread {

    private  static Logger logger= Logger.getLogger(HttpClientConnectionMonitorThread.class);
    private final HttpClientConnectionManager connManager;
    public HttpClientConnectionMonitorThread(HttpClientConnectionManager connManager) {
        super();
        this.setName("http-connection-monitor");
        this.setDaemon(true);
        this.connManager = connManager;
        this.start();
    }

    @Override
    public void run() {
        try {
                synchronized (this) {
                    wait(5000); // 等待5秒
                    // 关闭过期的链接
                    connManager.closeExpiredConnections();
                    // 选择关闭 空闲30秒的链接
                    connManager.closeIdleConnections(30, TimeUnit.SECONDS);
                    logger.info("关闭过期连接、空闲30秒连接");
                }
        } catch (InterruptedException ex) {
        }
    }


}