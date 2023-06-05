package com.liuyuan.chinaoracle.model.conversion;

import com.liuyuan.chinaoracle.model.entity.User;
import com.liuyuan.chinaoracle.model.vo.LoginUserVO;
import com.liuyuan.chinaoracle.model.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 用户实体转换器接口.
 */
@Mapper
public interface UserConvert {
    /**
     * 映射器工厂.
     */
    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);


    // region to VO

    /**
     * user to user vo.
     *
     * @param user 用户
     * @return 用户视图
     */
    UserVO toUserVo(User user);

    /**
     * user to loginUser vo.
     *
     * @param user 用户
     * @return 当前登录用户视图
     */
    LoginUserVO toLoginUserVo(User user);

    // endregion

}
