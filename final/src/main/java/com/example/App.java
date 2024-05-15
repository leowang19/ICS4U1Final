package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    private TableView table = new TableView();
    @Override
    public void start(Stage stage) throws IOException {
        //make stage 
        Scene scene = new Scene(new Group());
        stage.setTitle("Contacts");
        stage.setWidth(400);
        stage.setHeight(800);
        final Label label = new Label("Contacts");
        label.setFont(new Font("Arial", 20));
 
        table.setEditable(true);
 
        TableColumn firstNameCol = new TableColumn("First Name");
        TableColumn lastNameCol = new TableColumn("Last Name");
        TableColumn emailCol = new TableColumn("Number");
        TableColumn firstEmailCol = new TableColumn("Primary");
        TableColumn secondEmailCol = new TableColumn("Secondary");
        emailCol.getColumns().addAll(firstEmailCol, secondEmailCol);
        
        table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);
 
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);
 
        ((Group) scene.getRoot()).getChildren().addAll(vbox);

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