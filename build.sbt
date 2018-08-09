name := "spark-DataRepublicTest"

version := "1.0"

scalaVersion := "2.11.8"

val sparkVersion = "2.0.2"
val hadoopVersion = "2.7.3"
val jacksonVersion = "2.8.10"
val slf4jVersion = "1.7.25"

libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-core_2.11" % sparkVersion,
  "org.apache.spark" % "spark-sql_2.11" % sparkVersion,
  "org.apache.spark" % "spark-hive_2.11" % sparkVersion,
  "org.apache.hadoop" % "hadoop-aws" % hadoopVersion,
  "org.apache.hadoop" % "hadoop-client" % hadoopVersion,
  "com.google.guava" % "guava" % "22.0",
  "org.slf4j" % "slf4j-api" % slf4jVersion,
  "org.slf4j" % "slf4j-log4j12" % slf4jVersion,

  "com.amazonaws" % "aws-java-sdk" % "1.10.75.1",
  "net.java.dev.jets3t" % "jets3t" % "0.9.4"
//  "com.crealytics" % "spark-excel_2.11" % "0.9.17"
)

libraryDependencies += "org.specs2" % "specs2-core_2.11"            % "4.0.1" % "test"
libraryDependencies += "org.specs2" % "specs2-junit_2.11"           % "4.0.1" % "test"
libraryDependencies += "org.specs2" % "specs2-scalacheck_2.11"      % "4.0.1" % "test"
libraryDependencies += "org.specs2" % "specs2-matcher-extra_2.11"   % "4.0.1" % "test"
libraryDependencies += "org.specs2" % "specs2-mock_2.11"            % "4.0.1" % "test"

//dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-databind" % jacksonVersion
//dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-annotations" % jacksonVersion

coverageEnabled in Test := true
coverageMinimum := 70
coverageFailOnMinimum := true
parallelExecution in Test := false

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

mainClass in assembly := Some("au.com.nig.dr.run.Main")

scalacOptions in Test ++= Seq("-Yrangepos")
