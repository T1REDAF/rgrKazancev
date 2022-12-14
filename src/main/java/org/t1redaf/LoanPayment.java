package org.t1redaf;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;

public class LoanPayment extends Application{

    private TextField principalTextField;
    private TextField yearTextField;
    private TextField interestTextField;
    private TextField monthlyPaymentTextField;
    private TextField totalAmountPayableTextField;
    private Button calculateButton;
    private Button resetButton;
    private Button exitButton;
    private Label errorLabel;
    private ToggleGroup toggleGroup;
    private RadioButton creditType1;
    private RadioButton creditType2;

    public static void main(String[] args){
        Application.launch(args);
    }
    public void start(Stage primaryStage){
        Text instruction = new Text("Калькулятор пользовательских кредитов");
        instruction.setFont(new Font(20));
        HBox topBox = new HBox(instruction);
        topBox.setPadding(new Insets(20, 10, 10, 20));

        //Image image = new Image("src/main/java/org.t1redaf/icon.png");
        //primaryStage.getIcons().add(new Image("src/main/java/org.t1redaf/icon.png"));

        Label creditTypeLabel = new Label("Тип кредита:");
        creditTypeLabel.setPrefWidth(100);
        toggleGroup = new ToggleGroup();
        creditType1 = new RadioButton("Аннуитетный");
        creditType2 = new RadioButton("Дифференцированный");
        creditType1.setToggleGroup(toggleGroup);
        creditType2.setToggleGroup(toggleGroup);
        toggleGroup.selectToggle(creditType1);
        HBox radioButtonBox = new HBox(creditTypeLabel,creditType1,creditType2);
        radioButtonBox.setSpacing(20);

        Label principalLabel = new Label("Сумма кредита:");
        principalLabel.setPrefWidth(120);
        principalTextField = new TextField();
        principalLabel.setTextAlignment(TextAlignment.RIGHT);
        principalTextField.setPrefColumnCount(10);
        principalTextField.setPromptText("Principal");
        HBox principalBox = new HBox(principalLabel, principalTextField);

        Label yearLabel = new Label("Месяца:");
        yearLabel.setPrefWidth(120);
        yearTextField = new TextField();
        yearTextField.setPrefColumnCount(10);
        yearTextField.setPromptText("Year");
        HBox yearBox = new HBox(yearLabel, yearTextField);

        Label interestLabel = new Label("Процентная ставка:");
        interestLabel.setPrefWidth(120);
        interestTextField = new TextField();
        interestTextField.setPrefColumnCount(8);
        interestTextField.setPromptText("Interest Rate");
        HBox interestBox = new HBox(interestLabel, interestTextField);

        Label monthlyPaymentLabel = new Label("Ежемесячная выплата:");
        monthlyPaymentLabel.setPrefWidth(120);
        monthlyPaymentTextField = new TextField();
        monthlyPaymentTextField.setPrefColumnCount(8);
        monthlyPaymentTextField.setEditable(false);
        monthlyPaymentTextField.setDisable(true);
        HBox monthlyPaymentBox = new HBox(monthlyPaymentLabel, monthlyPaymentTextField);
        monthlyPaymentTextField.setStyle("-fx-text-fill:green;-fx-font-weight: bold;");

        Label totalAmountPayableLabel = new Label("Полная сумма кредита");
        totalAmountPayableLabel.setPrefWidth(120);
        totalAmountPayableTextField = new TextField();
        totalAmountPayableTextField.setPrefColumnCount(8);
        totalAmountPayableTextField.setEditable(false);
        totalAmountPayableTextField.setDisable(true);
        HBox totalAmountPayableBox = new HBox(totalAmountPayableLabel, totalAmountPayableTextField);
        totalAmountPayableTextField.setStyle("-fx-text-fill:green;-fx-font-weight: bold;");


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

        errorLabel = new Label();
        errorLabel.setStyle("-fx-background-color: red;-fx-text-fill:white;-fx-font-weight: bold;");
        HBox errorLabelBox = new HBox(errorLabel);
        errorLabelBox.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(topBox, principalBox, radioButtonBox, yearBox, interestBox, monthlyPaymentBox,totalAmountPayableBox, buttonsBox, errorLabelBox);
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

    public void resetCalc(){
        principalTextField.setText("");
        interestTextField.setText("");
        yearTextField.setText("");
    }

    public void exitCalc(){
        System.exit(0);
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
