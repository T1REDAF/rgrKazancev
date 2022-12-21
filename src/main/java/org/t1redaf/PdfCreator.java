package org.t1redaf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PdfCreator {
    //TODO бан за полный путь
    public static final Font FONT = FontFactory.getFont("C:\\\\WINDOWS\\Fonts\\\\ARIAL.TTF","CP1251",true);//шрифт

    public static void create(List<Double> procents, List<Double> ostatok,int popolnenie, String replenishmentOrPayment,DepositDTO depositDTO) {//статический метод
        String[] tableHeads = {"Месяц","Проценты (руб)",replenishmentOrPayment,"Остаток (руб)"};//шапка пдф документа
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

            Paragraph firstLine = new Paragraph("ОТЧЕТ О РАБОТЕ ПРОГРАММЫ: \n\n", FONT);
            firstLine.setAlignment(Element.ALIGN_CENTER);
            doc.add(firstLine);

            doc.add(new Paragraph(depositDTO.toString(), FONT));
            PdfPTable table = new PdfPTable(4);

            image.setAbsolutePosition(475, 725);
            writer.getDirectContent().addImage(image);
            doc.add(image);

            for (String tableHead : tableHeads) {
                table.addCell(new Phrase(tableHead,FONT));
            }
            for (int i = 0; i < procents.size(); i++) {
                table.addCell(Integer.toString(i+1));
                table.addCell(String.format("%.2f", procents.get(i)));
                table.addCell(Integer.toString(depositDTO.getPopolnenie()));
                table.addCell(String.format("%.2f",ostatok.get(i)));
            }
            doc.add(table);

            Paragraph footer = new Paragraph("Создано командой разработчиков №3", FONT);
            footer.setAlignment(Element.ALIGN_CENTER);
            doc.add(footer);

            doc.close();
            writer.close();

        } catch (DocumentException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}