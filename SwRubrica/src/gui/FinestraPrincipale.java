/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Color;
import java.io.FileNotFoundException;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import swrubrica.Persona;
import swrubrica.SwRubrica;

/**
 *
 * @author babya
 */
public class FinestraPrincipale extends javax.swing.JFrame {
    
    DefaultTableModel model;
    private boolean selected=false;
    private int indexSel;
    private static String nomeSel;
    private static String cognomeSel;
    private static String telefonoSel;

    /**
     * Creates new form FinestraPrincipale
     */
    public FinestraPrincipale() {
        initComponents();
        
        initJTable();
        
        initSelectorListener();
    }
    
    /*
    Inizializza la tabella della Finestra Principale.
    */
    public void initJTable(){
        
        model = (DefaultTableModel) table.getModel();
        
        Object rowData[] = new Object[3];
        SwRubrica.aggiornaListaSwRubrica();
        
        if(!SwRubrica.contatti.isEmpty()){
            
            for(int ii=0; ii<SwRubrica.contatti.size(); ii++){

                rowData[0] = SwRubrica.contatti.get(ii).getNome();
                rowData[1] = SwRubrica.contatti.get(ii).getCognome();
                rowData[2] = SwRubrica.contatti.get(ii).getTelefono();
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
                System.out.println("Nessun contatto selezionato.");
            }
            else
                selected=true;
            
            String nome = null;
            String cognome = null;
            String telefono = null;
            int index = 0;
            indexSel=0;
            
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
                for(int i=0; i<SwRubrica.contatti.size(); i++){
                    Persona p = SwRubrica.contatti.get(i);
                    if((p.getNome().compareTo(nomeSel)==0) && (p.getCognome().compareTo(cognomeSel)==0) && (p.getTelefono().compareTo(telefonoSel)==0))
                        ind = SwRubrica.contatti.indexOf(p);
                }
            
            }
            catch(NullPointerException e2){  System.err.println(e2);  }
            
            indexSel = ind;
            System.out.println("Selected: " + nome + " " + cognome+ " "+ind);
          }

        });
    }
    
    /*
    Aggiorna la tabella della Finestra Principale dopo una creazione.
    */
    public void aggiornaFinestra() throws FileNotFoundException{
        model = (DefaultTableModel) table.getModel();
        
        Object rowData[] = new Object[3];
        
        rowData[0] = SwRubrica.contatti.lastElement().getNome();
        rowData[1] = SwRubrica.contatti.lastElement().getCognome();
        rowData[2] = SwRubrica.contatti.lastElement().getTelefono();
        model.addRow(rowData);
        
        SwRubrica.aggiornaFileSwRubrica();
   
    }
    
    /*
    Modifica della tabella della Finestra Principale dopo una modifica.
    */
    public void modificaFinestra(int i) throws FileNotFoundException{
        model = (DefaultTableModel) table.getModel();
        
        Object rowData[] = new Object[3];
         
        rowData[0] = SwRubrica.contatti.get(i).getNome();
        rowData[1] = SwRubrica.contatti.get(i).getCognome();
        rowData[2] = SwRubrica.contatti.get(i).getTelefono();

        model.removeRow(i);
        model.insertRow(i, rowData);
        
        SwRubrica.aggiornaFileSwRubrica();
    }
    
    /*
    Elimina di un elemento della tabella della Finestra Principale dopo eliminazione.
    */
    public void eliminaFinestra(int i) throws FileNotFoundException{
        model = (DefaultTableModel) table.getModel();

        model.removeRow(i);
        
        SwRubrica.aggiornaFileSwRubrica();
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        
        EditorPersona ep = new EditorPersona();
        ep.setVisible(true);
        SwRubrica.finestra.setVisible(false);
    }//GEN-LAST:event_creaBActionPerformed

    private void modificaBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificaBActionPerformed
        
        if(selected){
            EditorPersona ep = new EditorPersona(nomeSel, cognomeSel, indexSel);
            ep.setVisible(true);
            SwRubrica.finestra.setVisible(false);
        }
        else{
            JOptionPane.showMessageDialog(null, "Seleziona un contatto.");
        }
    }//GEN-LAST:event_modificaBActionPerformed

    private void eliminaBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminaBActionPerformed
        
        if(selected){
            Persona p = SwRubrica.contatti.get(indexSel);
            int input = JOptionPane.showConfirmDialog(null, "Elimina la persona "+p.getNome().toUpperCase()+" "+p.getCognome().toUpperCase()+"?");
            if(input==0){
                SwRubrica.contatti.remove(indexSel);
                try {
                    eliminaFinestra(indexSel);
                } catch (FileNotFoundException ex) {
                    System.err.println(ex);
                }
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Seleziona un contatto.");
        }
    }//GEN-LAST:event_eliminaBActionPerformed

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
    private javax.swing.JButton modificaB;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
