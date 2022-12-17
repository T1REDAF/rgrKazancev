package org.t1redaf;

import java.util.ArrayList;
import java.util.List;

public class Calc {
    private double RV; // остаток вклада
    private List<Double> procents = new ArrayList<>();
    private List<Double> ostatok = new ArrayList<>();
    private double r; // процентная ставка
    private int n; // число месяцев
    private int pop; // ежемесячный взнос
    int popCount = 0; // накопление pop
    double sum = 0; // для вычисления процентов

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
        //в зависимости от снятия или пополнения значение pop будет отрицательным или положительным
        for (int i = 0; i<n; i++){
            popCount += pop;
            bac = RV;
            RV = (RV * month)+pop;
            sum = sum + RV - bac;
            procents.add(RV-bac-pop);
            ostatok.add(RV);
        }
        //"Остаток вклада: "+ RV+" Начислено процентов: "+(sum-popCount)+" Пополнено (или снято): "+(popCount));
        PdfCreator.create(procents,ostatok);
    }

}
