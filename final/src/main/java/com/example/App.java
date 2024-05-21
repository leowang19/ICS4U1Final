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
import java.util.Optional;

import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.CsvWriter;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    private TableView table = new TableView();
    //array to store values of csv 
    public String data[][] = new String[0][0];

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
        //click logic 
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                //open 
                TextInputDialog newContact = new TextInputDialog("enter contact information");
                newContact.show();
                Optional<String> result = newContact.showAndWait();
                String input = result.orElse("");
                System.out.println(input);
            }
        });

        table.setEditable(true);
        // name of columns
        TableColumn firstNameCol = new TableColumn("First Name");
        TableColumn lastNameCol = new TableColumn("Last Name");
        TableColumn emailCol = new TableColumn("Number");
        TableColumn firstEmailCol = new TableColumn("Primary");
        TableColumn secondEmailCol = new TableColumn("Secondary");
        emailCol.getColumns().addAll(firstEmailCol, secondEmailCol);

        table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);
    
        //where everything is made
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, add);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);


        String line = "";
        String splitBy = ",";
        try {
            // parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader("C:\\test.csv"));
            while ((line = br.readLine()) != null) // returns a Boolean value
            {
                String[] employee = line.split(splitBy); // use comma as separator and stores values in an array
                System.out.println(employee[0]);
                
                
            }
            FileWriter outputfile = new FileWriter("C:\\test.csv");
            CSVWriter writer = new CsvWriter(outputfile);
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
