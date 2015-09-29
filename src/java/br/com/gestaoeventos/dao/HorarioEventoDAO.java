/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.dao;

import java.util.Map;
import java.util.TreeMap;
import javax.ejb.Stateless;

/**
 *
 * @author cleiton
 */
@Stateless
public class HorarioEventoDAO {

    public Map<Integer, String> recuperaHorarioInicio() {

        Map<Integer, String> mapHorarioInicio = new TreeMap<Integer, String>();

        mapHorarioInicio.put(8, "8:00");
        mapHorarioInicio.put(9, "9:00");
        mapHorarioInicio.put(10, "10:00");
        mapHorarioInicio.put(11, "11:00");

        mapHorarioInicio.put(13, "13:00");
        mapHorarioInicio.put(14, "14:00");
        mapHorarioInicio.put(15, "15:00");
        mapHorarioInicio.put(16, "16:00");
        mapHorarioInicio.put(17, "17:00");

        mapHorarioInicio.put(19, "19:00");
        mapHorarioInicio.put(20, "20:00");
        mapHorarioInicio.put(21, "21:00");

        return mapHorarioInicio;
    }

}
