/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.mbean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


/**
 *
 * @author cleiton
 */
@ManagedBean(name = "cadastroUnidadeMB")
@SessionScoped
public class CadastroUnidadeMB {
 
    
    private String nomeUnidade;
    private String descricaoUnidade;
    
    
    
    /**
     * Inicio de Cadastro de Unidade. Esse metodo permite cadastrar Unidade por
     * um Administrador
     * 
     */
    public void iniciaCadastroUnidade(){
        System.out.println("OBA UNIDADEEE");
        System.out.println("Nome unidade " + getNomeUnidade() + " Descricao " + getDescricaoUnidade());
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
     * @return the descricaoUnidade
     */
    public String getDescricaoUnidade() {
        return descricaoUnidade;
    }

    /**
     * @param descricaoUnidade the descricaoUnidade to set
     */
    public void setDescricaoUnidade(String descricaoUnidade) {
        this.descricaoUnidade = descricaoUnidade;
    }
    
    
    
}
