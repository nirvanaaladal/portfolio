#include <Wire.h>
#include "MAX30105.h"
#include "heartRate.h"
#include <Adafruit_MPU6050.h>
#include <Adafruit_Sensor.h>
#include <WiFi.h>
#include <WiFiUdp.h>
#include <Adafruit_GPS.h>
#include <SPI.h>
#include "pitches.h"

#define GPSSerial Serial1

Adafruit_GPS GPS(&GPSSerial);
#define GPSECHO false

const char* ssid = "HUAWEI MatePad Pro";
const char* password = "123456789";

WiFiUDP Udp;

IPAddress udpServerIP(172, 20, 10, 3);
unsigned int udpServerPort = 8888;

uint32_t timer = millis();

MAX30105 particleSensor;

const byte RATE_SIZE = 4; //Increase this for more averaging. 4 is good.
byte rates[RATE_SIZE]; //Array of heart rates
byte rateSpot = 0;
long lastBeat = 0; //Time at which the last beat occurred

float beatsPerMinute;
int beatAvg;

Adafruit_MPU6050 mpu;
unsigned long startTime = 0; 

// Melodies
//excellent 
int melody1[] = {
  NOTE_C4, NOTE_E4, NOTE_G4, NOTE_C5, NOTE_G4, NOTE_E4,
  NOTE_C4, NOTE_E4, NOTE_G4, NOTE_C5, NOTE_G4, NOTE_E4,
  NOTE_C4, NOTE_E4, NOTE_G4, NOTE_C5, NOTE_G4, NOTE_E4,
  NOTE_C4, NOTE_E4, NOTE_G4, NOTE_C5, NOTE_G4, NOTE_E4,
  NOTE_C4, NOTE_E4, NOTE_G4, NOTE_C5, NOTE_G4, NOTE_E4
};
int noteDurations1[] = {
4, 4, 4, 4, 4, 4,
4, 4, 4, 4, 4, 4,
4, 4, 4, 4, 4, 4,
4, 4, 4, 4, 4, 4,
4, 4, 4, 4, 4, 4,
4, 4, 4, 4, 4, 4

};


// focus 
int melody2[] = {

NOTE_A5, 0, NOTE_A5, 0, NOTE_A5, 0,
NOTE_A5, 0, NOTE_A5, 0, NOTE_A5, 0,
NOTE_A5, 0, NOTE_A5, 0, NOTE_A5, 0,
NOTE_A5, 0, NOTE_A5, 0, NOTE_A5, 0,
NOTE_A5, 0, NOTE_A5, 0, NOTE_A5, 0,
NOTE_A5, 0, NOTE_A5, 0, NOTE_A5, 0
};
int noteDurations2[] = {
4, 4, 4, 4, 4, 4,
4, 4, 4, 4, 4, 4,
4, 4, 4, 4, 4, 4,
4, 4, 4, 4, 4, 4,
4, 4, 4, 4, 4, 4,
4, 4, 4, 4, 4, 4

};


int melody3[] = { // Für Elise (first few notes)
  NOTE_E5, NOTE_DS5, NOTE_E5, NOTE_DS5, NOTE_E5, NOTE_B4, NOTE_D5, NOTE_C5, NOTE_A4
};
int noteDurations3[] = {
  16, 16, 16, 16, 16, 16, 16, 16, 16
};


void setup() {

    Serial.begin(115200);
  Serial.println("Initializing...");

Serial.print("Connecting to ");
  Serial.println(ssid);
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.println("Connecting to WiFi...");
  }
  Serial.println("Connected to WiFi");

  // Start the UDP server
  Udp.begin(udpServerPort);

   // Start UDP for speaker
//  Udp.begin(localPort);
  Serial.print("Listening for UDP packets on port: ");
  Serial.println(udpServerPort);

 

//HR
    delay(5); // will pause Zero, Leonardo, etc until serial console opens

  // Initialize sensor
  if (!particleSensor.begin(Wire, I2C_SPEED_FAST)) //Use default I2C port, 400kHz speed
  {
    Serial.println("MAX30105 was not found. Please check wiring/power. ");
    while (1);
  }
  Serial.println("Place your index finger on the sensor with steady pressure.");

  particleSensor.setup(); //Configure sensor with default settings
  particleSensor.setPulseAmplitudeRed(0x0A); //Turn Red LED to low to indicate sensor is running
  particleSensor.setPulseAmplitudeGreen(0); //Turn off Green LED

//mpu
while (!Serial)
    delay(10); 

  Serial.println("Adafruit MPU6050 test!");

  if (!mpu.begin()) {
    Serial.println("Failed to find MPU6050 chip");
    while (1) {
      delay(10);
    }
  }
  Serial.println("MPU6050 Found!");

  mpu.setHighPassFilter(MPU6050_HIGHPASS_0_63_HZ);
  mpu.setMotionDetectionThreshold(1);
  mpu.setMotionDetectionDuration(20);
  mpu.setInterruptPinLatch(true); 
  mpu.setInterruptPinPolarity(true);
  mpu.setMotionInterrupt(true);

  startTime = millis(); 

  Serial.println("");
  
  //gps
  GPS.begin(9600);
  GPS.sendCommand(PMTK_SET_NMEA_OUTPUT_RMCGGA);
 
  GPS.sendCommand(PMTK_SET_NMEA_UPDATE_1HZ); // 1 Hz update rate

  GPS.sendCommand(PGCMD_ANTENNA);

  GPSSerial.println(PMTK_Q_RELEASE);

}

void loop() {
//for speaker
    int packetSize = Udp.parsePacket();
  if (packetSize) {
    char incomingPacket[packetSize + 1];
    int len = Udp.read(incomingPacket, packetSize);
    if (len > 0) {
      incomingPacket[len] = 0;
    }

    int melodySelector = atoi(incomingPacket);
    playMelody(melodySelector);
  }

long irValue = particleSensor.getIR();

  if (checkForBeat(irValue) == true)
  {
    //We sensed a beat!
    long delta = millis() - lastBeat;
    lastBeat = millis();

    beatsPerMinute = 60 / (delta / 1000.0);

    if (beatsPerMinute < 255 && beatsPerMinute > 20)
    {
      rates[rateSpot++] = (byte)beatsPerMinute; //Store this reading in the array
      rateSpot %= RATE_SIZE; //Wrap variable

      //Take average of readings
      beatAvg = 0;
      for (byte x = 0 ; x < RATE_SIZE ; x++)
        beatAvg += rates[x];
      beatAvg /= RATE_SIZE;
    }
  }

  Serial.print("IR=");
  Serial.print(irValue);
  Serial.print(", BPM=");
  Serial.print(beatsPerMinute);
  Serial.print(", Avg BPM=");
  Serial.print(beatAvg);

  if (irValue < 50000)
    Serial.print(" No finger?");

  Serial.println();


  //mpu
  sensors_event_t a, g, temp;
  mpu.getEvent(&a, &g, &temp);

  unsigned long currentTime = millis(); // get the current time
  long time = currentTime - startTime;
  float accelx = a.acceleration.x;
  float accely = a.acceleration.y;
  float accelz = a.acceleration.z;
  float gyrox = g.gyro.x;
  float gyroy = g.gyro.y;
  float gyroz = g.gyro.z;

 // if(mpu.getMotionInterruptStatus()) {
    /* Get new sensor events with the readings */

    /* Print out the values */
    Serial.print("Time:");
    Serial.print(time); // print the elapsed time
    Serial.print(", ");
    Serial.print("AccelX:");
    Serial.print(a.acceleration.x);
    Serial.print(",");
    Serial.print("AccelY:");
    Serial.print(a.acceleration.y);
    Serial.print(",");
    Serial.print("AccelZ:");
    Serial.print(a.acceleration.z);
    Serial.print(", ");
    Serial.print("GyroX:");
    Serial.print(g.gyro.x);
    Serial.print(",");
    Serial.print("GyroY:");
    Serial.print(g.gyro.y);
    Serial.print(",");
    Serial.print("GyroZ:");
    Serial.print(g.gyro.z);
    Serial.println("");

//  }

  
//gps
 // GPS
// GPS
  char c = GPS.read();
 // long latitude = (GPS.latitude, 4);
 // char lat = GPS.lat;
  //long longitude = (GPS.longitude, 4);
 // char lon = GPS.lon;


  if (GPSECHO)
    if (c) Serial.print(c);
  if (GPS.newNMEAreceived()) {

  //  Serial.print(GPS.lastNMEA());
    if (!GPS.parse(GPS.lastNMEA()))
      return;
  }
  if (millis() - timer > 2000) {
    timer = millis();  // reset the timer
    Serial.print("\nTime: ");
    if (GPS.hour < 10) { Serial.print('0'); }
    Serial.print(GPS.hour, DEC);
    Serial.print(':');
    if (GPS.minute < 10) { Serial.print('0'); }
    Serial.print(GPS.minute, DEC);
    Serial.print(':');
    if (GPS.seconds < 10) { Serial.print('0'); }
    Serial.print(GPS.seconds, DEC);
    Serial.print('.');
    if (GPS.milliseconds < 10) {
      Serial.print("00");
    } else if (GPS.milliseconds > 9 && GPS.milliseconds < 100) {
      Serial.print("0");
    }
    Serial.println(GPS.milliseconds);
    Serial.print("Date: ");
    Serial.print(GPS.day, DEC);
    Serial.print('/');
    Serial.print(GPS.month, DEC);
    Serial.print("/20");
    Serial.println(GPS.year, DEC);

    if (GPS.fix) {
      Serial.print("Latitude: ");
      Serial.print(GPS.latitude, 4);
      Serial.print("Longitude: ");
      Serial.print(GPS.longitude, 4);

    }
  }
//for ml
 // String message =  "Time=" + String(time) + ",AccelX=" + String(accelx) + ",AccelY=" + String(accely) + ",AccelZ=" + String(accelz) + ",GyroX=" + String(gyrox) + ",GyroY=" + String(gyroy) + ",GyroZ=" + String(gyroz);
//for all
 
 // String message =  "IR=" + String(irValue) + ",BPM=" + String(beatsPerMinute) + ",Avg BPM=" + String(beatAvg) + ",Time=" + String(time) + ",AccelX=" + String(accelx) + ",AccelY=" + String(accely) + ",AccelZ=" + String(accelz) + ",GyroX=" + String(gyrox) + ",GyroY=" + String(gyroy) + ",GyroZ=" + String(gyroz) + ",Latitude=" + String(GPS.latitude, 4) + ",Longitude=" + String(GPS.longitude, 4);
 
  String message =  "IR=" + String(irValue) + ",BPM=" + String(beatsPerMinute) + ",Avg BPM=" + String(beatAvg) + ",Time=" + String(time) + ",AccelX=" + String(accelx) + ",AccelY=" + String(accely) + ",AccelZ=" + String(accelz) + ",GyroX=" + String(gyrox) + ",GyroY=" + String(gyroy) + ",GyroZ=" + String(gyroz) + ",Latitude=" + String(GPS.latitude, 4) + ",Longitude=" + String(GPS.longitude, 4) + ",Speed=" +(GPS.speed) + ",Distance=" + (GPS.speed);

  Udp.beginPacket(udpServerIP, udpServerPort);
  Udp.write(message.c_str(), message.length());
  Udp.endPacket();

}

void playMelody(int melodySelector) {
  int *melody;
  int *noteDurations;
  int melodyLength;

  switch (melodySelector) {
    case 1:
      melody = melody1;
      noteDurations = noteDurations1;
      melodyLength = sizeof(melody1) / sizeof(melody1[0]);
      break;
    case 2:
      melody = melody2;
      noteDurations = noteDurations2;
      melodyLength = sizeof(melody2) / sizeof(melody2[0]);
      break;
    case 3:
      melody = melody3;
      noteDurations = noteDurations3;
      melodyLength = sizeof(melody3) / sizeof(melody3[0]);
      break;
    default:
      return; // Ignore invalid values
  }

  for (int thisNote = 0; thisNote < melodyLength; thisNote++) {
    int noteDuration = 1000 / noteDurations[thisNote];
    tone(6, melody[thisNote], noteDuration);

    int pauseBetweenNotes = noteDuration * 1.30;
    delay(pauseBetweenNotes);
    noTone(8);
  }
}


