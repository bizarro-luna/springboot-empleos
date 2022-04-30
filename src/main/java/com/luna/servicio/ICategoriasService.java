package com.luna.servicio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.luna.modelo.Categoria;


public interface ICategoriasService {
	void guardar(Categoria categoria);
	List<Categoria> buscarTodas();
	Categoria buscarPorId(Integer idCategoria);	
	
	
	//Implementar el metodo
	public void eliminar(Integer idCategoria);
	
	public Page<Categoria> buscarTodas(Pageable page);
}