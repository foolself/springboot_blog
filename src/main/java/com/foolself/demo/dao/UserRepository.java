package com.foolself.demo.dao;

import com.foolself.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    List<User> findAll();
    User findByUsername(String username);
    User findByUid(Integer uid);
    boolean existsByUsername(String username);
    User save(User user);

    @Transactional
    @Query(value = "update User set email=?1, home_url=?2 where username=?3", nativeQuery = true)
    @Modifying
    void updateInfo(String email, String homeUrl, String username);

    @Transactional
    @Query(value = "update User set password=?1 where username=?2", nativeQuery = true)
    @Modifying
    void updatePassword(String password, String username);

    @Transactional
    @Query(value = "update User set avatar=?1 where username=?2", nativeQuery = true)
    @Modifying
    void updateAvatar(String avatar, String username);

    @Transactional
    void deleteByUid(Integer uid);
}
