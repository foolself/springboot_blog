package com.foolself.demo.entity;

import java.io.Serializable;
import java.util.Date;

public class UserOnline implements Serializable {
    private String id;
    private String username;
    private String host;
    private String systemhost;
    private String status;
    private Date startTimeStamp;
    private Date lastAccessTime;
    private Long timeout;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getSystemhost() {
        return systemhost;
    }

    public void setSystemhost(String systemhost) {
        this.systemhost = systemhost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartTimeStamp() {
        return startTimeStamp;
    }

    public void setStartTimeStamp(Date startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }

    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Date lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    @Override
    public String toString() {
        return "UserOnline{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", host='" + host + '\'' +
                ", systemhost='" + systemhost + '\'' +
                ", status='" + status + '\'' +
                ", startTimeStamp=" + startTimeStamp +
                ", lastAccessTime=" + lastAccessTime +
                ", timeout=" + timeout +
                '}';
    }
}
