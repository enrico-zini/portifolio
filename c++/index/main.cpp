#include <iostream>
#include <fstream>
#include <sstream>
#include <map>
#include <string>
#include <algorithm>
#include <cctype>
#include <chrono>

using namespace std;

class Word
{
private:
    string word;
    int count;
public:
    // Default Contructor
    Word() : word(""), count(0) {}
    // Constructor
    Word(const string &w) : word(w), count(1) {}//comstructor receives a string w wich will be atributed to word

    // Getter for the word
    string getWord() const
    {
        return word;
    }

    // Method to add a page number
    void addCount()
    {
        count++;
    }

    // Method to display the word and associated pages
    void display() const
    {
        cout << word << ": " << count << endl;
    }
};

// Function to clean up a word (remove punctuation and convert to lowercase)
string cleanWord(const string &word)
{
    string cleanedWord;
    for (char c : word)
    {
        if (isalnum(c) || c == '\'')
        { // Allow letters, numbers, and apostrophes
            cleanedWord += tolower(c);
        }
    }
    return cleanedWord;
}

int main()
{
    auto start = chrono::steady_clock::now();
    
    ifstream file("cato.txt");
    if (!file.is_open())
    {
        cerr << "Error opening file." << endl;
        return 1;
    }

    string word;
    map<string,Word> words;//it tries to initialize the Word class, so it needs a default constructor with no arguments
    
    while (file >> word)
    { // Read each word from the file
        word = cleanWord(word);
        bool found = false;
        auto aux = words.find(word);
        if(aux != words.end())// If found, returns an iterator pointing to the key-value pair in the map, If the key is not found, it returns words.end().
        {
            aux -> second.addCount();// gets the second member of the map i.e Word
        }
        else
        {
            Word w(word);
            words[word] = w;
        }
    }

    for (const auto& w : words)
    {
        w.second.display();
    }

    auto end = chrono::steady_clock::now();
    auto time_taken = end-start;
    cout << chrono::duration<double,milli>(time_taken).count() << "ms" << endl; 

    return 0;
}
