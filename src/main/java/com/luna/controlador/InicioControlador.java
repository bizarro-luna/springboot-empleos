package com.luna.controlador;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.luna.modelo.Perfil;
import com.luna.modelo.Usuario;
import com.luna.modelo.Vacante;
import com.luna.servicio.ICategoriasService;
import com.luna.servicio.IUsuariosService;
import com.luna.servicio.VacantesServicio;

@Controller
public class InicioControlador {
	
	
	@Autowired
	private VacantesServicio servicioVacantes;
	
	@Autowired
	private IUsuariosService servicioUsuario;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	
	@Autowired
	@Qualifier("categoriasServiceJpa")
	private ICategoriasService servicioCategoria;
	
	@GetMapping("/")
	public String mostrarHome(Model modelo,Authentication auth,HttpSession session){
		usaurioSession(auth, session);
		return "home";
	}
	
	@GetMapping("/index")
	public String mostrarIndex(Authentication auth,HttpSession session) {
		usaurioSession(auth, session);
		return "redirect:/";
	}
	
	@GetMapping("/registrar")
	public String registrarse(Usuario usuario) {
		return "/usuarios/formRegistro";
	}
	
	@PostMapping("/guardarRegistro")
	public String guardarRegistro(Usuario usuario, RedirectAttributes attributes,BindingResult result) {
		
		if(result.hasErrors()) {
			
			return "/usuarios/formRegistro";
		}
		
		
		servicioUsuario.guardar(usuario);
		
		return "redirect:/usuarios/index";
	}
	
	@GetMapping("/buscar")
	public String buscar(@ModelAttribute("search") Vacante vacante,Model modelo) {
		
		System.out.println("Buscando por "+vacante.toString());
		
		ExampleMatcher matcher=ExampleMatcher
				//where descripcion like '%%'
				.matching().withMatcher("descripcion", ExampleMatcher.GenericPropertyMatchers.contains());
		
		Example<Vacante> example= Example.of(vacante,matcher);
		List<Vacante> lista= servicioVacantes.buscarByExample(example);
		
		modelo.addAttribute("vacantes", lista);
		
		return "home";
	}
	
	/**
	 * InitBinder para Strings si los detecta vacios el Data Biunding los setea a NULL
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	@ModelAttribute //Anotacion para agregar atributos a todos los metodos del controlador
	public void setGenericos(Model modelo) {
		Vacante vacanteSearch= new Vacante();
		modelo.addAttribute("vacantes", servicioVacantes.buscarDestacadas());
		modelo.addAttribute("categorias", servicioCategoria.buscarTodas());
		modelo.addAttribute("search", vacanteSearch);
	}
	
	
	@GetMapping("/listado")
	public String mostrarListado(Model modelo) {
		
		List<String> lista= new LinkedList<>();
		lista.add("Ingeniero de sistemas");
		lista.add("Auxiliar de contabilidad");
		lista.add("Vendedor");
		lista.add("Arquitecto");
		
		
		modelo.addAttribute("empleos", lista);
		
		return "catalogo/listado";
	}
	
	@GetMapping("/detalle")
	public String mostrarDetalle(Model modelo) {
		Vacante v= new Vacante(); 
		
		v.setNombre("Ingeniero de Comunicaciones");
		v.setDescripcion("Se solicita ingeniero para dar soporte a intranet");
		v.setFecha(new Date());
		v.setSalario(9800.0);
		
		modelo.addAttribute("vacante", v);
		
	
		return "catalogo/detalle";
	}
	
	@GetMapping("/tabla")
	public String mostrarTabla(Model modelo) {
		List<Vacante> listaVacante= servicioVacantes.getVacantes();
		modelo.addAttribute("vacantes", listaVacante);
		
		return "catalogo/tabla";
	}
	
	
	/**
	 * Renderizar al login
	 * @return
	 */
	@GetMapping("/login")
	public String mostrarLogin() {
		return "formLogin";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
		logoutHandler.logout(request, null, null);
		return "redirect:/ogin";
	}

	
	private void usaurioSession(Authentication auth,HttpSession session) {
		
		if(auth!=null) {
			String userName= auth.getName();
			if(session.getAttribute("usuario")==null) {
				Usuario usuario = servicioUsuario.buscarPorUsername(userName);
				usuario.setPassword(null);
				System.out.println(usuario);
				session.setAttribute("usuario", usuario);
			}
		}
		
		
	}
	
	
	/**
	 * Regresar Texto con el {@link ResponseBody}
	 * @param texto
	 * @return
	 */
	@GetMapping("/bcrypt/{texto}")
	@ResponseBody
	public String encriptar(@PathVariable("texto") String texto) {
		return texto+ " Encriptado en bcrypt :"+ passwordEncoder.encode(texto) ;
	}
	
	
	

}
