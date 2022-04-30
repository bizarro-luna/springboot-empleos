package com.luna.servicio.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.luna.modelo.Categoria;
import com.luna.servicio.ICategoriasService;


@Service
//@Primary
public class CategoriasServiceImpl implements ICategoriasService {

	private List<Categoria> listaCategorias;
	
	
	
	public CategoriasServiceImpl(){
		listaCategorias= new LinkedList<>();
		
		
		Categoria c1= new Categoria();
		Categoria c2= new Categoria();
		Categoria c3= new Categoria();
		Categoria c4= new Categoria();
		Categoria c5= new Categoria();
		Categoria c6= new Categoria();
		
		
		c1.setId(1);
		c1.setNombre("Informatica");
		c1.setDescripcion("Desarrollo de software");
		
		c2.setId(2);
		c2.setNombre("Ventas");
		c2.setDescripcion("Ventas de software");
		
		c3.setId(3);
		c3.setNombre("Contabilidad");
		c3.setDescripcion("Contabilidad de la empresa");
		
		c4.setId(4);
		c4.setNombre("Transporte");
		c4.setDescripcion("Manejo de trasnporte publico");
		
		c5.setId(5);
		c5.setNombre("Construccion");
		c5.setDescripcion("Construir edificios");
		
		c6.setId(6);
		c6.setNombre("Desarrollo de Software");
		c6.setDescripcion("Trabajo para programadores");
		
		
		
		
		listaCategorias.add(c1);
		listaCategorias.add(c2);
		listaCategorias.add(c3);
		listaCategorias.add(c4);
		listaCategorias.add(c5);
		listaCategorias.add(c6);
	}
	
	
	
	
	@Override
	public void guardar(Categoria categoria) {
			int idCategoria=listaCategorias.size()+1;
			categoria.setId(idCategoria);
			listaCategorias.add(categoria);
	}

	@Override
	public List<Categoria> buscarTodas() {
		// TODO Auto-generated method stub
		return listaCategorias;
	}

	@Override
	public Categoria buscarPorId(Integer idCategoria) {
		
		Categoria respuesta=new Categoria();
		
		for(Categoria c:listaCategorias) {
			if(idCategoria.intValue()==c.getId().intValue()) {
				respuesta=c;
			}
			
		}
		
		return respuesta;
	}




	@Override
	public void eliminar(Integer idCategoria) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public Page<Categoria> buscarTodas(Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}
