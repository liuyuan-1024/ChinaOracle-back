package com.liuyuan.chinaoracle.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuyuan.chinaoracle.common.response.ErrorCode;
import com.liuyuan.chinaoracle.constant.CommonConstant;
import com.liuyuan.chinaoracle.exception.BusinessException;
import com.liuyuan.chinaoracle.exception.ThrowUtils;
import com.liuyuan.chinaoracle.mapper.RepositoryMapper;
import com.liuyuan.chinaoracle.model.conversion.RepositoryConvert;
import com.liuyuan.chinaoracle.model.dto.repository.RepositoryQueryRequest;
import com.liuyuan.chinaoracle.model.entity.Repository;
import com.liuyuan.chinaoracle.model.vo.RepositoryVO;
import com.liuyuan.chinaoracle.service.RepositoryService;
import com.liuyuan.chinaoracle.utils.SqlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 源
 * @description 针对表【repository(代码仓库表)】的数据库操作Service实现
 * @createDate 2023-05-07 17:57:21
 */
@Service
public class RepositoryServiceImpl extends ServiceImpl<RepositoryMapper, Repository> implements RepositoryService {

    @Override
    public void verifyRepository(Repository repository, boolean add) {
        if (repository == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 待校验字段
        Long id = repository.getId();
        String name = repository.getName();
        String description = repository.getDescription();
        Long ownerId = repository.getOwnerId();

        // 创建时，参数不能为空
        if (add) {
            ThrowUtils.throwIf(StringUtils.isAnyBlank(name, description), ErrorCode.PARAMS_ERROR);
        }

        // 参数校验
        boolean flag = id == null || ownerId == null || id <= 0 || ownerId <= 0;
        ThrowUtils.throwIf(flag, ErrorCode.PARAMS_ERROR);

        // 有参数则校验
        if (StringUtils.isNotBlank(name) && name.length() > 256) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "仓库名称过长");
        }
        if (StringUtils.isNotBlank(description) && description.length() > 2048) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "仓库描述过长");
        }
    }

    @Override
    public RepositoryVO getRepositoryVO(Repository repository) {
        if (repository == null) {
            return null;
        }

        // 参数校验
        this.verifyRepository(repository, false);

        return RepositoryConvert.INSTANCE.toRepositoryVo(repository);
    }

    @Override
    public List<RepositoryVO> getRepositoryVO(List<Repository> repositoryList) {
        if (CollectionUtils.isEmpty(repositoryList)) {
            return new ArrayList<>();
        }
        return repositoryList.stream().map(this::getRepositoryVO).collect(Collectors.toList());
    }

    @Override
    public QueryWrapper<Repository> getQueryWrapper(RepositoryQueryRequest repositoryQueryRequest) {

        if (repositoryQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }

        Long id = repositoryQueryRequest.getId();
        String name = repositoryQueryRequest.getName();
        String description = repositoryQueryRequest.getDescription();
        Long ownerId = repositoryQueryRequest.getOwnerId();
        String sortField = repositoryQueryRequest.getSortField();

        boolean isAsc = repositoryQueryRequest.getSortOrder().equals(CommonConstant.SORT_ORDER_ASC);

        QueryWrapper<Repository> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(id != null, Repository::getId, id);
        queryWrapper.lambda().like(StringUtils.isNotBlank(name), Repository::getName, name);
        queryWrapper.lambda().like(StringUtils.isNotBlank(description), Repository::getDescription,
            description);
        queryWrapper.lambda().eq(ownerId != null, Repository::getOwnerId, ownerId);

        queryWrapper.orderBy(SqlUtils.verifySortField(sortField), isAsc, sortField);

        return queryWrapper;
    }
}




