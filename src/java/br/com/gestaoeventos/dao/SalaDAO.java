/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.dao;

import br.com.gestaoeventos.bean.Sala;
import br.com.gestaoeventos.bean.Unidade;
import br.com.gestaoeventos.exceptions.SalaExistenteException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author cleiton
 */
@Stateless
public class SalaDAO {
    
    @PersistenceContext
    private EntityManager em;
    
    private static final String SALA_EXISTENTE_NA_UNIDADE = "JÁ EXISTE UMA SALA COM O MESMO NOME CADASTRADA NA MESMA UNIDADE";
    
    
    /**
     * Cadastrar Sala. Metodo responsavel por cadasrar uma nova sala
     * @param sala 
     * @throws br.com.gestaoeventos.exceptions.SalaExistenteException 
     */
    public void cadastrarSalaDAO(Sala sala) throws SalaExistenteException{
        
        validaExistenciaSalaNaUnidade(sala);
        
        em.persist(sala);
        em.flush();
        
    }
    
    /**
     * Recupera as unidades das Salas.
     * @return 
     */
    public List<Unidade> retrieveUnidadeSalaDAO(){
        List<Unidade> lstUnidade = new ArrayList<Unidade>();
        
        lstUnidade = em.createQuery("SELECT U FROM Unidade U ORDER BY U.nomeUnidade").getResultList();
        
        return lstUnidade;
    }
    
    /**
     * Verifica existencia de uma Sala na Unidade. Metodo responsavel por verificar
     * se ja existe uma Sala com o mesmo nome na Unidade.
     * @param sala
     * @throws SalaExistenteException 
     */
    public void validaExistenciaSalaNaUnidade(Sala sala) throws SalaExistenteException{
        
        List<Sala> lstSala = em.createQuery("SELECT S FROM Sala S, Unidade U Where S.unidade.idUnidade = U.idUnidade AND S.nomeSala = :nomeSala AND U.idUnidade = :idUnidade").setParameter("nomeSala", sala.getNomeSala()).setParameter("idUnidade", sala.getUnidade().getIdUnidade()).getResultList();
        
        if(!lstSala.isEmpty()){
            throw new SalaExistenteException(SALA_EXISTENTE_NA_UNIDADE);
        }
    }
}
