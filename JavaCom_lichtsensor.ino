int licht = A0;
int lichtwaarde =0;






void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);



}

void loop() {
  // put your main code here, to run repeatedly:

    delay(100);
    lichtwaarde = analogRead(licht);
    Serial.println(lichtwaarde);



}