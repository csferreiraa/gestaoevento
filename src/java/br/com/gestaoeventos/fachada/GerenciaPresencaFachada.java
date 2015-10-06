/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.fachada;

import br.com.gestaoeventos.bean.Evento;
import br.com.gestaoeventos.bean.Inscricao;
import br.com.gestaoeventos.dao.PresencaEventoDAO;
import br.com.gestaoeventos.exceptions.EventoNaoEncontradoException;
import br.com.gestaoeventos.exceptions.InscricaoInexistenteException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author cleiton
 */
@Stateless
public class GerenciaPresencaFachada {
    
    @EJB
    private PresencaEventoDAO presencaEventoDAO;
    
    /**
     * Atualiza Presenca. Metodo responsavel por atualizar Presenca na Fachada.
     * @param inscricao 
     */
    public void atualizaPresencaFechada(Inscricao inscricao){
        presencaEventoDAO.atualizaPresencaDAO(inscricao);
    }
    
    
    /**
     * Recuperar inscricoes do evento. Metodo responsavel por recuperar as inscricoes
     * do Evento.
     * 
     * @param evento
     * @return
     * @throws InscricaoInexistenteException 
     */
    public List<Inscricao> recuperaInscricoesEventoFachada(Evento evento) throws InscricaoInexistenteException{
        return presencaEventoDAO.recuperaInscricoesEventoDAO(evento);
    }
    
    /**
     * Recupra Lista de Eventos. Metodo responsavel por recuperar lista de eventos
     * para o gerenciamento de presenca
     * 
     * @return
     * @throws EventoNaoEncontradoException 
     */
    public List<Evento> recuperaEventosPresencaFachada() throws EventoNaoEncontradoException{
        return presencaEventoDAO.recuperaEventosPresencaDAO();
    }
    
}
