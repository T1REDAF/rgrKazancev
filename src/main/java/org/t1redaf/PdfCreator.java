package org.t1redaf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.scene.text.TextAlignment;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class PdfCreator {

    public static final Font FONT = FontFactory.getFont("C:\\\\WINDOWS\\Fonts\\\\ARIAL.TTF","CP1251",true);//шрифт

    public static void create(List<Double> procents, List<Double> ostatok,int popolnenie, String replenishmentOrPayment,DepositDTO depositDTO) {//статический метод
        String[] tableHeads = {"Месяц","Проценты (руб)",replenishmentOrPayment,"Остаток (руб)"};//шапка пдф документа
        String tableFooter = "Создано командой разработчиков №3";
        Document doc = new Document();//иницализируем документ

        Image image = null;
        try {
            image = Image.getInstance ("src/main/resources/Images/money.png");
        } catch (BadElementException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        image.scaleAbsolute(100f, 100f); //image width,height

        try {
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("Результат расчета.pdf"));
            doc.open();

            doc.add(new Paragraph(depositDTO.toString(), FONT));

            PdfPTable table = new PdfPTable(4);

            image.setAbsolutePosition(400, 730);
            writer.getDirectContent().addImage(image);

            for (String tableHead : tableHeads) {
                table.addCell(new Phrase(tableHead,FONT));
            }
            for (int i = 0; i < procents.size(); i++) {
                table.addCell(Integer.toString(i+1));
                table.addCell(procents.get(i).toString());
                table.addCell(Integer.toString(depositDTO.getPopolnenie()));
                table.addCell(ostatok.get(i).toString());
            }



            doc.add(image);
            doc.add(table);
            doc.add((new Phrase(tableFooter,FONT)));
            doc.close();
            writer.close();
        } catch (DocumentException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}