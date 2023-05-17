package com.liuyuan.chinaoracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuyuan.chinaoracle.mapper.LanguageMapper;
import com.liuyuan.chinaoracle.model.entity.Language;
import com.liuyuan.chinaoracle.service.LanguageService;
import org.springframework.stereotype.Service;

/**
* @author 源
* @description 针对表【language(编程语言标签表)】的数据库操作Service实现
* @createDate 2023-05-07 17:59:01
*/
@Service
public class LanguageServiceImpl extends ServiceImpl<LanguageMapper, Language>
    implements LanguageService{

}




