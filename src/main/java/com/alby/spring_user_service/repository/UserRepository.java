package com.alby.spring_user_service.repository;

import com.alby.spring_user_service.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UsersEntity, Long>{
    
    boolean existsByUsername(String username);

    Optional<UsersEntity> findByUsernameAndPassword(String username, String password);

    Optional<UsersEntity> findByUsername(String username);
}
