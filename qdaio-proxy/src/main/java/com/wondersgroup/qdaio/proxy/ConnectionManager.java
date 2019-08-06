package com.wondersgroup.qdaio.proxy;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;

public class ConnectionManager {


   private  ConnectionPropertis connectionPropertis;

    PoolingHttpClientConnectionManager cm;
    CloseableHttpClient httpClient;
    /**
     * 重连接策略
     */
    HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandler() {
        public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
            if (executionCount >= 3) {
                // Do not retry if over max retry count
                return false;
            }
            if (exception instanceof InterruptedIOException) {
                // Timeout
                return false;
            }
            if (exception instanceof UnknownHostException) {
                // Unknown host
                return false;
            }
            if (exception instanceof ConnectTimeoutException) {
                // Connection refused
                return false;
            }
            if (exception instanceof SSLException) {
                // SSL handshake exception
                return false;
            }
            HttpClientContext clientContext = HttpClientContext.adapt(context);
            HttpRequest request = clientContext.getRequest();
            boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
            if (idempotent) {
                // Retry if the request is considered idempotent
                return true;
            }
            return false;
        }
    };

    /**
     * 配置连接参数
     */
    RequestConfig requestConfig ;

    public ConnectionManager(ConnectionPropertis connectionPropertis) {

        setConnectionPropertis(connectionPropertis);

        cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(connectionPropertis.getMax_total());
        cm.setDefaultMaxPerRoute(connectionPropertis.getMax_per_route());

        /**
         * 配置连接参数
         */
        requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(connectionPropertis.getConnection_request_timeout())
                .setConnectTimeout(connectionPropertis.getConnection_timeout())
                .setSocketTimeout(connectionPropertis.getSocket_timeout())
                .build();
        // 定制实现HttpClient，全局只有一个HttpClient
        httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .setDefaultRequestConfig(requestConfig)
                .setRetryHandler(retryHandler)
                .build();
    }

    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    public HttpClientConnectionManager getManager() {
        return cm;
    }
    public ConnectionPropertis getConnectionPropertis() {
        return connectionPropertis;
    }

    public void setConnectionPropertis(ConnectionPropertis connectionPropertis) {
        this.connectionPropertis = connectionPropertis;
    }
}

