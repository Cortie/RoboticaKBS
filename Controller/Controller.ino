#define left 2
#define right 3
void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:
  if(digitalRead(left))
  {
    goLeft();
  }
  if(digitalRead(right))
  {
    goRight();
  }
  delay(100);
}
void goLeft()
{
  Serial.println(1000);
}
void goRight()
{
  Serial.println(500);
}
