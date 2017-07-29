package br.edu.ifrn.infra.security;

import com.github.adminfaces.template.session.AdminSession;
import org.omnifaces.util.Faces;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Specializes;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

import static br.edu.ifrn.util.Utils.adicionaMensagem;

/**
 *
 * This is just a login example.
 *
 * AdminSession uses isLoggedIn to determine if user must be redirect to login page or not.
 * By default AdminSession isLoggedIn always resolves to true so it will not try to redirect user.
 *
 * If you already have your authorization mechanism which controls when user must be redirect to initial page or logon
 * you can skip this class.
 */
@Named
@SessionScoped
@Specializes
public class LogonMB extends AdminSession implements Serializable {

    private String usuarioAtual;
    private String email;
    private String password;
    private boolean relembrar;


    public void login() throws IOException {
        usuarioAtual = email;
        adicionaMensagem("Logado com sucesso como <b>" + email + "</b>");
        Faces.getExternalContext().getFlash().setKeepMessages(true);
        Faces.redirect("index.xhtml");
    }

    @Override
    public boolean isLoggedIn() {

        return usuarioAtual != null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRelembrar() {
        return relembrar;
    }

    public void setRelembrar(boolean relembrar) {
        this.relembrar = relembrar;
    }

    public String getUsuarioAtual() {
        return usuarioAtual;
    }

    public void setUsuarioAtual(String usuarioAtual) {
        this.usuarioAtual = usuarioAtual;
    }
}
