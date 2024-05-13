#include "linkedlist.h"

void initList(LinkedList *l)
{
    l->head = NULL;
    l->tail = NULL;
    l->size = 0;
}
void addNode(LinkedList *l, int key, int value)
{
    Pair p;
    p.key = key;
    p.value = value;
    Node *novo = malloc(sizeof(Node));
    novo->p = p;
    novo->next = NULL;
    if (l->size == 0)
    {
        l->head = novo;
        l->tail = novo;
    }
    else
    {
        l->tail->next = novo;
        l->tail = novo;
    }
    l->size++;
}
void printList(LinkedList *l)
{
    Node *cur = l->head;
    while (cur != NULL)
    {
        printf("[%d, %d]->", cur->p.key, cur->p.value);
        cur = cur->next;
    }
    printf("NULL\n");
}
int removeNode(LinkedList *l, int key) {
    if (l->head == NULL)
        return 1; // List is empty
    
    Node *cur = l->head;
    if (cur->p.key == key) {
        l->head = cur->next;
        free(cur);
        l->size--;
        if (l->head == NULL)
            l->tail = NULL;
        return 0;
    }
    Node *aux;
    while (cur->next != NULL) {
        if (cur->next->p.key == key) 
        {
            aux = cur->next;
            cur->next = cur->next->next;
            if(aux == l->tail)
                l->tail = cur;
            free(aux);
            l->size--;
            return 0;
        }
        cur = cur->next;
    }
    return 1;
}

void clean(LinkedList *l)
{
    Node *cur;
    while (l->head != NULL)
    {
        cur = l->head;
        l->head = l->head->next;
        free(cur);
    }
}

int indexOf(LinkedList *l, int key)
{
    if(l->size == 0)
        return -1;
    Node *cur = l->head;
    int index = 0;
    while(cur != NULL)
    {
        if(cur->p.key == key)
            return index;
        index++;
        cur = cur->next;
    }
    return -1;
}

Node* getNode(LinkedList *l, int key)
{
    Node *cur = l->head;
    while(cur != NULL)
    {
        if(cur->p.key == key)
            return cur;
        cur = cur->next;
    }
    return NULL;
}