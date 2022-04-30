package com.luna.controlador;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.luna.modelo.Solicitud;
import com.luna.modelo.Usuario;
import com.luna.modelo.Vacante;
import com.luna.servicio.ISolicitudesService;
import com.luna.servicio.VacantesServicio;
import com.luna.utileria.Utileria;

@Controller
@RequestMapping("/solicitudes")
public class SolicitudesController {
	
	
	@Autowired
	private ISolicitudesService servicio;
	
	
	@Autowired
	private VacantesServicio servicioVacante;
	
	
	/**
	 * EJERCICIO: Declarar esta propiedad en el archivo application.properties. El valor sera el directorio
	 * en donde se guardarán los archivos de los Curriculums Vitaes de los usuarios.
	 */
	@Value("${empleosapp.ruta.cv}")
	private String ruta;
		
    /**
	 * Metodo que muestra la lista de solicitudes sin paginacion
	 * Seguridad: Solo disponible para un usuarios con perfil ADMINISTRADOR/SUPERVISOR.
	 * @return
	 */
    @GetMapping("/index") 
	public String mostrarIndex(Model modelo) {
    	
    	List<Solicitud> solicitudes= new LinkedList<>();
    	solicitudes= servicio.buscarTodas();
    	
    	modelo.addAttribute("solicitudes", solicitudes);

    	// EJERCICIO
		return "solicitudes/listSolicitudes";
		
	}
    
    /**
	 * Metodo que muestra la lista de solicitudes con paginacion
	 * Seguridad: Solo disponible para usuarios con perfil ADMINISTRADOR/SUPERVISOR.
	 * @return
	 */
	@GetMapping("/indexPaginate")
	public String mostrarIndexPaginado(Model modelo, Pageable page) {
		Page<Solicitud> solicitudes= servicio.buscarTodas(page);
    	modelo.addAttribute("solicitudes", solicitudes);
		
		// EJERCICIO
		return "solicitudes/listSolicitudes";
		
	}
    
	/**
	 * Método para renderizar el formulario para aplicar para una Vacante
	 * Seguridad: Solo disponible para un usuario con perfil USUARIO.
	 * @return
	 */
	@GetMapping("/crear/{idVacante}")
	public String crear(@PathVariable("idVacante")Integer idVacante, Model modelo,Solicitud solicitud,HttpSession session) {
		
		Vacante vacante= servicioVacante.buscarPorId(idVacante);
		Usuario usuario= (Usuario) session.getAttribute("usuario");
		System.out.println(usuario);
		
		solicitud.setVacante(vacante);
		solicitud.setUsuario(usuario);
		
		modelo.addAttribute("vacante", vacante);
		
		// EJERCICIO
		return "solicitudes/formSolicitud";
		
	}
	
	/**
	 * Método que guarda la solicitud enviada por el usuario en la base de datos
	 * Seguridad: Solo disponible para un usuario con perfil USUARIO.
	 * @return
	 */
	@PostMapping("/guardar")
	public String guardar(Solicitud solicitud, @RequestParam("archivoCV") MultipartFile multiPart, BindingResult result,RedirectAttributes atributos) {	
		
		if (result.hasErrors()) {
			for (ObjectError error: result.getAllErrors()){
				System.out.println("Ocurrio un error: " + error.getDefaultMessage());
			}
			return "solicitudes/formSolicitud";
		}
		
		
		
		if (!multiPart.isEmpty()) {
			String nombreArchivo = Utileria.guardarArchivo(multiPart, ruta);
			if (nombreArchivo != null) { // La imagen si se subio
				// Procesamos la variable nombreImagen
				solicitud.setArchivo(nombreArchivo);
			}
		}
		
		
		servicio.guardar(solicitud);
		
		// EJERCICIO
		return "redirect:/";	
		
	}
	
	/**
	 * Método para eliminar una solicitud
	 * Seguridad: Solo disponible para usuarios con perfil ADMINISTRADOR/SUPERVISOR. 
	 * @return
	 */
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable("id")Integer id,RedirectAttributes atributos) {
		
		servicio.eliminar(id);
		
		atributos.addFlashAttribute("msg","La solicitud fue eliminada");
		
		
		// EJERCICIO
		return "redirect:/solicitudes/indexPaginate";
		
	}
			
	/**
	 * Personalizamos el Data Binding para todas las propiedades de tipo Date
	 * @param webDataBinder
	 */
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
}
