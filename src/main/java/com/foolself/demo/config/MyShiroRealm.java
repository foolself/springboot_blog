package com.foolself.demo.config;

import com.foolself.demo.dao.UserRepository;
import com.foolself.demo.entity.Permission;
import com.foolself.demo.entity.Role;
import com.foolself.demo.entity.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class MyShiroRealm extends AuthorizingRealm {
    @Autowired
    private UserRepository userRepository;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("---> MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        System.out.println("---> username: " + principalCollection.getPrimaryPrincipal());
        User user = userRepository.findByUsername((String) principalCollection.getPrimaryPrincipal());

        System.out.println("---> user: " + user.getUsername());

        for (Role role : user.getRoleList()) {
            authorizationInfo.addRole(role.getName());
            for (Permission permission : role.getPermissionList()) {
                authorizationInfo.addStringPermission(permission.getName());
            }
        }
        System.out.println("---> autorizationInfo.getRoles()");
        System.out.println(authorizationInfo.getRoles());
        System.out.println("---> authorizationInfo.getStringPermissions()");
        System.out.println(authorizationInfo.getStringPermissions());
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("---> MyShiroRealm.doGetAuthenticationInfo()");
        String username = (String) authenticationToken.getPrincipal();
        User user = userRepository.findByUsername(username);
        System.out.println("---> user: " + user.getUsername());
        if (user == null) {
            System.out.println("---> user == null");
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUsername(),
                user.getPassword(),
                ByteSource.Util.bytes(user.getUsername()),
                getName()
        );
        return authenticationInfo;
    }
}
