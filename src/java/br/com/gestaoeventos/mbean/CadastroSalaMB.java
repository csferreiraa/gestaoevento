/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.mbean;

import br.com.gestaoeventos.bean.Unidade;
import br.com.gestaoeventos.fachada.CadastroSalaFachada;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author cleiton
 */
@ManagedBean(name = "cadastroSalaMB")
@SessionScoped
public class CadastroSalaMB {
    
    private String nomeSala;
    private Integer capacidadeMaxima;
    private String observacaoSala;
    private Unidade unidadeSala;
    private Integer idUnidade;
    
    private List<Unidade> lstUnidade = new ArrayList<Unidade>();
    
    @EJB
    private CadastroSalaFachada cadastroSalaFachada;
 
    public void iniciaCadastroSala(){
        
        Unidade unidade = new Unidade();
        
        System.out.println("Nome sala " + getNomeSala());
        System.out.println("Capacidade " + getCapacidadeMaxima());
        System.out.println("Observacao " + getObservacaoSala());
        System.out.println("Unidade " + getIdUnidade());
    }
    
    /**
     * Recuperar Unidade. Metodo responsavel por recuperar as Unidade existentes
     */
    public void recuperaListaUnidadeListener(){
        setLstUnidade(cadastroSalaFachada.retrieveUnidadeSalaFachada());
    }
    
    /**
     * Redirecionar para Cadastro de Sala. Metodo responsavel por direcionar
     * o usuario logado para a pagina de cadastro de Sala
     * 
     * @return String
     */
    public String returnPageCadastrarSala(){
        return "/pages/cadastro/sala/cadastroSala.xhtml?faces-redirect=true";
    }

    /**
     * @return the nomeSala
     */
    public String getNomeSala() {
        return nomeSala;
    }

    /**
     * @param nomeSala the nomeSala to set
     */
    public void setNomeSala(String nomeSala) {
        this.nomeSala = nomeSala;
    }

    /**
     * @return the capacidadeMaxima
     */
    public Integer getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    /**
     * @param capacidadeMaxima the capacidadeMaxima to set
     */
    public void setCapacidadeMaxima(Integer capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
    }

    /**
     * @return the observacaoSala
     */
    public String getObservacaoSala() {
        return observacaoSala;
    }

    /**
     * @param observacaoSala the observacaoSala to set
     */
    public void setObservacaoSala(String observacaoSala) {
        this.observacaoSala = observacaoSala;
    }

    /**
     * @return the unidadeSala
     */
    public Unidade getUnidadeSala() {
        return unidadeSala;
    }

    /**
     * @param unidadeSala the unidadeSala to set
     */
    public void setUnidadeSala(Unidade unidadeSala) {
        this.unidadeSala = unidadeSala;
    }

    /**
     * @return the lstUnidade
     */
    public List<Unidade> getLstUnidade() {
        return lstUnidade;
    }

    /**
     * @param lstUnidade the lstUnidade to set
     */
    public void setLstUnidade(List<Unidade> lstUnidade) {
        this.lstUnidade = lstUnidade;
    }

    /**
     * @return the idUnidade
     */
    public Integer getIdUnidade() {
        return idUnidade;
    }

    /**
     * @param idUnidade the idUnidade to set
     */
    public void setIdUnidade(Integer idUnidade) {
        this.idUnidade = idUnidade;
    }

    
    
    
    
}
