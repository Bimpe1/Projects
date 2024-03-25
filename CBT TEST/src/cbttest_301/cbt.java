/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbttest_301;

import com.mysql.cj.protocol.Resultset;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import static javafx.application.Platform.exit;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author adeto
 */
public class cbt extends javax.swing.JFrame {

    private String matno;
    private String email;

    private ArrayList<Question> questionsList = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private String lastSelectedAnswer = null; // Variable to store the last selected answer
    int score = 0;
//    String questionText;
//    String optionA;
//    String optionB;
//    String optionC;
//    String optionD;
//    String correctAnswer;
//    String selectedAnswer;
//    cbt(int matNo, String email) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

//    // Assuming you have a Question class
    class Question {

        private int number;
        private String question;
        private String optionA;
        private String optionB;
        private String optionC;
        private String optionD;
        private String selectedAnswer;
        private String correctAnswer;

        public Question(int number, String question, String optionA, String optionB, String optionC, String optionD, String correctAnswer) {
            this.number = number;
            this.question = question;
            this.optionA = optionA;
            this.optionB = optionB;
            this.optionC = optionC;
            this.optionD = optionD;
            this.selectedAnswer = "";
            this.correctAnswer = correctAnswer;

        }

        public String getQuestion() {
            return question;
        }

        public String getOptionA() {
            return optionA;
        }

        public String getOptionB() {
            return optionB;
        }

        public String getOptionC() {
            return optionC;
        }

        public String getOptionD() {
            return optionD;
        }

        public int getNumber() {
            return number;
        }

        public String getSelectedAnswer() {
            return selectedAnswer;
        }

        public void setSelectedAnswer(String selectedAnswer) {
            this.selectedAnswer = selectedAnswer;
        }

        public String getCorrectAnswer() {
            return correctAnswer;
        }

    }

    // ActionListener for option buttons
    private void optionButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // Reset the appearance of all buttons
        resetButtonColors();
        // Store and visually indicate the selected answer
        lastSelectedAnswer = evt.getActionCommand();
        setButtonSelected((javax.swing.JButton) evt.getSource());
        questionsList.get(currentQuestionIndex - 1).setSelectedAnswer(lastSelectedAnswer);

    }

    public class Timer implements Runnable {

        private volatile boolean isRunning = true;  // Add this flag
        private volatile boolean isSubmitted = false;  // Add this flag

        // Assuming totalSeconds is declared somewhere in your class
        private int totalSeconds;

        public Timer() {
            try {
                // Connect to the database
                String path = "jdbc:mysql://localhost:3306/cbt";
                String user = "root";
                String pass = "$Temilolu12";
                Connection con = DriverManager.getConnection(path, user, pass);

                // Execute a query to retrieve data
                String selectQuery = "SELECT * from questions";
                PreparedStatement ps = con.prepareStatement(selectQuery);
                ResultSet rs = ps.executeQuery();
                // Assuming there is only one row in the result set
                while (rs.next()) {
                    int duration = rs.getInt("duration");
                    totalSeconds = duration;  // Assuming duration is in seconds
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void run() {
            while (isRunning && totalSeconds > 0) {
                int remainingHours = totalSeconds / 3600;
                int remainingMinutes = (totalSeconds % 3600) / 60;
                int remainingSeconds = totalSeconds % 60;
                String formattedTime = String.format("Time Remaining %02d:%02d:%02d%n", remainingHours, remainingMinutes, remainingSeconds);
                jLabel5.setText(formattedTime);

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                totalSeconds--;
            }
            String formattedTime = String.format("Time Remaining %02d:%02d:%02d%n", 0, 0, 0);
            jLabel5.setText(formattedTime);
            dispose();
            JOptionPane.showMessageDialog(rootPane, "Time up", "Time up", JOptionPane.INFORMATION_MESSAGE);

        }

        public void stopTimer() {
            isRunning = false;
        }
    }
    private Timer timer;  // Declare the Timer instance

    public cbt(String matno, String email) {
        initComponents();
        currentQuestionIndex = 0;
        getQuestionsFromDatabase();
        displayNextQuestion();
        jButton4.addActionListener(this::optionButtonActionPerformed);
        jButton5.addActionListener(this::optionButtonActionPerformed);
        jButton6.addActionListener(this::optionButtonActionPerformed);
        jButton7.addActionListener(this::optionButtonActionPerformed);
        timer = new Timer();  // Initialize the Timer
        Thread timerThread = new Thread(timer);
        timerThread.start();
        this.matno = matno;
        this.email = email;

    }

    private void getQuestionsFromDatabase() {
        try {
            // Connect to the database
            String path = "jdbc:mysql://localhost:3306/cbt";
            String user = "root";
            String pass = "$Temilolu12";
            Connection con = DriverManager.getConnection(path, user, pass);
            // Execute a query to retrieve data
            String selectQuery = "SELECT * FROM questions ORDER BY RAND()";
            PreparedStatement ps = con.prepareStatement(selectQuery);
            ResultSet rs = ps.executeQuery();
            // Populate the ArrayList with questions
            while (rs.next()) {
                int number = rs.getInt("number");
                String questionText = rs.getString("question");
                String optionA = rs.getString("optionA");
                String optionB = rs.getString("optionB");
                String optionC = rs.getString("optionC");
                String optionD = rs.getString("optionD");
                String correctAnswer = rs.getString("answer");
                Question question = new Question(number, questionText, optionA, optionB, optionC, optionD, correctAnswer);

                questionsList.add(question);

            }
        } catch (SQLException ex) {
        }
    }

    private void checkAnswers() {
        for (Question question : questionsList) {
            String selectedAnswer = question.getSelectedAnswer();
            String correctAnswer = question.getCorrectAnswer();

            if (selectedAnswer.equals(correctAnswer)) {
                score += 1;
                System.out.println(" Correct Answer: " + correctAnswer);
            } else {
                System.out.println("Incorrect! Correct Answer: " + correctAnswer);
            }
        }

        System.out.println("Your Score: " + score);
        System.out.println("Your email: " + email);

    }

    private void displayNextQuestion() {

        if (currentQuestionIndex < questionsList.size() + 1) {
            Question currentQuestion = questionsList.get(currentQuestionIndex);

            jLabel6.setText(currentQuestion.getNumber() + ". " + currentQuestion.getQuestion());
            jButton4.setText(currentQuestion.getOptionA());
            jButton5.setText(currentQuestion.getOptionB());
            jButton6.setText(currentQuestion.getOptionC());
            jButton7.setText(currentQuestion.getOptionD());

            currentQuestionIndex++;

            // Enable or disable buttons based on the current index
            updateButtonState();
        } else {
            // Handle the case when the user is already at the last question
            JOptionPane.showMessageDialog(this, "You are at the last question.");
        }
    }

    private void displayPreviousQuestion() {

        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
            Question currentQuestion = questionsList.get(currentQuestionIndex);
            jLabel6.setText(currentQuestion.getNumber() + ". " + currentQuestion.getQuestion());
            jButton4.setText(currentQuestion.getOptionA());
            jButton5.setText(currentQuestion.getOptionB());
            jButton6.setText(currentQuestion.getOptionC());
            jButton7.setText(currentQuestion.getOptionD());
        updateButtonState();
        } else {
            // Handle the case when the user is already at the first question
            JOptionPane.showMessageDialog(this, "You are at the first question.");
        }
    }

    private void clearOptionButtonSelection() {
        jButton4.setSelected(false);
        jButton5.setSelected(false);
        jButton6.setSelected(false);
        jButton7.setSelected(false);
    }

// Helper method to get the selected option from the buttons
// Helper method to get the selected option from the buttons
    private String getSelectedOption() {
        if (jButton4.isSelected()) {
            return jButton4.getText();
        } else if (jButton5.isSelected()) {
            return jButton5.getText();
        } else if (jButton6.isSelected()) {
            return jButton6.getText();
        } else if (jButton7.isSelected()) {
            return jButton7.getText();
        } else {
            return "";
        }
    }

// Helper method to set the selected option on the buttons
    private void setSelectedOption(String selectedOption) {
        jButton4.setSelected(jButton4.getText().equals(selectedOption));
        jButton5.setSelected(jButton5.getText().equals(selectedOption));
        jButton6.setSelected(jButton6.getText().equals(selectedOption));
        jButton7.setSelected(jButton7.getText().equals(selectedOption));
    }

    private void updateButtonState() {
        // Enable or disable buttons based on the current index
        jButton1.setEnabled(currentQuestionIndex > 0); // Enable "Previous" button if not on the first question
        jButton3.setEnabled(currentQuestionIndex < questionsList.size()); // Enable "Next" button if not on the last question
    }

    private void getDuration() {
        try {
            // Connect to the database
            String path = "jdbc:mysql://localhost:3306/cbt";
            String user = "root";
            String pass = "$Temilolu12";
            Connection con = DriverManager.getConnection(path, user, pass);
            // Execute a query to retrieve data
            String selectQuery = "SELECT * FROM questions";
            PreparedStatement ps = con.prepareStatement(selectQuery);
            ResultSet rs = ps.executeQuery();
            // Populate the ArrayList with questions
            while (rs.next()) {
                String durationText = rs.getString("duration");

            }
        } catch (Exception e) {
        }
    }

    

    private void setButtonSelected(javax.swing.JButton button) {
        // Set the selected appearance for the button
        button.setBackground(Color.lightGray); // You can customize the color
    }

    private void resetButtonColors() {
        // Reset the appearance of all buttons
        jButton4.setBackground(null);
        jButton5.setBackground(null);
        jButton6.setBackground(null);
        jButton7.setBackground(null);
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
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setFont(new java.awt.Font("Perpetua", 0, 18)); // NOI18N
        jButton1.setText("Previous");
        jButton1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Perpetua", 0, 18)); // NOI18N
        jButton2.setText("Submit");
        jButton2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Perpetua", 0, 18)); // NOI18N
        jButton3.setText("Next");
        jButton3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Perpetua", 0, 18)); // NOI18N
        jButton4.setText("jButton4");

        jButton5.setFont(new java.awt.Font("Perpetua", 0, 18)); // NOI18N
        jButton5.setText("jButton5");

        jButton6.setFont(new java.awt.Font("Perpetua", 0, 18)); // NOI18N
        jButton6.setText("jButton6");

        jButton7.setFont(new java.awt.Font("Perpetua", 0, 18)); // NOI18N
        jButton7.setText("jButton7");

        jLabel1.setFont(new java.awt.Font("Perpetua", 0, 18)); // NOI18N
        jLabel1.setText("A");

        jLabel2.setFont(new java.awt.Font("Perpetua", 0, 18)); // NOI18N
        jLabel2.setText("B");

        jLabel3.setFont(new java.awt.Font("Perpetua", 0, 18)); // NOI18N
        jLabel3.setText("C");

        jLabel4.setFont(new java.awt.Font("Perpetua", 0, 18)); // NOI18N
        jLabel4.setText("D");

        jLabel5.setText("jLabel5");

        jLabel6.setFont(new java.awt.Font("Perpetua", 1, 30)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(99, 99, 99)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(92, 92, 92)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 633, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(102, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 103, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(109, Short.MAX_VALUE))
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
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        displayNextQuestion();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        displayPreviousQuestion();

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed
    public int getScore() {
        return score;
    }

    private void testStatus() {
        String updateQuery = "UPDATE cbtrecords SET status = ? WHERE mat_no = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cbt", "root", "$Temilolu12");
                PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            String status = "done";

            // Set the parameters for the update statement
            preparedStatement.setString(1, status);

            //update where matno equals matno
            preparedStatement.setString(2, matno); // Assuming question_id is an integer and starts from 1, adjust accordingly

            // Execute the update statement
            preparedStatement.executeUpdate();

            // Optionally commit the changes (depending on your transaction requirements)
            //connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String formattedTime = String.format("Time Remaining %02d:%02d:%02d%n", 0, 0, 0);
        jLabel5.setText(formattedTime);
        timer.stopTimer();
        checkAnswers();
        result jfrm8 = new result(score, questionsList, matno);
        jfrm8.show();
        int score = getScore();
        jfrm8.setScore(score);
        System.out.println(matno);

        
        
        String updateQuery = "UPDATE cbtrecords SET score = ? WHERE mat_no = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cbt", "root", "$Temilolu12");
                PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            String studentMarks = String.valueOf(score);

            // Set the parameters for the update statement
            preparedStatement.setString(1, studentMarks);

            //update where matno equals matno
            preparedStatement.setString(2, matno);

            // Execute the update statement
            preparedStatement.executeUpdate();

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
                message.setSubject("CBT Result");
                message.setText("Your score for your CBT test is: " + score);
                Transport.send(message);
                JOptionPane.showMessageDialog(rootPane, "Email Sent");
            } catch (MessagingException e) {

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        testStatus();
    }//GEN-LAST:event_jButton2ActionPerformed

//    private void testResult() {
//        String Query = "UPDATE cbrrecords SET questions = ?, WHERE mat_no = ?";
//
//        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cbt_quiz", "root", "stanislaus1@");
//                PreparedStatement preparedStatement = connection.prepareStatement(Query)) {
//
//            String questionsString = questionsList.stream().map(Object::toString).collect(Collectors.joining(", "));
//
//            preparedStatement.setInt(1, matNo);
//            preparedStatement.setString(1, questionsString);
//
//            preparedStatement.executeUpdate();
//
//            // Optionally commit the changes (depending on your transaction requirements)
//            //connection.commit();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        new cbt("", "").setVisible(true);

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
            java.util.logging.Logger.getLogger(cbt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(cbt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(cbt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(cbt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
