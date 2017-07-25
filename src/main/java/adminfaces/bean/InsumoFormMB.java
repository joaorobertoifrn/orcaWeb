/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adminfaces.bean;

import adminfaces.model.Insumos;
import adminfaces.service.InsumosService;
import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Faces;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

import static com.github.adminfaces.template.util.Assert.has;
import static adminfaces.util.Utils.adicionaMensagem;

@Named
@ViewScoped
public class InsumoFormMB implements Serializable {


    private Integer id;
    private Insumos insumo;


    @Inject
    InsumosService insumoService;

    public void init() {
        if(Faces.isAjaxRequest()){
           return;
        }
        if (has(id)) {
            insumo = insumoService.findById(id);
        } else {
            insumo = new Insumos();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Insumos getInsumo() {
        return insumo;
    }

    public void setInsumo(Insumos insumo) {
        this.insumo = insumo;
    }


    public void remove() throws IOException {
        if (has(insumo) && has(insumo.getId())) {
            insumoService.remover(insumo);
            adicionaMensagem("Insumo " + insumo.getDescricao()
                    + " removido com sucesso");
            Faces.getFlash().setKeepMessages(true);
            Faces.redirect("insumo-lista.xhtml");
        }
    }

    public void salvar() {
        String msg;
        if (insumo.getId() == null) {
            insumoService.inserir(insumo);
            msg = "Insumo " + insumo.getDescricao() + " salvo com sucesso";
        } else {
            insumoService.atualizar(insumo);
            msg = "Insumo " + insumo.getDescricao() + " atualizado com successo";
        }
        adicionaMensagem(msg);
    }

    public void limpar() {
        insumo = new Insumos();
        id = null;
    }

    public boolean isNew() {
        return insumo == null || insumo.getId() == null;
    }


}
