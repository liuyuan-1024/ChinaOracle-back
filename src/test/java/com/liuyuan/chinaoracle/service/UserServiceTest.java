package com.liuyuan.chinaoracle.service;

import com.liuyuan.chinaoracle.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * 用户服务测试
 */
@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserService userService;

    @Test
    void userRegister() {
        String email = "ChinaOracle@qq.com";
        String password = "ChinaOracle";
        String checkPassword = "ChinaOracle";

        userService.userRegister(email, password, checkPassword);

    }

    @Test
    void isAdminTest() {

    }
}
