package com.innov.wakasinglebase.core.utils

import android.content.Context

import android.database.Cursor
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.documentfile.provider.DocumentFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Random


/**
 * Created by innov Victor on 3/16/2023.
 */
object FileUtils {

    fun saveBitmapToTempFile(context: Context, bitmap: Bitmap, filename: String): File? {
        val cacheDir = context.cacheDir
        val file = File(cacheDir, filename)

        try {
            val outputStream = FileOutputStream(file)
            // Use the compress method to save the Bitmap to the OutputStream
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.close()
            return file
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    fun Context.getFilePath(uri: Uri): String? {
        var cursor: Cursor? = null
        val projection = arrayOf(MediaStore.Video.VideoColumns.DATA)
        try {
            cursor = contentResolver.query(
                uri, projection, null, null,
                null
            )
            if (cursor != null && cursor.moveToFirst()) {
                val index = cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DATA)
                var tt=cursor.getString(index)
                Log.e("Example","$tt")
                return cursor.getString(index)
            }
        } finally {
            cursor?.close()
        }
        return null
    }


    private  fun getRealPathFromUri(context: Context, uri: Uri): String? {
        val projection = arrayOf(MediaStore.Video.Media.DATA, MediaStore.Video.Media.DISPLAY_NAME)
        val cursor = context.contentResolver.query(uri, projection, null, emptyArray(), null)

        cursor?.use {
            val dataIndex = it.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            val displayNameIndex = it.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
            if (it.moveToFirst()) {
                val displayName = it.getString(displayNameIndex)
                val path = it.getString(dataIndex)
                // In some cases, the display name may contain spaces, which can cause issues with the path, so let's replace them
                return path?.replace(displayName, "")
            }
        }
        return null
    }

    fun uriToFile(context: Context, uri: Uri): File? {
        Log.e("Example","$uri ok")
        val path = getRealPathFromUri(context, uri)
        Log.e("Example","$path ok")
        return path?.let { File(it) }
    }

    fun uriToFile2(context: Context, uri: Uri): File? {
        val documentFile = DocumentFile.fromSingleUri(context, uri)
        return documentFile?.let {
            val file = File(context.cacheDir, documentFile.name ?: "video.mp4")
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                file.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            file
        }
    }







    suspend fun extractThumbnailFromUri(context: Context,videoUrl: Uri): Bitmap? {
        return withContext(Dispatchers.IO) {
            try {
                val retriever = MediaMetadataRetriever()

                // Set the data source using the video URL
                val headers = HashMap<String, String>()
               // retriever.setDataSource()
               // retriever.setDataSource(videoUrl, headers)

                retriever.setDataSource(context,videoUrl)

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

//    fun extractThumbnail(assetFileDesc: AssetFileDescriptor?, atTime: Int): Bitmap? {
//        var bitmap: Bitmap? = null
//        val retriever: MediaMetadataRetriever
//        if (assetFileDesc != null && assetFileDesc.fileDescriptor.valid()) try {
//            retriever = MediaMetadataRetriever()
//            assetFileDesc.apply {
//                retriever.setDataSource(
//                    fileDescriptor,
//                    startOffset,
//                    length
//                )
//            }
//
//            bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
//                retriever.getScaledFrameAtTime(
//                    atTime.toLong(),
//                    MediaMetadataRetriever.OPTION_CLOSEST,
//                    1280,
//                    720
//                )
//            } else {
//                retriever.getFrameAtTime(atTime.toLong())
//            }
//            assetFileDesc.close()
//            retriever.release()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        return bitmap
//    }

      fun getFileNameFromUri(context: Context, uri: Uri):String?{
        var fileName:String?=null
        val projection= arrayOf(MediaStore.MediaColumns.DISPLAY_NAME)
        val cursor: Cursor?=context.contentResolver.query(uri,projection,null,null,null)

        cursor?.use {
            if(it.moveToFirst()){
                val columnIndex:Int=it.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME)
                if(columnIndex!=-1){
                    fileName=it.getString(columnIndex)
                }
            }
        }
        return fileName
    }

    fun generateFileNameString():String{
        val currentTimeMillis=System.currentTimeMillis()
        val random= Random(currentTimeMillis)
        val charPool:List<Char> = ('a'..'z')+('A'..'Z')+('0'..'9')
        val randomString=(1..32)
            .map{
                random.nextInt(charPool.size)

            }.map (
                charPool::get
            ).joinToString ("")
        return  randomString
    }

    fun getVideoInfo(context: Context, videoUri: Uri): VideoInfo? {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(context, videoUri)

        val duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        val width = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)
        val height = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)
        val size =retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE)

        retriever.release()

        if (duration != null && width != null && height != null) {
            return VideoInfo(
                duration = duration.toLong(),
                width = width.toInt(),
                height = height.toInt(),
                size=size?.toInt()
            )
        }

        return null
    }
}

data class VideoInfo(
    val duration: Long,
    val width: Int,
    val height: Int,
    val size: Int?
)