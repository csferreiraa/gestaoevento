/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.fachada;

import br.com.gestaoeventos.bean.Usuario;
import br.com.gestaoeventos.dao.UsuarioDAO;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Cleiton
 */
@Stateless
public class CadastroUsuarioFachada {
    
    @EJB
    private UsuarioDAO usuarioDAO;
    
    /**
     * Cadastro Usuario Fachada. Metodo responsavel por executar o DAO para 
     * gravar o usuario.
     * 
     * @param usuario 
     */
    public void cadastrarUsuarioFachada(Usuario usuario) throws Exception{
        usuarioDAO.cadastrarUsuario(usuario);
    }
    
}
