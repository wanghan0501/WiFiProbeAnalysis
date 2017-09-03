package edu.cs.scu.scalautils

import net.minidev.json.{JSONArray, JSONObject}

import scala.collection.mutable
import scala.collection.JavaConversions.mutableMapAsJavaMap


object ParseMapToJson extends App {
  def map2Json(map : mutable.Map[String,Int]) : String = {
    val jsonString = JSONObject.toJSONString(map)
    jsonString
  }

  def map2Json2(map : mutable.Map[String,Long]) : String = {
    val jsonString = JSONObject.toJSONString(map)
    jsonString
  }

  def map2JsonList(list: List[(String, Int)]): String = {
    var map: mutable.Map[String, Int] = mutable.Map()
    for(item <- list){
      map +=(item._1 -> item._2)
    }
    map2Json(map)
  }
}

