/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.mbean;

import br.com.gestaoeventos.bean.Inscricao;
import br.com.gestaoeventos.bean.InscricaoAux;
import br.com.gestaoeventos.bean.Usuario;
import br.com.gestaoeventos.exceptions.InscricaoCancelamentoIndevidoException;
import br.com.gestaoeventos.exceptions.InscricaoNaoSelecionadaException;
import br.com.gestaoeventos.exceptions.MeuEventoNaoEncontradoException;
import br.com.gestaoeventos.fachada.MeuEventoFachada;
import br.com.gestaoeventos.servicos.Converter;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author cleiton
 */
@ManagedBean(name = "meuEventoMB")
@SessionScoped
public class MeuEventoMB {

    private Date dataEventoInicio;
    private Date dataEventoFim;
    private Character tipoPresenca;
    private List<Inscricao> lstInscricao = new ArrayList<Inscricao>();
    private List<InscricaoAux> lstInscricaoAux = new ArrayList<InscricaoAux>();

    @EJB
    private MeuEventoFachada meuEventoFachada;

    @EJB
    private Converter converter;

    private InscricaoAux inscricaoSelecionada;

    /**
     * Inicia o cancelamento da inscricao. Metodo res
     */
    public void iniciaCancelamentoEvento() {

        if (inscricaoSelecionada == null) {
            try {

                throw new InscricaoNaoSelecionadaException("NECESSÁRIO SELECIONAR UMA INSCRIÇÃO");

            } catch (InscricaoNaoSelecionadaException inse) {
                FacesContext context2 = FacesContext.getCurrentInstance();
                context2.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", inse.getMessage()));
            }
        } else {

            Inscricao i = new Inscricao();
            i.setIdInscricao(getInscricaoSelecionada().getIdInscricao());
            i.setUsuario(getInscricaoSelecionada().getUsuario());
            i.setPresencaAluno(getInscricaoSelecionada().getPresencaAluno());
            i.setEvento(getInscricaoSelecionada().getEvento());
            i.setDataEmailInscricao(getInscricaoSelecionada().getDataEmailInscricao());

            try {

                meuEventoFachada.cancelaInscricaoFachada(i);
                
                lstInscricao = new ArrayList<Inscricao>();
                lstInscricaoAux = new ArrayList<InscricaoAux>();

                FacesContext context3 = FacesContext.getCurrentInstance();
                context3.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "INSCRIÇÃO FOI REMOVIDA"));

            } catch (InscricaoCancelamentoIndevidoException icie) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", icie.getMessage()));

            }
        }

    }

    /**
     * Recuperar inscricoes. Metodo responsavel por recuperar inscricoes
     * realizadas
     */
    public void iniciaPesquisaMeuEvento() {

        lstInscricaoAux = new ArrayList<InscricaoAux>();

        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        Usuario usuario = (Usuario) session.getAttribute("userLoggedIn");

        try {
            setLstInscricao(meuEventoFachada.recuperaInscricoesFachada(usuario, getDataEventoInicio(), getDataEventoFim(), getTipoPresenca()));

            for (Inscricao i : lstInscricao) {
                InscricaoAux iAux = new InscricaoAux();
                iAux.setIdInscricao(i.getIdInscricao());
                iAux.setUsuario(i.getUsuario());
                iAux.setEvento(i.getEvento());
                iAux.setPresencaAluno(i.getPresencaAluno());
                iAux.setDataEventoString(converter.convertDateToString(i.getEvento().getDataEvento()));
                lstInscricaoAux.add(iAux);
            }

        } catch (MeuEventoNaoEncontradoException menee) {
            FacesContext contextErro = FacesContext.getCurrentInstance();
            contextErro.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", menee.getMessage()));
        }
    }

    /**
     * Redirecionar para Meus Eventos. Metodo responsavel por direcionar o
     * usuario logado para a pagina dos eventos registrados
     *
     * @return String
     */
    public String returnPageMeuEvento() {
        lstInscricao = new ArrayList<Inscricao>();
        lstInscricaoAux = new ArrayList<InscricaoAux>();
        return "/pages/pesquisa/meuEvento/meuEvento.xhtml?faces-redirect=true";
    }

    /**
     * @return the dataEventoInicio
     */
    public Date getDataEventoInicio() {
        return dataEventoInicio;
    }

    /**
     * @param dataEventoInicio the dataEventoInicio to set
     */
    public void setDataEventoInicio(Date dataEventoInicio) {
        this.dataEventoInicio = dataEventoInicio;
    }

    /**
     * @return the dataEventoFim
     */
    public Date getDataEventoFim() {
        return dataEventoFim;
    }

    /**
     * @param dataEventoFim the dataEventoFim to set
     */
    public void setDataEventoFim(Date dataEventoFim) {
        this.dataEventoFim = dataEventoFim;
    }

    /**
     * @return the tipoPresenca
     */
    public Character getTipoPresenca() {
        return tipoPresenca;
    }

    /**
     * @param tipoPresenca the tipoPresenca to set
     */
    public void setTipoPresenca(Character tipoPresenca) {
        this.tipoPresenca = tipoPresenca;
    }

    /**
     * @return the lstInscricao
     */
    public List<Inscricao> getLstInscricao() {
        return lstInscricao;
    }

    /**
     * @param lstInscricao the lstInscricao to set
     */
    public void setLstInscricao(List<Inscricao> lstInscricao) {
        this.lstInscricao = lstInscricao;
    }

    /**
     * @return the lstInscricaoAux
     */
    public List<InscricaoAux> getLstInscricaoAux() {
        return lstInscricaoAux;
    }

    /**
     * @param lstInscricaoAux the lstInscricaoAux to set
     */
    public void setLstInscricaoAux(List<InscricaoAux> lstInscricaoAux) {
        this.lstInscricaoAux = lstInscricaoAux;
    }

    /**
     * @return the inscricaoSelecionada
     */
    public InscricaoAux getInscricaoSelecionada() {
        return inscricaoSelecionada;
    }

    /**
     * @param inscricaoSelecionada the inscricaoSelecionada to set
     */
    public void setInscricaoSelecionada(InscricaoAux inscricaoSelecionada) {
        this.inscricaoSelecionada = inscricaoSelecionada;
    }

}
