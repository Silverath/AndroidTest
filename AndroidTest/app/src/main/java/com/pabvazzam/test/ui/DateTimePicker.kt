package com.pabvazzam.test.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.EditText
import java.util.Calendar

fun showDatePicker(context: Context?, editText: EditText, onDateTimeSelected: (Calendar) -> Unit) {
    val now = Calendar.getInstance()
    context?.let {
        DatePickerDialog(
            it, { _, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)
                showTimePicker(selectedDate, now, context, editText, onDateTimeSelected)
            },
            now.get(Calendar.YEAR),
            now.get(Calendar.MONTH),
            now.get(Calendar.DAY_OF_MONTH)
        )
    }?.show()
}

fun showTimePicker(
    selectedDate: Calendar,
    now: Calendar,
    context: Context?,
    editText: EditText,
    onDateTimeSelected: (Calendar) -> Unit
) {
    context?.let {
        TimePickerDialog(
            it, { _, hours: Int, minutes: Int ->
                selectedDate.set(Calendar.HOUR, hours)
                selectedDate.set(Calendar.MINUTE, minutes)
                editText.clearFocus()
                onDateTimeSelected(selectedDate)
            },
            now.get(Calendar.HOUR),
            now.get(Calendar.MINUTE),
            true
        )
    }?.show()
}