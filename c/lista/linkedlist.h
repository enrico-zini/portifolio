#ifndef LINKEDLIST_H
#define LINKEDLIST_H

#include <stdio.h>
#include <stdlib.h>

typedef struct
{
    int key;
    int value;
} Pair;


struct localNode
{
    Pair p;
    struct localNode *next;
};
typedef struct localNode Node;

typedef struct
{
    int size;
    Node *head;
    Node *tail;
} LinkedList;

void initList(LinkedList *l);
void addNode(LinkedList *l, int key, int value);
void printList(LinkedList *l);
int removeNode(LinkedList *l, int key);
int indexOf(LinkedList *l, int key);
void clean(LinkedList *l);
Node* getNode(LinkedList *l, int key);

#endif