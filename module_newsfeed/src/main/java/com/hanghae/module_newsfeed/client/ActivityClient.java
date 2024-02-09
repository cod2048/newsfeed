package com.hanghae.module_newsfeed.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "activityClient", url = "${feign.activityClient.url}")
public interface ActivityClient {
    @RequestMapping(method = RequestMethod.GET, value = "/api/internal/follows", consumes = "application/json")
    List<Long> findFollowingIds(@RequestParam(name = "userId") Long principalId);
}
