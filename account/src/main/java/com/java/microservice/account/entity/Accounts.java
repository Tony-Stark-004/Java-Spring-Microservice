package com.java.microservice.account.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="accounts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Accounts extends BaseEntity {
    
    @Column
    private Integer customerId;

    @Id
    @Column(name="account_number")
    private Integer accountNumber;

    @Column
    private String accountType;

    @Column
    private String branchAddress;

}
