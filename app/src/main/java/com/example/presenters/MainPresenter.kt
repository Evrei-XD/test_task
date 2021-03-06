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

package com.example.presenters

import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager

import com.example.WDApplication
import com.example.compose.BasePresenter
import com.example.persistence.sqlite.SqliteManager
import com.example.viewType.MainActivityView

import javax.inject.Inject


class MainPresenter : BasePresenter<MainActivityView>() {

  @Inject
  lateinit var sqliteManager: SqliteManager

  override fun onCreate(context: Context, savedInstanceState: Bundle?) {
    super.onCreate(context, savedInstanceState)
    WDApplication.component.inject(this)
  }

  fun addRecord(value: String) = sqliteManager.addRecord(1, value,0,0,"")
}
