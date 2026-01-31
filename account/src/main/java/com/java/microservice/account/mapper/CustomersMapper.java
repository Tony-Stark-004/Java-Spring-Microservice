package com.java.microservice.account.mapper;

import com.java.microservice.account.dto.CustomersDto;
import com.java.microservice.account.entity.Customers;

public class CustomersMapper {

    public static CustomersDto mapToCustomersDto(Customers customers, CustomersDto customersDto) {
        customersDto.setEmail(customers.getEmail());
        customersDto.setName(customers.getName());
        customersDto.setMobileNumber(customers.getMobileNumber());
        return customersDto;
    }

    public static Customers mapToCustomers(Customers customers, CustomersDto customersDto) {
        customers.setEmail(customersDto.getEmail());
        customers.setName(customersDto.getName());
        customers.setMobileNumber(customersDto.getMobileNumber());
        return customers;
    }
}
