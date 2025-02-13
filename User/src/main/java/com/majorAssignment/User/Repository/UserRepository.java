package com.majorAssignment.User.Repository;

import com.majorAssignment.User.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
