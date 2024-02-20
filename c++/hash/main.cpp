#include <iostream>
using namespace std;

char matrix[3][3];

int showMatrix()
{
    for (int i = 0; i < 3; i++)
    {
        for (int j = 0; j < 3; j++)
        {
            cout << matrix[i][j] << " ";
        }
        cout << endl;
    }
    return 0;
}
bool tie()
{
    for (size_t i = 0; i < 3; i++)
    {
        for (size_t j = 0; j < 3; j++)
        {
            if(matrix[i][j] == '-')
            {
                return false;
            }
        }
    }
    return true;
}
bool win()
{
    for (size_t i = 0; i < 3; i++)
    {
        if(matrix[i][0] != '-' && matrix[i][0] == matrix[i][1] && matrix[i][1] == matrix[i][2])
        {
            return true;
        }
    }
    for (size_t i = 0; i < 3; i++)
    {
        if(matrix[0][i] != '-' && matrix[0][i] == matrix[1][i] && matrix[1][i] == matrix[2][i])
        {
            return true;
        }
    }
    if(matrix[0][0] != '-' && matrix[0][0] == matrix[1][1] && matrix[1][1] == matrix[2][2])
    {
        return true;
    }
    if(matrix[0][2] != '-' && matrix[0][2] == matrix[1][1] && matrix[1][1] == matrix[2][0])
    {
        return true;
    }

    return false;
}

int main()
{
    for (int i = 0; i < 3; i++)
    {
        for (int j = 0; j < 3; j++)
        {
            matrix[i][j] = '-';
        }
    }

    showMatrix();
    int n = -1;
    char symbol = 'x';
    do
    {
        if(n == -1)
        {
            symbol = 'x'; 
        }
        else if (n == 1)
        {
            symbol = 'o';
        }
        cout << symbol << " turn: " << endl;
        int row = 0;
        int column = 0;
        cout << "row: ";
        cin >> row;
        cout << "column: ";
        cin >> column;
        matrix[row][column] = symbol;
        n *= -1;
        showMatrix();
    } while ((win() == false) || tie() == false);    

    return 0;
}
