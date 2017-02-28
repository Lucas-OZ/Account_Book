package com.per.note.utils;

import android.content.ContentValues;
import android.content.Context;

import com.per.note.db.SqliteManage;

/**
 * Created by 22762 on 2016/1/24.
 */
public class SqliteUtils {
    public static void updata(Context context, String count, Double changeMoney) {
        SqliteManage.QueryResult result = SqliteManage.getInstance(context).
                query("count", "count=?", new String[]{count});
        if (result.cursor != null && result.cursor.getCount() != 0) {
            result.cursor.moveToFirst();
            Double money = result.cursor.getDouble(result.cursor.getColumnIndex("money"));
            //Double changeValues = Double.parseDouble(changeMoney);
            ContentValues updateValues = new ContentValues();
            updateValues.put("money", money + changeMoney);
            SqliteManage.getInstance(context).updateItem
                    ("count", "count=?", new String[]{count}, updateValues);
        }
        result.cursor.close();
        result.db.close();
    }
}
