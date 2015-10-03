/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.fachada;

import br.com.gestaoeventos.bean.Curso;
import br.com.gestaoeventos.bean.Evento;
import br.com.gestaoeventos.bean.EventoAux;
import br.com.gestaoeventos.bean.Inscricao;
import br.com.gestaoeventos.bean.Usuario;
import br.com.gestaoeventos.dao.EventoDAO;
import br.com.gestaoeventos.dao.InscricaoDAO;
import br.com.gestaoeventos.exceptions.EventoNaoEncontradoException;
import br.com.gestaoeventos.exceptions.InscricaoNaoPermitidaException;
import br.com.gestaoeventos.servicos.Converter;
import java.text.ParseException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author cleiton
 */
@Stateless
public class BuscaEventoFachada {

    @EJB
    private EventoDAO eventoDAO;
    
    @EJB
    private InscricaoDAO inscricaoDAO;

    @EJB
    private Converter converter;

    private final static String CONFLITO_HORARIO_INSCRICAO = "VOCÊ JÁ POSSUI UMA INSCRICAO PARA O INTERVALO SOLICITADO NO MESMO DIA";

    /**
     * Valida total de inscricoes. Metodo da fachada responsavel por valida o total
     * de inscricoes no Evento
     * 
     * @param evento 
     * @throws br.com.gestaoeventos.exceptions.InscricaoNaoPermitidaException 
     */
    public void validaTotalInscricoesEventoFachada(EventoAux evento) throws InscricaoNaoPermitidaException{
        inscricaoDAO.validaTotalInscricoesEventoDAO(evento);
    }
    
    /**
     * Validar possibilidade de Inscricao. Metodo responsavel por validar uma
     * possibilidade de inscricao para o evento selecionado.
     *
     * @param usuario
     * @param eventoSolicitado
     * @throws ParseException
     * @throws InscricaoNaoPermitidaException
     */
    public void validaInscricoesUsuarioFachada(Usuario usuario, EventoAux eventoSolicitado) throws ParseException, InscricaoNaoPermitidaException {

        List<Inscricao> lstInscricao;

        lstInscricao = eventoDAO.validaInscricoesUsuarioDAO(usuario, eventoSolicitado);

        if (!lstInscricao.isEmpty()) {

            for (Inscricao i : lstInscricao) {

                Integer horaInicioEventoSolicitado = eventoSolicitado.getHorarioInicio();

                Integer horaInicioEventoInscrito = i.getEvento().getHorarioInicio();
                Integer horaFimEventoInscrito = (i.getEvento().getHorarioInicio() + i.getEvento().getDuracaoEvento());

                if ((horaInicioEventoSolicitado >= horaInicioEventoInscrito) && (horaInicioEventoSolicitado <= horaFimEventoInscrito)) {
                    throw new InscricaoNaoPermitidaException(CONFLITO_HORARIO_INSCRICAO);
                }
            }
        }
    }

    /**
     * Calcula quantidade de inscricoes do evento. Metodo responsavel por
     * calcular a quantidade de inscricoes ja realizada no evento
     *
     * @param evento
     * @return
     */
    public Integer calculaQuantidadeInscricoesEventoFachada(Evento evento) {
        return eventoDAO.calculaQuantidadeInscricoesEventoDAO(evento);
    }

    /**
     * Retorna lista de eventos. Metodo responsavel por recuperar a lista de
     * eventos de acordo com o curso e evento selecionado.
     *
     * @param idCurso
     * @param idEvento
     * @return
     * @throws br.com.gestaoeventos.exceptions.EventoNaoEncontradoException
     */
    public List<Evento> pesquisaDetalheEventoFachada(Integer idCurso, Integer idEvento) throws EventoNaoEncontradoException {
        return eventoDAO.pesquisaDetalheEventoDAO(idCurso, idEvento);
    }

    /**
     * Recupera lista de Cursos. Metodo da fachada responsavel por recuperar
     * lista de Cursos para selecao de evento.
     *
     * @return List
     */
    public List<Curso> retornaCursosRelacionadoEventoFachada() {
        return eventoDAO.recuperaListaCursoRelacionadosComEventoDAO();
    }

    /**
     * Recupera lista de Eventos. Metodo da fachada responsavel por recuperar
     * lista de Eventos para selecao do mesmo.
     *
     * @param idCurso
     * @return List
     */
    public List<Evento> retornaEventosByCursoSalaFachada(Integer idCurso) {
        return eventoDAO.retornaEventosByCursoSalaDAO(idCurso);
    }

}
