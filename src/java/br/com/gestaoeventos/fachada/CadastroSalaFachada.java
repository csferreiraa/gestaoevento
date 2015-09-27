/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.fachada;

import br.com.gestaoeventos.bean.Grupos;
import br.com.gestaoeventos.bean.Sala;
import br.com.gestaoeventos.bean.Unidade;
import br.com.gestaoeventos.dao.SalaDAO;
import br.com.gestaoeventos.exceptions.SalaExistenteException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author cleiton
 */
@Stateless
public class CadastroSalaFachada {
    
    @EJB
    private SalaDAO salaDAO;
    
    
    
    public void cadastrarSalaFachada(Sala sala) throws SalaExistenteException{
        
        salaDAO.cadastrarSalaDAO(sala);
        
    }
    
    
    /**
     * Recuperar Unidade. Metodo responsavel por recuperar as Unidades
     * disponiveis para associar com o cadastro de novas salas
     * 
     * @return List<Unidade>
     */
    public List<Unidade> retrieveUnidadeSalaFachada(){
        return salaDAO.retrieveUnidadeSalaDAO();
    }
}
