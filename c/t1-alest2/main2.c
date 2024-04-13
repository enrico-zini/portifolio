#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include <time.h>

#define INDESEJAVEIS 14 // caracter 13 para baixo tem os caracteres de controle como \r e \n

int prev_x = 0;
int cur_x = 0;

int prev_y = 0;
int cur_y = 0;

int linhas;
int colunas;

int hv = 1; // (1)horizontal (-1)vertical
int movimento = 1;

char **matriz;

void printMatriz(char **matriz, int x, int y)
{
    for (int i = 0; i < x; ++i)
    {
        for (int j = 0; j < y; ++j)
        {
            printf("%c", matriz[i][j]);
        }
        printf("\n");
    }
}

void caminha()
{
    if (hv > 0)
    {
        prev_x = cur_x;
        prev_y = cur_y;
        cur_y += movimento;
    }
    else if (hv < 0)
    {
        prev_y = cur_y;
        prev_x = cur_x;
        cur_x += movimento;
    }
}

int soma()
{
    for (int i = 0; i < linhas; ++i)
    { // coloca coordenadas no inicio do caminho
        if (matriz[i][0] == '-')
        {
            cur_x = i;
            break;
        }
    }
    int soma = 0;
    char concat[100] = "";
    while (matriz[cur_x][cur_y] != '#')
    {
        if (isdigit(matriz[cur_x][cur_y]))
        {
            char aux[2] = {matriz[cur_x][cur_y], '\0'};
            strcat(concat, aux);
        }
        else if (concat[0] != '\0') // mais eficiente do que (concat != "")
        {
            soma += atoi(concat);
            memset(concat, 0, sizeof(concat));
        }

        int vertical = cur_x - prev_x;   // checa se coordenadas estao se movendo para cima(<0) ou para baixo(>0)
        int horizontal = cur_y - prev_y; // checa se coordenadas estao se movendo para direita(>0) ou esquerda(<0)

        if (horizontal > 0) // direita
        {
            if (matriz[cur_x][cur_y] == '\\')
            {
                hv *= -1;
                movimento = 1;
            }
            else if (matriz[cur_x][cur_y] == '/')
            {
                hv *= -1;
                movimento = -1;
            }
        }
        else if (horizontal < 0) // esquerda
        {
            if (matriz[cur_x][cur_y] == '\\')
            {
                hv *= -1;
                movimento = -1;
            }
            else if (matriz[cur_x][cur_y] == '/')
            {
                hv *= -1;
                movimento = 1;
            }
        }
        else if (vertical > 0) // baixo
        {
            if (matriz[cur_x][cur_y] == '\\')
            {
                hv *= -1;
                movimento = 1;
            }
            else if (matriz[cur_x][cur_y] == '/')
            {
                hv *= -1;
                movimento = -1;
            }
        }
        else if (vertical < 0) // cima
        {
            if (matriz[cur_x][cur_y] == '\\')
            {
                hv *= -1;
                movimento = -1;
            }
            else if (matriz[cur_x][cur_y] == '/')
            {
                hv *= -1;
                movimento = 1;
            }
        }
        caminha();
    }

    if (concat[0] != '\0')
    {
        soma += atoi(concat);
    }

    return soma;
}

char **criaMatriz(char argv[])
{
    FILE *file = fopen(argv, "r");
    fscanf(file, "%d %d", &linhas, &colunas);
    fgetc(file);

    // cria matriz a partir do arquivo .txt
    matriz = (char **)malloc(linhas * sizeof(char *));
    for (int i = 0; i < linhas; i++)
    {
        matriz[i] = (char *)malloc(colunas * sizeof(char));
        for (int j = 0; j < colunas; j++)
        {
            char c = fgetc(file);
            // Referencia: Juliano Maia
            // (c == '\n') funciona apenas no windows
            // (c == '\n' || c == '\r') funciona tambem no linux
            while (c < INDESEJAVEIS) // pula caracteres indesejaveis
            {
                //printf("%d\n", c);
                c = fgetc(file);
            }
            matriz[i][j] = c;
        }
    }
    fclose(file);
    return matriz;
}

void metodozao(int argc, char *argv[])
{
    clock_t start, end;

    // start = clock();
    matriz = criaMatriz(argv[1]);// cria matriz a partir do arquivo txt
    // end = clock();
    // double dif = ((double)(end - start)) / CLOCKS_PER_SEC;
    
    //printMatriz(matriz, linhas, colunas);
    
    start = clock();
    //printf("%d\n", soma());
    soma();
    end = clock();
    double dif2 = ((double)(end - start)) / CLOCKS_PER_SEC;
    free(matriz);

    //printf("Tempo de criacao da matriz: %.6f sec\n", dif);
    printf("Tempo para achar o total: %.6f sec\n", dif2);
}
int main(int argc, char *argv[])
{
    for (int i = 0; i < 100; i++)
    {
        metodozao(argc, argv);
        // reseta variaveis iniciais
        prev_x = 0;
        cur_x = 0;

        prev_y = 0;
        cur_y = 0;

        hv = 1; // (1)horizontal (-1)vertical
        movimento = 1;
    }
    return 0;
}
