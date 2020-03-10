/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
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
public class FinestraPrincipale extends javax.swing.JFrame {
    
    DefaultTableModel model;
    private boolean selected=false;
    private int indexSel;
    private int indexSelTab;
    private static String nomeSel;
    private static String cognomeSel;
    private static String telefonoSel;
    
    private String url = "";
    private String username = "";
    private String password = "";
    
    private Connection conn;
    private boolean connesso = false;
    
    private SwRubrica rubrica;
    private int userid;

    /**
     * Creates new form FinestraPrincipale
     */
    public FinestraPrincipale() {
        initComponents();
        
        DettagliConnessione dc = new DettagliConnessione();
        dc.settaDettagli();
        url = dc.getUrl();
        username = dc.getUsername();
        password = dc.getPassword();
        
        rubrica = new SwRubrica();
        
        //initJTable();
        
        initSelectorListener();
    }
    
    /*
    Costruisce una Finestra Principale per un utente specificato.
    */
    public FinestraPrincipale(int u) {
        initComponents();
        
        DettagliConnessione dc = new DettagliConnessione();
        dc.settaDettagli();
        url = dc.getUrl();
        username = dc.getUsername();
        password = dc.getPassword();
        
        rubrica = new SwRubrica();
        userid = u;
        rubrica.setContatti(getLista(userid));
        initJTable();
        
        initSelectorListener();
    }
    
    /*
    Inizializza la tabella della Finestra Principale.
    */
    public void initJTable(){
        
        model = (DefaultTableModel) table.getModel();
        
        Object rowData[] = new Object[3];
       
        if(!rubrica.getContatti().isEmpty()){
            
            for(int ii=0; ii<rubrica.getContatti().size(); ii++){
                System.out.println(rubrica.getContatti().get(ii).toString());

                rowData[0] = rubrica.getContatti().get(ii).getNome();
                rowData[1] = rubrica.getContatti().get(ii).getCognome();
                rowData[2] = rubrica.getContatti().get(ii).getTelefono();
                model.addRow(rowData);
            }  
        }        
    }
    
    /*
    Inizializza il SelectionListener.
    */
    public void initSelectorListener(){
        ListSelectionModel cellSelectionModel = table.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
          public void valueChanged(ListSelectionEvent e) {
            String selectedData = null;

            int[] selectedRow = table.getSelectedRows();
            if(selectedRow.length==0){
                selected=false;
               
            }
            else
                selected=true;
            
            String nome = null;
            String cognome = null;
            String telefono = null;
            int index = 0;
            indexSel=0;
            indexSelTab=table.getSelectedRow();;
          
            for (int i = 0; i < selectedRow.length; i++, index++) {
              
                nome = (String) table.getValueAt(selectedRow[i], 0);
                cognome = (String) table.getValueAt(selectedRow[i], 1);
                telefono = (String) table.getValueAt(selectedRow[i], 2);
     
            }
 
            nomeSel = nome;
            cognomeSel = cognome;
            telefonoSel = telefono;
            int ind =0;
            try{
                for(int i=0; i<rubrica.getContatti().size(); i++){
                    Persona p = rubrica.getContatti().get(i);
                    if((p.getNome().compareTo(nomeSel)==0) && (p.getCognome().compareTo(cognomeSel)==0) && (p.getTelefono().compareTo(telefonoSel)==0))
                        ind = p.getId();
                }
            
            }
            catch(NullPointerException e2){  System.out.println("Nel initSelectorListener");System.err.println(e2);  }
            
            indexSel = ind;
            System.out.println("Selected: " + nome + " " + cognome+ " "+indexSel);
            System.out.println("Selected row: " + nome + " " + cognome+ " "+indexSelTab);
          }

        });
    }
    
    /*
    Recupera la lista di contatti dell'utente corrente.
    */
    public Vector<Persona> getLista(int u){
        Vector<Persona> lista = new Vector<Persona>();
        Vector<Persona> listaNuova = new Vector<Persona>();
        try {
            conn = DriverManager.getConnection(url, username, password);
            connesso = true;
            System.out.println("Connesso a PostgreSQL server.");

            PreparedStatement stat = conn.prepareStatement("select * from persone where userid=?;");    
            stat.setInt(1, u);    

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
    
    /*
    Aggiorna la tabella della Finestra Principale dopo una creazione.
    */
    public void aggiornaFinestra(Persona p, int i){
        model = (DefaultTableModel) table.getModel();
        
        Object rowData[] = new Object[3];

        rowData[0] = p.getNome();
        rowData[1] = p.getCognome();
        rowData[2] = p.getTelefono();
        
        model.insertRow(i, rowData);
   
    }
    
    /*
    Modifica della tabella della Finestra Principale dopo una modifica.
    */
    public void modificaFinestra(int i){
        model = (DefaultTableModel) table.getModel();
        
        Object rowData[] = new Object[3];
         
        rowData[0] = rubrica.getContatti().get(i).getNome();
        rowData[1] = rubrica.getContatti().get(i).getCognome();
        rowData[2] = rubrica.getContatti().get(i).getTelefono();

        model.removeRow(i);
        model.insertRow(i, rowData);

    }
    
    /*
    Elimina di un elemento della tabella della Finestra Principale dopo eliminazione.
    */
    public void eliminaFinestra(int i){
        model = (DefaultTableModel) table.getModel();

        model.removeRow(i);
 
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
        creaB = new javax.swing.JButton();
        modificaB = new javax.swing.JButton();
        eliminaB = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        logoutB = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Rubrica");
        setBackground(new java.awt.Color(204, 255, 255));

        jToolBar1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jToolBar1.setRollover(true);

        creaB.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        creaB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/crea.png"))); // NOI18N
        creaB.setText("Crea");
        creaB.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        creaB.setFocusable(false);
        creaB.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        creaB.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        creaB.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                creaBFocusGained(evt);
            }
        });
        creaB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                creaBActionPerformed(evt);
            }
        });
        jToolBar1.add(creaB);

        modificaB.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        modificaB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/edit.png"))); // NOI18N
        modificaB.setText("Modifica");
        modificaB.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        modificaB.setFocusable(false);
        modificaB.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        modificaB.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        modificaB.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                modificaBFocusGained(evt);
            }
        });
        modificaB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificaBActionPerformed(evt);
            }
        });
        jToolBar1.add(modificaB);

        eliminaB.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        eliminaB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/elimina.png"))); // NOI18N
        eliminaB.setText("Elimina");
        eliminaB.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eliminaB.setFocusable(false);
        eliminaB.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        eliminaB.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        eliminaB.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                eliminaBFocusGained(evt);
            }
        });
        eliminaB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminaBActionPerformed(evt);
            }
        });
        jToolBar1.add(eliminaB);

        table.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome", "Cognome", "Telefono"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setResizable(false);
            table.getColumnModel().getColumn(1).setResizable(false);
            table.getColumnModel().getColumn(2).setResizable(false);
        }

        logoutB.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        logoutB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/logout.png"))); // NOI18N
        logoutB.setText("Logout");
        logoutB.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        logoutB.setFocusable(false);
        logoutB.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        logoutB.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        logoutB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutBActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(logoutB, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                    .addComponent(logoutB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 547, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void eliminaBFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_eliminaBFocusGained
        
        eliminaB.setBackground(Color.cyan);
    }//GEN-LAST:event_eliminaBFocusGained

    private void creaBFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_creaBFocusGained
        
        creaB.setBackground(Color.cyan);
    }//GEN-LAST:event_creaBFocusGained

    private void modificaBFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_modificaBFocusGained
        
        modificaB.setBackground(Color.cyan);
    }//GEN-LAST:event_modificaBFocusGained

    private void creaBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_creaBActionPerformed
        
        EditorPersona ep = new EditorPersona(userid);
        ep.setVisible(true);
        super.dispose();
        
    }//GEN-LAST:event_creaBActionPerformed

    private void modificaBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificaBActionPerformed
        
        if(selected){
            EditorPersona ep = new EditorPersona(nomeSel, cognomeSel, indexSel, userid);
            ep.setVisible(true);
            super.dispose();
        }
        else{
            JOptionPane.showMessageDialog(null, "Seleziona un contatto.");
        }
    }//GEN-LAST:event_modificaBActionPerformed

    private void eliminaBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminaBActionPerformed
        
        if(selected){
            Persona p;
            for(int i=0; i<rubrica.getContatti().size(); i++){
                Persona c = rubrica.getContatti().get(i);
                if(c.getId()==indexSel){
                    p = c;
                    int input = JOptionPane.showConfirmDialog(null, "Elimina la persona "+p.getNome().toUpperCase()+" "+p.getCognome().toUpperCase()+"?");
                    if(input==0){
                        rubrica.eliminaContatto(p);
                        try {
                            conn = DriverManager.getConnection(url, username, password);
                            connesso = true;
                            System.out.println("Connesso a PostgreSQL server.");

                            Statement stat = conn.createStatement();

                            String sql = "delete from persone where id=? and userid=?;";
                           
                            PreparedStatement ps = conn.prepareStatement(sql);
                            ps.setInt(1, indexSel);
                            ps.setInt(2, userid);
                            ps.addBatch();

                            conn.setAutoCommit(false);
                            ps.executeBatch();
                            conn.setAutoCommit(true);

                            conn.close();
                        } catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                        
                        eliminaFinestra(indexSelTab);
                    }
                }
                    
            }
            
        }
        else{
            JOptionPane.showMessageDialog(null, "Seleziona un contatto.");
        }
    }//GEN-LAST:event_eliminaBActionPerformed

    private void logoutBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutBActionPerformed
        int input = JOptionPane.showConfirmDialog(null, "Sei sicuro di volere uscire?");
        if(input==0){
            super.dispose();
        }       
    }//GEN-LAST:event_logoutBActionPerformed

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
            java.util.logging.Logger.getLogger(FinestraPrincipale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FinestraPrincipale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FinestraPrincipale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FinestraPrincipale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FinestraPrincipale().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton creaB;
    private javax.swing.JButton eliminaB;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton logoutB;
    private javax.swing.JButton modificaB;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
