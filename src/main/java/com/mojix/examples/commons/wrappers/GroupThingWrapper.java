package com.mojix.examples.commons.wrappers;

import java.io.Serializable;

public class GroupThingWrapper extends ThingPropertyValueWrapper implements Serializable {
    private static final long serialVersionUID = 1L;
    public ThingPropertyValueWrapper groupType;

    public ThingPropertyValueWrapper getGroupType() {
        return groupType;
    }

    public GroupThingWrapper() {
        super(0L, null, null);
    }

    public GroupThingWrapper(ThingPropertyValueWrapper groupType, Long id, String name, String code) {
        super(id, name, code);
        this.groupType = groupType;
    }

    public void setGroupType(ThingPropertyValueWrapper groupType) {
        this.groupType = groupType;
    }
}
