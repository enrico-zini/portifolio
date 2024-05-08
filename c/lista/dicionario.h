#ifndef DICIONARIO_H
#define DICIONARIO_H

#include <math.h>
#include "linkedlist.h"

typedef struct 
{
    LinkedList *array;
    int tam;
} Dicionario;

int hash(Dicionario *d, int key);
void initDicionario(Dicionario *d, int tam);
void put(Dicionario *d, int key, int value);
int containsKey(Dicionario *d, int key);
int containsValue(Dicionario *d, int value);
int getValue(Dicionario *d, int key);
void printDict(Dicionario *d);
void cleanDict(Dicionario *d);
#endif