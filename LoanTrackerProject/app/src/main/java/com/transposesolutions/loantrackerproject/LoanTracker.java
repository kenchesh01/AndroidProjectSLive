package com.transposesolutions.loantrackerproject;

import android.app.Application;

public class LoanTracker extends Application {
    String day;
    String month;
    String year;


    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }


    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }


    //check row1
    int row1;


    public int getRow1() {
        return row1;
    }

    public void setRow1(int row1) {
        this.row1 = row1;
    }

    public boolean getEditLoan() {
        return editLoan;
    }

    public void setEditLoan(boolean editLoan) {
        this.editLoan = editLoan;
    }

    public boolean editLoan =true;

    public boolean getEditDate() {
        return editDate;
    }

    public void setEditDate(boolean editDate) {
        this.editDate = editDate;
    }

    public boolean editDate=false;

}
