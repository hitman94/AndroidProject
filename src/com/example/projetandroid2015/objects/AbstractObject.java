package com.example.projetandroid2015.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AbstractObject {
	
	private Long id;
	
	private Long idParent;
	
	private String name;
	
	private boolean sealed;
	
	private Map<String, AbstractObject> objects;
	private Map<String, SpecialObject<Object>> specialObjects;
	
	public AbstractObject(Long id,Long idParent, String name, boolean sealed) {
		this.id=id;
		this.idParent=idParent;
		this.name=name;
		this.sealed=sealed;
		objects=new HashMap<String, AbstractObject>();
		specialObjects = new HashMap<String, SpecialObject<Object>>();
	}
	
	public void addSpecialProperty(String name, SpecialObject<? super Object> o) {
		specialObjects.put(name, o);
	}
	
	public void addProperty(String name, AbstractObject o) {
		objects.put(name, o);
	}
	
	
	//TODO recuperer depuis la BDD
	public List<AbstractObject> getChilds() {
		return new ArrayList<AbstractObject>();
	}
	
	public Map<String,AbstractObject> getProperties() {
		return new HashMap<String, AbstractObject>(objects);
	}
	
	public Map<String, SpecialObject<Object>> getSpecialProperties() {
		return new HashMap<String, SpecialObject<Object>>(specialObjects);
	}
	
	/**
	 * GETTERS / SETTERS
	 * 
	 */
	
	public Long getId() {
		return id;
	}
	
	public Long getIdParent() {
		return idParent;
	}
	
	public void setIdParent(Long idParent) {
		this.idParent = idParent;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isSealed() {
		return sealed;
	}
	
	

}
