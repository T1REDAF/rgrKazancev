package org.t1redaf;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private Button infoButton;
    private String replenishmentOrPayment;

    public static void main(String[] args){
        Application.launch(args);
    }
    //TODO картинки в Images final class, модификаторы доступа protected, Суперклассы и подклассы. Переопределение и перегрузка., Обработка примитивных типов как объектных (обертки), Отношения между классами (UML)Файл, Создание объектов с помощью конструкторов.Файл, разобраться с лямба выражениями
    //todo выводить в пдф информация с формы
    public void start(Stage primaryStage){
        primaryStage.setTitle("Депозитный калькулятор с капитализацией");
        primaryStage.setWidth(400);

        Image image = new Image("file:src/main/resources/Images/icon.png");
        primaryStage.getIcons().add(image);

        infoButton = new Button("О разработчиках");
        HBox infoButtonsBox = new HBox(infoButton);
        infoButtonsBox.setPadding(new Insets(10,0,10,0));
        infoButtonsBox.setAlignment(Pos.CENTER);

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
        onlyNumberWithZeroTextField(monthActionTextField);
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
        monthActionBox3.setPadding(new Insets(0,0,10,0));
        monthActionBox3.setAlignment(Pos.TOP_LEFT);

        calculateButton = new Button("Посчитать");
        resetButton = new Button("Сбросить");
        exitButton = new Button("Выход");
        HBox buttonsBox = new HBox(calculateButton,resetButton, exitButton);
        buttonsBox.setSpacing(30);
        buttonsBox.setPadding(new Insets(10,0,0,0));
        buttonsBox.setAlignment(Pos.CENTER);

        //TODO Спросить у Динара что тут нах происходит
        infoButton.setOnAction( e -> {
            primaryStage.close();
            Stage primaryStageNew = new InfoDevelopers();
            primaryStageNew.show();
        });

        calculateButton.setOnAction( e -> {
            buttonCalc();
        });

        resetButton.setOnAction( e -> {
            resetCalc();
        });

        exitButton.setOnAction( e -> {
            exitCalc();
        });

        VBox vBox = new VBox(infoButtonsBox, principalBox, yearBox, interestBox, monthActionBox,buttonsBox, monthActionBox2,monthActionBox3);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10, 20, 10, 20));

        Scene sc = new Scene(vBox);

        primaryStage.setResizable(false);
        primaryStage.setScene(sc);
        primaryStage.show();
    }

    public void buttonCalc(){
        try {
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(); //Change amount to currency format
            currencyFormat.setMaximumFractionDigits(2);
            int popolnenie = Integer.parseInt(monthActionTextField.getText());
            double vznos = Double.parseDouble(principalTextField.getText());
            double procentStav = Double.parseDouble(interestTextField.getText());
            int srokvklad = Integer.parseInt(yearTextField.getText());

            if (monthActionChoiceBox.getValue().equals(monthActions[1])){
                popolnenie = popolnenie*-1;
                replenishmentOrPayment = "Выплаты (руб)   ";
            }else {replenishmentOrPayment = "Пополнения (руб)";}

            Calc call = new Calc();
            call.setAll(vznos, procentStav, srokvklad,popolnenie,replenishmentOrPayment);
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
        }catch(NumberFormatException e){}
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
        if (tf==null) return;
        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("([1-9][0-9]*)?")) {
                return change;
            }
            return null;
        };
        tf.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), null, integerFilter));
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
        tf.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), null, integerFilter));
    }
}
