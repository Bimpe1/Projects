/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_exam;

import java.awt.Image;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import javax.mail.PasswordAuthentication;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Timer;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.TimerTask;
import javax.swing.SwingUtilities;

/**
 *
 * @author adeto
 */
public class home extends javax.swing.JFrame {

    String dob;
    String name;
    public String filename = null;
    static byte photo[] = null;
    String oldEmail = null;
    String oldUsername = null;
    String oldPassword = null;
    String text;
    ArrayList<String> notifications = new ArrayList<>();
    LocalDate currentDate;
    String databaseUser;
    String databasePassword;
    String databaseName;
    String backupfilepath;
    LocalTime currentTime;

    private static String backupTime = "00:00";
    private static Timer timer;

    public String Hash(String c) {
        try {
            MessageDigest msgDigest = MessageDigest.getInstance("MD5");
            msgDigest.update((new String(c)).getBytes("UTF8"));
            String passHash = new String(msgDigest.digest());
            return passHash;
        } catch (Exception ex) {

            return c;
        }
    }

    /**
     * Creates new form home
     */
    public home() {
        initComponents();
        BirthdayReminder();
        getBackupTime();
//        DateandTime();
        for (String notification : notifications) {
            jTextArea1.append("- " + notification + "\n\n");
        }

    }

    private void getBackupTime() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");                               
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pos", "root", "$Temilolu12");
            PreparedStatement ps = con.prepareStatement("select * from backup");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                backupTime = (rs.getString(1));
            }
            //gets the backup time and sets it to the latest time set in the db
            System.out.println(backupTime);

            //the timer is triggered
            if (isBackupTimeSet() && timer == null) {
                timer = new Timer(true);
                timer.scheduleAtFixedRate(new BackupTask(), calculateInitialDelay(), 24 * 60 * 60 * 1000);
            }
        } catch (Exception e) {

        }
    }

    private static boolean isBackupTimeSet() {
        return !backupTime.equals("00:00");
    }

    private static long calculateInitialDelay() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String currentDate = dateFormat.format(new java.util.Date());

        String nextBackupDateTime = currentDate.split(" ")[0] + " " + backupTime;

        if (calculateTimeDifference(nextBackupDateTime) <= 0) {
            nextBackupDateTime = addDayToDateTime(nextBackupDateTime);
        }

        long timeUntilNextBackup = calculateTimeDifference(nextBackupDateTime);
        return Math.max(0, timeUntilNextBackup);
    }

    private static String addDayToDateTime(String dateTime) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            java.util.Date currentDate = dateFormat.parse(dateTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            return dateFormat.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return dateTime;
        }
    }

    private static long calculateTimeDifference(String targetDateTime) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            java.util.Date targetDate = dateFormat.parse(targetDateTime);
            java.util.Date currentDate = new java.util.Date();
            return targetDate.getTime() - currentDate.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private class BackupTask extends TimerTask {

        @Override
        public void run() {
            // Perform the database backup operation here
            try {
                backupDatabase();
                JOptionPane.showMessageDialog(null, "Database backup completed at " + new java.util.Date());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void backupDatabase() throws Exception {
            // Your database backup code here
            // e.g., execute mysqldump command
            String dumpCommand = "mysqldump -u" + "root" + " -p" + "$Temilolu12"
                    + " -B pos -r C:\\Users\\adeto\\Downloads\\backup\\backup.sql"; 
            Process process = Runtime.getRuntime().exec(dumpCommand);
            process.waitFor();
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

        jFileChooser1 = new javax.swing.JFileChooser();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jTextField2 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jComboBox5 = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox<>();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jTextField10 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField15 = new javax.swing.JTextField();
        jButton9 = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Name:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Date of birth:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Phone number:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Email Address:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Username:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Gender:");

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jTextField3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jComboBox3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Gender", "Male", "Female", " " }));

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel10.setOpaque(true);

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton1.setText("Browse");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton2.setText("Add");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton4.setText("Web Cam");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Password:");

        jTextField6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("Department:");

        jComboBox4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Department", "IT Manager", "IT Admin", "Sales Manager", "Inventory/Stocks Manager", "Sales person", "Inventory staff" }));

        jTextField2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jTextField7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jTextField8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jButton7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton7.setText("Clear");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel13.setText("User Registration");

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel23.setText("Staff ID:");

        jTextField13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8)
                            .addComponent(jLabel11)
                            .addComponent(jLabel23)
                            .addComponent(jLabel14))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox4, 0, 533, Short.MAX_VALUE)
                                    .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextField3)
                                    .addComponent(jTextField6)
                                    .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextField2)
                                    .addComponent(jTextField7)
                                    .addComponent(jTextField1)
                                    .addComponent(jTextField13))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(128, 128, 128)
                                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(42, 42, 42))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(21, 21, 21)))))
                        .addGap(42, 42, 42))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel13)
                        .addGap(289, 289, 289))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(204, 204, 204)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(213, 213, 213)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(109, 109, 109))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)
                        .addGap(22, 22, 22)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(94, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(" Staff Registration", jPanel4);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Name:");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setText("Date of birth:");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("Phone number:");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setText("Email Address:");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel18.setText("Username:");

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel19.setText("Gender:");

        jTextField4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jTextField5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jComboBox5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Gender", "Male", "Female" }));

        jLabel20.setBackground(new java.awt.Color(255, 255, 255));
        jLabel20.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel20.setOpaque(true);

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton3.setText("Browse");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton5.setText("Update");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton6.setText("Web Cam");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel21.setText("Password:");

        jTextField9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel22.setText("Department:");

        jComboBox6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Department", "IT Manager", "IT Admin", "Sales Manager", "Inventory/Stocks Manager", "Sales person", "Inventory staff" }));

        jTextField10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jTextField11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jTextField12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jButton8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton8.setText("Clear");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Search ID:");

        jTextField15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jButton9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton9.setText("Search");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(233, 233, 233)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton9))
                .addGap(19, 19, 19))
        );

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel24.setText("Staff ID:");

        jTextField14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton5)
                        .addGap(198, 198, 198)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(267, 267, 267))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(jLabel12)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17)
                            .addComponent(jLabel19)
                            .addComponent(jLabel18)
                            .addComponent(jLabel21)
                            .addComponent(jLabel24))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField14)
                            .addComponent(jTextField4)
                            .addComponent(jDateChooser2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField5)
                            .addComponent(jTextField10)
                            .addComponent(jComboBox5, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField11)
                            .addComponent(jTextField9)
                            .addComponent(jComboBox6, 0, 442, Short.MAX_VALUE))
                        .addGap(124, 124, 124)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(68, 68, 68))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(122, 122, 122))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(92, 92, 92))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jButton3)
                                .addGap(134, 134, 134))))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(68, 68, 68)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(58, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton6)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addGap(174, 174, 174))))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Update Records", jPanel2);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 982, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 712, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Notifications", jPanel7);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed

        String id = jTextField15.getText();
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pos", "root", "$Temilolu12");
            PreparedStatement ps = con.prepareStatement("select * from userregistration where id=?"); // Corrected SQL query

            ps.setString(1, id); // Set the parameter for the prepared statement

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                jTextField14.setText(rs.getString("id"));
                jTextField4.setText(rs.getString("name"));
                jDateChooser2.setDate(rs.getDate("dob"));
                jTextField5.setText(rs.getString(4));
                oldEmail = rs.getString(5);
                jTextField10.setText(rs.getString(5));

                String gender = rs.getString(6);
                int count = jComboBox5.getItemCount();
                for (int i = 0; i < count; i++) {
                    if (gender.equalsIgnoreCase(jComboBox5.getItemAt(i).toString())) {
                        jComboBox5.setSelectedIndex(i);
                        break;
                    }
                }
                oldUsername = rs.getString(7);
                jTextField11.setText(rs.getString(7));
                oldPassword = rs.getString(8);
                jTextField9.setText(rs.getString(8));

                String department = rs.getString(9);
                int count1 = jComboBox6.getItemCount();
                for (int i = 0; i < count1; i++) {
                    if (department.equalsIgnoreCase(jComboBox6.getItemAt(i).toString())) {
                        jComboBox6.setSelectedIndex(i); // Corrected the combo box reference
                        break;
                    }
                }
                photo = rs.getBytes("picture");
                ImageIcon ic = new ImageIcon(photo);
                Image im = ic.getImage();
                Image scaled = im.getScaledInstance(jLabel20.getWidth(), jLabel20.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon scaledIc = new ImageIcon(scaled);
                jLabel20.setIcon(scaledIc);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "No record for " + id); // Print the exception details for debugging
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        jTextField14.setText(null);
        jTextField15.setText(null);
        jTextField4.setText(null);
        jTextField5.setText(null);
        jTextField10.setText(null);
        jTextField11.setText(null);
        jTextField12.setText(null);
        jTextField9.setText(null);
        jComboBox5.setSelectedIndex(0);
        jComboBox6.setSelectedIndex(0);
        jDateChooser2.setDate(null);
        jLabel20.setIcon(null);          // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        webcam2 jfrm1 = new webcam2();
        jfrm1.show();
        jfrm1.setVisible(true);// TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        String id = jTextField14.getText();
        String name = jTextField4.getText();
        Date date = jDateChooser2.getDate();
        java.sql.Date dob = new java.sql.Date(date.getTime());
        String phoneno = jTextField5.getText();
        String email = jTextField10.getText();
        String gender = jComboBox5.getSelectedItem().toString();
        String username = jTextField11.getText();
        String password = jTextField9.getText();
        String hashpassword = Hash(password);
        String department = jComboBox6.getSelectedItem().toString();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pos", "root", "$Temilolu12");
            PreparedStatement ps = con.prepareStatement("UPDATE userregistration SET name=?, dob=?, phoneno=?, email=?, gender=?, username=?, password=?, department=?,picture=? WHERE id=?");
            ps.setString(1, name);
            ps.setDate(2, dob);
            ps.setString(3, phoneno);
            ps.setString(4, email);
            ps.setString(5, gender);
            ps.setString(6, username);
            ps.setString(7, hashpassword);
            ps.setString(8, department);
            ps.setBytes(10, photo);
            ps.setString(11, id);

            int rs = ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Update Successful..");

            if (!password.equals(oldPassword) || !username.equals(oldUsername) || !email.equals(oldEmail)) {
                String receiver = email;
                String senderEmail = "adetobaadebimpe@gmail.com";
                String senderPassword = "fxczbtaorwpkjrns";
                Properties props = new Properties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.port", "587");
                Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(senderEmail, senderPassword);
                    }
                });

                try {
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(senderEmail));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
                    message.setSubject("Log in details for CBT");
                    message.setText("Below are your updated log in details for your cbt test\nUsername: " + username + "\nPassword: " + password + "\nGood Luck!!");
                    Transport.send(message);
                    JOptionPane.showMessageDialog(rootPane, "Email Sent");
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Image", "jpg");

            jFileChooser1.setAcceptAllFileFilterUsed(false);
            jFileChooser1.addChoosableFileFilter(filter);
            jFileChooser1.showOpenDialog(null);

            File f = jFileChooser1.getSelectedFile();
            filename = f.getAbsolutePath();
            String path = f.getAbsolutePath();
            jTextField12.setText(path);

            Image im = Toolkit.getDefaultToolkit().createImage(path);
            im = im.getScaledInstance(jLabel20.getWidth(), jLabel20.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon ic = new ImageIcon(im);
            jLabel20.setIcon(ic);

            File image = new File(filename);
            FileInputStream fis = new FileInputStream(image);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] Byte = new byte[1024];

            for (int i = 0; (i = fis.read(Byte)) != -1;) {
                baos.write(Byte, 0, i);
            }
            photo = baos.toByteArray();
        } catch (Exception e) {

        }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        jTextField1.setText(null);
        jTextField2.setText(null);
        jTextField3.setText(null);
        jTextField6.setText(null);
        jTextField7.setText(null);
        jTextField13.setText(null);
        jTextField8.setText(null);
        jComboBox3.setSelectedIndex(0);
        jComboBox4.setSelectedIndex(0);
        jDateChooser1.setDate(null);
        jLabel10.setIcon(null);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        webcam1 jfrm1 = new webcam1();
        jfrm1.show();
        jfrm1.setVisible(true);// TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            String id = jTextField13.getText();
            String name = jTextField1.getText();
            Date date = jDateChooser1.getDate();
            java.sql.Date dob = new java.sql.Date(date.getTime());
            String phoneno = jTextField3.getText();
            String email = jTextField2.getText();
            String gender = jComboBox3.getSelectedItem().toString();
            String username = jTextField7.getText();
            String password = jTextField6.getText();
            String hashpassword = Hash(password);
            String department = jComboBox4.getSelectedItem().toString();

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pos", "root", "$Temilolu12");
            PreparedStatement ps = con.prepareStatement("Insert into userregistration values(?,?,?,?,?,?,?,?,?,?)");
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setDate(3, dob);
            ps.setString(4, phoneno);
            ps.setString(5, email);
            ps.setString(6, gender);
            ps.setString(7, username);
            ps.setString(8, hashpassword);
            ps.setString(9, department);
            ps.setBytes(10, photo);
            int rs = ps.executeUpdate();
            JOptionPane.showMessageDialog(rootPane, name + " record inserted");
            String receiver = jTextField2.getText();
            String senderEmail = "adetobaadebimpe@gmail.com";
            String senderPassword = "fxczbtaorwpkjrns";
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(senderEmail, senderPassword);
                }
            });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(senderEmail));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
                message.setSubject("Welcome to " + department + " department!!");
                message.setText("Below are your log in details for POS service \nUsername: " + username + "\nPassword: " + password);
                Transport.send(message);
                JOptionPane.showMessageDialog(rootPane, "Email Sent");
            } catch (MessagingException e) {

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Image", "jpg");

            jFileChooser1.setAcceptAllFileFilterUsed(false);
            jFileChooser1.addChoosableFileFilter(filter);
            jFileChooser1.showOpenDialog(null);

            File f = jFileChooser1.getSelectedFile();
            filename = f.getAbsolutePath();
            String path = f.getAbsolutePath();
            jTextField8.setText(path);

            Image im = Toolkit.getDefaultToolkit().createImage(path);
            im = im.getScaledInstance(jLabel10.getWidth(), jLabel10.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon ic = new ImageIcon(im);
            jLabel10.setIcon(ic);

            File image = new File(filename);
            FileInputStream fis = new FileInputStream(image);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] Byte = new byte[1024];

            for (int i = 0; (i = fis.read(Byte)) != -1;) {
                baos.write(Byte, 0, i);
            }
            photo = baos.toByteArray();
        } catch (Exception e) {

        }
    }//GEN-LAST:event_jButton1ActionPerformed
//public void DateandTime() {
//        Timer timer = new Timer(1000, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // Get the current time
//                currentTime = LocalTime.now();
//                currentDate = LocalDate.now();
//
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
//                String formattedTime = currentTime.format(formatter);
//
//                // Display the formatted time
//                jLabel26.setText(currentDate.toString() + " " + formattedTime);
//            }
//        });
//
//        // Start the timer
//        timer.start();
//    }

//    public static void performDatabaseBackup(String databaseUser, String databasePassword,
//            String databaseName, String backupFilePath) {
//        // Construct the mysqldump command
//        String mysqldumpPath = "\"C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump.exe\"";
//        String[] command = {
//            mysqldumpPath,
//            "-u" + databaseUser,
//            "--password=" + databasePassword,
//            databaseName,
//            "--result-file=" + backupFilePath
//        };
//
//        try {
//            // Execute the mysqldump command
//            ProcessBuilder processBuilder = new ProcessBuilder(command);
//            processBuilder.redirectErrorStream(true);  // Merge error and output streams
//            Process process = processBuilder.start();
//
//            // Capture the output (both error and normal output) of the process
//            InputStream inputStream = process.getInputStream();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
//            }
//
//            int exitCode = process.waitFor();
//
//            if (exitCode == 0) {
//                System.out.println("Backup completed successfully.");
//            } else {
//                System.err.println("Backup failed. Exit code: " + exitCode);
//            }
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
   private void BirthdayReminder() {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pos", "root", "$Temilolu12");
        PreparedStatement ps = con.prepareStatement("select * from userregistration");
        ResultSet rs = ps.executeQuery();
        
        // Get today's date in MM-dd format
        String currentDayMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd"));
        
        while (rs.next()) {
             name = rs.getString("name");
             dob = rs.getString("dob");
            
            // Get user's birthday in MM-dd format
            String userDayMonth = new SimpleDateFormat("MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(dob));

            if (currentDayMonth.equals(userDayMonth)) {
                 text = "Happy Birthday " + name + "!";
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            JOptionPane.showMessageDialog(rootPane, text);
                        }
                    },1000);
                break;
            }
        }
                    notifications.add(text);
        con.close(); // Close connection when done
    } catch (Exception e) {
        e.printStackTrace(); // Handle exception appropriately
    }
}


//                if (currentDayMonth.equals(userDayMonth)) {
//                        text = "Happy Birthday " + name + "!";
//                    SwingUtilities.invokeLater(() -> {
//                     Timer timer = new Timer(2000, new ActionListener() {
//                        @Override
//                        public void actionPerformed(ActionEvent e) {
//                    JOptionPane.showMessageDialog(rootPane, "Happy Birthday " + name + "!");
//                    
//                            JOptionPane.showMessageDialog(rootPane, "Happy Birthday " + name + "!");
//                        }
//                                            notifications.add(text);
//                    });
//                    timer.setRepeats(false); // Execute only once
//                    timer.start();
//                };
//                
//            }
//
//        } catch (Exception e) {
//
//        }
    

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
            java.util.logging.Logger.getLogger(home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    public static javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    public static javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    public static javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    public static javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables
}
