package com.liuyuan.chinaoracle.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuyuan.chinaoracle.model.entity.User;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;

/**
 * @author 源
 * @description 针对表【user】的数据库操作Mapper
 * @createDate 2023-05-17 12:46:08
 * @Entity com.liuyuan.chinaoracle.model.entity.User
 */
public interface UserMapper extends BaseMapper<User> {
    Integer selectIsAdminById(@Param("id") Serializable id);
}




