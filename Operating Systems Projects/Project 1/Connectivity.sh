#!/bin/bash

#subroutine
sub(){
if ping -q -w1 -c1 google.com &>/dev/null #check if the system is online
then echo online #display online
#Ping the gateway with 10 packets each packet is 1KB and save it in /tmp/PINGRESULTS.TXT
ping -c10 -s1024 $(ip route show default | grep default |awk {'print $3'})>/tmp/PINGRESULTS.TXT
nslookup qu.edu.qa >>/tmp/PINGRESULTS.TXT #Query the Domain Name System to qu.edu.qa.

else echo offline #if we can not ping google, It is offline
ping -c 1 0 > /var/tmp/NETINFO.TXT #Ping local machine



echo "nameserver 8.8.8.8" | sudo tee /etc/resolv.conf 2> /dev/null >> /var/tmp/NETINFO.TXT
sudo apt-get install net-tools >> /var/tmp/NETINFO.TXT
ifconfig -a >> /var/tmp/NETINFO.TXT #display IP configuration
route -n >> /var/tmp/NETINFO.TXT #display Routing Table and save all work in /tmp/NETINFO.TXT
sleep 15; reboot #reboot the system after 15 seconds.
fi
}

#calling sub functions
sub
