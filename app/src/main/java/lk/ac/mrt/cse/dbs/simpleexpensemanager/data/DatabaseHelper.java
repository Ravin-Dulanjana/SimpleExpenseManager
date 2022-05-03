package lk.ac.mrt.cse.dbs.simpleexpensemanager.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String ACCOUNT_TABLE = "ACCOUNT_TABLE";
    public static final String COLUMN_ACCOUNT_NO = "accountNo";
    public static final String COLUMN_BANK_NAME = "bankName";
    public static final String COLUMN_ACCOUNT_HOLDER_NAME = "accountHolderName";
    public static final String COLUMN_BALANCE = "balance";
    public static final String TRANSACTION_TABLE = "TRANSACTION_TABLE";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_EXPENSE_TYPE = "expenseType";
    public static final String COLUMN_AMOUNT = "amount";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "190179M.db", null, 1);
    }

    public void addAccount(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ACCOUNT_NO,account.getAccountNo());
        cv.put(COLUMN_ACCOUNT_HOLDER_NAME,account.getAccountHolderName());
        cv.put(COLUMN_BANK_NAME,account.getBankName());
        cv.put(COLUMN_BALANCE,account.getBalance());
        db.insert(ACCOUNT_TABLE,null,cv);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createAccountTableStatement = "CREATE TABLE " + ACCOUNT_TABLE + " (" + COLUMN_ACCOUNT_NO + " INTEGER PRIMARY KEY, " + COLUMN_BANK_NAME + " TEXT, " + COLUMN_ACCOUNT_HOLDER_NAME + " TEXT, " + COLUMN_BALANCE + " INTEGER);";
        String createTransactionTableStatement = "CREATE TABLE " + TRANSACTION_TABLE + " (" + COLUMN_DATE + " TEXT, " + COLUMN_ACCOUNT_NO + " INTEGER, " + COLUMN_EXPENSE_TYPE + " TEXT, " + COLUMN_AMOUNT + " INTEGER)";
        db.execSQL(createAccountTableStatement);
        db.execSQL(createTransactionTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
