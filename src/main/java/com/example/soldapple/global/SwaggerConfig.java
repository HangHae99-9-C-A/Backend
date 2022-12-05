package com.example.soldapple.global;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@Configuration
    @EnableSwagger2
    public class SwaggerConfig {

        @Bean
        public Docket api() {
            return new Docket(DocumentationType.OAS_30)
                    .securityContexts(Arrays.asList(securityContext()))
                    .securitySchemes(Arrays.asList(apiKey()))
                    .select()  //ApiSelectorBuilder를 생성하여 apis()와 paths()를 사용할 수 있게 해준다.
                    .apis(RequestHandlerSelectors.basePackage("com.example.soldapple"))//api 스펙이 작성되어 있는 패키지를 지정한다.
                                        //즉, 컨트롤러가 존재하는 패키지를 basepackage로 지정하여 해당 패키지에 존재하는 API를 문서화 한다.
                    .paths(PathSelectors.any())
                    .build()
                    .apiInfo(apiInfo());
        }

        private ApiInfo apiInfo(){ //스웨거 페이지에서 보일 정보 입력
            return new ApiInfoBuilder()
                    .title("Find Apple")
                    .description("1조 Swagger")
                    .version("1.0")
                    .build();
        }

        //밑으로는 스웨거에서 access_token사용하기위한 설정
        private ApiKey apiKey() {
            return new ApiKey("Access_Token", "Access_Token", "header");
        }

        private SecurityContext securityContext() {
            return SecurityContext.builder()
                    .securityReferences(defaultAuth())
                    .build();
        }

        private List<SecurityReference> defaultAuth() {
            AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
            AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
            authorizationScopes[0] = authorizationScope;
            return Arrays.asList(new SecurityReference("Access_Token", authorizationScopes));
        }
    }



