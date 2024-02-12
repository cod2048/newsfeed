package com.hanghae.module_newsfeed.resilience;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "ErrorClient", url="http://localhost:8081")
public interface ErrorClient {
    @RequestMapping(method = RequestMethod.GET, value = "/errorful/case1", consumes = "application/json")
    String case1();

    @RequestMapping(method = RequestMethod.GET, value = "/errorful/case2", consumes = "application/json")
    String case2();

    @RequestMapping(method = RequestMethod.GET, value = "/errorful/case3", consumes = "application/json")
    String case3();
}
