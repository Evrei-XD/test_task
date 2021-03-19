/*Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.persistence.sqlite

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class SqliteManager(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {

  override fun onCreate(db: SQLiteDatabase) {
    val CREATE_TABLE_RECORD = "CREATE TABLE $TABLE_RECORD(pk_recordid integer primary key autoincrement, recorddate DATETIME DEFAULT (datetime('now','localtime')), type_event integer, amount varchar(4), carbs integer, bg_sugar_in_blood integer, note varchar(500));"
    db.execSQL(CREATE_TABLE_RECORD)

    val CREATE_TABLE_ALARM = "CREATE TABLE $TABLE_ALARM(requestcode integer primary key, daylist varchar(20), starttime varchar(20), endtime varchar(20), interval integer);"
    db.execSQL(CREATE_TABLE_ALARM)

    val CREATE_TABLE_CAPACITY = "CREATE TABLE $TABLE_CAPACITY(capacity real primary key)"
    db.execSQL(CREATE_TABLE_CAPACITY)

    val CREATE_TABLE_BASAL = "CREATE TABLE $TABLE_BASAL(basal real primary key)"
    db.execSQL(CREATE_TABLE_BASAL)

    val CREATE_TABLE_LOG = "CREATE TABLE $TABLE_LOG(recordid integer primary key autoin" +
            "crement, recorddate DATETIME DEFAULT (datetime('now','localtime')), type_event integer, note varchar(500));"
    db.execSQL(CREATE_TABLE_LOG)
  }

  override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    db.execSQL("DROP TABLE IF EXISTS $TABLE_RECORD")
    db.execSQL("DROP TABLE IF EXISTS $TABLE_ALARM")
    db.execSQL("DROP TABLE IF EXISTS $TABLE_CAPACITY")
    db.execSQL("DROP TABLE IF EXISTS $TABLE_BASAL")
    db.execSQL("DROP TABLE IF EXISTS $TABLE_LOG")
    onCreate(db)
  }


  @SuppressLint("Recycle")
  fun getDayDrinkAmount(datetime: String): Int {
    var totalAmount = 0
    val cursor = readableDatabase.rawQuery("select * from " + TABLE_RECORD + " where recorddate >= datetime(date('" + datetime + "','localtime')) " +
        "and recorddate < datetime(date('" + datetime + "', 'localtime', '+1 day'))", null)
    if (cursor != null && cursor.count > 0 && cursor.moveToFirst()) {
      do {
        totalAmount += cursor.getInt(3)
      } while (cursor.moveToNext())
    }
    cursor.close()
    return totalAmount
  }
  fun addRecord(typeEvent: Int, amount: String, carbs: Int, bg_sugar_in_blood: Int, note: String) {
    //                                                имя столбца                                                  записываемое в него значение
    //                                                ↓                                                            ↓
    val query_addRecord = "Insert Into $TABLE_RECORD (type_event, amount, carbs, bg_sugar_in_blood, note) Values('$typeEvent','$amount','$carbs','$bg_sugar_in_blood','$note');"//запрос на добавление строки
    writableDatabase.execSQL(query_addRecord)
  }
  fun deleteRecord(index: Int) {
    val query_deliteRecord = "Delete from $TABLE_RECORD Where pk_recordid = '$index'"//запрос удаление строки
    writableDatabase.execSQL(query_deliteRecord)
  }

  @Synchronized
  override fun close() {
    super.close()
  }

  companion object {
    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "insulin_pump.db"//waterdays   insulin_pump

    private const val TABLE_RECORD = "RecordList"
    private const val TABLE_ALARM = "AlarmList"
    private const val TABLE_CAPACITY = "capacityList"
    private const val TABLE_BASAL = "basalList"
    private const val TABLE_LOG = "logList"
  }
}
