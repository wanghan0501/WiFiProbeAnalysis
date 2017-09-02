package edu.cs.scu.scalautils
import java.text.SimpleDateFormat
import java.util.Calendar

object GetDate {

  def getYesterday:String = {
      val  dateFormat:SimpleDateFormat = new SimpleDateFormat("yyyyMMdd")
      val cal:Calendar=Calendar.getInstance()
      cal.add(Calendar.DATE,-1)
      val yesterday=dateFormat.format(cal.getTime())
      yesterday
  }

  def getDateTest: String = "20170808"


}
