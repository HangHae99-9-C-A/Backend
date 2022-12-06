package com.example.soldapple.global;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
    @EnableSwagger2
    public class SwaggerConfig {

        @Bean
        public Docket api() {
            return new Docket(DocumentationType.OAS_30)
                    .select()  //ApiSelectorBuilder를 생성하여 apis()와 paths()를 사용할 수 있게 해준다.
                    .apis(RequestHandlerSelectors.basePackage("com.example.soldapple"))//api 스펙이 작성되어 있는 패키지를 지정한다.
                                        //즉, 컨트롤러가 존재하는 패키지를 basepackage로 지정하여 해당 패키지에 존재하는 API를 문서화 한다.
                    .paths(PathSelectors.any())//apis()로 선택되어진 API중 특정 path 조건에 맞는 API들을 다시 필터링하여 문서화한다.
                                        //PathSelectors.any()로 설정하면 패키지 안에 모든 API를 한 번에 볼 수 있다.
                    .build()
                    .apiInfo(apiInfo()); //제목, 설명 등 문서에 대한 정보들을 설정하기 위해 호출한다.
        }

        private ApiInfo apiInfo(){ //스웨거 페이지에서 보여질 제목, 설명 등 문서에 대한 정보 입력
            return new ApiInfoBuilder()
                    .title("Find Apple")
                    .description("1조 Swagger")
                    .version("1.0")
                    .build();
        }
    }



