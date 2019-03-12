package com.mustika.weddingcardseller.weddingcardseller.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mustika.weddingcardseller.weddingcardseller.R;
import com.mustika.weddingcardseller.weddingcardseller.helper.PDFInvoiceGenerate;

import java.util.ArrayList;
import java.util.List;

public class TestInvoiceActivity extends AppCompatActivity {

    PDFInvoiceGenerate pdfInvoiceGenerate;

    public static String fullNameM, nickNameM, fatherNameM, motherNameM, parentAddressM,
            fullNameW, nickNameW, fatherNameW, motherNameW, parentAddressW, dayAndDate, time, place,
            fullNameO, nickNameO, emailO, telephoneO;
    public static int itemId, discountId, weddingCardQuantity, discount;
    public static String itemName, description, imageUrl, username, emailSeller, telephoneSeller, citySeller,
    orderNumber;
    public static int price, priceQuantity, subTotalPrice, taxPrice, finalPrice;

    public static List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_invoice);

        pdfInvoiceGenerate = new PDFInvoiceGenerate();

        list = new ArrayList<>();
        list.add("Satu");
        list.add("Dua");
        list.add("Tiga");


        fullNameM = "a";
        emailO = "a@gmail.com";
        telephoneO = "088711";
        username = "aaa";
        emailSeller = "aaa@gmail.com";
        telephoneSeller = "0866111";
        citySeller = "Jogja";
        itemName = "Kartu 1";
        price = 2000;
        weddingCardQuantity = 500;
        priceQuantity = 20000;
        subTotalPrice = 40000;
        discount = 10;
        taxPrice = 200;
        finalPrice = 60000;
        orderNumber = "A222d8822";

//        pdfInvoiceGenerate.createPDF(this, "invoice", fullNameO, emailO, telephoneO, username, emailSeller,
//                telephoneSeller, citySeller, name, String.valueOf(price), String.valueOf(weddingCardQuantity),
//                String.valueOf(priceQuantity), String.valueOf(subTotalPrice), String.valueOf(discount), String.valueOf(finalPrice), orderNumber);
    }

    @Override
    protected void onResume() {
        super.onResume();

//        pdfInvoiceGenerate.createPDF(this, fullNameO, telephoneO, emailO,  username, emailSeller,
//                telephoneSeller, citySeller, itemName, String.valueOf(price), String.valueOf(discount), String.valueOf(weddingCardQuantity),
//                String.valueOf(priceQuantity), String.valueOf(subTotalPrice), String.valueOf(taxPrice), String.valueOf(finalPrice), orderNumber, list);
    }
}
