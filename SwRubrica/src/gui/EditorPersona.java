/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Color;
import swrubrica.Persona;
import swrubrica.SwRubrica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.Vector;
import swrubrica.DettagliConnessione;

/**
 *
 * @author anne
 */
public class EditorPersona extends javax.swing.JFrame {
    
    private boolean modificaFlag = false;
    private boolean creaFlag = false;
    private boolean salva = false;
    private boolean annulla = false;
   
    private String nomeV;
    private String cognomeV;
    private String indirizzoV;
    private String telefonoV;
    private int etaV;
    private Persona personaV;
    private int indexV;
    
    private String url = "";
    private String username = "";
    private String password = "";
    
    private Connection conn;
    private boolean connesso = false;
    
    private SwRubrica rubrica;
    private int userid;

    /**
     * Crea un nuovo form EditorPersona
     */
    public EditorPersona() {
        initComponents();
        DettagliConnessione dc = new DettagliConnessione();
        dc.settaDettagli();
        url = dc.getUrl();
        username = dc.getUsername();
        password = dc.getPassword();
        
        rubrica = new SwRubrica();
        
        rubrica.setContatti(rubrica.listaContatti());
        creaFlag = true;
        modificaFlag = false;
    }
    
    /**
     Costruttore EditorPersona per la creazione di un contatto.
     */
    public EditorPersona(int u) {
        initComponents();
        
        DettagliConnessione dc = new DettagliConnessione();
        dc.settaDettagli();
        url = dc.getUrl();
        username = dc.getUsername();
        password = dc.getPassword();
        
        rubrica = new SwRubrica();
        userid = u;
        rubrica.setContatti(getLista());
        creaFlag = true;
        modificaFlag = false;
    }
    
    /*
    Costruttore EditorPersona per la modifica di un contatto.
    */
    public EditorPersona(String n, String c, int i, int u) {
        initComponents();
        
        DettagliConnessione dc = new DettagliConnessione();
        dc.settaDettagli();
        url = dc.getUrl();
        username = dc.getUsername();
        password = dc.getPassword();
        
        rubrica = new SwRubrica();
        userid = u;
        rubrica.setContatti(getLista());
        
        Persona rp = trovaPersona(rubrica.getContatti(), i);
        
        personaV = rp;
        nomeV = rp.getNome();
        cognomeV = rp.getCognome();
        indirizzoV = rp.getIndirizzo();
        telefonoV = rp.getTelefono();
        etaV = rp.getEta();
        indexV = rp.getId();
        
        nomeTF.setText(rp.getNome());
        cognomeTF.setText(rp.getCognome());
        indirizzoTF.setText(rp.getIndirizzo());
        telefonoTF.setText(rp.getTelefono());
        String e = rp.getEta()+"";
        etaTF.setText(e);
        
        creaFlag = false;
        modificaFlag = true;
    }
    
    /*
    Trova la persona in vp con id uguale a i.
    */
    public Persona trovaPersona(Vector<Persona> vp, int i){
        
        boolean trovata = false;
        String nome ="";
        String cognome = "";
        String indirizzo = "";
        String telefono = "";
        int eta = 0;
        
        for(int c=0; c<vp.size(); c++){
            Persona p = vp.get(c);
            if(p.getId()==i){
                nome = p.getNome();
                cognome = p.getCognome();
                indirizzo = p.getIndirizzo();
                telefono = p.getTelefono();
                eta = p.getEta();
                trovata = true;
            }
        }
        if(trovata){
            Persona c = new Persona(nome, cognome, indirizzo, telefono, eta);
            c.setId(i);
            return c;
        }
        else
            return null;
    }
    
    /*
    Recupera la lista di contatti dell'utente corrente.
    */
    public Vector<Persona> getLista(){
        Vector<Persona> lista = new Vector<Persona>();
        try {
            conn = DriverManager.getConnection(url, username, password);
            connesso = true;
            System.out.println("Connesso a PostgreSQL server.");

            PreparedStatement stat = conn.prepareStatement("select * from persone where userid=?;");    
            stat.setInt(1, userid);               
            
            ResultSet rs = stat.executeQuery();
            
            while(rs.next()){
                int index = rs.getInt("id");
                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");
                String indirizzo = rs.getString("indirizzo");
                String telefono = rs.getString("telefono");
                int eta = rs.getInt("eta");
                System.out.println(index+" "+nome+" "+cognome+" "+indirizzo+" "+telefono+" "+eta);
                Persona p = new Persona(nome, cognome, indirizzo, telefono, eta);
                p.setId(index);
                lista.add(p);
            }

            rs.close(); 

            conn.setAutoCommit(false);
            
            conn.setAutoCommit(true);

            conn.close();
        } catch (SQLException e) {
                System.out.println(e.getMessage());
        }
        return lista;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        salvaB = new javax.swing.JButton();
        annullaB = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        nomeTF = new javax.swing.JTextField();
        cognomeTF = new javax.swing.JTextField();
        indirizzoTF = new javax.swing.JTextField();
        telefonoTF = new javax.swing.JTextField();
        etaTF = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Editor Persona");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jToolBar1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jToolBar1.setRollover(true);

        salvaB.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        salvaB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/salva.png"))); // NOI18N
        salvaB.setText("Salva");
        salvaB.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        salvaB.setFocusable(false);
        salvaB.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        salvaB.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        salvaB.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                salvaBFocusGained(evt);
            }
        });
        salvaB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salvaBActionPerformed(evt);
            }
        });
        jToolBar1.add(salvaB);

        annullaB.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        annullaB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/annulla.png"))); // NOI18N
        annullaB.setText("Annulla");
        annullaB.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        annullaB.setFocusable(false);
        annullaB.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        annullaB.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        annullaB.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                annullaBFocusGained(evt);
            }
        });
        annullaB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                annullaBActionPerformed(evt);
            }
        });
        jToolBar1.add(annullaB);

        jLabel1.setText("Nome");

        jLabel2.setText("Cognome");

        jLabel3.setText("Indirizzo");

        jLabel4.setText("Telefono");

        jLabel5.setText("Età");

        nomeTF.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        nomeTF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nomeTFMouseClicked(evt);
            }
        });

        cognomeTF.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        cognomeTF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cognomeTFMouseClicked(evt);
            }
        });

        indirizzoTF.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        indirizzoTF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                indirizzoTFMouseClicked(evt);
            }
        });

        telefonoTF.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        telefonoTF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                telefonoTFMouseClicked(evt);
            }
        });

        etaTF.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        etaTF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                etaTFMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(74, 74, 74)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(nomeTF)
                    .addComponent(cognomeTF)
                    .addComponent(indirizzoTF)
                    .addComponent(telefonoTF)
                    .addComponent(etaTF, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(nomeTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2))
                    .addComponent(cognomeTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(indirizzoTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(telefonoTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(etaTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void salvaBFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_salvaBFocusGained
        
        salvaB.setBackground(Color.cyan);
    }//GEN-LAST:event_salvaBFocusGained

    private void annullaBFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_annullaBFocusGained
        
        annullaB.setBackground(Color.cyan);
    }//GEN-LAST:event_annullaBFocusGained

    private void salvaBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salvaBActionPerformed
        salva = true;
        boolean valida = true;
        boolean nuovaPersonaFlag = true;
        String nome = nomeTF.getText();
        if(nome.equals("")){
            nomeTF.setBackground(Color.red);
            valida = false;
        }
        
        String cognome = cognomeTF.getText();
        if(cognome.equals("")){
            cognomeTF.setBackground(Color.red);
            valida = false;
        }
   
        String indirizzo = indirizzoTF.getText();
        if(indirizzo.equals("")){
            indirizzoTF.setBackground(Color.red);
            valida = false;
        }
        
        String telefono = telefonoTF.getText();
        if(telefono.equals("")){
            telefonoTF.setBackground(Color.red);
            valida = false;
        }
        
        int eta =0;
        try {
            eta = Integer.parseInt(etaTF.getText());
            if (eta <= 0 || eta>=130){
                etaTF.setBackground(Color.red);
                valida = false;
            }      
        } catch (NumberFormatException e) {
            etaTF.setBackground(Color.red);
            valida = false;
            System.err.println(e);
        }
        
        Persona p = new Persona(nome, cognome, indirizzo, telefono, eta);
        int indexMax = 0;
        for(int i=0; i<rubrica.getContatti().size(); i++){
            Persona c = rubrica.getContatti().get(i);
            if(c.getId()>indexMax)
                indexMax = c.getId(); 
        }
        int indexP = indexMax+1;
        p.setId(indexP);
        if(creaFlag && valida){
            
            System.out.println("Crea contatto");
            //rubrica.getContatti().add(p);
            rubrica.creaContatto(p);
            //int index = rubrica.trovaIndice(nome, cognome, telefono);
            
            try {
                conn = DriverManager.getConnection(url, username, password);
                connesso = true;
                System.out.println("Connesso a PostgreSQL server.");
                System.out.println("Nel salvaCreaAction");

                Statement stat = conn.createStatement();

                String sql = "insert into persone values(?,?,?,?,?,?,?);";

                PreparedStatement ps = conn.prepareStatement(sql);

                ps.setInt(1, userid);
                ps.setInt(2, indexP);
                System.out.print(indexP+" ");
                ps.setString(3, nome);
                System.out.print(nome+" ");
                ps.setString(4, cognome);
                System.out.print(cognome+" ");
                ps.setString(5, indirizzo);
                System.out.print(indirizzo+" ");
                ps.setString(6, telefono);
                System.out.print(telefono+" ");
                ps.setInt(7, eta);
                System.out.println(eta);
                ps.addBatch();

                conn.setAutoCommit(false);
                ps.executeBatch();
                conn.setAutoCommit(true);

                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            creaFlag = false;
            
                
       
        }
        if(modificaFlag && valida){           
                    
            int index = indexV;
                
            try {
                conn = DriverManager.getConnection(url, username, password);
                connesso = true;
                System.out.println("Connesso a PostgreSQL server.");

                Statement stat = conn.createStatement();
         
                String sql = "update persone set nome=?, cognome=?, indirizzo=?, telefono=?, eta=? where id=? and userid=?;";
              
                PreparedStatement ps = conn.prepareStatement(sql);
               
                ps.setString(1, nome);
                ps.setString(2, cognome);
                ps.setString(3, indirizzo);
                ps.setString(4, telefono);
                ps.setInt(5, eta);
                ps.setInt(6, index);
                ps.setInt(7, userid);
                ps.addBatch();

                conn.setAutoCommit(false);
                ps.executeBatch();
                conn.setAutoCommit(true);

                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }        
            
            modificaFlag = false;

        }
        
        if(valida){
            
            super.dispose();
            FinestraPrincipale fp = new FinestraPrincipale(userid);
            fp.setVisible(true);
        }

    }//GEN-LAST:event_salvaBActionPerformed

    private void annullaBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_annullaBActionPerformed
        annulla = true;
        FinestraPrincipale fp = new FinestraPrincipale(userid);
        fp.setVisible(true);
        super.dispose();
    }//GEN-LAST:event_annullaBActionPerformed

    private void nomeTFMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nomeTFMouseClicked
        
        nomeTF.setBackground(Color.white);
    }//GEN-LAST:event_nomeTFMouseClicked

    private void cognomeTFMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cognomeTFMouseClicked
        
        cognomeTF.setBackground(Color.white);
    }//GEN-LAST:event_cognomeTFMouseClicked

    private void indirizzoTFMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_indirizzoTFMouseClicked
        
        indirizzoTF.setBackground(Color.white);
    }//GEN-LAST:event_indirizzoTFMouseClicked

    private void telefonoTFMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_telefonoTFMouseClicked
        
        telefonoTF.setBackground(Color.white);
    }//GEN-LAST:event_telefonoTFMouseClicked

    private void etaTFMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_etaTFMouseClicked
        
        etaTF.setBackground(Color.white);
    }//GEN-LAST:event_etaTFMouseClicked

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        if(!salva && !annulla){
            FinestraPrincipale fp = new FinestraPrincipale(userid);
            fp.setVisible(true);
        }
    }//GEN-LAST:event_formWindowClosed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EditorPersona.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditorPersona.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditorPersona.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditorPersona.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditorPersona().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton annullaB;
    private javax.swing.JTextField cognomeTF;
    private javax.swing.JTextField etaTF;
    private javax.swing.JTextField indirizzoTF;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField nomeTF;
    private javax.swing.JButton salvaB;
    private javax.swing.JTextField telefonoTF;
    // End of variables declaration//GEN-END:variables
}
