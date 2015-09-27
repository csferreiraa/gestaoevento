/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.dao;

import br.com.gestaoeventos.bean.Curso;
import br.com.gestaoeventos.bean.Sala;
import br.com.gestaoeventos.exceptions.CursoExistenteException;
import br.com.gestaoeventos.exceptions.SalaExistenteException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author cleiton
 */
@Stateless
public class CursoDAO {
    
    @PersistenceContext
    private EntityManager em;
    
    private static final String CURSO_EXISTENTE = "JÁ EXISTE UM CURSO CADASTRADO COM O MESMO NOME";
    
    
    /**
     * Cadastrar Curso. Metodo responsavel por cadasrar um novo Curso
     * @param curso
     * @throws br.com.gestaoeventos.exceptions.CursoExistenteException
     */
    public void cadastrarCursoDAO(Curso curso) throws CursoExistenteException{
        
        validaExistenciaSalaNaUnidade(curso);
        
        em.persist(curso);
        em.flush();
        
    }
    
    
    
    /**
     * Verifica existencia de um Curso. Metodo responsavel por verificar se ja 
     * existe um Curso cadastrado com o mesmo nome.
     * @param curso
     * @throws br.com.gestaoeventos.exceptions.CursoExistenteException
     */
    public void validaExistenciaSalaNaUnidade(Curso curso) throws CursoExistenteException{
        
        List<Curso> lstCurso = em.createQuery("SELECT C FROM Curso C WHERE C.nomeCurso = :nomeCurso").setParameter("nomeCurso", curso.getNomeCurso()).getResultList();
        
        if(!lstCurso.isEmpty()){
            throw new CursoExistenteException(CURSO_EXISTENTE);
        }
    }
    
    
}
