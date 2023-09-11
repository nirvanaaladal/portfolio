#first subroutine
Subroutine_1() {
	 apt-get upgrade & apt-get update #to update and upgrade
}

#second subroutine
Subroutine_2(){
	echo "\n\nThe status of the firewall"
	ufw status #Check default status of the firewall
	ufw default deny incoming # Deny incoming traffic
	ufw allow OpenSSH #Allow incoming SSH connection through port 22
	echo "\nEnabling the firewall"
	ufw enable #Enable and start the firewall
	echo "\n\n"
	ufw status verbose #Checked status after the changes
}

Subroutine_1
Subroutine_2
