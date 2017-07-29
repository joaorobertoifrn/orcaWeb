package br.edu.ifrn.control;

import br.edu.ifrn.model.Orcamento;
import br.edu.ifrn.model.Orcamento;
import br.edu.ifrn.service.OrcamentoService;
import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Faces;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

import static com.github.adminfaces.template.util.Assert.has;
import static br.edu.ifrn.util.Utils.adicionaMensagem;

@Named
@ViewScoped
public class OrcaFormMB implements Serializable {


    private Integer id;
    private Orcamento orcamento;


    @Inject
    OrcamentoService orcamentoService;

    public void init() {
        if(Faces.isAjaxRequest()){
           return;
        }
        if (has(id)) {
            orcamento = orcamentoService.encontrarOrcamentoId(id);
        } else {
            orcamento = new Orcamento();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Orcamento getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Orcamento orca) {
        this.orcamento = orca;
    }


    public void remover() throws IOException {
        // TODO: Remover orcamento 
        //       Remover Itens do Orcamento 
        
        if (has(orcamento) && has(orcamento.getIdOrcamento())) {
            orcamentoService.remover(orcamento);
            adicionaMensagem("Orca " + orcamento.getDescricao() + " removida com sucesso");
            Faces.getFlash().setKeepMessages(true);
            Faces.redirect("orca-lista.xhtml");
        }
    }

    public void salvar() {
        String msg;
        if (orcamento.getIdOrcamento() == null) {
            orcamentoService.inserir(orcamento);
            msg = "Orcamento " + orcamento.getDescricao() + " salvo com sucesso";
        } else {
            orcamentoService.atualizar(orcamento);
            msg = "Orcamento " + orcamento.getDescricao() + " atualizado com successo";
        }
        adicionaMensagem(msg);
    }

    public void limpar() {
        orcamento = new Orcamento();
        id = null;
    }

    public boolean isNew() {
        return orcamento == null || orcamento.getIdOrcamento() == null;
    }
}
