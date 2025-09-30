package com.centralclim.Centralclim.service;

import com.centralclim.Centralclim.dto.CriarServicoRequest;
import com.centralclim.Centralclim.model.Cliente;
import com.centralclim.Centralclim.model.Servico;
import com.centralclim.Centralclim.model.StatusServico;
import com.centralclim.Centralclim.model.Usuario;
import com.centralclim.Centralclim.repository.ClienteRepository;
import com.centralclim.Centralclim.repository.ServicoRepository;
import com.centralclim.Centralclim.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class ServicoService {
    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Servico agendarServico(CriarServicoRequest request) {
        // Busca as entidades completas no banco a partir dos IDs recebidos no DTO
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));

        Usuario tecnico = usuarioRepository.findById(request.getTecnicoId())
                .orElseThrow(() -> new RuntimeException("Técnico não encontrado!"));

        // Cria a nova entidade Servico
        Servico novoServico = new Servico();
        novoServico.setDescricao(request.getDescricao());
        novoServico.setValor(request.getValor());
        novoServico.setDataAgendamento(request.getDataAgendamento());
        novoServico.setStatus(StatusServico.AGENDADO);

        // Associa as entidades relacionadas
        novoServico.setCliente(cliente);
        novoServico.setUsuario(tecnico);

        // Salva a nova entidade no banco
        return servicoRepository.save(novoServico);
    }


    //  Lista todos os serviços
    public List<Servico> listarServicos() {
        return servicoRepository.findAll();
    }

    //  Busca um serviço por ID
    public Optional<Servico> buscarPorId(Long id) {
        return servicoRepository.findById(id);
    }

    public Servico atualizarStatus(Long id, StatusServico novoStatus) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado!"));

        servico.setStatus(novoStatus);
        return servicoRepository.save(servico);
    }
}
