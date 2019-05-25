package com.example.jitendrakumar.myapplication.models;

public class ExpenseData {


    String expenseId;
    String expenseType;
    String expenseAmount;

    public ExpenseData()
    {

    }

    public String getExpenseDesc() {
        return expenseDesc;
    }

    public void setExpenseDesc(String expenseDesc) {
        this.expenseDesc = expenseDesc;
    }

    String expenseDesc;

    public ExpenseData(String expensId, String expenseType, String expenseAmount, String expenseDate, String expenseTime, String expenseDesc) {
        this.expenseId = expensId;
        this.expenseType = expenseType;
        this.expenseAmount = expenseAmount;
        this.expenseDate = expenseDate;
        this.expenseTime = expenseTime;
        this.expenseDesc = expenseDesc;
    }

    public String getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(String  expensId) {
        this.expenseId = expensId;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public String getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(String expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getExpenseTime() {
        return expenseTime;
    }

    public void setExpenseTime(String expenseTime) {
        this.expenseTime = expenseTime;
    }

    String expenseDate;
    String expenseTime;
}
