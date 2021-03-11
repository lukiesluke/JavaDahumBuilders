package com.tprealty.corporation.model;

import java.util.List;

public class Summary {
    public String datePaid;
    public Double totalCash;
    public Double totalCheck;
    public Double totalBankTransfer;
    public Double expenses;
    public Double totalCashOnHand;
    public List<Detail> details;

    public String getDatePaid() {
        return datePaid;
    }

    public Double getTotalCash() {
        return totalCash;
    }

    public Double getTotalCheck() {
        return totalCheck;
    }

    public Double getTotalBankTransfer() {
        return totalBankTransfer;
    }

    public Double getExpenses() {
        return expenses;
    }

    public Double getTotalCashOnHand() {
        return totalCashOnHand;
    }

    public List<Detail> getDetails() {
        return details;
    }
}
