import pygame
import random
import controller_snake

WIN_WIDTH = 600
WIN_HEIGHT = 400
DELAY = 0.15
RED = (255, 0, 0)
YELLOW = (191, 255, 0)
GREEN2 = (0, 155, 0)
WHITE = (255, 255, 255)
BLUE = (22, 229, 201)
ObjectSize = 20

all_sprites = pygame.sprite.Group()
obstacles = pygame.sprite.Group()
#snake2 = pygame.sprite.Group()


# set_timer(TICK, T) T= 100ms
# direccio en vector x,y normal (1,0) i sumar el event que tenim (si es 0 la suma vol dir que es direcc contraria)

# subclase de sprite

class Block(pygame.sprite.Sprite):
    def __init__(self, start, head=False, color=GREEN2, oponent=False):
        super().__init__()

        self.head = head
        self.image = pygame.Surface([ObjectSize, ObjectSize])
        self.image.fill(color)

        if self.head:
            if not oponent:
                self.image1 = pygame.image.load("SnakeHeadGreen.png")
                self.image = self.image1
            else:
                self.image1 = pygame.image.load("SnakeHeadRed.png")
                self.image = self.image1

        self.rect = self.image.get_rect()
        # es posa a la posició que li pasem
        self.rect.x = start[0]
        self.rect.y = start[1]

    def rotate(self, direction, changeDir):
        if direction == [1, 0] and changeDir == True:
            self.image = pygame.transform.rotate(self.image1, 270)
        if direction == [-1, 0] and changeDir == True:
            self.image = pygame.transform.rotate(self.image1, 90)
        if direction == [0, 1] and changeDir == True:
            self.image = pygame.transform.rotate(self.image1, 0)
        if direction == [0, -1] and changeDir == True:
            self.image = pygame.transform.rotate(self.image1, 180)


class Apple(pygame.sprite.Sprite):
    def __init__(self, color=RED):
        super().__init__()
        apple = pygame.image.load("Apple.png")
        self.image = apple
        self.rect = self.image.get_rect()

    def randomPos(self, group):
        # crea una poma aleatoria i després mira que no estigui sobre el cos de la serp i si ho està torna a crear una altre.
        while True:
            self.rect.x = random.randrange(ObjectSize, WIN_WIDTH, ObjectSize)
            self.rect.y = random.randrange(ObjectSize, WIN_HEIGHT, ObjectSize)
            collide = pygame.sprite.spritecollideany(self, group)
            if not collide:
                break
    def getPos(self):
        position = (self.rect.x, self.rect.y)
        return position

    def posApple(self, player):
        self.rect.x = player.applePos[0]
        self.rect.y = player.applePos[1]

class Snake(object):
    body_group = pygame.sprite.Group()
    #snake2 = pygame.sprite.Group()
    body = []
    #body2 = []

    def __init__(self, pos, color, oponent=False):

        self.head = Block(pos, True, color, oponent)
        #self.rect = (pos, ObjectSize, ObjectSize)

        #if oponent:
        #    self.snake2.add(self.head)
        #else:
        self.body_group.add(self.head)
        all_sprites.add(self.head)
        self.direction = [1, 0]
        self.head.rotate(self.direction, True)
        self.eaten = False
        self.key = controller_snake.Detection()
        self.ok = True
        self.color = color

    # Aquesta es la funcio principal que fa que a cada tic de rellotge la serp es mogui i es on apliquem
    # la deteccio de colisions, l'augment del tamany de la serp y es llegeix la tecla que es prem per
    # fer els canvis de direccio que pertoquin
    def sum(self, vect):
        aux = [self.direction[0] + vect[0], self.direction[1] + vect[1]]
        if aux == [0, 0] or vect == [0, 0] or self.direction == vect:
            return False
        else:
            return True

    def move(self, apple, normal):
        detect = self.key.keyDetection()
        accept = self.sum(detect)  # retorna true o fals depenent del moviment
        # print(accept)
        if accept:
            self.direction = detect
        self.bodySize = len(self.body)

        # Si eaten es Fals vol dir que ens movem sense afegir segment per tant posem el ultim segment
        # a la posicio del cap i reordenem el conjunt
        if not self.eaten and self.bodySize > 0:
            self.body[-1].rect.x = self.head.rect.x
            self.body[-1].rect.y = self.head.rect.y
            self.body.insert(0, self.body.pop())

        self.eaten = False

        if self.body_group.has(self.head):
            self.body_group.remove(self.head)

        # comprueba la dirección, y en caso de superar los limites de la ventana aparece por el otro lado
        if normal:
            if self.direction == [1, 0]:
                self.head.rect.x += ObjectSize
                if self.head.rect.x >= WIN_WIDTH:
                    self.head.rect.x = 0
            elif self.direction == [-1, 0]:
                self.head.rect.x -= ObjectSize
                if self.head.rect.x < 0:
                    self.head.rect.x = WIN_WIDTH - ObjectSize
            elif self.direction == [0, 1]:
                self.head.rect.y -= ObjectSize
                if self.head.rect.y < 0:
                    self.head.rect.y = WIN_HEIGHT - ObjectSize
            elif self.direction == [0, -1]:
                self.head.rect.y += ObjectSize
                if self.head.rect.y >= WIN_HEIGHT:
                    self.head.rect.y = 0
        else:
            if self.direction == [1, 0]:
                self.head.rect.x += ObjectSize
            elif self.direction == [-1, 0]:
                self.head.rect.x -= ObjectSize
            elif self.direction == [0, 1]:
                self.head.rect.y -= ObjectSize
            elif self.direction == [0, -1]:
                self.head.rect.y += ObjectSize

        self.head.rotate(self.direction, accept)

        self.collisionDetect(normal)
        self.eaten = self.eat(apple)

        if self.eaten:

            self.body_group.add(self.head)
            self.newSegment(self.color)
            apple.randomPos(self.body_group)


    def moveS2(self, apple, player):
        normal = True

        self.bodySize = len(self.body)
        self.eaten = player.eated

        if not self.eaten and self.bodySize > 0:

            self.body[-1].rect.x = self.head.rect.x
            self.body[-1].rect.y = self.head.rect.y
            self.body.insert(0, self.body.pop())

        if self.direction != player.direction:
            self.direction = player.direction
            self.head.rotate(self.direction, True)

        # comprueba la dirección, y en caso de superar los limites de la ventana aparece por el otro lado
        if normal:
            if self.direction == [1, 0]:
                self.head.rect.x += ObjectSize
                if self.head.rect.x >= WIN_WIDTH:
                    self.head.rect.x = 0
            elif self.direction == [-1, 0]:
                self.head.rect.x -= ObjectSize
                if self.head.rect.x < 0:
                    self.head.rect.x = WIN_WIDTH - ObjectSize
            elif self.direction == [0, 1]:
                self.head.rect.y -= ObjectSize
                if self.head.rect.y < 0:
                    self.head.rect.y = WIN_HEIGHT - ObjectSize
            elif self.direction == [0, -1]:
                self.head.rect.y += ObjectSize
                if self.head.rect.y >= WIN_HEIGHT:
                    self.head.rect.y = 0
        else:
            if self.direction == [1, 0]:
                self.head.rect.x += ObjectSize
            elif self.direction == [-1, 0]:
                self.head.rect.x -= ObjectSize
            elif self.direction == [0, 1]:
                self.head.rect.y -= ObjectSize
            elif self.direction == [0, -1]:
                self.head.rect.y += ObjectSize


        #if self.snake2.has(self.head):
        #    self.snake2.remove(self.head)
        if self.body_group.has(self.head):
            self.body_group.remove(self.head)


        self.collisionDetect(normal)
        #self.eaten = self.eat(apple)

        if self.eaten:
            self.body_group.add(self.head)
            #self.snake2.add(self.head)
            self.newSegment(self.color)
            #apple.randomPos(self.body_group)

    # Fa les comparacions de la posicio del cap de la serp per saber si esta colisionant amb qualsevol cosa
    def collisionDetect(self, normal):

        col = pygame.sprite.spritecollideany(self.head, self.body_group)
        if col:
            self.ok = False
        elif pygame.sprite.spritecollideany(self.head, obstacles):
            self.ok = False
        elif not normal:
            if self.head.rect.x < 0 or self.head.rect.x > (WIN_WIDTH - ObjectSize + 10):
                self.ok = False
            elif self.head.rect.y < 0 or self.head.rect.y > (WIN_HEIGHT - ObjectSize + 10):
                self.ok = False

    # Funcio que ens permet saber si el cap esta en la mateixa posicio que la poma i per tant
    # se la ha menjat
    def eat(self, apple):
        #sound = pygame.mixer.Sound("Beep.mp3")
        if self.head.rect.x == apple.rect.x and self.head.rect.y == apple.rect.y:
            #sound.play()
            return True
        else:
            return False

    # Crea un nou segment i el posa a la posicio antiga del cap abans de que aquest es mogui
    def newSegment(self, color):

        pos = [self.head.rect.x, self.head.rect.y]
        newSegment = Block(pos, False, color)

        #if self.color == GREEN2:
        self.body.insert(0, newSegment)
        self.body_group.add(newSegment)  # el segment als sprites del cos de la serp.
        self.bodySize = len(self.body)  # modifiquem la llargada del cos
        #else:
        #    self.body2.insert(0, newSegment)
        #    self.snake2.add(newSegment)
        #    self.bodySize = len(self.body2)  # modifiquem la llargada del cos

        all_sprites.add(newSegment)  # Afegim al grup de tots els sprites.


class Obstacles(object):  # barrier
    def __init__(self):
        self.obsatacles_arr = []
'''
    def pared(self, group):
        ran = 20  # range
        pos_x = (WIN_WIDTH / 2) - (ran / 2 * 20)  # WIN_WIDTH/2 - (range/2 * 20)
        pos_y = (WIN_HEIGHT / 2)
        pos = [pos_x, pos_y]
        for x in range(20):
            # afegim barrier a sprite
            barrier = Block(pos, False, YELLOW)
            pos_x += ObjectSize
            self.obsatacles_arr.append(barrier)
            obstacles.add(barrier)
            all_sprites.add(barrier)
            pos = [pos_x, pos_y]
'''