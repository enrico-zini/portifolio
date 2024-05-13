#include <stdio.h>
#include "dicionario.h"

int main()
{
    Dicionario d;
    initDicionario(&d, 10);
    int array[] = {2, 7, 5, 15, 2, 5, 7};
    for (size_t i = 0; i < 7; i++)
    {
        if (containsKey(&d, array[i]))
            removeKey(&d, array[i]);
        else
            put(&d, array[i], i);        
    }
    printDict(&d);
    cleanDict(&d);
    return 0;
}
