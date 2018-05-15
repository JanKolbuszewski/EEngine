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
	
	long mTimeLived() {
		long time = mLifeStart - System.nanoTime();
		return time;
	}
	
	public GameObject(long life_time,String name)
	{
		mLifeTime = life_time;
		mID = id_source;
		id_source ++;
		this.name = name;
	}
	
	void kill() 
	{
		//kill
	}
	//updating objects
	void update() 
	{
		
		if(mLifeTime<mTimeLived()) 
		{
			kill();
		}
		else if(Process.mKill)
		{
			kill();
		}
		if(isPhysicsComponent) 
		{
			PhysicsComponent.update();
		}
		if(isInputComponent) 
		{
			InputComponent.update();
		}
		if(isSoundComponent) 
		{
			SoundComponent.update();
		}
		if(isGraphicComponent) 
		{
			GraphicComponent.update();
		}
	}
	//adding components ex: ball.addPhysicsComponentO(parameters);
	void addPhysicsComponent() {
		new PhysicsComponent();
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

}
