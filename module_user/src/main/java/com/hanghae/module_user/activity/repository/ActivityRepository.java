package com.hanghae.module_user.activity.repository;

import com.hanghae.module_user.activity.entity.Activity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByUserIdIn(List<Long> userIds, Sort sort);
}
