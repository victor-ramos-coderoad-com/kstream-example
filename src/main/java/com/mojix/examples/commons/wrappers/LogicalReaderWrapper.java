package com.mojix.examples.commons.wrappers;

import java.io.Serializable;

/**
 * Created by achambi on 11/6/16.
 */
public class LogicalReaderWrapper extends ThingPropertyValueWrapper implements Serializable {
    private static final long serialVersionUID = 1L;
    public Long zoneInId;
    public Long zoneOutId;
    public String x;
    public String y;
    public String z;
}
