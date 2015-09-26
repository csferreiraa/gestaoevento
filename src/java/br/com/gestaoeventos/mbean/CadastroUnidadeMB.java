/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.mbean;

import br.com.gestaoeventos.bean.Unidade;
import br.com.gestaoeventos.exceptions.UnidadeExistenteException;
import br.com.gestaoeventos.fachada.CadastroUnidadeFachada;
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
@ManagedBean(name = "cadastroUnidadeMB")
@SessionScoped
public class CadastroUnidadeMB {
 
    
    private String nomeUnidade;
    private String observcaoUnidade;
    
    @EJB
    private CadastroUnidadeFachada cadastroUnidadeFachada;
    
    
    /**
     * Inicio de Cadastro de Unidade. Esse metodo permite cadastrar Unidade por
     * um Administrador
     * 
     */
    public void iniciaCadastroUnidade(){

        Unidade unidade = new Unidade();
        String nomeMsg = getNomeUnidade().toUpperCase();
        unidade.setNomeUnidade(getNomeUnidade().toUpperCase());
        unidade.setObservacaoUnidade(getObservcaoUnidade().toUpperCase());
        
        try {
            cadastroUnidadeFachada.cadastrarUnidadeFachada(unidade);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso",  "Cadastro da unidade " + nomeMsg.trim() + " realizado") );
            setNomeUnidade(null);
            setObservcaoUnidade(null);
        } catch (UnidadeExistenteException uee) {
            setObservcaoUnidade(null);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",  uee.getMessage() + " - Nome: " + getNomeUnidade().toUpperCase()) );
            // Logger.getLogger(CadastroUnidadeMB.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    /**
     * Redirecionar para Cadastro de Unidade. Metodo responsavel por direcionar
     * o usuario logado para a pagina de cadastro de Unidade
     * 
     * @return String
     */
    public String returnPageCadastrarUnidade(){
        return "/pages/cadastro/unidade/cadastroUnidade.xhtml?faces-redirect=true";
    }

    /**
     * @return the nomeUnidade
     */
    public String getNomeUnidade() {
        return nomeUnidade;
    }

    /**
     * @param nomeUnidade the nomeUnidade to set
     */
    public void setNomeUnidade(String nomeUnidade) {
        this.nomeUnidade = nomeUnidade;
    }

    /**
     * @return the observcaoUnidade
     */
    public String getObservcaoUnidade() {
        return observcaoUnidade;
    }

    /**
     * @param observcaoUnidade the observcaoUnidade to set
     */
    public void setObservcaoUnidade(String observcaoUnidade) {
        this.observcaoUnidade = observcaoUnidade;
    }


    
    
}
