package br.edu.ifrn.control;

import br.edu.ifrn.model.Composicao;
import br.edu.ifrn.service.ComposicaoService;
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
public class CompoFormMB implements Serializable {


    private Integer id;
    private Composicao composicao;


    @Inject
    ComposicaoService composicaoService;

    public void init() {
        if(Faces.isAjaxRequest()){
           return;
        }
        if (has(id)) {
            composicao = composicaoService.encontrarComposicaoId(id);
        } else {
            composicao = new Composicao();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Composicao getComposicao() {
        return composicao;
    }

    public void setComposicao(Composicao composicao) {
        this.composicao = composicao;
    }


    public void remover() throws IOException {
        if (has(composicao) && has(composicao.getIdComposicao())) {
            composicaoService.remover(composicao);
            adicionaMensagem("Composição " + composicao.getDescricao() + " removida com sucesso");
            Faces.getFlash().setKeepMessages(true);
            Faces.redirect("compo-lista.xhtml");
        }
    }

    public void salvar() {
        String msg;
        if (composicao.getIdComposicao() == null) {
            
            composicaoService.inserir(composicao);
            
            msg = "Composição " + composicao.getDescricao() + " salva com sucesso";
        } else {
            
            composicaoService.atualizar(composicao);
            
            msg = "Composição " + composicao.getDescricao() + " atualizado com successo";
        }
        adicionaMensagem(msg);
    }

    public void limpar() {
        composicao = new Composicao();
        id = null;
    }

    public boolean isNew() {
        return composicao == null || composicao.getIdComposicao() == null;
    }

}
