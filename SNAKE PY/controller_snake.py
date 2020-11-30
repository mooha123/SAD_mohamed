import pygame 
import random 
import model_snake
import view_snake
import threading




class Detection():
    def __init__(self): 
        self.direction = [0, 1]

    
    def keyDetection(self):
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                    pygame.quit()

                # Llegim els key events per saber quina tecla estem prement en cada tic de rellotge
                # i prenem la decisio de si pot girar o no ja que no volem que pugui anar directament en la direccio
                # contraria a la que estava anant
                # donem la opcio de jugar tant amb les fletxes de direccio com amb W/A/S/D

            elif event.type == pygame.KEYDOWN:
                if event.key == pygame.K_UP or event.key == pygame.K_w:
                    return [0, 1]
                elif event.key == pygame.K_DOWN or event.key == pygame.K_s:
                    return [0, -1]
                elif event.key == pygame.K_RIGHT or event.key == pygame.K_d:
                    return [1, 0]
                elif event.key == pygame.K_LEFT or event.key == pygame.K_a: 
                    return [-1, 0]
                    
        return [0, 0]
                
    