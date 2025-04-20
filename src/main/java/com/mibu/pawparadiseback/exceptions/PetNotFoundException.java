package com.mibu.pawparadiseback.exceptions;

public class PetNotFoundException extends RuntimeException {
  public PetNotFoundException(String message) {
    super(message);
  }
}
