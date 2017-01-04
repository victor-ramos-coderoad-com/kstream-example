package com.mojix.examples.commons.wrappers;

import java.io.Serializable;

/**
 * Created by achambi on 11/6/16.
 */
public class LogicalReaderWrapper extends ThingPropertyValueWrapper implements Serializable {
    private static final long serialVersionUID = 1L;
    public Long zoneInId;
    public Long zoneOutId;

    public LogicalReaderWrapper(Long zoneInId, Long zoneOutId, String x, String y, String z, Long id, String name, String code) {
        super(id, name, code);
        this.zoneInId = zoneInId;
        this.zoneOutId = zoneOutId;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String x;
    public String y;
    public String z;

    public Long getZoneInId() {
        return zoneInId;
    }

    public void setZoneInId(Long zoneInId) {
        this.zoneInId = zoneInId;
    }

    public Long getZoneOutId() {
        return zoneOutId;
    }

    public void setZoneOutId(Long zoneOutId) {
        this.zoneOutId = zoneOutId;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getZ() {
        return z;
    }

    public void setZ(String z) {
        this.z = z;
    }
}
