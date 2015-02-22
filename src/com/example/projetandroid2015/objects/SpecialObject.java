package com.example.projetandroid2015.objects;

public class SpecialObject<T> {

	private T value=null;
	
	SpecialObject(T value) {
		this.value=value;
	}
	
	public void setValue(T value) {
		this.value = value;
	}
	
	public T getValue() {
		return value;
	}
	
	public String getType() {
		return value.getClass().getName();
	}
}
