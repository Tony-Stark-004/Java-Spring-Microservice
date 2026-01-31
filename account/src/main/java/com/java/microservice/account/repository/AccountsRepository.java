package com.java.microservice.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.java.microservice.account.entity.Accounts;

import jakarta.transaction.Transactional;

import java.util.Optional;


@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Integer> {

    Optional<Accounts> findByCustomerId(Integer customerId);

    @Transactional
    @Modifying
    void deleteByCustomerId(Integer customerId);

} 
