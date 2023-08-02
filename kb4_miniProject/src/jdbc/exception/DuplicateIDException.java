package jdbc.exception;

public class DuplicateIDException {
	public DuplicateIDException() {
	    this ("이미 존재하는 사용자입니다!");
	  }
	  
	  public DuplicateIDException (String msg) {
	    super ();
	  }

}
