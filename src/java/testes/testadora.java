/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author godoyeve
 */
public class testadora {

    public static void main(String[] args) throws ParseException {
        // primeiro conjunto
        Set<Integer> conjuntoA = new TreeSet<>();
        conjuntoA.add(2);
        conjuntoA.add(5);
        conjuntoA.add(8);
        conjuntoA.add(19);
        conjuntoA.add(30);

        // segundo conjunto
        Set<Integer> conjuntoB = new TreeSet<>();
        conjuntoB.add(2);
        conjuntoB.add(3);
        conjuntoB.add(1);
        conjuntoB.add(30);

        // vamos obter a interseção dos dois conjuntos      
        Set<Integer> conjuntoC = intersecao(conjuntoA, conjuntoB);

        // vamos exibir os elementos no conjunto C
        Iterator iterator = conjuntoC.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        
        System.out.println(zerarHoraDatas(new Date()));
    }
    
        private static Date zerarHoraDatas(Date data) throws ParseException {
        DateFormat dtFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dtFormat.parse(dtFormat.format(data));
    }
    
    

    // método genérico que permite obter a interseção de dois conjuntos
    public static <T> Set<T> intersecao(Set<T> conjA, Set<T> conjB) {
        Set<T> conjC = new TreeSet<>();
        // percorremos todos os elementos do conjunto A
        for (T elemento : conjA) {
            // e verificamos se o elemento está contido no conjunto B
            if (conjB.contains(elemento)) {
                conjC.add(elemento); // se estiver contido nós o adicionamos no conjunto C 
            }
        }

        return conjC; // e retornamos o conjunto C
    }

    /*  

     public static void main(String[] args) {
     Date date = new Date();   // given date
     Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
     calendar.setTime(date);   // assigns calendar to given date 
     System.out.println(calendar.get(Calendar.HOUR_OF_DAY)); // gets hour in 24h format
     System.out.println(calendar.get(Calendar.DAY_OF_MONTH)); 
     System.out.println(calendar.get(Calendar.MONTH) + 1); 
     System.out.println(calendar.get(Calendar.YEAR)); */
    /* HorarioEventoFachada hef = new HorarioEventoFachada();
     Map<Integer, String> mapa = hef.recuperaHorarioEventoInicioFachada();
        
     Iterator entries = mapa.entrySet().iterator();
        
     List<HorarioEvento> lstHoraEvento = new ArrayList<HorarioEvento>();
        
     while (entries.hasNext()) {
     Entry thisEntry = (Entry) entries.next();

     Integer key =(Integer) thisEntry.getKey();
            
     if(key > calendar.get(Calendar.HOUR_OF_DAY)){
     HorarioEvento he = new HorarioEvento();
     he.setIdHorario((Integer) thisEntry.getKey());
     he.setDescricaoHorario(thisEntry.getValue().toString());
     lstHoraEvento.add(he);
     }           
     }

        
     for(HorarioEvento hev : lstHoraEvento){
     System.out.println("");
     System.out.println("ID " + hev.getIdHorario());
     System.out.println("HORARIO " + hev.getDescricaoHorario());
     }
     System.out.println("Tamanho da lisa " + lstHoraEvento.size());
       
        
        
        
        
        
     }*/
}
