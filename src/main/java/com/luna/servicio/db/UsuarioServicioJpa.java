package com.luna.servicio.db;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.luna.modelo.Perfil;
import com.luna.modelo.Usuario;
import com.luna.repositorio.UsuariosRepository;
import com.luna.servicio.IUsuariosService;



@Service
public class UsuarioServicioJpa implements IUsuariosService {

	
	
	@Autowired
	private UsuariosRepository repositorio;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Override
	public void guardar(Usuario usuario) {
		
		
		String pwPlano= usuario.getPassword();
		String pwEncriptado= passwordEncoder.encode(pwPlano);
		usuario.setPassword(pwEncriptado);
		
		
		List<Perfil> listaPerfil = new LinkedList<>();
		
		Perfil perfil= new Perfil();
		perfil.setId(3);
		
		listaPerfil.add(perfil);
		
		usuario.setEstatus(1);
		usuario.setFechaRegistro(new Date());
		usuario.setPerfiles(listaPerfil);
		
		
		repositorio.save(usuario);
	}

	@Override
	public void eliminar(Integer idUsuario) {
		repositorio.deleteById(idUsuario);
	}

	@Override
	public List<Usuario> buscarTodos() {
		return repositorio.findAll();
	}

	@Override
	public Usuario buscarPorUsername(String username) {
		// TODO Auto-generated method stub
		return repositorio.findByUsername(username);
	}
	
	

}
