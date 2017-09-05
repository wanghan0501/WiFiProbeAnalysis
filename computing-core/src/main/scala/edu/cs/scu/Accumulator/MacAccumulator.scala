package edu.cs.scu.Accumulator
import org.apache.spark.util.AccumulatorV2

import scala.collection.mutable
class MacAccumulator extends AccumulatorV2[String, mutable.Map[String, Int]]{
  private var macMap: mutable.Map[String, Int] = mutable.Map()
  override def isZero: Boolean = macMap.isEmpty

  override def copy(): AccumulatorV2[String, mutable.Map[String, Int]] = MacAccumulator.this

  override def reset(): Unit = macMap.clear()

  override def add(v: String): Unit = {
    if(macMap.contains(v)){
      macMap.update(v, macMap(v) + 1)
    }else{
      macMap +=(v -> 1)
    }
  }

  override def merge(other: AccumulatorV2[String, mutable.Map[String, Int]]): Unit = {
    this.value ++=other.value
  }

  override def value: mutable.Map[String, Int] = macMap
}
