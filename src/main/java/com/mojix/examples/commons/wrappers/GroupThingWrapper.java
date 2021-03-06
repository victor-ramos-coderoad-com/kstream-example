package com.mojix.examples.commons.wrappers;

import java.io.Serializable;

public class GroupThingWrapper extends ThingPropertyValueWrapper implements Serializable {
    private static final long serialVersionUID = 1L;
    public ThingPropertyValueWrapper groupType;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public ThingPropertyValueWrapper getGroupType() {
        return groupType;
    }

    public void setGroupType(ThingPropertyValueWrapper groupType) {
        this.groupType = groupType;
    }
}
