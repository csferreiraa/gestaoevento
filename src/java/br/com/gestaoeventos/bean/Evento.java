/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.bean;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author godoyeve
 */
@Entity
@Table(name = "evento", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nome_evento", "data_evento"}),
    @UniqueConstraint(columnNames = {"id_sala", "data_evento", "horario_inicio"})})
@SequenceGenerator(name = "sq_evento_universidade", allocationSize = 1, sequenceName = "public.sq_evento_universidade")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Evento.findAll", query = "SELECT e FROM Evento e"),
    @NamedQuery(name = "Evento.findByIdEvento", query = "SELECT e FROM Evento e WHERE e.idEvento = :idEvento"),
    @NamedQuery(name = "Evento.findByNomeEvento", query = "SELECT e FROM Evento e WHERE e.nomeEvento = :nomeEvento"),
    @NamedQuery(name = "Evento.findByNomePalestrante", query = "SELECT e FROM Evento e WHERE e.nomePalestrante = :nomePalestrante"),
    @NamedQuery(name = "Evento.findByDataEvento", query = "SELECT e FROM Evento e WHERE e.dataEvento = :dataEvento"),
    @NamedQuery(name = "Evento.findByHorarioInicio", query = "SELECT e FROM Evento e WHERE e.horarioInicio = :horarioInicio"),
    @NamedQuery(name = "Evento.findByDuracaoEvento", query = "SELECT e FROM Evento e WHERE e.duracaoEvento = :duracaoEvento"),
    @NamedQuery(name = "Evento.findByObservacaoEvento", query = "SELECT e FROM Evento e WHERE e.observacaoEvento = :observacaoEvento")})
public class Evento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_evento", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_evento_universidade")
    private Integer idEvento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "nome_evento", nullable = false, length = 20)
    private String nomeEvento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nome_palestrante", nullable = false, length = 50)
    private String nomePalestrante;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_evento", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataEvento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "horario_inicio", nullable = false)
    private int horarioInicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "duracao_evento", nullable = false)
    private int duracaoEvento;
    @Size(max = 200)
    @Column(name = "observacao_evento", length = 200)
    private String observacaoEvento;
    @JoinColumn(name = "id_sala", referencedColumnName = "id_sala", nullable = false)
    @ManyToOne(optional = false)
    private Sala sala;
    @JoinColumn(name = "id_curso", referencedColumnName = "id_curso", nullable = false)
    @ManyToOne(optional = false)
    private Curso curso;

    public Evento() {
    }

    public Evento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public Evento(Integer idEvento, String nomeEvento, String nomePalestrante, Date dataEvento, int horarioInicio, int duracaoEvento) {
        this.idEvento = idEvento;
        this.nomeEvento = nomeEvento;
        this.nomePalestrante = nomePalestrante;
        this.dataEvento = dataEvento;
        this.horarioInicio = horarioInicio;
        this.duracaoEvento = duracaoEvento;
    }

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public String getNomeEvento() {
        return nomeEvento;
    }

    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

    public String getNomePalestrante() {
        return nomePalestrante;
    }

    public void setNomePalestrante(String nomePalestrante) {
        this.nomePalestrante = nomePalestrante;
    }

    public Date getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(Date dataEvento) {
        this.dataEvento = dataEvento;
    }

    public int getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(int horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public int getDuracaoEvento() {
        return duracaoEvento;
    }

    public void setDuracaoEvento(int duracaoEvento) {
        this.duracaoEvento = duracaoEvento;
    }

    public String getObservacaoEvento() {
        return observacaoEvento;
    }

    public void setObservacaoEvento(String observacaoEvento) {
        this.observacaoEvento = observacaoEvento;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEvento != null ? idEvento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Evento)) {
            return false;
        }
        Evento other = (Evento) object;
        if ((this.idEvento == null && other.idEvento != null) || (this.idEvento != null && !this.idEvento.equals(other.idEvento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.gestaoeventos.bean.Evento[ idEvento=" + idEvento + " ]";
    }

}
