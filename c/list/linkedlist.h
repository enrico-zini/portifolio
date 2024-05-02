#ifndef LINKEDLIST_H
#define LINKEDLIST_H

#include <stdio.h>
#include <stdlib.h>

struct localNode
{
    int value;
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
void addNode(LinkedList *l, int value);
void printList(LinkedList *l);
int removeNode(LinkedList *l, int value);
int indexOf(LinkedList *l, int value);
void clean(LinkedList *l);

#endif