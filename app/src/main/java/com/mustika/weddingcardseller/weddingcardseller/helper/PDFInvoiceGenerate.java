package com.mustika.weddingcardseller.weddingcardseller.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.mustika.weddingcardseller.weddingcardseller.R;
import com.mustika.weddingcardseller.weddingcardseller.model.Invoice;
import com.mustika.weddingcardseller.weddingcardseller.model.Order;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by David on 14/10/2017.
 */

public class PDFInvoiceGenerate {

    public static String name, phoneNumber, address;
    public static String orderNumber;
//    public static String itemOrder, itemQuantity, itemPrice = "";
    public static String isDesign, designQuantity, designPrice, totalPrice = "";
    public static long payment30, payment40 = 0;
    private static String currentDate = "";

    public static String itemOrder2, itemQuantity2, itemRate2, itemPrice2 = "";

    public static String nameOrder, telephoneOrder, emailOrder;
    public static String nameSeller, emailSeller, telephoneSeller, citySeller, addressSeller, paymentTerms;
    public static String itemName, itemPrice, discount, productName, quantity, rate, amount;
    public static String weddingCardQuantity, totalPriceWeddingCard, subTotalPrice, taxPrice, grandTotalPrice;

    public static List<String> list;

    public static Context context;

    //we will add some products to arrayListROrder to show in the PDF document
    private static ArrayList<Invoice> arrayListInvoice = new ArrayList<Invoice>();
    //creating a PdfWriter variable. PdfWriter class is available at com.itextpdf.text.pdf.PdfWriter
    private PdfWriter pdfWriter;

    /**
     * iText allows to add metadata to the PDF which can be viewed in your Adobe Reader. If you right click
     * on the file and to to properties then you can see all these information.
     *
     * @param document
     */
    private static void addMetaData(Document document) {
        document.addTitle("Order Invoice");
        document.addSubject("none");
        document.addKeywords("Wedding Card, Seller");
        document.addAuthor("Mustika");
//        document.addCreator(Constant.USER_MODEL.getName());
    }

    /**
     * In this method title(s) of the document is added.
     *
     * @param document
     * @throws DocumentException
     */
    private static void addTitlePage(Document document)
            throws DocumentException {
        Paragraph paragraph = new Paragraph();
        Paragraph paragraph1 = new Paragraph();
        Paragraph paragraph2 = new Paragraph();

//        try {
//
////            document.open();
//
//            Drawable d = context.getResources().getDrawable(R.drawable.image_90);
//
//            BitmapDrawable bitDw = ((BitmapDrawable) d);
//
//            Bitmap bmp = bitDw.getBitmap();
//
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//
//            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
//
//            Image image = Image.getInstance(stream.toByteArray());
//
//            document.add(image);
//
////            document.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        // Adding several title of the document. Paragraph class is available in  com.itextpdf.text.Paragraph
//        Paragraph childParagraph = new Paragraph("Mustika", Constant.FONT_TITLE); //public static Font FONT_TITLE = new Font(Font.FontFamily.TIMES_ROMAN, 22,Font.BOLD);
//        childParagraph.setAlignment(Element.ALIGN_LEFT);
//        paragraph.add(childParagraph);


        // load image
        try {
            // get input stream
            InputStream ims = context.getAssets().open("logo2.png");
            Bitmap bmp = BitmapFactory.decodeStream(ims);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 50, stream);
            Image image = Image.getInstance(stream.toByteArray());
            image.setAlignment(Element.ALIGN_CENTER);
            document.add(image);
        }
        catch(IOException ex)
        {
//            return;
            ex.printStackTrace();
        }

//        try {
//            Image img = Image.getInstance(String.valueOf(context.getResources().getDrawable(R.drawable.image_90)));
//            img.setAbsolutePosition(450f, 10f);
//            document.add(img);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        try {
//            Image img = Image.getInstance("image_90.png");
//            img.setAbsolutePosition(450f, 10f);
//            document.add(img);
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        Paragraph paragraph4 = new Paragraph("Address : "+addressSeller);
        paragraph4.setFont(Constant.FONT_SUBTITLE);
        paragraph4.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph4);

//        Paragraph childParagraph2 = new Paragraph("DATE: "+currentDate+"\n" + "INVOICE: "+orderNumber+"\n" + "FOR: Payment"); //public static Font FONT_TITLE = new Font(Font.FontFamily.TIMES_ROMAN, 22,Font.BOLD);
//        childParagraph2.setAlignment(Element.ALIGN_RIGHT);
//        paragraph.add(childParagraph2);

//        Paragraph childParagraph3 = new Paragraph("Jl. Candi Raya, Purwoyoso, Ngaliyan\n" +
//                "Semarang, Jawa Tengah, Indonesia\n" +
//                "Phone 0811-2599-889"); //public static Font FONT_TITLE = new Font(Font.FontFamily.TIMES_ROMAN, 22,Font.BOLD);
//        childParagraph3.setAlignment(Element.ALIGN_LEFT);
//        paragraph.add(childParagraph3);

        Paragraph p = new Paragraph();
        addEmptyLine(p, 3);
        paragraph1.add(p);

        Font red = new Font(Font.FontFamily.COURIER, 16, Font.BOLD, BaseColor.RED);
        Chunk redText = new Chunk("Bill To :", red);

        Paragraph p2 = new Paragraph("");
        p2.setAlignment(Element.ALIGN_LEFT);
        p2.add(redText);
        paragraph.add(p2);

        Paragraph childParagraph4 = new Paragraph("Name: "+nameSeller+"\n" + "Email: "+emailSeller+"\n" + "Street Address: "
                +address+"\n"+ "City : "+citySeller+", ST ZIP Code:\n" + "Phone: "+telephoneSeller+"\n"); //public static Font FONT_TITLE = new Font(Font.FontFamily.TIMES_ROMAN, 22,Font.BOLD);
        childParagraph4.setAlignment(Element.ALIGN_LEFT);
        paragraph.add(childParagraph4);

//        childParagraph = new Paragraph("Product List", Constant.FONT_SUBTITLE); //public static Font FONT_SUBTITLE = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
//        childParagraph.setAlignment(Element.ALIGN_CENTER);
//        paragraph.add(childParagraph);
//
//        childParagraph = new Paragraph("Report generated on: 17.12.2015" , Constant.FONT_SUBTITLE);
//        childParagraph.setAlignment(Element.ALIGN_CENTER);
//        paragraph.add(childParagraph);

        addEmptyLine(paragraph, 2);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);
        //End of adding several titles

    }

    /**
     * In this method the main contents of the documents are added
     *
     * @param document
     * @throws DocumentException
     */

    private static void addContent(Document document) throws DocumentException {

        Paragraph reportBody = new Paragraph();
        reportBody.setFont(Constant.FONT_BODY); //public static Font FONT_BODY = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.NORMAL);

        Paragraph reportBody2 = new Paragraph();
        reportBody2.setFont(Constant.FONT_BODY);

//        Paragraph paragraph = new Paragraph();
//        addEmptyLine(paragraph, 2);
//        document.add(paragraph);

        createTable2(reportBody2);

        // now add all this to the document
        document.add(reportBody2);

        Paragraph paragraph2 = new Paragraph();
        addEmptyLine(paragraph2, 2);
        document.add(paragraph2);

        // Creating a table
        createTable(reportBody);

        // now add all this to the document
        document.add(reportBody);


        Font regular = new Font(Font.FontFamily.HELVETICA, 12);
        Font bold = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
//        Paragraph p = new Paragraph("NAME: ", bold);
//        p.add(new Chunk(CC_CUST_NAME, regular));

//        Paragraph paragraph3 = new Paragraph("Make all checks payable to Your Company Name\n" +
//                "If you have any questions concerning this invoice, contact Name, Phone Number, E-mail");
//        document.add(paragraph3);

//        document.add(new Paragraph("Make all checks payable to Your Company Name\n" +
//                "If you have any questions concerning this invoice, contact Name, Phone Number, E-mail"));

        Paragraph p = new Paragraph();
        addEmptyLine(p, 1);
        document.add(p);


//        FontSelector selector1 = new FontSelector();
//        Font f1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12);
//        f1.setColor(BaseColor.BLUE);
//        selector1.addFont(f1);
//        Phrase ph = selector1.process(str1);


        Font red = new Font(Font.FontFamily.HELVETICA, 20, Font.NORMAL, BaseColor.RED);
        Chunk redText = new Chunk("CONGRATULATIONS!", red);
        Font blue = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLUE);
        Chunk blueText = new Chunk("This text is blue and bold. ", blue);
        Font green = new Font(Font.FontFamily.HELVETICA, 12, Font.ITALIC, BaseColor.GREEN);
        Chunk greenText = new Chunk("This text is green and italic. ", green);

//        Paragraph paragraph4 = new Paragraph();
//        paragraph4.setAlignment(Element.ALIGN_BOTTOM);
//        paragraph4.add(redText);
//        document.add(paragraph4);
    }

    public static PdfPTable createFirstTable() {
        // a table with three columns
        PdfPTable table = new PdfPTable(3);
        // the cell object
        PdfPCell cell;
        // we add a cell with colspan 3
        cell = new PdfPCell(new Phrase("Cell with colspan 3"));

        cell.setColspan(3);
        table.addCell(cell);
        // now we add a cell with rowspan 2
        cell = new PdfPCell(new Phrase("Cell with rowspan 2"));
        cell.setRowspan(2);
        table.addCell(cell);
        // we add the four remaining cells with addCell()
        table.addCell("row 1; cell 1");
        table.addCell("row 1; cell 2");
        table.addCell("row 2; cell 1");
        table.addCell("row 2; cell 2");
        return table;
    }

    /**
     * This method is responsible to add table using iText
     *
     * @param reportBody
     * @throws BadElementException
     */
    private static void createTable(Paragraph reportBody)
            throws BadElementException {

        float[] columnWidths = {3, 2, 2, 1}; //total 4 columns and their width. The first three columns will take the same width and the fourth one will be 5/2.
        PdfPTable table = new PdfPTable(columnWidths);
        table.setWidthPercentage(100); //set table with 100% (full page)
        table.getDefaultCell().setUseAscender(true);


        //Adding table headers
        PdfPCell cell = new PdfPCell(new Phrase("Product Name", //Table Header
                Constant.FONT_TABLE_HEADER)); //Public static Font FONT_TABLE_HEADER = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.BOLD);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER); //alignment
        cell.setBackgroundColor(new GrayColor(0.75f)); //cell background color
        cell.setFixedHeight(30); //cell height
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Quantity",
                Constant.FONT_TABLE_HEADER));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(new GrayColor(0.75f));
        cell.setFixedHeight(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Rate",
                Constant.FONT_TABLE_HEADER));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(new GrayColor(0.75f));
        cell.setFixedHeight(20);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Amount",
                Constant.FONT_TABLE_HEADER));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(new GrayColor(0.75f));
        cell.setFixedHeight(30);
        table.addCell(cell);




//        List<String> list1 = new ArrayList<>();
////        ListItem item1 = new ListItem("JavaFX");
////        ListItem item2 = new ListItem("Java");
////        ListItem item3 = new ListItem("Java Servlets");
//        list1.add("item1");
//        list1.add("item2");
//        list1.add("item3");
//
//        PdfPCell c2 = new PdfPCell();
//        c2.add(list1);
//        table.addCell(c2);

        generateTableData();

        for (int i = 0; i < arrayListInvoice.size(); i++) { //
            cell = new PdfPCell(new Phrase(arrayListInvoice.get(i).getItemName()));
            cell.setFixedHeight(40);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(arrayListInvoice.get(i).getItemQuantity()));
            cell.setFixedHeight(40);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(arrayListInvoice.get(i).getItemRate()));
            cell.setFixedHeight(40);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(arrayListInvoice.get(i).getItemAmount()));
            cell.setFixedHeight(40);
            table.addCell(cell);
        }

        
        
        
        
//        cell = new PdfPCell(new Phrase("Total"));
//        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        cell.setBackgroundColor(new GrayColor(0.75f));
//        cell.setFixedHeight(30);
//        cell.setColspan(1);
//        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Sub Total"));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setColspan(3);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("IDR "+subTotalPrice));
//        cell.setRowspan(1);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Tax 10%"));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setColspan(3);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("IDR "+taxPrice));
//        cell.setRowspan(1);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Grand Total"));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setColspan(3);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("IDR "+grandTotalPrice));
//        cell.setRowspan(1);
        table.addCell(cell);

//        cell = new PdfPCell(new Phrase("30% Payment"));
//        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//        cell.setBorderColor(BaseColor.WHITE);
//        cell.setColspan(2);
//        table.addCell(cell);
//
//        cell = new PdfPCell(new Phrase("IDR "+payment30));
////        cell.setRowspan(1);
//        table.addCell(cell);


//        Paragraph paragraph1 = new Paragraph();
////        Paragraph paragraph2 = new Paragraph();
//
//        // Adding several title of the document. Paragraph class is available in  com.itextpdf.text.Paragraph
//        Paragraph childParagraph = new Paragraph("PT. Global Perkasa Menanindo", Constant.FONT_TITLE); //public static Font FONT_TITLE = new Font(Font.FontFamily.TIMES_ROMAN, 22,Font.BOLD);
//        childParagraph.setAlignment(Element.ALIGN_LEFT);
//        paragraph1.add(childParagraph);
//
//        Paragraph childParagraph2 = new Paragraph("DATE: October 7, 2017\n" + "INVOICE # 100\n" + "FOR:"); //public static Font FONT_TITLE = new Font(Font.FontFamily.TIMES_ROMAN, 22,Font.BOLD);
//        childParagraph2.setAlignment(Element.ALIGN_LEFT);
//        paragraph1.add(childParagraph2);


        // we add the four remaining cells with addCell()
//        table.addCell("row 1; cell 1");
//        table.addCell("row 1; cell 2");
//        table.addCell("row 2; cell 1");
//        table.addCell("row 2; cell 2");

        //End of adding table headers

        //This method will generate some static data for the table
//        generateTableData();

        //Adding data into table
//        for (int i = 0; i < arrayListROrder.size(); i++) { //
//            cell = new PdfPCell(new Phrase(arrayListROrder.get(i).getName()));
//            cell.setFixedHeight(28);
//            table.addCell(cell);
//
//            cell = new PdfPCell(new Phrase(arrayListROrder.get(i).getBrandName()));
//            cell.setFixedHeight(28);
//            table.addCell(cell);
//
//            cell = new PdfPCell(new Phrase(arrayListROrder.get(i).getCategoryName()));
//            cell.setFixedHeight(28);
//            table.addCell(cell);
//
//            cell = new PdfPCell(new Phrase(arrayListROrder.get(i).getUnitName()));
//            cell.setFixedHeight(28);
//            table.addCell(cell);
//        }

        reportBody.add(table);

    }

    private static void createTable2(Paragraph reportBody2)
            throws BadElementException {

        float[] columnWidths = {2, 2, 2, 2, 2}; //total 4 columns and their width. The first three columns will take the same width and the fourth one will be 5/2.
        PdfPTable table = new PdfPTable(columnWidths);
        table.setWidthPercentage(100); //set table with 100% (full page)
        table.getDefaultCell().setUseAscender(true);


        //Adding table headers
        PdfPCell cell = new PdfPCell(new Phrase("Product Name", //Table Header
                Constant.FONT_TABLE_HEADER)); //Public static Font FONT_TABLE_HEADER = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.BOLD);

        cell = new PdfPCell(new Phrase("Invoice #",
                Constant.FONT_TABLE_HEADER));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(new GrayColor(0.75f));
        cell.setFixedHeight(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Invoice Date",
                Constant.FONT_TABLE_HEADER));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(new GrayColor(0.75f));
        cell.setFixedHeight(20);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Name of Rep.",
                Constant.FONT_TABLE_HEADER));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(new GrayColor(0.75f));
        cell.setFixedHeight(30);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Contact phone",
                Constant.FONT_TABLE_HEADER));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(new GrayColor(0.75f));
        cell.setFixedHeight(30);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Payment terms",
                Constant.FONT_TABLE_HEADER));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(new GrayColor(0.75f));
        cell.setFixedHeight(30);
        table.addCell(cell);


        for (int i = 0; i < arrayListInvoice.size(); i++) { //
            cell = new PdfPCell(new Phrase(orderNumber));
            cell.setFixedHeight(40);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(currentDate));
            cell.setFixedHeight(40);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(nameSeller));
            cell.setFixedHeight(40);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(telephoneSeller));
            cell.setFixedHeight(40);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(paymentTerms));
            cell.setFixedHeight(40);
            table.addCell(cell);
        }

        reportBody2.add(table);
    }

    /**
     * This method is used to add empty lines in the document
     *
     * @param paragraph
     * @param number
     */
    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    /**
     * Generate static data for table
     */

    private static void generateTableData() {
        Invoice invoice = null;
        invoice = new Invoice(productName, quantity, rate, amount);
        arrayListInvoice.add(invoice);

//        Order = new Order("Countainer 40 Feet", "1", "Rp 3.000.000");
//        arrayListROrder.add(Order);
//
//        Order = new Order("Design", "3", "Rp 750.0000");
//        arrayListROrder.add(Order);

//        Order = new Order("LG Mini", "Piece", "LG", "Smartphone");
//        arrayListROrder.add(Order);
//
//        Order = new Order("iPhone 6", "Piece", "Apple", "Smartphone");
//        arrayListROrder.add(Order);
    }

//    public boolean createPDF(Context context, String nameOrder, String telephoneOrder, String emailOrder, String nameSeller, String emailSeller,
//                             String telephoneSeller, String citySeller, String itemName, String itemPrice, String discount,
//                             String weddingCardQuantity, String totalPriceWeddingCard, String subTotalPrice, String taxPrice,
//                             String grandTotalPrice, String orderNumber) {

    public boolean createPDF(Context context, String sellerAddress, String orderName, String orderEmail, String orderPhone,
                             String orderNumber, String currentDate, String sellerName, String sellerPhone, String paymentTerms,
                             String productName, String quantity, String rate, String amount, String discount, String subTotal, String tax, String grandTotal) {

        arrayListInvoice.clear();

        this.context = context;

        this.addressSeller = sellerAddress;
        this.nameOrder = orderName;
        this.emailOrder = orderEmail;
        this.telephoneOrder = orderPhone;

        this.orderNumber = orderNumber;
        this.currentDate = currentDate;
        this.nameSeller = sellerName;
        this.telephoneSeller = sellerPhone;
        this.paymentTerms = paymentTerms;

        this.productName = productName;
        this.quantity = quantity;
        this.rate = rate;
        this.amount = amount;
        this.discount = discount;
        this.subTotalPrice = subTotal;
        this.taxPrice = tax;
        this.grandTotalPrice = grandTotal;


//        this.citySeller = citySeller;
//        this.itemName = itemName;
//        this.itemPrice = itemPrice;
//        this.discount = discount;
//        this.weddingCardQuantity = weddingCardQuantity;
//        this.totalPriceWeddingCard = totalPriceWeddingCard;
//        this.subTotalPrice = subTotalPrice;
//        this.taxPrice = taxPrice;
//        this.grandTotalPrice = grandTotalPrice;
//
//        this.list = list;
//
//        Date date = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//        currentDate = sdf.format(date);

//        this.itemOrder = itemOrder +"\n"+isDesign;

//        itemOrder2 = this.itemOrder + "\n" + isDesign;
//        itemQuantity2 = this.itemQuantity + "\n" + designQuantity;
//        itemPrice2 = this.itemPrice + "\n" + designPrice;;

//        itemOrder2 = this.itemName;
//        itemQuantity2 = this.weddingCardQuantity;
//        itemRate2  = this.itemPrice;
//        itemPrice2 = this.totalPriceWeddingCard;
//
//
//        Log.e("PDF", itemOrder2+", "+itemQuantity2+", "+itemPrice2);

//        payment30 = Long.parseLong(totalPrice) * 30/100;
//        payment40 = Long.parseLong(totalPrice) * 40/100;

        try {
            //creating a directory in SD card
            File mydir = new File(Environment.getExternalStorageDirectory()
                    + Constant.PATH_ORDER_INVOICE); //PATH_PRODUCT_REPORT="/SIAS/REPORT_PRODUCT/"
            if (!mydir.exists()) {
                mydir.mkdirs();
            }

            //getting the full path of the PDF report name
            String mPath = Environment.getExternalStorageDirectory().toString()
                    + Constant.PATH_ORDER_INVOICE //PATH_PRODUCT_REPORT="/SIAS/REPORT_PRODUCT/"
                    + orderNumber + ".pdf"; //reportName could be any name

            //constructing the PDF file
            File pdfFile = new File(mPath);

            //Creating a Document with size A4. Document class is available at  com.itextpdf.text.Document
            Document document = new Document(PageSize.A4);

            //assigning a PdfWriter instance to pdfWriter
            pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));

            //PageFooter is an inner class of this class which is responsible to create Header and Footer
            PageHeaderFooter event = new PageHeaderFooter();
            pdfWriter.setPageEvent(event);

            //Before writing anything to a document it should be opened first
            document.open();

            //Adding meta-data to the document
            addMetaData(document);
            //Adding Title(s) of the document
            addTitlePage(document);
            //Adding main contents of the document
            addContent(document);

// Adding cell to the table
//            PdfPCell listCell = new PdfPCell(); // Creating a cell
            //Closing the document
            document.close();
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
        return true;
    }

    /**
     * This is an inner class which is used to create header and footer
     *
     * @author XYZ
     */

    class PageHeaderFooter extends PdfPageEventHelper {
        Font ffont = new Font(Font.FontFamily.UNDEFINED, 5, Font.ITALIC);

        public void onEndPage(PdfWriter writer, Document document) {

            /**
             * PdfContentByte is an object containing the user positioned text and graphic contents
             * of a page. It knows how to apply the proper font encoding.
             */
            PdfContentByte cb = writer.getDirectContent();

            /**
             * In iText a Phrase is a series of Chunks.
             * A chunk is the smallest significant part of text that can be added to a document.
             *  Most elements can be divided in one or more Chunks. A chunk is a String with a certain Font
             */

            Font red = new Font(Font.FontFamily.COURIER, 16, Font.BOLD, BaseColor.RED);
            Chunk redText = new Chunk("CONGRATULATIONS!", red);
            Font blue = new Font(Font.FontFamily.COURIER, 9, Font.NORMAL, BaseColor.BLUE);
            Chunk blueText = new Chunk("May the bond of your love be strengthend at all times. " +
                    "Keep the faith, love and thrust with one another.", blue);
            Font green = new Font(Font.FontFamily.HELVETICA, 12, Font.ITALIC, BaseColor.GREEN);
            Chunk greenText = new Chunk("This text is green and italic. ", green);

            Phrase footer_poweredBy = new Phrase("", Constant.FONT_HEADER_FOOTER); //public static Font FONT_HEADER_FOOTER = new Font(Font.FontFamily.UNDEFINED, 7, Font.ITALIC);
//            footer_poweredBy.setFont(Font.BOL);
            footer_poweredBy.add(redText);
            Phrase footer_pageFooter = new Phrase("", Constant.FONT_HEADER_FOOTER);
            footer_pageFooter.add(blueText);

            // Header
            // ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, header,
            // (document.getPageSize().getWidth()-10),
            // document.top() + 10, 0);

            // footer: show page number in the bottom right corner of each age
            ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                    footer_pageFooter,
                    (document.getPageSize().getWidth() - 295),
                    document.bottom() -20, 0);
//			// footer: show page number in the bottom right corner of each age
            ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                    footer_poweredBy, (document.right() - document.left()) / 2
                            + document.leftMargin(), document.bottom() - 5, 0);
        }
    }
}
