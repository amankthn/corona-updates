package com.ndapps.coronaupdates.model;

public class Deaths {

    public String newDeaths;
    public int total;

    public Deaths() {
    }

    public Deaths(String newDeaths, int total) {
        this.newDeaths = newDeaths;
        this.total = total;
    }

    public String getNewDeaths() {
        return newDeaths;
    }

    public int getTotal() {
        return total;
    }

    public void setNewDeaths(String newDeaths) {
        this.newDeaths = newDeaths;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Deaths{" +
                "newDeaths='" + newDeaths + '\'' +
                ", total=" + total +
                '}';
    }
}
