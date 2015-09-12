/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

import br.com.gestaoeventos.bean.Grupos;
import br.com.gestaoeventos.bean.Usuario;
import br.com.gestaoeventos.fachada.CadastroUsuarioFachada;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author godoyeve
 */
public class ClasseTestadora {

    public static void main(String[] args) {

        Usuario us = new Usuario();
        us.setNomeCompleto("ttt");
        us.setEmail("ttt");
        us.setSenha("96e79218965eb72c92a549dd5a330112");
        us.setDataInicioCadastro(new Date());
        us.setDataFimCadastro(null);
        us.setIdUsuario(222222);

        // FIXADO
        Grupos grupos = new Grupos(4, "ALUNO");
        us.setGrupos(grupos);

        // Inicia processo de gravacao
        CadastroUsuarioFachada cuf = new CadastroUsuarioFachada();
        try {
            cuf.cadastrarUsuarioFachada(us);
        } catch (Exception ex) {
            Logger.getLogger(ClasseTestadora.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
