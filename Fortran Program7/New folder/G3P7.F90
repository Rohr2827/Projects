!1234567

! G3P7, Program5, Jared Rohrbaugh, Caleb Cordis, Andrew Yankovich, Group 3
! CSC 306
! Dr. Pyzdrowski
! Group 3


		PROGRAM G3P7
		Implicit none 
		
		!initializes arrays and other variables
		
		LOGICAL :: quit = .FALSE.
		CHARACTER ::file_name*30
		CHARACTER ::line*80
		INTEGER:: count = 0
		
		CHARACTER :: people(25)*30
		INTEGER :: num(25)
				
		CALL getif(file_name,quit) ! calls all subroutines for the program
		
		if(quit.EQV..FALSE.)then
		CALL getof(file_name,quit)
		end if
		
		if(quit.EQV..FALSE.)then
		CALL readf(people, num, count)
		end if
		
		if(quit.EQV..FALSE.)then
		CALL linked(people, num, count)
		end if
	
		end PROGRAM
		
		
	
		SUBROUTINE getif(file_name,quit) ! gets the in file of names
				
		CHARACTER, INTENT(OUT):: file_name*30
		LOGICAL, INTENT(OUT):: quit
		INTEGER status
		LOGICAL :: exists = .FALSE.
		
		WRITE(*,*)"Please enter an input file name." !asks for the filename
		READ(*,'(A)') file_name
	
		INQUIRE(File = file_name, EXIST = exists) !checks if the filenaem exists
		
		if(exists)then 
			
			OPEN(UNIT=1, FILE=file_name,STATUS='OLD', IOSTAT=status)! opens it if it does 
			WRITE(*,*)'File: ', TRIM(file_name), ' opened.'
			
		else 
		
		do while((exists .EQV. .FALSE.) .AND. (quit .EQV. .FALSE.)) ! if it dosnt ask again until it gets one or they quit
		
			WRITE(*,*)'File: ', TRIM (file_name), ' does not exist.' 
			WRITE(*,*)'Please enter an input file name or QUIT to quit'
			READ(*,'(A)') file_name
			
			INQUIRE(File = file_name, EXIST = exists)
			
			if(exists)then 
			
			OPEN(UNIT=1, FILE=file_name,STATUS='OLD', IOSTAT=status)
			WRITE(*,*)'File: ', TRIM(file_name), ' opened.'
			
			end if
			
			if(file_name .EQ. 'QUIT')then
			
				quit = .TRUE.
				
			end if
			
		end do

		end if	
		
		end SUBROUTINE
		
		
		
		RECURSIVE SUBROUTINE getof(file_name,quit)! gets the outfile
		
		CHARACTER, INTENT(OUT):: file_name*30
		LOGICAL, INTENT(OUT):: quit
		INTEGER status, choice
		LOGICAL :: exists = .FALSE.
		
		WRITE(*,*)"Please enter an output file name."
		READ(*,'(A)') file_name
	
		INQUIRE(File = file_name, EXIST = exists) ! checks if it already exists 
		
		if(exists)then 
			
		WRITE(*,*)'File: ', TRIM (file_name), ' already exists.' ! if it does give them three otions 
		WRITE(*,*)'Would you like to'
		WRITE(*,*)'1 enter a new file name'
		WRITE(*,*)'2 overwrite existing file'
		WRITE(*,*)'3 QUIT'
		
		READ(*,*)choice
		
		SELECT CASE(choice)
		
		case(1)
		
		CALL getof(file_name,quit) ! asks for file again
			
		case(2)
			
		OPEN(UNIT=2, FILE=file_name,STATUS='REPLACE',IOSTAT=status) ! replaces old file
		WRITE(*,*)'File: ', TRIM(file_name), ' opened.'
			
		case(3)
			
		quit = .TRUE. ! quits program
			
		case default
		
		END SELECT
		
		else ! if it dosnt make a new file
					
		OPEN(UNIT=2, FILE=file_name,STATUS='NEW', IOSTAT=status) 
		WRITE(*,*)'File: ', TRIM(file_name), ' opened.'

		end if	
		
		end SUBROUTINE
		
		
		
		SUBROUTINE readf(people, num, count) ! reads the file and checks for good data 
			
		INTEGER ::i = 1, j = 1, status
		CHARACTER, INTENT(OUT):: people(25)*30
		INTEGER, INTENT(OUT):: num(25)
		INTEGER, INTENT(OUT):: count
				
		do i= 1, 25 !loops through the max input 
		
			READ(1,*, IOSTAT = status) people(i) ! checks the status
						
			READ(1,*, IOSTAT = status) num(i) ! checks the status
			
			if(status .EQ. 0)then !if status is good 
				
			count = count+1	
									
			end if

		end do
		
		if(count .EQ. 25)then ! if there are more than 25 inputs 
			
			WRITE(*,*)'You reached 25 inputs every input after ',TRIM(people(25)),' will not be included'
			
		else
		
			WRITE(*,*)count ,' inputs included in the list'
			
		end if	
		END SUBROUTINE
		
		
		
		
		
		
		
		SUBROUTINE linked(people, num, count) ! makes linked list and goes through it 
		
		CHARACTER, INTENT(OUT):: people(25)*30
		INTEGER, INTENT(OUT):: num(25)
		INTEGER, INTENT(OUT):: count
		
		INTEGER movenum, sign
		
  1     FORMAT(' ','New node',I3,' entered with the data ',A10,I3) !format statements 
  2		FORMAT(' ','First node 1 entered with the data ',A10,I3)
  3		FORMAT(' ', 'Starting with',X,A10,X,'with a count of',I3)
  4     FORMAT(' ', 'Node about to be deleted is',X,A10,X,'with a count of',I3)   
  5     FORMAT(' ', "traversed through list, gonna delete ", A10) 
  6     FORMAT(' ', "curp = ", A10, "loop = ", I3)
  7     FORMAT(' ', A10, "is the winner!!! cool!")
			
		
		TYPE::node !initializes the linked list
		
		CHARACTER::people1*30
		INTEGER:: num1
				
		TYPE(node), Pointer:: next, prev
		
		END TYPE
		
		
		TYPE(node), POINTER:: headp, tailp, curp, delp, printp !sets pointers for the linked list
		INTEGER istat
		NULLIFY(headp,tailp)
		
		do i = 1, count !assigns values for the linked list
		
			ALLOCATE(curp, STAT = istat)
			
			!INIT FIRST NODE
			if(istat .EQ. 0)then
			
				NULLIFY(curp%next)
		        NULLIFY(curp%prev)
				
				if(num(i) .NE. 0)then !makes first node
				
				    curp%people1 = people(i)
				    curp%num1 = num(i)
				
				else
				
				    WRITE(*,*) TRIM(people(i)), ' has a count of zero and will not be added'
				
				end if
			
			end if
			
			
			if(ASSOCIATED(headp))then ! checks if there is a first node
			    !init rest of nodes
				tailp%next=> curp !if there is make the next node
				curp%prev => tailp
				tailp=> curp
				
				WRITE(*,1) i,TRIM(curp%people1), curp%num1
				
			else
			    !init first node
				headp=>curp !make head and tail equal the first node
				tailp=>curp
				
				WRITE(*,2)TRIM(headp%people1), headp%num1
			
			end if
			
		end do
		
		WRITE(*,*)'-----------------------------------------------'
		WRITE(*,*)'Data Entry complete'
		WRITE(*,*)'-----------------------------------------------'
		
		
		!start of eliminating data
		
		curp => headp   !START AT THE HEAD
		
        !ASSOCIATED = TRUE means it exists
		do while(ASSOCIATED(curp%next) .or. ASSOCIATED(curp%prev))  !while our neighbors exist
        sign = 0
		
        WRITE(*,3) TRIM(curp%people1), curp%num1
		
        movenum = curp%num1 !this is our moving number
            !********traverse through linked list************
		    do while (movenum .ne. 0)   !while movenum is not null, we will loop through as many times as movenum

		        if(movenum .GT. 0)then
		            sign = 1    !make sign as positive
			        if(ASSOCIATED(curp%next))then   !if curpnext is not null
			        
                        curp => curp%next   !we move to the next
			            movenum = movenum - 1   !decrament our number
			        
			        else    !if it is null
			        
			            curp => headp   !loop to head
			            movenum = movenum - 1   !decrament number
			        
			        end if
			        
		        else if(movenum .LT. 0)then
	                sign = 2    !make sign as negative
		            if(ASSOCIATED(curp%prev))then   !if next is not null
		            
		                curp => curp%prev   !we move to prev
		                movenum = movenum + 1   !incrament   		
		            
		            else
		                curp => tailp
		                movenum = movenum + 1
			            
		            end if
				 end if
				 
            end do
            
	        !************if it's zero, delete it!**********
	        if(ASSOCIATED(curp%prev) .or. ASSOCIATED(curp%next))then    !if we have a node next to us, continue
			
                write(*,4) TRIM(curp%people1), curp%num1 
				
                    !if our next node is null, we are the tail, make prev the new tail
                if(.not. ASSOCIATED(curp%next))then  
                    !write(*,*) "trying to delete the tail" 
                    !nullify(curp%prev%next)
                    tailp=>curp%prev
                    delp=>curp
                    if(sign .eq. 1)then
                        curp=>headp !since we were at the end, loop back!
                    else if(sign .eq. 2)then
                        curp=>tailp
                    endif
                    nullify(tailp%next)
                    
                else if(.not. ASSOCIATED(curp%prev))then   !if our prev node is null, we are the head, loop to tail
                    !write(*,*) "trying to delete the head" 
                    !nullify(curp%next%prev)
                    headp => curp%next
                    delp=>curp
                    if(sign .eq. 1)then
                        curp=>headp !since we were at the end, loop back!
                    else if(sign .eq. 2)then
                        curp=>tailp
                    endif
                    nullify(headp%prev)
                
                else    !if it's normal, loop ourselves around, make curp nextp
                    curp%prev%next => curp%next
	                curp%next%prev => curp%prev
                    delp => curp
                    curp => delp%next
                    if(sign .eq. 1)then
                        curp=>delp%next !since we were at the end, loop back!
                    else if(sign .eq. 2)then
                        curp=>delp%prev
                    endif
                end if

		        deallocate(delp)    !purge pointer marked to die!! >:)

            end if
		end do
        sign = 0
		
		write(*,7) curp%people1
		write(2,7) curp%people1
		
		
		END SUBROUTINE
