import tkinter as tk
from tkinter import ttk, simpledialog
from tkinter import filedialog
import pyautogui
import time

class MyPoint:
    def __init__(self, x, y):
        self.x = x
        self.y = y

class MouseTrackerApp:
    
    def choose_file_and_create(self):
        file_path = filedialog.asksaveasfilename(defaultextension=".txt", filetypes=[("Text files", "*.txt")])
        file = open(file_path,"a")
        for point in self.myPoints:
            x = point.x
            y = point.y
            file.write(f"{x},{y}\n")
        file.close()

    def choose_file_and_read(self):
        file_path = filedialog.askopenfilename(defaultextension=".txt", filetypes=[("Text files", "*.txt")])
        file = open(file_path,"r")
        for line in file:
            time.sleep(1)
            data = line.strip().split(',')
            x = data[0]
            y = data[1]
            if x == "Type":
                pyautogui.typewrite(y,0.03)
            elif x == "Click":
                pyautogui.click()
            elif x == "Press":
                pyautogui.mouseDown()
            elif x == "Release":
                pyautogui.mouseUp()
            else:
                pyautogui.moveTo(int(x),int(y),0)

    def __back__(self):
        self.myPoints.pop()
        self.tree.delete(*self.tree.get_children())#remove all
        for point in self.myPoints:#adds again without the last
            self.tree.insert("", "end", values=(point.x, point.y))

    def __instructions__(self):
        instruction_frame = tk.Toplevel(self.root)
        instruction_frame.title("Instructions")
        instructions_text = (
            "Space: Record mouse position\n"
            "Enter: Record typing\n"
            "C: Record mouse click\n"
            "P: Record mouse press\n"
            "R: Record mouse release"
        )
        tk.Label(instruction_frame, text=instructions_text, justify="left").pack()
       


    def __init__(self, root):
        self.root = root
        self.root.title("Action Tracker")    

        self.tree = ttk.Treeview(root, columns=("X", "Y"), show="headings")
        self.tree.heading("X", text="X")
        self.tree.heading("Y", text="Y")
        # Add a vertical scrollbar
        y_scrollbar = ttk.Scrollbar(root, orient="vertical", command=self.tree.yview)
        y_scrollbar.pack(side="right", fill="y")
        self.tree.configure(yscrollcommand=y_scrollbar.set)

        self.tree.pack()

        menu_bar = tk.Menu(root)
        root.config(menu=menu_bar)

        file_menu = tk.Menu(menu_bar, tearoff=0)
        file_menu.add_command(label="Save As", command=self.choose_file_and_create)
        file_menu.add_command(label="Read", command=self.choose_file_and_read)
        file_menu.add_command(label="Back", command=self.__back__)
        file_menu.add_command(label='Instructions', command=self.__instructions__)
        menu_bar.add_cascade(label="File", menu=file_menu)

        self.myPoints = []

        self.root.bind("<space>", self.on_space_press)
        self.root.bind("<Return>", self.on_enter_press)
        self.root.bind("<c>", self.on_c_press)
        self.root.bind("<p>", self.on_p_press)
        self.root.bind("<r>", self.on_r_press)

    def on_space_press(self, event):
        x, y = pyautogui.position()
        my_point = MyPoint(x, y)
        self.myPoints.append(my_point)
        self.tree.insert("", "end", values=(my_point.x, my_point.y))

    def on_enter_press(self, event):
        input_text = simpledialog.askstring("Input", "Type:")
        self.myPoints.append(MyPoint("Type", input_text))
        self.tree.insert("", "end", values=("Type", input_text))

    def on_c_press(self, event):
        self.myPoints.append(MyPoint("Click", "Click"))
        self.tree.insert("", "end", values=("Click", "Click"))

    def on_p_press(self, event):
        self.myPoints.append(MyPoint("Press", "Press"))
        self.tree.insert("", "end", values=("Press", "Press"))

    def on_r_press(self, event):
        self.myPoints.append(MyPoint("Release", "Release"))
        self.tree.insert("", "end", values=("Release", "Release"))

class main:
    root = tk.Tk()
    app = MouseTrackerApp(root)
    root.mainloop()
