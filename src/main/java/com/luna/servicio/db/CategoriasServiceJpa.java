package com.luna.servicio.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.luna.modelo.Categoria;
import com.luna.repositorio.CategoriasRepository;
import com.luna.servicio.ICategoriasService;


@Service
//@Primary //Se utiliza para indicar que utilice esta implementacion cuando hay dos implementaciones de la misma interfaz
public class CategoriasServiceJpa implements ICategoriasService{

	
	@Autowired
	private CategoriasRepository repoCatego;
	
	
	@Override
	public void guardar(Categoria categoria) {
		repoCatego.save(categoria);
		
	}

	@Override
	public List<Categoria> buscarTodas() {
		
		return repoCatego.findAll();
	}

	@Override
	public Categoria buscarPorId(Integer idCategoria) {
		
		Optional<Categoria> optional=repoCatego.findById(idCategoria);
		
		if(optional.isPresent()) {
			return optional.get();
		}
		
		return null;
	}

	@Override
	public void eliminar(Integer idCategoria) {
		repoCatego.deleteById(idCategoria);
	}

	@Override
	public Page<Categoria> buscarTodas(Pageable pageable) {
		return repoCatego.findAll(pageable);
	}

}
