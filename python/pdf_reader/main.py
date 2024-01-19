from PIL import Image
import pytesseract
import os
import fitz
from tkinter import HORIZONTAL, Label, Tk, filedialog
from tkinter.ttk import Progressbar
import shutil

def update_progress(bar, loading):
    bar['value'] += 1
    loading.update()

def convert_scanned_pdf_to_images(pdf_path, output_directory='images'):
    # Ensure the output directory exists or create it
    os.makedirs(output_directory, exist_ok=True)

    # Open the PDF file
    pdf_document = fitz.open(pdf_path)
    tasks = pdf_document.page_count

    # Create loading bar
    loading = Tk()
    bar = Progressbar(loading, orient=HORIZONTAL)
    bar.pack(padx=50, pady=10)
    bar["maximum"] = tasks
    label = Label(loading, text="Processing PDF")
    label.pack()

    for page_num in range(pdf_document.page_count):
        # Get the page
        page = pdf_document[page_num]

        # Convert the page to an image (pixmap)
        image = page.get_pixmap(matrix=fitz.Matrix(1,1))# 1,1 = original scale
        
        # Convert the pixmap to a PIL Image
        pil_image = Image.frombytes("RGB", [image.width, image.height], image.samples)

        # Save the image to the output directory
        image_filename = os.path.join(output_directory, f'page_{page_num + 1}.png')
        pil_image.save(image_filename, 'PNG')
        update_progress(bar=bar,loading=loading)

    # Close the PDF document
    pdf_document.close()
    loading.destroy()

pdf_path = filedialog.askopenfile()
convert_scanned_pdf_to_images(pdf_path)


# Path to the Tesseract executable (change this based on your installation)
pytesseract.pytesseract.tesseract_cmd = r'C:\Program Files\Tesseract-OCR\tesseract.exe'

def extract_text_from_image(image_path):
    # Open the image using Pillow
    img = Image.open(image_path)
    # Use Tesseract to do OCR on the image
    text = pytesseract.image_to_string(img)
    return text

# Replace 'your_image.png' with the path to your PNG image
folder_path = 'images'
image_files = os.listdir(folder_path)
image_files = sorted(image_files, key=lambda x: int(x.split('_')[1].split('.')[0]))
#page_1.png -> [0] = page, [1] = 1.png
#1.png -> [0] = 1, [1] = png

string = ""

tasks2 = len(image_files)

# Create the 2nd progress bar
loading2 = Tk()
bar2 = Progressbar(loading2, orient=HORIZONTAL)
bar2.pack(padx=50, pady=10)
bar2["maximum"] = tasks2
label2 = Label(loading2, text="Processing Text")
label2.pack()
for i in image_files:
    text = extract_text_from_image(f'{folder_path}/{i}')
    string += text + '\n'
    update_progress(bar=bar2, loading=loading2)
    print(text)
loading2.destroy()

# Deletes the images folder
shutil.rmtree(folder_path)

# Asks where should the txt file be saved
output_file = filedialog.asksaveasfile(defaultextension=".txt", filetypes=[("Text files", "*.txt")])
with open(output_file.name, 'w') as output:
    output.write(string)
