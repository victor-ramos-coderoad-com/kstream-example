package com.mojix.examples.avro;

/**
 * Created by dbascope on 1/3/17
 */
public class EmployeeValue {
    Object value;
    Long dataTypeId;

    public Object getValue() {
        if (dataTypeId == 1L){
            return (Long) value;
        } else {
            if (dataTypeId == 2L){
                return value.toString();
            } else {
                return value;
            }
        }
    }

    public EmployeeValue() {
        this.value = "value";
        this.dataTypeId = 2L;
    }

    public EmployeeValue(Object value, Long dataTypeId) {
        this.value = value;
        this.dataTypeId = dataTypeId;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Long getDataTypeId() {
        return dataTypeId;
    }

    public void setDataTypeId(Long dataTypeId) {
        this.dataTypeId = dataTypeId;
    }

    @Override
    public String toString(){
        return value.toString();
    }
}
