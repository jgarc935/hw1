import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.concurrent.TimeUnit; 
import processing.sound.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class sketch_elevator1 extends PApplet {



SoundFile s1s;
SoundFile s1us;

int[] elevatorPosition = new int[5];
SoundFile[] soundsS = new SoundFile[6];
SoundFile[] soundsUS = new SoundFile[6];
int currentFloor = 0;
boolean activeRequest = false;
Button fB1;
Button fB2;
Button fB3;
Button fB4;
Button fB5;
Button bOpen;
Button bClose;
Button fDisplay;
Button bEmergency;
Button[] allButtons = new Button[9];

public void setup(){
  

  fB1 = new Button("1 Bookstore & Cafeteria",25,350,250,50,0);
  allButtons[0] = fB1;
  soundsS[0] = new SoundFile(this, "data/s1stfloor.wav");
  soundsUS[0] = new SoundFile(this, "data/us1stfloor.wav");
  fB2 = new Button("2 Police",25,275,250,50,1);
  allButtons[1] = fB2;
  soundsS[1] = new SoundFile(this, "data/s2ndfloor.wav");
  soundsUS[1] = new SoundFile(this, "data/us2ndfloor.wav");
  fB3 = new Button("3 Library",25,200,250,50,2);
  allButtons[2] = fB3;
  soundsS[2] = new SoundFile(this, "data/s3rdfloor.wav");
  soundsUS[2] = new SoundFile(this, "data/us3rdfloor.wav");
  fB4 = new Button("4 Library Personnel",25,125,250,50,3);
  allButtons[3] = fB4;
  soundsS[3] = new SoundFile(this, "data/s4thfloor.wav");
  soundsUS[3] = new SoundFile(this, "data/us4thfloor.wav");
  fB5 = new Button("5 Admin",25,50,250,50,4);
  allButtons[4] = fB5;
  soundsS[4] = new SoundFile(this, "data/s5thfloor.wav");
  soundsUS[4] = new SoundFile(this, "data/us5thfloor.wav");
  bEmergency = new Button("Emergency", 25, 540, 250, 50, 5);
  allButtons[5] = bEmergency;
  soundsS[5] = new SoundFile(this, "data/semergency.wav");
  soundsUS[5] = new SoundFile(this, "data/usemergency.wav");
  bClose = new Button("CLOSE",175,425,100,100, 6);
  allButtons[6] = bClose;
  bOpen = new Button("OPEN",25,425,100,100, 7);
  allButtons[7] = bOpen;
  fDisplay = new Button("Current Floor:"+(currentFloor+1),5,10,290,25, 8);
  allButtons[8] = fDisplay;

}

public void draw(){
  background(150);
  
  fB5.Draw();
  fB4.Draw();
  fB3.Draw();
  fB2.Draw();
  fB1.Draw();
  bOpen.Draw();
  bClose.Draw();
  bEmergency.Draw();
  fDisplay.Draw();
}

public void mouseMoved(){
  for(int btn = 0; btn < 8; btn++){
    if(allButtons[btn].mOver()){
    }
  }
}

public void mouseReleased(){
  
  for(int btn = 0; btn < 8; btn++){
    if(allButtons[btn].mOver()){
      allButtons[btn].floorRequested();
    }
  }
}


class Button {
  
  String floorLabel;
  int positionX;
  int positionY;
  int width;
  int height;
  boolean requested;
  boolean mOver;
  int widthSize;
  int heightSize;
  int buttonPosition;
  
  Button(String fl, int x, int y, int w, int h, int p) {
  floorLabel = fl;
  positionX = x;
  positionY = y;
  width = w;
  height = h;
  requested = false;
  mOver = false;
  widthSize = positionX + width;
  heightSize = positionY + height;
  buttonPosition = p;
  }
  
  public void Draw() {
    if(mOver){
    fill(color(255,255,179));
    rect(positionX, positionY, width, height);
    textSize(20);
    textAlign(CENTER, CENTER);
    fill(0);
    text(floorLabel, positionX + (width / 2), positionY + (height / 2));
    }
    else if(requested){
    fill(color(255,255,0));
    rect(positionX, positionY, width, height);
    textSize(20);
    textAlign(CENTER, CENTER);
    fill(0);
    text(floorLabel, positionX + (width / 2), positionY + (height / 2));
    }
    else if(requested){
    fill(255);
    rect(positionX, positionY, width, height);
    textSize(20);
    textAlign(CENTER, CENTER);
    fill(0);
    text(floorLabel, positionX + (width / 2), positionY + (height / 2));
    }
    else{
    fill(255);
    rect(positionX, positionY, width, height);
    textSize(20);
    textAlign(CENTER, CENTER);
    fill(0);
    text(floorLabel, positionX + (width / 2), positionY + (height / 2));
    }
  }
  
  public boolean mOver(){
    if(mouseX > positionX && mouseX < widthSize && mouseY > positionY && mouseY < heightSize){
      mOver = true;
      return true;
    }
      mOver = false;
      return false;
  }
  
  public void floorRequested(){
    if(mOver){
    requested = !requested;
    if(requested){
      if(buttonPosition<6)
    soundsS[buttonPosition].play();
    }
    else{
      if(buttonPosition<6)
    soundsUS[buttonPosition].play();
    }
    }
    
    if(requested&&!activeRequest){
      thread("elevator");
    }
  }
  
  public void setText(String string){
  floorLabel = string;
  }
  
}

public void checkActiveRequest(){
if(allButtons[0].requested||allButtons[1].requested||allButtons[2].requested||allButtons[3].requested||allButtons[4].requested||allButtons[5].requested||allButtons[6].requested){
  activeRequest = true;
}
else{
  activeRequest = false;
}
}

public void elevator(){

  checkActiveRequest();
  boolean up = true;
  delay(3000);
  
   while(activeRequest){
     println("active request");
     
if(allButtons[5].requested){
  delay(5000);
  allButtons[5].requested = false;
}

if(allButtons[6].requested){
  delay(3000);
  allButtons[6].requested = false;
}
    while(up){
      println("Going up");
      for(int requestCheck = currentFloor; requestCheck <5; requestCheck++){
        println("Request check:" +requestCheck);
        if(allButtons[requestCheck].requested){
          for(int actualFloor = currentFloor; actualFloor<5; actualFloor++){
          allButtons[8].setText("Current floor: "+ (actualFloor+1));
          delay(5000);
          //sleep to simulate process.
          if(allButtons[actualFloor].requested){
            allButtons[actualFloor].requested =false;
            //open door select door button
            // set a sleep delay
            //close door
          currentFloor = actualFloor;
          println("Current Floor looop: " + currentFloor);
          }
        }
      }
    }
    
   up =false;
  }
  while(!up){
    println("going down");
          for(int requestCheck = currentFloor; requestCheck >=0; requestCheck--){
        if(allButtons[requestCheck].requested){
          for(int actualFloor = currentFloor; actualFloor>=0; actualFloor--){
          allButtons[8].setText("Current floor:"+(actualFloor+1));
          delay(5000);
          //sleep to simulate process.
          if(allButtons[actualFloor].requested){
            allButtons[actualFloor].requested =false;
            //open door select door button
            // set a sleep delay
            //close door
            currentFloor = actualFloor;
          }
        }
      }
  }
  up = true;
    }
       checkActiveRequest();
  }

//}

} 
  public void settings() {  size(300,600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "sketch_elevator1" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
