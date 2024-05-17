#include "linkedlist.hpp"
int main()
{
    LinkedList<std::string> l;
    l.initList();
    l.addNode("fulano");
    l.addNode("ciclano");
    l.addNode("beltrano");
    l.removeNode("ciclano");
    l.printList();
    l.clean();
    return 0;
}
