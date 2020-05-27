/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dashboard;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import login.Banking;
import login.LoginScreenController;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class DashboardController implements Initializable {
    
    private double xoffset = 0;
    private double yoffset = 0;
    
    
    @FXML
    private Pane dashboard_main;
    
    @FXML
    private Text name;
    
    @FXML
    private Circle profilepic;
    @FXML
    private FontAwesomeIconView ico;
    
    @FXML
    private void closeApp(MouseEvent event) {
        Platform.exit();
        System.exit(0);
        
    }
    
    @FXML
    private void minimizeApp(MouseEvent event) {
     Stage stage = (Stage) ico.getScene().getWindow();
     
     stage.setIconified(true);     
    }
    
    public void setData(){
        Connection con=null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank?serverTimezone=UTC" , "root", "");
            String sql = "SELECT * FROM userdata WHERE AccountNo=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, LoginScreenController.acc);
            //JOptionPane.showMessageDialog(null, "Dashboard : "+ LoginScreenController.acc);
            
            
             rs = ps.executeQuery();
            if(rs.next()){
                
                name.setText(rs.getString("Name"));
                InputStream is = rs.getBinaryStream("ProfilePic");
                OutputStream os = new FileOutputStream(new File("pic.jpg"));
                byte[] content = new byte[1024];
                int size = 0;
                while((size = is.read(content)) != -1){
                    os.write(content, 0, size);
                }
                os.close();
                is.close();
                Image img = new Image("file:pic.jpg",false);
                profilepic.setFill(new ImagePattern(img));
                
                
            }
            else
            {   Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setHeaderText("Error in login");
                a.setContentText("Your account number or pin is wrong. Enter again..!!!" );
                a.showAndWait();
                
                
            }
            
            
        }catch(Exception e){
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setHeaderText("Error in login .");
                a.setContentText("There is some error. PLEASE TRY AGAIN..!!!");
                a.showAndWait();
            
        }
    
}
    @FXML
    public void click(MouseEvent event){
     xoffset=event.getSceneX();
     yoffset=event.getSceneY();   
    }
    
    @FXML
    public void drag(MouseEvent event){
      LoginScreenController.stage.setX(event.getScreenX()- xoffset);
      LoginScreenController.stage.setY(event.getScreenY()- yoffset);  
    }
    
    @FXML
    public void accountInformation(MouseEvent event) throws IOException{
      Parent fxml= FXMLLoader.load(getClass().getResource("/accountinfo/AccountInformation.fxml"));
        dashboard_main.getChildren().removeAll();
        dashboard_main.getChildren().addAll(fxml);
    }
    
    @FXML
    public void withdraw(MouseEvent event) throws IOException{
      Parent fxml= FXMLLoader.load(getClass().getResource("/withdraw/WithdrawAmount.fxml"));
        dashboard_main.getChildren().removeAll();
        dashboard_main.getChildren().addAll(fxml);
    }
    
    @FXML
    public void transactionHistory(MouseEvent event) throws IOException{
      Parent fxml= FXMLLoader.load(getClass().getResource("/transactionhistory/TransactionHistory.fxml"));
        dashboard_main.getChildren().removeAll();
        dashboard_main.getChildren().addAll(fxml);
    }
    
    @FXML
    public void deposit(MouseEvent event) throws IOException{
      Parent fxml= FXMLLoader.load(getClass().getResource("/deposit/DepositAmount.fxml"));
        dashboard_main.getChildren().removeAll();
        dashboard_main.getChildren().addAll(fxml);
    }
    
    @FXML
    public void pinChange(MouseEvent event) throws IOException{
      Parent fxml= FXMLLoader.load(getClass().getResource("/changepin/ChangePin.fxml"));
        dashboard_main.getChildren().removeAll();
        dashboard_main.getChildren().addAll(fxml);
    }
    
    @FXML
    public void transferAmount(MouseEvent event) throws IOException{
      Parent fxml= FXMLLoader.load(getClass().getResource("/transferamount/TransferAmount.fxml"));
        dashboard_main.getChildren().removeAll();
        dashboard_main.getChildren().addAll(fxml);
    }
    
    
    @FXML
    public void mainScreen() throws IOException{
      Parent fxml= FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        dashboard_main.getChildren().removeAll();
        dashboard_main.getChildren().addAll(fxml);
    }
    
    public void logout(MouseEvent event) throws IOException{
        ((Node)event.getSource()).getScene().getWindow().hide();
        Parent root= FXMLLoader.load(getClass().getResource("/login/LoginScreen.fxml"));
                Scene scene =new Scene(root);
                scene.getStylesheets().add(getClass().getResource("/design/design.css").toExternalForm());
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.show();
                root.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                xoffset=event.getSceneX();
                yoffset=event.getSceneY();
            }
        
        
        });
        
        root.setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX()- xoffset);
                stage.setY(event.getScreenY()- yoffset);
            }
        
        
        });
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setData();
        try {
            mainScreen();
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }    
    
}
