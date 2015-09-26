/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.fachada;

import br.com.gestaoeventos.bean.Unidade;
import br.com.gestaoeventos.dao.UnidadeDAO;
import br.com.gestaoeventos.exceptions.UnidadeExistenteException;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author cleiton
 */
@Stateless
public class CadastroUnidadeFachada {
    
    @EJB
    UnidadeDAO unidadeDAO = new UnidadeDAO();
    
    public void cadastrarUnidadeFachada(Unidade unidade) throws UnidadeExistenteException{
        
        unidadeDAO.cadastrarUnidade(unidade);
        
    }
    
}
