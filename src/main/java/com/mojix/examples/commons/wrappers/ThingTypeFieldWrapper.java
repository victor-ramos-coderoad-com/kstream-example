package com.mojix.examples.commons.wrappers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ThingTypeFieldWrapper implements Serializable {
	private static final long serialVersionUID = 1L;
	public Long id;

	public String name;

	public DataTypeWrapper dataType;

	public DataTypeThingTypeWrapper dataTypeThingType;
}
