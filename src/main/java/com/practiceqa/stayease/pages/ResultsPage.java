package com.practiceqa.stayease.pages;

import io.qameta.allure.Step;
import java.util.List;
import org.openqa.selenium.WebElement;

public class ResultsPage extends BasePage {

  @Step("Keep only {stars}-star stays")
  public ResultsPage filterByStars(int stars) {
    check(testId("star-" + stars));
    return this;
  }

  @Step("Sort by {order}")
  public ResultsPage sortBy(String order) {
    select(testId("sort"), order);
    return this;
  }

  /** Waits for the count line to read e.g. "2 properties", which also means the list has re-rendered. */
  public ResultsPage waitForCount(String expected) {
    waitForText(testId("result-count"), expected);
    return this;
  }

  public String resultCount() {
    return text(testId("result-count"));
  }

  public List<String> hotelNames() {
    return atLeast(testId("hotel-name"), 1).stream().map(WebElement::getText).toList();
  }

  public List<Double> prices() {
    return atLeast(testId("hotel-price"), 1).stream().map(price -> amount(price.getText())).toList();
  }

  public int cardCount() {
    return atLeast(testId("hotel-card"), 1).size();
  }

  @Step("Open hotel {hotelId}")
  public HotelPage viewRooms(String hotelId) {
    click(testId("view-rooms-" + hotelId));
    waitForUrl("/apps/hotel/hotel/" + hotelId);
    return new HotelPage();
  }
}
