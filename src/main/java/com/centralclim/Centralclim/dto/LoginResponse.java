package com.centralclim.Centralclim.dto;

import com.centralclim.Centralclim.model.Perfil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class LoginResponse {
    private Long id;
    private String nome;
    private Perfil perfil;



}
