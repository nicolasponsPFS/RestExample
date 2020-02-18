package com.example.rest.spring.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.model.Coche;
import com.example.model.Database;
import com.example.model.Empleado;

@Controller
public class ServicioEmpleados{
	
	private Database db;

	public ServicioEmpleados() {
		super();
		db = new Database();
	}
	
	@RequestMapping(method=RequestMethod.GET, value = "/empleados")
	public @ResponseBody List<Empleado> getEmpleados() {
		List<Empleado> res = new ArrayList<Empleado>();
		try {
			String s = ficheroEmpleados();
			String c = ficheroCoches();
			String[] listaCoches = c.split(";");
			String[] empleados = s.split(";");
			res = empleados(empleados, listaCoches);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}

	@RequestMapping(method=RequestMethod.GET, value = "/empleados/{id}")
	public @ResponseBody Empleado getEmpleado(@PathVariable(value="id") String id) {
		Empleado e = new Empleado();
		try {
			List<Empleado> empl = new ArrayList<Empleado>();
			String s = ficheroEmpleados();
			String c = ficheroCoches();
			String[] listaCoches = c.split(";");
			String[] empleados = s.split(";");
			empl = empleados(empleados, listaCoches);
			for (int i = 0; i < empl.size(); i++) {
				if(empl.get(i).getId() == Integer.parseInt(id)) {
					e = empl.get(i);
				}
			}
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return e;
	}
	
	@RequestMapping(method=RequestMethod.POST, value = "/empleados")
	public @ResponseBody void addEmpleado(@RequestBody Empleado parametros) {
		Empleado e = new Empleado();
		e.setId(parametros.getId());
		e.setNombre(parametros.getNombre());
		e.setEdad(parametros.getEdad());
		try {
			System.out.println(e.toString());
			db.escribirFichero(e.toString(), Database.EMPLEADOS, true);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	@RequestMapping(method=RequestMethod.PUT, value = "/empleados/{id}")
	public @ResponseBody Empleado updateEmpleado(@RequestBody Empleado parametros, @PathVariable(value="id") String id) {
		Empleado empleado = new Empleado();
		try {
			String fe = ficheroEmpleados();
			String fc = ficheroCoches();
			String res = "";
			List<Empleado> empleados = empleados(fe.split(";"), fc.split(";"));
			String nuevoNombre = parametros.getNombre();
			int nuevoEdad = parametros.getEdad();
			empleado.setId(Integer.parseInt(id));
			empleado.setNombre(nuevoNombre);
			empleado.setEdad(nuevoEdad);
			for (int i = 0; i < empleados.size(); i++) {
				if(empleados.get(i).getId() == Integer.parseInt(id)) {
					System.out.println(empleados.get(i).toString());
					res+= empleado.toString()+"\r";
				} else {
					res += empleados.get(i)+"\r";
				}
			}
			db.escribirFichero(res, Database.EMPLEADOS, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return empleado;
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value = "/empleados/{id}")
	public @ResponseBody boolean deleteEmpleado(@PathVariable(value="id") String id) {
		String res ="";
		try {
			String s = ficheroEmpleados();
			String c = ficheroCoches();
			String[] listaCoches = c.split(";");
			String[] empleados = s.split(";");
			List<Empleado> listaEmpleados = empleados(empleados, listaCoches);
			for (int i = 0; i < listaEmpleados.size(); i++) {
				if(listaEmpleados.get(i).getId() != Integer.parseInt(id)) {
					res+=listaEmpleados.get(i).toString()+"\r";
				}
			}
			db.escribirFichero(res, Database.EMPLEADOS, false);
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	private String ficheroCoches() throws IOException {
		return db.leerFichero(Database.COCHES);
	}
	
	private String ficheroEmpleados() throws IOException {
		return db.leerFichero(Database.EMPLEADOS);
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
}
