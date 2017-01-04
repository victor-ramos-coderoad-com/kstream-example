package com.tierconnect.riot.bridges;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * POJO container for the property time-values pairs related to ONE thing
 *
 * This class is mean to prepresent one messsage received across the MQTT
 * "/v1/data/#" topic.
 *
 * There may be many things (serialNumbers) per message.
 *
 * For example:
 *
 * <pre>
 *
 * TOPIC:
 *
 * /v1/data/ALEB/default_rfid_thingtype
 *
 * BODY:
 *
 * sn, 162
 * 000000000000000000481,1454695653156,location,-118.44394653934091;34.04823814917886;0.0
 * 000000000000000000481,1454695653156,locationXYZ,24.642882339787377;47.83314079436429;0.0
 * 000000000000000000481,1454695653156,logicalReader,LR5
 * 000000000000000000481,1454695653156,lastLocateTime,1454695653156
 * 000000000000000000481,1454695653156,lastDetectTime,1454695653156
 * 000000000000000000482,1454695653156,location,-118.44390997513838;34.048255816178326;0.0
 * 000000000000000000482,1454695653156,locationXYZ,37.247158377433934;50.11202476409718;0.0
 * 000000000000000000482,1454695653156,logicalReader,LR5
 * 000000000000000000482,1454695653156,lastLocateTime,1454695653156
 * 000000000000000000482,1454695653156,lastDetectTime,1454695653156
 * </pre>
 *
 *
 * NOTE: this class is NOT meant to be a conatiner for data otherwise located in
 * the THING CACHE.
 *
 * *DO NOT* add additional properties here - get from the THING CACHE INSTEAD
 * !!!!!!!!
 *
 *
 * @author tcrown
 *
 */
public class ThingMessage implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * The edgeBridgeCode this message came in on.
     */
    public String edgeBridgeCode;

    // optional, may be null. Use in thingMongoDAO for logging only
    public String specName;

    // optional, may be null. Use in thingMongoDAO for logging only
    public Long sequenceNumber;

    public String thingTypeCode;

    public String serialNumber;

    // what is this the time of ?
    private long time;

    //time CoreBridge
    private long modifiedTime;

    // KEY: property name
    public Map<String, ThingPropertyMessage> tpm = new HashMap<String, ThingPropertyMessage>();

    // Does this blink represent a new thing ?
    public boolean isNew = false;

    //TODO: implement !!!!!!!
    // needed by sparkcb
    public boolean outOfOrder = false;

    //TODO: implement !!!!!!!
    // needed by sparkcb
    public boolean reblinked = false;

    // the kafka partition
    //TODO: implement !!!!!!!
    public int partition;

    // the kafka number of partitions
    //TODO: implement !!!!!!!
    public int numPartitions;

    // the coordinate System String, e.g.:
    // needed by sparkcb
    public String cs;

    // the kafka offset
    // used by sparkcb
    public long offset;

    // the kafka highWaterMark
    // used by sparkcb
    public long highWaterMark;

    // the message age in seconds
    // used by sparkcb
    public double age;

    // the topic this message arrived on
    // used by sparkcb
    public String topic;

    public ThingMessage( String topic, String specName, Long sequenceNumber, String edgeBridgeCode, String thingTypeCode, String serialNumber, String cs )
    {
        this.topic = topic;
        this.specName = specName;
        this.sequenceNumber = sequenceNumber;
        this.edgeBridgeCode = edgeBridgeCode;
        this.thingTypeCode = thingTypeCode;
        this.serialNumber = serialNumber;
        this.cs = cs;
    }

    public ThingMessage( String specName, Long sequenceNumber, String thingTypeCode, String serialNumber )
    {
        this.specName = specName;
        this.sequenceNumber = sequenceNumber;
        this.thingTypeCode = thingTypeCode;
        this.serialNumber = serialNumber;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(long modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getThingTypeCode()
    {
        return thingTypeCode;
    }

    public String getSerialNumber()
    {
        return serialNumber;
    }

    public ThingPropertyMessage getUdf( String udfName )
    {
        return tpm.get( udfName );
    }

    public void put( long timestamp, String propertyName )
    {
        put( timestamp, propertyName, "" + timestamp );
    }

    public void put( long timestamp, String propertyName, boolean ruleChanged )
    {
        put( timestamp, propertyName, "" + timestamp, ruleChanged );
    }

    public void put( long timestamp, String propertyName, String value )
    {
        put( new ThingPropertyMessage( timestamp, propertyName, value ) );
    }

    public void put( long timestamp, String propertyName, String value, boolean ruleChanged )
    {
        put( new ThingPropertyMessage( timestamp, propertyName, value, ruleChanged ) );
    }

    public void put( long timestamp, String propertyName, long value )
    {
        put( new ThingPropertyMessage( timestamp, propertyName, "" + value ) );
    }

    public void put( long timestamp, String propertyName, long value, boolean ruleChanged )
    {
        put( new ThingPropertyMessage( timestamp, propertyName, "" + value, ruleChanged ) );
    }

    // Use exclusively for JSRule Property Setter
    @SuppressWarnings("unused")
    public void putField(long timestamp, String propertyName, String value)
    {
        put( new ThingPropertyMessage( timestamp, propertyName, value, true ) );
    }

    public void put( ThingPropertyMessage d )
    {
        tpm.put( d.getPropertyName(), d );
    }

    public ThingPropertyMessage getProperty(String key) {
        return tpm.get(key);
    }

    public Map<String, ThingPropertyMessage> getProperties() {
        return tpm;
    }
}
