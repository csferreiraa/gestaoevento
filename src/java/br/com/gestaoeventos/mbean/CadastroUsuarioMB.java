/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.mbean;

import br.com.gestaoeventos.bean.Grupos;
import br.com.gestaoeventos.bean.Usuario;
import br.com.gestaoeventos.fachada.CadastroUsuarioFachada;
import br.com.gestaoeventos.servicos.Converter;
import br.com.gestaoeventos.servicos.GeraSenha;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author cleiton
 */
@ManagedBean(name = "cadastroUsuarioMB")
@SessionScoped
public class CadastroUsuarioMB implements Serializable{

    private List<Grupos> lstGrupos = new ArrayList<Grupos>();
    
    @EJB
    private CadastroUsuarioFachada cadastrarUsuarioFachada;
    @EJB
    private Converter converter;
    @EJB
    private GeraSenha geraSenha;
    
    
    // Montando Usuario
    private String nomeCompleto;
    private String email;
    //private Grupos grupos;
    private Integer idGrupo;
    private String senhaGerada;
    
    
    /**
     * Creates a new instance of CadastroUsuarioMB
     */
    public CadastroUsuarioMB() {

    }

    
    /**Recuperar Grupos. Metodo responsavel por recuperar os Grupos existentes
     * 
     */
    public void recuperaListaGruposListener(){
        setLstGrupos(cadastrarUsuarioFachada.retrieveGruposFachada());
        System.out.println("Tamanho da lista " + getLstGrupos().size());
        
        for(Grupos g : getLstGrupos()){
            System.out.println("ID " + g.getIdGrupo() + " Nome Grupo " + g.getDescricaoGrupo());
        }
        
    }
    
    /**
     * Inicio de Cadastro de Usuario. Esse metodo permite cadastrar usuarios por
     * um Administrador
     * 
     */
    public void iniciaCadastroUsuario(){
        Usuario us = new Usuario();
        
        us.setNomeCompleto(getNomeCompleto().toUpperCase());
        us.setEmail(getEmail());
        
        Grupos grupos = new Grupos();
        grupos.setIdGrupo(getIdGrupo());
        us.setGrupos(grupos);
        
        setSenhaGerada(geraSenha.geraSenhaUsuario());
        us.setSenha(converter.stringToMD5(getSenhaGerada()));
        
        us.setDataInicioCadastro(new Date());
        
        System.out.println("Senha gerada " + getSenhaGerada());
        
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso",  "Cadastro do usuário " + getNomeCompleto().toUpperCase() + " realizado") );
        

        try {
            cadastrarUsuarioFachada.cadastrarUsuarioFachada(us);
        } catch (Exception ex) {
            Logger.getLogger(CadastroUsuarioMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    /**
     * Redirecionar para Cadastro de Usuario. Metodo responsavel por direcionar
     * o usuario logado para a pagina de cadastro de novos usuarios
     * 
     * @return String
     */
    public String returnPageCadastroUsuario(){
        return "/pages/cadastro/usuario/cadastroUsuario.xhtml?faces-redirect=true";
    }

    /**
     * @return the grupos

    public Grupos getGrupos() {
        return grupos;
    }     */

    /**
     * @param grupos the grupos to set
     
    public void setGrupos(Grupos grupos) {
        this.grupos = grupos;
    }*/

    /**
     * @return the lstGrupos
     */
    public List<Grupos> getLstGrupos() {
        return lstGrupos;
    }

    /**
     * @param lstGrupos the lstGrupos to set
     */
    public void setLstGrupos(List<Grupos> lstGrupos) {
        this.lstGrupos = lstGrupos;
    }

    /**
     * @return the idGrupo
     */
    public Integer getIdGrupo() {
        return idGrupo;
    }

    /**
     * @param idGrupo the idGrupo to set
     */
    public void setIdGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
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
     * @return the senhaGerada
     */
    public String getSenhaGerada() {
        return senhaGerada;
    }

    /**
     * @param senhaGerada the senhaGerada to set
     */
    public void setSenhaGerada(String senhaGerada) {
        this.senhaGerada = senhaGerada;
    }


    
    
}
