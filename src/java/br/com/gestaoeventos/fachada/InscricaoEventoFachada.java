/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.fachada;

import br.com.gestaoeventos.bean.Inscricao;
import br.com.gestaoeventos.dao.InscricaoDAO;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author cleiton
 */
@Stateless
public class InscricaoEventoFachada {
    
    @EJB
    private InscricaoDAO inscricaoDAO;
    
    /**
     * Gravar Inscricao Fachada. Metodo da fachada responsavel por gravar inscricao
     * @param inscricao 
     */
    public void gravarInscricaoFachada(Inscricao inscricao){
        inscricaoDAO.gravarInscricaoDAO(inscricao);
    }
    
}
