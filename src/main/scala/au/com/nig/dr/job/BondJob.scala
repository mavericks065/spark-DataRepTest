package au.com.nig.dr.job

import java.sql.Date
import java.text.SimpleDateFormat

import au.com.nig.dr.model.BondRecord
import org.apache.spark.sql.{Dataset, Row, SparkSession}
import org.slf4j.LoggerFactory

class BondJob {

  private val logger = LoggerFactory.getLogger(this.getClass)

  private val transformToBondRecord: _root_.org.apache.spark.sql.Row => BondRecord = row => {
    implicit val r: Row = row
    val formatter = new SimpleDateFormat("dd/MM/yyyy")
    val startTenancyStr = row.getAs[String]("DateTenancyCommenced")
    val lodgementStr = row.getAs[String]("DateLodgement")

    val startTenancyDate = if (startTenancyStr != null && !startTenancyStr.isEmpty && !startTenancyStr.equals("NULL"))
      new Date(formatter.parse(startTenancyStr).getTime)
    else
      null
    val lodgementDate = if (lodgementStr != null && !lodgementStr.isEmpty && !lodgementStr.equals("NULL"))
      new Date(formatter.parse(lodgementStr).getTime)
    else
      null

    BondRecord(
      startTenancyDate,
      lodgementDate,
      row.getAs[String]("BondAmount"),
      row.getAs[String]("PremisesWeeklyRent"),
      row.getAs[String]("PremisesDwellingType"),
      row.getAs[String]("NumberBedrooms"),
      row.getAs[String]("Premises_Postcode"),
      row.getAs[String]("Premises_Suburb")
    )
  }

  def execute(session: SparkSession): Dataset[BondRecord] = {
    import session.implicits._

    logger.info("execute RESIDENTIAL_BOND")

    val df = session.table("RESIDENTIAL_BOND")
      .na.replace(Seq("BondAmount", "PremisesWeeklyRent", "PremisesDwellingType", "NumberBedrooms"), Map("" -> "", "NULL" -> ""))
    val residentialBondDf = df
      .na.replace("NumberBedrooms", Map("" -> "0", "NULL" -> "0"))

    residentialBondDf.map(transformToBondRecord)
  }
}
