package com.popularmovies.vpaliy.popularmoviesapp.ui.utils

import android.os.Bundle
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.lang.reflect.Type
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

fun compress(data: String?): ByteArray? {
  return data?.let {
    val bos = ByteArrayOutputStream(data.length)
    try {
      val gzip = GZIPOutputStream(bos)
      gzip.write(data.toByteArray())
      gzip.close()
      val compressed = bos.toByteArray()
      bos.close()
      return compressed
    } catch (ex: IOException) {
      ex.printStackTrace()
    }
    return null
  }
}


fun decompress(compressed: ByteArray?): String? {
  return compressed?.let {
    val bis = ByteArrayInputStream(compressed)
    try {
      val gis = GZIPInputStream(bis)
      val br = BufferedReader(InputStreamReader(gis, "UTF-8"))
      val sb = StringBuilder()
      var line = br.readLine()
      while (line != null) {
        sb.append(line)
        line = br.readLine()
      }
      br.close()
      gis.close()
      bis.close()
      return sb.toString()
    } catch (ex: IOException) {
      ex.printStackTrace()
    }

    return null
  }
}

fun <T> convertFromJsonString(jsonString: String?, type: Type) = jsonString?.let {
  Gson().fromJson<T>(jsonString, type)
}

fun convertToJsonString(`object`: Any?, type: Type) = `object`?.let {
  Gson().toJson(`object`, type)
}

fun <T> Bundle.packHeavyObject(key: String, `object`: T): Bundle {
  val jsonString = convertToJsonString(`object`, object : TypeToken<T>() {}.type)
  val compressed = compress(jsonString)
  putByteArray(key, compressed)
  return this
}

fun <T> Bundle?.fetchHeavyObject(key: String, type: Type): T? {
  if (this == null) return null
  val jsonString = decompress(getByteArray(key))
  return convertFromJsonString<T>(jsonString, type)
}