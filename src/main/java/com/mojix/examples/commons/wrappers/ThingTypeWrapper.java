package com.mojix.examples.commons.wrappers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ThingTypeWrapper
{	
	public Long id;
	
	public String name;
	
	public String code;

	public List<ThingTypeFieldWrapper> fields;

	public List<ThingTypeFieldWrapper> getUdfs()
	{
		List<ThingTypeFieldWrapper> out = new ArrayList<ThingTypeFieldWrapper>();

		for( ThingTypeFieldWrapper ttfw : fields )
		{
			// TODO: special case for location, locationXYZ, etc.
			out.add( ttfw );
		}

		Collections.sort( out, new Comparator<ThingTypeFieldWrapper>()
		{
			@Override
			public int compare( ThingTypeFieldWrapper o1, ThingTypeFieldWrapper o2 )
			{
				return o1.name.compareTo( o2.name );
			}

		} );

		return out;
	}

	public ThingTypeFieldWrapper getUdf( Long id )
	{
		for( ThingTypeFieldWrapper ttf : fields )
		{
			System.out.println( ttf.id + " " + id );
			if( ttf.id == id )
			{
				return ttf;
			}
		}
		
		return null;
	}
}
