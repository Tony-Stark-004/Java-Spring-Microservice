package com.java.microservice.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.microservice.account.entity.Customers;
import java.util.Optional;


@Repository
public interface CustomersRepository extends JpaRepository<Customers, Integer> {
    Optional<Customers> findByMobileNumber(String mobileNumber);
}
