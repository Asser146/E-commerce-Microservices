package com.ecommerce.users.users.repositories;

import com.ecommerce.users.users.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}