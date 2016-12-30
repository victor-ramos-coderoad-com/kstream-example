package com.mojix.examples.commons.wrappers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mojix.examples.commons.serializers.DateAsStringEncoding;
import com.mojix.examples.commons.serializers.ThingPropertyDeserializer;
import org.apache.avro.reflect.AvroEncode;
import org.apache.avro.reflect.DateAsLongEncoding;

import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = ThingPropertyDeserializer.class)
public class ThingPropertyWrapper implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Correlative number to thing type id.
     */
    public Long id;

    /**
     * Time of thing type field
     */
    @AvroEncode(using = DateAsLongEncoding.class)
//    @AvroEncode(using = DateAsStringEncoding.class)
    public Date time;

    /**
     * flag to verify the field was blinked.
     */
    public boolean blinked;

    /**
     * flag to verify field was modified.
     */
    public boolean modified;

    /**
     * flag to verify field is timeSeries.
     */
    public boolean timeSeries;

    /**
     * Template with contains a generic object.
     */
//    @AvroEncode(using = ThingPropertyEncoding.class)
    public Object value;

    /**
     * Database id of thing type.
     */
    public Long dataTypeId;

    public ThingPropertyWrapper(Long id, Date time, boolean blinked, boolean modified, boolean timeSeries, Object value, Long dataTypeId) {
        this.id = id;
        this.time = time;
        this.blinked = blinked;
        this.modified = modified;
        this.timeSeries = timeSeries;
        this.value = value;
        this.dataTypeId = dataTypeId;
    }

    public ThingPropertyWrapper() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setBlinked(boolean blinked) {
        this.blinked = blinked;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }

    public void setTimeSeries(boolean timeSeries) {
        this.timeSeries = timeSeries;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setDataTypeId(Long dataTypeId) {
        this.dataTypeId = dataTypeId;
    }

    public Long getId() {
        return id;
    }

    public Date getTime() {
        return time;
    }

    public boolean isBlinked() {
        return blinked;
    }

    public boolean isModified() {
        return modified;
    }

    public boolean isTimeSeries() {
        return timeSeries;
    }

    public Object getValue() {
        return value;
    }

    public Long getDataTypeId() {
        return dataTypeId;
    }
}
