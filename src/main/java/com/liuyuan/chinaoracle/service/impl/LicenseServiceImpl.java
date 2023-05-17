package com.liuyuan.chinaoracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuyuan.chinaoracle.mapper.LicenseMapper;
import com.liuyuan.chinaoracle.model.entity.License;
import com.liuyuan.chinaoracle.service.LicenseService;
import org.springframework.stereotype.Service;

/**
* @author 源
* @description 针对表【license(开源许可证类型表)】的数据库操作Service实现
* @createDate 2023-05-07 17:58:54
*/
@Service
public class LicenseServiceImpl extends ServiceImpl<LicenseMapper, License>
    implements LicenseService{

}




