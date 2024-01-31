package com.saving.account.dataaccess.repository;

import com.saving.account.dataaccess.entity.SavingAccount;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SavingAccountRepository {

  public static List<SavingAccount> accounts = new ArrayList<>();

  public SavingAccount createAccount(String name, double balance) {
    SavingAccount savingAccount = new SavingAccount("Saving" + accounts.size() + 1, name, balance);
    accounts.add(savingAccount);
    System.out.println("Saving Account created:" + savingAccount);
    return savingAccount;
  }

  public void deposit(String accountNum, double amount) {

    for (SavingAccount account : accounts) {
      if (account.getAccNum().equalsIgnoreCase(accountNum)) {
        account.setBalance(account.getBalance() + amount);
        System.out.println("deposit done:" + account);
      }
    }
  }

  public void withdraw(String accountNum, double amount) {
    for (SavingAccount account : accounts) {
      if (account.getAccNum().equalsIgnoreCase(accountNum)) {
        account.setBalance(account.getBalance() - amount);
        System.out.println("withdraw done:" + account);
      }
    }
  }

  public List<SavingAccount> getAll() {
    return accounts;
  }

  public SavingAccount getAccount(String accNum) {
    return accounts.stream().filter(a -> a.getAccNum().equalsIgnoreCase(accNum)).findFirst().orElse(null);
  }
}
