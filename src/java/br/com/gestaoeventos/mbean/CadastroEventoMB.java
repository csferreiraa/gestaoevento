/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.mbean;

import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author cleiton
 */
@ManagedBean
@RequestScoped
public class CadastroEventoMB {

    private String nomeEvento;
    private String nomePalestrante;
    private Date dataInicio;
    
    
    /**
     * Creates a new instance of CadastroEventoMB
     */
    public CadastroEventoMB() {
    }
    
    /**
     * Redirecionar para Cadastro de Evento. Metodo responsavel por direcionar
     * o usuario logado para a pagina de cadastro de Evento
     * 
     * @return String
     */
    public String returnPageCadastrarEvento(){
        return "/pages/cadastro/evento/cadastroEvento.xhtml?faces-redirect=true";
    }
    
}
