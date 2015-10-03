/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.dao;

import br.com.gestaoeventos.bean.EventoAux;
import br.com.gestaoeventos.bean.Inscricao;
import br.com.gestaoeventos.exceptions.InscricaoNaoPermitidaException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author cleiton
 */
@Stateless
public class InscricaoDAO {
    
    @PersistenceContext
    private EntityManager em;
    
    private static final String INSCRICOES_ENCERRADAS = "CAPACIDADE TOTAL DA SALA FOI ESGOTADA";
    
    
    public void validaTotalInscricoesEventoDAO(EventoAux evento) throws InscricaoNaoPermitidaException{
       
        List<Inscricao> lstInscricao = em.createQuery("SELECT I FROM Inscricao I WHERE I.evento.idEvento = :idEvento").setParameter("idEvento", evento.getIdEvento()).getResultList();

        if(lstInscricao.size() >= evento.getSala().getCapacidadeMaximaPessoas()){
            throw new InscricaoNaoPermitidaException(INSCRICOES_ENCERRADAS);
        }
        
        
    }
    
    
    /**
     * Gravar Inscricao. Metodo responsavel por gravar uma inscricao.
     * @param inscricao 
     */
    public void gravarInscricaoDAO(Inscricao inscricao){
        em.persist(inscricao);
        em.flush();
    }
}
