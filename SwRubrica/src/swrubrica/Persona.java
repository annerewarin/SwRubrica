/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swrubrica;

/**
 *
 * @author babya
 */
public class Persona {
    protected String nome;
    protected String cognome;
    protected String indirizzo;
    protected String telefono;
    protected int eta;
    
    public Persona(String n, String c, String i, String t, int e){
        this.nome = n;
        this.cognome = c;
        this.indirizzo = i;
        this.telefono = t;
        this.eta = e;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getEta() {
        return eta;
    }

    public void setEta(int eta) {
        this.eta = eta;
    }
    
    public String toString(){
        return this.nome+";"+this.cognome+";"+this.indirizzo+";"+this.telefono+";"+this.eta;
    }
}
