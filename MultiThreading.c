#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <time.h>
#define MIN 1923456000
#define MAX 1923457200
#define NumThread  3

pthread_mutex_t the_mutex; 
pthread_cond_t condc, condp;
unsigned long long buffer = 0;
unsigned long long buffer2 = 0;

int IsPrime(unsigned long long n)// prime number function
{
	unsigned long long k, limit;
	
	if( n== 2 )	//checks if your number is = to 2
		return 1;
	if( n%2 == 0 ) // checks if the modulus of your number is zero so not prime
		return 0;
	limit = n/2;
	for(k=3; k<=limit ; k+=2) //loops through all possible numbers 
		if( n%k ==0 )	// if = 0 not prime
			return 0;
	return 1;
}

void *producer(void *ptr) // producer
{
	int i;

	for(i = MIN; i<= MAX; i++) // 1923456000, 1923457200 goes through every number
	{
		pthread_mutex_lock(&the_mutex);
		while(buffer != 0 && buffer2 != 0) pthread_cond_wait(&condp, &the_mutex); //loops while the buffer is not 0
		
		if(buffer == 0) 
		{
			buffer = i;
		}
		
		else if(buffer2  == 0)
		{
			buffer2 = i;
		}
	
		
		pthread_cond_signal(&condc); // unlocks the consumer
		pthread_mutex_unlock(&the_mutex);//unlocks the mutex
		
	} 
	
	for(i = 1; i<= NumThread; i++) // while the number is <= how many threads there are
	{
		pthread_mutex_lock(&the_mutex); // locks the mutex
		while(buffer != 0) pthread_cond_wait(&condp, &the_mutex);// makes the consumer and mutex wait
		buffer = -1;
		pthread_cond_signal(&condc);  // unlocks the consumer
		pthread_mutex_unlock(&the_mutex); //unlocks the mutex	
	}
	
	pthread_exit(0); //exits the thread
}



void * consumer(void *ptr) // consumer
{
	unsigned long long number = 0;

	while(number != -1) // conditional loop 
	{
		
		pthread_mutex_lock(&the_mutex);// locks the mutex
		while(buffer == 0) pthread_cond_wait(&condc, &the_mutex);// wait 
		
		if(buffer != 0)
		{
			number = buffer;
			buffer =0; //empties the buffer
		}
		
		else if(buffer2  != 0)
		{
			number = buffer2;
			buffer2 =0; //empties the buffer
		}
		
		pthread_cond_signal(&condp); //unlocks the consumer
		pthread_mutex_unlock(&the_mutex); //unlocks the mutex
		
		if(number != -1)
		{
			if(IsPrime(number) == 1) // prints prime numbers
			{
				printf("Prime %d\n",number);
			}
		}	
	}
	
	pthread_exit(0);
}


int main()
{
	float totalTime; //used to store time of the program
	int i;
	int ptr;// thread number that gets passed
	
	pthread_t pro, con[NumThread];//initializes 8 con threads
	
	pthread_mutex_init(&the_mutex, 0);
	pthread_cond_init(&condc,0);
	pthread_cond_init(&condp,0);
	
	pthread_create(&pro, 0, producer, 0);
	
	for(ptr = 0; ptr < NumThread; ptr++) //creates 8 con threads
	{	
	
		sleep(1);	
		pthread_create(&con[i], 0,consumer,&ptr);
			
	}
		
	
	printf("\nPROCESSING\n\n");
    time_t startTime = time(NULL); //start time
    
    pthread_join(pro,0);
    
    for(i = 0; i < NumThread; i++)// //iterates through 8 con threads
	{
		pthread_join(con[i],0);
	}
		
	time_t endTime = time(NULL); //ends time
    printf("\nCOMPLETE\n");
    
    totalTime = difftime(endTime, startTime);// total time
    printf("Run Time: %f sec\n", totalTime);
	
	pthread_cond_destroy(&condc);
	pthread_mutex_destroy(&the_mutex);
	
	return 0;
}
