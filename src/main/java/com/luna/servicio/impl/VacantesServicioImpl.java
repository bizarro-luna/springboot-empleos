package com.luna.servicio.impl;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.luna.modelo.Vacante;
import com.luna.servicio.VacantesServicio;


@Service
public class VacantesServicioImpl implements VacantesServicio {

	List<Vacante> listaVacantes= null;
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	
	public VacantesServicioImpl() {
		listaVacantes= new LinkedList<>();
		try {
			Vacante v1= new Vacante();
			v1.setId(1);
			v1.setNombre("Java jr");
			v1.setDescripcion("Experiencia de 6 meses");
			v1.setFecha(sdf.parse("14-01-2020"));
			v1.setSalario(15500.0);
			v1.setDestacado(1);
			v1.setImagen("logo1.png");
			v1.setEstatus("Aprobada");
			
			Vacante v2= new Vacante();
			v2.setId(2);
			v2.setNombre("Java medio");
			v2.setDescripcion("Experiencia de 1 año");
			v2.setFecha(sdf.parse("14-01-2020"));
			v2.setSalario(30500.0);
			v2.setDestacado(1);
			v2.setImagen("logo2.png");
			v2.setEstatus("Creada");
			
			
			Vacante v3= new Vacante();v2.setId(2);
			v3.setId(3);
			v3.setNombre("Java Sr");
			v3.setDescripcion("Experiencia de 3 años");
			v3.setFecha(sdf.parse("14-01-2020"));
			v3.setSalario(40500.0);
			v3.setDestacado(0);
			v3.setImagen("logo3.png");
			v3.setEstatus("Eliminada");
		
			listaVacantes.add(v1);
			listaVacantes.add(v2);
			listaVacantes.add(v3);
		}catch(Exception e) {
			
			System.out.println("Error "+ e.getMessage());
		}	
		
	}
	
	
	@Override
	public List<Vacante> getVacantes() {
		// TODO Auto-generated method stub
		return listaVacantes;
	}


	@Override
	public Vacante buscarPorId(Integer id) {
		
		Vacante v= new Vacante();
		  for(Vacante va:listaVacantes) {
			  if(id.intValue()==va.getId().intValue()){
				  v=va;
			  }
			  
		  }
		return v;
	}


	@Override
	public void guardar(Vacante vacante) {
		int idVacante=listaVacantes.size()+1;
		vacante.setId(idVacante);
		listaVacantes.add(vacante);
	}


	@Override
	public List<Vacante> buscarDestacadas() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void eliminar(Integer id) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public List<Vacante> buscarByExample(Example<Vacante> example) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Page<Vacante> buscarTodas(Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}

}
