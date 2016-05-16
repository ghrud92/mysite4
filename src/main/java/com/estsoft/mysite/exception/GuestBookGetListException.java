package com.estsoft.mysite.exception;

@SuppressWarnings("serial")
public class GuestBookGetListException extends RuntimeException {
	
	public GuestBookGetListException() {
		super("Exception occurs getting guestbook list");
	}
	
	public GuestBookGetListException(String message){
		System.out.println(message);
	}
	
}
