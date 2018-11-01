package com.foolself.demo.service;

import com.foolself.demo.entity.UserOnline;

import java.util.List;

public interface SessionService {
    List<UserOnline> getUserOnline();
    boolean forceLogoutById(String sessionId);
}
