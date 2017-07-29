package br.edu.ifrn.util;

import br.edu.ifrn.model.Fase;
import br.edu.ifrn.model.Obra;
import org.omnifaces.util.Messages;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import java.io.Serializable;
import java.util.*;
import java.util.stream.IntStream;

/// gera dados dados demo antes da app iniciar
@ApplicationScoped
public class Utils implements Serializable {

    private List<Obra> obra;
    private List<Fase> fase;
    


    @PostConstruct
    public void init() {
        fase = new ArrayList<>();
        obra = new ArrayList<>();
      
        IntStream.rangeClosed(1, 30).forEach(i -> fase.add(gerarFases(i)));
        IntStream.rangeClosed(1, 30).forEach(j -> obra.add(gerarObras(j)));
        
    }

    private static Obra gerarObras(int i) {
        return new Obra(i).nomeObra("Obra " + i).BDI(Double.valueOf(i));
    }
    private static Fase gerarFases(int i) {
        return new Fase(i).descricao("Fase " + i).item(i+".");
    }

    public static void adicionaMensagem(String msg){
       adicionaMensagem(msg, null);
    }

    public static void adicionaMensagem(String msg, FacesMessage.Severity severity){

        FacesMessage facesMessage = Messages.create("").detail(msg).get();
        if(severity != null && severity != FacesMessage.SEVERITY_INFO) {
            facesMessage.setSeverity(severity);
        } else{
            Messages.add(null,facesMessage);
        }
    }

    @Produces
    public List<Fase> getFase() {
        return fase;
    }
    @Produces
    public List<Obra> getObra() {
        return obra;
    }
    

}
