package com.luna.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.luna.modelo.Categoria;
import com.luna.servicio.ICategoriasService;

@Controller
@RequestMapping(value="/categorias")
public class CategoriasControlador {
	
	@Autowired
	@Qualifier("categoriasServiceJpa")
	private ICategoriasService servicio;
	
	
	// @GetMapping("/index")
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String mostrarIndex(Model modelo) {
		
		List<Categoria> listaCategorias= servicio.buscarTodas();
		
		modelo.addAttribute("categorias", listaCategorias);
		
	return "categorias/listCategorias";
	}
	
	/**
	 * Mostrar con paginador
	 * @param model
	 * @param page
	 * @return
	 */
	@GetMapping(value = "/indexPaginate")
	public String mostrarIndexPaginado(Model model, Pageable page) {
		Page<Categoria> lista= servicio.buscarTodas(page);
		model.addAttribute("categorias", lista);
		return "categorias/listCategorias";
	}
	
	// @GetMapping("/create")
	@RequestMapping(value="/crear", method=RequestMethod.GET)
	public String crear(Categoria categoria) {
	return "categorias/formCategoria";
	}
	// @PostMapping("/save")
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String guardar(Categoria categoria,BindingResult result,RedirectAttributes atributos) {
		
		System.out.println("Nombre: "+categoria.getNombre());
		System.out.println("Descripcion: "+categoria.getDescripcion());
		
		
		if(result.hasErrors()) {
			
			return "categorias/formCategoria";
		}
		
		servicio.guardar(categoria);
		atributos.addFlashAttribute("msg", "Guardado con Exito");
		
		return "redirect:/categorias/index";
	}
	
	
	@GetMapping("/editar/{id}")
	public String editar(@PathVariable("id") int idCategoria,Model modelo) {
		
		Categoria categoria= servicio.buscarPorId(idCategoria);
		
		modelo.addAttribute("categoria", categoria);
		
		
		return "categorias/formCategoria";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable("id") int idCategoria,RedirectAttributes atributos) {
		
		servicio.eliminar(idCategoria);
		
		atributos.addFlashAttribute("msg", "Se elimino con exito");
	
		return "redirect:/categorias/index";
	}


}
