import socket
from sense_hat import SenseHat
sense = SenseHat()

soc = socket.socket()
host = "192.168.178.46"
port = 8080
soc.bind((host, port))
soc.listen(5)

def move_up(event):
    conn.send("8")

def move_down(event):
    conn.send("2")
      
        
def move_left(event):
    conn.send("4")

def move_right(event):
    conn.send("6")
while True:
    conn, addr = soc.accept()
    print ("Got connection from", addr)
    conn.send("Got connection from pi")
    sense.stick.direction_down = move_down
    sense.stick.direction_up = move_up
    sense.stick.direction_left = move_left
    sense.stick.direction_right = move_right

