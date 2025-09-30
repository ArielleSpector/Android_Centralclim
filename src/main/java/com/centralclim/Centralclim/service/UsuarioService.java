package com.centralclim.Centralclim.service;


import com.centralclim.Centralclim.dto.LoginRequest;
import com.centralclim.Centralclim.dto.LoginResponse;
import com.centralclim.Centralclim.model.Usuario;
import com.centralclim.Centralclim.repository.UsuarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public LoginResponse autenticar(LoginRequest loginRequest){
        Usuario usuario = usuarioRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));



        if (usuario.getSenha().equals(loginRequest.getSenha())) {
            return new LoginResponse(usuario.getId(), usuario.getNome(), usuario.getPerfil());
        } else {
            throw new RuntimeException("Senha inválida!");
        }
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }





}
