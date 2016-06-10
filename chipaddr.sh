#!/bin/bash
config_file="/etc/sysconfig/network-scripts/ifcfg-wlan0"
bak_file=$1
declare -i loop=0
cat $bak_file | while read line
do
	if [ $loop == 0 ];then
        	echo $line>$config_file
	else
		echo $line>>$config_file
	fi
	((loop++))
done

service network restart

echo "over!"
