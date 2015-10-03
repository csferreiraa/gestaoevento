/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.dao;

import br.com.gestaoeventos.bean.Curso;
import br.com.gestaoeventos.bean.Evento;
import br.com.gestaoeventos.bean.EventoAux;
import br.com.gestaoeventos.bean.Inscricao;
import br.com.gestaoeventos.bean.Sala;
import br.com.gestaoeventos.bean.Usuario;
import br.com.gestaoeventos.exceptions.EventoDataExistenteException;
import br.com.gestaoeventos.exceptions.EventoNaoEncontradoException;
import br.com.gestaoeventos.servicos.Converter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
public class EventoDAO {

    @PersistenceContext
    private EntityManager em;
    
    @EJB
    private Converter converter;

    private final static String EVENTO_EXISTENTE = "JÁ EXISTE UM EVENTO CADASTRADO NO MESMO DIA COM O MESMO NOME";
    private final static String EVENTO_INEXISTENTE = "NAO HA DETALHES DE EVENTOS DISPONIVEIS";
    private final static String EVENTO_INEXISTENTE_HORA = "NAO HA EVENTOS DISPONIVEIS DEVIDO AO HORARIO ATUAL DO DIA";
    private Integer horaAtual;

    
    /**
     * Recupera Inscricoes para um determinado evento. Metodo responsavel por 
     * recuperar inscricoes para um determinado evento do usuario.
     * 
     * @param usuario
     * @param evento
     * @return 
     * @throws java.text.ParseException 
     */
    public List<Inscricao> validaInscricoesUsuarioDAO(Usuario usuario, EventoAux evento) throws ParseException{      
        
        Date dataAtual = new Date();
        
        return em.createQuery("SELECT I FROM Inscricao I WHERE I.usuario.idUsuario = :idUsuario AND I.evento.dataEvento >= :dataAtual").setParameter("idUsuario", usuario.getIdUsuario()).setParameter("dataAtual", converter.zerarHoraDatas(dataAtual)).getResultList();
    }
    
    
    /**
     * Calcula quantidade de inscricoes para o evento. Metodo DAO responsavel
     * por calcular a quantidade de inscricoes realizadas para um determinado
     * evento.
     *
     * @param evento
     * @return
     */
    public Integer calculaQuantidadeInscricoesEventoDAO(Evento evento) {

        List<Inscricao> lstInscricao = em.createQuery("SELECT I FROM Inscricao I WHERE I.evento.idEvento = :idEvento").setParameter("idEvento", evento.getIdEvento()).getResultList();

        if (!lstInscricao.isEmpty()) {
            return lstInscricao.size();
        } else {
            return new Integer(0);
        }
    }

    /**
     * Retorna lista de Eventos. Metodo responsavel por recuperar a lista de
     * detalhe dos eventos selecionados.
     *
     * @param idCurso
     * @param idEvento
     * @throws br.com.gestaoeventos.exceptions.EventoNaoEncontradoException
     * @return List
     */
    public List<Evento> pesquisaDetalheEventoDAO(Integer idCurso, Integer idEvento) throws EventoNaoEncontradoException {
        
        List<Evento> lstEvento  = em.createQuery("SELECT E FROM Evento E WHERE ((-1 = :idCurso) OR (E.curso.idCurso = :idCurso)) AND ((-1 = :idEvento) OR (E.idEvento = :idEvento)) AND E.dataEvento >= current_date ORDER BY E.nomeEvento, E.dataEvento DESC").setParameter("idCurso", idCurso).setParameter("idEvento", idEvento).getResultList();

        if (lstEvento.isEmpty()) {
            throw new EventoNaoEncontradoException(EVENTO_INEXISTENTE);
        }

        return lstEvento;

    }

    /**
     * Persiste o Evento. Metodo responsavel por persistir o evento no banco
     *
     * @param evento
     */
    public void gravaEventoDAO(Evento evento) {

        em.persist(evento);
        em.flush();

    }

    /**
     * Recupra evento da sala no dia. Metodo responsavel por recuperar os
     * eventos registrados para a sala o dia selecionado.
     *
     * @param evento
     * @return List
     */
    public List<Evento> validaExistenciaEventoSalaDiaHoraDAO(Evento evento) {
        return em.createQuery("SELECT E FROM Evento E, Sala S WHERE E.sala.idSala = S.idSala and s.idSala = :idSala and E.dataEvento = :dataEvento order by E.horarioInicio").setParameter("idSala", evento.getSala().getIdSala()).setParameter("dataEvento", evento.getDataEvento()).getResultList();
    }

    /**
     * Valida existencia do evento. Metodo responsavel por verificar se o Evento
     * ja existe no mesmo dia.
     *
     * @param evento
     * @throws EventoDataExistenteException
     */
    public void validaExistenciaEventoDataDAO(Evento evento) throws EventoDataExistenteException {
        List<Evento> lstEvento = em.createQuery("SELECT E FROM Evento E WHERE E.nomeEvento = :nomeEvento and E.dataEvento = :dataEvento").setParameter("nomeEvento", evento.getNomeEvento()).setParameter("dataEvento", evento.getDataEvento()).getResultList();

        if (!lstEvento.isEmpty()) {
            throw new EventoDataExistenteException(EVENTO_EXISTENTE);
        }

    }

    /**
     * Retornar Lista de Sala. Metodo responsavel por retornar a lista de sala
     * para cadastro do Evento
     *
     * @return
     */
    public List<Sala> recuperaListaSalaCadastroEventoDAO() {
        return em.createQuery("SELECT S FROM Sala S ORDER BY S.nomeSala").getResultList();
    }

    /**
     * Retornar Lista de Curso. Metodo responsavel por retornar a lista de curso
     * para cadastro do Evento
     *
     * @return
     */
    public List<Curso> recuperaListaCursoCadastroEventoDAO() {
        return em.createQuery("SELECT C FROM Curso C ORDER BY C.nomeCurso").getResultList();
    }

    /**
     * Retornar Lista de Cursos. Metodo responsavel por recuperar uma lista de
     * Cursos que estao relacionados com Eventos Ativos.
     *
     * @return List
     */
    public List<Curso> recuperaListaCursoRelacionadosComEventoDAO() {

        List<Curso> lstCurso = new ArrayList<Curso>();

        lstCurso = em.createQuery("SELECT DISTINCT(C) FROM Curso C, Evento E WHERE E.curso.idCurso = C.idCurso AND E.dataEvento >= :dataAtual ORDER BY C.nomeCurso").setParameter("dataAtual", new Date()).getResultList();

        if (lstCurso.size() > 1) {
            Curso c = new Curso();
            c.setIdCurso(-1);
            c.setNomeCurso("TODOS");
            lstCurso.add(0, c);
        }

        return lstCurso;
    }

    /**
     * Retornar Lista de Evento. Metodo responsavel por recuperar uma lista de
     * Eventos de acordo com o Curso e Evento.
     *
     * @param idCurso
     * @return List
     */
    public List<Evento> retornaEventosByCursoSalaDAO(Integer idCurso) {

        List<Evento> lstEvento = new ArrayList<Evento>();
        List<Evento> lstEventoFinal = new ArrayList<Evento>(); // Para o retorno sem eventos que ja ocorreram HOJE

        // Tratamento de Hora. Busca apenas eventos que ainda nao passaram a HORA
        // Obter a HORA atual
        horaAtual = 0;
        Date dataAtual = new Date();   // given date
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(dataAtual);   // assigns calendar to given date 
        horaAtual = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format

        lstEvento = em.createQuery("Select E FROM Evento E WHERE ((-1 = :idCurso) OR (E.curso.idCurso = :idCurso)) and E.dataEvento >= current_date ORDER BY E.nomeEvento").setParameter("idCurso", idCurso).getResultList();      
        
        // Verifica os Eventos com a Data de HOJE que ja ocorreram e remove da lista
        for (Evento ev : lstEvento) {
            
            Date dataEventoLista = new Date();
            Date dataEventoSistema = new Date();
            
            // Zera o horario das DATAS
            try {
                dataEventoLista = converter.zerarHoraDatas(ev.getDataEvento());
                dataEventoSistema = converter.zerarHoraDatas(dataAtual);
            } catch (ParseException ex) {
                Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (dataEventoLista.equals(dataEventoSistema)) {
                if (ev.getHorarioInicio() > horaAtual) {
                    lstEventoFinal.add(ev);
                }
            }
            if (ev.getDataEvento().after(dataAtual)) {
                lstEventoFinal.add(ev);
            }
        }

        if (lstEventoFinal.size() > 1) {
            Evento e = new Evento();
            e.setIdEvento(-1);
            e.setNomeEvento("TODOS");
            lstEventoFinal.add(0, e);
        }

        return lstEventoFinal;
    }

}
