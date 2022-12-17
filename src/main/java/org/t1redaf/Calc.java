package org.t1redaf;

public class Calc {
    private double RV;
    private double r;
    private int n;
    private int pop;
    int popCount = 0;
    double sum = 0;

    public void setAll(double vznos, double procentStav, int srokvklad, int popolnenie){
        RV = vznos;
        r = procentStav;
        n = srokvklad;
        pop = popolnenie;
    }
    public String getRV(){
        String RVString = Double.toString(RV);
        return RVString;
    }
    public String getProc(){
        String proc = Double.toString(sum-popCount);
        return proc;
    }
    public String getPop(){
        String popString = Integer.toString(popCount);
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
        //"Остаток вклада: "+ RV+" Начислено процентов: "+(sum-popCount)+" Пополнено (или снято): "+(popCount));
    }

}
