/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.fachada;

import br.com.gestaoeventos.bean.Curso;
import br.com.gestaoeventos.dao.CursoDAO;
import br.com.gestaoeventos.exceptions.CursoExistenteException;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author cleiton
 */
@Stateless
public class CadastroCursoFachada {
    
    @EJB
    private CursoDAO cursoDAO;
    
    
    public void cadastrarCursoFachada(Curso Curso) throws CursoExistenteException{
        
        cursoDAO.cadastrarCursoDAO(Curso);
       
    }
    
}
