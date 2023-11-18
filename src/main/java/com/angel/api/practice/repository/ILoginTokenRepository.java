package com.angel.api.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.angel.api.practice.model.LoginToken;

@Repository
public interface ILoginTokenRepository extends JpaRepository<LoginToken, Integer> {
    // You can define custom query methods here if needed
	
	@Query(value = "SELECT jwt_token FROM `angel_api`.`login_token` ORDER BY id DESC LIMIT 0,1",nativeQuery = true)
	String getLiveToken();

}
