/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package createaccount;




import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.Math.random;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import login.Banking;
/**
 * FXML Controller class
 *
 * @author admin
 */
public class CreateAccountController implements Initializable {
    
    private FileChooser filechooser= new FileChooser();
    private File file;
    private FileInputStream fis;
    @FXML
    private ImageView pic;
    
    
    @FXML
    private TextField name;
    
    @FXML
    private TextField idcardno;
    
    @FXML
    private TextField mobileno;
    
    @FXML
    private TextField city;
    
    @FXML
    private TextField address;
    
    @FXML
    private TextField accountno;
    
    @FXML
    private TextField pin;
    
    @FXML
    private TextField balance;
    
    @FXML
    private TextField answer;
    
    @FXML
    private DatePicker dob;
    
    @FXML
    private ComboBox<String> gender;
    
    @FXML
    private ComboBox<String> martialstatus;
    
    @FXML
    private ComboBox<String> religion;
    
    @FXML
    private ComboBox<String> accounttype;
    
    @FXML
    private ComboBox<String> questions;
    
    
    ObservableList<String> list = FXCollections.observableArrayList("Male","Female","Other");
    ObservableList<String> list1 = FXCollections.observableArrayList("Single","Married");
    ObservableList<String> list2 = FXCollections.observableArrayList("Islam","Christian","Hindu","Others");
    ObservableList<String> list3 = FXCollections.observableArrayList("Saving","Current");
    ObservableList<String> list4 = FXCollections.observableArrayList("What is your pet name?","What is your childhood town?","What is your nick name?");

    public void backToLogin(MouseEvent event) throws IOException{
    Banking.stage.getScene().setRoot(FXMLLoader.load(getClass().getResource("/login/LoginScreen.fxml")));
    }
    public void closeApp(MouseEvent event){
        Platform.exit();
        System.exit(0);
    }
    
    public void setUpPic(MouseEvent event){
        filechooser.getExtensionFilters().add(new ExtensionFilter("Images Files","*.png","*.jpg"));
        file = filechooser.showOpenDialog(null);
        if(file!= null){
            Image img = new Image(file.toURI().toString(), 150, 150 , true, true);
            pic.setImage(img);
            pic.setPreserveRatio(true);
        }
        
    }
    
    public boolean validateName(){
        Pattern p = Pattern.compile("[a-zA-Z ]+");
        Matcher m = p.matcher(name.getText());
        if(m.find() && m.group().equals(name.getText())){
            return true;
        }
        else{
                Alert a = new Alert(AlertType.ERROR);
                a.setTitle("Wrong Name");
                a.setHeaderText(" Your Name is wrong .");
                a.setContentText("Please enter characters only in name . PLEASE TRY AGAIN..!!!");
                a.showAndWait();
                return false;  
        }
    }
    
    public boolean validateMobileNo(){
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(mobileno.getText());
        if(m.find() && m.group().equals(mobileno.getText())){
            return true;
        }
        else{
                Alert a = new Alert(AlertType.ERROR);
                a.setTitle("Wrong mobileno");
                a.setHeaderText(" Your mobileno is wrong .");
                a.setContentText("Please enter numbers only in mobileno . PLEASE TRY AGAIN..!!!");
                a.showAndWait();
                return false;  
        }
    }
    
    public boolean validateIdCardNo(){
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(idcardno.getText());
        if(m.find() && m.group().equals(idcardno.getText())){
            return true;
        }
        else{
                Alert a = new Alert(AlertType.ERROR);
                a.setTitle("Wrong Id Card No ");
                a.setHeaderText(" Your Id Card No is wrong .");
                a.setContentText("Please enter numbers only in idcardno . PLEASE TRY AGAIN..!!!");
                a.showAndWait();
                return false;  
        }
    }
    
    public boolean validateBalance(){
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(balance.getText());
        if(m.find() && m.group().equals(balance.getText())){
            return true;
        }
        else{
                Alert a = new Alert(AlertType.ERROR);
                a.setTitle("Invalid Balance ");
                a.setHeaderText(" Your Balance is wrong .");
                a.setContentText("Please enter numbers only in Balance . PLEASE TRY AGAIN..!!!");
                a.showAndWait();
                return false;  
        }
    }
    
    public void clearAllFields(){
        name.clear();
        idcardno.clear();
        mobileno.clear();
        gender.getSelectionModel().clearSelection();
        religion.getSelectionModel().clearSelection();
        martialstatus.getSelectionModel().clearSelection();
        dob.getEditor().clear();
        city.clear();
        address.clear();
        pin.clear();
        accounttype.getSelectionModel().clearSelection();
        balance.clear();
        questions.getSelectionModel().clearSelection();
        answer.clear();
        Image img = new Image("/images/default_pic.jpg");
        pic.setImage(img);
        accountno.setText(String.valueOf(generateAccountNo()));
               
    }
    
    public int generateAccountNo(){
        
        Random rand = new Random();
        int num = rand.nextInt(899999) + 100000;
        return num;
        
    }
    
    
    public void newAccount(MouseEvent event){
        Connection con=null;
        PreparedStatement ps = null;
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank?serverTimezone=UTC" , "root", "");
            
            if(validateName() && validateMobileNo() && validateIdCardNo() && validateBalance() ){
            String sql ="INSERT INTO userdata (Name, ICN, MobileNo, Gender, Religion, MartialStatus, DOB,City,Address,AccountNo,PIN,AccountType,Balance, SecurityQuestion,Answer,ProfilePic) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, name.getText());
            ps.setString(2, idcardno.getText());
            ps.setString(3, mobileno.getText());
            ps.setString(4, gender.getValue());
            ps.setString(5, religion.getValue());
            ps.setString(6, martialstatus.getValue());
            ps.setString(7, ((TextField)dob.getEditor()).getText());
            ps.setString(8, city.getText());
            ps.setString(9, address.getText());
            ps.setString(10, accountno.getText());
            ps.setString(11, pin.getText());
            ps.setString(12, accounttype.getValue());
            ps.setString(13, balance.getText());
            ps.setString(14, questions.getValue());
            ps.setString(15, answer.getText());
            fis = new FileInputStream(file);
            ps.setBinaryStream(16, (InputStream)fis, (int)file.length());
            
            int i = ps.executeUpdate();
            if(i>0){
                Alert a = new Alert(AlertType.INFORMATION);
                a.setTitle("Account Created");
                a.setHeaderText("Account Created Sucessfully.");
                a.setContentText("Your account is created sucessfully. You can now login with your account no and pin !!!");
                a.showAndWait();  
                clearAllFields();
            }
            else
            {
                Alert a = new Alert(AlertType.ERROR);
                a.setTitle("Error");
                a.setHeaderText("Account Not Created .");
                a.setContentText("Your account is not created. There is some error. PLEASE TRY AGAIN..!!!");
                a.showAndWait();
            }
           } 
            
        }catch(Exception e){
            Alert a = new Alert(AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Error in creating account");
            a.setContentText("Your account is not created. There is some technical issue..!!!" + e.getMessage());
            a.showAndWait();
            
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        gender.setItems(list);
        martialstatus.setItems(list1);
        religion.setItems(list2);
        accounttype.setItems(list3);
        questions.setItems(list4);
        accountno.setText(String.valueOf(generateAccountNo()));
        accountno.setEditable(false);
        
        
        
    }    
    
}
