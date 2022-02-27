package com.transposesolutions.loantrackerproject.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface LoanTrackerDao {


    // add the loan details   in Loan tracker entity (Using Loan tracker module2)
    @Insert
    void addLoan(LoanTrackerEntity...loanTrackerEntities);
    // update  the specific loan in Loan tracker entity (Using Loan tracker module1)
    @Update
    void updateLoan(LoanTrackerEntity loanTrackerEntity);

    // delete the specific loan in Loan tracker entity (Using Loan tracker module1)
    @Delete
    void deleteLoan(LoanTrackerEntity loanTrackerEntity);

    // collecting the Loan names for avoiding the duplicate names (Using Loan tracker module2 )
    @Query("SELECT loanName FROM `loan tracker`")
    public List<String> getLoanNames();
    // collecting the all Loans in Loan Tracker entity (Using Loan tracker module1 )
    @Query("Select * from `loan tracker`")
    List<LoanTrackerEntity> getAllLoans();

    // retrieve the all specific data amortization data and passing the parameter
    @Query("Select * from `loan tracker` where uid = :id ")
    List<LoanTrackerEntity> getSpecificDate(int id);

    // Amortization entity  sql Queries

    // adding the amortization data
    @Insert
    void addAmortizationData(AmortizationEntity amortizationEntity);

    // retrieve the specific balance (balance amount) passing the parameter
    @Query("SELECT balance from amortization WHERE termsId = :Id  AND  loanId=:loanIds ")
    public double getBalance(int Id,int loanIds);

    // retrieve the specific payment (payment amount) passing the parameter
    @Query("SELECT payment from amortization WHERE loanId=:loanIds ")
    public double getPayment(int loanIds);

    // retrieve the all specific data amortization data and passing the parameter
    @Query("Select * from amortization where loanId = :catId ")
    List<AmortizationEntity> getAllAmortizationList(int catId);
    // collecting the Loan names for avoiding the duplicate names (Using Loan tracker module2 )

    @Query("SELECT * FROM amortization")
    public List<AmortizationEntity> getAllAmortizationS();

    @Delete
    void deleteAmortizationData(AmortizationEntity amortizationEntity);

    @Update
    void updateAmortizationEntity(AmortizationEntity amortizationEntity);
}
