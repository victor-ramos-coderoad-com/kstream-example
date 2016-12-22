package com.mojix.examples.commons.wrappers;

import java.io.Serializable;

/**
 * Created by achambi on 11/5/16.
 *
 */
public class BaseDataTypeWrapper implements Serializable {
    private static final long serialVersionUID = 1L;
    public String name;
    public String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
