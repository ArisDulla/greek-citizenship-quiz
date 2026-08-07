package com.aris.greekcitizenshipquiz.data.remote.util

import java.io.File
import java.util.zip.ZipInputStream


object ZipExtractor {


    fun extractZip(
        zipFile: File,
        outputDir: File
    ): Map<String, String> {


        val files = mutableMapOf<String, String>()


        ZipInputStream(
            zipFile.inputStream()
        ).use { zipInputStream ->


            var entry = zipInputStream.nextEntry


            while (entry != null) {

                if (!entry.isDirectory) {


                    when {


                        entry.name.endsWith(".json", ignoreCase = true) -> {


                            val content =
                                zipInputStream
                                    .readBytes()
                                    .toString(Charsets.UTF_8)


                            files[entry.name] = content
                        }


                        entry.name.startsWith("images/") &&
                                entry.name.endsWith(".png", ignoreCase = true) -> {

                            val outputFile = File(outputDir, entry.name)

                            val canonicalOutputDir = outputDir.canonicalPath
                            val canonicalOutputFile = outputFile.canonicalPath

                            require(
                                canonicalOutputFile.startsWith(canonicalOutputDir + File.separator)
                            ) {
                                "Invalid ZIP entry: ${entry.name}"
                            }

                            outputFile.parentFile?.mkdirs()

                            outputFile.outputStream().use { output ->
                                zipInputStream.copyTo(output)
                            }
                        }
                    }
                }


                zipInputStream.closeEntry()

                entry = zipInputStream.nextEntry
            }
        }
        return files
    }
}