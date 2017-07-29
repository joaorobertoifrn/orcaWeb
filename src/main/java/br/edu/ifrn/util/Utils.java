package br.edu.ifrn.util;

import br.edu.ifrn.model.Base;
import br.edu.ifrn.model.Fase;
import br.edu.ifrn.model.Obra;
import br.edu.ifrn.model.Orcamento;
import org.omnifaces.util.Messages;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import java.io.Serializable;
import java.util.*;
import java.util.stream.IntStream;

/// gera dados dados demo antes do aplicativo iniciar
@ApplicationScoped
public class Utils implements Serializable {

    private List<Obra> obra;
    private List<Fase> fase;
    private List<Base> base;
    private List<Orcamento> orcamento;

    @PostConstruct
    public void init() {
        fase = new ArrayList<>();
        obra = new ArrayList<>();
        base = new ArrayList<>();
        orcamento = new ArrayList<>();
        
      
        IntStream.rangeClosed(1, 10).forEach(i -> fase.add(gerarFases(i)));
        IntStream.rangeClosed(1, 10).forEach(j -> obra.add(gerarObras(j)));
        IntStream.rangeClosed(1,  9).forEach(k -> base.add(gerarBases(k)));
        IntStream.rangeClosed(1,  8).forEach(i -> orcamento.add(gerarOrcas(i)));
        
    }

    private static Obra gerarObras(int i) {
        return new Obra(i).nomeObra("Obra " + i).BDI(Double.valueOf(i));
    }
    private static Fase gerarFases(int i) {
        return new Fase(i).descricao("Fase " + i).item(i+".");
    }
    private static Base gerarBases(int i) {
        return new Base(i).mesAno("0"+i+"2017" ).url("http://www.caixa.gov.br/Downloads/sinapi-a-partir-jul-2009-rn/SINAPI_ref_Insumos_Composicoes_RN_062017_NaoDesonerado.zip");
    }
    private static Orcamento gerarOrcas(int i) {
        return new Orcamento(i).descricao("Orçamento 0"+i+"/2017" ).total(i*3+(1240.54)).Obra(new Obra(i)).Base(new Base(i));
        // return new Orcamento(i).descricao("Orçamento 0"+i+"/2017" );
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
    @Produces
    public List<Base> getBase() {
        return base;
    }
    @Produces
    public List<Orcamento> getOrcamento() {
        return orcamento;
    }
    

}
