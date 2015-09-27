/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.mbean;

import br.com.gestaoeventos.bean.Curso;
import br.com.gestaoeventos.exceptions.CursoExistenteException;
import br.com.gestaoeventos.fachada.CadastroCursoFachada;
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
@ManagedBean(name = "cadastroCursoMB")
@SessionScoped
public class CadastroCursoMB {

    private String nomeCurso;
    private String observacaoCurso;
    
    @EJB
    private CadastroCursoFachada cadastroCursoFachada;
    
    
    /**
     * Creates a new instance of CadastroCursoMB
     */
    public CadastroCursoMB() {
    }
    
    
    
    /**
     * Cadastro de Curso. Metodo responsavel por iniciar o cadastro de curso
     */
    public void iniciaCadastroCurso(){
        
        Curso curso = new Curso();
        curso.setNomeCurso(getNomeCurso().trim().toUpperCase());
        curso.setObservacaoCurso(getObservacaoCurso());
        
        
        try {
            cadastroCursoFachada.cadastrarCursoFachada(curso);
            
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso",  "Curso " + curso.getNomeCurso() + " foi cadastrado.") );

            setObservacaoCurso(null);
            
        } catch (CursoExistenteException cee) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",  cee.getMessage() + " - Curso: " + curso.getNomeCurso()) );
        }
       
        
    }
    
    
    /**
     * Redirecionar para Cadastro de Curso. Metodo responsavel por direcionar
     * o usuario logado para a pagina de cadastro de Curso
     * 
     * @return String
     */
    public String returnPageCadastrarCurso(){
        setNomeCurso(null);
        setObservacaoCurso(null);
        return "/pages/cadastro/curso/cadastroCurso.xhtml?faces-redirect=true";
    }

    /**
     * @return the nomeCurso
     */
    public String getNomeCurso() {
        return nomeCurso;
    }

    /**
     * @param nomeCurso the nomeCurso to set
     */
    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    /**
     * @return the observacaoCurso
     */
    public String getObservacaoCurso() {
        return observacaoCurso;
    }

    /**
     * @param observacaoCurso the observacaoCurso to set
     */
    public void setObservacaoCurso(String observacaoCurso) {
        this.observacaoCurso = observacaoCurso;
    }
    
    
    
    
    
}
