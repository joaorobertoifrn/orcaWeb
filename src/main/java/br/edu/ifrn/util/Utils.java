package br.edu.ifrn.util;

import br.edu.ifrn.model.Obra;
import org.omnifaces.util.Messages;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import java.io.Serializable;
import java.util.*;
import java.util.stream.IntStream;

/// gera dados demo somente
@ApplicationScoped
public class Utils implements Serializable {

    private List<Obra> obra;


    @PostConstruct
    public void init() {
        obra = new ArrayList<>();
        IntStream.rangeClosed(1, 50)
                .forEach(i -> obra.add(criar(i)));
    }

    private static Obra criar(int i) {
        return new Obra(i).nomeObra("Obra " + i).BDI(Double.valueOf(i));
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
    public List<Obra> getInsumos() {
        return obra;
    }

}
