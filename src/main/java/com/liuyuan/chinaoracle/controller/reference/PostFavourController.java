package com.liuyuan.chinaoracle.controller.reference;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liuyuan.chinaoracle.common.response.BaseResponse;
import com.liuyuan.chinaoracle.common.response.ErrorCode;
import com.liuyuan.chinaoracle.common.response.ResultUtils;
import com.liuyuan.chinaoracle.exception.BusinessException;
import com.liuyuan.chinaoracle.exception.ThrowUtils;
import com.liuyuan.chinaoracle.model.dto.reference.post.PostQueryRequest;
import com.liuyuan.chinaoracle.model.dto.reference.postfavour.PostFavourAddRequest;
import com.liuyuan.chinaoracle.model.dto.reference.postfavour.PostFavourQueryRequest;
import com.liuyuan.chinaoracle.model.entity.User;
import com.liuyuan.chinaoracle.model.entity.reference.Post;
import com.liuyuan.chinaoracle.model.vo.PostVO;
import com.liuyuan.chinaoracle.service.UserService;
import com.liuyuan.chinaoracle.service.reference.PostFavourService;
import com.liuyuan.chinaoracle.service.reference.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import javax.annotation.Resource;

/**
 * 帖子收藏接口
 */
@RestController
@RequestMapping("/post_favour")
@Slf4j
public class PostFavourController {

    @Resource
    private PostFavourService postFavourService;

    @Resource
    private PostService postService;

    @Resource
    private UserService userService;

    /**
     * 收藏 / 取消收藏
     *
     * @param postFavourAddRequest
     * @param exchange
     * @return resultNum 收藏变化数
     */
    @PostMapping("/")
    public BaseResponse<Integer> doPostFavour(@RequestBody PostFavourAddRequest postFavourAddRequest,
                                              ServerWebExchange exchange) {
        if (postFavourAddRequest == null || postFavourAddRequest.getPostId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 登录才能操作
        final User loginUser = userService.getLoginUser(exchange);
        long postId = postFavourAddRequest.getPostId();
        int result = postFavourService.doPostFavour(postId, loginUser);
        return ResultUtils.success(result);
    }

    /**
     * 获取我收藏的帖子列表
     *
     * @param postQueryRequest
     * @param exchange
     */
    @PostMapping("/my/list/page")
    public BaseResponse<Page<PostVO>> listMyFavourPostByPage(@RequestBody PostQueryRequest postQueryRequest,
                                                             ServerWebExchange exchange) {
        if (postQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(exchange);
        long current = postQueryRequest.getCurrent();
        long size = postQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Post> postPage = postFavourService.listFavourPostByPage(new Page<>(current, size),
            postService.getQueryWrapper(postQueryRequest), loginUser.getId());
        return ResultUtils.success(postService.getPostVOPage(postPage, exchange));
    }

    /**
     * 获取用户收藏的帖子列表
     *
     * @param postFavourQueryRequest
     * @param exchange
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<PostVO>> listFavourPostByPage(@RequestBody PostFavourQueryRequest postFavourQueryRequest,
                                                           ServerWebExchange exchange) {
        if (postFavourQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long current = postFavourQueryRequest.getCurrent();
        long size = postFavourQueryRequest.getPageSize();
        Long userId = postFavourQueryRequest.getUserId();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20 || userId == null, ErrorCode.PARAMS_ERROR);
        Page<Post> postPage = postFavourService.listFavourPostByPage(new Page<>(current, size),
            postService.getQueryWrapper(postFavourQueryRequest.getPostQueryRequest()), userId);
        return ResultUtils.success(postService.getPostVOPage(postPage, exchange));
    }
}
