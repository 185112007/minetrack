package tr.com.minetrack.depo;

public class ParseIntTest
{

    public static void main(String[] args)
    {
	int tagid;
	try
	{
	    tagid = Integer.parseInt("62160f50");
	    System.out.println(tagid);
	} catch (Exception e)
	{
	    System.out.println("Tag.number format exception!!!");
	    return;
	}
    }

}
