!1234567

! G3P7, Program5, Jared Rohrbaugh, Caleb Cordis, Andrew Yankovich, Group 3
! CSC 306
! Dr. Pyzdrowski
! Group 3


		PROGRAM G3P7
		Implicit none 
		
		LOGICAL :: quit = .FALSE.
		CHARACTER ::file_name*30
		CHARACTER ::line*80
		INTEGER:: count = 0
		
		CHARACTER :: people(25)*30
		INTEGER :: num(25)
				
		CALL getif(file_name,quit)
		
		if(quit.EQV..FALSE.)then
		CALL getof(file_name,quit)
		end if
		
		if(quit.EQV..FALSE.)then
		CALL readf(people, num, count)
		end if
		
		if(quit.EQV..FALSE.)then
		CALL linked(people, num, count)
		end if
		
!		if(quit.EQV..FALSE.)then
!		CALL printf(quit,mix,max,miy,may,stepx,stepy,xa,ya,xya)
!		end if	
	
		end PROGRAM
		
		
	
		SUBROUTINE getif(file_name,quit)
				
		CHARACTER, INTENT(OUT):: file_name*30
		LOGICAL, INTENT(OUT):: quit
		INTEGER status
		LOGICAL :: exists = .FALSE.
		
		WRITE(*,*)"Please enter an input file name."
		READ(*,'(A)') file_name
	
		INQUIRE(File = file_name, EXIST = exists)
		
		if(exists)then 
			
			OPEN(UNIT=1, FILE=file_name,STATUS='OLD', IOSTAT=status)
			WRITE(*,*)'File: ', TRIM(file_name), ' opened.'
			
		else 
		
		do while((exists .EQV. .FALSE.) .AND. (quit .EQV. .FALSE.))
		
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
		
		
		
		RECURSIVE SUBROUTINE getof(file_name,quit)
		
		CHARACTER, INTENT(OUT):: file_name*30
		LOGICAL, INTENT(OUT):: quit
		INTEGER status, choice
		LOGICAL :: exists = .FALSE.
		
		WRITE(*,*)"Please enter an output file name."
		READ(*,'(A)') file_name
	
		INQUIRE(File = file_name, EXIST = exists)
		
		if(exists)then 
			
		WRITE(*,*)'File: ', TRIM (file_name), ' already exists.'
		WRITE(*,*)'Would you like to'
		WRITE(*,*)'1 enter a new file name'
		WRITE(*,*)'2 overwrite existing file'
		WRITE(*,*)'3 QUIT'
		
		READ(*,*)choice
		
		SELECT CASE(choice)
		
		case(1)
		
		CALL getof(file_name,quit)
			
		case(2)
			
		OPEN(UNIT=2, FILE=file_name,STATUS='REPLACE',IOSTAT=status)
		WRITE(*,*)'File: ', TRIM(file_name), ' opened.'
			
		case(3)
			
		quit = .TRUE.
			
		case default
		
		END SELECT
		
		else 
					
		OPEN(UNIT=2, FILE=file_name,STATUS='NEW', IOSTAT=status)
		WRITE(*,*)'File: ', TRIM(file_name), ' opened.'

		end if	
		
		end SUBROUTINE
		
		
		
		SUBROUTINE readf(people, num, count)
			
		INTEGER ::i = 1, j = 1, status
		CHARACTER, INTENT(OUT):: people(25)*30
		INTEGER, INTENT(OUT):: num(25)
		INTEGER, INTENT(OUT):: count
				
		do i= 1, 25 !maybe change to a do while so you can stop when bad data is found
		
			READ(1,*, IOSTAT = status) people(i)
						
			READ(1,*, IOSTAT = status) num(i)
			
			if(status .EQ. 0)then
			
			!WRITE(*,*) people(i)
			!WRITE(*,*)num(i)			
			count = count+1	
										
			end if

		end do
		
		if(count .EQ. 25)then
			
			WRITE(*,*)'You reached 25 inputs every input after ',TRIM(people(25)),' will not be included'
			
		else
		
			WRITE(*,*)count ,' inputs included in the list'
			
		end if	
		END SUBROUTINE
		
		
		
		
		
		
		
		SUBROUTINE linked(people, num, count) !make sure its double linked
		
		CHARACTER, INTENT(OUT):: people(25)*30
		INTEGER, INTENT(OUT):: num(25)
		INTEGER, INTENT(OUT):: count
		
		INTEGER :: loop=0, good = 0, i = 0
		
  1     FORMAT(' ','New node',I3,' entered with the data ',A10,I3)
  2		FORMAT(' ','First node 1 entered with the data ',A10,I3)
  3		FORMAT(' ','Starting with ',A10,' with a count of',I3)
  4		FORMAT(' ',A10,X,I3,' will be removed')
			
		
		TYPE::node !initializes the linked list
		
		CHARACTER::people1*30
		INTEGER:: num1
				
		TYPE(node), Pointer:: next, prev
		
		END TYPE
		
		
		TYPE(node), POINTER:: headp, tailp, curp, new !sets pointers for the linked list
		INTEGER istat
		NULLIFY(headp,tailp)
				
		do i = 1, count
			
			ALLOCATE(curp, STAT = istat) !creates a node
			
			if(istat .EQ. 0)then
			
				NULLIFY(curp%next)
				NULLIFY(curp%prev)
				
				if(num(i) .NE. 0)then
				
				curp%people1 = people(i)
				curp%num1 = num(i)
				
				else
				
				WRITE(*,*) TRIM(people(i)), ' has a count of zero and will not be added'
				
				end if
			
			end if ! end of creating a node
			
				
		
		if(ASSOCIATED(headp))then ! adds a node to the end of a linked list, single linked
			
				tailp%next=> curp
				curp%prev => tailp
				tailp=> curp
				
				WRITE(*,1) i,TRIM(curp%people1), curp%num1
				
			else
			
				headp=>curp 
				tailp=>curp
				
				WRITE(*,2)TRIM(headp%people1), headp%num1
			
			end if
			
		end do
		
		WRITE(*,*)'Data Entry complete'
		WRITE(*,*)'             ' !end single link



		
		
		
		
		!start of eliminating data
		
		curp => headp
	
		!do while(good .EQ. 0)
		
		do j = 1, 10
		
			if(ASSOCIATED(curp%next) .OR. ASSOCIATED(curp%prev))then
			
				WRITE(*,3)TRIM(curp%people1),curp%num1
				loop = curp%num1
				
				do while (loop .NE. 0)
				
					if(curp%num1 .GT. 0)then
					
						if(ASSOCIATED(curp%next))then
									
						!MOVE TO THE NEXT NOde
						
						curp => curp%next
						
						loop = loop-1
					
						else
						
						curp => headp
						
						loop = loop-1
						
						end if
						
					end if
						
					
					if(curp%num1 .LT. 0)then
					
						if(ASSOCIATED(curp%prev))then
									
						!MOVE TO THE NEXT NOde
						
						curp => curp%prev
						
						loop = loop+1
					
						else
						
						curp => tailp
						
						loop = loop+1
												
						end if
						
					end if	
						
					
				end do !under checks which node will be removed 
				
				if(ASSOCIATED(curp%next) .OR. ASSOCIATED(curp%prev))then
				
					WRITE(*,4) TRIM(curp%people1), curp%num1! says node is removed
					
					!link around node
					!update the nodes
					!remove the node
					
					else
					
					!last node
					
				end if
			
			end if
		
		end do! big loop
		

		
		END SUBROUTINE