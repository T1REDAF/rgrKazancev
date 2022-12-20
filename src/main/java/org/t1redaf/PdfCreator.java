package org.t1redaf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;


public class PdfCreator {

    public static final String FONT = "C:\\\\WINDOWS\\Fonts\\\\ARIAL.TTF";

    public static void create(List<Double> procents, List<Double> ostatok,int popolnenie, String replenishmentOrPayment,DepositDTO deposit) {
        Document doc = new Document();

        try {
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("Результат расчета.pdf"));

            doc.open();
            doc.add(new Paragraph(deposit.toString(), FontFactory.getFont(FONT,"CP1251",true)));
            doc.add(new Paragraph(String.format("%s %25s (руб) %35s %25s (руб.)","Месяц","Проценты",replenishmentOrPayment,"Остаток"), FontFactory.getFont(FONT,"CP1251",true)));
            for (int i=0; i<ostatok.size(); i++) {

                doc.add(new Paragraph(String.format(
                        "%d. %32.2f %37d %46.2f",
                        i+1,procents.get(i),popolnenie,ostatok.get(i),FontFactory.getFont(FONT,"CP1251",true))));
            }
            doc.close();
            writer.close();
        } catch (DocumentException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}