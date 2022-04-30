package com.luna.servicio;

import java.util.List;

import com.luna.modelo.Usuario;


public interface IUsuariosService {

	void guardar(Usuario usuario);
	void eliminar(Integer idUsuario);
	List<Usuario> buscarTodos();
	Usuario buscarPorUsername(String username);
}


