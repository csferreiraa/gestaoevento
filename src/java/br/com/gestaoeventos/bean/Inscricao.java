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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author godoyeve
 */
@Entity
@Table(name = "inscricao")
@SequenceGenerator(name= "sq_inscricao_evento", allocationSize = 1, sequenceName = "public.sq_inscricao_evento") 
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Inscricao.findAll", query = "SELECT i FROM Inscricao i"),
    @NamedQuery(name = "Inscricao.findByIdInscricao", query = "SELECT i FROM Inscricao i WHERE i.idInscricao = :idInscricao"),
    @NamedQuery(name = "Inscricao.findByPresencaAluno", query = "SELECT i FROM Inscricao i WHERE i.presencaAluno = :presencaAluno"),
    @NamedQuery(name = "Inscricao.findByDataEmailInscricao", query = "SELECT i FROM Inscricao i WHERE i.dataEmailInscricao = :dataEmailInscricao")})
public class Inscricao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_inscricao", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_inscricao_evento") 
    private Integer idInscricao;
    @Column(name = "presenca_aluno")
    private Character presencaAluno;
    @Column(name = "data_email_inscricao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEmailInscricao;
    @JoinColumn(name = "id_usuario_aluno", referencedColumnName = "id_usuario", nullable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;
    @JoinColumn(name = "id_evento", referencedColumnName = "id_evento", nullable = false)
    @ManyToOne(optional = false)
    private Evento evento;

    public Inscricao() {
    }

    public Inscricao(Integer idInscricao) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idInscricao != null ? idInscricao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inscricao)) {
            return false;
        }
        Inscricao other = (Inscricao) object;
        if ((this.idInscricao == null && other.idInscricao != null) || (this.idInscricao != null && !this.idInscricao.equals(other.idInscricao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.gestaoeventos.bean.Inscricao[ idInscricao=" + idInscricao + " ]";
    }
    
}
