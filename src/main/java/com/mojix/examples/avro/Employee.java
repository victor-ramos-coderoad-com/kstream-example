package com.mojix.examples.avro;

/**
 * Created by dbascope on 12/22/16
 */
class Employee {
    private String name;
    private String ssn;
    private int age;

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    String getSsn() {
        return ssn;
    }

    void setSsn(String ssn) {
        this.ssn = ssn;
    }

    int getAge() {
        return age;
    }

    void setAge(int age) {
        this.age = age;
    }
}
