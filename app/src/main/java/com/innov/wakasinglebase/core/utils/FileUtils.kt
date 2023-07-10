package com.innov.wakasinglebase.core.utils

import android.content.res.AssetFileDescriptor
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.os.Build
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException


/**
 * Created by innov Victor on 3/16/2023.
 */
object FileUtils {

    suspend fun extractThumbnailFromUrl(videoUrl: String): Bitmap? {
        return withContext(Dispatchers.IO) {
            try {
                val retriever = MediaMetadataRetriever()

                // Set the data source using the video URL
                val headers = HashMap<String, String>()
                retriever.setDataSource(videoUrl, headers)

                // Retrieve the frame at time 0
                val frame = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
                    retriever.getScaledFrameAtTime(
                        1,
                        MediaMetadataRetriever.OPTION_CLOSEST,
                        1280,
                        720
                    )

                }else{
                    retriever.getFrameAtTime(1)
                }
                // Release the retriever resources
                retriever.release()

                frame
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }
    }

    fun extractThumbnail(assetFileDesc: AssetFileDescriptor?, atTime: Int): Bitmap? {
        var bitmap: Bitmap? = null
        val retriever: MediaMetadataRetriever
        if (assetFileDesc != null && assetFileDesc.fileDescriptor.valid()) try {
            retriever = MediaMetadataRetriever()
            assetFileDesc.apply {
                retriever.setDataSource(
                    fileDescriptor,
                    startOffset,
                    length
                )
            }

            bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
                retriever.getScaledFrameAtTime(
                    atTime.toLong(),
                    MediaMetadataRetriever.OPTION_CLOSEST,
                    1280,
                    720
                )
            } else {
                retriever.getFrameAtTime(atTime.toLong())
            }
            assetFileDesc.close()
            retriever.release()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bitmap
    }
}