package com.java.microservice.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.microservice.account.constants.AccountConstants;
import com.java.microservice.account.dto.AccountsContactInfoDto;
import com.java.microservice.account.dto.CustomersDto;
import com.java.microservice.account.dto.ErrorResponseDto;
import com.java.microservice.account.dto.ResponseDto;
import com.java.microservice.account.service.IAccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@Tag(name = "CRUD REST APIs for Accounts", description = "CRUD REST APIs for Accounts to Create, Update, Fetch And Delete account details")
@RestController
@RequestMapping(path = "/api", produces = { MediaType.APPLICATION_JSON_VALUE })
@Validated
public class AccountController {

    private final IAccountService iAccountService;

    public AccountController(IAccountService iAccountService) {
        this.iAccountService = iAccountService;
    } 

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private AccountsContactInfoDto accountsContactInfoDto;

    /* Create */
    @Operation(summary = "Create Account REST API", description = "REST API to create new Customer & Account")
    @ApiResponse(responseCode = "201", description = "HTTP Status CREATED")
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomersDto customerdto) {
        iAccountService.createAccount(customerdto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountConstants.STATUS_201, AccountConstants.MESSAGE_201));
    }


    /* Fetch */
    @Operation(summary = "Fetch Account Details REST API", description = "REST API to fetch Customer & Account details based on a mobile number")
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    @GetMapping("/fetch")
    public ResponseEntity<CustomersDto> fetchAccountDetails(
            @RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {
        CustomersDto customerDto = iAccountService.fetchAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);

    }

    /* Update */
    @Operation(summary = "Update Account Details REST API", description = "REST API to update Customer & Account details based on a account number")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "417", description = "Expectation Failed"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error")
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomersDto customersDto) {
        boolean isUpdated = iAccountService.updateAccount(customersDto);
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_UPDATE));
        }
    }

    /* Delete */
    @Operation(summary = "Delete Account & Customer Details REST API", description = "REST API to delete Customer & Account details based on a account number")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "417", description = "Expectation Failed"),
            @ApiResponse(
                responseCode = "500", 
                description = "HTTP Status Internal Server Error", 
                content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
                )
            )
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(
            @RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {
        boolean isDeleted = iAccountService.deleteAccount(mobileNumber);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_DELETE));
        }

    }


    /* to get build info */
    @Operation(summary = "Fetch Build information", description = "Get Build information that is deployed into accounts microservice")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "417", description = "Expectation Failed"),
            @ApiResponse(
                responseCode = "500", 
                description = "HTTP Status Internal Server Error", 
                content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
                )
            )
    })
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
    } 


    /* to get java version */
    @Operation(summary = "Get Java version", description = "Get java versions details that is installed into accounts microservice")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "417", description = "Expectation Failed"),
            @ApiResponse(
                responseCode = "500", 
                description = "HTTP Status Internal Server Error", 
                content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
                )
            )
    })
    @GetMapping("/build-version")
    public ResponseEntity<String> getJavaVersion() {
        return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
    } 


  /* to get java version */
  @Operation(summary = "Get Contact Info", description = "Contact Info details that can be reached out in case of any issue")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
          @ApiResponse(responseCode = "417", description = "Expectation Failed"),
          @ApiResponse(
              responseCode = "500", 
              description = "HTTP Status Internal Server Error", 
              content = @Content(
                  schema = @Schema(implementation = ErrorResponseDto.class)
              )
          )
  })
  @GetMapping("/contact-info")
  public ResponseEntity<AccountsContactInfoDto> getContactInfo() {
      return ResponseEntity.status(HttpStatus.OK).body(accountsContactInfoDto);
  } 

}
