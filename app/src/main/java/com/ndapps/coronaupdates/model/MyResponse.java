package com.ndapps.coronaupdates.model;


public class MyResponse {
    public String country;
    public Cases cases;
    public Deaths deaths;
    public Tests tests;
    public String day;
    public String time;

    public MyResponse() {
    }

    public MyResponse(String country, Cases cases, Deaths deaths, Tests tests, String day, String time) {
        this.country = country;
        this.cases = cases;
        this.deaths = deaths;
        this.tests = tests;
        this.day = day;
        this.time = time;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Cases getCases() {
        return cases;
    }

    public void setCases(Cases cases) {
        this.cases = cases;
    }

    public Deaths getDeaths() {
        return deaths;
    }

    public void setDeaths(Deaths deaths) {
        this.deaths = deaths;
    }

    public Tests getTests() {
        return tests;
    }

    public void setTests(Tests tests) {
        this.tests = tests;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "MyResponse{" +
                "country='" + country + '\'' +
                ", cases=" + cases +
                ", deaths=" + deaths +
                ", tests=" + tests +
                ", day=" + day +
                ", time=" + time +
                '}';
    }
}


