package au.com.nig.dr.run

import au.com.nig.dr.job.BondJob
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.slf4j.LoggerFactory

object Run {
  private val logger = LoggerFactory.getLogger(this.getClass)

  def main(args: Array[String]): Unit = {
    val options = RunnerUtil.nextOption(Map(), args.toList)
    val s3InBucket = options('inBucket)
    val s3InFile = options('inFile)
    val s3OutBucket = options('outBucket)
    val s3OutFile = options('outFile)

    val inputFile = if (s3InBucket != null && !s3InBucket.isEmpty)
      s"$s3InBucket/$s3InFile"
    else
      s3InFile
    val outputFile = if (s3OutBucket != null && !s3OutBucket.isEmpty)
      s"$s3OutBucket/$s3OutFile"
    else
      s3OutFile

    logger.info("ruuuuuuuuuun")

    val sparkSession =
      SparkSession
        .builder()
        .appName("Test-Data-Republic")
        .master("local[2]")
        .getOrCreate()

    sparkSession.read
      .option("header", "true")
      .option("delimiter", ",")
      .csv(inputFile).createOrReplaceTempView("RESIDENTIAL_BOND")

    val sparkJob = new BondJob

    val resultDS = sparkJob.execute(sparkSession)

    resultDS.write
      .mode(SaveMode.Overwrite)
      .json(outputFile)

    sparkSession.stop()
  }
}

object RunnerUtil {

  private val logger = LoggerFactory.getLogger(RunnerUtil.getClass)
  private[run] type OptionMap = Map[Symbol, String]

  def nextOption(map: OptionMap, list: List[String]): OptionMap = {
    list match {
      case Nil => map
      case "--in-bucket" :: value :: tail =>
        nextOption(map ++ Map('inBucket -> value.toString), tail)
      case "--inFile" :: value :: tail =>
        nextOption(map ++ Map('inFile -> value.toString), tail)
      case "--out-bucket" :: value :: tail =>
        nextOption(map ++ Map('outBucket -> value.toString), tail)
      case "--outFile" :: value :: tail =>
        nextOption(map ++ Map('outFile -> value.toString), tail)
      case option :: _ => logger.info("Unknown option " + option)
        throw new UnsupportedOperationException("Argument is not supported")
    }
  }
}