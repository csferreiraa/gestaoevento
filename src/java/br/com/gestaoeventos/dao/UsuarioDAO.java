/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.dao;

import br.com.gestaoeventos.bean.Grupos;
import br.com.gestaoeventos.bean.Usuario;
import br.com.gestaoeventos.exceptions.UsuarioExistenteException;
import java.util.ArrayList;
import java.util.List;
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

    //@EJB
    //private UsuarioDAO usuarioDAO;

    @PersistenceContext
    private EntityManager em;
    
    private static final String USUARIO_EXISTENTE = "JÁ EXISTE UM USUÁRIO CADASTRADO COM O MESMO E-MAIL";

    /**
     * Retorna um Usuario com o Grupo Acesso para utilizacao apos o Login
     *
     * @param email
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
     * @throws br.com.gestaoeventos.exceptions.UsuarioExistenteException
     */
    public void cadastrarUsuario(Usuario usuario) throws UsuarioExistenteException {
            
        verificarExistenciaUsuario(usuario);
        
        em.persist(usuario);
        em.flush();

    }

    /**
     * Verifica Existencia de Usuario. Metodo responsavel por verificar se o
     * email do usuario ja esta cadastrado na base de dados.
     *
     * @param usuario
     * @throws br.com.gestaoeventos.exceptions.UsuarioExistenteException
     */
    public void verificarExistenciaUsuario(Usuario usuario) throws UsuarioExistenteException {
 
        List<Usuario> lstUs = em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email").setParameter("email", usuario.getEmail().trim().toLowerCase()).getResultList();

        if(!lstUs.isEmpty()){
            throw new UsuarioExistenteException(USUARIO_EXISTENTE);
        }

    }
    
    /**
     * Popular Grupos para Cadastro de Usuario. Metodo que recupera os grupos de
     * Usuario para o cadastro dos novos por um Administrador
     * 
     * @return 
     */
    public List<Grupos> retrieveGruposDAO(){
        List<Grupos> lstGrp = new ArrayList<Grupos>();
        
        lstGrp = em.createQuery("SELECT G FROM Grupos G").getResultList();
        
        return lstGrp;
    }

}
