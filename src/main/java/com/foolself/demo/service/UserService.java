package com.foolself.demo.service;

import com.foolself.demo.entity.User;

import java.util.List;

/**
 * @author http://foolself.github.io
 * @date 2018/10/19 22:07
 */
public interface UserService {
    List<User> findAll();
    User findByUsername(String username);
    User findByUid(Integer uid);
    String save(User user);
    String updateInfo(User user);
    String updatePassword(String pOld, String pNew, String username);
    String updateAvatar(String avatar, String username);
    void deleteByUid(Integer uid);
}
