from sense_hat import SenseHat

sense = SenseHat()
sense.clear()


humid = sense.get_humidity()
humid = round(humid,1)
print(humid)