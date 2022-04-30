package com.luna.servicio.db;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.luna.modelo.Solicitud;
import com.luna.repositorio.SolicitudesRepository;
import com.luna.servicio.ISolicitudesService;

@Service
public class SolicitudesServiceJpa implements ISolicitudesService {
	
	@Autowired
	private SolicitudesRepository repositorio;
	

	@Override
	public void guardar(Solicitud solicitud) {
		
		solicitud.setFecha(new Date());
		repositorio.save(solicitud);
	}

	@Override
	public void eliminar(Integer idSolicitud) {
		repositorio.deleteById(idSolicitud);
	}

	@Override
	public List<Solicitud> buscarTodas() {
		return repositorio.findAll();
	}

	@Override
	public Solicitud buscarPorId(Integer idSolicitud) {
		
		Solicitud solicitud= null;
		
		Optional<Solicitud> optional= repositorio.findById(idSolicitud);
		
		if (optional.isPresent()) {
			solicitud= optional.get();
		}
		
		return solicitud;
	}

	@Override
	public Page<Solicitud> buscarTodas(Pageable page) {
		return repositorio.findAll(page);
	}	

}
