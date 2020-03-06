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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;


/**
 *
 * @author babya
 */
public class SwRubrica {
    
    private Vector<Persona> contatti;
    private FinestraPrincipale finestra;
    private final String url = "jdbc:postgresql://localhost:5432/rubrica_postgresql";
    private final String username = "rubrica";
    private final String password = "rubric@";
    private Connection conn;
    private boolean connesso = false;
    
    public SwRubrica(){
        contatti = new Vector<Persona>();
    }
    
    public Vector<Persona> getContatti(){
        return this.contatti;
    }
    
    public void setContatti(Vector<Persona> c){
        this.contatti = c;
    }
    
    /*
    Recupera tutti i contatti della rubrica dal file.
    */
    public Vector<Persona> listaContatti(){
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
                    if(!contatti.contains(p))
                        contatti.addElement(p);
                } 
                ls.close();
            }
            s.close();
        }
        catch(FileNotFoundException fnfe){System.err.println(fnfe);}
        
        return contatti;
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
    public void creaContatto(Persona p) throws FileNotFoundException{
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
    public void modificaContatto(int i, Persona pn){
    
            Persona per = contatti.get(i);
            per.setNome(pn.nome);
            per.setCognome(pn.cognome);
            per.setIndirizzo(pn.indirizzo);
            per.setTelefono(pn.telefono);
            per.setEta(pn.eta);
            contatti.setElementAt(per, i);
            
            aggiornaFileSwRubrica();
            
            JOptionPane.showMessageDialog(null, "Contatto modificato.");
            System.out.println("Contatto modificato.");

    }
    
    /*
    Elimina un contatto in Rubrica.
    */
    public Persona eliminaContatto(Persona p){
        if(contatti.contains(p)){
            contatti.remove(p);
            
            aggiornaFileSwRubrica();
           
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
    public void aggiornaFileSwRubrica(){
        
        System.out.println("*** Lista -> File ***");
        try{
            FileOutputStream info = new FileOutputStream("informazioni.txt");
            PrintStream scrivi = new PrintStream(info);

            for(int i=0; i<contatti.size(); i++)
                scrivi.println(contatti.get(i).toString());
        }
        catch(FileNotFoundException ex){System.out.println("nel aggiornaFileSwRubrica"); System.err.println(ex);}
    }
    
    /*
    Aggiorna la lista Vector<Persona> contatti.
    */
    public void aggiornaListaSwRubrica(){
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
                //System.out.println("index "+index);
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
                    if(!contatti.contains(p))
                        contatti.addElement(p);
                } 
                ls.close();
            }
            s.close();
        }
        catch(FileNotFoundException fnfe){System.err.println(fnfe);}

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
