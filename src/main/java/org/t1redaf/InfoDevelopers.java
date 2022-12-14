package org.t1redaf;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class InfoDevelopers extends Stage {

  public InfoDevelopers() {
            setTitle("Информация о разрабах:");
            setWidth(300);

            try{
              BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Images/developers.png")));
              WritableImage image2 = SwingFXUtils.toFXImage(image, null);
              getIcons().add(image2);
            }catch (IOException e) {
              throw new RuntimeException(e);
            }

            Label moder = new Label("Модератор - Якупов Д.Р.");
            Label razrab1Label = new Label("Разработчик 1 - Абдрахманов А.Т.");
            Label razrab2Label = new Label("Разработчик 2 - Кочетова А.А.");
            Label razrab3Label = new Label("Разработчик 3 - Нуртдинов Г.Т.");
            VBox infoBox = new VBox(moder,razrab1Label,razrab2Label,razrab3Label);
            infoBox.setPadding(new Insets(10, 0, 10, 0));
            infoBox.setAlignment(Pos.TOP_LEFT);
            infoBox.setSpacing(20);

            Button exitButton = new Button("Назад");
            HBox exitButtonsBox = new HBox(exitButton);
            exitButtonsBox.setPadding(new Insets(10, 0, 0, 0));
            exitButtonsBox.setAlignment(Pos.CENTER);

            VBox vBox2 = new VBox(infoBox, exitButtonsBox);
            vBox2.setSpacing(10);
            vBox2.setPadding(new Insets(10, 20, 10, 20));

            Scene sc = new Scene(vBox2);
            setResizable(false);
            setScene(sc);
            show();

            exitButton.setOnAction(e -> {
              close();
              Stage primaryStageNew = new Stage();
              LoanPayment mainShow = new LoanPayment();
              mainShow.start(primaryStageNew);
            });
        }
}
