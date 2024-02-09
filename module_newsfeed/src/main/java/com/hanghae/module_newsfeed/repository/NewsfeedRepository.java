package com.hanghae.module_newsfeed.repository;

import com.hanghae.module_newsfeed.entity.Newsfeed;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsfeedRepository extends JpaRepository<Newsfeed, Long> {
    List<Newsfeed> findByUserIdIn(List<Long> userIds, Sort sort);
}
