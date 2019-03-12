package com.mustika.weddingcardseller.weddingcardseller.helper;

import com.itextpdf.text.Font;

/**
 * Created by David on 23/02/2018.
 */

public class Constant {
    //Data URL

    public static final String DATA_URL = "http:///cardhouse.tech/login/feed_item.php?page=";
    public static final String IMAGE_URL = "http://cardhouse.tech/login/module/mod_item/files/";
    public static final String BANNER_URL = "http://cardhouse.tech/login/module/mod_seller/files/";
    public static final String DESIGN_URL = "http://cardhouse.tech/login/module/mod_monitor/files/";
    public static final String CUSTOM_URL = "http://cardhouse.tech/login/uploads/";


    public static final String DISCOUNT_URL = "http://cardhouse.tech/login/feed_discount.php";
    public static final String ADDON_URL = "http://cardhouse.tech/login/feed_addon.php";

    public static final String SOUVENIR_URL = "http://cardhouse.tech/login/feed_souvenir.php?page=";
    public static final String SOUVENIR_IMAGE_URL = "http://cardhouse.tech/login/module/mod_souvenir/files/";

    public static final String ORDER_URL = "http://cardhouse.tech/login/order.php";

    private static final String ROOT_URL = "http://cardhouse.tech/login/Api.php?apicall=";
    public static final String URL_REGISTER = ROOT_URL + "register";
    public static final String URL_LOGIN= ROOT_URL + "login";
    public static final String URL_BUYER_AMOUNT = ROOT_URL + "buyeramount";
//    public static final String URL_LOGIN= "http://10.0.2.2/web/feed_login2.php";

//    public static final String READ_ORDER_URL = "http://10.0.2.2/web/feed_orders.php";

    public static final String READ_ORDER_URL = "http://cardhouse.tech/login/order.php?apicall=select";
    public static final String READ_ORDER_URL_ID = "http://cardhouse.tech/login/order.php?apicall=selectid&sellerId=";
    public static final String READ_ORDER_URL_ALL = "http://cardhouse.tech/login/order.php?apicall=selectall&orderId=";
//    public static final String READ_ORDER_URL_ID = "http://10.0.2.2/web/order.php?apicall=select&id=";

    public static final String READ_ORDER_HISTORY_URL_ALL = "http://cardhouse.tech/login/order.php?apicall=selecthistoryall&orderId=";

    public static final String READ_SELLER_URL = "http://cardhouse.tech/login/Api.php?apicall=readseller";
    public static final String READ_ITEM_URL = "http://cardhouse.tech/login/Api.php?apicall=readitem";
    public static final String READ_SOUVENIR_URL = "http://cardhouse.tech/login/Api.php?apicall=readsouvenir";
    public static final String READ_ADDON_URL = "http://cardhouse.tech/login/Api.php?apicall=readaddon";

    public static final String READ_ORDER_HISTORY_URL = "http://cardhouse.tech/login/order.php?apicall=selectidhistory&sellerId=";

    public static final String CHANGE_COMPLETE_PAYMENT_URL = "http://cardhouse.tech/login/order.php?apicall=update_payment&orderId=";
    public static final String CHANGE_COMPLETE_ORDER_URL = "http://cardhouse.tech/login/order.php?apicall=update_order&orderId=";

    public static final String READ_REPORT_URL = "http://cardhouse.tech/login/order.php?apicall=read_report&orderId=";
    public static final String UPDATE_REPORT_URL = "http://cardhouse.tech/login/order.php?apicall=update_report&reportId=";

    private static final String ROOT_IMAGE_URL = "http://cardhouse.tech/login/image.php?apicall=";
    public static final String UPLOAD_URL = ROOT_IMAGE_URL + "uploadpic";
    public static final String GET_PICS_URL = ROOT_IMAGE_URL + "getpics";




// Emulator
//    public static final String DATA_URL = "http://10.0.2.2/web/feed_item.php?page=";
//    public static final String IMAGE_URL = "http://10.0.2.2/web/module/mod_item/files/";
//    public static final String BANNER_URL = "http://10.0.2.2/web/module/mod_seller/files/";
//    public static final String DESIGN_URL = "http://10.0.2.2/web/module/mod_monitor/files/";
//    public static final String CUSTOM_URL = "http://10.0.2.2/web/uploads/";
//
//
//    public static final String DISCOUNT_URL = "http://10.0.2.2/web/feed_discount.php";
//    public static final String ADDON_URL = "http://10.0.2.2/web/feed_addon.php";
//
//    public static final String SOUVENIR_URL = "http://10.0.2.2/web/feed_souvenir.php?page=";
//    public static final String SOUVENIR_IMAGE_URL = "http://10.0.2.2/web/module/mod_souvenir/files/";
//
//    public static final String ORDER_URL = "http://10.0.2.2/web/order.php";
//
//    private static final String ROOT_URL = "http://10.0.2.2/web/Api.php?apicall=";
//    public static final String URL_REGISTER = ROOT_URL + "register";
//    public static final String URL_LOGIN= ROOT_URL + "login";
//    public static final String URL_BUYER_AMOUNT = ROOT_URL + "buyeramount";
////    public static final String URL_LOGIN= "http://10.0.2.2/web/feed_login2.php";
//
////    public static final String READ_ORDER_URL = "http://10.0.2.2/web/feed_orders.php";
//
//    public static final String READ_ORDER_URL = "http://10.0.2.2/web/order.php?apicall=select";
//    public static final String READ_ORDER_URL_ID = "http://10.0.2.2/web/order.php?apicall=selectid&sellerId=";
//    public static final String READ_ORDER_URL_ALL = "http://10.0.2.2/web/order.php?apicall=selectall&orderId=";
////    public static final String READ_ORDER_URL_ID = "http://10.0.2.2/web/order.php?apicall=select&id=";
//
//    public static final String READ_ORDER_HISTORY_URL_ALL = "http://10.0.2.2/web/order.php?apicall=selecthistoryall&orderId=";
//
//    public static final String READ_SELLER_URL = "http://10.0.2.2/web/Api.php?apicall=readseller";
//    public static final String READ_ITEM_URL = "http://10.0.2.2/web/Api.php?apicall=readitem";
//    public static final String READ_SOUVENIR_URL = "http://10.0.2.2/web/Api.php?apicall=readsouvenir";
//    public static final String READ_ADDON_URL = "http://10.0.2.2/web/Api.php?apicall=readaddon";
//
//    public static final String READ_ORDER_HISTORY_URL = "http://10.0.2.2/web/order.php?apicall=selectidhistory&sellerId=";
//
//    public static final String CHANGE_COMPLETE_PAYMENT_URL = "http://10.0.2.2/web/order.php?apicall=update_payment&orderId=";
//    public static final String CHANGE_COMPLETE_ORDER_URL = "http://10.0.2.2/web/order.php?apicall=update_order&orderId=";
//
//    public static final String READ_REPORT_URL = "http://10.0.2.2/web/order.php?apicall=read_report&orderId=";
//    public static final String UPDATE_REPORT_URL = "http://10.0.2.2/web/order.php?apicall=update_report&reportId=";
//
//    private static final String ROOT_IMAGE_URL = "http://10.0.2.2/web/image.php?apicall=";
//    public static final String UPLOAD_URL = ROOT_IMAGE_URL + "uploadpic";
//    public static final String GET_PICS_URL = ROOT_IMAGE_URL + "getpics";



    //JSON TAGS
    public static final String TAG_ITEM_ID = "item_id";
    public static final String TAG_NAME = "name";
    public static final String TAG_DESCRIPTION = "description";
    public static final String TAG_PRICE = "price";
    public static final String TAG_IMAGE_URL = "image_url";

    public static final String TAG_SOUVENIR_ID = "souvenir_id";
    public static final String TAG_SOUVENIR_NAME = "souvenir_name";
    public static final String TAG_SOUVENIR_PRICE = "souvenir_price";
    public static final String TAG_SOUVENIR_IMAGE_URL = "souvenir_image_url";

    public static final String TAG_DISCOUNT_ID = "discount_id";
    public static final String TAG_TOTAL = "total";
    public static final String TAG_DISCOUNT = "discount";

    public static final String TAG_ADDON_ID = "addon_id";

    public static final String TAG_ORDER_ID = "order_id";
    public static final String TAG_ORDER_TIME = "order_time";
    public static final String TAG_SELLER_NAME = "username";
    public static final String TAG_BUYER_NAME = "full_name_o";

    public static final String TAG_ORDER_HISTORY_ID = "order_history_id";

//    private static final String ROOT_URL = "http://10.0.2.2/wedding/Api.php?apicall=";
//    public static final String URL_REGISTER = ROOT_URL + "signup";
//    public static final String URL_LOGIN= ROOT_URL + "login";

    public static String PATH_ORDER_INVOICE = "/MUSTIKA/ORDER_INVOICE/";
    public static String PATH_BANNER_FILE = "/MUSTIKA/BANNER_FILE";

    public static Font FONT_TITLE = new Font(Font.FontFamily.TIMES_ROMAN, 22,Font.BOLD);
    public static Font FONT_SUBTITLE = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
    public static Font FONT_BODY = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.NORMAL);
    public static Font FONT_TABLE_HEADER = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.BOLD);
    public static Font FONT_HEADER_FOOTER = new Font(Font.FontFamily.UNDEFINED, 7, Font.ITALIC);
}
