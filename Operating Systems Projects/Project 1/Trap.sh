#!/bin/bash

#subroutine to ignore signals
function Trap_Sign(){

 trap '' INT TERM #Perform the following function incase of interruption from user for SIGINT & SIGTERM [CTRL+C]; 
 
while true; do 
  sleep 1
 done


}
#function call
Trap_Sign


