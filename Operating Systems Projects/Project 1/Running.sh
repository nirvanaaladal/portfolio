#!/bin/bash
#first subroutine
function firstsub(){

#Takes from the user the username
echo "Enter user name:" 
read username
#display a welcome username
echo "Welcome $username"

echo "******************************************************"
#Message
echo -e "$username is currently logged in the linux system\n" 
#echo -e to use escaped characters

Day=$(date +%a) #creating variable Day that shows the weekday name
day=$(date +%d) #creating variable day that shows the day of month
mon=$(date +%b) #creating variable mon that shows the abbreviated month name
year=$(date +%Y) #creating variable year that shows the year
time=$(date +%X) #creating variable time that shows the time representation
zone=$(date +%Z) #creating variable zone that shows the time zone 

boottime=$(who -b) #creating variable  holds time of last system boot
idletime=$(who -u) #creating variable idletime holds idle time
size=$(du -sk $HOME) #creating variable size that estimates file space storage  

#Display Current day in a calendar format
echo "The current month calendar "
cal

#Display the current time using variable reference
echo -e "The time on the linux system is $Day $day $mon $year $time $zone \n "

#Display System boot time and idle time using variable reference
echo  -e "$boottime and system idle time $idletime \n"

#Working path and Shell type
echo "The current working path is $(pwd)"
echo "The current working shell is $SHELL"
#the directory size in KB in your Home
echo "My home directory is $HOME with the directory size in my home = $size KB " 
#Message briefly describing the requited tasks
echo "The purpose of the script: "
echo "The required tasks are given in order to log and track the activity of the users by displaying and saving their information such as date, time, boot time, idle time, working path, shell type, home directory and its size. "

echo "******************************************************"
echo -e "Waiting for results...\n"
}



#Second subroutine

	#Second subroutine
function secondsub(){
	#runs indefinitely
	while true 
	do
		#checking constantly to avoid intruptions
		trap ./Trap.sh SIGTERM 
		trap ./Trap.sh SIGINT
		#Get current system time
		currenttime=$(date +%H:%M)
		#checking if time is equal to 11:59
		if [[ "$currenttime" == "11:59" ]]; then
			#Sets off the first two scripts
			./ControlTraffic.sh
			./Connectivity.sh
			#Waiting for 10 minutes for call the other two scripts
			sleep 10s
			#run remaining scripts
			./FileSystem.sh
			./Performance.sh		
		fi
	done
	#end of loop
}



#call the first soubroutine
firstsub
#call the second soubroutine
secondsub



