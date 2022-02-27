package com.transposesolutions.loantrackerproject.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Loan Tracker")
public class LoanTrackerEntity {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "loanName")
    public String loanName;

    @ColumnInfo(name = "loanAmount")
    public int loanAmount;

    @ColumnInfo(name = "loanTerms")
    public int loanTerms;

    @ColumnInfo(name = "loanRate")
    public int loanRate;



    @ColumnInfo(name = "LoanStartDate")
    public  String loanStartDate;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getLoanName() {
        return loanName;
    }

    public void setLoanName(String loanName) {
        this.loanName = loanName;
    }

    public int getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(int loanAmount) {
        this.loanAmount = loanAmount;
    }

    public int getLoanTerms() {
        return loanTerms;
    }

    public void setLoanTerms(int loanTerms) {
        this.loanTerms = loanTerms;
    }

    public int getLoanRate() {
        return loanRate;
    }

    public void setLoanRate(int loanRate) {
        this.loanRate = loanRate;
    }



    public String getLoanStartDate() {
        return loanStartDate;
    }

    public void setLoanStartDate(String loanStartDate) {
        this.loanStartDate = loanStartDate;
    }


}
