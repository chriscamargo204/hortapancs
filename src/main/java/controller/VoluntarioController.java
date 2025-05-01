package com.pancs.hortapancs.controller;

import com.pancs.hortapancs.model.Instituicao;
import com.pancs.hortapancs.model.Voluntario;
import com.pancs.hortapancs.repository.InstituicaoRepository;
import com.pancs.hortapancs.repository.VoluntarioRepository;
import com.pancs.hortapancs.service.VoluntarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/voluntarios")
public class VoluntarioController {

    @Autowired
    private VoluntarioRepository voluntarioRepository;

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    @Autowired
    private VoluntarioService voluntarioService;

    private Long voluntarioTempId; // <--- Guarda o ID temporariamente

    // Formulário de cadastro
    @GetMapping("/cadastrar")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("voluntario", new Voluntario());
        return "form-voluntario";
    }

    // Salva voluntário e redireciona para escolha manual se necessário
    @PostMapping("/salvar")
    public String salvarVoluntario(@ModelAttribute Voluntario voluntario, RedirectAttributes redirectAttributes) {
        Voluntario salvo = voluntarioService.salvarComInstituicaoMaisProxima(voluntario);

        if (salvo.getInstituicao() == null) {
            voluntarioTempId = salvo.getId(); // guarda ID para recuperar depois
            return "redirect:/voluntarios/escolher-instituicao";
        }

        redirectAttributes.addFlashAttribute("mensagem", "Voluntário cadastrado com sucesso.");
        return "redirect:/voluntarios/listar";
    }

    // Página para escolher a instituição manualmente
    @GetMapping("/escolher-instituicao")
    public String escolherInstituicao(Model model) {
        Voluntario voluntario = voluntarioRepository.findById(voluntarioTempId).orElse(null);

        if (voluntario != null) {
            model.addAttribute("voluntario", voluntario);
            model.addAttribute("instituicoes", instituicaoRepository.findAll());
            model.addAttribute("distancias", voluntarioService.mapearDistancias(voluntario.getCep()));
        }

        return "escolher-instituicao";
    }

    // Associa instituição manualmente
    @PostMapping("/salvar-manual")
    public String salvarManual(
            @RequestParam Long voluntarioId,
            @RequestParam Long instituicaoId,
            RedirectAttributes redirectAttributes
    ) {
        Voluntario voluntario = voluntarioRepository.findById(voluntarioId).orElse(null);
        Instituicao instituicao = instituicaoRepository.findById(instituicaoId).orElse(null);

        if (voluntario != null && instituicao != null) {
            voluntario.setInstituicao(instituicao);
            voluntarioRepository.save(voluntario);
        }

        redirectAttributes.addFlashAttribute("mensagem", "Instituição associada com sucesso ao voluntário.");
        return "redirect:/voluntarios/listar";
    }

    // Lista todos os voluntários
    @GetMapping("/listar")
    public String listarVoluntarios(Model model) {
        model.addAttribute("voluntarios", voluntarioRepository.findAll());
        return "listar-voluntarios";
    }

    // Editar voluntário
    @GetMapping("/editar/{id}")
    public String editarVoluntario(@PathVariable Long id, Model model) {
        Voluntario voluntario = voluntarioRepository.findById(id).orElseThrow();
        model.addAttribute("voluntario", voluntario);
        return "form-voluntario";
    }

    // Excluir voluntário
    @GetMapping("/excluir/{id}")
    public String excluirVoluntario(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        voluntarioRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("mensagem", "Voluntário excluído com sucesso.");
        return "redirect:/voluntarios/listar";
    }
}
