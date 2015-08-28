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
import javax.persistence.ManyToMany;
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
@Table(name = "usuario", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_usuario"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByIdUsuario", query = "SELECT u FROM Usuario u WHERE u.idUsuario = :idUsuario"),
    @NamedQuery(name = "Usuario.findByNomeCompleto", query = "SELECT u FROM Usuario u WHERE u.nomeCompleto = :nomeCompleto"),
    @NamedQuery(name = "Usuario.findByEmail", query = "SELECT u FROM Usuario u WHERE u.email = :email"),
    @NamedQuery(name = "Usuario.findBySenha", query = "SELECT u FROM Usuario u WHERE u.senha = :senha"),
    @NamedQuery(name = "Usuario.findByDataInicioCadastro", query = "SELECT u FROM Usuario u WHERE u.dataInicioCadastro = :dataInicioCadastro"),
    @NamedQuery(name = "Usuario.findByDataFimCadastro", query = "SELECT u FROM Usuario u WHERE u.dataFimCadastro = :dataFimCadastro")})
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_usuario", nullable = false)
    private int idUsuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "nome_completo", nullable = false, length = 40)
    private String nomeCompleto;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "email", nullable = false, length = 50)
    private String email;
    @Size(max = 50)
    @Column(name = "senha", length = 50)
    private String senha;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_inicio_cadastro", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataInicioCadastro;
    @Column(name = "data_fim_cadastro")
    @Temporal(TemporalType.DATE)
    private Date dataFimCadastro;
    @ManyToMany(mappedBy = "usuarioCollection")
    private Collection<Curso> cursoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<Inscricao> inscricaoCollection;
    @JoinColumn(name = "id_grupo_usuario", referencedColumnName = "id_grupo", nullable = false)
    @ManyToOne(optional = false)
    private Grupos grupos;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<GrupoUsuario> grupoUsuarioCollection;

    public Usuario() {
    }

    public Usuario(String email) {
        this.email = email;
    }

    public Usuario(String email, int idUsuario, String nomeCompleto, Date dataInicioCadastro) {
        this.email = email;
        this.idUsuario = idUsuario;
        this.nomeCompleto = nomeCompleto;
        this.dataInicioCadastro = dataInicioCadastro;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Date getDataInicioCadastro() {
        return dataInicioCadastro;
    }

    public void setDataInicioCadastro(Date dataInicioCadastro) {
        this.dataInicioCadastro = dataInicioCadastro;
    }

    public Date getDataFimCadastro() {
        return dataFimCadastro;
    }

    public void setDataFimCadastro(Date dataFimCadastro) {
        this.dataFimCadastro = dataFimCadastro;
    }

    @XmlTransient
    public Collection<Curso> getCursoCollection() {
        return cursoCollection;
    }

    public void setCursoCollection(Collection<Curso> cursoCollection) {
        this.cursoCollection = cursoCollection;
    }

    @XmlTransient
    public Collection<Inscricao> getInscricaoCollection() {
        return inscricaoCollection;
    }

    public void setInscricaoCollection(Collection<Inscricao> inscricaoCollection) {
        this.inscricaoCollection = inscricaoCollection;
    }

    public Grupos getGrupos() {
        return grupos;
    }

    public void setGrupos(Grupos grupos) {
        this.grupos = grupos;
    }

    @XmlTransient
    public Collection<GrupoUsuario> getGrupoUsuarioCollection() {
        return grupoUsuarioCollection;
    }

    public void setGrupoUsuarioCollection(Collection<GrupoUsuario> grupoUsuarioCollection) {
        this.grupoUsuarioCollection = grupoUsuarioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (email != null ? email.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.email == null && other.email != null) || (this.email != null && !this.email.equals(other.email))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.gestaoeventos.bean.Usuario[ email=" + email + " ]";
    }
    
}
