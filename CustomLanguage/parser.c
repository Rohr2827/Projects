#include "parser.h"
#include "scanner.h"
#include "file_util.h"

//extern *token_ident;

bool parser()
{
	return true;
}


void system_goal(char *token_id)
{
	//scanner(input_file, output_file, list_file);
	//printf("TOKEN_id: %s\n", token_id);
	program();
	//match(SCANOF);
	
	//if program and match have no errors the source file is processed
}


bool program()
{
	char BEGIN[5] = "BEGIN";
	char END[3] = "END";
	
	match(BEGIN);
	//statement_list();
	//match(END);
	
}


bool match(char *BEGIN)
{	
	scanner(input_file, output_file, list_file);
	//recieves a token and calls scanner to compare them 
	
	printf("YES %s\n", BEGIN);
	//printf("TOKEN_id: %s\n", token_id);
	
	//if they match return true else false, false sends syntax error	
}


void next_token(BEGIN)
{
	
	scanner(input_file, output_file, list_file);
	//printf("HERE, %s", BEGIN);
	
}
