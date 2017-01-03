package com.mojix.examples.commons.wrappers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mojix.examples.commons.serializers.DateAsStringEncoding;
import com.mojix.examples.commons.serializers.ThingDeserializer;
import com.mojix.examples.commons.serializers.ThingSerializer;
import org.apache.avro.reflect.AvroEncode;
import org.apache.avro.reflect.DateAsLongEncoding;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(using = ThingSerializer.class)
@JsonDeserialize(using = ThingDeserializer.class)
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
    @AvroEncode(using = DateAsLongEncoding.class)
//    @AvroEncode(using = DateAsStringEncoding.class)
    public Date createdTime;

    /**
     * Date Time of modifiedTime.
     */
    @AvroEncode(using = DateAsLongEncoding.class)
//    @AvroEncode(using = DateAsStringEncoding.class)
    public Date modifiedTime;

    /**
     * Message time
     */
    @AvroEncode(using = DateAsLongEncoding.class)
//    @AvroEncode(using = DateAsStringEncoding.class)
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
    public List<Map<String, ThingPropertyWrapper>> properties;

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

    public List<Map<String, ThingPropertyWrapper>> getProperties() {
        return properties;
    }

    public void setProperties(List<Map<String, ThingPropertyWrapper>> properties) {
        this.properties = properties;
    }

    ThingPropertyWrapper getUdf(String name) {
        return properties.get(0).get(name);
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
                        List<Map<String, ThingPropertyWrapper>> properties) {
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

    /**
     * Static method to create a instance of message wrapper.
     *
     * @param tw A json string with contains the message.
     * @return instance of message wrapper.
     * @throws IOException input or output exception.
     */
    public static String toJsonString(ThingWrapper tw) throws IOException {
        ObjectMapper m = new ObjectMapper();
        return m.writeValueAsString(tw);
    }
}
