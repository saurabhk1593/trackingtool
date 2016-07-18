package com.techm.trackingtool.util;

public class LindeMap extends LinkedHashMap{
	
	private String name;

	private String id;

	/**
	 * Creates a new CRCMap object.
	 */
	public LindeMap() {
		name     = "";
		id		 = "";
	}

	/**
	 * Returns the id.
	 * @return String
	 */
	public String getId() {
		return id;
	}

	/**
	 * Returns the name.
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the id.
	 * @param id The id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Sets the name.
	 * @param name The name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
