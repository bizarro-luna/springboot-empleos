package com.luna.configuracion;

import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguracion implements WebMvcConfigurer  {
	
		@Value("${empleosapp.ruta.imagenes}")
		private String rutaImagen;
	
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			//registry.addResourceHandler("/logos/**").addResourceLocations("file:/empleos/img-vacantes/"); // Linux
//			registry.addResourceHandler("/logos/**").addResourceLocations("file:c:/empleos/img-vacantes/"); // Windows
			registry.addResourceHandler("/logos/**").addResourceLocations("file:"+rutaImagen); // Windows
		}

	

}
