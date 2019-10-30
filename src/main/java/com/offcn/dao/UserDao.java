package com.offcn.dao;

import com.offcn.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserDao extends JpaRepository<User,Long> {
    public User findByUsername(String name);
}
