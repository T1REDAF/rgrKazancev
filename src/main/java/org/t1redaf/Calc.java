package org.t1redaf;

public class Calc {
    private double RV;
    private double r;
    private int n;
    private int pop;
    int popCount = 0;
    double sum = 0;
    double otvet1;
    double otvet2;
    int otvet3;

    public void setAll(double a, double b, int c, int d){
        RV = a;
        r = b;
        n = c;
        pop = d;
    }
    public String getRV(){
        String RVString = Double.toString(otvet1);
        return RVString;
    }
    public String getProc(){
        String proc = Double.toString(otvet2);
        return proc;
    }
    public String getPop(){
        String popString = Integer.toString(otvet3);
        return popString;
    }
    public void ras(){
        double month = 1 + (r / 12);//процентная ставка
        double bac;//переменная для сохранения предыдущего значения вклада для вычитания

        //RV = 100000;
        // Math.pow((1+r/12),n);
        //в зависимости от снятия или пополнения значение pop будет отрицательным или положительным
        for (int i = 0; i<n; i++){
            popCount += pop;
            bac = RV;
            RV = (RV * month)+pop;
            sum = sum + RV - bac;
        }
        otvet1=RV;
        otvet2=sum-popCount;
        otvet3=popCount;
        //System.out.println("Остаток вклада: "+ RV+" Начислено процентов: "+(sum-popCount)+" Пополнено (или снято): "+(popCount));
    }

}
