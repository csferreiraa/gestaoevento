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
import javax.faces.context.FacesContext;
import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Cleiton
 */
@Stateless
public class LoginFachada {

    @EJB
    private UsuarioDAO usuarioDAO;

    /**
     * LOGIN. Metodo responsavel por efetuar o login
     *
     * @param username
     * @param password
     * @return Usuario
     * @throws LoginException
     */    
    public Usuario efetuarLoginFachada(String username, String password) throws LoginException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        try {
            request.login(username, password);

            return usuarioDAO.getUserByName(username);
        } catch (ServletException ex) {
            throw new LoginException("Usuario ou Senha invalidos");
        }

    }

    /**
     * LOGOUT. Metodo responsavel por efetuar o logOut
     *
     * @throws LoginException
     */
    public void efetuarlogoutFachada() throws LoginException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        try {
            request.getSession().invalidate();
            request.logout();
        } catch (ServletException ex) {
            throw new LoginException("NAO_FOI_POSSIVEL_EFETUAR_O_LOGOUT");
        }
    }

}
