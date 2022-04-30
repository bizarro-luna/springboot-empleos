package com.luna.servicio;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.luna.modelo.Vacante;

public interface VacantesServicio {
	
	
	public List<Vacante> getVacantes();
	
	public Vacante buscarPorId(Integer id);
	
	public void guardar(Vacante vacante);
	
	public List<Vacante> buscarDestacadas();
	
	public void eliminar (Integer id);
	
	public List<Vacante> buscarByExample(Example<Vacante> example);
	
	public Page<Vacante> buscarTodas(Pageable page);
	
}
