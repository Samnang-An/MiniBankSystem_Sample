package com.checking.account.dataaccess.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckingAccount {
  private String  accNum;
  private String name;
  private double balance;

  @Override
  public String toString(){
    return "AccountNum:" + accNum + ";name:" + name + ";balance:" + balance;
  }

}
