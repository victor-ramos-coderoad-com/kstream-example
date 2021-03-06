package com.mojix.examples.commons.wrappers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mojix.examples.commons.serializers.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = ThingDeserializer.class)
@JsonSerialize(using = ThingSerializer.class)
public class ThingWrapper implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Correlation id of thing instance.
     */
    public Long id;

    /**
     * Thing Serial Number.
     */
    public String serialNumber;

    /**
     * Thing Name.
     */
    public String name;

    /**
     * Date time of createdTime.
     */
    public Date createdTime;

    /**
     * Date Time of modifiedTime.
     */
    public Date modifiedTime;

    /**
     * Message time
     */
    public Date time;

    /**
     * Group field
     */
    public GroupThingWrapper group;

    /**
     * Thing type of thing
     */
    public ThingPropertyValueWrapper thingType;

    /**
     * list of properties first element of the list is a current thing
     * list of properties second element of the list is a previous thing
     */
    public PropertyWrapper properties;

    public MetaWrapper meta;

    /**
     * Get meta information.
     *
     * @return a {@link MetaWrapper} object.
     */
    public MetaWrapper getMeta() {
        return meta;
    }

    /**
     * Set meta information.
     *
     * @param meta MetaWrapper object to set
     */
    public void setMeta(MetaWrapper meta) {
        this.meta = meta;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public GroupThingWrapper getGroup() {
        return group;
    }

    public void setGroup(GroupThingWrapper group) {
        this.group = group;
    }

    public ThingPropertyValueWrapper getThingType() {
        return thingType;
    }

    public void setThingType(ThingPropertyValueWrapper thingType) {
        this.thingType = thingType;
    }

    public PropertyWrapper getProperties() {
        return properties;
    }

    public void setProperties(PropertyWrapper properties) {
        this.properties = properties;
    }

    ThingPropertyWrapper getUdf(String name) {
        return properties.getCurrent().get(name);
    }

    public ThingWrapper() {

    }

    public ThingWrapper(MetaWrapper meta,
                        long id, String serialNumber,
                        String name,
                        Date createdTime,
                        Date modifiedTime,
                        Date time,
                        GroupThingWrapper group,
                        ThingPropertyValueWrapper thingType,
                        PropertyWrapper properties) {
        this.meta = meta;
        this.id = id;
        this.serialNumber = serialNumber;
        this.name = name;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
        this.time = time;
        this.group = group;
        this.thingType = thingType;
        this.properties = properties;
    }

    /**
     * Static method to create a instance of message wrapper.
     *
     * @param json A json string with contains the message.
     * @return instance of message wrapper.
     * @throws IOException input or output exception.
     */
    public static ThingWrapper parse(String json) throws IOException {
        ObjectMapper m = new ObjectMapper();
        return m.readValue(json, ThingWrapper.class);
    }

    public static String toJsonString(ThingWrapper tw) throws IOException {
        ObjectMapper m = new ObjectMapper();
        return m.writeValueAsString(tw);
    }
}
