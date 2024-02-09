package com.hanghae.module_newsfeed.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "userClient", url = "${feign.userClient.url}")
public interface UserClient {
    @RequestMapping(method =  RequestMethod.GET, value = "/api/internal/users/check", consumes = "application/json")
    boolean checkUserExsits(@RequestParam(name = "userId") Long principalId);

    @RequestMapping(method =  RequestMethod.GET, value = "/api/internal/users/find", consumes = "application/json")
    String findUserName(@RequestParam(name = "userId") Long principalId);
}
