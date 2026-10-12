package com.practiceqa.stayease.data;

/** A payment card. These are test numbers that only the practice app accepts. */
public record Card(String number, String expiry, String cvv) {

  public static Card valid() {
    return new Card("4242424242424242", "1230", "123");
  }
}
