/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.dao;

import br.com.gestaoeventos.bean.Evento;
import br.com.gestaoeventos.bean.Inscricao;
import br.com.gestaoeventos.exceptions.EventoNaoEncontradoException;
import br.com.gestaoeventos.exceptions.InscricaoInexistenteException;
import br.com.gestaoeventos.servicos.Converter;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author cleiton
 */
@Stateless
public class PresencaEventoDAO {
    
    @PersistenceContext
    private EntityManager em;
    
    @EJB
    private Converter converter;
    
    private static final String EVENTO_NAO_ENCONTRADO = "NÃO EXISTEM EVENTOS PARA MARCAR PRESENCA NO PERÍODO DE HOJE MENOS 15 DIAS.";
    private static final String INSCRICAO_NAO_ENCONTRADA = "NÃO EXISTEM INSCRICOES NO EVENTO SELECIONADO.";
    
    /**
     * Recuperar Lista de Inscricoes do Evento. Metodo responsavel por recuperar
     * lista de inscricoes do evento.
     * 
     * @param evento
     * @return 
     */
    public List<Inscricao> recuperaInscricoesEventoDAO(Evento evento) throws InscricaoInexistenteException{
        
        List<Inscricao> lstInscricao = em.createQuery("SELECT I FROM Inscricao I WHERE I.evento.idEvento = :idEvento ORDER BY I.usuario.nomeCompleto ASC").setParameter("idEvento", evento.getIdEvento()).getResultList();
        
        if(lstInscricao.isEmpty()){
            throw new InscricaoInexistenteException(INSCRICAO_NAO_ENCONTRADA);
        }
        
        return lstInscricao;
    }
    
    
    /**
     * Recuperar Lista de Evento. Metodo DAO que verifica os eventos disponiveis para marcacao de presenca.
     * @return
     * @throws EventoNaoEncontradoException 
     */
    public List<Evento> recuperaEventosPresencaDAO() throws EventoNaoEncontradoException{
        
        // Trata as datas de Inicio e Fim
        Date dataInicioBusca = converter.calculaDiasData(new Date(), 15 , "-");
        Date datafimBusca = new Date();

        try {
            datafimBusca = converter.zerarHoraDatas(new Date());
        } catch (ParseException ex) {
            Logger.getLogger(PresencaEventoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        List lstEvento = em.createQuery("SELECT E FROM Evento E WHERE E.dataEvento BETWEEN :dataInicioBusca AND :datafimBusca ORDER BY E.nomeEvento, E.dataEvento").setParameter("dataInicioBusca", dataInicioBusca).setParameter("datafimBusca", datafimBusca).getResultList();
        
        if(lstEvento.isEmpty()){
            throw new EventoNaoEncontradoException(EVENTO_NAO_ENCONTRADO);
        }
        
        return lstEvento;
    }
    
}
