import pygame, sys
import model_snake
import pygame.freetype
from pygame.sprite import Sprite
from enum import Enum
import cliente


BLUE = (106, 159, 181)
GREEN =(46, 204, 113)
WHITE = (255, 255, 255)
BLACK = (0, 0, 0)

def create_surface_with_text(text, font_size, text_rgb, bg_rgb):
    """ Returns surface with text written on """
    font = pygame.freetype.SysFont("Courier", font_size, bold=False)
    # create a Font object from the system fonts
    surface, _ = font.render(text=text, fgcolor=text_rgb, bgcolor=bg_rgb)
    # change the pixel format of an image including per pixel alphas
    return surface.convert_alpha()

class Element(Sprite):
    """ An user interface element that can be added to a surface """

    def __init__(self, center_position, text, font_size, bg_rgb, text_rgb, action = None):
        """
        Args:
            center_position (x, y)
            text - string of text to write
            font_size - int
            bg_rgb (background colour) - tuple (r, g, b)
            text_rgb (text colour) - tuple (r, g, b)
        """
        self.mouse_over = False  # indicates if the mouse is over the element

        # create the default image
        default_image = create_surface_with_text(
            text=text, font_size=font_size, text_rgb=text_rgb, bg_rgb=bg_rgb
        )

        # create the image that shows when mouse is over the element
        highlighted_image = create_surface_with_text(
            text=text, font_size=font_size * 1.2, text_rgb=text_rgb, bg_rgb=bg_rgb
        )

        # add both images and their rects to lists
        self.images = [default_image, highlighted_image]
        self.rects = [
            default_image.get_rect(center=center_position),
            highlighted_image.get_rect(center=center_position),
        ]

        # calls the init method of the parent sprite class
        super().__init__()
        self.action = action

    @property
    def image(self):
        return self.images[1] if self.mouse_over else self.images[0]

    @property
    def rect(self):
        return self.rects[1] if self.mouse_over else self.rects[0]
 
    def update(self, mouse_pos, mouse_up):
        if self.rect.collidepoint(mouse_pos):
            self.mouse_over = True
            if mouse_up: 
                return self.action
        else:
            self.mouse_over = False

    def draw(self, surface):
        """ Draws element onto a surface """
        surface.blit(self.image, self.rect)

class GameState(Enum):
    QUIT = -1
    TITLE = 0
    NEWGAME = 1
    LEVELS = 2
    NEWGAMEX = 3
    MULTI = 4

def levels_screen(screen):
    noob = Element(
        center_position=(100, 150),
        font_size=30,
        bg_rgb=GREEN,
        text_rgb=BLACK,
        text="NOOB",
        action=GameState.NEWGAME,
    )
    expert = Element(
        center_position=(100, 200),
        font_size=30,
        bg_rgb=GREEN,
        text_rgb=BLACK,
        text="EXPERT",
        action=GameState.NEWGAMEX,
    )
    twoSnakes = Element(
        center_position=(100, 250),
        font_size=30,
        bg_rgb=GREEN,
        text_rgb=BLACK,
        text="Two Players",
        action=GameState.MULTI,
    )
    quit = Element(
        center_position=(100, 350),
        font_size=30,
        bg_rgb=GREEN,
        text_rgb=BLACK,
        text="QUIT",
        action=GameState.QUIT,
    )
    image_snake_x = 300
    image_snake_y = 100
    speed_x = 3
    #reloj
    clock = pygame.time.Clock()

    buttons = [noob, expert, twoSnakes, quit]

    while True:
        mouse_up = False
        for event in pygame.event.get():
            if event.type == pygame.MOUSEBUTTONUP and event.button == 1: #si
                mouse_up = True
            elif event.type == pygame.QUIT:
                return GameState.QUIT
        # Poner fondo verde
        screen.fill(GREEN)
        #dibujar
        IMG = pygame.image.load('snake_image.png')
        #animacion
        if image_snake_x <= 500:
            image_snake_x = image_snake_x + speed_x
        else: image_snake_x = 300

        screen.blit(IMG, (image_snake_x, image_snake_y))

        for button in buttons:
            ui_action = button.update(pygame.mouse.get_pos(), mouse_up) # detecta si s'ha fet click a sobre
            if ui_action is not None:
                return ui_action
            button.draw(screen)


        pygame.display.flip()
        clock.tick(20)

def title_screen(screen):
    play = Element(
        center_position=(100, 150),
        font_size=30,
        bg_rgb=GREEN,
        text_rgb=BLACK,
        text="Play snake",
        action=GameState.LEVELS,
    )
    quit = Element(
        center_position=(100, 250),
        font_size=30,
        bg_rgb=GREEN,
        text_rgb=BLACK,
        text="Quit",
        action=GameState.QUIT,
    )
    image_snake_x = 300
    image_snake_y = 100
    speed_x = 3
    #reloj
    clock = pygame.time.Clock()

    buttons = [play, quit]

    while True:
        mouse_up = False
        for event in pygame.event.get():
            if event.type == pygame.MOUSEBUTTONUP and event.button == 1:
                mouse_up = True
            elif event.type == pygame.QUIT:
                return GameState.QUIT
        # Poner fondo verde
        screen.fill(GREEN)
        #dibujar
        IMG = pygame.image.load('snake_image.png')
        #animacion
        if image_snake_x <= 500:
            image_snake_x = image_snake_x + speed_x
        else: image_snake_x = 300

        screen.blit(IMG, (image_snake_x, image_snake_y))

        for button in buttons:
            ui_action = button.update(pygame.mouse.get_pos(), mouse_up)
            if ui_action is not None:
                return ui_action
            button.draw(screen)


        pygame.display.flip()
        clock.tick(20)


class Window():
    def __init__(self, width, height):
        #self.myfont = pygame.font.Font('freesansbold.ttf', 14)
        self.gameWindow = pygame.display.set_mode([width, height])
        pygame.display.set_caption('Snake Game')

    def update(self, group, score):
        self.gameWindow.fill(WHITE)
        scoretext = self.myfont.render(score, True, (255, 255, 255), (0, 0, 0))
        self.gameWindow.blit(scoretext, (5, 10))
        group.draw(self.gameWindow)
        # actualizar pantalla
        pygame.display.flip()


def main():
    pygame.init()

    screen = pygame.display.set_mode((600, 400))
    game_state = GameState.TITLE
    velocitatNoob = 5
    velocitatExpert = 5

    while True:
        if game_state == GameState.TITLE:
            game_state = title_screen(screen)

        if game_state == GameState.LEVELS:
            game_state = levels_screen(screen)

        if game_state == GameState.NEWGAME:
            model_snake.main(velocitatNoob, True, True)
            game_state = GameState.TITLE
            screen = pygame.display.set_mode((600, 400))

        if game_state == GameState.NEWGAMEX:
            #ficar una altre screen
            model_snake.main(velocitatExpert, False, True)
            game_state = GameState.TITLE
            screen = pygame.display.set_mode((600, 400))

        if game_state == GameState.MULTI:
            cliente.main(velocitatExpert, True, False)
            game_state = GameState.TITLE
            screen = pygame.display.set_mode((600, 400))

        if game_state == GameState.QUIT:
            sys.exit()
            return