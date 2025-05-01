package com.pancs.hortapancs.controller;

import com.pancs.hortapancs.model.Voluntario;
import com.pancs.hortapancs.repository.VoluntarioRepository;
import com.pancs.hortapancs.service.GeolocalizacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/teste")
public class TesteController {

    @Autowired
    private GeolocalizacaoService geolocalizacaoService;

    @Autowired
    private VoluntarioRepository voluntarioRepository;


    @GetMapping
    public String mostrarFormulario() {
        return "distancia"; // Thymeleaf procurará por distancia.html
    }

    // Teste de associação entre dois CEPs usando ViaCEP (sem coordenadas)
    @GetMapping("/distancia")
    @ResponseBody
    public String testarDistancia(@RequestParam String cep1, @RequestParam String cep2) {
        // Obter endereço do CEP
        String endereco1 = geolocalizacaoService.obterEnderecoPorCEP(cep1);
        String endereco2 = geolocalizacaoService.obterEnderecoPorCEP(cep2);

        if (endereco1 != null && endereco2 != null) {
            return String.format("Endereço do CEP %s: %s\nEndereço do CEP %s: %s", cep1, endereco1, cep2, endereco2);
        } else {
            return "Não foi possível obter os endereços dos CEPs.";
        }
    }

    // Página com todos os voluntários
    @GetMapping("/voluntarios")
    public String listarVoluntarios(Model model) {
        List<Voluntario> voluntarios = voluntarioRepository.findAll();
        model.addAttribute("voluntarios", voluntarios);
        return "voluntarios-lista";
    }
}
