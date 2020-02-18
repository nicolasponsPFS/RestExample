package com.example.rest.spring.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.model.Coche;
import com.example.model.Database;
import com.example.model.Empleado;

@Controller
public class ServicioCoches {

	private Database db;
	
	public ServicioCoches() {
		super();
		db = new Database();
	}
	
	@RequestMapping(method=RequestMethod.GET, value = "/coches")
	public @ResponseBody List<Coche> getCoches() {
		List<Coche> res = new ArrayList<Coche>();
		try {
			String c = ficheroCoches();
			String[] listaCoches = c.split(";");
			res = coches(listaCoches);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	@RequestMapping(method=RequestMethod.GET, value = "/empleados/{id}/coches")
	public @ResponseBody List<Coche> getCochesEmpleado(@PathVariable(value="id") String id) {
		List<Coche> res = new ArrayList<Coche>();
		try {
			String em = ficheroEmpleados();
			String c = ficheroCoches();
			String[] listaCoches = c.split(";");
			String[] listaEmpleados = em.split(";");
			List<Empleado> empleados = empleados(listaEmpleados, listaCoches);
			Empleado empleado = new Empleado();
			for (int i = 0; i < empleados.size(); i++) {
				if(empleados.get(i).getId() == Integer.parseInt(id)) {
					empleado = empleados.get(i);
				}
			}
			res = empleado.getCoches();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	private String ficheroEmpleados() throws IOException {
		return db.leerFichero(Database.EMPLEADOS);
	}
	
	private String ficheroCoches() throws IOException {
		return db.leerFichero(Database.COCHES);
	}
	
	private List<Coche> coches(String[] listaCoches) {
		List<Coche> res = new ArrayList<Coche>();
		for (int i = 0; i < listaCoches.length; i += 3) {
			Coche c = new Coche();
			c.setId(Integer.parseInt(listaCoches[i]));
			c.setMarca(listaCoches[i+1]);
			c.setModelo(listaCoches[i+2]);
			res.add(c);
		}
		return res;
	}
	
	private List<Empleado> empleados(String[] empleados, String[] listaCoches) {
		List<Empleado> res = new ArrayList<Empleado>();
		for (int i = 0; i < empleados.length; i += 4) {
			Empleado e = new Empleado();
			e.setId(Integer.parseInt(empleados[i]));
			e.setNombre(empleados[i+1]);
			e.setEdad(Integer.parseInt(empleados[i+2]));
			List<Coche> cochesEmpleado = null;
			if(!empleados[i+3].equals("null")) {
				String coches[] = empleados[i+3].split(",");
				cochesEmpleado = cochesEmpleado(coches, listaCoches);
			}
			else { 
				cochesEmpleado = new ArrayList<Coche>();
			}
			e.setCoches(cochesEmpleado);
			res.add(e);
		}
		return res;
	}
	
	private List<Coche> cochesEmpleado(String[]coches, String[]listaCoches) {
		List<Coche> cochesEmpleado = new ArrayList<Coche>();
		for (int j = 0; j < coches.length; j++) {
			for (int k = 0; k < listaCoches.length; k += 3) {
				if(coches[j].equals(listaCoches[k])) {
					Coche coche = new Coche();
					coche.setId(Integer.parseInt(listaCoches[k]));
					coche.setMarca(listaCoches[k+1]);
					coche.setModelo(listaCoches[k+2]);
					cochesEmpleado.add(coche);
				}
			}
		}
		return cochesEmpleado;
	}
	
}
