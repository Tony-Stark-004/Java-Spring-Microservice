package com.java.microservice.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
    name = "Accounts",
    description = "Name of the customer"
)
public class AccountsDto  {

    @Schema(
        description = "Account number of customer",
        example = "3456754312"
    )
    @NotEmpty(message = "AccountNumber can not be a null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Account number must be 10 digits")
    private Integer accountNumber;

    @Schema(
        description = "Account type of the customer",
        example = "saving"
    )
    @NotEmpty(message = "AccountType can not be a null or empty")
    private String accountType;

    @Schema(
        description = "Account branch address",
        example = "256 New Delhi"
    )
    @NotEmpty(message = "BranchAddress can not be a null or empty")
    private String branchAddress;
}
