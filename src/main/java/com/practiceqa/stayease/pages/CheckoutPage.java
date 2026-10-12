package com.practiceqa.stayease.pages;

import com.practiceqa.stayease.data.Card;
import com.practiceqa.stayease.data.Guest;
import io.qameta.allure.Step;

public class CheckoutPage extends BasePage {

  @Step("Fill in the guest details")
  public CheckoutPage fillGuest(Guest guest) {
    type(testId("guest-first-name"), guest.firstName());
    type(testId("guest-last-name"), guest.lastName());
    type(testId("guest-email"), guest.email());
    type(testId("guest-phone"), guest.phone());
    select(testId("arrival-time"), guest.arrival());
    return this;
  }

  @Step("Pay now by card")
  public CheckoutPage payNow(Card card) {
    click(testId("pay-now"));
    type(testId("card-number"), card.number());
    type(testId("card-expiry"), card.expiry());
    type(testId("card-cvv"), card.cvv());
    return this;
  }

  @Step("Choose to pay at the hotel")
  public CheckoutPage payAtHotel() {
    click(testId("pay-at-hotel"));
    return this;
  }

  public CheckoutPage acceptPolicy() {
    check(testId("accept-policy"));
    return this;
  }

  public double total() {
    return amount(text(testId("checkout-total")));
  }

  /** Submits without expecting success; used by validation tests. */
  public CheckoutPage submit() {
    click(testId("confirm-booking"));
    return this;
  }

  @Step("Confirm the booking")
  public ConfirmationPage confirm() {
    submit();
    waitForUrl("/apps/hotel/confirmation");
    return new ConfirmationPage();
  }

  public String errorSummary() {
    return text(testId("form-errors"));
  }
}
