package com.liuyuan.chinaoracle.controller;

import com.liuyuan.chinaoracle.common.response.BaseResponse;
import com.liuyuan.chinaoracle.common.response.ErrorCode;
import com.liuyuan.chinaoracle.common.response.ResultUtils;
import com.liuyuan.chinaoracle.exception.BusinessException;
import com.liuyuan.chinaoracle.model.dto.star.StarRequest;
import com.liuyuan.chinaoracle.model.entity.User;
import com.liuyuan.chinaoracle.service.StarService;
import com.liuyuan.chinaoracle.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import javax.annotation.Resource;

/**
 * 仓库点赞接口
 */
@RestController
@RequestMapping("/star")
public class StarController {

    @Resource
    private UserService userService;

    @Resource
    private StarService starService;

    /**
     * 点赞 / 取消点赞
     *
     * @param starRequest 仓库 点赞/取消点赞 请求体
     * @param exchange    代表一个http请求和响应的完整过程的对象
     * @return 本次点赞变化数
     */
    @PostMapping("/")
    public BaseResponse<Integer> doStar(@RequestBody StarRequest starRequest,
                                        ServerWebExchange exchange) {

        if (starRequest == null || starRequest.getRepositoryId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 登录才能点赞
        final User loginUser = userService.getLoginUser(exchange);
        Long repositoryId = starRequest.getRepositoryId();
        int result = starService.doStar(repositoryId, loginUser);
        return ResultUtils.success(result);
    }

}
