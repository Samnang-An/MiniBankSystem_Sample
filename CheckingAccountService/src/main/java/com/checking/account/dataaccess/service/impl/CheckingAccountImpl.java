package com.checking.account.dataaccess.service.impl;

import com.checking.account.dataaccess.entity.CheckingAccount;
import com.checking.account.dataaccess.repository.CheckingAccountRepository;
import com.checking.account.dataaccess.service.CheckingAccountService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckingAccountImpl implements CheckingAccountService {

  @Autowired
  private CheckingAccountRepository savingAccountRepository;

  @Override
  public CheckingAccount createAccount(String name, double balance) {
    return savingAccountRepository.createAccount(name, balance);
  }

  @Override
  public void deposit(String accountNum, double amount) {
    savingAccountRepository.deposit(accountNum, amount);
  }

  @Override
  public void withdraw(String accountNum, double amount) {
    savingAccountRepository.withdraw(accountNum, amount);
  }

  @Override
  public List<CheckingAccount> getAll() {
    return savingAccountRepository.getAll();
  }

  @Override
  public CheckingAccount getAccount(String accNum) {
    return savingAccountRepository.getAccount(accNum);
  }
}
