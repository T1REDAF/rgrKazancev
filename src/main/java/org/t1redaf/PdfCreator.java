package org.t1redaf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Stream;

public class PdfCreator {

    public static final String FONT = "C:\\\\WINDOWS\\Fonts\\\\ARIAL.TTF";

    public static void create(List<Double> procents, List<Double> ostatok,int popolnenie, String replenishmentOrPayment) {
        Document doc = new Document();

        try {
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("Payments.pdf"));

            doc.open();
            //TODO доделать назуй когда в пдф выводит пополнение(сделано) еще и кривой вывод (выплаты) сука


            doc.add(new Paragraph(String.format("%25s %25s %25s %25s","Месяц","Проценты",replenishmentOrPayment,"Остаток"), FontFactory.getFont(FONT,"CP1251",true)));
            for (int i=0; i<ostatok.size(); i++) {

                doc.add(new Paragraph(String.format(
                        "%25s. %25s руб. %25s руб. %25s руб.",
                        i+1,
                        new DecimalFormat("###,###,###.##"
                        ).format(procents.get(i)),
                        new DecimalFormat("###,###,###.##"
                        ).format(popolnenie),
                        new DecimalFormat("###,###,###.##"
                        ).format(ostatok.get(i))), FontFactory.getFont(FONT,"CP1251",true)));
            }
            doc.close();
            writer.close();
        } catch (DocumentException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}