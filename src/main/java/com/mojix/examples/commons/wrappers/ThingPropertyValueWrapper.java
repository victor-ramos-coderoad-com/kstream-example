package com.mojix.examples.commons.wrappers;

import java.io.Serializable;

/**
 * Created by achambi on 11/5/16.
 * Thing Property Value.
 */
public class ThingPropertyValueWrapper extends BaseDataTypeWrapper implements Serializable {
    private static final long serialVersionUID = 1L;
    public Long id;

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
