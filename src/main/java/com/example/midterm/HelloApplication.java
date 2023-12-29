package com.example.midterm;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.LightBase;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
import org.w3c.dom.ranges.Range;

import javax.crypto.NullCipher;
import javax.xml.transform.Result;
import java.io.IOException;
import java.sql.*;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

public class HelloApplication extends Application {

    private String FN,ln,BD,rg,ex,ge,t,mb,emb;
    private int TCost;
  private boolean add_client;
    private boolean f,g,z,m,mo,yo;
    Connection connection;
    @Override
    public void start(Stage stage) throws IOException {


        try {
           connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym", "root", "fekrat");
        } catch (Exception exception) {
            System.out.println(exception.toString());
        }
        BorderPane pane = new BorderPane();
        TextField txtUserName = new TextField();
        PasswordField txtPassword = new PasswordField();
        Button btnLogin = new Button("Login");
        Image logoLN = new Image("file:src/main/resources/tigers_octagon_logo-transformed.png");
        ImageView logor = new ImageView(logoLN);
        Label lblUserName = new Label("User Name: ", txtUserName);
        lblUserName.setContentDisplay(ContentDisplay.BOTTOM);
        Label lblPassword = new Label("Password: ", txtPassword);
        lblPassword.setContentDisplay(ContentDisplay.BOTTOM);
        logor.setFitHeight(60);
        logor.setFitWidth(200);
        txtUserName.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(1.5))));
        txtPassword.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(1.5))));

        Label lblAccess = new Label("");


        txtUserName.setMaxWidth(150);
        txtPassword.setMaxWidth(150);
        logor.setSmooth(true);
        VBox vb = new VBox();
        lblUserName.setPadding(new Insets(20,20,10,10));
        lblPassword.setPadding(new Insets(10));
        btnLogin.setPadding(new Insets(10));
        btnLogin.setPrefSize(60,40);
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().addAll(logor,lblUserName, lblPassword, btnLogin);
        pane.setCenter(vb);
        pane.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(4),new Insets(10))));
        vb.getChildren().add(lblAccess);
        Button validBtn = new Button("VALIDATION");
        validBtn.setPrefSize(100, 100);

        Button CManagebtn = new Button("CLIENTS\nMANAGMENT");
        CManagebtn.setPrefSize(100, 100);

        Button PaymentsBtn = new Button("PAYMENTS\nMANAGMENT");
        PaymentsBtn.setPrefSize(100, 100);
        Scene scene = new Scene(pane, 350, 300);;

     scene.setOnKeyPressed(e->
     { if(e.getCode().toString().equals("ENTER"))

         try {

             Statement statement = connection.createStatement();
             Statement statement2 = connection.createStatement();


             ResultSet resultSet = statement.executeQuery("SELECT * FROM `employee` WHERE username= '" + txtUserName.getText() + "' AND password= '" + txtPassword.getText() + "'");
             ResultSet resultSet2 = statement2.executeQuery("SELECT privilege FROM `employee` WHERE username= '" + txtUserName.getText() + "' AND password= '" + txtPassword.getText() + "'");


             if (resultSet.next()) {
                 if (resultSet2.next()) {
                     boolean isAdmin = resultSet2.getBoolean("privilege");

                     // Enable or disable the button based on the isAdmin value
                     CManagebtn.setDisable(!isAdmin);
                     PaymentsBtn.setDisable(!isAdmin);
                 }

                 System.out.println(resultSet.getString(3));

                 lblAccess.setTextFill(Color.GREEN);
                 lblAccess.setText("Access Granted");
                 Stage dashboard = new Stage();
                 dashboard.setTitle("USER - " + txtUserName.getText());


                 BorderPane root = new BorderPane();


                 VBox btnpane = new VBox();
                 btnpane.getChildren().add(PaymentsBtn);
                 btnpane.setAlignment(Pos.BOTTOM_RIGHT);
                 validBtn.setStyle("-fx-pref-width:200 ; -fx-pref-height: 100;");
                 CManagebtn.setStyle("-fx-pref-width:200 ; -fx-pref-height: 100;");
                 PaymentsBtn.setStyle("-fx-pref-width:200 ; -fx-pref-height: 100;");
                 validBtn.setBackground(new Background(new BackgroundFill(Color.rgb(180, 196, 36),new CornerRadii(7), new Insets(4))));
                 CManagebtn.setBackground(new Background(new BackgroundFill(Color.rgb(180, 196, 36),new CornerRadii(7), new Insets(4))));
                 PaymentsBtn.setBackground(new Background(new BackgroundFill(Color.rgb(180, 196, 36),new CornerRadii(7), new Insets(4))));
                 root.setPadding(new Insets(10));
                 root.setCenter(validBtn);
                 root.setBottom(btnpane);
                 root.setTop(CManagebtn);
                 root.setBackground(new Background(new BackgroundFill(Color.GREY,new CornerRadii(0), new Insets(4))));;


                 Scene scene2 = new Scene(root, 700, 500);
                 dashboard.setScene(scene2);

                 dashboard.show();
                 stage.close();

             } else {
                 lblAccess.setTextFill(Color.RED);
                 lblAccess.setText("Access Denied");
             }
         } catch (Exception exception) {
             System.out.println(exception.toString());
         }});

         btnLogin.setOnAction(ex ->
         {
             try {

                 Statement statement = connection.createStatement();
                 Statement statement2 = connection.createStatement();


                 ResultSet resultSet = statement.executeQuery("SELECT * FROM `employee` WHERE username= '" + txtUserName.getText() + "' AND password= '" + txtPassword.getText() + "'");
                 ResultSet resultSet2 = statement2.executeQuery("SELECT privilege FROM `employee` WHERE username= '" + txtUserName.getText() + "' AND password= '" + txtPassword.getText() + "'");


                 if (resultSet.next()) {
                     if (resultSet2.next()) {
                         boolean isAdmin = resultSet2.getBoolean("privilege");

                         // Enable or disable the button based on the isAdmin value
                         CManagebtn.setDisable(!isAdmin);
                         PaymentsBtn.setDisable(!isAdmin);
                     }

                     System.out.println(resultSet.getString(3));

                     lblAccess.setTextFill(Color.GREEN);
                     lblAccess.setText("Access Granted");
                     Stage dashboard = new Stage();
                     dashboard.setTitle("USER - " + txtUserName.getText());


                     BorderPane root = new BorderPane();


                     VBox btnpane = new VBox();
                     btnpane.getChildren().add(PaymentsBtn);
                     btnpane.setAlignment(Pos.BOTTOM_RIGHT);
                     validBtn.setStyle("-fx-pref-width:200 ; -fx-pref-height: 100;");
                     CManagebtn.setStyle("-fx-pref-width:200 ; -fx-pref-height: 100;");
                     PaymentsBtn.setStyle("-fx-pref-width:200 ; -fx-pref-height: 100;");
                     validBtn.setBackground(new Background(new BackgroundFill(Color.rgb(180, 196, 36),new CornerRadii(7), new Insets(4))));
                     CManagebtn.setBackground(new Background(new BackgroundFill(Color.rgb(180, 196, 36),new CornerRadii(7), new Insets(4))));
                     PaymentsBtn.setBackground(new Background(new BackgroundFill(Color.rgb(180, 196, 36),new CornerRadii(7), new Insets(4))));
                     root.setCenter(validBtn);
                     root.setBottom(btnpane);
                     root.setTop(CManagebtn);
                        root.setPadding(new Insets(10));
                     root.setBackground(new Background(new BackgroundFill(Color.GREY,new CornerRadii(0), new Insets(4))));;


                     Scene scene2 = new Scene(root, 800, 600);
                     dashboard.setScene(scene2);

                     dashboard.show();
                     stage.close();

                 } else {
                     lblAccess.setTextFill(Color.RED);
                     lblAccess.setText("Access Denied");
                 }
             } catch (Exception exception) {
                 System.out.println(exception.toString());
             }
         });



           CManagebtn.setOnAction(e->
           {
               HBox pane3 = new HBox();
               VBox persInfoPane = new VBox();
               VBox regesPane = new VBox();
               VBox logoPane = new VBox();
               HBox GenAge = new HBox();
               VBox idPer = new VBox();
               VBox genLab = new VBox();
               VBox ESR = new VBox();
               HBox ES = new HBox();
               VBox accessPane = new VBox();
               TextField ids = new TextField();
               ids.setAlignment(Pos.CENTER);
               ids.setPrefSize(150, 40);
               ids.setBorder(new Border(new BorderStroke(Color.rgb(180, 196, 36), BorderStrokeStyle.DASHED, new CornerRadii(3), new BorderWidths(2))));

               ids.setStyle("-fx-font-size: 16px;");


               TextField firstName = new TextField();
               TextField lastName = new TextField();
               Label fis_Name = new Label("first Name:");
               fis_Name.setLabelFor(firstName);
               Label lasName = new Label("last Name");



               idPer.setSpacing(20);
               //persInfoPane.setStyle("-fx-border-color: black; -fx-border-width: 0.5px;");

               Label label = new Label("PERSONAL INFORMATION",persInfoPane);
               label.setFont(Font.font("Rust"));
               label.setContentDisplay(ContentDisplay.BOTTOM);

               DatePicker BirthDatePic = new DatePicker();
               Label BirthDate = new Label("Date Of Birth:");
               BirthDate.setPadding(new Insets(20,20,0,0));
               label.setPadding(new Insets(20,20,0,0));
               ChoiceBox <String> genderList = new ChoiceBox<>();
               genderList.getItems().addAll("Male", "Female");
               Label GEN = new Label("Gender: ");
               genLab.getChildren().addAll(GEN,genderList);
               GEN.setPadding(new Insets(20,20,0,0));
               TextField age = new TextField();
               age.setEditable(false);
               age.setAlignment(Pos.CENTER);
               age.setPrefSize(40,40);
               age.setPadding(new Insets(20,20,0,0));
               Label ageLabe = new Label("Age", age);
               ageLabe.setPadding(new Insets(20,20,0,0));
               ageLabe.setContentDisplay(ContentDisplay.BOTTOM);
               BirthDatePic.setOnAction(eP ->
               {
                   int n =BirthDatePic.getValue().getYear();
                   int r= Year.now().getValue();
                   age.setText(Integer.toString((r-n)));

               });
               ChoiceBox<String> type = new ChoiceBox<>();
               type.getItems().addAll("Regular", "Red Cross", "Military", "Family Bundle");
               Label typeLabe = new Label("Offer Type", type);
               typeLabe.setContentDisplay(ContentDisplay.BOTTOM);
               typeLabe.setPadding(new Insets(20,20,20,20));
               TextField mobileNb = new TextField();
               Label MbNb = new Label("Mobile Number:");
               MbNb.setPadding(new Insets(20,20,0,0));
               TextField emgMbnb = new TextField();
               Label emg = new Label("Emergency contact:");
               emg.setPadding(new Insets(20,20,0,0));

                HBox id =new HBox();
                Button eP= new Button("  ->",ids);
               eP.setBackground(new Background(new BackgroundFill(Color.rgb(180, 196, 36),new CornerRadii(15),new Insets(0
               ))));
                id.getChildren().addAll(eP);

               GenAge.getChildren().addAll(ageLabe, genLab);
               GenAge.setSpacing(20);
               lasName.setPadding(new Insets(20,20,0,0));
               idPer.getChildren().addAll(id);
               //idPer.setBorder(new Border(new BorderStroke(Color.rgb(180, 196, 36),BorderStrokeStyle.SOLID,new CornerRadii(20),new BorderWidths(5))));


               persInfoPane.setPadding(new Insets(20,20,20,20));

               persInfoPane.getChildren().addAll(fis_Name,firstName, lasName,lastName,MbNb , mobileNb,emg,emgMbnb,BirthDate,BirthDatePic, GenAge  );

               Label error = new Label("");
               error.setFont(Font.font(10));

               persInfoPane.setBorder(new Border( new BorderStroke(Color.rgb(180,196,36),BorderStrokeStyle.SOLID,new CornerRadii(20),new BorderWidths(5))));
               error.setPadding(new Insets(20,20,20,20));

               idPer.getChildren().addAll(label,persInfoPane,error);
               RadioButton Edit1= new RadioButton("Edit");
               RadioButton Add = new RadioButton("Add");

               ToggleGroup EA = new ToggleGroup();



               Edit1.setToggleGroup(EA);
               Add.setToggleGroup(EA);
               //Add.setSelected(true);
               regesPane.setBorder(new Border(new BorderStroke(Color.rgb(180, 196, 36),BorderStrokeStyle.SOLID,new CornerRadii(20),new BorderWidths(5))));
               Label reg = new Label("Registration Details", regesPane);
               reg.setFont(Font.font(14));
               reg.setContentDisplay(ContentDisplay.BOTTOM);
               reg.setPadding(new Insets(20,20,0,0));



               DatePicker Regist = new DatePicker();
               DatePicker Expiry = new DatePicker();
               Label regD = new Label("Registration Date");
               Label ExpD = new Label("Expiry Date");
               ExpD.setPadding(new Insets(20,20,0,0));
               Label access = new Label("ACCESS");
                Regist.setValue(LocalDate.now());

               CheckBox yoga =new CheckBox("YOGA");
               CheckBox Fitness =new CheckBox("FITNESS");
               CheckBox MMA =new CheckBox("MMA");
               CheckBox GYMNASTIC =new CheckBox("GYMNASTIC");
               CheckBox moujthai = new CheckBox("MOUJTHAI");
               CheckBox zumba =new CheckBox("ZUMBA");
               accessPane.getChildren().addAll( Fitness, yoga ,MMA, GYMNASTIC, moujthai, zumba);
               accessPane.setPadding(new Insets(0,0,20,20));
               accessPane.setSpacing(5);







               regesPane.getChildren().addAll(regD ,Regist,ExpD,Expiry, typeLabe, access,accessPane);
               //regesPane.setBackground(new Background(new BackgroundFill(Color.rgb(180, 196, 36),new CornerRadii(0),new Insets(0))));
               pane3.setBackground(new Background(new BackgroundFill(Color.GREY,new CornerRadii(0),new Insets(0))));

               ES.getChildren().addAll(Edit1,Add);
               ES.setBorder(new Border(new BorderStroke(Color.rgb(180, 196, 36),BorderStrokeStyle.SOLID,new CornerRadii(20),new BorderWidths(5))));
               ES.setAlignment(Pos.CENTER);
               ES.setSpacing(20);
               ES.setPadding(new Insets(20,20,20,20));
               regesPane.setPadding(new Insets(20,20,20,20));
               ESR.setSpacing(30);
               ESR.getChildren().addAll(ES,reg);
               Image logo = new Image("file:src/main/resources/tigers_octagon_logo-transformed.png");
               ImageView logoV = new ImageView(logo);
               logoV.setFitHeight(150);
               logoV.setFitWidth(250);
               logoV.setPreserveRatio(true);
               TextField cost = new TextField();
               cost.setPrefSize(50,40);
               cost.setMaxSize(50,40);
               Button check = new Button("CHECK TOTAl", cost);
               //Label costLab = new Label("TOTAL",cost);
              // costLab.setContentDisplay(ContentDisplay.RIGHT);
               VBox try1 = new VBox();
               VBox BtnEnd = new VBox();
               HBox CD = new HBox();
               Button save = new Button("SAVE");
               save.setPrefSize(60,30);
               Button cancelBtn = new Button("CANCEL");
               cancelBtn.setBackground(new Background(new BackgroundFill(Color.rgb(180, 196, 36),new CornerRadii(15),new Insets(0
               ))));
               save.setBackground(new Background(new BackgroundFill(Color.rgb(180, 196, 36),new CornerRadii(15),new Insets(0
               ))));
               Button delete = new Button("DELETE");
               delete.setBackground(new Background(new BackgroundFill(Color.rgb(180, 196, 36),new CornerRadii(15),new Insets(0
               ))));
             Alert  alert = new Alert(Alert.AlertType.ERROR);
             Alert warn = new Alert(Alert.AlertType.WARNING);

               Edit1.setOnAction(ed->
               {   ids.setEditable(true);
                   ids.setDisable(false);
                   eP.setDisable(false);
                   ids.setText("");
                   eP.setOnAction(eP1->
                   {Fitness.setSelected(false);
                       yoga.setSelected(false);
                       MMA.setSelected(false);
                       GYMNASTIC.setSelected(false);
                       moujthai.setSelected(false);
                       zumba.setSelected(false);


                       try {
                           Statement statement = connection.createStatement();
                           ResultSet resultSet = statement.executeQuery("SELECT MAX(clients.Client_ID)\n" +
                                   "FROM clients");
                           if (resultSet.next()) {
                               int temp = resultSet.getInt(1);

                               if (Integer.valueOf(ids.getText()) <= temp && Integer.valueOf(ids.getText()) > 999) {
                                   Statement statment1 = connection.createStatement();
                                   ResultSet resultSet1 = statment1.executeQuery("select * from clients natural join subscription where client_id = " + ids.getText());

                                   while (resultSet1.next()) {
                                       firstName.setText(resultSet1.getString(2));
                                       FN = firstName.getText();
                                       lastName.setText(resultSet1.getString(3));
                                       ln = lastName.getText();
                                       BirthDatePic.setValue(LocalDate.parse(resultSet1.getString(7), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                                       BD = BirthDatePic.getValue().toString();
                                       genderList.setValue(resultSet1.getString(9));
                                       ge = genderList.getValue();
                                       Regist.setValue(LocalDate.parse(resultSet1.getString(12), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                                       rg = Regist.getValue().toString();
                                       Expiry.setValue(LocalDate.parse(resultSet1.getString(13), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                                       type.setValue(resultSet1.getString(8));
                                       ex = Expiry.getValue().toString();
                                       t = type.getValue();
                                       mobileNb.setText(resultSet1.getString(4));
                                       mb = mobileNb.getText();
                                       emgMbnb.setText(resultSet1.getString(5));
                                       emb = emgMbnb.getText();

                                       switch (resultSet1.getString(11)) {
                                           case "fitness":
                                               Fitness.setSelected(true);
                                               f = true;
                                               break;
                                           case "yoga":
                                               yoga.setSelected(true);
                                               yo = true;
                                               break;

                                           case "MMA":
                                               MMA.setSelected(true);
                                               m = true;
                                               break;
                                           case "gymnastics":
                                               GYMNASTIC.setSelected(true);
                                               g = true;
                                               break;
                                           case "moujthai":
                                               moujthai.setSelected(true);
                                               mo = true;
                                               break;
                                           case "zumba":
                                               zumba.setSelected(true);
                                               z = true;
                                               break;
                                       }


                                       firstName.textProperty().addListener((observable, oldValue, newValue) -> {
                                           // This code will be executed whenever the user types or deletes a character in the text field.
                                           System.out.println("Text changed: " + oldValue + " -> " + newValue);
                                       });
                                   }
                               }
                               else {
                                   alert.setTitle("Error");
                                   alert.setHeaderText("ID IS NOT VALID");
                                   alert.showAndWait();
                               }
                           }

                       } catch (Exception em) {
                           System.out.println(em.toString());
                       }
                    save.setOnAction(s->
                    {
                        if(firstName.getText().isEmpty() || lastName.getText().isEmpty()|| mobileNb.getText().isEmpty() || emgMbnb.getText().isEmpty()||
                                BirthDatePic.getValue() == null || Regist.getValue() == null || Expiry.getValue() == null || type.getValue().isEmpty() || genderList.getValue().isEmpty()  )
                        {
                            warn.setHeaderText("Cant have an empty field ");
                            warn.setTitle("Warning");
                            warn.show();
                        }
                        else if(!Fitness.isSelected() && !GYMNASTIC.isSelected() && !MMA.isSelected()
                                && !yoga.isSelected() && !zumba.isSelected() && !moujthai.isSelected())
                        {
                            warn.setTitle("Warning");
                            warn.setHeaderText("No Access type is chosen ");
                            warn.show();
                        }
                        else if(!isValid(mobileNb.getText()) || !isValid(emgMbnb.getText()))
                        {
                            alert.setTitle("Error");
                            alert.setHeaderText("Mobile number is not valid ");
                            alert.show();
                        }
                        else if(BirthDatePic.getValue().compareTo(LocalDate.now()) >= 0 || Regist.getValue().compareTo(LocalDate.now()) > 0 || Expiry.getValue().compareTo(Regist.getValue()) <= 0)
                        {
                            alert.setTitle("Error");
                            alert.setHeaderText("Please enter valid Dates ");
                            alert.show();
                        }
                        else {
                            ////////////////////////////////////////////////////////////////////////////////////////////////////
                            if (!FN.equals(firstName.getText())) {
                                try {
                                    Statement statement = connection.createStatement();
                                    int resultSet = statement.executeUpdate("UPDATE clients SET client_fname='" + firstName.getText() + "'  WHERE  client_fname='" + FN + "'");
                                    System.out.println(resultSet);


                                } catch (Exception ex) {
                                    System.out.println(ex.toString());
                                }
                                FN = firstName.getText();

                            }
                            if (!ln.equals(lastName.getText())) {
                                try {
                                    Statement statement = connection.createStatement();
                                    int resultSet = statement.executeUpdate("UPDATE clients SET client_lname='" + lastName.getText() + "'  WHERE  client_lname='" + ln + "'");
                                    System.out.println(resultSet);


                                } catch (Exception ex) {
                                    System.out.println(ex.toString());
                                }
                                ln = lastName.getText();

                            }
                            if (!BD.equals(BirthDatePic.getValue().toString())) {
                                try {
                                    Statement statement = connection.createStatement();
                                    int resultSet = statement.executeUpdate("UPDATE clients SET Bdate='" + BirthDatePic.getValue().toString() + "'  WHERE  bdate='" + BD + "' and client_id =" + ids.getText() + "'");
                                    System.out.println(resultSet);


                                } catch (Exception ex) {
                                    System.out.println(ex.toString());
                                }
                                BD = BirthDatePic.getValue().toString();

                            }
                            if (!rg.equals(Regist.getValue().toString())) {
                                try {
                                    Statement statement = connection.createStatement();
                                    int resultSet = statement.executeUpdate("UPDATE subscription SET reg_date = '" + Regist.getValue().toString() + "'  WHERE reg_date ='" + rg + "' AND client_id = '" + ids.getText() + "'");
                                    System.out.println(resultSet);


                                } catch (Exception ex) {
                                    System.out.println(ex.toString());
                                }
                                rg = Regist.getValue().toString();

                            }
                            if (!ex.equals(Expiry.getValue().toString())) {
                                try {
                                    Statement statement = connection.createStatement();
                                    int resultSet = statement.executeUpdate("UPDATE subscription SET expiry_date = '" + Expiry.getValue().toString() + "'  WHERE expiry_date ='" + ex + "' AND client_id = '" + ids.getText() + "'");
                                    System.out.println(resultSet);


                                } catch (Exception ex) {
                                    System.out.println(ex.toString());
                                }
                                ex = Expiry.getValue().toString();

                            }
                            if (f != Fitness.isSelected()) {
                                if (Fitness.isSelected()) {
                                    try {
                                        Statement statement = connection.createStatement();
                                        int resultSet = statement.executeUpdate("insert into subscription (`Client_ID`, `ServiceType`, `reg_date`, `expiry_date`) values " +
                                                "('" + ids.getText() + "','fitness','" + Regist.getValue().toString() + "','" + Expiry.getValue().toString() + "');");
                                        System.out.println(resultSet);
                                        f = true;

                                    } catch (Exception ex) {
                                        System.out.println(ex.toString());
                                    }
                                } else {
                                    try {
                                        Statement statement = connection.createStatement();
                                        int resultSet = statement.executeUpdate("delete from subscription where client_id= '" + ids.getText() + "' and servicetype='fitness';");
                                        System.out.println(resultSet);
                                        f = false;

                                    } catch (Exception ex) {
                                        System.out.println(ex.toString());
                                    }

                                }
                            }
                            if (g != GYMNASTIC.isSelected()) {
                                if (GYMNASTIC.isSelected()) {
                                    try {
                                        Statement statement = connection.createStatement();
                                        int resultSet = statement.executeUpdate("insert into subscription (`Client_ID`, `ServiceType`, `reg_date`, `expiry_date`) values " +
                                                "('" + ids.getText() + "','gymnastics','" + Regist.getValue().toString() + "','" + Expiry.getValue().toString() + "');");
                                        System.out.println(resultSet);
                                        g = true;

                                    } catch (Exception ex) {
                                        System.out.println(ex.toString());
                                    }
                                } else {
                                    try {
                                        Statement statement = connection.createStatement();
                                        int resultSet = statement.executeUpdate("delete from subscription where client_id= '" + ids.getText() + "' and servicetype='gymnastics';");
                                        System.out.println(resultSet);
                                        g = false;

                                    } catch (Exception ex) {
                                        System.out.println(ex.toString());
                                    }

                                }
                            }
                            if (m != MMA.isSelected()) {
                                if (MMA.isSelected()) {
                                    try {
                                        Statement statement = connection.createStatement();
                                        int resultSet = statement.executeUpdate("insert into subscription (`Client_ID`, `ServiceType`, `reg_date`, `expiry_date`) values " +
                                                "('" + ids.getText() + "','mma','" + Regist.getValue().toString() + "','" + Expiry.getValue().toString() + "');");
                                        System.out.println(resultSet);
                                        m = true;

                                    } catch (Exception ex) {
                                        System.out.println(ex.toString());
                                    }
                                } else {
                                    try {
                                        Statement statement = connection.createStatement();
                                        int resultSet = statement.executeUpdate("delete from subscription where client_id= '" + ids.getText() + "' and servicetype='mma';");
                                        System.out.println(resultSet);
                                        m = false;

                                    } catch (Exception ex) {
                                        System.out.println(ex.toString());
                                    }

                                }
                            }
                            if (mo != moujthai.isSelected()) {
                                if (moujthai.isSelected()) {
                                    try {
                                        Statement statement = connection.createStatement();
                                        int resultSet = statement.executeUpdate("insert into subscription (`Client_ID`, `ServiceType`, `reg_date`, `expiry_date`) values " +
                                                "('" + ids.getText() + "','moujthai','" + Regist.getValue().toString() + "','" + Expiry.getValue().toString() + "');");
                                        System.out.println(resultSet);
                                        mo = true;

                                    } catch (Exception ex) {
                                        System.out.println(ex.toString());
                                    }
                                } else {
                                    try {
                                        Statement statement = connection.createStatement();
                                        int resultSet = statement.executeUpdate("delete from subscription where client_id= '" + ids.getText() + "' and servicetype='moujthai';");
                                        System.out.println(resultSet);
                                        mo = false;

                                    } catch (Exception ex) {
                                        System.out.println(ex.toString());
                                    }

                                }
                            }
                            if (z != zumba.isSelected()) {
                                if (zumba.isSelected()) {
                                    try {
                                        Statement statement = connection.createStatement();
                                        int resultSet = statement.executeUpdate("insert into subscription (`Client_ID`, `ServiceType`, `reg_date`, `expiry_date`) values " +
                                                "('" + ids.getText() + "','zumba','" + Regist.getValue().toString() + "','" + Expiry.getValue().toString() + "');");
                                        System.out.println(resultSet);
                                        z = true;

                                    } catch (Exception ex) {
                                        System.out.println(ex.toString());
                                    }
                                } else {
                                    try {
                                        Statement statement = connection.createStatement();
                                        int resultSet = statement.executeUpdate("delete from subscription where client_id= '" + ids.getText() + "' and servicetype='zumba';");
                                        System.out.println(resultSet);
                                        z = false;

                                    } catch (Exception ex) {
                                        System.out.println(ex.toString());
                                    }

                                }
                            }
                            if (yo != yoga.isSelected()) {
                                if (yoga.isSelected()) {
                                    try {
                                        Statement statement = connection.createStatement();
                                        int resultSet = statement.executeUpdate("insert into subscription (`Client_ID`, `ServiceType`, `reg_date`, `expiry_date`) values " +
                                                "('" + ids.getText() + "','yoga','" + Regist.getValue().toString() + "','" + Expiry.getValue().toString() + "');");
                                        System.out.println(resultSet);
                                        yo = true;

                                    } catch (Exception ex) {
                                        System.out.println(ex.toString());
                                    }
                                } else {
                                    try {
                                        Statement statement = connection.createStatement();
                                        int resultSet = statement.executeUpdate("delete from subscription where client_id= '" + ids.getText() + "' and servicetype='yoga';");
                                        System.out.println(resultSet);
                                        yo = false;

                                    } catch (Exception ex) {
                                        System.out.println(ex.toString());
                                    }

                                }
                            }

                            if (!mb.equals(mobileNb.getText())) {
                                try {
                                    Statement statement = connection.createStatement();
                                    int resultSet = statement.executeUpdate("UPDATE clients SET phone='" + mobileNb.getText() + "'  WHERE  phone='" + mb + "'");
                                    System.out.println(resultSet);


                                } catch (Exception ex) {
                                    System.out.println(ex.toString());
                                }
                                mb = mobileNb.getText();

                            }
                            if (!emb.equals(emgMbnb.getText())) {
                                try {
                                    Statement statement = connection.createStatement();
                                    int resultSet = statement.executeUpdate("UPDATE clients SET emergency_contact='" + emgMbnb.getText() + "'  WHERE  phone='" + mb + "'");
                                    System.out.println(resultSet);


                                } catch (Exception ex) {
                                    System.out.println(ex.toString());
                                }
                                emb = emgMbnb.getText();

                            }
                            int costt = 0;

                            if (Fitness.isSelected())
                                try {
                                    Statement statement = connection.createStatement();
                                    ResultSet resultSet = statement.executeQuery("select servicefees from service where servicetype ='fitness'");
                                    if (resultSet.next()) {
                                        costt += resultSet.getInt(1);
                                    }
                                } catch (Exception ex) {
                                    System.out.println(ex.toString());
                                }
                            if (GYMNASTIC.isSelected()) {
                                try {
                                    Statement statement = connection.createStatement();
                                    ResultSet resultSet = statement.executeQuery("select servicefees from service where servicetype ='gymnastics'");
                                    if (resultSet.next()) {
                                        costt += resultSet.getInt(1);
                                    }
                                } catch (Exception ex) {
                                    System.out.println(ex.toString());
                                }
                            }
                            if (yoga.isSelected()) {
                                try {
                                    Statement statement = connection.createStatement();
                                    ResultSet resultSet = statement.executeQuery("select servicefees from service where servicetype ='yoga'");
                                    if (resultSet.next()) {
                                        costt += resultSet.getInt(1);
                                    }
                                } catch (Exception ex) {
                                    System.out.println(ex.toString());
                                }

                            }
                            if (MMA.isSelected()) {
                                try {
                                    Statement statement = connection.createStatement();
                                    ResultSet resultSet = statement.executeQuery("select servicefees from service where servicetype ='mma'");
                                    if (resultSet.next()) {
                                        costt += resultSet.getInt(1);
                                    }
                                } catch (Exception ex) {
                                    System.out.println(ex.toString());
                                }
                            }
                            if (moujthai.isSelected()) {
                                try {
                                    Statement statement = connection.createStatement();
                                    ResultSet resultSet = statement.executeQuery("select servicefees from service where servicetype ='moujthai'");
                                    if (resultSet.next()) {
                                        costt += resultSet.getInt(1);
                                    }
                                } catch (Exception ex) {
                                    System.out.println(ex.toString());
                                }
                            }
                            if (zumba.isSelected()) {
                                try {
                                    Statement statement = connection.createStatement();
                                    ResultSet resultSet = statement.executeQuery("select servicefees from service where servicetype ='zumba'");
                                    if (resultSet.next()) {
                                        costt += resultSet.getInt(1);
                                    }
                                } catch (Exception ex) {
                                    System.out.println(ex.toString());
                                }
                            }
                            if (!type.getValue().equals("Regular"))
                                costt *= 0.7;
                            TCost = costt;
                            cost.setText(String.valueOf(TCost));

                            try {
                                Statement checkP = connection.createStatement();
                                ResultSet checkResult = checkP.executeQuery("selecT count(*) from payments where amount='" + TCost + "' and client_id='" + ids.getText() + "' and date ='" + LocalDate.now() + "'");
                                if (checkResult.next()) {
                                    if (checkResult.getInt(1) > 0) {
                                        alert.setTitle("Error");
                                        alert.setHeaderText("Payment already Set");
                                        alert.show();
                                    } else {
                                        try {
                                            Statement statement1 = connection.createStatement();
                                            int resultSet1 = statement1.executeUpdate("INSERT INTO `gym`.`payments` (`Client_id`, `amount`, `date`) values ('" +
                                                    ids.getText() + "','" + TCost + "','" + LocalDate.now().toString() + "')");


                                            System.out.println(resultSet1);
                                        } catch (Exception ex) {
                                            System.out.println(ex.toString());
                                        }
                                    }
                                }
                            } catch (Exception c) {
                                System.out.println(c.toString());
                            }
                        }
                    });


                   });

                    });
check.setOnAction(ec->
{ int costt =0;

    if(Fitness.isSelected())
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select servicefees from service where servicetype ='fitness'");
            if (resultSet.next()) {
                costt+=resultSet.getInt(1);
             }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
     if (GYMNASTIC.isSelected()) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select servicefees from service where servicetype ='gymnastics'");
            if (resultSet.next()) {
                costt+=resultSet.getInt(1);
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
     if (yoga.isSelected()) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select servicefees from service where servicetype ='yoga'");
            if (resultSet.next()) {
                costt+=resultSet.getInt(1);
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

    }
     if (MMA.isSelected()) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select servicefees from service where servicetype ='mma'");
            if (resultSet.next()) {
                costt+=resultSet.getInt(1);
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
     if (moujthai.isSelected()) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select servicefees from service where servicetype ='moujthai'");
            if (resultSet.next()) {
                costt+=resultSet.getInt(1);
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
     if (zumba.isSelected()) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select servicefees from service where servicetype ='zumba'");
            if (resultSet.next()) {
                costt+=resultSet.getInt(1);
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
    if(!type.getValue().equals("Regular") )
        costt *= 0.7;
int n= Expiry.getValue().getMonth().getValue()- Regist.getValue().getMonth().getValue() ;
   costt=costt*n;
   TCost = costt;
    cost.setText(String.valueOf(costt));


});

 Add.setOnAction(a->
 {
     ids.setDisable(true);
     try {
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery("select Max(client_id) from clients");
         if (resultSet.next()) {
             System.out.println(resultSet.getString(1));
             ids.setText(String.valueOf(resultSet.getInt(1)+1));
         }
     } catch (Exception ex) {
         System.out.println(ex.toString());
     }
 save.setOnAction(s->
 {
     if(firstName.getText().isEmpty() || lastName.getText().isEmpty()|| mobileNb.getText().isEmpty() || emgMbnb.getText().isEmpty()||
             BirthDatePic.getValue() == null || Regist.getValue() == null || Expiry.getValue() == null || type.getValue().isEmpty() || genderList.getValue().isEmpty()  )
     {
         warn.setHeaderText("All field are required.");
         warn.setTitle("Warning");
         warn.show();
     }
     else if(!Fitness.isSelected() && !GYMNASTIC.isSelected() && !MMA.isSelected()
             && !yoga.isSelected() && !zumba.isSelected() && !moujthai.isSelected())
     {
         warn.setTitle("Warning");
         warn.setHeaderText("No Access type is chosen ");
         warn.show();
     }
     else if(!isValid(mobileNb.getText()) || !isValid(emgMbnb.getText()))
     {
         alert.setTitle("Error");
         alert.setHeaderText("Mobile number is not valid ");
         alert.show();
     }
     else if(BirthDatePic.getValue().compareTo(LocalDate.now()) >= 0 || Regist.getValue().compareTo(LocalDate.now()) > 0 || Expiry.getValue().compareTo(Regist.getValue()) <= 0)
     {
         alert.setTitle("Error");
         alert.setHeaderText("Please enter valid Dates ");
         alert.show();
     }
     else{
          rg = Regist.getValue().toString();
          ex = Expiry.getValue().toString();
          emb = emgMbnb.getText();
          mb = mobileNb.getText();
         try{
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("select * from clients where client_fname = '" + firstName.getText() + "' and client_lname='" + lastName.getText() + "' and " +
                     "age= '" + age.getText() + "' and gender= '" + genderList.getValue().toString()
                     + "';");
         if(!resultSet.next()){
/////////////////////////////////////////////////////////////////////
             try {
                 Statement statement3 = connection.createStatement();
                 int resultSet3 = statement3.executeUpdate("INSERT INTO clients (`Client_FName`, `Client_LName`, `Phone`, `emergency_contact`, `Age`, `Bdate`, `gender`,`Type`) values ('" +
                         firstName.getText() + "','" + lastName.getText() + "','" + mb + "','" + emb + "','" + age.getText() + "','" + BirthDatePic.getValue() + "','" + genderList.getValue() + "','" + type.getValue() + "')");

                 if (resultSet3 == 1) {
                     System.out.println(resultSet3);
                     add_client = true;
                     if (Fitness.isSelected()) {
                         try {
                             Statement statement1 = connection.createStatement();
                             int resultSet2 = statement1.executeUpdate("INSERT INTO `gym`.`subscription` (`Client_id`, `servicetype`, `reg_date`, `expiry_date`) values ('" +
                                     ids.getText() + "','fitness','" + rg + "','" + ex + "')");


                             System.out.println(resultSet2);
                         } catch (Exception ex) {
                             System.out.println(ex.toString());
                         }
                     }
                     if (GYMNASTIC.isSelected()) {
                         try {
                             Statement statement1 = connection.createStatement();
                             int resultSet1 = statement1.executeUpdate("INSERT INTO `gym`.`subscription` (`Client_id`, `servicetype`, `reg_date`, `expiry_date`) values ('" +
                                     ids.getText() + "','gymnastics','" + rg + "','" + ex + "')");


                             System.out.println(resultSet1);
                         } catch (Exception ex) {
                             System.out.println(ex.toString());
                         }

                     }
                     if (MMA.isSelected()) {
                         try {
                             Statement statement1 = connection.createStatement();
                             int resultSet1 = statement1.executeUpdate("INSERT INTO `gym`.`subscription` (`Client_id`, `servicetype`, `reg_date`, `expiry_date`) values ('" +
                                     ids.getText() + "','MMA','" + rg + "','" + ex + "')");


                             System.out.println(resultSet1);
                         } catch (Exception ex) {
                             System.out.println(ex.toString());
                         }

                     }
                     if (yoga.isSelected()) {
                         try {
                             Statement statement1 = connection.createStatement();
                             int resultSet1 = statement1.executeUpdate("INSERT INTO `gym`.`subscription` (`Client_id`, `servicetype`, `reg_date`, `expiry_date`) values ('" +
                                     ids.getText() + "','yoga','" + rg + "','" + ex + "')");


                             System.out.println(resultSet1);
                         } catch (Exception ex) {
                             System.out.println(ex.toString());
                         }

                     }
                     if (zumba.isSelected()) {
                         try {
                             Statement statement1 = connection.createStatement();
                             int resultSet1 = statement1.executeUpdate("INSERT INTO `gym`.`subscription` (`Client_id`, `servicetype`, `reg_date`, `expiry_date`) values ('" +
                                     ids.getText() + "','zumba','" + rg + "','" + ex + "')");


                             System.out.println(resultSet1);
                         } catch (Exception ex) {
                             System.out.println(ex.toString());
                         }

                     }
                     if (moujthai.isSelected()) {
                         try {
                             Statement statement1 = connection.createStatement();
                             int resultSet1 = statement1.executeUpdate("INSERT INTO `gym`.`subscription` (`Client_id`, `servicetype`, `reg_date`, `expiry_date`) values ('" +
                                     ids.getText() + "','moujthai','" + rg + "','" + ex + "')");


                             System.out.println(resultSet1);
                         } catch (Exception ex) {
                             System.out.println(ex.toString());
                         }

                     }
                     /////////////////////////////////////////////////////////////
                     int costt =0;

                     if(Fitness.isSelected())
                         try {
                             Statement statement2 = connection.createStatement();
                             ResultSet resultSet2 = statement2.executeQuery("select servicefees from service where servicetype ='fitness'");
                             if (resultSet2.next()) {
                                 costt+=resultSet2.getInt(1);
                             }
                         } catch (Exception ex) {
                             System.out.println(ex.toString());
                         }
                     if (GYMNASTIC.isSelected()) {
                         try {
                             Statement statement2 = connection.createStatement();
                             ResultSet resultSet2 = statement2.executeQuery("select servicefees from service where servicetype ='gymnastics'");
                             if (resultSet2.next()) {
                                 costt+=resultSet2.getInt(1);
                             }
                         } catch (Exception ex) {
                             System.out.println(ex.toString());
                         }
                     }
                     if (yoga.isSelected()) {
                         try {
                             Statement statement2 = connection.createStatement();
                             ResultSet resultSet2 = statement2.executeQuery("select servicefees from service where servicetype ='yoga'");
                             if (resultSet2.next()) {
                                 costt+=resultSet2.getInt(1);
                             }
                         } catch (Exception ex) {
                             System.out.println(ex.toString());
                         }

                     }
                     if (MMA.isSelected()) {
                         try {
                             Statement statement2 = connection.createStatement();
                             ResultSet resultSet2 = statement2.executeQuery("select servicefees from service where servicetype ='mma'");
                             if (resultSet2.next()) {
                                 costt+=resultSet2.getInt(1);
                             }
                         } catch (Exception ex) {
                             System.out.println(ex.toString());
                         }
                     }
                     if (moujthai.isSelected()) {
                         try {
                             Statement statement2 = connection.createStatement();
                             ResultSet resultSet2 = statement2.executeQuery("select servicefees from service where servicetype ='moujthai'");
                             if (resultSet2.next()) {
                                 costt+=resultSet2.getInt(1);
                             }
                         } catch (Exception ex) {
                             System.out.println(ex.toString());
                         }
                     }
                     if (zumba.isSelected()) {
                         try {
                             Statement statement2 = connection.createStatement();
                             ResultSet resultSet2 = statement2.executeQuery("select servicefees from service where servicetype ='zumba'");
                             if (resultSet2.next()) {
                                 costt+=resultSet2.getInt(1);
                             }
                         } catch (Exception ex) {
                             System.out.println(ex.toString());
                         }
                     }
                     if(!type.getValue().equals("Regular") )
                         costt *= 0.7;
                     TCost = costt;
                     cost.setText(String.valueOf(costt));
                     try{
                         Statement checkP = connection.createStatement();
                         ResultSet checkResult = checkP.executeQuery("selecT count(seqNo) from payments where amount='"+TCost+"' and client_id='"+ ids.getText()+"' and date ='"+LocalDate.now()+"'");
                         if(checkResult.next())
                             if(checkResult.getInt(1)>0){
                                 alert.setTitle("Error");
                                 alert.setHeaderText("Payment already Set");
                                 alert.show();
                             }
                             else
                             {
                                 try {
                                     Statement statement1 = connection.createStatement();
                                     int resultSet1 = statement1.executeUpdate("INSERT INTO payments (`Client_id`, `amount`, `date`) values ('" +
                                             ids.getText()+"','"+ TCost+"','" + LocalDate.now().toString() +"')" );


                                     System.out.println(resultSet1);
                                 } catch (Exception ex) {
                                     System.out.println(ex.toString());
                                 }
                             }
                     }catch (Exception c){
                         System.out.println(c.toString());

                     }

                     //////////////////////////////////////////////////////////////
                 }
                 /////////////////////
                 System.out.println(resultSet);
             } catch (Exception ex) {
                 System.out.println(ex.toString());
             }



//////////////////////////////////////////////////////////////////////

         }
         else {
             System.out.println(resultSet.getString(1));
             warn.setTitle("Warning");
             warn.setHeaderText("" +
                     "client Aready Exists ");
             warn.show();
              }
         }catch(Exception c){
             System.out.println(e.toString());
         }

     }

 });



 });
 delete.setOnAction(d->
 {
     firstName.setText("");
     lastName.setText("");
     mobileNb.setText("");
     emgMbnb.setText("");
     BirthDatePic.setValue(null);
     Expiry.setValue(null);
     type.setValue(null);
     genderList.setValue(null);
    age.setText(null);
    Fitness.setSelected(false);
    yoga.setSelected(false);
    MMA.setSelected(false);
    GYMNASTIC.setSelected(false);
    moujthai.setSelected(false);
    zumba.setSelected(false);

 });




               CD.getChildren().addAll(cancelBtn, delete);
               CD.setAlignment(Pos.CENTER);
               CD.setSpacing(20);
               CD.setPadding(new Insets(40,40,30,30));
               BtnEnd.setSpacing(30);
               BtnEnd.getChildren().addAll( CD,save);
               BtnEnd.setAlignment(Pos.BOTTOM_RIGHT);
               // Label error = new Label("hello");
               // try1.setSpacing(30);
               try1.getChildren().addAll(check );
               try1.setAlignment(Pos.CENTER);

               logoPane.getChildren().addAll(logoV,try1,BtnEnd);
               logoPane.setSpacing(130);




               //pane3.setBackground(new Background(new BackgroundFill(Color.WHITE,new CornerRadii(0),new Insets(20,20,20,20))));
               pane3.setSpacing(40);
               pane3.setPadding(new Insets(20,20,20,20));
               pane3.getChildren().addAll(idPer, ESR, logoPane);
               Scene scene3 = new Scene(pane3, 800, 600);


               Stage CM = new Stage();
               CM.setScene(scene3);
               CM.setTitle("Clients Managment");
               CM.show();
               cancelBtn.setOnAction(ec->
               {
                        CM.close();
               });


           });

           PaymentsBtn.setOnAction(p->
           {
               Stage pay = new Stage();

             Button btnAdd = new Button("ADD");
             Button btnEdit = new Button("EDIT");
             Button btnDelete = new Button("DELETE");
            Button btnSort = new Button();

   HBox paym = new HBox();
   VBox btnsVB = new VBox();
               BorderPane hr = new BorderPane();

               try {

                   Statement statement = connection.createStatement();
                   ResultSet resultSet = statement.executeQuery("SELECT  seqno, client_fname, client_lname, amount , date  \n" +
                           "FROM payments JOIN clients USING (client_id)\n");
                   ObservableList<Map<String, Object>> items = FXCollections.<Map<String, Object>>observableArrayList();
                   TableView tableView = new TableView();
                   while (resultSet.next()) {

                       Map<String, Object> row = new HashMap<>();


                       row.put("seqNo", resultSet.getString(1));
                       row.put("client_id", resultSet.getString(2));
                       row.put("lastName", resultSet.getString(3));
                       row.put("amount", resultSet.getDouble(4));
                       row.put("date", resultSet.getDate(5));
                       items.addAll(row);
                   }
                       TableColumn<Map, String> firstNameColumn = new TableColumn<>("First Name");
                       firstNameColumn.setCellValueFactory(new MapValueFactory<>("client_id"));

                       TableColumn<Map, String> lastNameColumn = new TableColumn<>("Last Name");
                       lastNameColumn.setCellValueFactory(new MapValueFactory<>("lastName"));

                       TableColumn<Map, String> AmountColumn = new TableColumn<>("Amount");
                       AmountColumn.setCellValueFactory(new MapValueFactory<>("amount"));


                       TableColumn<Map, String> dateColumn = new TableColumn<>("Date");
                       dateColumn.setCellValueFactory(new MapValueFactory<>("date"));


                       TableColumn<Map, String> seqColumn = new TableColumn<>("SeqNO");
                       seqColumn.setCellValueFactory(new MapValueFactory<>("seqNo"));


                       tableView.getColumns().addAll(seqColumn,firstNameColumn, lastNameColumn,AmountColumn,dateColumn);



                       tableView.getItems().addAll(items);




                       btnsVB.setSpacing(20);
                   ObservableList<String> CId = FXCollections.observableArrayList();


                       btnDelete.setPrefSize(80,40);
                       btnAdd.setPrefSize(80,40);
                      Label edit = new Label("EDIT", btnEdit);
                      Label sort = new Label("SORT", btnSort);
                       btnEdit.setPrefSize(80,40);
                       btnSort.setPrefSize(80,40);
                       btnsVB.setPadding(new Insets(50,50,50,50));
                       //hr.setCenter(btnAdd);
                       //hr.setTop(btnDelete);
                      // hr.setBottom(btnEdit);

                       btnsVB.getChildren().addAll(btnAdd, btnDelete,btnEdit,btnSort);
                       paym.getChildren().addAll(tableView,btnsVB);



                     btnAdd.setOnAction(a->
                     {


                         HBox adH = new HBox();


                         try {

                             Statement statement1 = connection.createStatement();
                             ResultSet resultSet1 = statement1.executeQuery("select * from clients");
                            // ObservableList<String> CId = FXCollections.observableArrayList();
                             while (resultSet1.next()) {

                                 CId.add(resultSet1.getString(1));

                             }

                             ChoiceBox <String>  clients= new ChoiceBox<String>(CId);
                             Label cl = new Label("Client id",clients);
                             TextField amount = new TextField();
                             Label am = new Label("Amount", amount);
                             Button s = new Button("ADD");
                             adH.setAlignment(Pos.CENTER);
                             adH.setSpacing(20);
                             Scene ad = new Scene(adH, 450,160);
                             Stage add= new Stage();
                             add.setScene(ad);
                             add.setTitle("Add");
                             add.show();
                            s.setOnAction(r->
                            {
                                boolean t = true;
                                if(clients.getValue()!= null && !amount.getText().isEmpty()){
                                    for(int i=0;i< amount.getText().length();i++){
                                        if(!Character.isDigit(amount.getText().charAt(i))){
                                            Alert alert = new Alert(Alert.AlertType.ERROR);
                                            alert.setHeaderText(" Ilegal Amount");
                                            alert.setTitle("Error");
                                            t= false;
                                            alert.show();
                                            break;
                                        }


                                    }
                                    if(t){
                                        try {

                                            Statement statement2 = connection.createStatement();
                                            int resultSet2 = statement2.executeUpdate("INSERT INTO `gym`.`payments` (`client_id`, `amount`, `date`) VALUES ('"+ clients.getValue()+"','"+amount.getText()+"','"
                                                    + LocalDate.now()+"')" );
                                            if(resultSet2 ==1){
                                                add.close();
                                            }
                                        }catch (Exception te){
                                            te.toString();
                                        }

                                    }

                                }
                            });


/*
in the payments stage
ad edit delete button
add button done
set error messge for mayments as an information not error

 */
                             adH.getChildren().addAll(cl,am, s);
                         }catch (Exception e){
                             System.out.println(e.toString());
                         }




                     });


                     btnDelete.setOnAction(d->
                     {
                         Stage del = new Stage();
                         HBox adH = new HBox();


                         try {

                             Statement statement1 = connection.createStatement();
                             ResultSet resultSet1 = statement1.executeQuery("select * from Payments");
                             // ObservableList<String> CId = FXCollections.observableArrayList();
                             while (resultSet1.next()) {

                                 CId.add(resultSet1.getString(1));

                             }
                         }catch (Exception e){
                             System.out.println(e.toString());
                         }

                             ChoiceBox <String>  clients= new ChoiceBox<String>(CId);
                             Label cl = new Label("SeqNO",clients);



                             Button s = new Button("Confirm");
                             s.setOnAction(S->{
                                 try {

                                     Statement statement2 = connection.createStatement();
                                     int resultSet2 = statement2.executeUpdate("delete from payments where seqno = "+ clients.getValue() );
                                     if(resultSet2 ==1){
                                         del.close();
                                     }
                                     else{
                                         Alert alert = new Alert(Alert.AlertType.ERROR);
                                         alert.setTitle("ERROR");
                                         alert.setHeaderText("No such payment ");
                                     }
                                 }catch (Exception te){
                                     te.toString();
                                 }
                             });
                           cl.setContentDisplay(ContentDisplay.BOTTOM);
                             adH.getChildren().addAll(cl, s);
                             adH.setAlignment(Pos.CENTER);
                             adH.setSpacing(20);
                             Scene ad = new Scene(adH, 300,100);

                             del.setScene(ad);
                             del.setTitle("DELETE");
                             del.show();
                     });

                     btnEdit.setOnAction(e->
                     { VBox Gen = new VBox();
                         Stage editStg = new Stage();
                         HBox edOp = new HBox();
                         Button paybtn= new Button("Edit Payment");
                         Button costBtn = new Button("Edit Services");
                       edOp.getChildren().addAll(paybtn,costBtn);
                       edOp.setSpacing(40);
                       paybtn.setPrefSize(100,50);
                       costBtn.setPrefSize(100,50);
                       edOp.setAlignment(Pos.CENTER);
                       Gen.getChildren().addAll(edOp);
                       Gen.setAlignment(Pos.CENTER);
                       Gen.setSpacing(40);
                         Scene ed1 = new Scene(Gen, 400,300);
                       paybtn.setOnAction(v->
                       {    paybtn.setDisable(true);
                           TextField seq = new TextField();
                           Label seqLab = new Label("seqNo" , seq);

                           Gen.getChildren().add(seqLab);
                           seqLab.setContentDisplay(ContentDisplay.BOTTOM);
                           HBox paymentStg = new HBox();
                           VBox paymentStgV = new VBox();
                           Button con = new Button("CONFIRM");
                            paymentStgV.getChildren().addAll(paymentStg,con);
                            paymentStgV.setSpacing(20);
                            paymentStgV.setAlignment(Pos.CENTER);
                           Stage editPay = new Stage();
                           Scene panee = new Scene(paymentStgV,700,150);
                           editPay.setScene(panee);
                          ed1.setOnKeyPressed(pa->
                          {
                              if(pa.getCode().toString().equals("ENTER")){
                                  System.out.println(pa.getCode().toString());
                                  try{
                                      Statement statement1 = connection.createStatement();
                                      ResultSet resultSet11 = statement1.executeQuery("SELECT * FROM payments NATURAL JOIN clients WHERE seqno = " + seq.getText());
                                      if(resultSet11.next()){
                                          TextField Fname= new TextField(resultSet11.getString(5));
                                          Fname.setEditable(false);
                                          Label FName = new Label("First Name", Fname);
                                          FName.setContentDisplay(ContentDisplay.BOTTOM);

                                          TextField Lname = new TextField(resultSet11.getString(6));
                                          Lname.setEditable(false);
                                          Label LName = new Label("Last Name", Lname);
                                          LName.setContentDisplay(ContentDisplay.BOTTOM);

                                          TextField Amount = new TextField(resultSet11.getString(3));
                                          Label amount = new Label("Amount", Amount);
                                          amount.setContentDisplay(ContentDisplay.BOTTOM);

                                          TextField Date = new TextField(resultSet11.getString(4));
                                          Label date = new Label("Date", Date);
                                          Date.setEditable(false);
                                          date.setContentDisplay(ContentDisplay.BOTTOM);

                                          con.setOnAction(c->
                                          {
                                              try{
                                                  Statement s = connection.createStatement();
                                                  int result = s.executeUpdate("Update payments set amount = " + Amount.getText()+" where seqno = "+ seq.getText());
                                                  System.out.println(result);
                                                  if(result ==1){
                                                      editPay.close();
                                                  }
                                              }catch (Exception r){
                                                  System.out.println(r.toString());
                                              }
                                          });





                                          paymentStg.getChildren().addAll(FName, LName,amount,date);
                                          paymentStg.setAlignment(Pos.CENTER);
                                          paymentStg.setSpacing(20);


                                          editPay.show();



                                      }
                                      else{
                                          Alert warnn = new Alert(Alert.AlertType.ERROR);
                                          warnn.setTitle("ERROR");
                                          warnn.setHeaderText("Payment Not Found");
                                          warnn.show();
                                      }
                                  }catch (Exception exceptionx){
                                      System.out.println(exceptionx.toString());
                                  }
                              }
                          });
});


                         costBtn.setOnAction(c->
                         {
                             Pattern pattern = Pattern.compile("[0-9]*");

// Create text formatter with pattern to restrict input to numeric values
                             TextFormatter<String> formatter = new TextFormatter<>(change -> {
                                 String newText = change.getControlNewText();
                                 if (pattern.matcher(newText).matches()) {
                                     return change;
                                 } else {
                                     return null;
                                 }
                             });



                             TextFormatter<String> formatter2= new TextFormatter<>(change -> {
                                 String newText = change.getControlNewText();
                                 if (pattern.matcher(newText).matches()) {
                                     return change;
                                 } else {
                                     return null;
                                 }
                             });


                             editStg.close();
                             Stage serviceStageEdit = new Stage();
                             VBox GeneralPane = new VBox();
                             Button Add = new Button("ADD");
                             Button Delete = new Button("DELETE");
                             Button Edit = new Button("EDIT");
                             HBox OptionBtn = new HBox();
                             OptionBtn.getChildren().addAll(Add, Delete,Edit);
                             OptionBtn.setAlignment(Pos.CENTER);
                             OptionBtn.setSpacing(20);
                             GeneralPane.setAlignment(Pos.CENTER);
                             GeneralPane.setSpacing(20);
                             GeneralPane.getChildren().addAll(OptionBtn);
                             Scene scene1 = new Scene(GeneralPane,400,200);
                             serviceStageEdit.setScene(scene1);
                             serviceStageEdit.show();

                             HBox ServiceEmp = new HBox();
                             VBox GenServiceEmp = new VBox();

                             TextField ServiceName= new TextField();
                             Label ServiceNameLab = new Label("Service Name", ServiceName);
                             ServiceNameLab.setContentDisplay(ContentDisplay.BOTTOM);
                             TextField ServiceFees = new TextField();
                             ServiceFees.setTextFormatter(formatter);
                             Label ServiceFeesLab = new Label("Servie Fees", ServiceFees);
                             Button confirmAdd = new Button("CONFIRM");
                             ServiceFeesLab.setContentDisplay(ContentDisplay.BOTTOM);
                             ServiceEmp.getChildren().addAll(ServiceNameLab,ServiceFeesLab);
                             ServiceEmp.setSpacing(20);
                             ServiceEmp.setAlignment(Pos.CENTER);
                             GenServiceEmp.getChildren().addAll(ServiceEmp, confirmAdd);
                             GenServiceEmp.setSpacing(20);
                             GenServiceEmp.setAlignment(Pos.CENTER);


                             ChoiceBox <String> ServicesAv = new ChoiceBox<>();
                             Button ConfirmDel = new Button("Remove");
                             HBox hello = new HBox();

                             hello.getChildren().addAll(ServicesAv, ConfirmDel);
                             hello.setSpacing(20);
                             hello.setPadding(new Insets(30,30,30,30));
                             hello.setAlignment(Pos.CENTER);


                             ChoiceBox <String> ServicesAv2 = new ChoiceBox<>();
                             Label ServicesAv2Lab = new Label("Services", ServicesAv2);
                             ServicesAv2Lab.setContentDisplay(ContentDisplay.BOTTOM);
                             TextField fee = new TextField();
                             Label feeLab = new Label("Servies Fees", fee);
                             feeLab.setContentDisplay(ContentDisplay.BOTTOM);
                             Button ConfirmEd = new Button("Confirm Edit");
                             HBox ServEdit = new HBox();
                             VBox GenServEdit = new VBox();

                             ServEdit.getChildren().addAll(ServicesAv2Lab, feeLab);
                             ServEdit.setAlignment(Pos.CENTER);
                             ServEdit.setSpacing(20);
                             GenServEdit.getChildren().addAll(ServEdit,ConfirmEd);
                             GenServEdit.setAlignment(Pos.CENTER);
                             GenServEdit.setSpacing(20);

                        // addddd

                          Add.setOnAction(a->{
                              GeneralPane.getChildren().remove(hello);
                              GeneralPane.getChildren().add(GenServiceEmp);
                              GeneralPane.getChildren().remove(GenServEdit);

                              confirmAdd.setOnAction(ca->
                              {
                                  if(ServiceFees.getText().isEmpty() || ServiceName.getText().isEmpty()){
                                      Alert alert = new Alert(Alert.AlertType.ERROR);
                                      alert.setTitle("ERROR");
                                      alert.setHeaderText("not All fields are Completed");
                                      alert.show();
                                  }
                                  else {
                                      try {
                                          Statement statement1 = connection.createStatement();
                                          int result = statement1.executeUpdate("insert into service (`servicetype`,`servicefees`) values" +
                                                  "('" + ServiceName.getText() + "','" + ServiceFees.getText() + "')");
                                          if (result == 1) {
                                              Alert alertt = new Alert(Alert.AlertType.INFORMATION);
                                              alertt.setTitle("Service Emplimentation");
                                              alertt.setHeaderText("Service is add SUCCESSFULLY");
                                              alertt.show();
                                          } else {
                                              Alert alert = new Alert(Alert.AlertType.ERROR);
                                              alert.setTitle("ERROR");
                                              alert.setHeaderText("UNSSUCCESFULL ACTION");
                                              alert.show();
                                          }

                                      } catch (Exception exp) {
                                          Alert alert = new Alert(Alert.AlertType.ERROR);
                                          alert.setTitle("ERROR");
                                          alert.setHeaderText("Service Name can not be duplicated");
                                          alert.show();
                                      }
                                  }
                              });
                              System.out.println("helloo");


                          });


                           // deleteee

                             Delete.setOnAction(d->
                             {
                                 GeneralPane.getChildren().remove(GenServiceEmp);
                                 GeneralPane.getChildren().addAll(hello);
                                 GeneralPane.getChildren().remove(GenServEdit);

                                 ObservableList<String> ServName = FXCollections.observableArrayList();
                                 try {

                                     Statement statement1 = connection.createStatement();
                                     ResultSet resultSet1 = statement1.executeQuery("select * from service");
                                     // ObservableList<String> CId = FXCollections.observableArrayList();
                                     while (resultSet1.next()) {

                                         ServName.add(resultSet1.getString(1));
                                     }
                                 }catch (Exception ex){
                                     System.out.println(ex);
                                 }
                                 ServicesAv.setItems(ServName);

                                 ConfirmDel.setOnAction(cd->
                                 {
                                     if(ServicesAv.getValue().isEmpty())
                                     {
                                         Alert alert = new Alert(Alert.AlertType.ERROR);
                                         alert.setTitle("ERROR");
                                         alert.setHeaderText("No service have been Chosen");
                                         alert.show();
                                     }
                                     else
                                     {
                                         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                         alert.setTitle("Confirmation Dialog");
                                         alert.setHeaderText("Are you sure you want to delete "+ ServicesAv.getValue()+ " service?");
                                         alert.setContentText("This action cannot be undone.");


                                         Optional<ButtonType> result = alert.showAndWait();


                                         if (result.isPresent() && result.get() == ButtonType.OK){
                                             try{
                                                 Statement statement1 = connection.createStatement();
                                                 int result1 = statement1.executeUpdate("delete from service where servicetype= '"+ ServicesAv.getValue()+"'");
                                                 if(result1==1){
                                                     Alert a = new Alert(Alert.AlertType.INFORMATION);
                                                     a.setTitle("Delete");
                                                     a.setHeaderText(" Service Successfully deleted");
                                                     a.show();
                                                 }
                                             }catch (Exception exp){
                                                 System.out.println(exp);
                                             }
                                         }
                                     }
                                 });
                                 System.out.println("bye");

                             });

                             // editttt

                             Edit.setOnAction(t->
                             {
                                 GeneralPane.getChildren().remove(GenServiceEmp);
                                 GeneralPane.getChildren().remove(hello);
                                 GeneralPane.getChildren().add(GenServEdit);

                                fee.setTextFormatter(formatter2);
                                 ObservableList<String> ServName = FXCollections.observableArrayList();
                                 try {

                                     Statement statement1 = connection.createStatement();
                                     ResultSet resultSet1 = statement1.executeQuery("select * from service");
                                     // ObservableList<String> CId = FXCollections.observableArrayList();
                                     while (resultSet1.next()) {

                                         ServName.add(resultSet1.getString(1));
                                     }
                                 }catch (Exception ex){
                                     System.out.println(ex);
                                 }
                                 ServicesAv2.setItems(ServName);

                                 ConfirmEd.setOnAction(ce->
                                 {
                                     if(ServicesAv2.getValue().isEmpty()){
                                         Alert alert = new Alert(Alert.AlertType.ERROR);
                                         alert.setTitle("ERROR");
                                         alert.setHeaderText("No Service is chosen");
                                         alert.show();
                                     }
                                     else{
                                         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                         alert.setTitle("Confirmation Dialog");
                                         alert.setHeaderText("Are you sure you want to change the service fee of  "+ ServicesAv2.getValue()+ " to " + fee.getText());



                                         Optional<ButtonType> result = alert.showAndWait();


                                         if (result.isPresent() && result.get() == ButtonType.OK){
                                             try{
                                                 Statement statement1 = connection.createStatement();
                                                 int result1 = statement1.executeUpdate("update service set serviceFees = '"+ fee.getText()+" '  where servicetype= '"+ ServicesAv2.getValue()+" '");
                                                 if(result1==1){
                                                     Alert a = new Alert(Alert.AlertType.INFORMATION);
                                                     a.setTitle("EDIT");
                                                     a.setHeaderText(" Service Successfully EDITED");
                                                     a.show();
                                                 }
                                             }catch (Exception exp){
                                                 System.out.println(exp);
                                             }
                                         }









                                     }
                                 });

                                 System.out.println("hellllo");

                             });

                         });

                         editStg.setScene(ed1);
                         editStg.show();


                     });



                     btnSort.setOnAction(s->
                     {
                         Stage IncomeStage = new Stage();
                         VBox GeneralIn = new VBox();
                         HBox in = new HBox();
                         TextField monthText = new TextField();
                         monthText.setPrefSize(40,25);
                         Label monthLab = new Label("Month", monthText);
                         monthLab.setContentDisplay(ContentDisplay.RIGHT);
                         TextField dayText = new TextField();
                         dayText.setPrefSize(40,25);
                         Label dayLab= new Label("Day", dayText);
                         dayLab.setContentDisplay(ContentDisplay.RIGHT);
                         TextField yearText = new TextField();
                         yearText.setPrefSize(40,25);
                         Label yearLab = new Label("Year", yearText);
                         yearLab.setContentDisplay(ContentDisplay.RIGHT);
                         Label income = new Label("");
                         Button checkBtn = new Button("Check\nincome");
                         checkBtn.setContentDisplay(ContentDisplay.BOTTOM);

                         checkBtn.setOnAction(d->
                         {

                         });



                         in.getChildren().addAll(dayLab, monthLab, yearLab,checkBtn);
                         in.setSpacing(20);
                         in.setAlignment(Pos.CENTER);
                         GeneralIn.getChildren().addAll(in,income);
                         GeneralIn.setSpacing(20);
                         GeneralIn.setAlignment(Pos.CENTER);

                         Scene scene1 = new Scene(GeneralIn,400,200);
                         IncomeStage.setScene(scene1);
                         IncomeStage.show();
                     });



                       Scene table = new Scene(paym, 600,400);
                       pay.setScene(table);
                       pay.setTitle("Pyaments Managment");
                       pay.show();

               }catch (Exception e){
                   System.out.println(e.toString());
               }






           });



        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private void handle(ActionEvent e) {

    }
    public boolean isValid(String n){
    int i;
    String s;
        if(true){
        System.out.println("Enter phone number:");

        if( n.length() != 8)
        {
            System.out.println("Must be 9 digits ");
            return false;
        }

        switch (n.substring(0, 2)) {
            case "71": case "76": case"81": case"03": case"78": case"70":
                break;
            default:
                System.out.println("please enter a valid regional code!!");
                return false;
        }

        s= n.substring(2);
        for(i=0 ; i< s.length();i++)
        {
            if(!(s.charAt(i)>='0' && s.charAt(i)<='9')) {
                System.out.println("invalid phone number!!");
                return false;
            }

        }

    }

   return true; }
}
