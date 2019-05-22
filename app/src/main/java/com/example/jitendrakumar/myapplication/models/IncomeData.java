package com.example.jitendrakumar.myapplication.models;

public class IncomeData {

    public IncomeData()
    {

    }

    public String getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(String incomeId) {
        this.incomeId = incomeId;
    }

    String incomeId;
    String incomeType;
    String incomeAmount;

    public String getIncomeDesc() {
        return incomeDesc;
    }

    public void setIncomeDesc(String incomeDesc) {
        this.incomeDesc = incomeDesc;
    }

    String incomeDesc;

    public IncomeData(String incomeId, String incomeType, String incomeAmount, String incomeDate, String incomeTime, String incomeDesc) {

        this.incomeId = incomeId;
        this.incomeType = incomeType;
        this.incomeAmount = incomeAmount;
        this.incomeDate = incomeDate;
        this.incomeTime = incomeTime;
        this.incomeDesc  = incomeDesc;
    }

    public String getIncomeType() {
        return incomeType;
    }

    public void setIncomeType(String inputType) {
        this.incomeType = inputType;
    }

    public String getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(String incomeAmount) {
        this.incomeAmount = incomeAmount;
    }

    public String getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(String incomeDate) {
        this.incomeDate = incomeDate;
    }

    public String getIncomeTime() {
        return incomeTime;
    }

    public void setIncomeTime(String incomeTime) {
        this.incomeTime = incomeTime;
    }

    String incomeDate;
    String incomeTime;
}
