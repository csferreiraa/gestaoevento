/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.bean;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author godoyeve
 */
@Entity
@Table(name = "unidade", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nome_unidade"})})
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
    private Integer idUnidade;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "nome_unidade", nullable = false, length = 20)
    private String nomeUnidade;
    @Size(max = 200)
    @Column(name = "observacao_unidade", length = 200)
    private String observacaoUnidade;

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
