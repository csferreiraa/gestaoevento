/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.dao;

import br.com.gestaoeventos.bean.Unidade;
import br.com.gestaoeventos.exceptions.UnidadeExistenteException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author cleiton
 */
@Stateless
public class UnidadeDAO {
    
    @PersistenceContext
    private EntityManager em;
    
    private final static String UNIDADE_EXISTENTE_EXCEPTION = "JÁ EXISTE UMA UNIDADE CADASTRADA COM O MESMO NOME";
    
    public void cadastrarUnidade(Unidade unidade) throws UnidadeExistenteException{
            
       validaExistenciaNomeUnidade(unidade);

       em.persist(unidade);
       em.flush();
       
    }
    
    public void validaExistenciaNomeUnidade(Unidade unidade) throws UnidadeExistenteException{
        List<Unidade> lstUnd = em.createQuery("SELECT u FROM Unidade u WHERE u.nomeUnidade = :nomeUnidade").setParameter("nomeUnidade", unidade.getNomeUnidade().toUpperCase().trim()).getResultList();
        
        if(!lstUnd.isEmpty()){
            throw new UnidadeExistenteException(UNIDADE_EXISTENTE_EXCEPTION);
        } 
    }
    
}
