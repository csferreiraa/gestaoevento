/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.dao;

import br.com.gestaoeventos.bean.Curso;
import br.com.gestaoeventos.bean.Evento;
import br.com.gestaoeventos.bean.Sala;
import br.com.gestaoeventos.exceptions.EventoDataExistenteException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author godoyeve
 */
@Stateless
public class EventoDAO {
    
    @PersistenceContext
    private EntityManager em;
    
    private final static String EVENTO_EXISTENTE = "JÁ EXISTE UM EVENTO CADASTRADO NO MESMO DIA COM O MESMO NOME";
    
    
    /**
     * Persiste o Evento. Metodo responsavel por persistir o evento no banco
     * @param evento 
     */
    public void gravaEventoDAO(Evento evento){

        em.persist(evento);
        em.flush();
        
    }
    
    
    /**
     * Recupra evento da sala no dia. Metodo responsavel por recuperar os eventos
     * registrados para a sala o dia selecionado.
     * 
     * @param evento
     * @return List
     */
    public List<Evento> validaExistenciaEventoSalaDiaHoraDAO(Evento evento){
        return em.createQuery("SELECT E FROM Evento E, Sala S WHERE E.sala.idSala = S.idSala and s.idSala = :idSala and E.dataEvento = :dataEvento order by E.horarioInicio").setParameter("idSala", evento.getSala().getIdSala()).setParameter("dataEvento", evento.getDataEvento()).getResultList();
    }
    
    
    /**
     * Valida existencia do evento. Metodo responsavel por verificar se o Evento
     * ja existe no mesmo dia.
     * 
     * @param evento
     * @throws EventoDataExistenteException 
     */
    public void validaExistenciaEventoDataDAO(Evento evento) throws EventoDataExistenteException{
        List<Evento> lstEvento = em.createQuery("SELECT E FROM Evento E WHERE E.nomeEvento = :nomeEvento and E.dataEvento = :dataEvento").setParameter("nomeEvento", evento.getNomeEvento()).setParameter("dataEvento", evento.getDataEvento()).getResultList();
        
        if(!lstEvento.isEmpty()){
            throw new EventoDataExistenteException(EVENTO_EXISTENTE);
        }
        
    }
    
    /**
     * Retornar Lista de Sala. Metodo responsavel por retornar a lista de sala
     * para cadastro do Evento
     * 
     * @return 
     */
    public List<Sala> recuperaListaSalaCadastroEventoDAO(){
        return em.createQuery("SELECT S FROM Sala S ORDER BY S.nomeSala").getResultList();
    }
    
    /**
     * Retornar Lista de Curso. Metodo responsavel por retornar a lista de curso
     * para cadastro do Evento
     * 
     * @return 
     */
    public List<Curso> recuperaListaCursoCadastroEventoDAO(){
        return em.createQuery("SELECT C FROM Curso C ORDER BY C.nomeCurso").getResultList();
    }
    
}
