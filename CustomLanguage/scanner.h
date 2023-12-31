#ifndef SCANNER_H
#define SCANNER_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include "file_util.h"

//Variables and Definitions
#define BUFFER 1024
#define TOKEN 50

//Enumeration of the tokens
typedef enum {
    BEGIN,
    END,
    READ,
    WRITE,
    IF,
    THEN,
    ELSE,
    ENDIF,
    WHILE,
    ENDWHILE,
    ID,
    INTLITERAL,
    FALSEOP,
    TRUEOP,
    NULLOP,
    LPAREN,
    RPAREN,
    SEMICOLON,
    COMMA,
    ASSIGNOP,
    PLUSOP,
    MINUSOP,
    MULTOP,
    DIVOP,
    NOTOP,
    LESSOP,
    LESSEQUALOP,
    GREATEROP,
    GREATEREQUALOP,
    EQUALOP,
    NOTEQUALOP,
    SCANEOF,
    ERROR
} token;

//Function Prototype
void clear_buffer(char*, int);
void write_output(FILE*, token, char*, char*);
bool scanner(FILE*, FILE*, FILE*);
char *token_ident(token);
char *symbol_ident(token);
char *number_ident(token);
token check_reserved(char*);
token check_symbol(char*);
token check_number(char*);

#endif
