package core;

import java.util.ArrayList;

public class GameObject {
String name; //enum mType {Pane,Block,Ball}; //typy obiekotw do dodania
int mID;
boolean mGameState;
static int id_source = 997;
int mComponents = 0;
long mLifeTime = 9999999;
long mLifeStart = System.nanoTime();
long mTimeLived() {
	long time = mLifeStart - System.nanoTime();
	return time;
}

public int GameObject(long life_time,String name ){
	mLifeTime = life_time;
	mID = id_source;
	id_source ++;
	this.name = name;
	ArrayList<GameObjectComponent> ComponentList = new ArrayList<GameObjectComponent>();
	
	return mID;
}
void kill() {
	//destroy all components
}

void update() {
	
	if(mLifeTime<mTimeLived()) {
		kill();
	}else{
		//update component list
	}
}
int addComponent(GameObjectComponent newComponent){
	int id
	
	return 
}

}
