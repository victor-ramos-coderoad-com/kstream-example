package com.mojix.examples.commons.wrappers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataTypeThingTypeWrapper implements Serializable {
	private static final long serialVersionUID = 1L;
	public String thingTypeCode;
}
