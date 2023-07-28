#include "file_util.h"
#include "scanner.h"

int main (int argc, char *argv[]) {
    //Process Flags
    bool file_util_flag;
    bool scanner_flag;
    bool parser_flag;
    char *token_id;

    file_util_flag = process_files(argc, argv);
    if (file_util_flag == true) 
	{
    	system_goal();
    	
        /*scanner_flag = scanner(input_file, output_file, list_file);

        if (scanner_flag == true) {
            printf("Scanner Processed Successfully...\n");
            parser_flag = parser();
        }*/
    }
    file_util_flag = close_files();

    return 0;
}
