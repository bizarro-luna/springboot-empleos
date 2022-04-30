package com.luna.servicio.db;

import java.util.List;
import java.util.Optional;

import org.hibernate.id.enhanced.InitialValueAwareOptimizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.luna.modelo.Vacante;
import com.luna.repositorio.VacantesRepository;
import com.luna.servicio.VacantesServicio;

@Service
@Primary
public class VacantesServicioJpa implements VacantesServicio {

	
	@Autowired
	private VacantesRepository repoVacantes;
	
	
	@Override
	public List<Vacante> getVacantes() {
		// TODO Auto-generated method stub
		return repoVacantes.findAll();
	}

	@Override
	public Vacante buscarPorId(Integer id) {
		Optional<Vacante> optional=repoVacantes.findById(id);
		
		if(optional.isPresent()) {
			return optional.get();
		}
		
		return null;
	}

	@Override
	public void guardar(Vacante vacante) {
		repoVacantes.save(vacante);
		
	}

	@Override
	public List<Vacante> buscarDestacadas() {
		// TODO Auto-generated method stub
		return repoVacantes.findByDestacadoAndEstatusOrderByIdDesc(1, "Aprobada");
	}

	@Override
	public void eliminar(Integer id) {
		repoVacantes.deleteById(id);
	}

	@Override
	public List<Vacante> buscarByExample(Example<Vacante> example) {
		return repoVacantes.findAll(example);
	}

	@Override
	public Page<Vacante> buscarTodas(Pageable page) {
		return repoVacantes.findAll(page);
	}

}
