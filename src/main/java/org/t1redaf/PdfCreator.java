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

    public static void create(List<Double> procents, List<Double> ostatok,int popolnenie) {
        Document doc = new Document();

        try {
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("Payments.pdf"));

            doc.open();
            //TODO доделать назуй когда в пдф выводит пополнение еще и кривой вывод (выплаты) сука
            doc.add(new Paragraph(String.format("%15s %15s %15s %15s","Месяц","Проценты","Пополнение","Остаток"), FontFactory.getFont(FONT,"CP1251",true)));
            for (int i=0; i<ostatok.size(); i++) {

                doc.add(new Paragraph(String.format(
                        "%15s. %15s руб. %15s руб. %15s руб.",
                        i+1,
                        new DecimalFormat("###,###,###.##"
                        ).format(procents.get(i)),
                        new DecimalFormat("###,###.##"
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