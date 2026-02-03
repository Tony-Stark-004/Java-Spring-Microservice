package com.loans.loan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.loans.loan.entity.Loans;
import java.util.Optional;

@Repository
public interface LoansRepository extends JpaRepository<Loans, Integer> {

    Optional<Loans> findByMobileNumber(String mobileNumber);
    Optional<Loans> findByLoanNumber(String loanNumber);

}

