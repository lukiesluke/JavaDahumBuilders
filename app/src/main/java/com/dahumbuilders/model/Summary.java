package com.dahumbuilders.model;

import java.util.List;

public class Summary {
    public String datePaid;
    public String totalCash;
    public String totalCheck;
    public String totalBankTransfer;
    public String expenses;
    public String totalCashOnHand;
    public List<Details> details;

    public void setDatePaid(String datePaid) {
        this.datePaid = datePaid;
    }

    public void setTotalCash(String totalCash) {
        this.totalCash = totalCash;
    }

    public void setTotalCheck(String totalCheck) {
        this.totalCheck = totalCheck;
    }

    public void setTotalBankTransfer(String totalBankTransfer) {
        this.totalBankTransfer = totalBankTransfer;
    }

    public void setExpenses(String expenses) {
        this.expenses = expenses;
    }

    public void setTotalCashOnHand(String totalCashOnHand) {
        this.totalCashOnHand = totalCashOnHand;
    }

    public void setDetails(List<Details> details) {
        this.details = details;
    }

    public String getDatePaid() {
        return datePaid;
    }

    public String getTotalCash() {
        return totalCash;
    }

    public String getTotalCheck() {
        return totalCheck;
    }

    public String getTotalBankTransfer() {
        return totalBankTransfer;
    }

    public String getExpenses() {
        return expenses;
    }

    public String getTotalCashOnHand() {
        return totalCashOnHand;
    }

    public List<Details> getDetails() {
        return details;
    }
}
