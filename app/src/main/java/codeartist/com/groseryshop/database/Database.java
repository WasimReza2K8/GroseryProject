package codeartist.com.groseryshop.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import codeartist.com.groseryshop.datamodel.CouponDataModel;
import codeartist.com.groseryshop.datamodel.FinalPriceModel;
import codeartist.com.groseryshop.datamodel.ProductDataModel;

/**
 * Created by ASUS on 2/7/2016.
 */
public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "grocery";
    static final int DATABASE_VERSION = 1;
    private static Database instance = null;
    private static SQLiteDatabase database = null;
    public static final String PRODUCT_TABLE = "product_table";
    public static final String COUPON_RATE_TABLE = "coupon_rate_table";
    public static final String COUPON_ITEM_TABLE = "coupon_item_table";
    public static final String PRODUCT_NAME = "product_name";
    public static final String PRODUCT_PRICE = "product_price";
    public static final String PRODUCT_QUANTITY = "product_quantity";
    public static final String COLUMN_COUPON_ITEM_ID = "_id";
    public static final String COLUMN_COUPON_NUMBER = "coupon_number";
    public static final String COLUMN_COUPON_ID = "coupon_id";
    public static final String COLUMN_COUPON_DISCOUNT = "coupon_discount";
    public static final String COLUMN_COUPON_ITEM = "coupon_item";
    private static String[] sProductColumns = new String[]{
            PRODUCT_NAME,
            PRODUCT_PRICE,
            PRODUCT_QUANTITY
    };
    private static String[] sCouponRateColumns = new String[]{
            COLUMN_COUPON_ID,
            COLUMN_COUPON_DISCOUNT
    };
    private static String[] sCouponItemColumns = new String[]{
            COLUMN_COUPON_ITEM_ID,
            COLUMN_COUPON_NUMBER,
            COLUMN_COUPON_ITEM
    };
    private static String[] sOnlyCouponItem = new String[]{
            COLUMN_COUPON_ITEM
    };
    private static String[] sOnlyCouponNumber = new String[]{
            COLUMN_COUPON_NUMBER
    };


    Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static void init(Context context) {
        if (null == instance) {
            instance = new Database(context);
        }
    }

    public static SQLiteDatabase getDatabase() {
        if (null == database) {
            database = instance.getWritableDatabase();
        }
        return database;
    }

    public static void deactivate() {
        if (null != database && database.isOpen()) {
            database.close();
        }
        database = null;
        instance = null;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS " + PRODUCT_TABLE + " ("
                + PRODUCT_NAME + " TEXT primary key, "
                + PRODUCT_PRICE + " REAL NOT NULL, "
                + PRODUCT_QUANTITY + " INTEGER DEFAULT 1);");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + COUPON_RATE_TABLE + " ("
                + COLUMN_COUPON_ID + " INTEGER primary key, "
                + COLUMN_COUPON_DISCOUNT + " REAL NOT NULL);");
        String qu = "CREATE TABLE IF NOT EXISTS " + COUPON_ITEM_TABLE + " ("
                + COLUMN_COUPON_ITEM_ID + " INTEGER primary key autoincrement, "
                + COLUMN_COUPON_NUMBER + " INTEGER, "
                + COLUMN_COUPON_ITEM + " TEXT NOT NULL,"
                + " FOREIGN KEY (" + COLUMN_COUPON_NUMBER + ") REFERENCES " + COUPON_RATE_TABLE + " (" + COLUMN_COUPON_ID + "));";


        db.execSQL(qu);
        Log.e("coupon_item_query", qu);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public static synchronized long insertProductData(ProductDataModel model) {
        ContentValues cv = new ContentValues();
        cv.put(PRODUCT_NAME, model.getProductName());
        cv.put(PRODUCT_PRICE, model.getPrice());
        cv.put(PRODUCT_QUANTITY, model.getQuantity());
        return getDatabase().insert(PRODUCT_TABLE, null, cv);
    }

    public static synchronized long insertCouponRateData(CouponDataModel model) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_COUPON_ID, model.getCouponNumber());
        cv.put(COLUMN_COUPON_DISCOUNT, model.getDiscount());
        return getDatabase().insert(COUPON_RATE_TABLE, null, cv);
    }

    public static synchronized long insertCouponItemData(CouponDataModel model) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_COUPON_NUMBER, model.getCouponNumber());
        cv.put(COLUMN_COUPON_ITEM, model.getItem());
        return getDatabase().insertOrThrow(COUPON_ITEM_TABLE, null, cv);
    }


    public static synchronized int deleteAll(String tableName) {
        return getDatabase().delete(tableName, "1", null);
    }


    public static synchronized ProductDataModel getData(String productName) {
        // TODO Auto-generated method stub

        Cursor c = getDatabase().query(PRODUCT_TABLE, sProductColumns, PRODUCT_NAME + "=" + productName, null, null, null,
                null);
        ProductDataModel model = null;

        if (c.moveToFirst()) {

            model = new ProductDataModel();
            model.setProductName(c.getString(0));
            model.setPrice(c.getFloat(1));
            model.setQuantity(c.getInt(2));

        }
        c.close();
        return model;
    }

    public static synchronized Cursor getCursor(String tableName) {
        // TODO Auto-generated method stub
        //String[] columns =
        return getDatabase().query(tableName, sProductColumns, null, null, null, null, null);
    }

    public static synchronized Cursor getCouponItem(String tableName) {
        // TODO Auto-generated method stub
        //String[] columns =
        return getDatabase().query(tableName, sCouponItemColumns, null, null, null, null, null);
    }

    public static synchronized Cursor getOnlyCouponItem(String tableName, int couponNumber) {
        // TODO Auto-generated method stub
        //String[] columns =
        return getDatabase().query(tableName, sOnlyCouponItem, COLUMN_COUPON_NUMBER + "=" + couponNumber, null, null, null, null);
    }

    public static synchronized Cursor getCouponNumberByItem(String tableName, String itemName) {
        // TODO Auto-generated method stub
        //String[] columns =
        return getDatabase().query(tableName, sOnlyCouponNumber, COLUMN_COUPON_ITEM + " = '" + itemName + "'"
                , null, null, null, null);
    }

    public static synchronized Cursor getCouponRate(String tableName) {
        // TODO Auto-generated method stub
        //String[] columns =
        return getDatabase().query(tableName, sCouponRateColumns, null, null, null, null, null);
    }

    public static int deleteCoupon(int id) {
        return getDatabase().delete(COUPON_RATE_TABLE, COLUMN_COUPON_ID + "=" + id, null);
    }

    public static int deleteCouponItem(int id) {
        return getDatabase().delete(COUPON_ITEM_TABLE, COLUMN_COUPON_NUMBER + "=" + id, null);
    }

    public static synchronized boolean isValidCouponNumber(int number) {
        Cursor c = null;
        try {

            c = getDatabase().query(COUPON_RATE_TABLE, sCouponRateColumns, COLUMN_COUPON_ID + "=" + number, null, null, null, null);
            if (c == null || !c.moveToFirst()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            c.close();
        }
        return false;
    }

    public static synchronized ArrayList<ProductDataModel> getAllProduct() {
        ArrayList<ProductDataModel> list = new ArrayList<>();
        Cursor c = Database.getCursor(PRODUCT_TABLE);
        if (c.moveToFirst()) {

            do {
                ProductDataModel model = new ProductDataModel();
                model.setProductName(c.getString(0));
                model.setPrice(c.getFloat(1));
                model.setQuantity(c.getInt(2));
                list.add(model);


            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    public static synchronized ArrayList<String> getAllCouponItem() {
        ArrayList<String> list = new ArrayList<>();
        String model;
        Cursor c = Database.getCouponItem(COUPON_ITEM_TABLE);
        if (c.moveToFirst()) {

            do {
                model = c.getString(2);
                list.add(model);

            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    public static synchronized ArrayList<CouponDataModel> getAllCoupon() {
        ArrayList<CouponDataModel> list = new ArrayList<>();

        Cursor c = Database.getCouponRate(COUPON_RATE_TABLE);
        if (c.moveToFirst()) {

            do {
                CouponDataModel model = new CouponDataModel();
                model.setCouponNumber(Integer.parseInt(c.getString(0)));
                model.setDiscount(Float.parseFloat(c.getString(1)));
                list.add(model);

            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    public static synchronized CouponDataModel getOneCouponItem(int couponNumber, CouponDataModel model) {
        ArrayList<String> items = new ArrayList<>();
        Cursor c = Database.getOnlyCouponItem(COUPON_ITEM_TABLE, couponNumber);
        if (c.moveToFirst()) {

            do {
                items.add(c.getString(0));

            } while (c.moveToNext());
        }
        model.setItemList(items);
        c.close();
        return model;
    }

    public static synchronized long updateProductPrice(ProductDataModel model) {
        ContentValues cv = new ContentValues();
        cv.put(PRODUCT_PRICE, model.getPrice());
        String strSQL = "UPDATE " + PRODUCT_TABLE + " SET " + PRODUCT_PRICE + " = " + model.getPrice() + " WHERE " + PRODUCT_NAME + " = '" + model.getProductName() + "'";
        getDatabase().execSQL(strSQL);
        return 1;
        //return getDatabase().update(PRODUCT_TABLE, cv, PRODUCT_NAME+" = "+model.getProductName(), null);
    }

    public static synchronized ArrayList<CouponDataModel> shoppingList(Float budget) {
        String getPriceSum = "SELECT SUM(" + PRODUCT_PRICE + ") FROM " + PRODUCT_TABLE + " INNER JOIN "
                + COUPON_ITEM_TABLE + " ON " + PRODUCT_TABLE + "." + PRODUCT_NAME + " = " + COUPON_ITEM_TABLE + "."
                + COLUMN_COUPON_ITEM + " GROUP BY " + COLUMN_COUPON_ITEM + "." + COLUMN_COUPON_NUMBER;

        String getShoppingList = "SELECT (" + getPriceSum + " - " + COUPON_RATE_TABLE + "."
                + COLUMN_COUPON_DISCOUNT + ") AS finalRate, " + COLUMN_COUPON_DISCOUNT + ", "
                + COLUMN_COUPON_ID + " FROM " + COUPON_RATE_TABLE;

        return null;
    }

    public static synchronized ArrayList<CouponDataModel> getAllCouponWithFullInfo() {
        ArrayList<CouponDataModel> couponList = new ArrayList<>();
        CouponDataModel model = null;
        Cursor cursor = getDatabase().rawQuery("SELECT "+ COUPON_RATE_TABLE + "."+ COLUMN_COUPON_ID
                +", "+  COUPON_RATE_TABLE + "."+ COLUMN_COUPON_DISCOUNT  +", "+ COUPON_ITEM_TABLE + "."
                + COLUMN_COUPON_ITEM + ", "+ PRODUCT_TABLE+ "." + PRODUCT_PRICE + " FROM " + COUPON_RATE_TABLE
                + " JOIN " + COUPON_ITEM_TABLE + " ON "
                + COUPON_RATE_TABLE+ "."+ COLUMN_COUPON_ID + " = " + COUPON_ITEM_TABLE + "."
                + COLUMN_COUPON_NUMBER + " JOIN " + PRODUCT_TABLE + " ON " +  PRODUCT_TABLE+ "."
                + PRODUCT_NAME + " = " +  COUPON_ITEM_TABLE + "." + COLUMN_COUPON_ITEM,null);
        if (cursor.moveToFirst()) {
            do {
                Log.e("coupon_result", " id: "+cursor.getString(0) + " discount: "+cursor.getString(1)
                        + " item: "+ cursor.getString(2)+ " price: "+ cursor.getString(3));
                int id = Integer.parseInt(cursor.getString(0));
                if(couponList.size() == 0 || (model != null && id != model.getCouponNumber())){
                    model = new CouponDataModel();
                    model.setCouponNumber(id);
                    model.setDiscount(Float.parseFloat(cursor.getString(1)));
                    model.getItemList().add(cursor.getString(2));
                    model.getProductList().add(new ProductDataModel(cursor.getString(2), Float.parseFloat(cursor.getString(3) )));
                    couponList.add(model);
                } else {
                    model.getItemList().add(cursor.getString(2));
                    model.getProductList().add(new ProductDataModel(cursor.getString(2), Float.parseFloat(cursor.getString(3) )));

                }

            }while (cursor.moveToNext());
        }
        return couponList;
    }


}
