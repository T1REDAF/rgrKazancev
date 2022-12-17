package org.t1redaf;

import java.util.ArrayList;
import java.util.List;

public class Calc {
    private double RV;
    private List<Double> procents = new ArrayList<>();
    private List<Double> ostatok = new ArrayList<>();
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
        return Double.toString(RV);
    }
    public String getProc(){
        return Double.toString(sum-popCount);
    }
    public String getPop(){
        return Integer.toString(popCount);
    }
    public void ras(){
        r/=100;
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
            //if(i==0) bac=0;
            procents.add(sum-popCount-bac);
            System.out.println(sum);
            ostatok.add(RV);
            System.out.println(RV);
        }
        //"Остаток вклада: "+ RV+" Начислено процентов: "+(sum-popCount)+" Пополнено (или снято): "+(popCount));
    }

}
