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
	static ArrayList<GameObject> ObjectList = new ArrayList<GameObject>();
	// updating array list
	void update()
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
	static GameObject setGameObject(long life_time,String name)
	{
		GameObject object = new GameObject(life_time,name);
		ObjectList.add(object);
		return object;
	}

}