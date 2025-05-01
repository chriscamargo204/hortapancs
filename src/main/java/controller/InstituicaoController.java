package com.pancs.hortapancs.controller;

import com.pancs.hortapancs.model.Instituicao;
import com.pancs.hortapancs.repository.InstituicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/instituicoes")
public class InstituicaoController {

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    // Mostrar formulário de cadastro
    @GetMapping("/cadastrar")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("instituicao", new Instituicao());
        return "form-instituicao";
    }

    // Salvar nova ou editar existente
    @PostMapping("/salvar")
    public String salvarInstituicao(@ModelAttribute Instituicao instituicao, RedirectAttributes redirectAttributes) {
        try {
            instituicaoRepository.save(instituicao);
            redirectAttributes.addFlashAttribute("mensagem", "Instituição cadastrada com sucesso.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar a instituição: " + e.getMessage());
        }
        return "redirect:/instituicoes/listar";
    }

    // Listar instituições
    @GetMapping("/listar")
    public String listarInstituicoes(Model model,
                                     @ModelAttribute("mensagem") String mensagem,
                                     @ModelAttribute("erro") String erro) {
        model.addAttribute("instituicoes", instituicaoRepository.findAll());
        return "listar-instituicoes";
    }

    // Editar instituição
    @GetMapping("/editar/{id}")
    public String editarInstituicao(@PathVariable Long id, Model model) {
        Instituicao instituicao = instituicaoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Instituição não encontrada: " + id));
        model.addAttribute("instituicao", instituicao);
        return "form-instituicao";
    }

    // Excluir instituição
    @GetMapping("/excluir/{id}")
    public String excluirInstituicao(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            instituicaoRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("mensagem", "Instituição excluída com sucesso.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir: " + e.getMessage());
        }
        return "redirect:/instituicoes/listar";
    }

    // Voltar para a página inicial
    @GetMapping("/")
    public String voltarAoInicio() {
        return "index";
    }
}
