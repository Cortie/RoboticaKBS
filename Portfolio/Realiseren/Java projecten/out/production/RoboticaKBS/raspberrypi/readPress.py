from sense_hat import SenseHat

sense = SenseHat()
sense.clear()


Pressure = sense.get_pressure()
Pressure = round(Pressure,1)
print(Pressure)