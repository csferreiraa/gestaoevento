/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.mbean;

import br.com.gestaoeventos.bean.Grupos;
import br.com.gestaoeventos.bean.Usuario;
import br.com.gestaoeventos.fachada.CadastroUsuarioFachada;
import br.com.gestaoeventos.fachada.LoginFachada;
import br.com.gestaoeventos.servicos.Converter;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Cleiton
 */
@ManagedBean(name = "loginMB")
@SessionScoped
public class LoginMB implements Serializable {

    private String email;
    private String nomeCompleto;
    private String password;
    private Usuario usuario;

    private Boolean userLogado;
    private HttpSession session;

    @EJB
    private LoginFachada loginFachada;
    @EJB
    private CadastroUsuarioFachada cadastrarUsuarioFachada;
    @EJB
    private Converter converter;

    private Boolean adminUsr;
    private Boolean coordUsr;
    private Boolean profUsr;
    private Boolean aluUsr;

    private static final String ADMINISTRADOR = "ADMINISTRADOR";
    private static final String COORDENADOR = "COORDENADOR";
    private static final String PROFESSOR = "PROFESSOR";
    private static final String ALUNO = "ALUNO";

    
    /**
     * Metodo responsavel por realizar o login e redirecionar para tala inicio
     *
     * @return String
     */
    public String efetuarLoginMB() {

        try {
            this.setUsuario(loginFachada.efetuarLoginFachada(getEmail(), getPassword()));
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) facesContext.getCurrentInstance().getExternalContext().getRequest();
            session = request.getSession();
            session.setAttribute("userLoggedIn", this.getUsuario());
            setUserLogado(Boolean.TRUE);

            // Recupera o Grupo de Acesso para a aplicacao
            validaGrupoAcesso(this.getUsuario());

            return "/pages/inicio.xhtml?faces-redirect=true";
            //setUrlDestino("/pages/inicio.xhtml?faces-redirect=true");
        } catch (LoginException ex) {
            Logger.getLogger(LoginMB.class.getName()).log(Level.SEVERE, null, ex);
            //setUrlDestino("/login.xhtml?faces-redirect=true");
            return "/login.xhtml?faces-redirect=true";
        }

    }

    /**
     * Logout. Metodo responsavel por sair da aplicao/logout
     *
     * @return String
     */
    public String efetuarLogoutMB() {
        try {
            this.setUsuario(null);
            this.setUserLogado(Boolean.FALSE);

            loginFachada.efetuarlogoutFachada();

            return "/login.xhtml?faces-redirect=true";

        } catch (LoginException ex) {
            Logger.getLogger(LoginMB.class.getName()).log(Level.SEVERE, null, ex);
            return "/pages/inicio.xhtml?faces-redirect=true";
        }
    }

    /**
     * Recuperar Grupo de Acesso. Metodo responsavel por recuperar o Grupo
     * Usuario
     *
     * @param user
     */
    public void validaGrupoAcesso(Usuario user) {

        if (user.getGrupos().getIdGrupo() == 1) {
            this.setAdminUsr(Boolean.TRUE);
        } else if (user.getGrupos().getIdGrupo() == 2) {
            this.setCoordUsr(Boolean.TRUE);
        } else if (user.getGrupos().getIdGrupo() == 3) {
            this.setProfUsr(Boolean.TRUE);
        } else if (user.getGrupos().getIdGrupo() == 4) {
            this.setAluUsr(Boolean.TRUE);
        }

        /*for(GrupoUsuario grupoUsuario : user.getGrupoUsuarioCollection()){
         if(getADMINISTRADOR().equals(grupoUsuario.getGrupoUsuarioPK().getNomeGrupo())){
         this.setAdminUsr(Boolean.TRUE);
         } else if (getCOORDENADOR().equals(grupoUsuario.getGrupoUsuarioPK().getNomeGrupo())){
         this.setCoordUsr(Boolean.TRUE);
         } else if (getPROFESSOR().equals(grupoUsuario.getGrupoUsuarioPK().getNomeGrupo())){
         this.setProfUsr(Boolean.TRUE);
         } else if (getALUNO().equals(grupoUsuario.getGrupoUsuarioPK().getNomeGrupo())){
         this.setAluUsr(Boolean.TRUE);
         }
   
         }*/
    }

    /**
     * Retorno ao inicio. Metodo responsavel por retornar ao inicio da aplicacao
     *
     * @return
     */
    public String retornaInicio() {
        return "/pages/inicio.xhtml?faces-redirect=true";
    }

    /**
     * Cadastro de Aluno. Metodo responsavel por preparar o cadastro de um Aluno
     * atraves do proprio Aluno.
     *
     * @return String
     */
    public String cadastrarAlunoPorAluno() {

        try {
            Usuario us = new Usuario();
            us.setNomeCompleto(this.getNomeCompleto().toUpperCase().trim());
            us.setEmail(this.getEmail().toLowerCase().trim());
            us.setSenha(converter.stringToMD5(this.getPassword().trim()));
            us.setDataInicioCadastro(new Date());
            us.setDataFimCadastro(null);

            // FIXADO
            Grupos grupos = new Grupos(4, "ALUNO");

            us.setGrupos(grupos);

            // Inicia processo de gravacao
            cadastrarUsuarioFachada.cadastrarUsuarioFachada(us);

            return "/login.xhtml?faces-redirect=true";
        } catch (Exception e) {
            return "";
        }

    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the nomeCompleto
     */
    public String getNomeCompleto() {
        return nomeCompleto;
    }

    /**
     * @param nomeCompleto the nomeCompleto to set
     */
    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the userLogado
     */
    public Boolean getUserLogado() {
        return userLogado;
    }

    /**
     * @param userLogado the userLogado to set
     */
    public void setUserLogado(Boolean userLogado) {
        this.userLogado = userLogado;
    }

    /**
     * @return the adminUsr
     */
    public Boolean getAdminUsr() {
        return adminUsr;
    }

    /**
     * @param adminUsr the adminUsr to set
     */
    public void setAdminUsr(Boolean adminUsr) {
        this.adminUsr = adminUsr;
    }

    /**
     * @return the coordUsr
     */
    public Boolean getCoordUsr() {
        return coordUsr;
    }

    /**
     * @param coordUsr the coordUsr to set
     */
    public void setCoordUsr(Boolean coordUsr) {
        this.coordUsr = coordUsr;
    }

    /**
     * @return the profUsr
     */
    public Boolean getProfUsr() {
        return profUsr;
    }

    /**
     * @param profUsr the profUsr to set
     */
    public void setProfUsr(Boolean profUsr) {
        this.profUsr = profUsr;
    }

    /**
     * @return the aluUsr
     */
    public Boolean getAluUsr() {
        return aluUsr;
    }

    /**
     * @param aluUsr the aluUsr to set
     */
    public void setAluUsr(Boolean aluUsr) {
        this.aluUsr = aluUsr;
    }

    /**
     * @return the ADMINISTRADOR
     */
    public static String getADMINISTRADOR() {
        return ADMINISTRADOR;
    }

    /**
     * @return the COORDENADOR
     */
    public static String getCOORDENADOR() {
        return COORDENADOR;
    }

    /**
     * @return the PROFESSOR
     */
    public static String getPROFESSOR() {
        return PROFESSOR;
    }

    /**
     * @return the ALUNO
     */
    public static String getALUNO() {
        return ALUNO;
    }    
    
}