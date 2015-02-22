package com.example.projetandroid2015.objects;

public class SpecialObjectFactory {

	public  <T> SpecialObject<T> createSpecialObject(T value) {
		if(value.getClass().getName().contains("Integer") || value.getClass().getName().contains("String") ||value.getClass().getName().contains("Float") )
			return new SpecialObject<T>(value);
		throw new IllegalArgumentException();
		
	}
}
