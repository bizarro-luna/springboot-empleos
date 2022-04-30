package com.luna.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.luna.modelo.Categoria;


//public interface CategoriasRepository extends CrudRepository<Categoria, Integer	>
public interface CategoriasRepository extends JpaRepository<Categoria, Integer	> {
	
	
	
	
	

}
