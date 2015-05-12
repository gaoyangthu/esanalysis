#!/bin/bash

ytd=$(date -d "yesterday" +"%Y-%m-%d");
td=$(date -d "today" +"%Y-%m-%d");
backup_dir=/home/ebusiness/nginx-log-backup

for file_name in $(hadoop fs -ls hdfs://bdcluster/esanalysis/nginx/log/)
do
  if [[ $file_name =~ "FlumeData" ]]
  then
    t=${file_name:(-13):8}
    d=`expr $t - 1`
    ddd=$(date -d "1970-01-01 UTC $d"00" seconds" "+%F")
    if [ $ddd = $ytd ]
    then
      hadoop fs -cat $file_name >> "$backup_dir/$ytd"
    fi
  fi
done
hadoop fs -put "$backup_dir/$ytd" hdfs://bdcluster/esanalysis/nginx/log/
gzip "$backup_dir/$ytd"