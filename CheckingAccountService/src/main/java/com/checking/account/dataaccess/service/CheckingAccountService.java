package com.checking.account.dataaccess.service;


import com.checking.account.dataaccess.entity.CheckingAccount;
import java.util.List;

public interface CheckingAccountService {
  CheckingAccount createAccount(String name, double balance);
  void deposit(String accountNum, double amount);
  void withdraw(String accountNum, double amount);

  List<CheckingAccount> getAll();

  CheckingAccount getAccount(String accNum);
}
