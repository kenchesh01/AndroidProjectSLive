package com.transposesolutions.loantrackerproject.db;

import android.widget.TableLayout;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "amortization")
public class AmortizationEntity {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "loanId")
    public  int loanId;

    @ColumnInfo(name = "termsId")
    public  int termsId;

    @ColumnInfo(name = "installmentDates")
    public  String installmentDates;

    @ColumnInfo(name = "Interest")
    public double Interest;

    @ColumnInfo(name = "Principal")
    public double Principal;

    @ColumnInfo(name = "balance")
    public double balance;

    @ColumnInfo(name = "payment")
    public double payment;

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }



    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public int getTermsId() {
        return termsId;
    }

    public void setTermsId(int termsId) {
        this.termsId = termsId;
    }

    public String getInstallmentDates() {
        return installmentDates;
    }

    public void setInstallmentDates(String installmentDates) {
        this.installmentDates = installmentDates;
    }

    public double getInterest() {
        return Interest;
    }

    public void setInterest(double interest) {
        Interest = interest;
    }

    public double getPrincipal() {
        return Principal;
    }

    public void setPrincipal(double principal) {
        Principal = principal;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }


}
