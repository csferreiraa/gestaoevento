/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.mbean;

import br.com.gestaoeventos.bean.Grupos;
import br.com.gestaoeventos.fachada.CadastroUsuarioFachada;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

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
    
    /**
     * Creates a new instance of CadastroUsuarioMB
     */
    public CadastroUsuarioMB() {

    }

    public void fazCoisaUm() {
        System.out.println("ssssssss");
    }
    
    public void fazCoisaDois() throws Exception {
        
        try {
            throw new Exception();
        } catch (Exception e) {
            System.out.println("Deu ERRO, nao vai exibir a chamada do fazCoisaUm()");
        }
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
     * Redirecionar para Cadastro de Usuario. Metodo responsavel por direcionar
     * o usuario logado para a pagina de cadastro de novos usuarios
     * 
     * @return String
     */
    public String returnPageCadastroUsuario(){
        return "/pages/cadastro/usuario/cadastroUsuario.xhtml?faces-redirect=true";
    }

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


    
    
}
