package we.pet.exception;

public class DuplicateIdException extends Exception {
	public DuplicateIdException(){
		this("이미 있는 회원이십니다. ");
	}
	public DuplicateIdException(String message){
		super(message);
	}
}
