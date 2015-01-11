package samples.elib;

public class InternalUser extends User {

	String internalId;
	
	public InternalUser(String name, String addr, String phone, String id){
		super(name, addr, phone);
		internalId = id;
	}
	
	public boolean authorizedUser(){
		return true;
	}
}
