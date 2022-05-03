package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

public class PersistentAccountDAO extends SQLiteOpenHelper implements AccountDAO{


    public PersistentAccountDAO(@Nullable Context context) {
        super(context, "expenses.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String create_table_statement1 = "CREATE TABLE 'ACCOUNTS' ('accountNo' TEXT PRIMARY KEY, 'bankName' TEXT NOT NULL, 'accountHolderName' TEXT NOT NULL, 'balance' REAL NOT NULL )";
        sqLiteDatabase.execSQL(create_table_statement1);

        String create_table_statement2 = "CREATE TABLE 'TRANSACTIONS' ('date' TEXT NOT NULL, 'accountNo' TEXT NOT NULL, 'expenseType' TEXT NOT NULL, 'amount' REAL NOT NULL )";
        sqLiteDatabase.execSQL(create_table_statement2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public List<String> getAccountNumbersList() {
        List<String> accNums = new ArrayList<>();

        String queryString = "SELECT accountNo from ACCOUNTS";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                String accNum = cursor.getString(0);
                accNums.add(accNum);
            }while (cursor.moveToNext());
        }
        else {
            cursor.close();
            db.close();
            return null;
        }
        cursor.close();
        db.close();
        return  accNums;
    }

    @Override
    public List<Account> getAccountsList() {
        List<Account> accs = new ArrayList<>();

        String queryString = "SELECT * from ACCOUNTS";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                String accNum = cursor.getString(0);
                String bankName = cursor.getString(1);
                String accHolderName = cursor.getString(2);
                double balance = cursor.getDouble(3);

                Account newAcc = new Account(accNum,bankName, accHolderName, balance);

                accs.add(newAcc);
            }while (cursor.moveToNext());
        }
        else {
            cursor.close();
            db.close();
            return null;
        }
        cursor.close();
        db.close();
        return  accs;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {

        String queryString = "SELECT * from ACCOUNTS WHERE accountNo="+accountNo;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            String accNum = cursor.getString(0);
            String bankName = cursor.getString(1);
            String accHolderName = cursor.getString(2);
            double balance = cursor.getDouble(3);

            Account newAcc = new Account(accNum,bankName, accHolderName, balance);

            cursor.close();
            db.close();
            return newAcc;
        }
        else {
            String msg = "Account " + accountNo + " is invalid.";

            cursor.close();
            db.close();
            throw new InvalidAccountException(msg);
        }
    }
    public void addAccount(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("accountNo", account.getAccountNo());
        cv.put("bankName", account.getBankName());
        cv.put("accountHolderName", account.getAccountHolderName());
        cv.put("balance", account.getBalance());

        db.insert("ACCOUNTS", null,cv);
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {

    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {

    }
}