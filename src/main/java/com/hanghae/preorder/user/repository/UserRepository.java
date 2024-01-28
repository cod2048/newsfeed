package com.hanghae.preorder.user.repository;

import com.hanghae.preorder.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
