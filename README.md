To run it locally, the following parameters are necessary:

```--in-bucket "" --inFile "/PATH/TO/REPO/spark-DataRepTest/complete-dataset.csv" --out-bucket "" --outFile "/PATH/TO/REPO/spark-DataRepTest/test.json"```

Main class: ```au.com.nig.dr.run.Run```

To run it on AWS EMR, spin up a cluster EMR 5.2.1 (due to version issues that i didn't have time to fix yet) then run with the following commands :

Jar location: ```command-runner.jar```

```spark-submit --deploy-mode cluster --executor-memory 4g --driver-memory 6g --class au.com.nig.dr.run.Run s3://PATH/Of/THE/JAR.jar --in-bucket s3://BUCKET/PATH --inFile complete-dataset.csv --out-bucket s3://BUCKET/PATH --outFile test.json```

There is still an issue: Job will have status fail even though it ran and succeeded. Check s3 folder where the json is supposed to have been written and you should find the result.