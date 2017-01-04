package com.tierconnect.riot.bridges;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

/**
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
public class ThingsMessage implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     *  the kafka partition key
     */
    private String key;

    String topic;

    Long time;

    String specName;

    Long sequenceNumber;

    String specName2;

    Long sequenceNumber2;

    public String cs;

    // NOTE: only one of bridgeCode or macId should ever be defined
    /**
     * The edgeBridgeCode
     */
    public String bridgeCode;

    @Deprecated //TODO: refactor to eliminate, is not used
            String macId;

    public String thingTypeCode;

    long alebTimstamp;

    public ArrayList<ThingMessage> entries;

    public Set<String> thingSet = new TreeSet<String>();

    public boolean swarmFilter = false;

    public ThingsMessage()
    {

    }

    public ThingsMessage( ThingsMessage thingsMessage )
    {
        this.topic = thingsMessage.topic;
        this.time = thingsMessage.time;
        this.specName = thingsMessage.specName;
        this.sequenceNumber = thingsMessage.sequenceNumber;
        this.specName2 = thingsMessage.specName2;
        this.sequenceNumber2 = thingsMessage.sequenceNumber2;
        this.cs = thingsMessage.cs;
        this.bridgeCode = thingsMessage.bridgeCode;
        this.macId = thingsMessage.macId;
        this.thingTypeCode = thingsMessage.thingTypeCode;
        this.alebTimstamp = thingsMessage.alebTimstamp;
        this.entries = new ArrayList<ThingMessage>();
        this.swarmFilter = thingsMessage.swarmFilter;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey( String key )
    {
        this.key = key;
    }

    public String getTopic()
    {
        return topic;
    }

    public long getTimestamp()
    {
        return time;
    }

    public String getBridgeCode()
    {
        return bridgeCode;
    }

    public String getMacId()
    {
        return macId;
    }

    public String getCode()
    {
        if( bridgeCode != null )
        {
            return bridgeCode;
        }
        else
        {
            return macId;
        }
        // TODO: handle [groupCode]
    }

    public int getThingCount()
    {
        return thingSet.size();
    }

    public ArrayList<ThingMessage> getThings()
    {
        return entries;
    }

    public Long getSequenceNumber()
    {
        return sequenceNumber;
    }

    public String getThingTypeCode()
    {
        return thingTypeCode;
    }

    public void put( String thingTypeCode, String serialNumber, long timestamp, String udf, String value )
    {
        ThingMessage tm = this.getThingMessage( thingTypeCode, serialNumber );
        tm.put( timestamp, udf, value );
    }

    private ThingMessage getThingMessage( String thingTypeCode, String serialNumber )
    {
        ThingMessage tm = null;

//		for( ThingMessage tm0 : entries )
//		{
//			if( thingTypeCode.equals( tm0.getThingTypeCode() ) && serialNumber.equals( tm0.getSerialNumber() ) )
//			{
//				tm = tm0;
//			}
//		}

        if( tm == null )
        {
            tm = new ThingMessage( specName, sequenceNumber, thingTypeCode, serialNumber );
            entries.add( tm );
            thingSet.add( serialNumber );
        }

        return tm;
    }
}
