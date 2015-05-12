#!/bin/sh

db_user=root
db_passwd=bigdata_mysql
db_host=localhost
db=portal_core

backup_dir=/home/ebusiness/mysql-backup

time=`date +%Y%m%d`

cd /home/ebusiness/mysql-backup/

wget http://localhost/sqlback/portal.$time.sql.gz

gunzip < $backup_dir/portal.$time.sql.gz | mysql -u $db_user -h $db_host -p$db_passwd $db