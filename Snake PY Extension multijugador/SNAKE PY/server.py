import socket
#import model_snake
#import view_snake
from _thread import *
from player import Player
#from snake import Apple
import pickle  # serializacion, almacenar informacion en un documento binario
#import time

RED = (255, 0, 0)
YELLOW = (191, 255, 0)
GREEN2 = (0, 155, 0)
WHITE = (255, 255, 255)
BLUE = (22, 229, 201)

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

server = "localhost"
port = 5555

server_ip = socket.gethostbyname(server)

try:
    s.bind((server, port))

except socket.error as e:
    print(str(e))

s.listen(2)
print("[START] Waiting for a connection")

#posHead = [(100, 100), (100, 200)]
posApple = (200, 140)
#appleS = Apple(RED)
#(self, posHead, body, applePos, color)
players = [Player((100, 100), (200, 140), BLUE), Player((100, 200), (200, 140), GREEN2)]

def threaded_client(conn, plays):
    global currentId, pos
    conn.send(pickle.dumps(players[plays]))
    #print("posicion enviada" + posHead[player]))
    reply = ""

    while True:
        try:
            data = pickle.loads(conn.recv(2048))
            players[plays] = data

            if not data:
                conn.send(str.encode("Goodbye"))
                break

            #if players[plays].eated:
            #    posApple = players[plays].applePos


            if plays == 1:
                reply = players[0]
            else:
                reply = players[1]

            #reply.applePos = posApple

            print("Recieved: ")
            print("Sending: ")

            conn.sendall(pickle.dumps(reply))
        except:
            break

    print("Connection Closed")
    conn.close()


plays = 0
while True:
    conn, addr = s.accept()
    print("Connected to: ", addr)

    start_new_thread(threaded_client, (conn, plays))
    plays += 1

    '''
                            arr = reply.split(":")
                            id = int(arr[0])
                            pos[id] = reply

                            if id == 0: nid = 1
                            if id == 1: nid = 0
                            reply = pos[nid][:]'''
