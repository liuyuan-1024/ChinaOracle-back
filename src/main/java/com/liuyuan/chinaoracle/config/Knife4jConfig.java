package com.liuyuan.chinaoracle.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Knife4j 接口文档配置
 * https://doc.xiaominfo.com/knife4j/documentation/get_start.html
 */
@Configuration
@EnableSwagger2
@Profile({"dev", "test"})
public class Knife4jConfig {

    @Bean
    public Docket defaultApi2() {

        ApiInfo apiInfo = new ApiInfoBuilder()
            .title("接口文档")
            .description("ChinaOracle, 古灵精怪的代码")
            .version("1.0")
            .build();

        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo)
            .select()
            // 指定 Controller 扫描包路径
            .apis(RequestHandlerSelectors.basePackage("com.liuyuan.chinaoracle.controller"))
            .paths(PathSelectors.any())
            .build();
    }

}