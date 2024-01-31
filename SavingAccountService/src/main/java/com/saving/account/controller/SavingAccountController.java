package com.saving.account.controller;

import com.saving.account.dataaccess.entity.SavingAccount;
import com.saving.account.dataaccess.service.SavingAccountService;
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
public class SavingAccountController {

  @Autowired
  CheckingAccountFeignClient checkingAccountQueryFeignClient;
  @Autowired
  private SavingAccountService savingAccountService;

  @GetMapping("/")
  public ResponseEntity<List<Object>> get() {
    List<SavingAccount> all = savingAccountService.getAll();
    return all.isEmpty()? ResponseEntity.ok().body(new ArrayList<>()):
        ResponseEntity.ok().body(Collections.singletonList(all));
  }

  @GetMapping("/{accNum}")
  public ResponseEntity<String> getAccount(@PathVariable String accNum) {
    SavingAccount acc = savingAccountService.getAccount(accNum);
    return Objects.isNull(acc)?
        ResponseEntity.noContent().build():
        ResponseEntity.ok().body(acc.toString());
  }

  @PostMapping("/create-account/{accName}/{balance}")
  public ResponseEntity<SavingAccount> createAccount(@PathVariable String accName, @PathVariable double balance) {
    SavingAccount account = savingAccountService.createAccount(accName, balance);
    return ResponseEntity.ok().body(account);
  }

  @PutMapping("/deposit/{accNum}/{amount}")
  public ResponseEntity<String> deposit(@PathVariable String accNum, @PathVariable double amount) {
    savingAccountService.deposit(accNum, amount);
    return ResponseEntity.ok("deposit Successful!");
  }

  @PutMapping("/withdraw/{accNum}/{amount}")
  public ResponseEntity<String> withdraw(@PathVariable String accNum, @PathVariable double amount) {
    savingAccountService.withdraw(accNum, amount);
    return ResponseEntity.ok("Withdraw Successful!");
  }

  @PutMapping("/transfer/{fromAccNum}/{toAccNum}/{amount}")
  public ResponseEntity<String> transfer(@PathVariable String fromAccNum,
      @PathVariable String toAccNum,
      @PathVariable double amount) {
    checkingAccountQueryFeignClient.deposit(toAccNum, amount);
    savingAccountService.withdraw(fromAccNum, amount);
    return ResponseEntity.ok("Transfer Successful!");
  }

  @FeignClient("checking-account-service")
  interface CheckingAccountFeignClient {

    @PutMapping("/deposit/{accNum}/{amount}")
    ResponseEntity<String> deposit(@PathVariable String accNum, @PathVariable double amount);
  }
}
