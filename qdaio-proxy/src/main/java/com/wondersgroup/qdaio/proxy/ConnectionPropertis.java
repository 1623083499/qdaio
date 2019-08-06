package com.wondersgroup.qdaio.proxy;

public class ConnectionPropertis {
    //目录Url
    private String target;
    // 最大连接数
    private  Integer max_total ;
    // 每一个路由的最大连接数
    private  Integer max_per_route;
    //从连接池中获得连接的超时时间
    private Integer connection_request_timeout ;
    //连接超时
    private Integer connection_timeout ;
    //获取数据的超时时间
    private  Integer socket_timeout ;

    private String project_name;

    private String api_token;
    private String api_login_name;
    private String api_login_password;
    private Integer log_pool_cache;
    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
    public Integer getMax_total() {
        return max_total;
    }

    public void setMax_total(Integer max_total) {
        this.max_total = max_total;
    }

    public Integer getMax_per_route() {
        return max_per_route;
    }

    public void setMax_per_route(Integer max_per_route) {
        this.max_per_route = max_per_route;
    }

    public Integer getConnection_request_timeout() {
        return connection_request_timeout;
    }

    public void setConnection_request_timeout(Integer connection_request_timeout) {
        this.connection_request_timeout = connection_request_timeout;
    }

    public Integer getConnection_timeout() {
        return connection_timeout;
    }

    public void setConnection_timeout(Integer connection_timeout) {
        this.connection_timeout = connection_timeout;
    }

    public Integer getSocket_timeout() {
        return socket_timeout;
    }

    public void setSocket_timeout(Integer socket_timeout) {
        this.socket_timeout = socket_timeout;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }

    public String getApi_login_name() {
        return api_login_name;
    }

    public void setApi_login_name(String api_login_name) {
        this.api_login_name = api_login_name;
    }

    public String getApi_login_password() {
        return api_login_password;
    }

    public void setApi_login_password(String api_login_password) {
        this.api_login_password = api_login_password;
    }

    public Integer getLog_pool_cache() {
        return log_pool_cache;
    }

    public void setLog_pool_cache(Integer log_pool_cache) {
        this.log_pool_cache = log_pool_cache;
    }
}
