#define left 2
#define right 3
void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  //Serial.println("controller");
}

void loop() {
  // put your main code here, to run repeatedly:
  if(digitalRead(left))
  {
    Serial.println(500);
  }
  if(digitalRead(right))
  {
    Serial.println(1000);
  }
  delay(10);
}
