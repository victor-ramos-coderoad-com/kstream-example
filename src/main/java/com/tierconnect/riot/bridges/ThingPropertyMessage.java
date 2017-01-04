package com.tierconnect.riot.bridges;

import java.io.Serializable;

import org.apache.log4j.Logger;

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
 * NOTE: this class is NOT meant to be a container for data otherwise located in the THING CACHE.
 *
 * *DO NOT* add additional properties here - get from the THING CACHE INSTEAD !!!!!!!!
 *
 *
 * @author tcrown
 *
 */
public class ThingPropertyMessage implements Serializable
{
    private static final long serialVersionUID = 1L;

    private static Logger logger = Logger.getLogger( ThingPropertyMessage.class );

    public long timestamp;

    public String propertyName;

    //this represents the value in the MQTT message body, which is always a String
    public String value;

    public boolean changed;

    public boolean ruleChanged;

    public ThingPropertyMessage()
    {

    }

    public ThingPropertyMessage( long timestamp )
    {
        this.timestamp = timestamp;
    }

    public ThingPropertyMessage( long time, String propertyName, String value, boolean ruleChanged )
    {
        this.timestamp = time;
        this.propertyName = propertyName;
        this.value = value;
        this.ruleChanged = ruleChanged;
        this.changed=true;
    }

    public ThingPropertyMessage( long time, String propertyName, String value )
    {
        this.timestamp = time;
        this.propertyName = propertyName;
        this.value = value;
        this.changed = true;
    }

    public long getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp( long timestamp )
    {
        this.timestamp = timestamp;
    }

    public String getPropertyName()
    {
        return propertyName;
    }

    public void setPropertyName( String propertyName )
    {
        this.propertyName = propertyName;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue( String value )
    {
        this.value = value;
    }
    public boolean isChanged()
    {
        return changed;
    }

    public void setChanged( boolean changed )
    {
        this.changed = changed;
    }

    public boolean isRuleChanged()
    {
        return ruleChanged;
    }

    public void setRuleChanged(boolean ruleChanged)
    {
        this.ruleChanged = ruleChanged;
    }

    @Override
    public String toString()
    {
        return "ThingPropertyMessage{" +
                "propertyName='" + propertyName + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
