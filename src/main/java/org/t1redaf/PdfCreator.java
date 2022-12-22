package org.t1redaf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class PdfCreator {
    private static BufferedImage image;
    private static Image image3;
    public static final Font FONT = FontFactory.getFont("/Fonts/times.ttf","CP1251",true);//шрифт

    public static void create(List<Double> procents, List<Double> ostatok,int popolnenie, String replenishmentOrPayment,String startLine) {//статический метод
        String[] tableHeads = {"Месяц","Проценты (руб)",replenishmentOrPayment,"Остаток (руб)"};//шапка пдф документа
        Document doc = new Document();//иницализируем документ

        try {
            image =  ImageIO.read(PdfCreator.class.getResource("/Images/money.png"));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            Image iTextImage = Image.getInstance(baos.toByteArray());
            //WritableImage image2 = SwingFXUtils.toFXImage(image, null);
            image3 = Image.getInstance(iTextImage);
        } catch (BadElementException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        image3.scaleAbsolute(100f, 100f); //image width,height

        try {
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("Результат расчета.pdf"));
            doc.open();

            Paragraph firstLine = new Paragraph("ОТЧЕТ О РАБОТЕ ПРОГРАММЫ: \n\n", FONT);
            firstLine.setAlignment(Element.ALIGN_CENTER);
            doc.add(firstLine);

            doc.add(new Paragraph(startLine, FONT));
            PdfPTable table = new PdfPTable(4);

            image3.setAbsolutePosition(475, 725);
            writer.getDirectContent().addImage(image3);
            doc.add(image3);

            for (String tableHead : tableHeads) {
                table.addCell(new Phrase(tableHead,FONT));
            }
            for (int i = 0; i < procents.size(); i++) {
                table.addCell(Integer.toString(i+1));
                table.addCell(String.format("%.2f", procents.get(i)));
                table.addCell(Integer.toString(popolnenie));
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