package com.mojix.examples.avro;

import com.mojix.examples.commons.serializers.DateAsStringEncoding;
import com.mojix.examples.commons.serializers.ValueEncoding;
import org.apache.avro.reflect.AvroAlias;
import org.apache.avro.reflect.AvroEncode;

import java.util.Date;

/**
 * Created by dbascope on 12/22/16
 */
class Employee {
    public String name;
    public String ssn;
    public int age;
    //    @AvroEncode(using = DateAsLongEncoding.class)
    @AvroEncode(using = DateAsStringEncoding.class)
    public Date birthday;
    @AvroEncode(using = ValueEncoding.class)
    public EmployeeValue value;

    public EmployeeValue getValue() {
        return value;
    }

    public void setValue(EmployeeValue value) {
        this.value = value;
    }

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

    Date getBirthday() {
        return birthday;
    }

    void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
