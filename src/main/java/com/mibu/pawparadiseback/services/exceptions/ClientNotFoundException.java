package com.mibu.pawparadiseback.services.exceptions;

public class ClientNotFoundException extends RuntimeException {
  public ClientNotFoundException(String message) {
    super(message);
  }
}
