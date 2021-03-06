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

package com.example.utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


object DateUtils {

  val dateFormat: String
    get() = "yyyy-MM-dd"// HH:mm:ss

  val timeFormat: String
    get() = "ss"// HH:mm:ss

  fun getFarDay(far: Int): String {
    val date = Date()
    val cal = Calendar.getInstance()
    cal.time = date
//    System.err.println("getFarDay time --> " +date)
    cal.add(Calendar.DAY_OF_MONTH, far)
    val sdf = SimpleDateFormat(DateUtils.dateFormat)
    val currentDateandTime = sdf.format(cal.time)
//    System.err.println("getFarDay --> " +currentDateandTime)
    return currentDateandTime
  }

  fun getDateDay(date: String, dateType: String): Int {
    try {
      val dateFormat = SimpleDateFormat(dateType)
      val nDate = dateFormat.parse(date)
      val cal = Calendar.getInstance()
//      System.err.println("getDateDay --> nDate="+nDate)
      cal.time = nDate
//      System.err.println("getDateDay --> cal.time="+cal.time)
//      System.err.println("getDateDay --> cal.get(Calendar.DAY_OF_WEEK) = "+(cal.get(Calendar.DAY_OF_WEEK)-2))
      return cal.get(Calendar.DAY_OF_WEEK) - 1
    } catch (e: ParseException) {
      e.printStackTrace()
    }
    return -1
  }

  @SuppressLint("SimpleDateFormat")
  fun getTime(): String? {
    val cal = Calendar.getInstance()
    val sdf = SimpleDateFormat(DateUtils.timeFormat)
    return sdf.format(cal.time)
  }

  fun getDayofWeek(data: String, dateType: String): Int {
    try {
      val dateFormat = SimpleDateFormat(dateType)
      val nDate = dateFormat.parse(data)
      val c = Calendar.getInstance()
      c.time = nDate
      val dayOfWeek = c.get(Calendar.DAY_OF_WEEK)
      return dayOfWeek
    } catch (e: ParseException) {
      e.printStackTrace()
    }

    return -1
  }

  fun getDayNameList(days: String): String {
    val builder = StringBuilder()
    if (days.contains("0"))
      builder.append("????")
    if (days.contains("1"))
      builder.append("????")
    if (days.contains("2"))
      builder.append("????")
    if (days.contains("3"))
      builder.append("????")
    if (days.contains("4"))
      builder.append("????")
    if (days.contains("5"))
      builder.append("????")
    if (days.contains("6"))
      builder.append("????")
    return builder.toString()
  }

  fun getIndexOfDayName(index: Int): String {
    val dname: String
    when (index) {
      1 -> dname = "??????????????????????"
      2 -> dname = "??????????????"
      3 -> dname = "??????????"
      4 -> dname = "??????????????"
      5 -> dname = "??????????????"
      6 -> dname = "??????????????"
      else -> dname = "??????????????????????"
    }
    return dname
  }

  fun getIndexofDayNameHead(index: Int): String {
    var dayName = " (????)"
    when (index) {
      1 -> dayName = " (????)"
      2 -> dayName = " (????)"
      3 -> dayName = " (????)"
      4 -> dayName = " (????)"
      5 -> dayName = " (????)"
      6 -> dayName = " (????)"
      7 -> dayName = " (????)"
    }
    return dayName
  }
}
