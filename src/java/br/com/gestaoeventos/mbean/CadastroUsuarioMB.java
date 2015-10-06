/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.mbean;

import br.com.gestaoeventos.bean.Curso;
import br.com.gestaoeventos.bean.Grupos;
import br.com.gestaoeventos.bean.Usuario;
import br.com.gestaoeventos.exceptions.UsuarioExistenteException;
import br.com.gestaoeventos.fachada.CadastroUsuarioFachada;
import br.com.gestaoeventos.servicos.Converter;
import br.com.gestaoeventos.servicos.GeraSenha;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    private Boolean mostraSenha = Boolean.TRUE;
    
    
    // Montando Usuario
    private String nomeCompleto;
    private String email;
    //private Grupos grupos;
    private Integer idGrupo;
    private String senhaGerada;
    
    private Boolean liberaComboCurso;
    private List<Curso> lstCurso = new ArrayList<Curso>();
    private Integer idCurso;
    
    
    /**
     * Creates a new instance of CadastroUsuarioMB
     */
    public CadastroUsuarioMB() {

    }

    
    /**
     * Recuperar Lista de Cursos. Caso o Grupo selecionado seja ALUNO, permite selecionar um curso
     * PARA USO DO selectGrp da pagina de cadastro de usuario
     
    public void recuperaListCursosAluno(){
        if(getIdGrupo().equals(4)){
            setLiberaComboCurso(Boolean.TRUE);
        } else {
            setLiberaComboCurso(Boolean.FALSE);
        }
    }*/
    
    /**
     * Recuperar Grupos. Metodo responsavel por recuperar os Grupos existentes e
     * Lista de Cursos para cadastro de Aluno
     */
    public void recuperaListaGruposListener(){
        setNomeCompleto(null);
        setEmail(null);        
        setLstGrupos(cadastrarUsuarioFachada.retrieveGruposFachada());  
        setLstCurso(cadastrarUsuarioFachada.recuperaCursoCadastroAlunoFacahada());
    }
    
    /**
     * Inicio de Cadastro de Usuario. Esse metodo permite cadastrar usuarios por
     * um Administrador
     * 
     */
    public void iniciaCadastroUsuario(){
        Usuario us = new Usuario();
        
        us.setNomeCompleto(getNomeCompleto().toUpperCase());
        us.setEmail(getEmail().toLowerCase().trim());
        
        Grupos grupos = new Grupos();
        grupos.setIdGrupo(getIdGrupo());
        us.setGrupos(grupos);
        
        setSenhaGerada(geraSenha.geraSenhaUsuario());
        us.setSenha(converter.stringToMD5(getSenhaGerada()));
        
        us.setDataInicioCadastro(new Date());
        
        try {
            cadastrarUsuarioFachada.cadastrarUsuarioFachada(us);
            setMostraSenha(Boolean.TRUE);

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso",  "Cadastro do Usuário " + us.getNomeCompleto().trim() + " realizado. Senha " + getSenhaGerada()) );
            
        } catch (UsuarioExistenteException uee) {
            setMostraSenha(Boolean.FALSE);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",  uee.getMessage() + " - E-mail: " + getEmail().toLowerCase()) );
            //Logger.getLogger(CadastroUsuarioMB.class.getName()).log(Level.SEVERE, null, ex);
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

    /**
     * @return the mostraSenha
     */
    public Boolean getMostraSenha() {
        return mostraSenha;
    }

    /**
     * @param mostraSenha the mostraSenha to set
     */
    public void setMostraSenha(Boolean mostraSenha) {
        this.mostraSenha = mostraSenha;
    }

    /**
     * @return the liberaComboCurso
     */
    public Boolean getLiberaComboCurso() {
        return liberaComboCurso;
    }

    /**
     * @param liberaComboCurso the liberaComboCurso to set
     */
    public void setLiberaComboCurso(Boolean liberaComboCurso) {
        this.liberaComboCurso = liberaComboCurso;
    }

    /**
     * @return the lstCurso
     */
    public List<Curso> getLstCurso() {
        return lstCurso;
    }

    /**
     * @param lstCurso the lstCurso to set
     */
    public void setLstCurso(List<Curso> lstCurso) {
        this.lstCurso = lstCurso;
    }

    /**
     * @return the idCurso
     */
    public Integer getIdCurso() {
        return idCurso;
    }

    /**
     * @param idCurso the idCurso to set
     */
    public void setIdCurso(Integer idCurso) {
        this.idCurso = idCurso;
    }



    
    
}
