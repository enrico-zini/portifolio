#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <time.h>

#define INDESEJAVEIS 14 // caracter 13 para baixo tem os caracteres de controle como \r e \n

int movimento = 1;
int linhas;
int colunas;
char *ptr;
char *prev_ptr;
char *matriz;

void printMatriz(char *ptr, int linhas, int colunas)
{
    for (int i = 0; i < linhas; i++)
    {
        for (int j = 0; j < colunas; j++)
        {
            printf("%c", *ptr);
            ptr++;
        }
        printf("\n");
    }
}

void caminha()
{
    int dif = ptr - prev_ptr; // checa para onde ponteiros estao se movendo
    if (dif == 1)
    { // direita
        if (*ptr == '/')
        {
            movimento = colunas * -1;
        }
        else if (*ptr == '\\')
        {
            movimento = colunas;
        }
    }
    if (dif == -1)
    { // esquerda
        if (*ptr == '/')
        {
            movimento = colunas;
        }
        else if (*ptr == '\\')
        {
            movimento = colunas * -1;
        }
    }
    if (dif == colunas)
    { // baixo
        if (*ptr == '/')
        {
            movimento = -1;
        }
        else if (*ptr == '\\')
        {
            movimento = 1;
        }
    }
    if (dif == -colunas)
    { // cima
        if (*ptr == '/')
        {
            movimento = 1;
        }
        else if (*ptr == '\\')
        {
            movimento = -1;
        }
    }
    // atualiza ponteiros
    prev_ptr = ptr;
    ptr += movimento;
}

int soma()
{
    int soma = 0;
    char concat[100] = "";
    while (*ptr != '-') // coloca ponteiro no inicio do caminho
    {
        ptr += colunas;
    }

    while (*ptr != '#')
    {
        if (isdigit(*ptr))
        {
            strncat(concat, ptr, 1);
        }
        // se nao for digito e tiver algo no concat, pode somar
        else if (concat[0] != '\0')
        {
            soma += atoi(concat);
            concat[0] = '\0'; // como se concat ficasse nulo
        }
        caminha();
    }
    if (concat[0] != '\0')
    {
        soma += atoi(concat);
    }
    return soma;
}

char *criaMatriz(char file_name[])
{
    FILE *file = fopen(file_name, "r");
    fscanf(file, "%d %d", &linhas, &colunas);
    fgetc(file);

    // aloca espaco
    char *matriz = (char *)malloc(linhas * colunas * sizeof(char));
    ptr = &matriz[0];

    for (int i = 0; i < linhas; i++)
    {
        for (int j = 0; j < colunas; j++)
        {
            char c = fgetc(file);
            // Referencia: Juliano Maia
            // (c == '\n') funciona apenas no windows
            // (c == '\n' || c == '\r') funciona tambem no linux
            while (c < INDESEJAVEIS) // pula caracteres indesejaveis
            {
                // printf("%d\n", c);
                c = fgetc(file);
            }
            *ptr = c;
            ptr++;
        }
    }
    fclose(file);
    return matriz;
}

void metodozao(int argc, char *argv[])
{
    clock_t start, end;

    //start = clock();
    matriz = criaMatriz(argv[1]);// cria matriz a partir do arquivo txt
    // end = clock();
    // double dif = ((double)(end - start)) / CLOCKS_PER_SEC;

    ptr = matriz; // seta ponteiro para o inicio da matriz
    // printMatriz(ptr,linhas,colunas);

    start = clock();
    // printf("%d\n", soma());
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
        movimento = 1;
    }
    return 0;
}
