package com.angel.api.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.angel.api.practice.model.LoginStatus;

@Repository
public interface ILoginStatusRepository extends JpaRepository<LoginStatus,Integer> {
    // You can define custom query methods here if needed
}