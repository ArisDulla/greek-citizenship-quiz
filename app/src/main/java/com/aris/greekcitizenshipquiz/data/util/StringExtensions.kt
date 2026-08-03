package com.aris.greekcitizenshipquiz.data.util

fun String?.isValidText(): Boolean {

    return !this.isNullOrBlank() &&
            !this.equals("None", ignoreCase = true)

}