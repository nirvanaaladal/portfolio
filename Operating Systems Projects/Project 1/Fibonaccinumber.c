#include <stdio.h>
#include <time.h>	//library for clock()
#include <stdlib.h>	//library for atoi()

// This program designed to compute the Fibonacci number of the passed argument.
int fibnum(int n)
{
   if (n<=1)			
      return n;		
   return fibnum(n-1) + fibnum(n-2);
}


int main(int argc, char *argv[]) //argc is the number of arguments, and argv is an array of strings containing the arguments.
{
    clock_t time_start,time_end;
    time_start = clock(); 						     //assignment of time when the function start
    char *num = argv[1];						     //pass the number to first aegc
    int n =atoi(num);							     //function to convert string to integer
    printf("The Fibonacci number of %d is: %d ",n,fibnum(n));	
    time_end = clock();						    //assignment of time when the function finish execution 
    double timetaken = ((double)(time_end - time_start))/CLOCKS_PER_SEC; //Calculate the elapsed time in seconds 
    printf("\nThe function took %f seconds to execute\n", timetaken);
    return 0;
}
