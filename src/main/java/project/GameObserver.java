package project;

public class GameObserver implements Observer {
    Character faceObserv; 
    int Index = 0; 
    int boardSize = 8;
    boolean gameOver = false;
    public GameObserver(Character faceObserv){
        this.faceObserv = faceObserv; 
    }

    @Override
    public void update(Character face, int newIndex) {
        if (face == faceObserv){
            this.Index = newIndex;
            System.out.println(this.Index);
        }
        if(this.Index == boardSize){
            fireGameOver();
        }
        
    }

    public void fireGameOver(){
        System.out.println("Game over");
        gameOver = true;
    }

    public boolean getIsGameOver(){
        return this.gameOver;
    }

    public Character getFaceObserv(){
        return this.faceObserv;
    }
    
}
