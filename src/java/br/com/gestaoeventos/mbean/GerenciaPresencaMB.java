/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.mbean;

import br.com.gestaoeventos.bean.Evento;
import br.com.gestaoeventos.bean.Inscricao;
import br.com.gestaoeventos.bean.InscricaoAux;
import br.com.gestaoeventos.exceptions.EventoNaoEncontradoException;
import br.com.gestaoeventos.exceptions.InscricaoInexistenteException;
import br.com.gestaoeventos.fachada.GerenciaPresencaFachada;
import br.com.gestaoeventos.servicos.Converter;
import java.util.ArrayList;
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
@ManagedBean(name = "gerenciaPresencaMB")
@SessionScoped
public class GerenciaPresencaMB {
    
    // Eventos dentro do peridio de 15 dias na data atual.
    private List<Evento> lstEvento = new ArrayList<Evento>();
    
    // Inscricoes dos Eventos
    private List<Inscricao> lstInscricao = new ArrayList<Inscricao>();
    
    // Evento Selecionado na pagina pesenca.xhtml
    private Integer idEvento;
    
    @EJB 
    private GerenciaPresencaFachada gerenciaPresencaFachada;
    
    @EJB
    private Converter conveter;
    
    // Lista de Inscricoes selecionas
    private List<Inscricao> lstInscricaoSelecionada = new ArrayList<Inscricao>();
    
    
    /**
     * Atualizar Presença no Evento. Metodo resposavel por iniciar o processo
     * de atualização da presença.
     */
    public void atualizarPresencaEvento(){
        
        for(Inscricao inscricao : getLstInscricaoSelecionada()){
            
            System.out.println("NOME SELECIONADO " + inscricao.getUsuario().getNomeCompleto());
            
        }
    }
    
    /**
     * Inicia busca das inscricoes. Metodo responsavel por recuperar a lista de
     * Inscricoes para determinado Evento.
     * 
     */
    public void pesquisarInscricoesEvento(){
        
        Evento e = new Evento(getIdEvento());
        
        try {
            setLstInscricao(gerenciaPresencaFachada.recuperaInscricoesEventoFachada(e));
            
            if(!lstInscricao.isEmpty()){
                
                

            }
            
        } catch (InscricaoInexistenteException iie) {
             FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",  iie.getMessage()) );
            
        }
        
        System.out.println("ID DO EVENTO " + getIdEvento());
    }
    
    /**
     * Recuperar Eventos. Metodo responsavel por recuperar lista de eventos para
     * realizar o gerenciamento de presenca.
     * Recupera os Eventos dos ultimos 15 dias a partir da data atual.
     * 
     * @throws br.com.gestaoeventos.exceptions.EventoNaoEncontradoException
     */
    public void recuperaEventosPresencaListener() throws EventoNaoEncontradoException{
        setLstEvento(gerenciaPresencaFachada.recuperaEventosPresencaFachada());
    }

       /**
     * Redirecionar para Gerenciamento de Presenca. Metodo responsavel por direcionar
     * o usuario logado para a pagina de Gerenciamento de Presenca
     * 
     * @return String
     */
    public String returnPageGerenciarPresenca(){
        return "/pages/presenca/registro/presenca.xhtml?faces-redirect=true";
    }

    /**
     * @return the lstEvento
     */
    public List<Evento> getLstEvento() {
        return lstEvento;
    }

    /**
     * @param lstEvento the lstEvento to set
     */
    public void setLstEvento(List<Evento> lstEvento) {
        this.lstEvento = lstEvento;
    }

    /**
     * @return the idEvento
     */
    public Integer getIdEvento() {
        return idEvento;
    }

    /**
     * @param idEvento the idEvento to set
     */
    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    /**
     * @return the lstInscricao
     */
    public List<Inscricao> getLstInscricao() {
        return lstInscricao;
    }

    /**
     * @param lstInscricao the lstInscricao to set
     */
    public void setLstInscricao(List<Inscricao> lstInscricao) {
        this.lstInscricao = lstInscricao;
    }

    /**
     * @return the lstInscricaoSelecionada
     */
    public List<Inscricao> getLstInscricaoSelecionada() {
        return lstInscricaoSelecionada;
    }

    /**
     * @param lstInscricaoSelecionada the lstInscricaoSelecionada to set
     */
    public void setLstInscricaoSelecionada(List<Inscricao> lstInscricaoSelecionada) {
        this.lstInscricaoSelecionada = lstInscricaoSelecionada;
    }



    
    
}
