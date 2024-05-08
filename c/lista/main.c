#include <stdio.h>
#include "dicionario.h"

int main()
{
    Dicionario d;
    initDicionario(&d, 10);
    int array[] = {2, 7, 5, 15};
    for (size_t i = 0; i < 4; i++)
    {
        put(&d, array[i], i);
    }
    printDict(&d);
    cleanDict(&d);
    return 0;
}
