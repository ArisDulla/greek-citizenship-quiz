package com.aris.greekcitizenshipquiz.data.util

import android.content.Context
import com.aris.greekcitizenshipquiz.domain.model.QuestionImage
import java.io.File

fun QuestionImage.toFile(
    context: Context
): File {
    return File(
        context.filesDir,
        "images/$image"
    )
}