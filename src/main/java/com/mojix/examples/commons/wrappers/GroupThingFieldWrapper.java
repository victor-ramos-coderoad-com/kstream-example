package com.mojix.examples.commons.wrappers;

import java.io.Serializable;

/**
 * Created by achambi on 11/5/16.
 * Class to parse a property value [zoneType, zoneGroup, facilityMap]
 */
public class GroupThingFieldWrapper extends ThingPropertyValueWrapper implements Serializable {
    private static final long serialVersionUID = 1L;
    public String description;
    public String hierarchyName;

    public GroupThingFieldWrapper(String description, String hierarchyName, boolean archived, Long treeLevel, Long id, String name, String code) {
        super(id, name, code);
        this.description = description;
        this.hierarchyName = hierarchyName;
        this.archived = archived;
        this.treeLevel = treeLevel;
    }

    public boolean archived;
    public Long treeLevel;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHierarchyName() {
        return hierarchyName;
    }

    public void setHierarchyName(String hierarchyName) {
        this.hierarchyName = hierarchyName;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public Long getTreeLevel() {
        return treeLevel;
    }

    public void setTreeLevel(Long treeLevel) {
        this.treeLevel = treeLevel;
    }
}
