package org.t1redaf;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.text.NumberFormat;
import java.util.function.UnaryOperator;

public class LoanPayment extends Application {

    private static final Font normalFont = new Font(14);
    private static final double normalWidth = 160;
    private static final double normalSpacing = 10;
    private static final String[] monthActions = {"пополнение","выплата"};

    private TextField principalTextField;
    private TextField yearTextField;
    private TextField interestTextField;
    private TextField monthActionTextField;
    private ChoiceBox<String> monthActionChoiceBox;
    private Button calculateButton;
    private Button resetButton;
    private Button exitButton;
    private Label ostatokLabel;
    private Label procentLabel;
    private Label changeLabel;

    public static void main(String[] args){
        Application.launch(args);
    }
    public void start(Stage primaryStage){
        primaryStage.setTitle("Депозитный калькулятор с капитализацией");
        primaryStage.setWidth(400);

        Image image = new Image("file:src/main/java/org/t1redaf/icon.png");
        primaryStage.getIcons().add(image);

        Label principalLabel = new Label("Первоначальный взнос:");
        principalLabel.setPrefWidth(normalWidth);
        principalLabel.setFont(normalFont);
        principalTextField = new TextField();
        onlyNumberTextField(principalTextField);
        principalLabel.setTextAlignment(TextAlignment.RIGHT);
        principalTextField.setPrefColumnCount(10);
        principalTextField.setPromptText("Principal");
        HBox principalBox = new HBox(principalLabel, principalTextField);
        principalBox.setSpacing(normalSpacing);

        Label yearLabel = new Label("Кол-во месяцов:");
        yearLabel.setPrefWidth(normalWidth);
        yearLabel.setFont(normalFont);
        yearTextField = new TextField();
        onlyNumberTextField(yearTextField);
        yearTextField.setPrefColumnCount(10);
        yearTextField.setPromptText("Month");
        HBox yearBox = new HBox(yearLabel, yearTextField);
        yearBox.setSpacing(normalSpacing);

        Label interestLabel = new Label("Процентная ставка:");
        interestLabel.setPrefWidth(normalWidth);
        interestLabel.setFont(normalFont);
        interestTextField = new TextField();
        onlyNumberTextField(interestTextField);
        interestTextField.setPrefColumnCount(10);
        interestTextField.setPromptText("Interest Rate");
        HBox interestBox = new HBox(interestLabel, interestTextField);
        interestBox.setSpacing(normalSpacing);

        Label monthActionLabel = new Label("Ежемесячно:");
        monthActionLabel.setPrefWidth(100);
        monthActionLabel.setFont(normalFont);
        monthActionTextField = new TextField();
        onlyNumberTextField(monthActionTextField);
        monthActionTextField.setPrefColumnCount(4);
        monthActionChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(monthActions));
        monthActionChoiceBox.setValue(monthActions[0]);
        HBox monthActionBox = new HBox(monthActionLabel, monthActionTextField, monthActionChoiceBox);
        monthActionBox.setSpacing(normalSpacing);

        Label calculationResultsLabel = new Label("Результаты расчета: ");
        HBox monthActionBox2 = new HBox(calculationResultsLabel);
        monthActionBox2.setPadding(new Insets(10,0,0,0));
        monthActionBox2.setAlignment(Pos.CENTER);

        ostatokLabel = new Label("Остаток вклада: ");
        procentLabel = new Label("Начислено процентов: ");
        changeLabel = new Label("Пополнено/снято: ");
        VBox monthActionBox3 = new VBox(ostatokLabel,procentLabel,changeLabel);
        monthActionBox3.setSpacing(10);
        monthActionBox3.setPadding(new Insets(0,0,0,0));
        monthActionBox3.setAlignment(Pos.TOP_LEFT);


        calculateButton = new Button("Посчитать");
        resetButton = new Button("Сбросить");
        exitButton = new Button("Выход");
        HBox buttonsBox = new HBox(calculateButton,resetButton, exitButton);
        buttonsBox.setSpacing(30);
        buttonsBox.setPadding(new Insets(10,0,0,0));
        buttonsBox.setAlignment(Pos.CENTER);

        calculateButton.setOnAction( e -> {
            buttonCalc();
        });

        resetButton.setOnAction( e -> {
            resetCalc();
        });

        exitButton.setOnAction( e -> {
            exitCalc();
        });


        VBox vBox = new VBox( principalBox, yearBox, interestBox, monthActionBox,buttonsBox, monthActionBox2,monthActionBox3);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10, 20, 10, 20));

        Scene sc = new Scene(vBox);

        primaryStage.setResizable(false);
        primaryStage.setScene(sc);
        primaryStage.show();
    }

    public void buttonCalc(){
        try{int popolnenie = Integer.parseInt(monthActionTextField.getText());
            if (monthActionChoiceBox.getValue() == monthActions[1]){
                popolnenie = popolnenie*-1;
            }
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(); //Change amount to currency format
            currencyFormat.setMaximumFractionDigits(2);
            Calc call = new Calc();
            double vznos = Double.parseDouble(principalTextField.getText());
            double procentStav = Double.parseDouble(interestTextField.getText());
            int srokvklad = Integer.parseInt(yearTextField.getText());
            call.setAll(vznos, procentStav, srokvklad,popolnenie);
            call.ras();
            ostatokLabel.setText("Остаток вклада: "+ currencyFormat.format(Double.parseDouble(call.getRV())));
            procentLabel.setText("Начислено процентов: "+ currencyFormat.format(Double.parseDouble(call.getProc())));
            if (monthActionChoiceBox.getValue() == monthActions[1]){
                changeLabel.setText("Cнято: "+  currencyFormat.format(-Double.parseDouble(call.getPop())));
            }else {
                changeLabel.setText("Пополнено: "+  currencyFormat.format(Double.parseDouble(call.getPop())));
            }} catch (NumberFormatException e){}

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

    public void exitCalc(){
        System.exit(0);
    }

    private static void onlyNumberTextField(TextField tf){
        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("([1-9][0-9]*)?")) {
                return change;
            }
            return null;
        };
        tf.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), null, integerFilter));
    }


}
