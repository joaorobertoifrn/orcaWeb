package br.edu.ifrn.control;

import br.edu.ifrn.model.Base;
import br.edu.ifrn.service.BaseService;
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
public class BaseFormMB implements Serializable {

    private Integer id;
    private Base base;

    @Inject
    BaseService baseService;

    public void init() {
        if(Faces.isAjaxRequest()){
           return;
        }
        if (has(id)) {
            base = baseService.encontrarBaseId(id);
        } else {
            base = new Base();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }


    public void remover() throws IOException {
        if (has(base) && has(base.getIdBase())) {
            baseService.remover(base);
            adicionaMensagem("Base de Precos SINAPI " + base.getMesAno() + " removida com sucesso");
            Faces.getFlash().setKeepMessages(true);
            Faces.redirect("base-lista.xhtml");
        }
    }

    public void salvar() {
        String msg;
        if (base.getIdBase() == null) {
            baseService.inserir(base);
            msg = "Base de Preços SINAPI " + base.getMesAno() + " salva com sucesso";
        } else {
            baseService.atualizar(base);
            msg = "Base de Preços SINAPI " + base.getMesAno() + " atualizado com successo";
        }
        adicionaMensagem(msg);
    }

    public void limpar() {
        base = new Base();
        id = null;
    }

    public boolean isNew() {
        return base == null || base.getIdBase() == null;
    }
    
    public void importaSinapi() {
        String msg = "Base de Preços "+base.getMesAno()+ " importada com sucesso! ";
        
    }

}
