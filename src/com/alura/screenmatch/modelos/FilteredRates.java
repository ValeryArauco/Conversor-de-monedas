package com.alura.screenmatch.modelos;

public class FilteredRates {
    private double USD;
    private double ARS;
    private double BOB;
    private double BRL;
    private double CLP;
    private double COP;

    // Getters and setters

    public double getUSD() {
        return USD;
    }

    public void setUSD(double USD) {
        this.USD = USD;
    }

    public double getARS() {
        return ARS;
    }

    public void setARS(double ARS) {
        this.ARS = ARS;
    }

    public double getBOB() {
        return BOB;
    }

    public void setBOB(double BOB) {
        this.BOB = BOB;
    }

    public double getBRL() {
        return BRL;
    }

    public void setBRL(double BRL) {
        this.BRL = BRL;
    }

    public double getCLP() {
        return CLP;
    }

    public void setCLP(double CLP) {
        this.CLP = CLP;
    }

    public double getCOP() {
        return COP;
    }

    public void setCOP(double COP) {
        this.COP = COP;
    }

    @Override
    public String toString() {
        return "FilteredRates{" +
                "USD=" + USD +
                ", ARS=" + ARS +
                ", BOB=" + BOB +
                ", BRL=" + BRL +
                ", CLP=" + CLP +
                ", COP=" + COP +
                '}';
    }
}