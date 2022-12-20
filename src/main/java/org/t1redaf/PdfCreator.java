package org.t1redaf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;


public class PdfCreator {

    public static final Font FONT = FontFactory.getFont("C:\\\\WINDOWS\\Fonts\\\\ARIAL.TTF","CP1251",true);

    public static void create(List<Double> procents, List<Double> ostatok,int popolnenie, String replenishmentOrPayment,DepositDTO depositDTO) {//статический метод
        String[] tableHeads = {"Месяц","Проценты (руб)",replenishmentOrPayment,"Остаток (руб)"};
        Document doc = new Document();

        try {
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("Результат расчета.pdf"));

            doc.open();

            doc.add(new Paragraph(depositDTO.toString(), FONT));
//            doc.add(new Paragraph(String.format("%s %25s (руб) %35s %25s (руб.)","Месяц","Проценты",replenishmentOrPayment,"Остаток"), FONT));
//            for (int i=0; i<ostatok.size(); i++) {
//
//                doc.add(new Paragraph(String.format(
//                        "%d. %32.2f %37d %46.2f",
//                        i+1,procents.get(i),popolnenie,ostatok.get(i),FONT)));
//            }
            PdfPTable table = new PdfPTable(4);

            for (String tableHead : tableHeads) {
                table.addCell(new Phrase(tableHead,FONT));
            }
            for (int i = 0; i < procents.size(); i++) {
                table.addCell(Integer.toString(i+1));
                table.addCell(procents.get(i).toString());
                table.addCell(Integer.toString(depositDTO.getPopolnenie()));
                table.addCell(ostatok.get(i).toString());
            }

            doc.add(table);
            doc.close();
            writer.close();
        } catch (DocumentException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}