package com.hanghae.preorder.activity.repository;

import com.hanghae.preorder.activity.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
