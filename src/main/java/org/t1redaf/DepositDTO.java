package org.t1redaf;

public final class DepositDTO extends CalculatorDTO {

    private int popolnenie;
    private int srokvklad;
    private String replenishmentOrPayment;

    @Override
    public String toString() {
        return super.toString() + "\nЕжемесячно = " + popolnenie +
                "\nСрок вклада = " + srokvklad ;
    }

    public DepositDTO() {
        super();
    }

    public DepositDTO(double vznos, double procentStav, int popolnenie, int srokvklad, String replenishmentOrPayment) {
        super(vznos, procentStav);
        this.popolnenie = popolnenie;
        this.srokvklad = srokvklad;
        this.replenishmentOrPayment = replenishmentOrPayment;
    }

    public int getPopolnenie() {
        return popolnenie;
    }

    public void setPopolnenie(int popolnenie) {
        this.popolnenie = popolnenie;
    }

    public int getSrokvklad() {
        return srokvklad;
    }

    public void setSrokvklad(int srokvklad) {
        this.srokvklad = srokvklad;
    }

    public String getReplenishmentOrPayment() {
        return replenishmentOrPayment;
    }

    public void setReplenishmentOrPayment(String replenishmentOrPayment) {
        this.replenishmentOrPayment = replenishmentOrPayment;
    }
}
