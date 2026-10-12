package com.practiceqa.stayease.data;

/** The person making the booking. All data is fictional. */
public record Guest(String firstName, String lastName, String email, String phone, String arrival) {

  public static Guest valid() {
    return new Guest("Ada", "Lovelace", "ada@example.com", "5551234567", "14:00 – 16:00");
  }

  public Guest withEmail(String email) {
    return new Guest(firstName, lastName, email, phone, arrival);
  }

  public Guest withPhone(String phone) {
    return new Guest(firstName, lastName, email, phone, arrival);
  }
}
