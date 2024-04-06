package Management;

public class UserDB_Current 
{	//ประกาศเเบบ static ทำให้สามารถเข้าถึงค่าใน class นี้ได้ โดยไม่ต้องสรา้ง obj ใหม่
	public static int user_id;	
	public static String username;
	public static String password;
	public static String status;
	
	public UserDB_Current()
	{
	}
	
	public UserDB_Current(int xuser_id,String xusername
				,String xpassword,String xstatus)
	{
		user_id  = xuser_id;
		username = xusername;
		password = xpassword;
		status   = xstatus;
		
	}

}
