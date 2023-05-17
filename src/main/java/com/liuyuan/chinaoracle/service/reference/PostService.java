package com.liuyuan.chinaoracle.service.reference;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liuyuan.chinaoracle.model.dto.reference.post.PostQueryRequest;
import com.liuyuan.chinaoracle.model.entity.reference.Post;
import com.liuyuan.chinaoracle.model.vo.PostVO;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author 源
 * @description 针对表【post(帖子)】的数据库操作Service
 * @createDate 2023-05-06 02:59:58
 */
public interface PostService extends IService<Post> {
    /**
     * 校验
     *
     * @param post
     * @param add
     */
    void validPost(Post post, boolean add);

    /**
     * 获取查询条件
     *
     * @param postQueryRequest
     * @return
     */
    QueryWrapper<Post> getQueryWrapper(PostQueryRequest postQueryRequest);

    /**
     * 从 ES 查询
     *
     * @param postQueryRequest
     * @return
     */
    Page<Post> searchFromEs(PostQueryRequest postQueryRequest);

    /**
     * 获取帖子封装
     *
     * @param post
     * @param exchange
     * @return
     */
    PostVO getPostVO(Post post, ServerWebExchange exchange);

    /**
     * 分页获取帖子封装
     *
     * @param postPage
     * @param exchange
     * @return
     */
    Page<PostVO> getPostVOPage(Page<Post> postPage, ServerWebExchange exchange);
}
