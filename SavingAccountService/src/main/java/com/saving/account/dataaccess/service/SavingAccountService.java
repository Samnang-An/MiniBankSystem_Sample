package com.saving.account.dataaccess.service;


import com.saving.account.dataaccess.entity.SavingAccount;
import java.util.List;

public interface SavingAccountService {
  SavingAccount createAccount(String name, double balance);
  void deposit(String accountNum, double amount);
  void withdraw(String accountNum, double amount);
  List<SavingAccount> getAll();

  SavingAccount getAccount(String accNum);
}
