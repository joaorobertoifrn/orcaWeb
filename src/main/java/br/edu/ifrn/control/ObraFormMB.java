package br.edu.ifrn.control;

import br.edu.ifrn.model.Obra;
import br.edu.ifrn.service.ObraService;
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
public class ObraFormMB implements Serializable {


    private Integer id;
    private Obra obra;


    @Inject
    ObraService obraService;

    public void init() {
        if(Faces.isAjaxRequest()){
           return;
        }
        if (has(id)) {
            obra = obraService.encontarId(id);
        } else {
            obra = new Obra();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Obra getObra() {
        return obra;
    }

    public void setObra(Obra obra) {
        this.obra = obra;
    }


    public void remove() throws IOException {
        if (has(obra) && has(obra.getIdObra())) {
            obraService.remover(obra);
            adicionaMensagem("Obra " + obra.getNomeObra() + " removida com sucesso");
            Faces.getFlash().setKeepMessages(true);
            Faces.redirect("obra-lista.xhtml");
        }
    }

    public void salvar() {
        String msg;
        if (obra.getIdObra() == null) {
            obraService.inserir(obra);
            msg = "Obra " + obra.getNomeObra() + " salva com sucesso";
        } else {
            obraService.atualizar(obra);
            msg = "Obra " + obra.getNomeObra() + " atualizado com successo";
        }
        adicionaMensagem(msg);
    }

    public void limpar() {
        obra = new Obra();
        id = null;
    }

    public boolean isNew() {
        return obra == null || obra.getIdObra() == null;
    }

}
