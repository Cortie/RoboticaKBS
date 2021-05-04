from sense_hat import SenseHat
import time
import sys

sense = SenseHat()
sense.clear()

temp = sense.get_temperature()
temp = round(temp,1)
print(temp)
