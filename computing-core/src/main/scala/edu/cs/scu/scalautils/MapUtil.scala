package edu.cs.scu.scalautils

import scala.collection.mutable

object MapUtil {
  def addMapListItem(map: mutable.Map[String, Int], list: List[(String, Int)]): mutable.Map[String, Int]={
    for(item <- list){
      if(map.contains(item._1)){
        map.update(item._1, map(item._1) + item._2)
      }else{
        map +=(item._1 ->item._2)
      }
    }
    map
  }
  def addMapListItemLong(map: mutable.Map[String, Long], list: List[(String, Long)]): mutable.Map[String, Long]={
    for(item <- list){
      if(map.contains(item._1)){
        map.update(item._1, map(item._1) + item._2)
      }else{
        map +=(item._1 ->item._2)
      }
    }
    map
  }

  def addMapItem(tmp: mutable.Map[String, Int], map:mutable.Map[String, Int]): mutable.Map[String, Int] = {
    for(item <- map){
      if(tmp.contains(item._1)){
        tmp.update(item._1, tmp(item._1) + item._2)
      }
      else{
        tmp +=(item._1 -> item._2)
      }
    }
    tmp
  }

  def getMax10(map: mutable.Map[String, Int]): List[(String, Int)] = {
    map.toList.sortWith(_._2 > _._2).take(10)
  }

  def getMax(map: mutable.Map[String, Int]): List[(String, Int)] = {
    map.toList.sortWith(_._2 > _._2).take(1)
  }
}
