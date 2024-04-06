package Management;

public class UserDB 
{
		//ประกาศเเบบ static ทำให้สามารถเข้าถึงค่าใน class นี้ได้ โดยไม่ต้องสรา้ง obj ใหม่
		public  int user_id;	
		public  String username;
		public  String password;
		public  String status;
		
		public UserDB()
		{
		}
		
		public UserDB(int xuser_id,String xusername
					,String xpassword,String xstatus)
		{
			user_id  = xuser_id;
			username = xusername;
			password = xpassword;
			status   = xstatus;
		}	
		
	
}
