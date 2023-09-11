#subroutine no. 1
subroutine1(){
	#The below command redirects the output for used disk space and write the details of used hdd in reserve order to a file named Disk_Usage.txt	
	du $HOME -hs > Disk_Usage.txt
	du $HOME | sort -n -r >> Disk_Usage.txt
 
	#Getting the kernel buffer output realted to memory and redirecting it to memo-HDMessages_Log.txt
	dmesg | grep -i memory > memo-HDMessages_Log.txt
	#Getting the kernel buffer output realted to hard disk and appending it to memo-HDMessages_Log.txt
	dmesg | grep -i sd >> memo-HDMessages_Log.txt

	#Collecting CPU information and redirecting it to cpu_info.txt
	lscpu > cpu_info.txt

	#count words in kernel output file only related to memory and hard disk and writing it to Message_Count.txt
	echo "Word count is: $(wc -w memo-HDMessages_Log.txt) " > Message_Count.txt
	 
	# Generating compressed tar for files "Disk_Usage.txt", "cpu_info.txt" and "Message_Count.txt" named per current date
	tar -czvf Phase1.tar.gz ./Disk_Usage.txt ./cpu_info.txt ./Message_Count.txt # c creates new archive 
	file=Phase1.tar.gz 
	#copying compressed tar to a new folder named current date and time
	currdate=$(date +"%H%M%S") 
	mkdir -p "$currdate" && cp "$file" "$currdate"
}

#Getting day and time for naming the directory
currday=$(date +"%y%m%d%H%M%S")

#subroutine no. 2
subroutine2(){	
	#Making directory, finding and copying files from home with read and write permission for owner, copying them to new directory and changing permission to read only for owner
	mkdir -p "$currday" && find $HOME -type f -perm -u+rw -exec cp "{}" "./$currday"  \; && find "./$currday" -type f -exec chmod u=r "{}" \; # "find -perm mode" is the basic command for finding files based on permissions.
}
#subroutine no. 3
subroutine3(){
	#Counting files we copied from home having read permission only for the owner
	numFiles=$(ls "./$currday" | wc -l)
	#Displaying the count of files and end of execution
	echo "number of files is: " $numFiles 
	echo "end of the execution"
}
#running subroutines
subroutine1
subroutine2
subroutine3


