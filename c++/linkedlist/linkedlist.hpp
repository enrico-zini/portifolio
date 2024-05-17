#ifndef LINKEDLIST_HPP
#define LINKEDLIST_HPP

#include <iostream>

template <typename T>
struct Node
{
    T value;
    struct Node<T> *next;
};

template <typename T>
struct LinkedList
{
    int size;
    Node<T> *head;
    Node<T> *tail;

    void initList();
    void addNode(T value);
    void printList();
    int removeNode(T value);
    void clean();
    int indexOf(T value);
    Node<T> *getNode(T value);
};
#include "linkedlist.ipp"
#endif