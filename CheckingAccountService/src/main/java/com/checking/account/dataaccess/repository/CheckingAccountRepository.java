package com.checking.account.dataaccess.repository;

import com.checking.account.dataaccess.entity.CheckingAccount;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CheckingAccountRepository {

  public static List<CheckingAccount> accounts = new ArrayList<>();

  public CheckingAccount createAccount(String name, double balance) {
    CheckingAccount savingAccount = new CheckingAccount("Checking" + accounts.size() + 1, name,
        balance);
    accounts.add(savingAccount);
    System.out.printf("Account Created" + savingAccount);
    return savingAccount;
  }

  public void deposit(String accountNum, double amount) {
    for (CheckingAccount account : accounts) {
      if (account.getAccNum().equalsIgnoreCase(accountNum)) {
        account.setBalance(account.getBalance() + amount);
        System.out.println("deposit done:" + account);
      }
    }
  }

  public void withdraw(String accountNum, double amount) {
    for (CheckingAccount account : accounts) {
      if (account.getAccNum().equalsIgnoreCase(accountNum)) {
        account.setBalance(account.getBalance() - amount);
        System.out.println("withdraw done:" + account);
      }
    }
  }

  public List<CheckingAccount> getAll() {
    return accounts;
  }

  public CheckingAccount getAccount(String accNum) {
    return accounts.stream().filter(a -> a.getAccNum().equalsIgnoreCase(accNum)).findFirst().orElse(null);
  }
}
