package com.liuyuan.chinaoracle.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuyuan.chinaoracle.model.entity.Role;
import org.apache.ibatis.annotations.Param;

/**
 * @author liuyuan-1024
 * @description 针对表【role(角色表)】的数据库操作Mapper
 * @createDate 2023-06-02 21:08:54
 * @Entity com.liuyuan.chinaoracle.model.entity.RoleEnum
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 依据name属性查询一个角色.
     *
     * @param name 名称
     * @return Role对象
     */
    Role selectOneByName(@Param("name") String name);

}




