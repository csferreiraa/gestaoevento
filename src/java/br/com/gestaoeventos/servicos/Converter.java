/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestaoeventos.servicos;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author Cleiton
 */
@Stateless
public class Converter {
    
    /**
     * String to MD5. Metodo responsavel por converter a Senha digitada pelo
     * Usuario para o formato MD5 de criptografia.
     * 
     * @param password
     * @return String
     */
    public String stringToMD5(String password) {

        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(password.getBytes(), 0, password.length());
            //System.out.println("MD5: "+ new BigInteger(1,m.digest()).toString(16));
            return new BigInteger(1, m.digest()).toString(16);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    /**
     * Converte Date To String. Metodo responsavel por convertar Data para String
     * @param data
     * @return 
     */
    public String convertDateToString(Date data){
        
        String resultado;
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
	resultado = sdf.format(data); 

        return resultado;
    }
    
    /**
     * Retirar Hora da Data. Metodo responsavel por retirar a hora da Data.
     * @param data
     * @return
     * @throws ParseException 
     */
    public Date zerarHoraDatas(Date data) throws ParseException {
        DateFormat dtFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dtFormat.parse(dtFormat.format(data));
    }
    
}
