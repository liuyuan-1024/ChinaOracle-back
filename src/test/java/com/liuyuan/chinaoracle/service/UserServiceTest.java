package com.liuyuan.chinaoracle.service;

import com.liuyuan.chinaoracle.mapper.UserMapper;
import com.liuyuan.chinaoracle.model.entity.User;
import org.junit.jupiter.api.Assertions;
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
        String email = "liuyuan";
        String password = "";
        String checkPassword = "123456";
        try {
            long result = userService.userRegister(email, password, checkPassword);
            Assertions.assertEquals(-1, result);
            email = "yu";
            result = userService.userRegister(email, password, checkPassword);
            Assertions.assertEquals(-1, result);
        } catch (Exception e) {

        }
    }

    @Test
    void isAdminTest() {
        Integer integer = userMapper.selectIsAdminById(1L);
        System.out.println("integer = " + integer);

        User user = new User();
        user.setId(1L);
        boolean superAdmin = userService.isSuperAdmin(user);
        System.out.println("superAdmin = " + superAdmin);
    }
}
