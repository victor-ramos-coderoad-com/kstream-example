package com.mojix.examples.commons.wrappers;

/**
 * Created by achambi on 11/5/16.
 *
 */
public class ZoneWrapper extends ThingPropertyValueWrapper{
    public ZonePropertyWrapper facilityMap;
    public ZonePropertyWrapper zoneType;
    public ZonePropertyWrapper zoneGroup;

    public ZonePropertyWrapper getFacilityMap() {
        return facilityMap;
    }

    public void setFacilityMap(ZonePropertyWrapper facilityMap) {
        this.facilityMap = facilityMap;
    }

    public ZonePropertyWrapper getZoneType() {
        return zoneType;
    }

    public void setZoneType(ZonePropertyWrapper zoneType) {
        this.zoneType = zoneType;
    }

    public ZonePropertyWrapper getZoneGroup() {
        return zoneGroup;
    }

    public void setZoneGroup(ZonePropertyWrapper zoneGroup) {
        this.zoneGroup = zoneGroup;
    }
}
