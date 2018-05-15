package core;

public class Main {

	public static void main(String[] args) 
	{
		
		Process xdi = new core.Process();
		
		

		GameObject kulka = xdi.setGameObject(10000,"kulka",true,false,false,false);
		
		for(GameObject G : Process.ObjectList)
		{
			G.update();
		}
		
	}

}
