package com.ndapps.coronaupdates.model;

public class Cases {
    public String newCases;
    public int active;
    public int critical;
    public int recovered;
    public int total;

    public Cases() {
    }

    public Cases(String newCases, int active, int critical, int recovered, int total) {
        this.newCases = newCases;
        this.active = active;
        this.critical = critical;
        this.recovered = recovered;
        this.total = total;
    }

    public String getNewCases() {
        return newCases;
    }

    public int getActive() {
        return active;
    }

    public int getCritical() {
        return critical;
    }

    public int getRecovered() {
        return recovered;
    }

    public int getTotal() {
        return total;
    }

    public void setNewCases(String newCases) {
        this.newCases = newCases;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public void setCritical(int critical) {
        this.critical = critical;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Cases{" +
                "newCases=" + newCases +
                ", active=" + active +
                ", critical=" + critical +
                ", recovered=" + recovered +
                ", total=" + total +
                '}';
    }
}
