package com.aris.greekcitizenshipquiz.data.local.util

import android.util.Log
import java.io.File
import java.nio.charset.StandardCharsets
import java.util.zip.ZipInputStream


object ZipExtractor {


    fun extractZip(
        zipFile: File
    ): Map<String, String> {


        val files = mutableMapOf<String, String>()


        ZipInputStream(
            zipFile.inputStream()
        ).use { zipInputStream ->


            var entry = zipInputStream.nextEntry


            while (entry != null) {


                Log.d(
                    "ZIP",
                    "ENTRY = ${entry.name}"
                )


                if (!entry.isDirectory &&
                    entry.name.endsWith(".json", ignoreCase = true)
                ) {


                    val content =
                        zipInputStream
                            .readBytes()
                            .toString(Charsets.UTF_8)


                    files[entry.name] = content
                }


                zipInputStream.closeEntry()


                entry = zipInputStream.nextEntry
            }
        }


        Log.d(
            "ZIP",
            "TOTAL JSON FILES = ${files.size}"
        )


        return files
    }
}