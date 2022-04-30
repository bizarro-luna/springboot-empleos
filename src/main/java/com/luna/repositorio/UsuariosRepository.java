package com.luna.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luna.modelo.Usuario;


public interface UsuariosRepository extends JpaRepository<Usuario, Integer> {
	
	
	Usuario findByUsername(String username);

}
