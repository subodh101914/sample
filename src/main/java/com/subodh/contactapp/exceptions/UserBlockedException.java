package com.subodh.contactapp.exceptions;

public class UserBlockedException extends Exception{
	public UserBlockedException() {
		
	}
	public UserBlockedException(String error) {
		super(error);
	}
}
