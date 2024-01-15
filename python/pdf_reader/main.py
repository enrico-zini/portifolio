#https://youtu.be/RULkvM7AdzY?si=3GIwyEZXzqOwciIP
#https://youtu.be/YK9a8E45X_Y?si=vEBfGjVcYQQCOxw7
import PyPDF2
from tkinter import filedialog
import re

class Word:
    def __init__(self, word):
        self.word = word
        self.pages = []

    def add_page(self, page_number):
        if not self.pages.__contains__(page_number):
            self.pages.append(page_number)
    def __str__(self):
        return f"{self.word}: {self.pages}"


def extract_text_from_pdf() -> [str]:
    pdf = filedialog.askopenfilename()
    reader = PyPDF2.PdfReader(pdf, strict=False)
    text = []

    for page in reader.pages:
        text.append(page.extract_text())

    return text

if __name__ == "__main__":
    pages = extract_text_from_pdf()
    words = []
    page_number = 0
    #look over every word of every page and sees if it is already in the list
    for page in pages:
        page_number += 1
        words_in_page = re.findall(r'\w+', page) 
        words_in_page.sort()
        
        for wip in words_in_page:
            found = False
            for w in words:
                if w.word == wip:#if finds the word, just add page number 
                    w.add_page(page_number)
                    found = True
                    break

            if not found:#if does not find word, creates and add new word
                aux = Word(wip)
                words.append(aux)
                aux.add_page(page_number)
search = input("Say the word: ")
for w in words:
    if search == w.word:
        print(w)