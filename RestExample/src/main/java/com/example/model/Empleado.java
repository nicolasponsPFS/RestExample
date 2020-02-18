package com.example.model;

import java.util.ArrayList;
import java.util.List;

public class Empleado {
	
	private String nombre;
	private int id,edad;
	private List<Coche> coches;
	
	
	public Empleado() {
		super();
		coches = new ArrayList<Coche>();
	}


	public Empleado(int id, String nombre, int edad, List<Coche> coches) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.edad = edad;
		this.coches = coches;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public int getEdad() {
		return edad;
	}


	public void setEdad(int edad) {
		this.edad = edad;
	}


	public List<Coche> getCoches() {
		return coches;
	}


	public void setCoches(List<Coche> coches) {
		this.coches = coches;
	}
	
	


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	@Override
	public String toString() {
		String res =  id + ";" + nombre + ";" + edad + ";";
		if(coches.size() <= 0) {
			res += "null";
		}
		else {
			for (int i = 0; i < coches.size(); i++) {
				res += coches.get(i).getId();
				if(i+1 != coches.size()) {
					res += ",";
				}
			}
		}
		res+=";";
		return res;
	}

}
