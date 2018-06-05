package core;

import java.util.ArrayList;

public class Process {
	static boolean mKill = false;
	boolean mActive = false;
	boolean mPaused = false;
	boolean mIsAttached = false;
	boolean mIsInitialised = false;
	int mNext = 987654321;
	
	// object list
	public static ArrayList<GameObject> ObjectList = new ArrayList<GameObject>();
	// updating array list
	public void update()
	{
		for(GameObject G : ObjectList)
		{
			G.update();
		}
	}
	//kill all objects on list
	void kill()
	{
		for(GameObject G : ObjectList)
		{
			G.kill();
		}
	}
	
	// create object ex: (creting ball object living 100sec) "GameObject ball = Main.setGameObject(100000,"ball");"
	public static GameObject setGameObject(long life_time, String name, boolean isPhysicsComponent1, boolean isInputComponent1 ,boolean isSoundComponent1  ,boolean isGraphicComponent1)
	{
		GameObject object = new GameObject(life_time,name, isPhysicsComponent1, isInputComponent1, isSoundComponent1, isGraphicComponent1);
		ObjectList.add(object);
		return object;
	}

}