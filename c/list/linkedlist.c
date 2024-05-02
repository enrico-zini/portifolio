#include "linkedlist.h"

void initList(LinkedList *l)
{
    l->head = NULL;
    l->tail = NULL;
    l->size = 0;
}
void addNode(LinkedList *l, int value)
{
    Node *novo = malloc(sizeof(Node));
    novo->value = value;
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
        printf("%d->", cur->value);
        cur = cur->next;
    }
    printf("NULL\n");
}
int removeNode(LinkedList *l, int value)
{
    // 0->1->2->NULL
    if (l->head->value == value)
    {
        l->head = l->head->next;
        l->size--;
        return 0;
    }
    Node *cur = l->head;
    int trocou = 0;
    while (cur->next != NULL && trocou == 0)
    {
        if (cur->next->value == value)
        {
            if(cur->next == l->tail)
                l->tail = cur;
            cur->next = cur->next->next;
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

int indexOf(LinkedList *l, int value)
{
    Node *cur = l->head;
    int index = 0;
    while(cur != NULL)
    {
        if(cur->value == value)
            return index;
        index++;
        cur = cur->next;
    }
    return -1;
}