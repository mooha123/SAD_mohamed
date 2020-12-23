
class Player (object): # el Player va a tener las posiciones de todos los objetos, vamos a crear un arra de posiciones para cada conjunto.
    # Array1: cuerpo snake.
    # apple.
    #body = []

    def __init__(self, posHead, applePos, color):
        self.color = color
        self.applePos = applePos
        self.posHead = posHead
        self.eated = False
        self.direction = [1, 0]
        self.ok = True
        self.start = False
        self.steps = 0

    def getPosHead(self):
        return self.posHead

    #def getBody(self):
    #    return self.body

    def getApplePos(self):
        return self.applePos

    def getColor(self):
        return self.color
