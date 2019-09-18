import java.util.concurrent.TimeUnit;
import processing.sound.*;
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
  size(300,600);

  fB1 = new Button("1 Bookstore & Cafeteria",25,350,250,50,0);
  allButtons[0] = fB1;
  soundsS[0] = new SoundFile(this, "audio/s1stfloor.wav");
  soundsUS[0] = new SoundFile(this, "audio/us1stfloor.wav");
  fB2 = new Button("2 Police",25,275,250,50,1);
  allButtons[1] = fB2;
  soundsS[1] = new SoundFile(this, "audio/s2ndfloor.wav");
  soundsUS[1] = new SoundFile(this, "audio/us2ndfloor.wav");
  fB3 = new Button("3 Library",25,200,250,50,2);
  allButtons[2] = fB3;
  soundsS[2] = new SoundFile(this, "audio/s3rdfloor.wav");
  soundsUS[2] = new SoundFile(this, "audio/us3rdfloor.wav");
  fB4 = new Button("4 Library Personnel",25,125,250,50,3);
  allButtons[3] = fB4;
  soundsS[3] = new SoundFile(this, "audio/s4thfloor.wav");
  soundsUS[3] = new SoundFile(this, "audio/us4thfloor.wav");
  fB5 = new Button("5 Admin",25,50,250,50,4);
  allButtons[4] = fB5;
  soundsS[4] = new SoundFile(this, "audio/s5thfloor.wav");
  soundsUS[4] = new SoundFile(this, "audio/us5thfloor.wav");
  bEmergency = new Button("Emergency", 25, 540, 250, 50, 5);
  allButtons[5] = bEmergency;
  soundsS[5] = new SoundFile(this, "audio/semergency.wav");
  soundsUS[5] = new SoundFile(this, "audio/usemergency.wav");
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

void mouseMoved(){
  for(int btn = 0; btn < 8; btn++){
    if(allButtons[btn].mOver()){
    }
  }
}

void mouseReleased(){
  
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
  
  void Draw() {
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
  
  boolean mOver(){
    if(mouseX > positionX && mouseX < widthSize && mouseY > positionY && mouseY < heightSize){
      mOver = true;
      return true;
    }
      mOver = false;
      return false;
  }
  
  void floorRequested(){
    if(mOver){
    requested = !requested;
    if(requested){
    soundsS[buttonPosition].play();
    }
    else{
    soundsUS[buttonPosition].play();
    }
    }
    
    if(requested&&!activeRequest){
      thread("elevator");
    }
  }
  
  void setText(String string){
  floorLabel = string;
  }
  
}

void checkActiveRequest(){
if(allButtons[0].requested||allButtons[1].requested||allButtons[2].requested||allButtons[3].requested||allButtons[4].requested||allButtons[5].requested||allButtons[6].requested){
  activeRequest = true;
}
else{
  activeRequest = false;
}
}

void elevator(){

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
