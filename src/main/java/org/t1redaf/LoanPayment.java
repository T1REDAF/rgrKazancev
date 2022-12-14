package org.t1redaf;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class LoanPayment extends Application{

    private static final Font normalFont = new Font(13);
    private static final double normalWidth = 160;
    private static final double normalSpacing = 10;
    private static final String[] monthActions = {"пополнение","выплата"};

    private TextField principalTextField;
    private TextField yearTextField;
    private TextField interestTextField;
    private TextField monthlyPaymentTextField;
    private TextField totalAmountPayableTextField;
    private TextField monthActionTextField;
    private ChoiceBox<String> monthActionChoiceBox;
    private Button calculateButton;
    private Label errorLabel;
    private ToggleGroup toggleGroup;
    private RadioButton creditType1;
    private RadioButton creditType2;

    public static void main(String[] args){
        Application.launch(args);
    }
    public void start(Stage primaryStage){
        primaryStage.setTitle("Депозитный калькулятор с капитализацией");
        primaryStage.setWidth(320);

        Label principalLabel = new Label("Первоначальный взнос:");
        principalLabel.setPrefWidth(normalWidth);
        principalLabel.setFont(normalFont);
        principalTextField = new TextField();
        principalLabel.setTextAlignment(TextAlignment.RIGHT);
        principalTextField.setPrefColumnCount(10);
        principalTextField.setPromptText("Principal");
        HBox principalBox = new HBox(principalLabel, principalTextField);
        principalBox.setSpacing(normalSpacing);

        Label yearLabel = new Label("Кол-во месяцов:");
        yearLabel.setPrefWidth(normalWidth);
        yearLabel.setFont(normalFont);
        yearTextField = new TextField();
        yearTextField.setPrefColumnCount(10);
        yearTextField.setPromptText("Month");
        HBox yearBox = new HBox(yearLabel, yearTextField);
        yearBox.setSpacing(normalSpacing);

        Label interestLabel = new Label("Процентная ставка:");
        interestLabel.setPrefWidth(normalWidth);
        interestLabel.setFont(normalFont);
        interestTextField = new TextField();
        interestTextField.setPrefColumnCount(10);
        interestTextField.setPromptText("Interest Rate");
        HBox interestBox = new HBox(interestLabel, interestTextField);
        interestBox.setSpacing(normalSpacing);

        Label monthActionLabel = new Label("Ежемесячно:");
        monthActionLabel.setPrefWidth(100);
        monthActionLabel.setFont(normalFont);
        monthActionTextField = new TextField();
        monthActionTextField.setPrefColumnCount(4);
        monthActionChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(monthActions));
        monthActionChoiceBox.setValue(monthActions[0]);
        HBox monthActionBox = new HBox(monthActionLabel, monthActionTextField, monthActionChoiceBox);
        monthActionBox.setSpacing(normalSpacing);

        calculateButton = new Button("Посчитать");
        HBox buttonBox = new HBox(calculateButton);
        buttonBox.setPadding(new Insets(20,0,0,0));
        buttonBox.setAlignment(Pos.CENTER);

        calculateButton.setOnAction( e -> {
            buttonCalc();
        });

        errorLabel = new Label();
        errorLabel.setStyle("-fx-background-color: red;-fx-text-fill:white;-fx-font-weight: bold;");
        HBox errorLabelBox = new HBox(errorLabel);
        errorLabelBox.setAlignment(Pos.CENTER);

        VBox vBox = new VBox( principalBox, yearBox, interestBox, monthActionBox, buttonBox, errorLabelBox);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10, 20, 10, 20));

        Scene sc = new Scene(vBox);

        primaryStage.setResizable(false);
        primaryStage.setScene(sc);
        primaryStage.show();

    }

    public void buttonCalc(){
        NumberFormat currencyFormat =
                NumberFormat.getCurrencyInstance(); //Change amount to currency format

        if(!isAcceptedNumber(principalTextField) || !isAcceptedNumber(yearTextField) || !isAcceptedNumber(interestTextField)){
            resetOutput();
            return;
        }
        double principal = Double.parseDouble(principalTextField.getText());
        double months = Double.parseDouble(yearTextField.getText());
        double rate = Double.parseDouble(interestTextField.getText());

        if((principal <= 0 )|| (months <= 0 )|| (rate <= 0 )){
            resetOutput();
            return;
        }
        double monthlyPayment = 0;
        List<Double> monthlyPayments;
        if (creditType1.isSelected()) {
            monthlyPayment = calcAnnMonthlyPayment(principal, months, rate);
            monthlyPaymentTextField.setDisable(false);
            monthlyPaymentTextField.setText(currencyFormat.format(monthlyPayment));
            totalAmountPayableTextField.setDisable(false);
            totalAmountPayableTextField.setText(currencyFormat.format(monthlyPayment * months));
        }
        else {
            monthlyPayments = calcDeffMonthlyPayment(principal, months, rate);
            totalAmountPayableTextField.setDisable(false);
            monthlyPaymentTextField.setText("");
            monthlyPaymentTextField.setDisable(true);
            double fullCredit = monthlyPayments.stream().reduce(0d,(a,b) -> a + b);
            totalAmountPayableTextField.setText(currencyFormat.format(fullCredit));
            PdfCreator.create(monthlyPayments);
        }

        errorLabel.setText("");

    }
    private boolean isAcceptedNumber(TextField userInput){
        try{
            Double.parseDouble(userInput.getText());
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }
    private double calcAnnMonthlyPayment(double principal, double months, double rate){

        double monthRate = (rate/12) / 100.0;

        return (monthRate * principal)/(1 - Math.pow((1 + monthRate), -months));
    }

    private List<Double> calcDeffMonthlyPayment(double principal, double months, double rate){
        List<Double> payments = new ArrayList<>();
        double mainDebtPayment = principal/months;
        double ratePayment;
        for (int i = 0; i < months; i++){
            ratePayment = (principal - (mainDebtPayment * i)) * rate / 100 / 12;
            payments.add(mainDebtPayment + ratePayment);
        }
        return payments;
    }
    private void resetOutput(){
        errorLabel.setText("Invalid Input!");
        monthlyPaymentTextField.setText("");//If there is a previous content, clear that.
        totalAmountPayableTextField.setText("");
        monthlyPaymentTextField.setDisable(true);
        totalAmountPayableTextField.setDisable(true);
    }

}
