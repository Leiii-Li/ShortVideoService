package com.nelson.mouseshortvideo;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        //  使用Web.xml 运行应用程序，指向 Application 最后启动SpringBoot
        return application.sources(DemoApplication.class);
    }

}
