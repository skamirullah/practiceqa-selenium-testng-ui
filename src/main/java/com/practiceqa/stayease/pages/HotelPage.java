package com.practiceqa.stayease.pages;

import io.qameta.allure.Step;

public class HotelPage extends BasePage {

  public String title() {
    return text(testId("hotel-title"));
  }

  @Step("Select the {room} room")
  public HotelPage selectRoom(String room) {
    click(testId("select-room-" + room));
    visible(testId("price-total"));
    return this;
  }

  @Step("Add the {addon} add-on")
  public HotelPage addAddon(String addon) {
    check(testId("addon-" + addon));
    visible(testId("price-addons"));
    return this;
  }

  public boolean canContinue() {
    return visible(testId("continue-booking")).isEnabled();
  }

  public boolean promptsForARoom() {
    return isPresent(testId("no-room-selected"));
  }

  public double roomTotal() {
    return amount(text(testId("price-rooms")));
  }

  public double addonTotal() {
    return amount(text(testId("price-addons")));
  }

  public double taxes() {
    return amount(text(testId("price-taxes")));
  }

  public double total() {
    return amount(text(testId("price-total")));
  }

  @Step("Continue to checkout")
  public CheckoutPage continueToBooking() {
    click(testId("continue-booking"));
    waitForUrl("/apps/hotel/checkout");
    return new CheckoutPage();
  }
}
