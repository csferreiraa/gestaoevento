/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.bean;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cleiton
 */
@Entity
@Table(name = "grupo_usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrupoUsuario.findAll", query = "SELECT g FROM GrupoUsuario g"),
    @NamedQuery(name = "GrupoUsuario.findByEmail", query = "SELECT g FROM GrupoUsuario g WHERE g.grupoUsuarioPK.email = :email"),
    @NamedQuery(name = "GrupoUsuario.findByNomeGrupo", query = "SELECT g FROM GrupoUsuario g WHERE g.grupoUsuarioPK.nomeGrupo = :nomeGrupo")})
public class GrupoUsuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GrupoUsuarioPK grupoUsuarioPK;
    @JoinColumn(name = "email", referencedColumnName = "email", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;

    public GrupoUsuario() {
    }

    public GrupoUsuario(GrupoUsuarioPK grupoUsuarioPK) {
        this.grupoUsuarioPK = grupoUsuarioPK;
    }

    public GrupoUsuario(String email, String nomeGrupo) {
        this.grupoUsuarioPK = new GrupoUsuarioPK(email, nomeGrupo);
    }

    public GrupoUsuarioPK getGrupoUsuarioPK() {
        return grupoUsuarioPK;
    }

    public void setGrupoUsuarioPK(GrupoUsuarioPK grupoUsuarioPK) {
        this.grupoUsuarioPK = grupoUsuarioPK;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (grupoUsuarioPK != null ? grupoUsuarioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrupoUsuario)) {
            return false;
        }
        GrupoUsuario other = (GrupoUsuario) object;
        if ((this.grupoUsuarioPK == null && other.grupoUsuarioPK != null) || (this.grupoUsuarioPK != null && !this.grupoUsuarioPK.equals(other.grupoUsuarioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.gestaoeventos.bean.GrupoUsuario[ grupoUsuarioPK=" + grupoUsuarioPK + " ]";
    }
    
}
