package com.checking.account.controller;

import com.checking.account.dataaccess.entity.CheckingAccount;
import com.checking.account.dataaccess.service.CheckingAccountService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckingAccountController {

  @Autowired
  SavingAccountFeignClient savingAccountQueryFeignClient;
  @Autowired
  private CheckingAccountService checkingAccountService;

  @GetMapping("/")
  public ResponseEntity<List<Object>> get() {
    List<CheckingAccount> all = checkingAccountService.getAll();
    return all.isEmpty()? ResponseEntity.ok().body(new ArrayList<>()):
        ResponseEntity.ok().body(Collections.singletonList(all));
  }

  @GetMapping("/{accNum}")
  public ResponseEntity<String> getAccount(@PathVariable String accNum) {
    CheckingAccount acc = checkingAccountService.getAccount(accNum);
    return Objects.isNull(acc)?
        ResponseEntity.noContent().build() :
        ResponseEntity.ok().body(acc.toString());
  }

  @PostMapping("/create-account/{accName}/{balance}")
  public ResponseEntity<CheckingAccount> createAccount(@PathVariable String accName, @PathVariable double balance) {
    CheckingAccount account = checkingAccountService.createAccount(accName, balance);
    return ResponseEntity.ok().body(account);
  }

  @PutMapping("/deposit/{accNum}/{amount}")
  public ResponseEntity<String> deposit(@PathVariable String accNum, @PathVariable double amount) {
    checkingAccountService.deposit(accNum, amount);
    return ResponseEntity.ok("deposit Successful!");
  }

  @PutMapping("/withdraw/{accNum}/{amount}")
  public ResponseEntity<String> withrdaw(@PathVariable String accNum, @PathVariable double amount) {
    checkingAccountService.withdraw(accNum, amount);
    return ResponseEntity.ok("Withdraw Successful!");
  }

  @PutMapping("/transfer/{fromAccNum}/{toAccNum}/{amount}")
  public ResponseEntity<String> transfer(@PathVariable String fromAccNum, @PathVariable String toAccNum,
      @PathVariable double amount) {
    savingAccountQueryFeignClient.deposit(toAccNum, amount);
    checkingAccountService.withdraw(fromAccNum, amount);
    return ResponseEntity.ok("Transfer Successful!");
  }

  @FeignClient("saving-account-service")
  interface SavingAccountFeignClient {

    @PutMapping("/deposit/{accNum}/{amount}")
    ResponseEntity<String> deposit(@PathVariable String accNum, @PathVariable double amount);
  }
}
