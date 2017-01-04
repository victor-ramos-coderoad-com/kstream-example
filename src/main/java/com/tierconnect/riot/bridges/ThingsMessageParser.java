package com.tierconnect.riot.bridges;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;

// this class parses a message on the "/v1/data" topic into a 'ThingsMessage'
// FOR "PROXY" or "AGGREGATOR" STYLE THINGS (.e.g. TAGS behind an MCON, STARFLEX, OPENRTLS, etc.)
// TODO: RENAME to ProxyMessageParser
public class ThingsMessageParser
{
    private static Logger logger = Logger.getLogger( ThingsMessageParser.class );

    static public boolean matchesTopic( String topic )
    {
        return topic.startsWith( "/v1/data" );
    }
    static final String REBLINK="___REBLINK___";

    // TOPIC:  /v1/data/[bridgeCode]/[thingTypeCode]
    // TOPIC:  /v1/data/STAR[macId]/[thingTypeCode]
    // TOPIC:  /v1/data/[bridgeCode]
    static public void parse( String topic, String body, ThingsMessage tm )
    {
        String[] topics = topic.split( "/" );

        if( topics.length >= 4 )
        {
            if( topics[3].startsWith( "STAR" ) )
            {
                //TODO: check this !
                tm.bridgeCode = topics[3];
                tm.macId = topics[3].substring( 3 );
                //tm.bridgeCode = topics[3];
                //tm.macId = null;
            }
            else
            {
                tm.bridgeCode = topics[3];
                tm.macId = null;
            }
            if( topics.length >= 5 )
            {
                tm.thingTypeCode = topics[4];
            }
        }
        else
        {
            tm.bridgeCode = null;
            tm.thingTypeCode = null;
        }

        tm.cs = null;

        tm.time = null;

        tm.entries = new ArrayList<ThingMessage>();

        tm.swarmFilter = true;

        ThingMessage ms = null;

        String[] lines = body.split( "\n" );

        for( int i = 0; i < lines.length; i++ )
        {
            String[] records = CSVParser.parseRecords( lines[i] );

            if( i == 0 && records[0] != null && records[0].startsWith( "sn" ) )
            {
                logger.info( "***************** line='" + lines[i] + "' l=" + records.length );
                if( "sn".equals( records[0] ) && records.length > 1 )
                {
                    tm.sequenceNumber = Long.parseLong( records[1] );
                }
                else
                {
                    tm.sequenceNumber = 0L;
                }

                if( records.length >= 3 )
                {
                    tm.specName = records[2];
                }

                if( records.length >= 5 )
                {
                    tm.specName2 = records[4];
                    tm.sequenceNumber2 = Long.parseLong( records[3] );
                }
            }
            else if( i == 1 && records[2] != null && "___CS___".equals( records[2] ) )
            {
                tm.cs = records[3];
            }
            else if( i == 2 && records[2] != null && "___TS___".equals( records[2] ) )
            {
                tm.alebTimstamp = Long.parseLong( records[3] );
            }
            else if(records[2] != null && records[2].equalsIgnoreCase("swarmFilter"))
            {
                tm.swarmFilter = Boolean.getBoolean(records[3]);
            }
            else
            {
                try
                {
                    int offset;
                    String ttc;
                    if( records.length == 4 )
                    {
                        offset = 0;
                        ttc = tm.thingTypeCode;
                    }
                    else
                    {
                        offset = 1;
                        ttc = records[0];
                    }

                    String serialNumber = records[offset+0];

                    ThingPropertyMessage d = new ThingPropertyMessage();

                    if( records[offset+1] == null || "".equals( records[offset+1] ) )
                    {
                        d.timestamp = System.currentTimeMillis();
                    }
                    else
                    {
                        d.timestamp = Long.parseLong( records[offset+1] );
                    }

                    d.propertyName = records[offset+2];

                    if( records.length > 3 )
                    {
                        d.value = records[offset+3];
                    }
                    else
                    {
                        d.value = null;
                    }

                    if( tm.time == null )
                    {
                        tm.time = d.timestamp;
                    }

                    if( serialNumber != null )
                    {
                        serialNumber = serialNumber.toUpperCase();
                        if( ms == null )
                        {
                            ms = new ThingMessage( topic, tm.specName, tm.sequenceNumber, tm.bridgeCode, ttc, serialNumber, tm.cs );

                            if(d.propertyName.equalsIgnoreCase(REBLINK)){
                                ms.reblinked=Boolean.valueOf(d.value!=null?d.value:"false");
                            }
                        }
                        else if( !serialNumber.equals( ms.serialNumber ) )
                        {
                            // new thing record
                            // logger.info( "adding ms=" + ms + "to entries=" +
                            // entries );
                            tm.entries.add( ms );
                            ms = new ThingMessage( topic, tm.specName, tm.sequenceNumber, tm.bridgeCode, ttc, serialNumber, tm.cs );
                        }
                    }

                    d.setChanged( true );

                    ms.put( d );
                    tm.thingSet.add( serialNumber );
                }
                catch( ArrayIndexOutOfBoundsException e )
                {
                    logger.warn( "Error: could not read thing property data skipping record=" + Arrays.toString( records ) );
                }
                catch( Exception e )
                {
                    logger.warn( "An error occurred, skipping record line='" + lines[i] + "' e: ", e );
                }
            }
        }

        // logger.info( "adding ms=" + ms + "to entries=" + entries );
        tm.entries.add( ms );
    }
}
