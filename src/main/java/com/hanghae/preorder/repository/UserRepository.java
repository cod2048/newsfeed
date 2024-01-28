package com.hanghae.preorder.repository;

import com.hanghae.preorder.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
