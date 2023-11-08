package com.epcafes.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;

import com.epcafes.enums.EnumUtil;
import com.epcafes.enums.TipoAuxiliarInsumos;
import com.epcafes.enums.TipoCombustivel;
import com.epcafes.enums.TipoInsumo;
import com.epcafes.exception.BusinessException;
import com.epcafes.model.Maquina;
import com.epcafes.service.MaquinaService;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.PostMapping;

@Getter
@Setter
@Controller
public class InsumoController {
    @Autowired
    private MaquinaService maquinaService;

    @GetMapping({ "/restricted/cadastro/cadastroInsumos", "restricted/cadastro/editarInsumo/{id}" })
    public String cadastroInsumos(Model model, Maquina maquinaFind, @PathVariable(name = "id") Optional<Long> id)
            throws BusinessException {
        TipoInsumo[] opcoesInsumos = TipoInsumo.values();

        List<TipoAuxiliarInsumos> opcoesMaquinas = EnumUtil.getTiposMaquinas();
        List<TipoAuxiliarInsumos> opcoesImplementos = EnumUtil.getTiposImplementos();
        TipoCombustivel[] opcoesCombustivel = TipoCombustivel.values();

        model.addAttribute("opcoesInsumos", opcoesInsumos);
        model.addAttribute("opcoesMaquinas", opcoesMaquinas);
        model.addAttribute("opcoesImplementos", opcoesImplementos);
        model.addAttribute("opcoesCombustivel", opcoesCombustivel);
        model.addAttribute("maquinas", maquinaService.buscarMaquinas());

        if (id.isPresent()) {
            maquinaFind = maquinaService.buscarPeloCodigo(id.get());
            model.addAttribute("maquina", maquinaFind);

        }

        return "restricted/cadastro/TesteModal";
    }

    @PostMapping({ "/restricted/cadastro/cadastroInsumos", "/restricted/cadastro/editarInsumos/{id}" })
    public String create(Maquina insumo, @PathVariable(name = "id") Optional<Long> id) throws BusinessException {
        if (id.isPresent()) {
            maquinaService.atualizar(insumo, id.get());

        }

        maquinaService.salvar(insumo);
        return "redirect:/restricted/cadastro/cadastroInsumos";

    }

    @PostMapping("/restricted/cadastro/cadastroInsumos/refresh")
    public String refresh() throws BusinessException {

        return "redirect:/restricted/cadastro/cadastroInsumos";

    }

    @GetMapping("restricted/cadastro/pesquisaInsumos")
    public String pesquisa(Model model) {
        TipoInsumo[] opcoesInsumos = TipoInsumo.values();
        List<TipoAuxiliarInsumos> opcoesMaquinas = EnumUtil.getTiposMaquinas();
        List<TipoAuxiliarInsumos> opcoesImplementos = EnumUtil.getTiposImplementos();
        TipoCombustivel[] opcoesCombustivel = TipoCombustivel.values();

        model.addAttribute("opcoesInsumos", opcoesInsumos);
        model.addAttribute("opcoesMaquinas", opcoesMaquinas);
        model.addAttribute("opcoesImplementos", opcoesImplementos);
        model.addAttribute("opcoesCombustivel", opcoesCombustivel);

        model.addAttribute("maquinas", maquinaService.buscarMaquinas());

        return "restricted/cadastro/TesteModal";

    }

    @GetMapping("restricted/cadastro/maquina/delete/{id}")
    public String deleteItem(@PathVariable("id") Long id) throws BusinessException {
        Maquina maquina = maquinaService.buscarPeloCodigo(id);

        maquinaService.excluir(maquina);
        return "redirect:/restricted/cadastro/cadastroInsumos";

    }

}
