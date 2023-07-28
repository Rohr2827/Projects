#ifndef FILE_UTIL
#define FILE_UTIL

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

//Variables and Definitions
#define FSIZE 30
#define BUFFER 1024
#define READ_FILE_MODE "r"
#define WRITE_FILE_MODE "w"
#define INPUT_EXTENSION ".IN"
#define OUTPUT_EXTENSION ".OUT"
#define LISTING_EXTENSION ".LIS"
#define BACKUP_EXTENSION ".BAK"
#define TEMP_FILE "tmpfile.TMP"

typedef enum {
    false,       // 0 for false
    true         // 1 for true 
} bool;

FILE *input_file;
FILE *output_file;
FILE *list_file;
FILE *temp_file;

//Function Prototypes
bool process_files(int, char**);             //File Start-Up Routine
bool process_input(char*);                   //Open the input file
bool process_output(char*);                  //Open the output file
bool process_tempfile();                     //Open temporary file
bool close_files();                          //File Wrap-Up Routine
void copy_file_contents(FILE*, FILE*);       //File copy routine

#endif