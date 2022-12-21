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
    private int popCount = 0; // накопление pop
    private double sum = 0; // для вычисления процентов
    private String status;
    private DepositDTO dto;

    public Calc(DepositDTO dto) {
        this.RV = dto.getVznos();
        this.r = dto.getProcentStav();
        this.n = dto.getSrokvklad();
        this.pop = dto.getPopolnenie();
        this.status = dto.getReplenishmentOrPayment();
        this.dto = dto;
    }

    public Calc() {
    }
    public void setRV(double RV) {
        this.RV = RV;
    }

    public double getRV(){
        return RV;
    }

    public double getProc(){
        return sum-popCount;
    }

    public int getPop(){
        return popCount;
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
        //"Остаток вклада: "+ RV+" Начислено процентов: "+(sum-popCount)+" Пополнено (или снято): "+(popCount))
    }
    public void generationPdf() {
        ras();
        PdfCreator.create(procents,ostatok,pop, status,dto);
    }
}
