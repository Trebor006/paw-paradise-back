package com.mibu.pawparadiseback.services.exceptions;

public class PersonNotFoundException extends RuntimeException {
  public PersonNotFoundException(String message) {
    super(message);
  }
}
