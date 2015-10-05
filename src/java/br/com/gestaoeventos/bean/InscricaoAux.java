/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.bean;

import java.io.Serializable;
import java.util.Date;


public class InscricaoAux implements Serializable {
    
    private Integer idInscricao;
    private Character presencaAluno;
    private Date dataEmailInscricao;
    private Usuario usuario;
    private Evento evento;
    private String dataEventoString;

    public InscricaoAux() {
    }

    public InscricaoAux(Integer idInscricao) {
        this.idInscricao = idInscricao;
    }

    public Integer getIdInscricao() {
        return idInscricao;
    }

    public void setIdInscricao(Integer idInscricao) {
        this.idInscricao = idInscricao;
    }

    public Character getPresencaAluno() {
        return presencaAluno;
    }

    public void setPresencaAluno(Character presencaAluno) {
        this.presencaAluno = presencaAluno;
    }

    public Date getDataEmailInscricao() {
        return dataEmailInscricao;
    }

    public void setDataEmailInscricao(Date dataEmailInscricao) {
        this.dataEmailInscricao = dataEmailInscricao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    /**
     * @return the dataEventoString
     */
    public String getDataEventoString() {
        return dataEventoString;
    }

    /**
     * @param dataEventoString the dataEventoString to set
     */
    public void setDataEventoString(String dataEventoString) {
        this.dataEventoString = dataEventoString;
    }
    
}
