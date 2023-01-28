package com.example.domaci;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class skupB extends Application {

    int i = 0;
    @Override
    public void start(Stage stage) {
        VBox box = new VBox();

        for (int i = 0; i < 3; i++) {
            HBox box1 = new HBox();

            box1.getChildren().addAll(new TextField(), new Label("X"), new TextField(), new Label("Y"), new TextField(), new Label("Z="), new TextField());
            box.getChildren().add(box1);
        }
        Button btn = new Button("SOLVE");
        box.getChildren().add(btn);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Socket s = new Socket("localhost",1234);
                    PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())), true);
                    StringBuilder sendToSrerver= new StringBuilder();
                    StringBuilder TempSolver= new StringBuilder();
                    for (Node elems : box.getChildren()) {
                        if( elems instanceof  HBox){
                            for (Node node : ((HBox) elems).getChildren()) {
                                if (node instanceof TextField) {
                                    i++;
                                    if (i % 4 == 0){
                                        TempSolver.append(((TextField) node).getText()).append(" ");

                                    }else{
                                        sendToSrerver.append(" ").append(((TextField) node).getText());
                                    }
                                }
                            }
                        }
                    }

                    writer.println(sendToSrerver.append("=").append(TempSolver));
                    BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    String REALRESULT = reader.readLine();
                    System.out.println(REALRESULT);
                    box.getChildren().add(new Label(REALRESULT));
                    writer.close();
                    s.close();
                } catch (Exception e) {
                    System.out.println("ERROR");
                    e.printStackTrace();
                    return;
                }
            }
        });
        Scene scene = new Scene(box,700,200);
        stage.setScene(scene);
        stage.show();

    }
}
