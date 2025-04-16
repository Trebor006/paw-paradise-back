package com.mibu.pawparadiseback.services.exceptions;

public class PetNotFoundException extends RuntimeException {
  public PetNotFoundException(String message) {
    super(message);
  }
}
