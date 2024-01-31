package com.saving.account.dataaccess.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavingAccount {
  private String  accNum;
  private String name;
  private double balance;
}
