/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.bean;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
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
@Table(name = "unidade", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nome_unidade"})})
@SequenceGenerator(name= "sq_unidade_universidade", allocationSize = 1, sequenceName = "public.sq_unidade_universidade") 
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Unidade.findAll", query = "SELECT u FROM Unidade u"),
    @NamedQuery(name = "Unidade.findByIdUnidade", query = "SELECT u FROM Unidade u WHERE u.idUnidade = :idUnidade"),
    @NamedQuery(name = "Unidade.findByNomeUnidade", query = "SELECT u FROM Unidade u WHERE u.nomeUnidade = :nomeUnidade"),
    @NamedQuery(name = "Unidade.findByObservacaoUnidade", query = "SELECT u FROM Unidade u WHERE u.observacaoUnidade = :observacaoUnidade")})
public class Unidade implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_unidade", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_unidade_universidade") 
    private Integer idUnidade;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "nome_unidade", nullable = false, length = 20)
    private String nomeUnidade;
    @Size(max = 200)
    @Column(name = "observacao_unidade", length = 200)
    private String observacaoUnidade;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unidade")
    private Collection<Sala> salaCollection;

    public Unidade() {
    }

    public Unidade(Integer idUnidade) {
        this.idUnidade = idUnidade;
    }

    public Unidade(Integer idUnidade, String nomeUnidade) {
        this.idUnidade = idUnidade;
        this.nomeUnidade = nomeUnidade;
    }

    public Integer getIdUnidade() {
        return idUnidade;
    }

    public void setIdUnidade(Integer idUnidade) {
        this.idUnidade = idUnidade;
    }

    public String getNomeUnidade() {
        return nomeUnidade;
    }

    public void setNomeUnidade(String nomeUnidade) {
        this.nomeUnidade = nomeUnidade;
    }

    public String getObservacaoUnidade() {
        return observacaoUnidade;
    }

    public void setObservacaoUnidade(String observacaoUnidade) {
        this.observacaoUnidade = observacaoUnidade;
    }

    @XmlTransient
    public Collection<Sala> getSalaCollection() {
        return salaCollection;
    }

    public void setSalaCollection(Collection<Sala> salaCollection) {
        this.salaCollection = salaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUnidade != null ? idUnidade.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Unidade)) {
            return false;
        }
        Unidade other = (Unidade) object;
        if ((this.idUnidade == null && other.idUnidade != null) || (this.idUnidade != null && !this.idUnidade.equals(other.idUnidade))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.gestaoeventos.bean.Unidade[ idUnidade=" + idUnidade + " ]";
    }
    
}
