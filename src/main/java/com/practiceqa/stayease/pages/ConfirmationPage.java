package com.practiceqa.stayease.pages;

public class ConfirmationPage extends BasePage {

  public boolean isConfirmed() {
    return visible(testId("booking-confirmed")).isDisplayed();
  }

  public String reservationId() {
    return text(testId("reservation-id"));
  }

  public String room() {
    return text(testId("confirmed-room"));
  }

  public double total() {
    return amount(text(testId("confirmed-total")));
  }
}
