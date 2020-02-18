package com.example.model;

public class Coche {

	private int id;
	private String marca, modelo;

	public Coche() {
		super();
	}

	public Coche(int id, String marca, String modelo) {
		super();
		this.id = id;
		this.marca = marca;
		this.modelo = modelo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return id + ";" + marca + ";" + modelo+";";
	}
	
}
