package com.luna.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luna.modelo.Vacante;


/**
 * Query Methods
 * @author Hector
 *
 */
public interface VacantesRepository extends JpaRepository<Vacante, Integer> {
	
	//Secrea las consultas con los atributos de los modelos
	
	List<Vacante> findByEstatus(String estatus);
	
	List<Vacante> findByDestacadoAndEstatusOrderByIdDesc(Integer destacado,String estatus);
	
	List<Vacante> findBySalarioBetweenOrderBySalarioDesc(Double salario1,Double salario2);
	
	List<Vacante> findByEstatusIn(String[]  estatus);
	

}
