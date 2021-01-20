package com.dahumbuilders.model;

import java.util.List;

public class Summary {
    public String datePaid;
    public double totalCash;
    public double totalCheck;
    public double totalBankTransfer;
    public double expenses;
    public double totalCashOnHand;
    public List<Details> details;

    public String getDatePaid() {
        return datePaid;
    }

    public void setDatePaid(String datePaid) {
        this.datePaid = datePaid;
    }

    public double getTotalCash() {
        return totalCash;
    }

    public void setTotalCash(double totalCash) {
        this.totalCash = totalCash;
    }

    public double getTotalCheck() {
        return totalCheck;
    }

    public void setTotalCheck(double totalCheck) {
        this.totalCheck = totalCheck;
    }

    public double getTotalBankTransfer() {
        return totalBankTransfer;
    }

    public void setTotalBankTransfer(double totalBankTransfer) {
        this.totalBankTransfer = totalBankTransfer;
    }

    public double getExpenses() {
        return expenses;
    }

    public void setExpenses(double expenses) {
        this.expenses = expenses;
    }

    public double getTotalCashOnHand() {
        return totalCashOnHand;
    }

    public void setTotalCashOnHand(double totalCashOnHand) {
        this.totalCashOnHand = totalCashOnHand;
    }

    public List<Details> getDetails() {
        return details;
    }

    public void setDetails(List<Details> details) {
        this.details = details;
    }
}
