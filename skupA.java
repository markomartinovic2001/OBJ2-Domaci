package com.example.domaci;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class skupA extends Application {
    //kreiranje scene i dodavanje potrebnih elemenata
    @Override
    public void start(Stage stage) throws Exception {
        VBox vb = new VBox();
        HBox hb = new HBox();
        HBox hb1 = new HBox();
        Label label = new Label("Unesite tekst");
        TextField tf = new TextField();
        Button btn = new Button("Posalji");
        btn.setOnAction(new EventHandler<ActionEvent>() {
    //uzimanje vrednosti unetog stringa i provera za space-ove jer je u zadatku naglaseno da se razmaci ne racunaju
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println(tf.getText());
                int i = 0;
                String res = "";
                for(i = 0; i < tf.getText().length(); i++){
                    if(!(Character.toString(tf.getText().charAt(i)).equals(" "))){
                        res += Character.toString(tf.getText().charAt(i));
                    }
                }
                System.out.println(res);
    //povezivanje klijenta sa serverom i koriscenje funkcije toLowerCase(), da bi tretirao sva velika slova kao mala
                try {
                    Socket s = new Socket("localhost", 1234);
                    PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())), true);
                    if(tf.getText().length() > 0){
                        writer.println(res.toLowerCase());
                    }
                    else{
                        return;
                    }
                    BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    Label label = new Label(reader.readLine());
                    hb1.getChildren().add(label);
                    writer.close();
                    s.close();
                } catch (Exception e){
                    throw new RuntimeException(e);
                }
            }
        });
    //postavljanje vidljivosti svih elemenata scene
        hb.getChildren().addAll(label, tf);
        hb1.getChildren().add(btn);
        vb.getChildren().addAll(hb, hb1);
        Scene scena = new Scene(vb, 700, 300);
        stage.setScene(scena);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}