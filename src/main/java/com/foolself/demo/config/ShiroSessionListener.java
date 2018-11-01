package com.foolself.demo.config;

import org.apache.shiro.session.Session;

import java.util.concurrent.atomic.AtomicInteger;

public class ShiroSessionListener implements org.apache.shiro.session.SessionListener {
    private final AtomicInteger sessionCount = new AtomicInteger(0);
    @Override
    public void onStart(Session session) {
        sessionCount.incrementAndGet();
    }

    @Override
    public void onStop(Session session) {
        sessionCount.decrementAndGet();
    }

    @Override
    public void onExpiration(Session session) {
        sessionCount.decrementAndGet();
    }
}
