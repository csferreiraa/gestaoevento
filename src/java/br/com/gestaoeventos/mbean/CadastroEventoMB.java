/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.mbean;

import br.com.gestaoeventos.bean.Curso;
import br.com.gestaoeventos.bean.Evento;
import br.com.gestaoeventos.bean.HorarioEvento;
import br.com.gestaoeventos.bean.Sala;
import br.com.gestaoeventos.exceptions.EventoDataExistenteException;
import br.com.gestaoeventos.exceptions.EventoHoraExcedenteException;
import br.com.gestaoeventos.exceptions.EventoHoraInicioException;
import br.com.gestaoeventos.exceptions.EventoSalaDiaHoraExistente;
import br.com.gestaoeventos.fachada.CadastroEventoFachada;
import br.com.gestaoeventos.fachada.HorarioEventoFachada;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author cleiton
 */
@ManagedBean(name = "cadastroEventoMB")
@SessionScoped
public class CadastroEventoMB {

    private String nomeEvento;
    private String nomePalestrante;
    private Date dataEvento;
    private String horarioInicio;
    private Sala salaEvento;
    private Curso cursoEvento;
    private String observacaoEvento;
    private Date minimaDataSelecao;
    private Date maximaDataSelecao;
    private Integer idHoraInicio;
    private List<HorarioEvento> lstHoraInicioEvento;
    private Integer horasEvento;
    private Integer idSalaEvento;
    private List<Sala> lstSalaEvento = new ArrayList<Sala>();
    private Integer idCursoEvento;
    private List<Curso> lstCursoEvento = new ArrayList<Curso>();

    @EJB
    private HorarioEventoFachada horarioEventoFachada;

    @EJB
    private CadastroEventoFachada cadastroEventoFachada;

    /**
     * Creates a new instance of CadastroEventoMB
     */
    public CadastroEventoMB() {
    }

    public void iniciaCadastroEvento() {

        Evento evento = new Evento();

        evento.setNomeEvento(getNomeEvento());
        evento.setNomePalestrante(getNomePalestrante());
        evento.setDataEvento(getDataEvento());
        evento.setHorarioInicio(getIdHoraInicio());
        evento.setDuracaoEvento(getHorasEvento());
        evento.setSala(new Sala(getIdSalaEvento()));
        evento.setCurso(new Curso(getIdCursoEvento()));
        evento.setObservacaoEvento(getObservacaoEvento());

        try {
            
            // 1 Passo. Verificar existencia do evento
            cadastroEventoFachada.validaExistenciaEventoDataFachada(evento);

            // 2 Passo. Verifica se a Soma da HORA mais a Duracao excede 24 Horas
            cadastroEventoFachada.validaSomatoriaHoraInicioDuracaoFachada(evento);

            // 3 Paso. Verifica se foi selecionada a Hora Inicio
            cadastroEventoFachada.validaHoraSelecionada(evento);
            
            // 4 Passo. Verifica se existe outro evento na mesma sala, dia e horario
            cadastroEventoFachada.validaExistenciaEventoSalaDiaHora(evento);
            
            // 5 Passo. Gravar o Evento caso nenhum erro tenha ocorrido
            cadastroEventoFachada.iniciaCadastroEventoFachada(evento);
            
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso",  "Evento " + evento.getNomeEvento() + " foi cadastrado.") );

            setNomePalestrante(null);
            setDataEvento(null);
            lstHoraInicioEvento = new ArrayList<HorarioEvento>();
            setObservacaoEvento(null);
            
        } catch (EventoDataExistenteException edee) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", edee.getMessage() + " - Evento: " + evento.getNomeEvento()));
        } catch (EventoHoraExcedenteException ehee) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", ehee.getMessage() + " - Soma : " + (evento.getHorarioInicio() + evento.getDuracaoEvento())));
        } catch (EventoSalaDiaHoraExistente eshe) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", eshe.getMessage() + " - Evento : " + evento.getNomeEvento()));
        } catch (EventoHoraInicioException ehie) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", ehie.getMessage()));
        }

    }

    /**
     * Atualizacao do Horario de Inicio. Esse metodo e responsavel por exibir os
     * horarios de inicio conforme o dia selecionado.
     */
    public void atualizaHoraInicio() {
        setLstHoraInicioEvento(horarioEventoFachada.recuperaHorarioEventoInicioFachada(getDataEvento()));
    }

    /**
     * Recuperar Unidade. Metodo responsavel por recuperar as Unidade existentes
     */
    public void recuperaDadosEventoListener() {
        lstSalaEvento.clear();
        lstCursoEvento.clear();
        setNomeEvento(null);
        setNomePalestrante(null);
        setDataEvento(null);
        lstHoraInicioEvento = new ArrayList<HorarioEvento>();
        setObservacaoEvento(null);

        setLstSalaEvento(cadastroEventoFachada.recuperaListaSalaFachada());

        setLstCursoEvento(cadastroEventoFachada.recuperaListaCursoFachada());

    }

    /**
     * Redirecionar para Cadastro de Evento. Metodo responsavel por direcionar o
     * usuario logado para a pagina de cadastro de Evento
     *
     * @return String
     */
    public String returnPageCadastrarEvento() {
        return "/pages/cadastro/evento/cadastroEvento.xhtml?faces-redirect=true";
    }

    /**
     * @return the nomeEvento
     */
    public String getNomeEvento() {
        return nomeEvento;
    }

    /**
     * @param nomeEvento the nomeEvento to set
     */
    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

    /**
     * @return the nomePalestrante
     */
    public String getNomePalestrante() {
        return nomePalestrante;
    }

    /**
     * @param nomePalestrante the nomePalestrante to set
     */
    public void setNomePalestrante(String nomePalestrante) {
        this.nomePalestrante = nomePalestrante;
    }

    /**
     * @return the dataEvento
     */
    public Date getDataEvento() {
        return dataEvento;
    }

    /**
     * @param dataEvento the dataEvento to set
     */
    public void setDataEvento(Date dataEvento) {
        this.dataEvento = dataEvento;
    }

    /**
     * @return the horarioInicio
     */
    public String getHorarioInicio() {
        return horarioInicio;
    }

    /**
     * @param horarioInicio the horarioInicio to set
     */
    public void setHorarioInicio(String horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    /**
     * @return the salaEvento
     */
    public Sala getSalaEvento() {
        return salaEvento;
    }

    /**
     * @param salaEvento the salaEvento to set
     */
    public void setSalaEvento(Sala salaEvento) {
        this.salaEvento = salaEvento;
    }

    /**
     * @return the cursoEvento
     */
    public Curso getCursoEvento() {
        return cursoEvento;
    }

    /**
     * @param cursoEvento the cursoEvento to set
     */
    public void setCursoEvento(Curso cursoEvento) {
        this.cursoEvento = cursoEvento;
    }

    /**
     * @return the observacaoEvento
     */
    public String getObservacaoEvento() {
        return observacaoEvento;
    }

    /**
     * @param observacaoEvento the observacaoEvento to set
     */
    public void setObservacaoEvento(String observacaoEvento) {
        this.observacaoEvento = observacaoEvento;
    }

    /**
     * @return the minimaDataSelecao
     */
    public Date getMinimaDataSelecao() {
        return minimaDataSelecao;
    }

    /**
     * @param minimaDataSelecao the minimaDataSelecao to set
     */
    public void setMinimaDataSelecao(Date minimaDataSelecao) {
        this.minimaDataSelecao = minimaDataSelecao;
    }

    /**
     * @return the maximaDataSelecao
     */
    public Date getMaximaDataSelecao() {
        return maximaDataSelecao;
    }

    /**
     * @param maximaDataSelecao the maximaDataSelecao to set
     */
    public void setMaximaDataSelecao(Date maximaDataSelecao) {
        this.maximaDataSelecao = maximaDataSelecao;
    }

    /**
     * @return the idHoraInicio
     */
    public Integer getIdHoraInicio() {
        return idHoraInicio;
    }

    /**
     * @param idHoraInicio the idHoraInicio to set
     */
    public void setIdHoraInicio(Integer idHoraInicio) {
        this.idHoraInicio = idHoraInicio;
    }

    /**
     * @return the lstHoraInicioEvento
     */
    public List<HorarioEvento> getLstHoraInicioEvento() {
        return lstHoraInicioEvento;
    }

    /**
     * @param lstHoraInicioEvento the lstHoraInicioEvento to set
     */
    public void setLstHoraInicioEvento(List<HorarioEvento> lstHoraInicioEvento) {
        this.lstHoraInicioEvento = lstHoraInicioEvento;
    }

    /**
     * @return the horasEvento
     */
    public Integer getHorasEvento() {
        return horasEvento;
    }

    /**
     * @param horasEvento the horasEvento to set
     */
    public void setHorasEvento(Integer horasEvento) {
        this.horasEvento = horasEvento;
    }

    /**
     * @return the idSalaEvento
     */
    public Integer getIdSalaEvento() {
        return idSalaEvento;
    }

    /**
     * @param idSalaEvento the idSalaEvento to set
     */
    public void setIdSalaEvento(Integer idSalaEvento) {
        this.idSalaEvento = idSalaEvento;
    }

    /**
     * @return the lstSalaEvento
     */
    public List<Sala> getLstSalaEvento() {
        return lstSalaEvento;
    }

    /**
     * @param lstSalaEvento the lstSalaEvento to set
     */
    public void setLstSalaEvento(List<Sala> lstSalaEvento) {
        this.lstSalaEvento = lstSalaEvento;
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

}
