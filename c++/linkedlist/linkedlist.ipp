#include "linkedlist.hpp"
#include <string>

template <typename T>
void LinkedList<T>::initList()
{
    this->head = nullptr;
    this->tail = nullptr;
    this->size = 0;
}

template <typename T>
void LinkedList<T>::addNode(T value)
{
    Node<T> *novo = new Node<T>;
    novo->value = value;
    novo->next = nullptr;
    if (this->size == 0)
    {
        this->head = novo;
        this->tail = novo;
    }
    else
    {
        this->tail->next = novo;
        this->tail = novo;
    }
    this->size++;
}

template <typename T>
void LinkedList<T>::printList()
{
    Node<T> *cur = this->head;
    while (cur != nullptr)
    {
        std::cout << "[" << cur->value << "]->";
        cur = cur->next;
    }
    std::cout << "NULL\n";
}

template <typename T>
int LinkedList<T>::removeNode(T value)
{
    if (this->head == nullptr)
        return 1; // list is empty

    Node<T> *cur = this->head;
    if (cur->value == value)
    {
        this->head = cur->next;
        delete cur;
        this->size--;
        if (this->head == nullptr)
            this->tail = nullptr;
        return 0;
    }
    Node<T> *prev = cur;
    cur = cur->next;
    while (cur != nullptr)
    {
        if (cur->value == value)
        {
            prev->next = cur->next;
            if (cur == this->tail)
                this->tail = prev;
            delete cur;
            this->size--;
            return 0;
        }
        prev = cur;
        cur = cur->next;
    }
    return 1; // node not found
}

template <typename T>
void LinkedList<T>::clean()
{
    Node<T> *cur;
    while (this->head != nullptr)
    {
        cur = this->head;
        this->head = this->head->next;
        delete cur;
    }
    this->tail = nullptr;
    this->size = 0;
}

template <typename T>
int LinkedList<T>::indexOf(T value)
{
    if (this->size == 0)
        return -1;
    Node<T> *cur = this->head;
    int index = 0;
    while (cur != nullptr)
    {
        if (cur->value == value)
            return index;
        index++;
        cur = cur->next;
    }
    return -1;
}

template <typename T>
Node<T> *LinkedList<T>::getNode(T value)
{
    Node<T> *cur = this->head;
    while (cur != nullptr)
    {
        if (cur->value == value)
            return cur;
        cur = cur->next;
    }
    return nullptr;
}