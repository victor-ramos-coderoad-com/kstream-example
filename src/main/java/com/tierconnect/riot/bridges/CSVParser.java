package com.tierconnect.riot.bridges;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.regex.Pattern;

public class CSVParser
{
    private static Logger logger = Logger.getLogger( CSVParser.class );

    private static Pattern COMMA_PATTERN = Pattern.compile( ",(?=([^\"]*[\"][^\"]*[\"])*[^\"]*$)" );

    // parses a csv string.
    // has special handling for specific chars
    public static String[] parseRecords( String line )
    {
        // special case the thingTypeCode is included on the line
        // ('edgeBridge' in this case, used for stats)
        if( line.startsWith( "edgeBridge" ) )
        {
            return  line.split( "," );
        }
        //RIOT-7909 Attachments cannot be uploaded when core bridge is up, json value is truncated
        //we found a better approach to split the string separated by commas
        //String[] records = COMMA_PATTERN.split( line );
        //if the split comes with more than four substring, the max parameter will join all substrings and returns
        //all of them in records[4]
        String[] records  = line.split(",", 4);
        if( records.length > 3 && StringUtils.isNumeric(records[2]) ){
            records  = line.split(",", 5);
        }

        for( int j = 0; j < records.length; j++ )
        {
            String record = records[j].trim();
            if( record.length() > 2 && record.charAt( 0 ) == '"' && record.charAt( record.length() - 1 ) == '"' )
            {
                records[j] = record.substring( 1, record.length() - 1 );
            }
            else
            {
                if( record.equals( "\"\"" ) || record.equals( "''" ) )
                {
                    records[j] = "";
                }
                else
                {
                    records[j] = record;
                }
            }
            logger.debug( "record[" + j + "]='" + records[j] + "'" );
        }

        return records;
    }
}
