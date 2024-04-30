#include <stdio.h>
#include <stdlib.h>

typedef struct localNode
{
    int value;
    struct localNode *next;
} Node;

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

int main()
{
    LinkedList l;
    initList(&l);
    addNode(&l, 0);
    addNode(&l, 1);
    addNode(&l, 2);
    addNode(&l, 3);
    printList(&l);
    removeNode(&l, 3);
    printList(&l);
    printf("tail = %d\n", l.tail->value);
    return 0;
}

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
{#include <stdio.h>
#include <stdlib.h>

typedef struct localNode
{
    int value;
    struct localNode *next;
} Node;

typedef struct
…        l->tail = &novo;
        l->size++;
    }
}

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
            if(cur->next = l->tail)
            {
                l->tail = cur;
            }
            cur->next = cur->next->next;
            l->size--;
            return 0;
        }
        cur = cur->next;
    }
}
void clean(LinkedList *l)
{
    Node *aux;
    while (l->head != NULL)
    {
        aux = l->head;
        
    }
    
}