/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.mbean;

import br.com.gestaoeventos.bean.Curso;
import br.com.gestaoeventos.bean.Evento;
import br.com.gestaoeventos.bean.EventoAux;
import br.com.gestaoeventos.bean.Inscricao;
import br.com.gestaoeventos.bean.Usuario;
import br.com.gestaoeventos.exceptions.EventoNaoEncontradoException;
import br.com.gestaoeventos.exceptions.InscricaoNaoPermitidaException;
import br.com.gestaoeventos.fachada.BuscaEventoFachada;
import br.com.gestaoeventos.fachada.InscricaoEventoFachada;
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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author cleiton
 */
@ManagedBean(name = "buscaEventoMB")
@SessionScoped
public class BuscaEventoMB {

    private Integer idCursoEvento;
    private List<Curso> lstCursoEvento = new ArrayList<Curso>();
    private Integer idEventoAux;
    private List<Evento> lstEventoAux = new ArrayList<Evento>();

    private List<Evento> lstEvento = new ArrayList<>();

    private List<EventoAux> lstEventoFinal = new ArrayList<EventoAux>();

    private EventoAux eventoAuxSelecionado;

    @EJB
    private BuscaEventoFachada buscaEventoFachada;

    @EJB
    private Converter converter;

    @EJB
    private InscricaoEventoFachada inscricaoEventoFachada;

    private Integer horaAtual;

    /**
     * Grava a inscricao do usuario. Metodo responsavel por gravar a inscricao.
     *
     * @throws ParseException
     */
    public void verificaPossibilidadeInscricaoEvento() throws ParseException {

        if (eventoAuxSelecionado == null) {

            try {
                throw new EventoNaoEncontradoException("NENHUM EVENTO FOI SELECIONADO");
            } catch (EventoNaoEncontradoException enee) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", enee.getMessage()));
            }
        } else {

            // Continua, Selecionou Evento. Recupera usuario da sessao
            FacesContext context = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
            Usuario usuario = (Usuario) session.getAttribute("userLoggedIn");

            try {
                // Verifica se a inscricao desse usuario estara em conflito com outra do mesmo usuario
                buscaEventoFachada.validaInscricoesUsuarioFachada(usuario, getEventoAuxSelecionado());

                // Verifica o total de inscricoes para o evento
                buscaEventoFachada.validaTotalInscricoesEventoFachada(getEventoAuxSelecionado());

                // Continua pois nao houve conflito no mesmo dia e horario
                Inscricao inscricao = new Inscricao();
                inscricao.setUsuario(usuario);
                inscricao.setEvento(new Evento(getEventoAuxSelecionado().getIdEvento()));

                inscricaoEventoFachada.gravarInscricaoFachada(inscricao);

                // Limpa a lista a tela
                lstEventoFinal = new ArrayList<EventoAux>();

                FacesContext context3 = FacesContext.getCurrentInstance();
                context3.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "INSCRIÇÃO FOI REALIZADA."));

            } catch (InscricaoNaoPermitidaException inpe) {
                FacesContext context2 = FacesContext.getCurrentInstance();
                context2.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", inpe.getMessage()));
            }

        }

    }

    /**
     * Pesquisar Evento. Metodo responsavel por recuperar detalhes do Evento
     */
    public void iniciaPesquisaDetalheEvento() {

        lstEventoFinal = new ArrayList<EventoAux>();

        // Obter a HORA atual
        horaAtual = 0;
        Date dataAtual = new Date();   // given date
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(dataAtual);   // assigns calendar to given date 
        horaAtual = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format

        try {
            setLstEvento(buscaEventoFachada.pesquisaDetalheEventoFachada(getIdCursoEvento(), getIdEventoAux()));

            // Para filtrar lista
            Date dataEventoSistema = new Date();

            for (Evento e : getLstEvento()) {

                // Criando as datas e zerando as HORAS
                Date dataEventoLista = new Date();

                try {
                    // Zera o horario das DATAS
                    dataEventoLista = converter.zerarHoraDatas(e.getDataEvento());
                    dataEventoSistema = converter.zerarHoraDatas(dataAtual);
                } catch (ParseException ex) {
                    Logger.getLogger(BuscaEventoMB.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (dataEventoLista.equals(dataEventoSistema)) {
                    if (e.getHorarioInicio() > horaAtual) {
                        EventoAux ea = new EventoAux();

                        // Verifica a quantidade de inscricoes ja realizada em cada evento
                        ea.setQuantidadeInscricoes(buscaEventoFachada.calculaQuantidadeInscricoesEventoFachada(e));

                        // Monta uma lista de visualizacao na pagina
                        ea.setIdEvento(e.getIdEvento());
                        ea.setNomeEvento(e.getNomeEvento());
                        ea.setNomePalestrante(e.getNomePalestrante());
                        ea.setDuracaoEvento(e.getDuracaoEvento());
                        ea.setCurso(e.getCurso());
                        ea.setSala(e.getSala());
                        ea.setObservacaoEvento(e.getObservacaoEvento());
                        ea.setHorarioInicio(e.getHorarioInicio());
                        ea.setDataEvento(e.getDataEvento());
                        ea.setDtEvento(converter.convertDateToString(e.getDataEvento()));
                        ea.setHrInicio(String.valueOf(e.getHorarioInicio() + ":00"));

                        lstEventoFinal.add(ea);
                    }
                }
                if (e.getDataEvento().after(dataAtual)) {
                    EventoAux ea = new EventoAux();

                    // Verifica a quantidade de inscricoes ja realizada em cada evento
                    ea.setQuantidadeInscricoes(buscaEventoFachada.calculaQuantidadeInscricoesEventoFachada(e));

                    // Monta uma lista de visualizacao na pagina
                    ea.setIdEvento(e.getIdEvento());
                    ea.setNomeEvento(e.getNomeEvento());
                    ea.setNomePalestrante(e.getNomePalestrante());
                    ea.setDuracaoEvento(e.getDuracaoEvento());
                    ea.setCurso(e.getCurso());
                    ea.setSala(e.getSala());
                    ea.setObservacaoEvento(e.getObservacaoEvento());
                    ea.setHorarioInicio(e.getHorarioInicio());
                    ea.setDataEvento(e.getDataEvento());
                    ea.setDtEvento(converter.convertDateToString(e.getDataEvento()));
                    ea.setHrInicio(String.valueOf(e.getHorarioInicio() + ":00"));

                    lstEventoFinal.add(ea);
                }

            }

        } catch (EventoNaoEncontradoException enee) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", enee.getMessage()));
        }

    }

    /**
     * Recupera lista de Cursos. Metodo responsavel por recuperar a lista de
     * cursos relacionadas com Eventos ativos.
     *
     */
    public void recuperaCursoRelacionadoEventoListener() {
        setLstCursoEvento(buscaEventoFachada.retornaCursosRelacionadoEventoFachada());

        lstEventoFinal = new ArrayList<EventoAux>();

        // Se houver apenas 1 curso, recupera as informacoes do combo Sala Evento
        if (getLstCursoEvento().size() == 1) {
            for (Curso c : getLstCursoEvento()) {
                setIdCursoEvento(c.getIdCurso());
                this.recuperaEventoByCurso(getIdCursoEvento());
            }
        }
    }

    /**
     * Recupera lista de Eventos. Metodo responsavel por recuperar a lista de
     * Eventos Caso exista apenas 1 Curso relacionada ao Evento. - Disparado por
     * recuperaCursoRelacionadoEventoListener()
     *
     * @param idCurso
     * @param idSala
     */
    public void recuperaEventoByCurso(Integer idCurso) {
        setLstEventoAux(buscaEventoFachada.retornaEventosByCursoSalaFachada(idCurso));
    }

    /**
     * Recupera lista de Evento (Listener). Metodo responsavel por recuperar a
     * lista de Evento Caso exista mais de 1 Sala relacionado ao Evento
     */
    public void recuperaEventoByCursoListener() {
        setLstEventoAux(buscaEventoFachada.retornaEventosByCursoSalaFachada(getIdCursoEvento()));
    }

    /**
     * Redirecionar para Busca de Evento. Metodo responsavel por direcionar o
     * usuario logado para a pagina de Busca de Evento
     *
     * @return String
     */
    public String returnPageBuscaEvento() {
        return "/pages/pesquisa/evento/buscaEvento.xhtml?faces-redirect=true";
    }

    /**
     * @return the idCursoEvento
     */
    public Integer getIdCursoEvento() {
        return idCursoEvento;
    }

    /**
     * @param idCursoEvento the idCursoEvento to set
     */
    public void setIdCursoEvento(Integer idCursoEvento) {
        this.idCursoEvento = idCursoEvento;
    }

    /**
     * @return the lstCursoEvento
     */
    public List<Curso> getLstCursoEvento() {
        return lstCursoEvento;
    }

    /**
     * @param lstCursoEvento the lstCursoEvento to set
     */
    public void setLstCursoEvento(List<Curso> lstCursoEvento) {
        this.lstCursoEvento = lstCursoEvento;
    }

    /**
     * @return the idEventoAux
     */
    public Integer getIdEventoAux() {
        return idEventoAux;
    }

    /**
     * @param idEventoAux the idEventoAux to set
     */
    public void setIdEventoAux(Integer idEventoAux) {
        this.idEventoAux = idEventoAux;
    }

    /**
     * @return the lstEventoAux
     */
    public List<Evento> getLstEventoAux() {
        return lstEventoAux;
    }

    /**
     * @param lstEventoAux the lstEventoAux to set
     */
    public void setLstEventoAux(List<Evento> lstEventoAux) {
        this.lstEventoAux = lstEventoAux;
    }

    /**
     * @return the lstEvento
     */
    public List<Evento> getLstEvento() {
        return lstEvento;
    }

    /**
     * @param lstEvento the lstEvento to set
     */
    public void setLstEvento(List<Evento> lstEvento) {
        this.lstEvento = lstEvento;
    }

    /**
     * @return the lstEventoFinal
     */
    public List<EventoAux> getLstEventoFinal() {
        return lstEventoFinal;
    }

    /**
     * @param lstEventoFinal the lstEventoFinal to set
     */
    public void setLstEventoFinal(List<EventoAux> lstEventoFinal) {
        this.lstEventoFinal = lstEventoFinal;
    }

    /**
     * @return the eventoAuxSelecionado
     */
    public EventoAux getEventoAuxSelecionado() {

        return eventoAuxSelecionado;
    }

    /**
     * @param eventoAuxSelecionado the eventoAuxSelecionado to set
     */
    public void setEventoAuxSelecionado(EventoAux eventoAuxSelecionado) {
        this.eventoAuxSelecionado = eventoAuxSelecionado;
    }

}
