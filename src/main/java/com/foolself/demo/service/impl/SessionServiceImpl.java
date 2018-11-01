package com.foolself.demo.service.impl;

import com.foolself.demo.entity.UserOnline;
import com.foolself.demo.service.SessionService;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class SessionServiceImpl implements SessionService {
    @Autowired
    private SessionDAO sessionDAO;

    @Override
    public List<UserOnline> getUserOnline() {
        List<UserOnline> list = new ArrayList<>();
        Collection<Session> sessions= sessionDAO.getActiveSessions();
        for (Session session :sessions) {
            UserOnline userOnline = new UserOnline();
//            UserInfo userInfo = new UserInfo();

            SimplePrincipalCollection principalCollection = new SimplePrincipalCollection();
            if (session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)==null){
                continue;
            } else {
                principalCollection = (SimplePrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
//                userInfo = (UserInfo) principalCollection.getPrimaryPrincipal();
                userOnline.setUsername(principalCollection.getPrimaryPrincipal().toString());
            }

            userOnline.setId(session.getId().toString());
            userOnline.setHost(session.getHost());
            userOnline.setStartTimeStamp(session.getStartTimestamp());
            userOnline.setLastAccessTime(session.getLastAccessTime());
            Long timeout = session.getTimeout();
            if (timeout == 0){
                userOnline.setStatus("outline");
            } else {
                userOnline.setStatus("online");
            }
            userOnline.setTimeout(timeout);
            list.add(userOnline);
        }
        return list;
    }

    @Override
    public boolean forceLogoutById(String sessionId) {
        Session session = sessionDAO.readSession(sessionId);
        session.setTimeout(0);
        System.out.println("---> SessionServiceImpl.forceLogoutById()");
//        sessionDAO.delete(session);
        return true;
    }
}
