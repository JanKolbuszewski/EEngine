package core;

import java.util.ArrayList;

public class Process {
boolean mKill = false;
boolean mActive = false;
boolean mPaused = false;
boolean mIsAttached = false;
boolean mIsInitialised = false;
int mNext = 987654321;

static ArrayList<GameObject> ObjectList = new ArrayList<GameObject>();

void update()
{
	
	for(1) 
	{
		
		
	}
	
}
static GameObject setGameObject(long life_time,String name)
{
	
	
	GameObject object = new GameObject(life_time,name);
	ObjectList.add(object);
	
	return object;
}

public void xd() {
	GameObject ball = Process.setGameObject(10000,"xd");
	
	
	
}

void kill()
{
	
}

}