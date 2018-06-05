package core;

import java.util.ArrayList;

public class GameObject {
	//id
	String name;
	int mID;
	static int id_source = 997;
	//lifetime
	long mLifeTime = 9999999;
	long mLifeStart = System.nanoTime();
	//Components
	boolean isPhysicsComponent  = false;
	boolean isInputComponent  = false;
	boolean isSoundComponent  = false;
	boolean isGraphicComponent  = false;
	
	PhysicsObject physix;
	InputComponent input; 
	SoundComponent sound; 
	GraphicComponent graphic;
	
	
	//position
	int x = 0;
	int y = 0;
	
	long mTimeLived() {
		long time = mLifeStart - System.nanoTime();
		return time;
	}
	
	public GameObject(long life_time, String name, boolean isPhysicsComponent1, boolean isInputComponent1 ,boolean isSoundComponent1  ,boolean isGraphicComponent1)
	{
		mLifeTime = life_time;
		mID = id_source;
		id_source ++;
		this.name = name;
		
		this.isPhysicsComponent = isPhysicsComponent1;
		this.isInputComponent = isInputComponent1;
		this.isSoundComponent = isSoundComponent1;
		this.isGraphicComponent = isGraphicComponent1;
	}
	
	void setPosition(int x, int y) 
	{
		this.x = x;
		this.y = y;
	}
	
	void kill() 
	{
		//kill
	}
	//updating objects
	public void update() 
	{
		
		if(mLifeTime<mTimeLived()) 
		{
			kill();
			return;
		}
		else if(Process.mKill)
		{
			kill();
			return;
		}
		
		if(isPhysicsComponent) 
		{
			//physix.update();
		}
		if(isInputComponent) 
		{
			input.update();
		}
		if(isSoundComponent) 
		{
			sound.update();
		}
		if(isGraphicComponent) 
		{
			graphic.update();
		}
		
	}
	
	/*
	adding components ex: ball.addPhysicsComponentO(parameters);
	void addPhysicsComponent() {
		PhysicsComponent physix = new PhysicsComponent();
		isPhysicsComponent  = true;
		
	}
	void addInputComponent() {
		isInputComponent  = true;
	}
	void addSoundComponent() {
		isSoundComponent  = true;
	}
	void addGraphicComponent() {
		isGraphicComponent  = true;
	}
	 */

}
