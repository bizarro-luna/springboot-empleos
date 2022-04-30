package com.luna.controlador;

import java.text.Bidi;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.luna.modelo.Vacante;
import com.luna.servicio.ICategoriasService;
import com.luna.servicio.VacantesServicio;
import com.luna.utileria.Utileria;

@Controller
@RequestMapping("/vacantes")	
public class VacantesControlador {
	
	
	@Value("${empleosapp.ruta.imagenes}")
	private String ruta;
	
	@Autowired
	private VacantesServicio servicioVacantes;
	
	@Autowired
	@Qualifier("categoriasServiceJpa")
	private ICategoriasService servicioCategorias;
	
	@GetMapping("/index")
	public String mostrarIndex(Model modelo) {
			
		List<Vacante> listaVacantes=new LinkedList<>();
		listaVacantes= servicioVacantes.getVacantes();
		modelo.addAttribute("vacantes", listaVacantes);
		
		return "vacantes/listVacantes";
	}
	
	@GetMapping(value = "/indexPaginate")
	public String mostrarIndexPaginado(Model model, Pageable page) {
		Page<Vacante> lista = servicioVacantes.buscarTodas(page);
		model.addAttribute("vacantes", lista);
		return "vacantes/listVacantes";
	}
	
	
	
	@GetMapping("/crear")
	public String crear(Vacante vacante,Model modelo) {
		
		
		return "vacantes/formVacante";
	}
	
	
/*	@PostMapping("/guardar")
	public String guardar(@RequestParam("nombre")  String nombre,@RequestParam("descripcion")  String descripcion, 
						  @RequestParam("categoria")  Integer categoria,@RequestParam("estatus")  String estatus,
						  @RequestParam("fecha") String fecha,@RequestParam("destacado")  Integer destacado,
						  @RequestParam("salario")  Double salario,@RequestParam("detalles")  String detalles) {
		
		
		System.out.println("Nombre:"+nombre);
		System.out.println("descripcion:"+descripcion);
		System.out.println("categoria:"+categoria);
		System.out.println("estatus:"+estatus);
		System.out.println("fecha:"+fecha);
		System.out.println("destacado:"+destacado);
		System.out.println("salario:"+salario);
		System.out.println("detalles:"+detalles);
		
		
		
		return "vacantes/listVacantes";
	}*/
	
	
	@PostMapping("/guardar")
	public String guardar(Vacante v, @RequestParam("archivoImagen") MultipartFile multiPart  ,BindingResult result,RedirectAttributes atributos) {
		if (result.hasErrors()) {
			
			for (ObjectError error: result.getAllErrors()){
				System.out.println("Ocurrio un error: " + error.getDefaultMessage());
			}
			
			
			
			return "vacantes/formVacante";
		}
		
		
		if (!multiPart.isEmpty()) {
			// String ruta = "/empleos/img-vacantes/"; // Linux/MAC
//			String ruta = "c:/empleos/img-vacantes/"; // Windows
			String nombreImagen = Utileria.guardarArchivo(multiPart, ruta);
			if (nombreImagen != null) { // La imagen si se subio
				// Procesamos la variable nombreImagen
				v.setImagen(nombreImagen);
			}
		}

		
		System.out.println(v.toString());
		
		servicioVacantes.guardar(v);
		String msg="Registro Guardado";
		atributos.addFlashAttribute("msg", msg);
		
		return "redirect:/vacantes/index";
	}
	
	@GetMapping("/view/{id}")
	public String verDetalles(@PathVariable("id") int idVacante,Model modelo) {
		
		System.out.println("IdVacante: "+idVacante);
		
		//Buscar el detalle en la base de datos
		Vacante v= servicioVacantes.buscarPorId(idVacante);
		
		modelo.addAttribute("idVacante", idVacante);
		
		modelo.addAttribute("vacante", v);
		
		return "catalogo/detalle";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(Model modelo,@PathVariable("id") int idVacante,RedirectAttributes atributos    ) {
		
		System.out.println("Id para eliminar es: "+ idVacante);
		
		servicioVacantes.eliminar(idVacante);
		
		atributos.addFlashAttribute("msg","La vacante fue eliminada");
		
		
		return "redirect:/vacantes/index";
	}
	
	@GetMapping("/editar/{id}")
	public String editar(@PathVariable("id") int idVacante,Model modelo) {
		
		Vacante vacante= servicioVacantes.buscarPorId(idVacante);
		
		
		modelo.addAttribute("vacante",vacante);
		
		return "vacantes/formVacante";
	}
	


	
	
	/**
	 * InitBider para pasar el formato date dd-MM-yyyy a una variable Date en el dominio
	 * @param webDataBinder
	 */
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	@ModelAttribute   //Datos al modelo genericos para todos lo metodos en el controlador
	public void setGenericos(Model modelo) {
		modelo.addAttribute("categorias", servicioCategorias.buscarTodas());
		
	}


}
