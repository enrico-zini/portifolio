#include <stdio.h>
#include "linkedlist.h"

int main()
{
    LinkedList l;
    addNode(&l, 0);
    addNode(&l, 1);
    addNode(&l, 2);
    addNode(&l, 3);
    printList(&l);
    printf("Index of 5: %d\n", indexOf(&l, 5));
    clean(&l);
    return 0;
}
