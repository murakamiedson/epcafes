package com.epcafes.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.epcafes.model.DespesaMaquina;
import com.epcafes.model.Maquina;
import com.epcafes.service.DespesaMaquinaService;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class LancarDespesaMaquinaController {

    @Autowired
    private DespesaMaquinaService despesaMaquinaService;

    @GetMapping("/restricted/custo/LancarDespesaMaquina")
    public String lancarDespesaMaquina(DespesaMaquina despesaMaquina, Model model,
            @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size,
            @RequestParam("qtdPorPagina") Optional<Integer> qtdPorPagina) {
        int currPage = page.orElse(1);
        int currSize = size.orElse(5);
        int pageSize = size.orElse(5);
        int qtdPorPaginaInt = qtdPorPagina.orElse(5);

        List<Maquina> maquinas = despesaMaquinaService.findAllMaquinas();

        List<DespesaMaquina> despesas = despesaMaquinaService.findPaginated(currPage, pageSize);
        int qtdPaginas = (int) Math.ceil(despesaMaquinaService.findAll().size() / (double) pageSize);
        List<Integer> pageNumbers = IntStream.rangeClosed(1, qtdPaginas).boxed().collect(Collectors.toList());
        List<Integer> qtdPorPaginaList = List.of(1, 2, 5, 10, 15, 20, 25);

        model.addAttribute("maquinas", maquinas);
        model.addAttribute("despesas", despesas);
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("qtdPaginas", qtdPaginas);
        model.addAttribute("currPage", currPage);
        model.addAttribute("qtdPorPagina", qtdPorPaginaInt);
        model.addAttribute("qtdPorPaginaList", qtdPorPaginaList);
        model.addAttribute("size", currSize);
        return "restricted/custo/LancarDespesaMaquina";
    }

    @PostMapping("/restricted/custo/LancarDespesaMaquina")
    public String novo(@Valid DespesaMaquina despesaMaquina, BindingResult result) {
        if (result.hasErrors()) {
            log.info("Erro: " + result.toString());
            return "restricted/custo/LancarDespesaMaquina";
        }

        log.info("Salvando DespesaMaquina: " + despesaMaquina.toString());
        despesaMaquinaService.save(despesaMaquina);

        return "redirect:/restricted/custo/LancarDespesaMaquina";
    }

    @GetMapping("/restricted/custo/LancarDespesaMaquina/delete/{id}")
    public String delete(@PathVariable(name = "id") Long id, Model model){

        DespesaMaquina despesaMaquina = despesaMaquinaService.findById(id);
        despesaMaquinaService.delete(despesaMaquina);

        return "redirect:/restricted/custo/LancarDespesaMaquina";
    }
}
