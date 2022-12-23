package org.t1redaf;

public final class DepositDTO extends CalculatorDTO {

    private final int popolnenie;
    private final int srokvklad;
    private final String replenishmentOrPayment;

    @Override
    public String toString() {
        return super.toString() + "\nЕжемесячно = " + popolnenie+ " руб." +
                "\nСрок вклада = " + srokvklad + " месяцев\n\n" ;
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

    public int getSrokvklad() {
        return srokvklad;
    }

    public String getReplenishmentOrPayment() {
        return replenishmentOrPayment;
    }

}
