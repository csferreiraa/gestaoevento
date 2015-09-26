/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.dao;

import br.com.gestaoeventos.bean.Unidade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author cleiton
 */
@Stateless
public class SalaDAO {
    
    @PersistenceContext
    private EntityManager em;
    
    public List<Unidade> retrieveUnidadeSalaDAO(){
        List<Unidade> lstUnidade = new ArrayList<Unidade>();
        
        lstUnidade = em.createQuery("SELECT U FROM Unidade U").getResultList();
        
        return lstUnidade;
    }
}
