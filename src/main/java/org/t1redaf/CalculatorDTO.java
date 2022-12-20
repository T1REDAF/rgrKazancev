package org.t1redaf;

public abstract class CalculatorDTO {
    private double vznos;
    private double procentStav;

    public CalculatorDTO() {
    }

    protected CalculatorDTO(double vznos, double procentStav) {
        this.vznos = vznos;
        this.procentStav = procentStav;
    }

    public double getVznos() {
        return vznos;
    }

    public void setVznos(double vznos) {
        this.vznos = vznos;
    }

    public double getProcentStav() {
        return procentStav;
    }

    public void setProcentStav(double procentStav) {
        this.procentStav = procentStav;
    }
}
