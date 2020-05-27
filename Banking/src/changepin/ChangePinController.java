
package changepin;

import dashboard.DashboardController;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import login.LoginScreenController;


public class ChangePinController implements Initializable {

    @FXML
    private TextField oldpin;
    
    @FXML
    private TextField newpin;
    
    @FXML
    private TextField confirmpin;
    
    DashboardController d = new DashboardController();
    
     public void changePin(MouseEvent event){
        Connection con=null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank?serverTimezone=UTC" , "root", "");
            String sql = "SELECT * FROM userdata WHERE AccountNo=? and PIN=? ";
            ps = con.prepareStatement(sql);
            ps.setString(1, LoginScreenController.acc);
            ps.setString(2,oldpin.getText());
            
            
             rs = ps.executeQuery();
            if(rs.next()){
               if(newpin.getText().equals(confirmpin.getText())){
                   
                   String sql1 = "UPDATE userdata SET PIN='"+newpin.getText()+"' WHERE AccountNo ='"+LoginScreenController.acc+"'";
                   ps = con.prepareStatement(sql1);
                   ps.execute();
                   
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("PIN Changed");
                a.setHeaderText("PIN Changed Sucessfully ");
                a.setContentText("Your PIN has been changed !!! Now you have to login again with new PIN !!");
                a.showAndWait();
                
                oldpin.setText("");
                newpin.setText("");
                confirmpin.setText("");
                d.logout(event);
                   
                   
               
               
               }  
                
                
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
                a.setContentText("There is some error. PLEASE TRY AGAIN..!!!"+e.getMessage());
                a.showAndWait();
            
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
