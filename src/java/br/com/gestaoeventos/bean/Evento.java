/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.bean;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author godoyeve
 */
@Entity
@Table(name = "evento", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nome_evento"}),
    @UniqueConstraint(columnNames = {"data_inicio", "data_fim", "horario_evento", "id_sala"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Evento.findAll", query = "SELECT e FROM Evento e"),
    @NamedQuery(name = "Evento.findByIdEvento", query = "SELECT e FROM Evento e WHERE e.idEvento = :idEvento"),
    @NamedQuery(name = "Evento.findByNomeEvento", query = "SELECT e FROM Evento e WHERE e.nomeEvento = :nomeEvento"),
    @NamedQuery(name = "Evento.findByNomePalestrante", query = "SELECT e FROM Evento e WHERE e.nomePalestrante = :nomePalestrante"),
    @NamedQuery(name = "Evento.findByDataInicio", query = "SELECT e FROM Evento e WHERE e.dataInicio = :dataInicio"),
    @NamedQuery(name = "Evento.findByDataFim", query = "SELECT e FROM Evento e WHERE e.dataFim = :dataFim"),
    @NamedQuery(name = "Evento.findByHorarioEvento", query = "SELECT e FROM Evento e WHERE e.horarioEvento = :horarioEvento"),
    @NamedQuery(name = "Evento.findByObservacaoEvento", query = "SELECT e FROM Evento e WHERE e.observacaoEvento = :observacaoEvento")})
public class Evento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_evento", nullable = false)
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
    @Column(name = "data_inicio", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataInicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_fim", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataFim;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "horario_evento", nullable = false, length = 10)
    private String horarioEvento;
    @Size(max = 200)
    @Column(name = "observacao_evento", length = 200)
    private String observacaoEvento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evento")
    private Collection<Inscricao> inscricaoCollection;
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

    public Evento(Integer idEvento, String nomeEvento, String nomePalestrante, Date dataInicio, Date dataFim, String horarioEvento) {
        this.idEvento = idEvento;
        this.nomeEvento = nomeEvento;
        this.nomePalestrante = nomePalestrante;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.horarioEvento = horarioEvento;
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

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public String getHorarioEvento() {
        return horarioEvento;
    }

    public void setHorarioEvento(String horarioEvento) {
        this.horarioEvento = horarioEvento;
    }

    public String getObservacaoEvento() {
        return observacaoEvento;
    }

    public void setObservacaoEvento(String observacaoEvento) {
        this.observacaoEvento = observacaoEvento;
    }

    @XmlTransient
    public Collection<Inscricao> getInscricaoCollection() {
        return inscricaoCollection;
    }

    public void setInscricaoCollection(Collection<Inscricao> inscricaoCollection) {
        this.inscricaoCollection = inscricaoCollection;
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
