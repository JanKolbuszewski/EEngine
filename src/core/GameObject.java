package core;

public class GameObject {
enum mType {Pane,Block,Ball}; //typy obiekotw do dodania
int mID;
boolean mGameState;
static int id_source = 997;

int mComponents;
long mLifeTime = 9999999;

long mLifeStart = System.nanoTime();

long mTimeLived() {
	long time = mLifeStart - System.nanoTime();
	return time;
}

public int GameObject(long life_time,String type ){
	mLifeTime = life_time;
	mID = id_source;
	id_source ++;
	mType = mType.valueOf(type); // ???????????????????????? jakis swich
	
	return mID;
}

void update() {
	
	if(mLifeTime<mTimeLived()) {
		kill();
	}
}

}
