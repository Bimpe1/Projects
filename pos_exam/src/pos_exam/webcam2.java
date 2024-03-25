/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_exam;

import static com.sun.webkit.graphics.WCImage.getImage;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.SecureRandom;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;
import static sun.applet.AppletResourceLoader.getImage;
import org.opencv.highgui.VideoCapture;

/**
 *
 * @author adeto
 */
public class webcam2 extends javax.swing.JFrame {
    static{
    File file = new File("C:\\Users\\adeto\\Downloads\\required\\opencv_java249.dll");
    System.load(file.getAbsolutePath());
}
    private static File getImage;
    private static final SecureRandom RAND = new SecureRandom();
   public static String Filename = null;
   private DaemonThread myThread = null;
   private VideoCapture websource = null;
   private final Mat frame = new Mat(1000,1000,1);
   private final MatOfByte mem = new MatOfByte();
   

    /**
     */
    public webcam2() {
        initComponents();
        websource = new VideoCapture(0);
        myThread = new DaemonThread(jLabel1);
        Thread t =new Thread(myThread);
        t.setDaemon(true);
        myThread.runnable=true;
        t.start();
        
    }
public class DaemonThread implements Runnable{
    protected volatile boolean runnable = false;
    
    public DaemonThread(JLabel capture){
        jLabel1 = capture;
    }
    
    @Override
    public void run(){
        synchronized(this){
            while(runnable){
                if(websource.grab()){
                    try{
                        websource.retrieve(frame);
                        Highgui.imencode(".bmp",frame,mem);
                        Image im = ImageIO.read(new ByteArrayInputStream(mem.toArray()));
                        BufferedImage buff =(BufferedImage) im;
                        Graphics g = jLabel1.getGraphics();
                        if(g.drawImage(buff,1,1,jLabel1.getWidth(),jLabel1.getHeight(),null)){
                            if(runnable == false){
                                this.wait();                            }
                        }
                    }catch(Exception e){
                        
                        }
            }
            }
        }
    }
}
private void stopCam(){
    if(myThread != null){
        if(myThread.runnable==true){
            myThread.runnable =false;
            websource.release();
        }
    }
}
private void CaptureImage(JLabel image){
    try{
        stopCam();
        if(getImage != null){
            ImageIcon imageicon =new ImageIcon(new ImageIcon(Filename).getImage().getScaledInstance(home.jLabel20.getWidth(),
                    home.jLabel20.getHeight(), Image.SCALE_DEFAULT));
            home.jLabel20.setIcon(imageicon);
        }
    }catch(Exception e){
        JOptionPane.showMessageDialog(rootPane, "Warning");
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("jLabel1");

        jButton1.setBackground(Color.decode("#FF432A"));
        jButton1.setText("Capture");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 559, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(249, 249, 249)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
int option =JOptionPane.showConfirmDialog(rootPane, "Are you sure you want to capture the image");
if(option ==0){
    try{
        File file = new File("Capture");
        boolean flag = true;
        if(!file.isDirectory()){
            flag = file.mkdir();
        }
        if(!flag){
            throw new Exception("Folder does not exist");
        }
        int imageNo =1+RAND.nextInt(999);
        Filename = file.getAbsolutePath()+ "\\" + "Webcam" + imageNo + ".jpg";
        Highgui.imwrite(Filename,frame);
        getImage =file;
        CaptureImage(jLabel1);
        JOptionPane.showMessageDialog(rootPane, Filename + " Captured");
        home.jTextField12.setText(Filename);
        File image = new File(Filename);
        FileInputStream fis = new FileInputStream(image);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] Byte = new byte[1024];
        
        for(int i; (i=fis.read(Byte)) != -1;){
            baos.write(Byte,0,i);
        }
        home.photo =baos.toByteArray();
        dispose();
    }catch(Exception e){
        stopCam();
        JOptionPane.showMessageDialog(rootPane," Warning");
    }
}
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(webcam2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(webcam2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(webcam2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(webcam2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new webcam2().setVisible(true);
            }
        });
    
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
