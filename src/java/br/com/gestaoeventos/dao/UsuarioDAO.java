/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.dao;

import br.com.gestaoeventos.bean.Usuario;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Cleiton
 */
@Stateless
public class UsuarioDAO {

    @EJB
    private UsuarioDAO usuarioDAO;

    @PersistenceContext
    private EntityManager em;

    /**
     * Retorna um Usuario com o Grupo Acesso para utilizacao apos o Login
     *
     * @param nomeCompleto
     * @return Usuario
     */
    public Usuario getUserByName(String email) {
        try {
            return (Usuario) em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email").setParameter("email", email).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * Gravar Usuario. Metodo responsavel por gravar o usuario na Base de Dados
     *
     * @param usuario
     */
    public void cadastrarUsuario(Usuario usuario) throws Exception {
        try {
            em.persist(usuario);
            em.flush();
        } catch (Exception e) {
            throw new Exception();
        }

    }

}
