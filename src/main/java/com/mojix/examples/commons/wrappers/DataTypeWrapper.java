package com.mojix.examples.commons.wrappers;

import java.io.Serializable;

public class DataTypeWrapper implements Serializable {
	private static final long serialVersionUID = 1L;
	public Long id;
	
	public String code;
	
	public String description;
	
	public String type;
	
	public String typeParent;
	
	public String value;
	
	public String clazz;
}
