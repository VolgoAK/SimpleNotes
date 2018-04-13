package com.volgoak.simplenotes

import android.content.Context
import android.text.Html
import android.text.Spannable
import android.text.Spanned
import java.text.DateFormat
import java.util.*

/**
 * Created by alex on 4/12/18.
 */

object Functions {
    fun timeToLocalDate(time: Long, context: Context): String {
        val date = Date(time)
        val dateFormat: DateFormat = android.text.format.DateFormat.getDateFormat(context)
        return dateFormat.format(date)
    }

    fun htmlToSpannable(text : String) : Spanned {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
        } else {
            return Html.fromHtml(text)
        }
    }
}