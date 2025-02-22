/*
 * Copyright 2015 Department of Computer Science and Engineering, University of Moratuwa.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *                  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package lk.ac.mrt.cse.dbs.simpleexpensemanager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.ExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.PersistentExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class ApplicationTest {

    private static ExpenseManager expenseManager;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        expenseManager = new PersistentExpenseManager(context);
    }

    @Test
    public void testAddAccount() {
        expenseManager.addAccount("123456A","ABC","Fernando",100000);
        List<String> accountNumbers = expenseManager.getAccountNumbersList();
        assertTrue(accountNumbers.contains("123456A"));
    }

    @Test
    public void testTransactionLog(){
        expenseManager.addAccount("190179M","HNB","Ravin",500000);

        try {
            expenseManager.updateAccountBalance("190179M",1,5,2022, ExpenseType.EXPENSE,"5000");
        } catch (InvalidAccountException e) {
            e.printStackTrace();
        }
        List<Transaction> transactionList = expenseManager.getTransactionLogs();
        Transaction lastTransaction = transactionList.get(transactionList.size()-1);

        assertEquals("190179M",lastTransaction.getAccountNo());
        assertEquals(5000,lastTransaction.getAmount(),0.0);
        assertEquals(ExpenseType.EXPENSE,lastTransaction.getExpenseType());


    }
}