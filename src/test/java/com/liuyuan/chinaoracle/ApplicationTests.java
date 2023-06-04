package com.liuyuan.chinaoracle;

import com.liuyuan.chinaoracle.mapper.UserMapper;
import com.liuyuan.chinaoracle.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * 主类测试
 */
@SpringBootTest
class ApplicationTests {

    @Resource
    private UserMapper userMapper;

    @Test
    void userConvertTest() {
        User user = userMapper.selectById(1);
        System.out.println("user = " + user);
//        Integer integer = userMapper.selectIsAdminById(1L);
//        System.out.println("integer = " + integer);
//
//        User user = new User();
//        user.setId(1L);
//        boolean superAdmin = userService.isSuperAdmin(user);
//        System.out.println("superAdmin = " + superAdmin);
    }

    @Test
    void repositoryConvertTest() {

    }


}
