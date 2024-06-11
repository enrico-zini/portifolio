#include <math.h>
#include <chipmunk.h>
#include <SOIL.h>
#include <time.h>
#include <stdlib.h>

// Rotinas para acesso da OpenGL
#include "opengl.h"

typedef struct
{
    cpBody *robot;
    int time;
    int posicao;
} Robot;

// Funções para movimentação de objetos
void moveRobo(cpBody *body, void *data);
void moveBola(cpBody *body, void *data);

// Prototipos
int tocou(cpVect *robotPosition, cpVect *ballPosition);
cpVect chute(cpVect* ballPosition, cpVect* gol);

void initCM();
void freeCM();
void restartCM();
cpShape *newLine(cpVect inicio, cpVect fim, cpFloat fric, cpFloat elast);
cpBody *newCircle(cpVect pos, cpFloat radius, cpFloat mass, char *img, bodyMotionFunc func, cpFloat fric, cpFloat elast);

// Score do jogo
int score1 = 0;
int score2 = 0;

// Flag de controle: 1 se o jogo tiver acabado
int gameOver = 0;

// cpVect e' um vetor 2D e cpv() e' uma forma rapida de inicializar ele.
cpVect gravity;

// O ambiente
cpSpace *space;

// Paredes "invisíveis" do ambiente
cpShape *leftWall, *rightWall, *topWall, *bottomWall;

// A bola
cpBody *ballBody;

// Um robô
cpBody *robotBody;
cpBody *robotBody2;

// Cada passo de simulação é 1/60 seg.
cpFloat timeStep = 1.0 / 60.0;



// Inicializa o ambiente: é chamada por init() em opengl.c, pois necessita do contexto
// OpenGL para a leitura das imagens
void initCM()
{
    gravity = cpv(0, 100);

    // Cria o universo
    space = cpSpaceNew();

    // Seta o fator de damping, isto é, de atrito do ar
    cpSpaceSetDamping(space, 0.8);

    // Descomente a linha abaixo se quiser ver o efeito da gravidade!
    // cpSpaceSetGravity(space, gravity);

    // Adiciona 4 linhas estáticas para formarem as "paredes" do ambiente
    leftWall = newLine(cpv(0, 0), cpv(0, ALTURA_JAN), 0, 1.0);
    rightWall = newLine(cpv(LARGURA_JAN, 0), cpv(LARGURA_JAN, ALTURA_JAN), 0, 1.0);
    bottomWall = newLine(cpv(0, 0), cpv(LARGURA_JAN, 0), 0, 1.0);
    topWall = newLine(cpv(0, ALTURA_JAN), cpv(LARGURA_JAN, ALTURA_JAN), 0, 1.0);

    // Agora criamos a bola...
    // Os parâmetros são:
    //   - posição: cpVect (vetor: x e y)
    //   - raio
    //   - massa
    //   - imagem a ser carregada
    //   - ponteiro para a função de movimentação (chamada a cada passo, pode ser NULL)
    //   - coeficiente de fricção
    //   - coeficiente de elasticidade
    ballBody = newCircle(cpv(512, 350), 8, 1, "small_football.png", moveBola, 0.2, 1);

    // ... e um robô de exemplo
    robotBody = newCircle(cpv(50, 350), 20, 2, "ship1.png", moveRobo, 0.2, 2);
    //Robot r1 = {robotBody, 0, 0};
}

int tocou(cpVect *robotPosition, cpVect *ballPosition)
{
    // SE A DISTANCIA ENTRE ROBO E BOLA FOR IGUAL A SOMA DOS RAIOS, ELES SE TOCARAM
    // https://www.bbc.co.uk/bitesize/guides/z9pssbk/revision/4#:~:text=To%20do%20this%2C%20you%20need,then%20the%20circles%20touch%20internally.
    float distance = hypot((robotPosition->x - ballPosition->x), (robotPosition->y - ballPosition->y));
    // printf("%f\n", distance);
    return distance < 28.0;
}

// Exemplo de função de movimentação: move o robô em direção à bola
void moveRobo(cpBody *body, void *data)
{
    // Veja como obter e limitar a velocidade do robô...
    if(cpBodyGetPosition(body).x == 512 && cpBodyGetPosition(body).x == 350){

    }
    cpVect vel = cpBodyGetVelocity(body);
    // printf("vel: %f %f", vel.x,vel.y);

    // Limita o vetor em 50 unidades
    vel = cpvclamp(vel, 50); // LIMITA VELOCIDADE DO ROBO
    // E seta novamente a velocidade do corpo
    cpBodySetVelocity(body, vel);

    // Obtém a posição do robô e da bola...
    cpVect robotPos = cpBodyGetPosition(body);
    cpVect ballPos = cpBodyGetPosition(ballBody);

    // Calcula um vetor do robô à bola (DELTA = B - R)
    cpVect pos = robotPos;
    pos.x = -robotPos.x;
    pos.y = -robotPos.y;
    cpVect delta = cpvadd(ballPos, pos);

    // Limita o impulso em 20 unidades
    delta = cpvmult(cpvnormalize(delta), 20);
    // Finalmente, aplica impulso no robô
    cpBodyApplyImpulseAtWorldPoint(body, delta, robotPos);
    if(robotPos.x > 100)// SE ROBO FOR DEFENSOR, SO VAI ATRAS DA BOLA ATE CERTO PONTO
    {
        if (delta.x > 0)
        {
            delta = cpv(-delta.x, 0);
            cpBodyApplyImpulseAtWorldPoint(body, delta, robotPos);
        }
    }
}
cpVect chute(cpVect* ballPosition, cpVect* gol)
{
    cpVect pos = *ballPosition;
    pos.x = -ballPosition->x;
    pos.y = -ballPosition->y;
    cpVect impulso = cpvadd(*gol, pos);
    impulso = cpvmult(cpvnormalize(impulso), 200); // chute em direção ao gol
    return impulso;
}

// Exemplo: move a bola aleatoriamente
void moveBola(cpBody *body, void *data)
{
    if(cpBodyGetPosition(body).x == 512 && cpBodyGetPosition(body).y == 350){
        
    }
    cpVect impulso = cpv(rand()%20-10,rand()%20-10);
    cpBodyApplyImpulseAtWorldPoint(body, impulso, cpBodyGetPosition(body));
    // Sorteia um impulso entre -10 e 10, para x e y
    
    // cpVect impulso = cpv(0, 0);

    // E aplica na bola
    

    cpVect robotPosition = cpBodyGetPosition(robotBody);
    cpVect ballPosition = cpBodyGetPosition(body);
    cpVect gol = cpv(955, 355);
    if (tocou(&robotPosition, &ballPosition))
    {
        printf("TESTE\n");
        cpVect impulso = chute(&ballPosition, &gol);
        cpBodyApplyImpulseAtWorldPoint(body, impulso, cpBodyGetPosition(body));
    }
    // posição do gol direito = (955, 355)
    // posição do gol esquerdo = (50, 355)
}

// Libera memória ocupada por cada corpo, forma e ambiente
// Acrescente mais linhas caso necessário
void freeCM()
{
    printf("Cleaning up!\n");
    UserData *ud = cpBodyGetUserData(ballBody);
    cpShapeFree(ud->shape);
    cpBodyFree(ballBody);

    ud = cpBodyGetUserData(robotBody);
    cpShapeFree(ud->shape);
    cpBodyFree(robotBody);

    cpShapeFree(leftWall);
    cpShapeFree(rightWall);
    cpShapeFree(bottomWall);
    cpShapeFree(topWall);

    cpSpaceFree(space);
}

// Função chamada para reiniciar a simulação
void restartCM()
{
    // Escreva o código para reposicionar os jogadores, ressetar o score, etc.

    // Não esqueça de ressetar a variável gameOver!
    gameOver = 1;
}

// ************************************************************
//
// A PARTIR DESTE PONTO, O PROGRAMA NÃO DEVE SER ALTERADO
//
// A NÃO SER QUE VOCÊ SAIBA ***EXATAMENTE*** O QUE ESTÁ FAZENDO
//
// ************************************************************

int main(int argc, char **argv)
{
    // Inicialização da janela gráfica
    init(argc, argv);

    // Não retorna... a partir daqui, interação via teclado e mouse apenas, na janela gráfica
    glutMainLoop();
    return 0;
}

// Cria e adiciona uma nova linha estática (segmento) ao ambiente
cpShape *newLine(cpVect inicio, cpVect fim, cpFloat fric, cpFloat elast)
{
    cpShape *aux = cpSegmentShapeNew(cpSpaceGetStaticBody(space), inicio, fim, 0);
    cpShapeSetFriction(aux, fric);
    cpShapeSetElasticity(aux, elast);
    cpSpaceAddShape(space, aux);
    return aux;
}

// Cria e adiciona um novo corpo dinâmico, com formato circular
cpBody *newCircle(cpVect pos, cpFloat radius, cpFloat mass, char *img, bodyMotionFunc func, cpFloat fric, cpFloat elast)
{
    // Primeiro criamos um cpBody para armazenar as propriedades fisicas do objeto
    // Estas incluem: massa, posicao, velocidade, angulo, etc do objeto
    // A seguir, adicionamos formas de colisao ao cpBody para informar o seu formato e tamanho

    // O momento de inercia e' como a massa, mas para rotacao
    // Use as funcoes cpMomentFor*() para calcular a aproximacao dele
    cpFloat moment = cpMomentForCircle(mass, 0, radius, cpvzero);

    // As funcoes cpSpaceAdd*() retornam o que voce esta' adicionando
    // E' conveniente criar e adicionar um objeto na mesma linha
    cpBody *newBody = cpSpaceAddBody(space, cpBodyNew(mass, moment));

    // Por fim, ajustamos a posicao inicial do objeto
    cpBodySetPosition(newBody, pos);

    // Agora criamos a forma de colisao do objeto
    // Voce pode criar multiplas formas de colisao, que apontam ao mesmo objeto (mas nao e' necessario para o trabalho)
    // Todas serao conectadas a ele, e se moverao juntamente com ele
    cpShape *newShape = cpSpaceAddShape(space, cpCircleShapeNew(newBody, radius, cpvzero));
    cpShapeSetFriction(newShape, fric);
    cpShapeSetElasticity(newShape, elast);

    UserData *newUserData = malloc(sizeof(UserData));
    newUserData->tex = loadImage(img);
    newUserData->radius = radius;
    newUserData->shape = newShape;
    newUserData->func = func;
    cpBodySetUserData(newBody, newUserData);
    printf("newCircle: loaded img %s\n", img);
    return newBody;
}
