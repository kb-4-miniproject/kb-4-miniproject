package jdbc.exception;

public class RecordNotFoundException {
	public RecordNotFoundException() {
	    this ("사용자를 찾을 수 없습니다!");
	  }
	  
	  public RecordNotFoundException (String msg) {
	    super ();
	  }
}
