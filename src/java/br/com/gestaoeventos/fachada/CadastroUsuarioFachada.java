/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.fachada;

import br.com.gestaoeventos.bean.Grupos;
import br.com.gestaoeventos.bean.Usuario;
import br.com.gestaoeventos.dao.UsuarioDAO;
import br.com.gestaoeventos.exceptions.UsuarioExistenteException;
import java.util.List;
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
    private static final String EMAIL_EXISTENTE = "J&aacute; existe outro usu&aacute; com o mesmo e-mail cadastrado na base de dados";
    
    /**
     * Cadastro Usuario Fachada. Metodo responsavel por executar o DAO para 
     * gravar o usuario.
     * 
     * @param usuario
     * @throws br.com.gestaoeventos.exceptions.UsuarioExistenteException
     */
    public void cadastrarUsuarioFachada(Usuario usuario) throws UsuarioExistenteException {

       usuarioDAO.cadastrarUsuario(usuario);
        
    }
    
    /**
     * Recuperar Grupos de Usuario do sistema. Metodo responsavel por recuperar
     * os Grupos de Usuario para cadastro de um Novo Usuario
     * 
     * @return List<Grupousuario>
     */
    public List<Grupos> retrieveGruposFachada(){
        return usuarioDAO.retrieveGruposDAO();
    }
    
}
