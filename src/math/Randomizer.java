package math;
import java.util.Random;
import java.util.LinkedList;
import java.util.List;

public class Randomizer 
{
	Random random;
	
	public Randomizer()
	{
		random = new Random();
	}

	public int newInt(int min, int max)
	{
		return random.nextInt(max + 1 - min) + min;
	}
	
    public List<Integer> newIntList(int listSize, int min, int max)
    {
        List<Integer> list = new LinkedList<>();
        for(int i=0; i<listSize; i++)
            list.add(newInt(min, max));

        return list;
    }
    
    public int[] newIntArray(int arraySize, int min, int max)
    {
    	int[] array = new int[arraySize];
        for(int i=0;i<arraySize;i++)
            array[i] = (newInt(min, max));
        
        return array;
    }   
	
	public float newFloat(int min, int max)
	{
		return random.nextFloat()*(max + 1 - min) + min;
	}	
	
    public List<Float> newFloatList(float listSize, int min, int max)
    {
        List<Float> list = new LinkedList<>();
        for(float i=0; i<listSize; i++)
            list.add(newFloat(min, max));

        return list;
    }
	
    public float[] newFloaArray(int arraySize, int min, int max)
    {
    	float[] array = new float[arraySize];
        for(int i=0;i<arraySize;i++)
            array[i] = (newInt(min, max));
        
        return array;
    }  
}