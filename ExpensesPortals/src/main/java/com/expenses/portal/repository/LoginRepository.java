package com.expenses.portal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.expenses.portal.entity.LoginEntity;

@Repository
public interface LoginRepository extends JpaRepository<LoginEntity, Long> {
	  Optional<LoginEntity> findByUsername(String username);

	    Optional<LoginEntity> findByEmail(String email);

	    Optional<LoginEntity> findByUsernameOrEmail(String username, String email);

	    boolean existsByUsername(String username);

	    boolean existsByEmail(String email);
}
