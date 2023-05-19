package com.liuyuan.chinaoracle.model.conversion;

import com.liuyuan.chinaoracle.model.entity.Repository;
import com.liuyuan.chinaoracle.model.vo.RepositoryVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 仓库实体转换器接口
 */
@Mapper
public interface RepositoryConvert {
    RepositoryConvert INSTANCE = Mappers.getMapper(RepositoryConvert.class);


    // region to VO

    RepositoryVO toRepositoryVo(Repository repository);

//    @Mapping(target = "name", source = "repository.name")
//    @Mapping(target = "description", source = "repository.description")
//    @Mapping(target = "createdAt", source = "repository.createdAt")
//    @Mapping(target = "updatedAt", source = "repository.updatedAt")
//    @Mapping(target = "languageName", source = "language.name")
//    @Mapping(target = "licenseName", source = "license.name")
//    @Mapping(target = "licenseUrl", source = "license.url")
//    RepositoryVO toRepositoryVo(Repository repository, Language language, License license);

    // endregion
}
