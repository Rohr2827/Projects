#ifndef PARSER_H
#define PARSER_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include "file_util.h"


//Function prototype
bool parser();
void system_goal(char*);
bool program();
bool match(char*);
void next_token();
void statement_list();










#endif
