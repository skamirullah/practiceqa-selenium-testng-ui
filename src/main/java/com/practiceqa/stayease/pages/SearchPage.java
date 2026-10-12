package com.practiceqa.stayease.pages;

import io.qameta.allure.Step;

public class SearchPage extends BasePage {

  @Step("Open the StayEase search page")
  public SearchPage open() {
    open("/apps/hotel");
    visible(testId("hotel-search-form"));
    return this;
  }

  public SearchPage chooseCity(String city) {
    select(testId("city"), city);
    return this;
  }

  /** Check-in days are offered as chips; index 0 is the first selectable day. */
  public SearchPage pickCheckInDay(int index) {
    atLeast(testId("checkin-day"), index + 1).get(index).click();
    return this;
  }

  public SearchPage addNights(int extra) {
    for (int i = 0; i < extra; i++) click(testId("nights-inc"));
    return this;
  }

  /** Submits the form without expecting it to succeed; used by validation tests. */
  public SearchPage submit() {
    click(testId("search-hotels"));
    return this;
  }

  @Step("Search for stays in {city}")
  public ResultsPage searchFor(String city) {
    chooseCity(city).pickCheckInDay(2).addNights(1).submit();
    waitForUrl("/apps/hotel/results");
    return new ResultsPage();
  }
}
