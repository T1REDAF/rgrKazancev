package org.t1redaf;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InfoDevelopers extends Application {
          private Button exitButton;

          public void start(Stage primaryStage) {
            primaryStage.setTitle("Информация о разрабах:");
            primaryStage.setWidth(300);

            Image image = new Image("file:src/main/java/org/t1redaf/developers.png");
            primaryStage.getIcons().add(image);

            Label moder = new Label("Модератор - Якупов");
            Label razrab1 = new Label("Разработчик1 - Абдрахманов");
            Label razrab2 = new Label("Разработчик2 - Кочетова");
            Label razrab3 = new Label("Разработчик3 - Нуртдинов Г.Т.");
            VBox infoBox = new VBox(moder,razrab1,razrab2,razrab3);
            infoBox.setPadding(new Insets(10, 0, 10, 0));
            infoBox.setAlignment(Pos.TOP_LEFT);
            infoBox.setSpacing(20);

            exitButton = new Button("Назад");
            HBox exitButtonsBox = new HBox(exitButton);
            exitButtonsBox.setPadding(new Insets(10, 0, 0, 0));
            exitButtonsBox.setAlignment(Pos.CENTER);

            VBox vBox2 = new VBox(infoBox, exitButtonsBox);
            vBox2.setSpacing(10);
            vBox2.setPadding(new Insets(10, 20, 10, 20));

            Scene sc = new Scene(vBox2);//Что это и все что ниже
            primaryStage.setResizable(false);
            primaryStage.setScene(sc);
            primaryStage.show();

            exitButton.setOnAction( e -> {
              primaryStage.close();
              Stage primaryStageNew = new Stage();
              LoanPayment mainShow = new LoanPayment();
              mainShow.start(primaryStageNew);
            });
        }
}