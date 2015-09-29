/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.fachada;

import br.com.gestaoeventos.bean.HorarioEvento;
import br.com.gestaoeventos.dao.HorarioEventoDAO;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author cleiton
 */
@Stateless
public class HorarioEventoFachada {

    @EJB
    private HorarioEventoDAO horarioEventoDAO;
    private Integer horaAtual;

    public List<HorarioEvento> recuperaHorarioEventoInicioFachada(Date diaEvento) {

        // Declarando a lista de horarios iniciais ainda vazia
        List<HorarioEvento> lstHoraEvento = new ArrayList<HorarioEvento>();

        // Obter a lista de Horas INICIAIS
        Map<Integer, String> mapHorarioInicio = horarioEventoDAO.recuperaHorarioInicio();

        // **** Tratamento de Exibicao ****
        if (validaHoraExibicaoInicio(diaEvento).equals(1)) {
            // Obter a HORA atual
            Date dataAtual = new Date();   // given date
            Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
            calendar.setTime(dataAtual);   // assigns calendar to given date 
            setHoraAtual(calendar.get(Calendar.HOUR_OF_DAY)); // gets hour in 24h format

            // Analisando cada item do Map, Add apenas a HORA que for maior que a HORA atual
            Iterator entries = mapHorarioInicio.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry thisEntry = (Map.Entry) entries.next();

                Integer key = (Integer) thisEntry.getKey();

                if (key > (calendar.get(Calendar.HOUR_OF_DAY)) + 2) {
                    HorarioEvento he = new HorarioEvento();
                    he.setIdHorario((Integer) thisEntry.getKey());
                    he.setDescricaoHorario(thisEntry.getValue().toString());
                    lstHoraEvento.add(he);
                }
            }
        } else if (validaHoraExibicaoInicio(diaEvento).equals(2)) {

            Iterator entries = mapHorarioInicio.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry thisEntry = (Map.Entry) entries.next();

                HorarioEvento he = new HorarioEvento();
                he.setIdHorario((Integer) thisEntry.getKey());
                he.setDescricaoHorario(thisEntry.getValue().toString());
                lstHoraEvento.add(he);
            }
        }

        return lstHoraEvento;
    }

    /**
     * Analisa a necessidade de filtrar a HORA. Metodo responsavel por verificar
     * a necessidade de filtrar HORA de inicio
     *
     * @param dataSelecionadaPeloUsuario
     * @return Boolean
     */
    public Integer validaHoraExibicaoInicio(Date dataSelecionadaPeloUsuario) {

        Date dataAtual = new Date();   // given date
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(dataAtual);   // assigns calendar to given 

        Integer diaAtual = calendar.get(Calendar.DAY_OF_MONTH);
        Integer mesAtual = calendar.get(Calendar.MONTH) + 1;
        Integer anoAtual = calendar.get(Calendar.YEAR);

        // Tratamento dos dados do usuario
        Calendar calendarUsuario = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendarUsuario.setTime(dataSelecionadaPeloUsuario);   // assigns calendar to given 

        Integer diaUsuario = calendarUsuario.get(Calendar.DAY_OF_MONTH);
        Integer mesUsuario = calendarUsuario.get(Calendar.MONTH) + 1;
        Integer anoUsuario = calendarUsuario.get(Calendar.YEAR);

        /* 1. Tem que filtrar horario
         * 2. Nao Precisa  filtrar horario
         * 999. Nao permite carregar nada
         */
        if (anoUsuario >= anoAtual) {
            if (mesUsuario > mesAtual) {
                System.out.println("ponto a");
                return 2;
            } else if (mesUsuario.equals(mesAtual)) {
                if (diaUsuario < diaAtual) {
                    System.out.println("ponto b");
                    return 999;
                } else if (diaUsuario.equals(diaAtual)) {
                    System.out.println("ponto c");
                    return 1;
                } else {
                    System.out.println("ponto d");
                    return 2;
                }
            } else {
                System.out.println("ponto e");
                return 999;
            }
        } else {
            System.out.println("ponto f");
            return 999;
        }

    }

    /**
     * @return the horaAtual
     */
    public Integer getHoraAtual() {
        return horaAtual;
    }

    /**
     * @param horaAtual the horaAtual to set
     */
    public void setHoraAtual(Integer horaAtual) {
        this.horaAtual = horaAtual;
    }

}
