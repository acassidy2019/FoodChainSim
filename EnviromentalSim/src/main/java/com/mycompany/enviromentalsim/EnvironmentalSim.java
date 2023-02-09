/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.enviromentalsim;
import java.util.ArrayList;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ConcurrentModificationException;
import java.util.Random;

/**
 *
 * @author acass
 */
public class EnvironmentalSim extends javax.swing.JFrame {
    boolean running = false;        
    
    // main program timer, controls task scheduling used in the main loop of the program
    private Timer timer = new Timer();
    
    
    // common containers
    public ArrayList<Sprite> Sprites = new ArrayList<>();
    
    /**
     * Creates new form EnvironmentalSim
     */
    public EnvironmentalSim() {
        initComponents();
    }

    
    /**
     * Scan our text boxes to see what animals should be included
     */
    public void updateAnimals() {
        Random rand = new Random();
        // populate grass       
        for (int i = 0; i < Integer.parseInt(jTextField4.getText()); i++) {
            Sprites.add(new Sprite(0, 5, 5, 0, 25, false, new int[] {rand.nextInt(RoamingArea.getWidth() - 25 * 2), rand.nextInt(RoamingArea.getHeight() - 25 * 2)}, new ArrayList(Arrays.asList()), new ArrayList(Arrays.asList()), 0, 0, 0, 0, 0, new int[] {1,1}));
        }
        // populate rabbits       
        for (int i = 0; i < Integer.parseInt(jTextField2.getText()); i++) {
            Sprites.add(new Sprite(1, 20, 20, 5, 20, false, new int[] {rand.nextInt(RoamingArea.getWidth() - 20 * 2), rand.nextInt(RoamingArea.getHeight() - 20 * 2)}, new ArrayList(Arrays.asList(0)), new ArrayList(Arrays.asList(3)), 0, 20, 30, 0, 50, new int[] {1,1}));
        }
        // populate deer        
        for (int i = 0; i < Integer.parseInt(jTextField3.getText()); i++) {
            Sprites.add(new Sprite(2, 50, 50, 5, 35, false, new int[] {rand.nextInt(RoamingArea.getWidth() - 35 * 2), rand.nextInt(RoamingArea.getHeight() - 35 * 2)}, new ArrayList(Arrays.asList(0)), new ArrayList(Arrays.asList()), 0, 12, 30, 0, 100, new int[] {1,1}));
        }
        // populate wolves       
        for (int i = 0; i < Integer.parseInt(jTextField1.getText()); i++) {
            Sprites.add(new Sprite(3, 100, 100, 10, 30, false, new int[] {rand.nextInt(RoamingArea.getWidth() - 30 * 2), rand.nextInt(RoamingArea.getHeight() - 30 * 2)}, new ArrayList(Arrays.asList(1, 2)), new ArrayList(Arrays.asList()), 0, 8, 30, 0, 150, new int[] {1,1}));
        }
        
        // update our sprites to be drawn
        for (Sprite sprite : Sprites) {
            RoamingArea.add(sprite);
            String picture = "Wolf";
            if (sprite.rank == 0) picture = "Grass";
            if (sprite.rank == 1) picture = "Rabbit";
            if (sprite.rank == 2) picture = "Deer";
            if (sprite.rank == 3) picture = "WolfSpeed";
            
            Image img = new ImageIcon(String.format("./src/main/java/com/mycompany/enviromentalsim/Media/Sprites/%s.gif", picture)).getImage().getScaledInstance(sprite.size * 2, sprite.size * 2, Image.SCALE_DEFAULT);
            sprite.setIcon(new ImageIcon(img));
            sprite.setSize(sprite.size * 2, sprite.size * 2);
            sprite.setVisible(true);
            sprite.setEnabled(true);
        }  
    }
    
    /**
     * Main running function of this program.  Called from main
     */
    public void runSim() {        
        // decay each sprite's health IF it is of aggressiveness > 0 FUTURE IMPLEMENTATION
        
        // main loop 
        // CAN NOT use while loops, must implement swing timer instead
        // otherwise loop will eat up cpu usage and graphics will never be updated
        // https://stackoverflow.com/questions/15266634/java-frame-not-displaying-properly-during-a-while-loop using swing timers
        if (running) {
            // this task will continue to call itself, giving us a "while loop" built by this function
            TimerTask runsim = new TimerTask() { public void run() { runSim(); } };
            TimerTask mainloop = new TimerTask() {        
                public void run() {
                    RoamingArea.setEnabled(true);
                    try { for (Sprite sprite : Sprites) sprite.move(Sprites, RoamingArea); }
                    // when we delete a dead sprite from the sprites list, an exception will be thrown. however the sprite still gets removed as desired,
                    // so we simply catch the exception
                    catch(ConcurrentModificationException ex) {}

                    try {
                        timer.cancel(); // clear any old scheduled tasks from the system timer to avoid overlap conflicts
                        timer = new Timer();
                        timer.schedule(runsim, 100);   
                        // safety schedule
                        timer.schedule(runsim, 500);
                      // catch the overlap task exception.
                    } catch (java.lang.IllegalStateException ex) {System.out.println("Overlap found"); timer.schedule(runsim, 1001); } 
                }
            };
            
            this.revalidate();
            this.repaint();
            try {timer.schedule(mainloop, 10);}
            catch (java.lang.IllegalStateException ex) {System.out.println("mainloop overlapped!"); }
        }
    }
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        RoamingArea = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        RoamingArea.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 0)));

        javax.swing.GroupLayout RoamingAreaLayout = new javax.swing.GroupLayout(RoamingArea);
        RoamingArea.setLayout(RoamingAreaLayout);
        RoamingAreaLayout.setHorizontalGroup(
            RoamingAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 781, Short.MAX_VALUE)
        );
        RoamingAreaLayout.setVerticalGroup(
            RoamingAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jButton1.setText("Run");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Stop");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        jLabel1.setText("Wolfs");

        jLabel2.setText("Rabbits");

        jLabel3.setText("Deer");

        jLabel4.setText("Grass");

        jButton3.setText("Clear");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(RoamingArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField1)
                            .addComponent(jTextField2)
                            .addComponent(jTextField3)
                            .addComponent(jTextField4)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(RoamingArea, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(118, 118, 118)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 229, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // lock text boxes
        updateAnimals();
        jTextField1.setEnabled(false);
        jTextField2.setEnabled(false);
        jTextField3.setEnabled(false);
        jTextField4.setEnabled(false);
        
        // run the sim
        running = true;
        runSim();
    }//GEN-LAST:event_jButton1ActionPerformed
        
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // set the text fields to re-enabled
        jTextField1.setEnabled(true);
        jTextField2.setEnabled(true);
        jTextField3.setEnabled(true);
        jTextField4.setEnabled(true);
        
        // stop the sim
        running = false;
        
        // reset timer
        timer.cancel();
        timer = new Timer();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        updateAnimals();
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        updateAnimals();
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        updateAnimals();
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        updateAnimals();
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // set the text fields to re-enabled
        jTextField1.setEnabled(true);
        jTextField2.setEnabled(true);
        jTextField3.setEnabled(true);
        jTextField4.setEnabled(true);
        
        // stop the sim
        running = false;
        
        // clear all animals out
        Sprites = new ArrayList<>();
        RoamingArea.removeAll();
        
        // reset our timer
        timer.cancel();
        timer = new Timer();
        
        this.revalidate();
        this.repaint();
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(EnvironmentalSim.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EnvironmentalSim.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EnvironmentalSim.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EnvironmentalSim.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EnvironmentalSim().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPanel RoamingArea;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
