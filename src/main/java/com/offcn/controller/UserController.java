package com.offcn.controller;

import com.offcn.dao.UserDao;
import com.offcn.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping(path = "/register")
    @ResponseBody
    public String register(@RequestBody User user){
        //密码加密
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        try {
            userDao.save(user);
            return "注册成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "注册失败";
        }
    }
}
