int licht = A0;
int lichtwaarde =0;

  
 
  


void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  milis(500);

  
  
}

void loop() {
  // put your main code here, to run repeatedly:
    milis(1000);
    lichtwaarde = analogRead(licht);
    milis(5);
   
    Serial.print(lichtwaarde);
    Serial.flush();
    Serial.print('\n');  
    
  
}
