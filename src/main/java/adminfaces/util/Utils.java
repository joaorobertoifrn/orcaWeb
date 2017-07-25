package adminfaces.util;

import adminfaces.model.Insumos;
import org.omnifaces.util.Messages;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import java.io.Serializable;
import java.util.*;
import java.util.stream.IntStream;

@ApplicationScoped
public class Utils implements Serializable {

    private List<Insumos> insumos;


    @PostConstruct
    public void init() {
        insumos = new ArrayList<>();
        IntStream.rangeClosed(1, 50)
                .forEach(i -> insumos.add(criar(i)));
    }

    private static Insumos criar(int i) {
        return new Insumos(i).model("descricao " + i).name("unidade" + i).price(Double.valueOf(i));
    }

     public static void adicionaMensagem(String mensagem){
       adicionaMensagem(mensagem, null);
    }

    public static void adicionaMensagem(String mensagem, FacesMessage.Severity severity){

        FacesMessage facesMessage = Messages.create("").detail(mensagem).get();
        if(severity != null && severity != FacesMessage.SEVERITY_INFO) {
            facesMessage.setSeverity(severity);
        } else{
            Messages.add(null,facesMessage);
        }
    }

    @Produces
    public List<Insumos> getInsumos() {
        return insumos;
    }

}
