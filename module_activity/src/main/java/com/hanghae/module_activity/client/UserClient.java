package com.hanghae.module_activity.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "userClient", url = "${feign.userClient.url}")
public interface UserClient {
    @RequestMapping(method = RequestMethod.GET, value = "/api/internal/users", consumes = "application/json")
    boolean checkUserExists(@RequestParam(name = "userId") Long principalId);
}
