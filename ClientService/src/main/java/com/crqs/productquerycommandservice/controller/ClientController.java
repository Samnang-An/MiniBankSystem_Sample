package com.crqs.productquerycommandservice.controller;

import java.util.ArrayList;
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
public class ClientController {

  @Autowired
  private CheckingAccountFeignClient checkingAccountFeignClient;
  @Autowired
  private SavingAccountFeignClient savingAccountFeignClient;

  @GetMapping("/")
  ResponseEntity<List<Object>> get() {
    List<Object> savingAccounts = savingAccountFeignClient.get().getBody();
    List<Object> checkingAccounts = checkingAccountFeignClient.get().getBody();
    List<Object> result = new ArrayList<>();
    if (!Objects.isNull(savingAccounts)) {
      result.addAll(savingAccounts);
    }
    if (!Objects.isNull(checkingAccounts)) {
      result.addAll(checkingAccounts);
    }
    return ResponseEntity.ok(result);
  }


  @GetMapping("/get-account/{accNum}")
  ResponseEntity<String> getAccount(@PathVariable String accNum) {
    if (!Objects.isNull(checkingAccountFeignClient.getAccount(accNum).getBody())) {
      return checkingAccountFeignClient.getAccount(accNum);
    }
    return savingAccountFeignClient.getAccount(accNum);
  }

  @PostMapping("/create-account/{type}")
  public ResponseEntity<Object> createAccount(@PathVariable String type, @RequestParam String name,
      @RequestParam double balance) {
    switch (type) {
      case "saving":
        return savingAccountFeignClient.createAccount(name, balance);
      case "checking":
        return checkingAccountFeignClient.createAccount(name, balance);
    }
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/deposit/{accNum}/{amount}")
  ResponseEntity<String> deposit(@PathVariable String accNum, @PathVariable double amount) {
    if (!Objects.isNull(checkingAccountFeignClient.getAccount(accNum).getBody())) {
      checkingAccountFeignClient.deposit(accNum, amount);
      return ResponseEntity.ok("Deposit to Checking Account!");
    }
    savingAccountFeignClient.deposit(accNum, amount);
    return ResponseEntity.ok("Deposit to Checking Account!");
  }

  @PutMapping("/withdraw/{accNum}/{amount}")
  ResponseEntity<String> withdraw(@PathVariable String accNum,
      @PathVariable double amount) {
    if (!Objects.isNull(checkingAccountFeignClient.getAccount(accNum).getBody())) {
      return checkingAccountFeignClient.withdraw(accNum, amount);
    }
    savingAccountFeignClient.withdraw(accNum, amount);
    return checkingAccountFeignClient.withdraw(accNum, amount);
  }

  @PutMapping("/transfer/{fromAccNum}/{toAccNum}/{amount}")
  ResponseEntity<String> withdraw(@PathVariable String fromAccNum,
      @PathVariable String toAccNum,
      @PathVariable double amount) {
    if (!Objects.isNull(checkingAccountFeignClient.getAccount(fromAccNum).getBody())) {
      return checkingAccountFeignClient.transfer(fromAccNum, toAccNum, amount);
    }
    return savingAccountFeignClient.transfer(fromAccNum, toAccNum, amount);
  }

  @FeignClient("checking-account-service")
  interface CheckingAccountFeignClient {

    @GetMapping("/")
    ResponseEntity<List<Object>> get();

    @GetMapping("/{accNum}")
    ResponseEntity<String> getAccount(@PathVariable String accNum);

    @PostMapping("/create-account/{accName}/{balance}")
    ResponseEntity<Object> createAccount(@PathVariable String accName,
        @PathVariable double balance);

    @PutMapping("/deposit/{accNum}/{amount}")
    ResponseEntity<String> deposit(@PathVariable String accNum, @PathVariable double amount);

    @PutMapping("/withdraw/{accNum}/{amount}")
    ResponseEntity<String> withdraw(@PathVariable String accNum,
        @PathVariable double amount);

    @PutMapping("/transfer/{fromAccNum}/{toAccNum}/{amount}")
    ResponseEntity<String> transfer(@PathVariable String fromAccNum,
        @PathVariable String toAccNum,
        @PathVariable double amount);
  }

  @FeignClient("saving-account-service")
  interface SavingAccountFeignClient {

    @GetMapping("/")
    ResponseEntity<List<Object>> get();

    @GetMapping("/{accNum}")
    ResponseEntity<String> getAccount(@PathVariable String accNum);

    @PostMapping("/create-account/{accName}/{balance}")
    ResponseEntity<Object> createAccount(@PathVariable String accName,
        @PathVariable double balance);

    @PutMapping("/deposit/{accNum}/{amount}")
    ResponseEntity<String> deposit(@PathVariable String accNum, @PathVariable double amount);

    @PutMapping("/withdraw/{accNum}/{amount}")
    ResponseEntity<String> withdraw(@PathVariable String accNum,
        @PathVariable double amount);

    @PutMapping("/transfer/{fromAccNum}/{toAccNum}/{amount}")
    ResponseEntity<String> transfer(@PathVariable String fromAccNum,
        @PathVariable String toAccNum, @PathVariable double amount);
  }

}
