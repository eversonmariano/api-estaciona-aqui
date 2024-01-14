package com.everson.estacionaaqui.repository;

import com.everson.estacionaaqui.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {



}
