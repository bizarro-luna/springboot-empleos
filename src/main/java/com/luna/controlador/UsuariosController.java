package com.luna.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.luna.servicio.IUsuariosService;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

	
	@Autowired
	private IUsuariosService servicio; 
    
    @GetMapping("/index")
	public String mostrarIndex(Model modelo) {
    	
    	modelo.addAttribute("usuarios", servicio.buscarTodos());
    	
    	return "usuarios/listUsuarios";
	}
    
    @GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable("id") int idUsuario, RedirectAttributes attributos) {		    	
		
    	attributos.addFlashAttribute("msg", "El usuario fue eliminado");
    	
    	servicio.eliminar(idUsuario);
    	
		return "redirect:/usuarios/index";
	}
}
