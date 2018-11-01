package com.foolself.demo.service.impl;

import com.foolself.demo.dao.UserRepository;
import com.foolself.demo.entity.User;
import com.foolself.demo.service.UserService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author http://foolself.github.io
 * @date 2018/10/19 22:13
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user;
    }

    @Override
    public User findByUid(Integer uid) {
        User user = userRepository.findByUid(uid);
        return user;
    }

    @Override
    public String save(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            System.out.println("---> existsByUsername()");
            return "用户名已存在。";
        }
        user.setPassword(getHash(user.getPassword(), user.getUsername()));
        try {
            user.setCreated(new Date());
            userRepository.save(user);
        } catch (Exception e){
            System.out.println("---> Error: userRepository.save(user)\n" + e);
            return "e";
        }
        return null;
    }

    @Override
    public String updateInfo(User user) {
        System.out.println("---> UserServiceImpl.updateInfo()");
        try {
            userRepository.updateInfo(user.getEmail(), user.getHomeUrl(), user.getUsername());
            System.out.println("---> userRepository.updateInfo() done");
        } catch (Exception e){
            return e.toString();
        }
        return null;
    }

    @Override
    public String updatePassword(String pOld, String pNew, String username) {
        System.out.println("---> UserServiceImpl.updatePassword()");
        String old = getHash(pOld, username);
        String db = userRepository.findByUsername(username).getPassword();
        System.out.println("---> old: " + old);
        System.out.println("---> db: " + db);
        if (old.equals(db)){
            System.out.println("---> old password right.");
            userRepository.updatePassword(getHash(pNew, username), username);
        } else {
            return "password error！";
        }
        return null;
    }

    @Override
    public String updateAvatar(String avatar, String username) {
        try {
            System.out.println("---> ");
            System.out.println("avatar: " + avatar);
            System.out.println("username: " + username);
            userRepository.updateAvatar(avatar, username);
            System.out.println("---> UserServiceImpl.updateAvatar(): " + "ok");
        } catch (Exception e){
            return e.getMessage();
        }
        return "";
    }

    @Override
    public void deleteByUid(Integer uid) {
        userRepository.deleteByUid(uid);
    }

    public static String getHash(String credentials, String salt){
        int hashIterations = 2;//加密的次数
        String hashAlgorithmName = "MD5";//加密方式
        Object simpleHash = new SimpleHash(hashAlgorithmName, credentials,
                salt, hashIterations);
        return simpleHash.toString();
    }
}
