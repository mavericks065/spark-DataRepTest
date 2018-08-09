[![build status](https://gitlab.ccoe.ampaws.com.au/EDI/spark-commons/badges/master/build.svg)](https://gitlab.ccoe.ampaws.com.au/EDI/spark-commons/commits/master)
[![coverage report](https://gitlab.ccoe.ampaws.com.au/EDI/spark-commons/badges/master/coverage.svg)](https://gitlab.ccoe.ampaws.com.au/EDI/spark-commons/commits/master)

## LADR

Spark 2.0.2 supports multiline reading of files over multiple partitions while Spark 2.1+ does not. 
We overcame the issue by implementing a per table multiLine parameter. Setting this parameter to true will read the table
over one partition which is less efficient. 

Spark 2.2.1 is part of EMR 5.11.1, it seems to have a few problems working properly with ORC/Parquet and S3, the problem 
might directly originate from S3A. 

The framework currently supports reading from S3 and pushing to RDS/S3/Hive/Athena. For BI purposes while Hive + Hue 
as an interface is suitable, we chose to move quickly to Athena + Superset (maybe Quicksight) because it's not cost 
efficient to run cluster 24/7 just to have an Hive interface. Furthermore maintaining and securing data accesses to EMR 
cluster is cumbersome and difficult to maintain as you evolve version.

S3A is still not officially supported by EMR and it will probably remain that way until they move from Hadoop 2.7.3 
which has been part of their stack for a while although significant S3FileSystem fixes have been published as part of 
Hadoop 2.8.2 
