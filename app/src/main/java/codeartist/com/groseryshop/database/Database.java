package codeartist.com.groseryshop.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    public static final String UPCOMING_TOURNAMENT_FIXTURE = "upcoming_tournament_fixture";
    public static final String NEWS_TABLE = "news_table";
    public static final String SQUAD_TABLE = "squad_table";
    public static final String TABLE_TEST = "table_test";
    public static final String TABLE_ODI = "table_ODI";
    public static final String TABLE_T20 = "table_t20";
    public static final String PRODUCT_NAME = "product_name";
    public static final String PRODUCT_PRICE = "product_price";
    public static final String PRODUCT_QUANTITY = "product_quantity";
    public static final String DURATION = "duration";
    public static final String HIGHLIGHTS_TABLE = "highlights_table";
    public static final String COLUMN_MATCH_ID = "_id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_BETWEEN = "_between";
    public static final String COLUMN_VENUE = "venue";
    public static final String COLUMN_MATCH_NUMBER = "match_number";
    public static final String COLUMN_TOURNAMENT = "tournament_name";
    public static final String COLUMN_MATCH_RESULT = "match_result";
    public static final String PLAYER_NUMBER = "player_number";
    public static final String PLAYER_NAME = "player_name";
    public static final String PLAYER_AGE = "player_age";
    public static final String PLAYER_IMAGE_LINK = "player_image_link";
    public static final String PLAYER_STYLE = "player_style";
    public static final String PLAYER_NUMBER_OF_MATCHES = "player_number_of_matches";
    public static final String BAT_INNS = "bat_inns";
    public static final String BAT_RUNS = "bat_runs";
    public static final String HIGHEST_SCORE = "highest_score";
    public static final String BAT_AVG = "bat_avg";
    public static final String BAT_STRIKE_RATE = "bat_strike_rate";
    public static final String BAT_HUNDREDS = "bat_hundreds";
    public static final String BAT_FIFTIES = "bat_fifties";
    public static final String BALL_INNS = "ball_inns";
    public static final String BALL_RUNS = "BALL_RUNS";
    public static final String BALL_WKTS = "ball_wickets";
    public static final String BALL_BEST_BOLWING_SCORE = "ball_best_bolwing";
    public static final String BALL_AVG = "ball_avg";
    public static final String BALL_ECON = "ball_econ";
    public static final String BALL_STRIKE_RATE = "ball_srike_rate";
    public static final String BALL_NO_OF_FIVE_WKTS = "ball_number_of_5_wkts";
    //public static final String COLUMN_MATCH_ID = "_id";
    public static final String PLAYER_ROLE = "player_role";


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
                + PRODUCT_PRICE + " TEXT NOT NULL, "
                + PRODUCT_QUANTITY + " INTEGER DEFAULT 1);");


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




    public static synchronized int deleteAll(String tableName) {
        return getDatabase().delete(tableName, "1", null);
    }


    public static int deleteEntry(int id, String tableName) {
        return getDatabase().delete(tableName, COLUMN_MATCH_ID + "=" + id, null);
    }

    public static synchronized ProductDataModel getData(String productName) {
        // TODO Auto-generated method stub
        String[] columns = new String[]{
                PRODUCT_NAME,
                PRODUCT_PRICE,
                PRODUCT_QUANTITY
        };
        Cursor c = getDatabase().query(PRODUCT_TABLE, columns, PRODUCT_NAME + "=" + productName, null, null, null,
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
        String[] columns = new String[]{
                PRODUCT_NAME,
                PRODUCT_PRICE,
                PRODUCT_QUANTITY
        };
        return getDatabase().query(tableName, columns, null, null, null, null, null);
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

    public static synchronized Cursor getCursorCareer(String tableName, int id) {
        // TODO Auto-generated method stub
        String[] columns = new String[]{
                COLUMN_MATCH_ID,
                PLAYER_NUMBER_OF_MATCHES,
                BAT_INNS,
                BAT_RUNS,
                HIGHEST_SCORE,
                BAT_AVG,
                BAT_STRIKE_RATE,
                BAT_HUNDREDS,
                BAT_FIFTIES,
                BALL_INNS,
                BALL_RUNS,
                BALL_WKTS,
                BALL_BEST_BOLWING_SCORE,
                BALL_AVG,
                BALL_ECON,
                BALL_STRIKE_RATE,
                BALL_NO_OF_FIVE_WKTS
        };
        return getDatabase().query(tableName, columns, COLUMN_MATCH_ID + "=" + id, null, null, null, null);
    }


    public static synchronized Cursor getSquadCursor() {
        // TODO Auto-generated method stub
        String[] columns = new String[]{
                COLUMN_MATCH_ID,
                PLAYER_NUMBER,
                PLAYER_NAME,
                PLAYER_AGE,
                PLAYER_ROLE,
                PLAYER_STYLE,
                PLAYER_IMAGE_LINK
        };
        return getDatabase().query(SQUAD_TABLE, columns, null, null, null, null, null);
    }


    public static synchronized Cursor getSquadCursor(int id) {
        // TODO Auto-generated method stub
        String[] columns = new String[]{
                COLUMN_MATCH_ID,
                PLAYER_NUMBER,
                PLAYER_NAME,
                PLAYER_AGE,
                PLAYER_ROLE,
                PLAYER_STYLE,
                PLAYER_IMAGE_LINK
        };
        return getDatabase().query(SQUAD_TABLE, columns, COLUMN_MATCH_ID + "=" + id, null, null, null, null);
    }



}
