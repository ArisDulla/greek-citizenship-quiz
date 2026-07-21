package com.aris.greekcitizenshipquiz.data.local.util

import java.io.File
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


                if (!entry.isDirectory) {


                    val content = zipInputStream
                        .bufferedReader()
                        .use { reader ->
                            reader.readText()
                        }


                    files[entry.name] = content
                }


                zipInputStream.closeEntry()


                entry = zipInputStream.nextEntry
            }
        }


        return files
    }
}