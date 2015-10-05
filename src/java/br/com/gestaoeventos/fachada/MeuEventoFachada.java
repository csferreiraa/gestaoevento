/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.fachada;

import br.com.gestaoeventos.bean.Evento;
import br.com.gestaoeventos.bean.Inscricao;
import br.com.gestaoeventos.bean.Usuario;
import br.com.gestaoeventos.dao.MeuEventoDAO;
import br.com.gestaoeventos.exceptions.InscricaoCancelamentoIndevidoException;
import br.com.gestaoeventos.exceptions.MeuEventoNaoEncontradoException;
import br.com.gestaoeventos.servicos.Converter;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author cleiton
 */
@Stateless
public class MeuEventoFachada {

    @EJB
    private MeuEventoDAO meuEventoDAO;

    @EJB
    private Converter conveter;
    
    private final static String CANCELAMENTO_INSCRICAO_IMPEDIDO = "ESSA INSCRICAO NAO PODE MAIS SER CANCELADA DEVIDO A DATA / HORA";

    
    /**
     * Cancelar Inscricao. Metodo que inicia a validacao da possibilidade do
     * cancelamento da inscricao.
     *
     * @param inscricao
     * @throws br.com.gestaoeventos.exceptions.InscricaoCancelamentoIndevidoException
     */
    public void cancelaInscricaoFachada(Inscricao inscricao) throws InscricaoCancelamentoIndevidoException {
        
        // Verifica possibilidade do cancelamento
        this.validaDataHoraCancelamentoInscricao(inscricao);
        
        // Cancela o evento
        meuEventoDAO.cancelaInscricaoDAO(inscricao);
    }

    /**
     * Verifica se pode ocorrer o cancelamento da inscricao. Metedo responsavel
     * por verificar se a inscricao pode ser cancelada.
     *
     * @param inscricao
     * @throws br.com.gestaoeventos.exceptions.InscricaoCancelamentoIndevidoException
     */
    public void validaDataHoraCancelamentoInscricao(Inscricao inscricao) throws InscricaoCancelamentoIndevidoException {

        Evento evento = inscricao.getEvento();

        try {
            // Data do Sistema e Evento da inscricao
            Date dataSistema = conveter.zerarHoraDatas(new Date());
            Date dataEventoInscricao = conveter.zerarHoraDatas(inscricao.getEvento().getDataEvento());

            // Hora do sistema
            Integer horaAtualSistema = 0;
            Date dataAtual = new Date();   // given date
            Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
            calendar.setTime(dataAtual);   // assigns calendar to given date 
            horaAtualSistema = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format

            // Verifica se a DATA do Evento e igual a DATA do Sistema
            // Se Sim, verifica se a HORA do evento inscrito e MENOR/IGUAL que a HORA do Sistema
            if(dataEventoInscricao.equals(dataSistema)){
                if(inscricao.getEvento().getHorarioInicio() <= horaAtualSistema){
                    throw new InscricaoCancelamentoIndevidoException(CANCELAMENTO_INSCRICAO_IMPEDIDO);
                }
            }
            // Verifica se a DATA do Evento e MENOR que a DATA do Sistema
            if (dataEventoInscricao.before(dataSistema)){
                throw new InscricaoCancelamentoIndevidoException(CANCELAMENTO_INSCRICAO_IMPEDIDO);
            }
            
        } catch (ParseException ex) {
            Logger.getLogger(MeuEventoFachada.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Recupera a lista de inscricoes. Metodo responsavel por recuperar uma
     * lista de inscricoes do usuario logado.
     *
     * @param usuario
     * @param dataInicio
     * @param dataFim
     * @param tipoPresenca
     * @return
     * @throws br.com.gestaoeventos.exceptions.MeuEventoNaoEncontradoException
     */
    public List<Inscricao> recuperaInscricoesFachada(Usuario usuario, Date dataInicio, Date dataFim, Character tipoPresenca) throws MeuEventoNaoEncontradoException {
        return meuEventoDAO.recuperaInscricoesDAO(usuario, dataInicio, dataFim, tipoPresenca);
    }

}
