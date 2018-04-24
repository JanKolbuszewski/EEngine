package core;

import java.lang.reflect.*;

public class GlobalInfo {
	
	public static void reportNow(Object ob)
	{
		System.out.println("Obiekt jest typu: "+ ob.getClass().getName());
		Field[] fieldsGiven = ob.getClass().getDeclaredFields();
		System.out.println("Obiekt zawiera:");
		for(int i=0;i<=fieldsGiven.length; i++)
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
}
