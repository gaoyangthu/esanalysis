#!/bin/bash

dddd=$(date -d "today" +"%Y%m%d");
tttt=$(date -d "today" +"%Y%m%d%H%M%S");

mkdir -p /usr/local/nginx/logs/$dddd/

mv /usr/local/nginx/logs/access.log /usr/local/nginx/logs/$dddd/access_$tttt.log

kill -USR1 `cat /usr/local/nginx/nginx.pid`

sleep 10

ln -s /usr/local/nginx/logs/$dddd/access_$tttt.log /tmp/flume/spooling/access_$tttt.log
