import socket
from sense_hat import SenseHat
sense = SenseHat()

temp = sense.get_temperature()
temp = round(temp,1)
temp = str(temp)

pressure = sense.get_pressure()
pressure = round(pressure,1)
pressure = str(pressure)

humidity = sense.get_humidity()
humidity = round(humidity,1)
humidity = str(humidity)

def my_temp():
    Temp = "Temperatuur: " + temp
    print(Temp)
    Tdata = Temp.encode()
    conn.send(Tdata)
    
def my_pressure():
    Pressure = "Pressure: " + pressure
    print(Pressure)
    Pdata = Pressure.encode()
    conn.send(Pdata)
    
def my_humidity():
    Humid = "Humidity: " + humidity
    print(Humid)
    Hdata = Humid.encode()
    conn.send(Hdata)

soc = socket.socket()
host = "192.168.0.124"
port = 8080
soc.bind((host, port))
soc.listen(5)

while True:
    conn, addr = soc.accept()
    print ("Got connection from", addr)
    my_temp()
    my_pressure()
    my_humidity()