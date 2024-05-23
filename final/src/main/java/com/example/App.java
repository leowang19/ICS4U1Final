package com.example;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.event.ActionEvent; 
import javafx.event.EventHandler; 
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

// import javax.naming.ConfigurationException;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    private TableView table = new TableView();
    //array to store values of csv 
    public String data[][] = new String[1][1];
    //array list for row data 
    public ArrayList<Contact> row = new ArrayList<Contact>();
    public String test = "";

    @Override
    public void start(Stage stage) throws IOException {
        // make stage
        Scene scene = new Scene(new Group());

        // size, name, font
        stage.setTitle("Contacts");
        stage.setWidth(400);
        stage.setHeight(800);
        final Label label = new Label("Contacts");
        label.setFont(new Font("Arial", 20));

        //text input 
        

        //button

        Button add = new Button("add");
        Button edit = new Button("edit");
        Button delete = new Button("delete");
        //click logic 
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                //open 
                TextInputDialog newContact = new TextInputDialog("enter contact information");
                newContact.show();
                Optional<String> result = newContact.showAndWait();
                String input = result.orElse("");
                // System.out.println(input);
            }
        });

        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                //open 
                TextInputDialog deleteContact = new TextInputDialog("enter row you want to delete");
                deleteContact.show();
                Optional<String> result = deleteContact.showAndWait();
                String deleted = result.orElse("");
                
            }
        });

        edit.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                //open 
                TextInputDialog editContactnum = new TextInputDialog("enter row you want to edit");
                editContactnum.show();
                Optional<String> result = editContactnum.showAndWait();
                if(result.orElse("")!=""){
                    TextInputDialog editContact= new TextInputDialog("enter new contact information");
                }
                 
                // String deleted = result.orElse("");
                
            }
        });



        table.setEditable(true);
        // name of columns
        TableColumn firstNameCol = new TableColumn("First Name");
        TableColumn lastNameCol = new TableColumn("Last Name");
        TableColumn emailCol = new TableColumn("Number");
        TableColumn firstEmailCol = new TableColumn("Primary");
        TableColumn secondEmailCol = new TableColumn("Secondary");
        TableColumn postalCode = new TableColumn<>("Postal Code");
        TableColumn email = new TableColumn<>("Email");
        emailCol.getColumns().addAll(firstEmailCol, secondEmailCol);

        table.getColumns().addAll(firstNameCol, lastNameCol, emailCol,postalCode, email);
    
        //where everything is made
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, add, edit, delete);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);


        String line = "";
        String splitBy = ",";
        try {
            // parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader("C:\\test.csv"));
            while ((line = br.readLine()) != null) // returns a Boolean value
            {
                
                // String[] employee = line.split(splitBy); // use comma as separator and stores values in an array
                // Contact person = new Contact(employee[0],employee[1],employee[2],employee[3],employee[4]);
                // row.add(person);

                
                
            }
            
            //use buffered file reader 
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setScene(scene);
        stage.show();

    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
