package com.everson.estacionaaqui.web.controller;


import com.everson.estacionaaqui.entity.Usuario;
import com.everson.estacionaaqui.service.UsuarioService;

import com.everson.estacionaaqui.web.dto.UsuarioCreateDTO;
import com.everson.estacionaaqui.web.dto.UsuarioResponseDTO;
import com.everson.estacionaaqui.web.dto.UsuarioSenhaDTO;
import com.everson.estacionaaqui.web.dto.mapper.UsuarioMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> create(@Valid @RequestBody UsuarioCreateDTO createDTO) {
        Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(createDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDTO(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> getById(@PathVariable Long id) {
        Usuario user = usuarioService.buscarporId(id);
        return ResponseEntity.ok(UsuarioMapper.toDTO(user));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassswordById(@PathVariable Long id,@Valid @RequestBody UsuarioSenhaDTO dto) {
        Usuario user = usuarioService.editarSenha(id, dto.getSenhaAtual(), dto.getNovaSenha(), dto.getConfirmaSenha());
        return ResponseEntity.noContent().build();
    }



    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> getAll() {
        List<Usuario> users = usuarioService.buscarTodos();
        return ResponseEntity.ok(UsuarioMapper.toListDTO(users));
    }

}
