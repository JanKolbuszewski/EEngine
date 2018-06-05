package core;

import java.lang.reflect.*;

//import soundeffects.Sound;

public class GlobalInfo {
	
	public static void reportNow(Object ob)
	{
		System.out.println("Obiekt jest typu: "+ ob.getClass().getName());
		Field[] fieldsGiven = ob.getClass().getDeclaredFields();
		System.out.println("Obiekt zawiera:");
		for(int i=0;i<=fieldsGiven.length-1; i++)
		{
			fieldsGiven[i].setAccessible(true);
			System.out.print("Pole " + (i+1) + ": <<" + fieldsGiven[i].getType().getSimpleName() + " " + fieldsGiven[i].getName() + 
					">> o wartosci: ");
			try {
				if (fieldsGiven[i].get(ob) == null)
				{
					System.out.println("NULL");
				}
				else if (fieldsGiven[i].get(ob).toString() == "")
				{
					System.out.println("Non parsable");
				}
				else System.out.println(fieldsGiven[i].get(ob).toString());
			} catch (IllegalArgumentException e) {
				System.out.println("Error: reflection args @ report");
			} catch (IllegalAccessException e) {
				System.out.println("Error: reflection access @ report");
				e.printStackTrace();
			}
		}
		
		Object superObj = ob.getClass().getSuperclass();
		while (superObj.getClass().getName() != "java.lang.Class")
		{
			System.out.print("Sciezka dziedziczenia: ");
			System.out.print(superObj.getClass().getName() + ", ");
			superObj = superObj.getClass().getSuperclass();
		}
		
	}
	
	public static void reportNow(GameObject go)
	{
		reportNow((Object) go);
		String gc = go.isGraphicComponent ? "GC " : "";
		String ic = go.isInputComponent ? "IC " : "";
		String phc = go.isPhysicsComponent ? "PhC " : "";
		String sc = go.isSoundComponent ? "SC " : "";
		System.out.println(gc + ic + phc + sc);
	}
	
	public static void reportPositionIterable(PhysicsObject pho, Integer time)
	{
		if (pho == null)
		{
			System.out.println("No object to iterable report");
			return;
		}	
		Thread thr = new Thread(new Runnable()
		{			
			@Override
			public void run() {
				while (true)
				{
					System.out.println("X: " + pho.getX() + "; Y: " + pho.getY());
					try {
						Thread.sleep(time);
					} catch (InterruptedException e) {
						break;
					}
				}
				
			}
		});
		thr.start();
	}
/*
	public static void reportNow(Sound snd)
	{
		reportNow((Object) snd);
		String isOn = snd.isOn ? "Sound ON" : "Sound OFF";
		System.out.println(isOn + ", played " + snd.isPlayed + " times.");
	}
*/
}