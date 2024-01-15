package com.everson.estacionaaqui.service;

import com.everson.estacionaaqui.entity.Usuario;
import com.everson.estacionaaqui.exception.EntityNotFoundException;
import com.everson.estacionaaqui.exception.PasswordInvalidException;
import com.everson.estacionaaqui.exception.UserNameUniqueViolationException;
import com.everson.estacionaaqui.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        try {
            return usuarioRepository.save(usuario);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new UserNameUniqueViolationException(String.format("Username {%s} já cadastrado.", usuario.getUsername()));
        }

    }

    @Transactional(readOnly = true)
    public Usuario buscarporId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário id=%s não encontrado.", id))
        );
    }

    @Transactional
    public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
        if (!novaSenha.equals(confirmaSenha)) {
            throw new PasswordInvalidException("Nova senha não confere com a confirmação de senha.");
        }

        Usuario user = buscarporId(id);
        if (!user.getPassword().equals(senhaAtual)) {
            throw  new PasswordInvalidException("Sua senha não confere!");
        }

        user.setPassword(novaSenha);
        return user;

    }

    @Transactional(readOnly = true)
    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }
}
