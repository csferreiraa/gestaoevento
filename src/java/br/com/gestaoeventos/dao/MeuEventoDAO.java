/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.dao;

import br.com.gestaoeventos.bean.Inscricao;
import br.com.gestaoeventos.bean.Usuario;
import br.com.gestaoeventos.exceptions.MeuEventoNaoEncontradoException;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author cleiton
 */
@Stateless
public class MeuEventoDAO {
    
    @PersistenceContext
    private EntityManager em;
    
    
    
    private final static String NAO_EXISTEM_INSCRICOES = "NÃO FORAM LOCALIZADAS INSCRIÇÕES PARA O USUÁRIO E PERÍODO SOLICITADOS.";
    
    
    /**
     * Remove Inscricao. Metodo responsavel por remover inscricoes
     * @param inscricao 
     */
    public void cancelaInscricaoDAO(Inscricao inscricao){
        
         Inscricao toBeRemoved = em.merge(inscricao);
         em.remove(toBeRemoved);
 
    }
    
    
    /**
     * Recuperar lista de Inscricoes DAO. Metodo responsavel por recuperar a lista
     * de inscricoes de evento para o usuario logado.
     * 
     * @param usuario
     * @param dataInicio
     * @param dataFim
     * @param tipoPresenca
     * @return 
     * @throws br.com.gestaoeventos.exceptions.MeuEventoNaoEncontradoException 
     */
    public List<Inscricao> recuperaInscricoesDAO(Usuario usuario, Date dataInicio, Date dataFim, Character tipoPresenca) throws MeuEventoNaoEncontradoException{
        
        List<Inscricao> lstInscricao;
        
        lstInscricao = em.createQuery("SELECT I FROM Inscricao I WHERE I.usuario.idUsuario = :idUsuario AND (I.evento.dataEvento BETWEEN :dataInicio AND :dataFim) AND I.presencaAluno = :tipoPresenca").setParameter("idUsuario", usuario.getIdUsuario()).setParameter("dataInicio", dataInicio).setParameter("dataFim", dataFim).setParameter("tipoPresenca", tipoPresenca).getResultList();
        
        if(lstInscricao.isEmpty()){
            throw new MeuEventoNaoEncontradoException(NAO_EXISTEM_INSCRICOES);
        }
        
        return lstInscricao;
        
    }
    
}
