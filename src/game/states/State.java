package game.states;

import java.awt.Graphics;

import game.Handler;


public abstract class State {

	// STATE MANAGER
    private static State currentState;

    public static State getState(){
    	return currentState;
    }

    public static void setState(State state, int mode){
    	if(state instanceof GameState) {
    		((GameState) state).newGame(mode);
    	}
    	currentState = state;
    }
    
    protected Handler handler;
    public State(Handler handler) {
    	this.handler = handler;
    }
    
	public abstract void update();
	public abstract void render(Graphics g);
}
