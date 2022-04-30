package com.luna.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luna.modelo.Solicitud;


public interface SolicitudesRepository extends JpaRepository<Solicitud, Integer> {

}
