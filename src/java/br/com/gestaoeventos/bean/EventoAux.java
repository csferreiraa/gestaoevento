/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.bean;

import java.util.Date;

/**
 *
 * @author cleiton
 */
public class EventoAux{

    private Integer idEvento;
    private String nomeEvento;
    private String nomePalestrante;
    private Date dataEvento;
    private int horarioInicio;
    private int duracaoEvento;
    private String observacaoEvento;
    private Sala sala;
    private Curso curso;
    private String dtEvento;
    private String hrInicio;
    private Integer quantidadeInscricoes;

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
     * @return the nomeEvento
     */
    public String getNomeEvento() {
        return nomeEvento;
    }

    /**
     * @param nomeEvento the nomeEvento to set
     */
    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

    /**
     * @return the nomePalestrante
     */
    public String getNomePalestrante() {
        return nomePalestrante;
    }

    /**
     * @param nomePalestrante the nomePalestrante to set
     */
    public void setNomePalestrante(String nomePalestrante) {
        this.nomePalestrante = nomePalestrante;
    }

    /**
     * @return the dataEvento
     */
    public Date getDataEvento() {
        return dataEvento;
    }

    /**
     * @param dataEvento the dataEvento to set
     */
    public void setDataEvento(Date dataEvento) {
        this.dataEvento = dataEvento;
    }

    /**
     * @return the horarioInicio
     */
    public int getHorarioInicio() {
        return horarioInicio;
    }

    /**
     * @param horarioInicio the horarioInicio to set
     */
    public void setHorarioInicio(int horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    /**
     * @return the duracaoEvento
     */
    public int getDuracaoEvento() {
        return duracaoEvento;
    }

    /**
     * @param duracaoEvento the duracaoEvento to set
     */
    public void setDuracaoEvento(int duracaoEvento) {
        this.duracaoEvento = duracaoEvento;
    }

    /**
     * @return the observacaoEvento
     */
    public String getObservacaoEvento() {
        return observacaoEvento;
    }

    /**
     * @param observacaoEvento the observacaoEvento to set
     */
    public void setObservacaoEvento(String observacaoEvento) {
        this.observacaoEvento = observacaoEvento;
    }

    /**
     * @return the sala
     */
    public Sala getSala() {
        return sala;
    }

    /**
     * @param sala the sala to set
     */
    public void setSala(Sala sala) {
        this.sala = sala;
    }

    /**
     * @return the curso
     */
    public Curso getCurso() {
        return curso;
    }

    /**
     * @param curso the curso to set
     */
    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    /**
     * @return the dtEvento
     */
    public String getDtEvento() {
        return dtEvento;
    }

    /**
     * @param dtEvento the dtEvento to set
     */
    public void setDtEvento(String dtEvento) {
        this.dtEvento = dtEvento;
    }

    /**
     * @return the hrInicio
     */
    public String getHrInicio() {
        return hrInicio;
    }

    /**
     * @param hrInicio the hrInicio to set
     */
    public void setHrInicio(String hrInicio) {
        this.hrInicio = hrInicio;
    }

    /**
     * @return the quantidadeInscricoes
     */
    public Integer getQuantidadeInscricoes() {
        return quantidadeInscricoes;
    }

    /**
     * @param quantidadeInscricoes the quantidadeInscricoes to set
     */
    public void setQuantidadeInscricoes(Integer quantidadeInscricoes) {
        this.quantidadeInscricoes = quantidadeInscricoes;
    }



}
