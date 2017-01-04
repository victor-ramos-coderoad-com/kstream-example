package com.mojix.examples.avro;

/**
 * Created by dbascope on 1/3/17
 */
public class EmployeeContact {
    public String email;
    public Long phone;
    public Long active;

    public Long isActive() {
        return active;
    }

    public void setActive(Long active) {
        this.active = active;
    }

    public EmployeeContact(String email, Long phone, Long active) {
        this.email = email;
        this.phone = phone;
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    @Override
    public String toString(){
        return "{\"email\":" + email + ", \"phone\":"+phone+", \"active\":" + active+ "}";
    }
}
