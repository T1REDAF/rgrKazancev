package org.t1redaf;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Objects;
import java.util.function.UnaryOperator;
import java.awt.image.BufferedImage;

public class LoanPayment extends Application {

    private static final Font NORMAL_FONT = new Font(14);// поля-константы
    private static final double NORMAL_WIDTH = 160;
    private static final double NORMAL_SPACING = 10;
    private static final String[] monthActions = {"пополнение","выплата"};

    private TextField principalTextField;//приватные поля
    private TextField yearTextField;
    private TextField interestTextField;
    private TextField monthActionTextField;
    private ChoiceBox<String> monthActionChoiceBox;
    private Label ostatokLabel;
    private Label procentLabel;
    private Label changeLabel;

    public void launcher(){
        Application.launch();
    }
    //TODO  Суперклассы и подклассы. Переопределение и перегрузка, разобраться с лямба выражениями
    //todo выводить в пдф информация с формы
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Депозитный калькулятор с капитализацией");
        primaryStage.setWidth(400);

        try{
            BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Images/icon.png")));
            WritableImage image2 = SwingFXUtils.toFXImage(image, null);
            primaryStage.getIcons().add(image2);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }

        Button infoButton = new Button("О разработчиках");
        Button generationPdf = new Button("Генерация PDF");
        HBox infoButtonsBox = new HBox(generationPdf, infoButton);
        infoButtonsBox.setPadding(new Insets(5,0,10,0));
        infoButtonsBox.setSpacing(20);
        infoButtonsBox.setAlignment(Pos.CENTER);

        //
        Label principalLabel = new Label("Первоначальный взнос:");
        principalLabel.setPrefWidth(NORMAL_WIDTH);
        principalLabel.setFont(NORMAL_FONT);
        principalTextField = new TextField();
        onlyNumberTextField(principalTextField);
        principalLabel.setTextAlignment(TextAlignment.RIGHT);
        principalTextField.setPrefColumnCount(10);
        principalTextField.setPromptText("Principal");
        HBox principalBox = new HBox(principalLabel, principalTextField);
        principalBox.setSpacing(NORMAL_SPACING);

        Label yearLabel = new Label("Кол-во месяцев:");
        yearLabel.setPrefWidth(NORMAL_WIDTH);
        yearLabel.setFont(NORMAL_FONT);
        yearTextField = new TextField();
        onlyNumberTextField(yearTextField);
        yearTextField.setPrefColumnCount(10);
        yearTextField.setPromptText("Month");
        HBox yearBox = new HBox(yearLabel, yearTextField);
        yearBox.setSpacing(NORMAL_SPACING);

        Label interestLabel = new Label("Процентная ставка:");
        interestLabel.setPrefWidth(NORMAL_WIDTH);
        interestLabel.setFont(NORMAL_FONT);
        interestTextField = new TextField();
        onlyNumberTextField(interestTextField);
        interestTextField.setPrefColumnCount(10);
        interestTextField.setPromptText("Interest Rate");
        HBox interestBox = new HBox(interestLabel, interestTextField);
        interestBox.setSpacing(NORMAL_SPACING);

        Label monthActionLabel = new Label("Ежемесячно:");
        monthActionLabel.setPrefWidth(100);
        monthActionLabel.setFont(NORMAL_FONT);
        monthActionTextField = new TextField();
        onlyNumberWithZeroTextField(monthActionTextField);
        monthActionTextField.setPrefColumnCount(4);
        monthActionChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(monthActions));
        monthActionChoiceBox.setValue(monthActions[0]);
        HBox monthActionBox = new HBox(monthActionLabel, monthActionTextField, monthActionChoiceBox);
        monthActionBox.setSpacing(NORMAL_SPACING);

        Label calculationResultsLabel = new Label("Результаты расчета: ");
        HBox monthActionBox2 = new HBox(calculationResultsLabel);
        monthActionBox2.setPadding(new Insets(10,0,0,0));
        monthActionBox2.setAlignment(Pos.CENTER);

        ostatokLabel = new Label("Остаток вклада: ");
        procentLabel = new Label("Начислено процентов: ");
        changeLabel = new Label("Пополнено/снято: ");
        VBox monthActionBox3 = new VBox(ostatokLabel,procentLabel,changeLabel);
        monthActionBox3.setSpacing(10);
        monthActionBox3.setPadding(new Insets(0,0,10,0));
        monthActionBox3.setAlignment(Pos.TOP_LEFT);

        Button calculateButton = new Button("Посчитать");
        Button resetButton = new Button("Сбросить");
        Button exitButton = new Button("Выход");
        HBox buttonsBox = new HBox(calculateButton, resetButton, exitButton);
        buttonsBox.setSpacing(30);
        buttonsBox.setPadding(new Insets(10,0,0,0));
        buttonsBox.setAlignment(Pos.CENTER);


        infoButton.setOnAction(e -> {
            primaryStage.close();
            Stage primaryStageNew = new InfoDevelopers();
            primaryStageNew.show();
        });

        calculateButton.setOnAction(e -> buttonCalc());

        generationPdf.setOnAction(e -> buttonGenerPDF());

        resetButton.setOnAction(e -> resetCalc());

        exitButton.setOnAction(e -> exitCalc());

        VBox vBox = new VBox(principalBox, yearBox, interestBox, monthActionBox,buttonsBox, monthActionBox2,monthActionBox3, infoButtonsBox);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10, 20, 10, 20));

        Scene sc = new Scene(vBox);

        primaryStage.setResizable(false);
        primaryStage.setScene(sc);
        primaryStage.show();
    }

    private void buttonCalc(){
        try {
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(); //Change amount to currency format
            currencyFormat.setMaximumFractionDigits(2);
            int popolnenie = Integer.parseInt(monthActionTextField.getText());
            double vznos = Double.parseDouble(principalTextField.getText());
            double procentStav = Double.parseDouble(interestTextField.getText());
            int srokvklad = Integer.parseInt(yearTextField.getText());
            String replenishmentOrPayment;

            if (monthActionChoiceBox.getValue().equals(monthActions[1])){
                popolnenie = popolnenie*-1;
                replenishmentOrPayment = "Выплаты (руб)   ";
            }else {
                replenishmentOrPayment = "Пополнения (руб)";
            }
            DepositDTO dto = new DepositDTO(vznos,procentStav,popolnenie,srokvklad,replenishmentOrPayment);
            Calc call = new Calc(dto);
            //call.setAll(vznos, procentStav, srokvklad,popolnenie,replenishmentOrPayment);
            call.ras();
            ostatokLabel.setText("Остаток вклада: "+ currencyFormat.format(call.getRV()));
            procentLabel.setText("Начислено процентов: "+ currencyFormat.format(call.getProc()));
            if (monthActionChoiceBox.getValue().equals(monthActions[1])){
                if (Integer.parseInt(monthActionTextField.getText()) == 0){
                    changeLabel.setText("Cнято: 0");
                }else {
                    changeLabel.setText("Cнято: "+  currencyFormat.format(-call.getPop()));}
            }else {
                changeLabel.setText("Пополнено: "+  currencyFormat.format(call.getPop()));
            }
        }catch(NumberFormatException ignored){}
    }

    private void buttonGenerPDF(){
        try {
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(); //Change amount to currency format
            currencyFormat.setMaximumFractionDigits(2);
            int popolnenie = Integer.parseInt(monthActionTextField.getText());
            double vznos = Double.parseDouble(principalTextField.getText());
            double procentStav = Double.parseDouble(interestTextField.getText());
            int srokvklad = Integer.parseInt(yearTextField.getText());
            String replenishmentOrPayment;

            if (monthActionChoiceBox.getValue().equals(monthActions[1])){
                popolnenie = popolnenie*-1;
                replenishmentOrPayment = "Выплаты (руб)   ";
            }else {
                replenishmentOrPayment = "Пополнения (руб)";
            }
            DepositDTO dto = new DepositDTO(vznos,procentStav,popolnenie,srokvklad,replenishmentOrPayment);
            Calc call = new Calc(dto);
            call.generationPdf();
        }catch(NumberFormatException ignored){}
    }
    public void resetCalc(){
        principalTextField.setText("");
        interestTextField.setText("");
        yearTextField.setText("");
        monthActionTextField.setText("");
        ostatokLabel.setText("Остаток вклада: ");
        procentLabel.setText("Начислено процентов: ");
        changeLabel.setText("Пополнено/снято: ");
    }

    private void exitCalc(){
        System.exit(0);
    }

    private static void onlyNumberTextField(TextField tf){
        if (tf==null) return;
        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("([1-9][0-9]*)?")) {
                return change;
            }
            return null;
        };
        tf.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), null, integerFilter));
    }

    private static void onlyNumberWithZeroTextField(TextField tf){
        if (tf==null) return;
        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("([0-9])*")) {
                return change;
            }
            return null;
        };
        tf.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), null, integerFilter));
    }
}
