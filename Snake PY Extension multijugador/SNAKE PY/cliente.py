from snake import *
from player import Player
from network import Network


class Window():
    def __init__(self, width, height):
        #self.myfont = pygame.font.Font('freesansbold.ttf', 14)
        self.gameWindow = pygame.display.set_mode([width, height])
        pygame.display.set_caption('Snake Game')

    def update(self, group, score):
        self.gameWindow.fill(WHITE)
        #scoretext = self.myfont.render(score, True, (255, 255, 255), (0, 0, 0))
        #self.gameWindow.blit(scoretext, (5, 10))
        group.draw(self.gameWindow)
        # actualizar pantalla
        pygame.display.flip()

class main():
        velocitat = 3
        normal = True

        all_sprites.empty()
        obstacles.empty()
        obs = Obstacles()
        window = Window(WIN_WIDTH, WIN_HEIGHT)

        n = Network()

        clock = pygame.time.Clock()

        p1 = n.getP() #debo crear los sprites del snake.

        posHead = p1.getPosHead()
        p1.color = GREEN2
        #body = p1.getBody()
        s = Snake(posHead, GREEN2)
        #s.body_group.add(Block(body[0], True, color)) #tengo un body[] y debo conseguir los bloques mal

        apple = Apple(RED)
        all_sprites.add(apple)
        #apple.randomPos(s.body_group)
        apple.posApple(p1)
        #sound2 = pygame.mixer.Sound("GameOver2.mp3")
        run = True


        p2 = n.send(p1)

        posHead2 = p2.getPosHead()
        p2.color = RED
        #body2 = p1.getBody()
        s2 = Snake(posHead2, RED, True)
        #mal s2.body_group.add(Block(body2[0], True, color))
        p1.start = True
        go = False

        while run:

            if not s.ok:
                break
            elif not p2.ok:
                break

            if p2.eated:
                apple.posApple(p2)
                p1.applePos = p2.applePos

            score1 = "Score1 = " + str(len(s.body))
            score2 = "Score2 = " + str(len(s2.body))

            print(score1)
            print(score2)
            if go: #and (p2.steps == p1.steps):
                s.move(apple, normal)
                #s2.move(apple, normal)
                s2.moveS2(apple, p2)
                #p1.steps += p1.steps

            window.update(all_sprites, score1)
            clock.tick(velocitat)
            #hay que modificar p1 y enviarlo
            p1.direction = s.direction
            p1.eated = s.eaten
            #si como debo cambio la posicion de apple, por tanto, debo cambiar p2 y p1
            if s.eaten:
                p1.applePos = apple.getPos()

            p2 = n.send(p1)
            go = p2.start

        #sound2.play()
        print("END GAME")

