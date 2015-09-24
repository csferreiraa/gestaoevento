/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

import br.com.gestaoeventos.bean.Grupos;
import br.com.gestaoeventos.bean.Usuario;
import br.com.gestaoeventos.fachada.CadastroUsuarioFachada;
import br.com.gestaoeventos.servicos.GeraSenha;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author godoyeve
 */
public class ClasseTestadora {

    public static void main(String[] args) {
        GeraSenha gs = new GeraSenha();
        System.out.println("A senha eh: " + gs.geraSenhaUsuario());
    }

   

}
