package com.java.microservice.account.service.impl;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Service;
import com.java.microservice.account.constants.AccountConstants;
import com.java.microservice.account.dto.AccountsDto;
import com.java.microservice.account.dto.CustomersDto;
import com.java.microservice.account.entity.Accounts;
import com.java.microservice.account.entity.Customers;
import com.java.microservice.account.exception.CustomerAlreadyExistsException;
import com.java.microservice.account.exception.ResourceNotFoundException;
import com.java.microservice.account.mapper.AccountsMapper;
import com.java.microservice.account.mapper.CustomersMapper;
import com.java.microservice.account.repository.AccountsRepository;
import com.java.microservice.account.repository.CustomersRepository;
import com.java.microservice.account.service.IAccountService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private AccountsRepository accountRepository;
    private CustomersRepository customerRepository;

    @Override
    public void createAccount(CustomersDto customerDto) {
        Customers customer = CustomersMapper.mapToCustomers(new Customers(), customerDto);
        Optional<Customers> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());

        if(optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException(String.format("Customer already registered with the given mobileNumber %s", customerDto.getMobileNumber()));
        }
        
        Customers savCustomers = customerRepository.save(customer);
        accountRepository.save(createNewAccounts(savCustomers));
    }


    /* @param customer - Customer Object */
    /* @return the new account details */
    private Accounts createNewAccounts(Customers customers) {
        Accounts account = new Accounts();

        account.setCustomerId(customers.getCustomerId());
        int randomAccNumber = ThreadLocalRandom.current().nextInt(1_000_000_000, 1_900_000_000);;

        account.setAccountNumber(randomAccNumber);
        account.setAccountType(AccountConstants.SAVINGS);
        account.setBranchAddress(AccountConstants.ADDRESS);

        return account;
    }
    

    /* @param mobileNumber - Input value */
    /* @return Account details based on mobile number */
    @Override
    public CustomersDto fetchAccount(String mobileNumber) {
        Customers customers = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
            () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        Accounts account = accountRepository.findByCustomerId(customers.getCustomerId()).orElseThrow(
            () -> new ResourceNotFoundException("Account", "customerId", customers.getCustomerId().toString())
        );

        CustomersDto customerDto = CustomersMapper.mapToCustomersDto(customers, new CustomersDto());
        customerDto.setAccounts(AccountsMapper.mapToAccountDto(account, new AccountsDto()));
        return customerDto;
    }


    /* @param customerDto - CustomerDto object */
    /* @return boolean indicating if the update of account details is successful or not */
    @Override
    public boolean updateAccount(CustomersDto customersDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customersDto.getAccounts();
        if(accountsDto != null) {
            Accounts accounts = accountRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );

            AccountsMapper.mapToAccounts(accounts, accountsDto);
            accountRepository.save(accounts);

            Integer customerId = accounts.getCustomerId();
            Customers customers = customerRepository.findById(customerId).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "CustomerId", customerId.toString())
            );

            CustomersMapper.mapToCustomers(customers, customersDto);
            customerRepository.save(customers);
            isUpdated = true;
        }

        return isUpdated;
    }

    
    /* @param mobileNumber - Input value */
    /* @return boolean  */
    @Override
    public boolean deleteAccount(String mobileNumber) {
        boolean isDeleted = false;
        Customers customers = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
            () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        accountRepository.deleteByCustomerId(customers.getCustomerId());
        customerRepository.deleteById(customers.getCustomerId());
        isDeleted = true;

        return isDeleted;
    }
}
