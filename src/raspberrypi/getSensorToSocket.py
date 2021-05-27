import socket
import time
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

def my_Sensors():
    Temp = "[Temperatuur: " + temp + "]"
    print(Temp)
    Tdata = Temp.encode()
    
    Pressure = "Pressure: (" + pressure + ")"
    print(Pressure)
    Pdata = Pressure.encode()
    
    Humid = "Humidity: |" + humidity
    print(Humid)
    Hdata = Humid.encode()
    my_Sensors.Sdata = Tdata + Pdata + Hdata
    
def switch_lights_on():
    sense.clear(255,255,255)
def switch_lights_off():
    sense.clear()



soc = socket.socket()
host = "192.168.0.124"
port = 8080
soc.bind((host, port))
soc.listen(2)

while True:
    conn, addr = soc.accept()
    print ("Got connection from", addr)
    data = conn.recv(1024)
    data = int.from_bytes(data, "big")
    print("light Int value: ", data)
    if data < 150:
        switch_lights_on()
    else:
        switch_lights_off()
    my_Sensors()
    conn.send(my_Sensors.Sdata)
    time.sleep(0.5)


