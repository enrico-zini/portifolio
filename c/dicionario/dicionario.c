#include "dicionario.h"
#include <math.h>

int hash(Dicionario *d, int key)
{
    return (int)pow(key, 2) % d->tam;
}

void initDicionario(Dicionario *d, int tam)
{
    d->tam = tam;
    d->array = malloc(sizeof(LinkedList) * tam);
    for (size_t i = 0; i < tam; i++)
    {
        LinkedList l;
        initList(&l);
        d->array[i] = l;
    }
}

void put(Dicionario *d, int key, int value)
{
    Pair p;
    p.key = key;
    p.value = value;
    int index = hash(d, p.key);
    LinkedList *l = &(d->array[index]);
    Node *n = getNode(l, p.key);
    if (n == NULL)
        addNode(l, p.key, p.value);
    else
    {
        n->p.value = p.value;
    }
}

int containsKey(Dicionario *d, int key)
{
    LinkedList l = d->array[hash(d, key)];
    if (indexOf(&l, key) != -1)
        return 1;
    return 0;
}

int containsValue(Dicionario *d, int value)
{
    for (size_t i = 0; i < d->tam; i++)
    {
        LinkedList l = d->array[i];
        if (l.size > 0)
        {
            if (indexOf(&l, value) != -1)
            {
                return 1;
            }
        }
    }
    return 0;
}

int getValue(Dicionario *d, int key)
{
    int index = hash(d, key);
    LinkedList l = d->array[index];
    return getNode(&l, key)->p.value;
}

void printDict(Dicionario *d)
{
    for (size_t i = 0; i < d->tam; i++)
    {
        printList(&(d->array[i]));
    }
}

void cleanDict(Dicionario *d)
{
    for (size_t i = 0; i < d->tam; i++)
    {
        clean(&(d->array[i]));
    }
    free(d->array);    
}

void removeKey(Dicionario *d, int key)
{
    int index = hash(d, key);
    removeNode(&(d->array[index]), key);
}