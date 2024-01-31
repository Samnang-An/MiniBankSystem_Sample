package com.saving.account.dataaccess.service.impl;

import com.saving.account.dataaccess.entity.SavingAccount;
import com.saving.account.dataaccess.repository.SavingAccountRepository;
import com.saving.account.dataaccess.service.SavingAccountService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SavingAccountImpl implements SavingAccountService {

  @Autowired
  private SavingAccountRepository savingAccountRepository;

  @Override
  public SavingAccount createAccount(String name, double balance) {
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
  public List<SavingAccount> getAll() {
    return savingAccountRepository.getAll();
  }

  @Override
  public SavingAccount getAccount(String accNum) {
    return savingAccountRepository.getAccount(accNum);
  }
}
