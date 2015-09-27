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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
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
@Table(name = "sala", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nome_sala", "id_unidade"})})
@SequenceGenerator(name= "sq_sala_universidade", allocationSize = 1, sequenceName = "public.sq_sala_universidade") 
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sala.findAll", query = "SELECT s FROM Sala s"),
    @NamedQuery(name = "Sala.findByIdSala", query = "SELECT s FROM Sala s WHERE s.idSala = :idSala"),
    @NamedQuery(name = "Sala.findByNomeSala", query = "SELECT s FROM Sala s WHERE s.nomeSala = :nomeSala"),
    @NamedQuery(name = "Sala.findByCapacidadeMaximaPessoas", query = "SELECT s FROM Sala s WHERE s.capacidadeMaximaPessoas = :capacidadeMaximaPessoas"),
    @NamedQuery(name = "Sala.findByObservacaoSala", query = "SELECT s FROM Sala s WHERE s.observacaoSala = :observacaoSala")})
public class Sala implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_sala", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_sala_universidade") 
    private Integer idSala;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "nome_sala", nullable = false, length = 20)
    private String nomeSala;
    @Basic(optional = false)
    @NotNull
    @Column(name = "capacidade_maxima_pessoas", nullable = false)
    private int capacidadeMaximaPessoas;
    @Size(max = 200)
    @Column(name = "observacao_sala", length = 200)
    private String observacaoSala;
    @JoinColumn(name = "id_unidade", referencedColumnName = "id_unidade", nullable = false)
    @ManyToOne(optional = false)
    private Unidade unidade;

    public Sala() {
    }

    public Sala(Integer idSala) {
        this.idSala = idSala;
    }

    public Sala(Integer idSala, String nomeSala, int capacidadeMaximaPessoas) {
        this.idSala = idSala;
        this.nomeSala = nomeSala;
        this.capacidadeMaximaPessoas = capacidadeMaximaPessoas;
    }

    public Integer getIdSala() {
        return idSala;
    }

    public void setIdSala(Integer idSala) {
        this.idSala = idSala;
    }

    public String getNomeSala() {
        return nomeSala;
    }

    public void setNomeSala(String nomeSala) {
        this.nomeSala = nomeSala;
    }

    public int getCapacidadeMaximaPessoas() {
        return capacidadeMaximaPessoas;
    }

    public void setCapacidadeMaximaPessoas(int capacidadeMaximaPessoas) {
        this.capacidadeMaximaPessoas = capacidadeMaximaPessoas;
    }

    public String getObservacaoSala() {
        return observacaoSala;
    }

    public void setObservacaoSala(String observacaoSala) {
        this.observacaoSala = observacaoSala;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSala != null ? idSala.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sala)) {
            return false;
        }
        Sala other = (Sala) object;
        if ((this.idSala == null && other.idSala != null) || (this.idSala != null && !this.idSala.equals(other.idSala))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.gestaoeventos.bean.Sala[ idSala=" + idSala + " ]";
    }
    
}
