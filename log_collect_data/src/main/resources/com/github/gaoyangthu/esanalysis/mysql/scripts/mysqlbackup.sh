#!/bin/sh
db_user=portal
db_passwd=portal.gaoyangthu
db_host=localhost

backup_dir=/home/ctpadmin/mysql_backup

time=`date +%Y%m%d`

for db in portal  vms_service
do  
    mysqldump -u $db_user -h $db_host -p$db_passwd $db | gzip -6 > "$backup_dir/$db.$time.sql.gz"
done  
