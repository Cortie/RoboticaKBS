import socket
import time
from sense_hat import SenseHat
sense = SenseHat()

temp = sense.get_temperature()
temp = round(temp,1)
temp2 = round(temp,1)
temp = str(temp)
temp2 = int(temp2)

pressure = sense.get_pressure()
pressure = round(pressure,1)
pressure = str(pressure)

humidity = sense.get_humidity()
humidity = round(humidity,1)
humidity = str(humidity)

tempSetting = 0
lightSetting = 0
lightValue = 0

def my_Sensors():
    Temp = "Temperatuur: " + temp + "]"
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

def switch_heater_on():
    sense.set_pixel(0,0,255,0,0)
def switch_heater_off():
    sense.set_pixel(0,0,0,0,0)

soc = socket.socket()
host = "192.168.0.124"
port = 8080
soc.bind((host, port))
soc.listen(2)

while True:
    conn, addr = soc.accept()
    print ("Got connection from", addr)
    data = conn.recv(1024)
    data = data.decode()
    print(data)


    [lightValue,lightSetting,tempSetting] = data.split("|")
    tempSetting = int(tempSetting)
    lightValue = int(lightValue)
    lightSetting = int(lightSetting)
    my_Sensors()
    print("light Int value: ", lightValue)
    if lightValue < lightSetting and temp2 < tempSetting:
        switch_lights_on()
        switch_heater_on()

    if lightValue >= lightSetting and temp2 < tempSetting:
        switch_lights_off()
        switch_heater_on()

    if lightValue < lightSetting and temp2 >= tempSetting:
        switch_lights_on()
        switch_heater_off()

    if lightValue >= lightSetting and temp2 >= tempSetting:
        sense.clear()


    conn.send(my_Sensors.Sdata)
    time.sleep(0.5)


