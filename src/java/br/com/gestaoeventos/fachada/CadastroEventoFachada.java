/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.fachada;

import br.com.gestaoeventos.bean.Curso;
import br.com.gestaoeventos.bean.Evento;
import br.com.gestaoeventos.bean.Sala;
import br.com.gestaoeventos.dao.EventoDAO;
import br.com.gestaoeventos.exceptions.EventoDataExistenteException;
import br.com.gestaoeventos.exceptions.EventoHoraExcedenteException;
import br.com.gestaoeventos.exceptions.EventoHoraInicioException;
import br.com.gestaoeventos.exceptions.EventoSalaDiaHoraExistente;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author cleiton
 */
@Stateless
public class CadastroEventoFachada {

    @EJB
    private EventoDAO eventoDAO;

    private static final String CONFLITO_DE_HORARIO_SALA_DIA = "O HORARIO SOLICITADO ENTROU EM CONFLITO COM OUTRO HORARIO JÁ CADASTRADO NO MESMO DIA PARA A MESMA SALA";
    private static final String HORARIO_DURACAO_INDISPONIVEL = "A SOMA DO HORÁRIO DE INICIO MAIS A DURAÇÃO EXCEDE 24 HORAS";
    private static final String HORARIO_INDEVIDO = "POR FAVOR, SELECIONE UM HORÁRIO DE INICIO VÁLIDO CASO AINDA HOUVER DISPONIBILIDADE";

    /**
     * Cadastro de Evento. Metodo responsavel por iniciar o cadastro do Evento
     *
     * @param evento
     */
    public void iniciaCadastroEventoFachada(Evento evento) {
        eventoDAO.gravaEventoDAO(evento);
    }

    /**
     * Verifica a existencia de evento na sala. Metodo responsavel por verificar
     * os eventos ja registrados no dia para a sala selecionada.
     *
     * @param eventoUsuario
     */
    public void validaExistenciaEventoSalaDiaHora(Evento eventoUsuario) throws EventoSalaDiaHoraExistente {

        List<Evento> lstEvento = eventoDAO.validaExistenciaEventoSalaDiaHoraDAO(eventoUsuario);

        if (!lstEvento.isEmpty()) {

            // 1 Passo. Verifica se o Novo evento ira ocupar parte do horario da reserva atual
            for (Evento eventoBanco : lstEvento) {

                // Conjuntos, Banco, Usuario e Interesecao
                Set<Integer> conjuntoHorasBanco = new TreeSet<>();
                Set<Integer> conjuntoHorasUsuario = new TreeSet<>();
                Set<Integer> conjuntoIntersecao = new TreeSet<>();
                Boolean possuiIntersecao = Boolean.FALSE;

                Integer horaInicioEventoBanco = eventoBanco.getHorarioInicio();
                Integer horasDeDuracaoEvento = eventoBanco.getDuracaoEvento();

                // 2 Passo. Usando objetos da interface Set para representar a intersecao de dois ou mais conjuntos
                for (int i = horaInicioEventoBanco; i <= (horaInicioEventoBanco + horasDeDuracaoEvento); i++) {
                    conjuntoHorasBanco.add(i);
                }

                for (int j = eventoUsuario.getHorarioInicio(); j <= (eventoUsuario.getHorarioInicio() + eventoUsuario.getDuracaoEvento()); j++) {
                    conjuntoHorasUsuario.add(j);
                }

                conjuntoIntersecao = intersecao(conjuntoHorasBanco, conjuntoHorasUsuario);


                // Validando o conjunto retornado
                Iterator iterator = conjuntoIntersecao.iterator();
                while (iterator.hasNext()) {
                    possuiIntersecao = Boolean.TRUE;
                }

                if (possuiIntersecao) {
                    throw new EventoSalaDiaHoraExistente(CONFLITO_DE_HORARIO_SALA_DIA);
                }

            }

        }

    }

    /**
     * Recupera Intersecao. método genérico que permite obter a interseção de
     * dois conjuntos
     *
     * @param <T>
     * @param conjuntoBanco
     * @param conjuntoUsuario
     *
     * @return
     */
    public <T> Set<T> intersecao(Set<T> conjuntoBanco, Set<T> conjuntoUsuario) {
        Set<T> conjIntersecao = new TreeSet<>();
        // percorremos todos os elementos do conjunto do evento do Banco
        for (T elemento : conjuntoBanco) {
            // e verificamos se o elemento está contido no conjunto do eveto do Usuario
            if (conjuntoUsuario.contains(elemento)) {
                conjIntersecao.add(elemento); // se estiver contido nós o adicionamos no conjunto Intersecao 
            }
        }
        return conjIntersecao;
    }

    /**
     * Valida Horario Inicio. Metodo que verifica o Horario Inicio do Usuario
     *
     * @param evento
     * @throws EventoHoraInicioException
     */
    public void validaHoraSelecionada(Evento evento) throws EventoHoraInicioException {
        if (evento.getHorarioInicio() == -1) {
            throw new EventoHoraInicioException(HORARIO_INDEVIDO);
        }
    }

    /**
     * Valdia Somatoria de Horas. Metodo responsavel por retornar a somatoria do
     * Horario de Inicio mais a quantidade hora de Duracao
     *
     * @param evento
     * @throws EventoHoraExcedenteException
     */
    public void validaSomatoriaHoraInicioDuracaoFachada(Evento evento) throws EventoHoraExcedenteException {
        if (evento.getHorarioInicio() + evento.getDuracaoEvento() > 24) {
            throw new EventoHoraExcedenteException(HORARIO_DURACAO_INDISPONIVEL);
        }
    }

    /**
     * Verifica a existencia do evento. Metodo responsavel por verificar se ja
     * existe algum evento com o mesmo nome no mesmo dia.
     */
    public void validaExistenciaEventoDataFachada(Evento evento) throws EventoDataExistenteException {
        eventoDAO.validaExistenciaEventoDataDAO(evento);
    }

    /**
     * Retorna lista de Sala. Metodo da fachada responsavel por retornar a lista
     * de sala para cadastro do Evento.
     *
     * @return
     */
    public List<Sala> recuperaListaSalaFachada() {
        return eventoDAO.recuperaListaSalaCadastroEventoDAO();
    }

    /**
     * Retorna lista de Curso. Metodo da fachada responsavel por retornar a
     * lista de curso para cadastro do Evento.
     *
     * @return
     */
    public List<Curso> recuperaListaCursoFachada() {
        return eventoDAO.recuperaListaCursoCadastroEventoDAO();
    }

}
