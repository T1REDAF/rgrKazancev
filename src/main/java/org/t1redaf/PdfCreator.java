package org.t1redaf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.List;

public class PdfCreator {

    public static final String FONT = "C:\\\\WINDOWS\\Fonts\\\\ARIAL.TTF";

    public static void create(List<Double> procents, List<Double> ostatok) {
        Document doc = new Document();

        try {
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("Payments.pdf"));

            doc.open();

            for (int i=0; i<ostatok.size(); i++) {

                doc.add(new Paragraph(String.format(
                        "%s-ый месяц --- Добавлено процентов: %s руб. --- Остаток вклада: %s руб.",
                        i+1,
                        new DecimalFormat("###,###.##"
                        ).format(procents.get(i)),ostatok.get(i)), FontFactory.getFont(FONT,"CP1251",true)));
            }
            doc.close();
            writer.close();
        } catch (DocumentException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}