package br.edu.ifrn.control;

import br.edu.ifrn.model.Fase;
import br.edu.ifrn.service.FaseService;
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
public class FaseFormMB implements Serializable {


    private Integer id;
    private Fase fase;


    @Inject
    FaseService faseService;

    public void init() {
        if(Faces.isAjaxRequest()){
           return;
        }
        if (has(id)) {
            fase = faseService.encontrarFaseId(id);
        } else {
            fase = new Fase();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Fase getFase() {
        return fase;
    }

    public void setFase(Fase fase) {
        this.fase = fase;
    }


    public void remover() throws IOException {
        if (has(fase) && has(fase.getIdFase())) {
            faseService.remover(fase);
            adicionaMensagem("Fase " + fase.getDescricao() + " removida com sucesso");
            Faces.getFlash().setKeepMessages(true);
            Faces.redirect("fase-lista.xhtml");
        }
    }

    public void salvar() {
        String msg;
        if (fase.getIdFase() == null) {
            faseService.inserir(fase);
            msg = "Fase " + fase.getDescricao() + " salva com sucesso";
        } else {
            faseService.atualizar(fase);
            msg = "Fase " + fase.getDescricao() + " atualizada com successo";
        }
        adicionaMensagem(msg);
    }

    public void limpar() {
        fase = new Fase();
        id = null;
    }

    public boolean isNew() {
        return fase == null || fase.getIdFase() == null;
    }

}
