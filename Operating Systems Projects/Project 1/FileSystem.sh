#!/bin/bash

cat /dev/null > OUTFILE.txt                                     #initialization
cat /dev/null > HOLDFILE.txt                                    #erase current file content and add to empty file

#sub1
function sub1(){
echo -e "\nDate/Time of Search: $(date +"%b %d %Y at %H:%M:%S")\n" >> OUTFILE.txt       #Insert date and time
}

#sub2
function sub2(){
#Takes from the user the username
echo "Enter username:" 
read username

find /home/$username -size +8M |sort -nk7>> HOLDFILE.txt               #Searching for files greater than 8MB in your home directory
echo -e "\n-----------FileSystem.sh----------"
echo -e "\nSearching for Files Larger Tan 8Mb starting in /home/HomeDir
Please Standby for the Search Results…"                                         #Message

if [ -s HOLDFILE.txt ]                                          #If the file is not empty
then 
echo -e "\nNumber of files found: $(cat HOLDFILE.txt | wc -l)"          #number of lines found in the HOLDFILE.txt
cat HOLDFILE.txt >> OUTFILE.txt                                 #redirect them to OUTFILE.txt
else 
echo -e "\nNo files were found that are larger than 8MB         # If the file is empty, display the message
Exiting…"
fi              #close if statment
}

#sub3
function sub3(){
cat OUTFILE.txt       #reads OUTFILE.txt and displays it on screen
        echo -e "\nThese search results are stored in /home/HomeDir/OUTFILE.txt
Search complete…Exiting…\n"                                                                     #required message
}

#calling sub functions
sub1
sub2
sub3


