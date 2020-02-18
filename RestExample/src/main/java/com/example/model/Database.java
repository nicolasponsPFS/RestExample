package com.example.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Database {

	public static final int EMPLEADOS = 0;
	public static final int COCHES = 1;
	
	private static final String PATH_EMPLEADOS = "empleados.txt";
	private static final String PATH_COCHES = "coches.txt";
	
	private File empleados;
	private File coches;
	
	public Database() {
		empleados = new File(PATH_EMPLEADOS);
		coches = new File(PATH_COCHES);
	}
	
	public String leerFichero(int opcion) throws IOException {
		String res = "";
		FileReader fr = null;
		switch (opcion) {
		case 0:
			fr = new FileReader(empleados);
			break;
		case 1:
			fr = new FileReader(coches);
			break;
		}
		BufferedReader br = new BufferedReader(fr);
		String linea;
		while((linea = br.readLine()) != null) {
			res += linea;
		}
		return res;
	}
	
	public void escribirFichero(String texto, int opcion, boolean append) throws IOException {
		FileWriter fw = null;
		switch (opcion) {
		case 0:
			fw = new FileWriter(empleados, append);
			break;
		case 1:
			fw = new FileWriter(coches, append);
			break;
		}
		PrintWriter pw = new PrintWriter(fw);
		pw.println(texto);
		pw.flush();
		pw.close();
	}
}
