package com.ndapps.coronaupdates.model;

public class Tests {
    public String total;

    public Tests(String total) {
        this.total = total;
    }

    public Tests() {
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Tests{" +
                "total=" + total +
                '}';
    }
}
