package org.t1redaf;

public class Calc {
    public static void ras(double RV, double r, int n, int pop){
        double sum = 0;//начислено процентов
        double month = 1 + (r / 12);//процентная ставка
        double bac;//переменная для сохранения предыдущего значения вклада для вычитания
        pop = -500;//пополнение или снятие
        int popCount = 0;//пополнение или снятие за весь период
        RV = 100000;
        // Math.pow((1+r/12),n);
        //в зависимости от снятия или пополнения значение pop будет отрицательным или положительным
        for (int i = 0; i<n; i++){
            popCount += pop;
            bac = RV;
            RV = (RV * month)+pop;
            sum = sum + RV - bac;
        }
        System.out.println("Остаток вклада: "+ RV+" Начислено процентов: "+(sum-popCount)+" Пополнено (или снято): "+(popCount));
    }
}
