package au.com.nig.dr.run

import org.specs2.mock.Mockito
import org.specs2.mutable.Specification

class RunnerUtilTest extends Specification with Mockito {
  "The function nextOptions" should {
    "return the proper arguments when they are rigths" in {
      val args = List("--in-bucket", "TEST", "--inFile", "myFile",
        "--out-bucket", "OUT_TEST", "--outFile", "myOutFile")
      val expectedResult = Map(Symbol("inBucket") -> "TEST",
        Symbol("inFile") -> "myFile",
        Symbol("outBucket") -> "OUT_TEST",
        Symbol("outFile") -> "myOutFile"
      )

      val result = RunnerUtil.nextOption(Map(), args)

      result must_== expectedResult
    }
    "throw an exception if arguments not planned" in {
      val args = List("BOUM", "TEST")

      RunnerUtil.nextOption(Map(), args) must throwA[UnsupportedOperationException]
    }
  }
}
