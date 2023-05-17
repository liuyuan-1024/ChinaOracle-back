package com.liuyuan.chinaoracle.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liuyuan.chinaoracle.model.entity.reference.Post;

import java.util.Date;
import java.util.List;

/**
* @author 源
* @description 针对表【post(帖子)】的数据库操作Mapper
* @createDate 2023-05-06 02:59:58
* @Entity com.liuyuan.chinaoracle.model.entity.reference.Post
*/
public interface PostMapper extends BaseMapper<Post> {

    /**
     * 查询帖子列表（包括已被删除的数据）
     */
    List<Post> listPostWithDelete(Date minUpdateTime);
}




