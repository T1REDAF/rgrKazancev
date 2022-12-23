package org.t1redaf;

public abstract class CalculatorDTO {
    private final double vznos;
    private final double procentStav;

    @Override
    public String toString() {
        return "Первоначальный взнос = " + vznos + " руб." +
                "\nПроцентная ставка = " + procentStav + "%";
    }

    protected CalculatorDTO(double vznos, double procentStav) {
        this.vznos = vznos;
        this.procentStav = procentStav;
    }

    public double getVznos() {
        return vznos;
    }

    public double getProcentStav() {
        return procentStav;
    }

}
