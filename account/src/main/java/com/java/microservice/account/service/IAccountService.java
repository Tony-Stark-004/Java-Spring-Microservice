package com.java.microservice.account.service;

import com.java.microservice.account.dto.CustomersDto;


public interface IAccountService {

    /* @param customerDto - CustomerDto object */
    void createAccount(CustomersDto customerDto);

    /* @param mobileNumber - Input value */
    /* @return Account details based on mobile number */
    CustomersDto fetchAccount(String mobileNumber); 

    /* @param customerDto - CustomerDto object */
    /* @return boolean indicating if the update of account details is successful or not */
    boolean updateAccount(CustomersDto customersDto);

    /* @param mobileNumber - Input value */
    /* @return boolean indicating if the delete of account details is successful or not */
    boolean deleteAccount(String mobileNumber);
} 