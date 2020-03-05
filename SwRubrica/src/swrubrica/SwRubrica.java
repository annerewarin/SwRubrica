/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swrubrica;

import gui.FinestraPrincipale;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author babya
 */
public class SwRubrica {
    
    public static Vector<Persona> contatti;
    public static FinestraPrincipale finestra;
    
    public SwRubrica(){
        contatti = new Vector<Persona>();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        SwRubrica r = new SwRubrica();
        r.initGUI();
    }
    
    /*
    Inizializza la finestra principale.
    */
    public void initGUI(){
        
        finestra = new FinestraPrincipale();
        finestra.setVisible(true);
    }
    
    /*
    Crea un contatto in Rubrica.
    */
    public static void creaContatto(Persona p) throws FileNotFoundException{
        if(contatti.contains(p)){
            JOptionPane.showMessageDialog(null, "Contatto gia' esistente.");
            System.out.println("Contatto gia' esistente.");
        }
        else{
            contatti.addElement(p);
            aggiornaFileSwRubrica();
            JOptionPane.showMessageDialog(null, p.getNome().toUpperCase()+" "+ p.getCognome().toUpperCase()+" aggiunto.");
            System.out.println("Persona: "+ p.toString() +" aggiunto.");
        }          
    }
    
    /*
    Modifica un contatto in Rubrica.
    */
    public void modificaContatto(Persona pv, Persona pn){
        if(contatti.contains(pv)){
            int ind = contatti.indexOf(pv);
            Persona per = contatti.get(ind);
            per.setNome(pn.nome);
            per.setCognome(pn.cognome);
            per.setIndirizzo(pn.indirizzo);
            per.setTelefono(pn.telefono);
            per.setEta(pn.eta);
            contatti.setElementAt(per, ind);
            JOptionPane.showMessageDialog(null, "Contatto modificato.");
            System.out.println("Contatto modificato.");
        }
        else{
            JOptionPane.showMessageDialog(null, "Contatto NON esistente.");
            System.out.println("Contatto non presente.");
        }
    }
    
    /*
    Elimina un contatto in Rubrica.
    */
    public static Persona eliminaContatto(Persona p){
        if(contatti.contains(p)){
            contatti.remove(p);
            JOptionPane.showMessageDialog(null, p.getNome().toUpperCase()+" "+p.getCognome().toUpperCase()+" eliminato.");
            System.out.println("Contatto eliminato.");
            return p;
        }
        else{
            JOptionPane.showMessageDialog(null, "Contatto NON esistente.");
            System.out.println("Contatto non presente.");
            return null;
        }
    }
    
    /*
    Aggiorna il file "informazioni.txt".
    */
    public static void aggiornaFileSwRubrica(){
        System.out.println("*** Lista -> File ***");
        try{
            FileOutputStream info = new FileOutputStream("informazioni.txt");
            PrintStream scrivi = new PrintStream(info);

            for(int i=0; i<SwRubrica.contatti.size(); i++)
                scrivi.println(SwRubrica.contatti.get(i).toString());
        }
        catch(FileNotFoundException ex){ System.err.println(ex);}
    }
    
    /*
    Aggiorna la lista Vector<Persona> contatti.
    */
    public static boolean aggiornaListaSwRubrica(){
        boolean vuota = true;
        System.out.println("*** File --> Lista ***");
        try{
            FileInputStream info = new FileInputStream("informazioni.txt");
            Scanner s = new Scanner(info);
            String nome= "";
            String cognome = "";
            String indirizzo="";
            String telefono = "";
            int eta =0;

            for(int index=0; s.hasNextLine(); index++)
            {
                System.out.println("index "+index);
                vuota = false;
                String line = s.nextLine();
                Scanner ls = new Scanner(line);
                ls.useDelimiter(";");
                for(int counter=0; ls.hasNext(); counter++){
                    String word = ls.next();

                    switch(counter){
                        case 0:
                            System.out.println(word);
                            nome = word;
                            break;
                        case 1:
                            System.out.println(word);
                            cognome = word;
                            break;
                        case 2:
                            System.out.println(word);
                            indirizzo = word;
                            break;
                        case 3:
                            System.out.println(word);
                            telefono = word;
                            break;
                        case 4:
                            System.out.println(word);
                            eta = Integer.parseInt(word);
                            break;
                        default:
                            break;
                    }
                }
                if(!vuota){
                    Persona p = new Persona(nome, cognome, indirizzo, telefono, eta);
                    if(!SwRubrica.contatti.contains(p))
                        SwRubrica.contatti.addElement(p);
                } 
                ls.close();
            }
            s.close();
        }
        catch(FileNotFoundException fnfe){System.err.println(fnfe);}
        
        return vuota;
    }
    
    /*
    Trova l'indice di un contatto dato il nome, cognome e telefono.
    */
    public int trovaIndice(String n, String c, String t){
        int ind =0;
        try{
            for(int i=0; i<contatti.size(); i++){
                Persona p = contatti.get(i);
                if((p.nome.compareTo(n)==0) && (p.cognome.compareTo(c)==0) && (p.telefono.compareTo(t)==0))
                    ind = contatti.indexOf(p);
            }            
        }
        catch(NullPointerException e){  System.err.println(e);  }
        return ind;
    }
}
