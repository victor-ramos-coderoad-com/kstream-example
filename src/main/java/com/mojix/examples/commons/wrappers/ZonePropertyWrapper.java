package com.mojix.examples.commons.wrappers;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by achambi on 11/5/16.
 * <p>
 * Class to parse a property value [zoneType, zoneGroup, facilityMap]
 */
public class ZonePropertyWrapper extends BaseDataTypeWrapper implements Serializable {
    private static final long serialVersionUID = 1L;
    public Date time;
    public Boolean blinked;
    public Boolean modified;
    public Boolean timeSeries;

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getTime() {
        return time;
    }

    public Boolean getBlinked() {
        return blinked;
    }

    public void setBlinked(Boolean blinked) {
        this.blinked = blinked;
    }

    public Boolean getModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }

    public void setTimeSeries(Boolean timeSeries) {
        this.timeSeries = timeSeries;
    }

    public Boolean getTimeSeries() {
        return timeSeries;
    }
}
