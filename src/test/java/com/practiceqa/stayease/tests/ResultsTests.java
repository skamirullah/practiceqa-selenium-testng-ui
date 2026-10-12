package com.practiceqa.stayease.tests;

import static org.assertj.core.api.Assertions.assertThat;

import com.practiceqa.stayease.base.BaseTest;
import com.practiceqa.stayease.data.Hotels;
import com.practiceqa.stayease.pages.ResultsPage;
import io.qameta.allure.Feature;
import java.util.Comparator;
import org.testng.annotations.Test;

@Feature("Filtering and sorting results")
public class ResultsTests extends BaseTest {

  @Test(description = "The star filter keeps only stays with that rating")
  public void filtersByStarRating() {
    ResultsPage results = searchPage().searchFor(Hotels.GOA).filterByStars(5).waitForCount("2 properties");

    assertThat(results.hotelNames()).containsExactlyInAnyOrder("Palm Grove Resort", "Coral Cove Villas");
  }

  @Test(description = "Sorting by price lists the cheapest stay first")
  public void sortsByPriceLowToHigh() {
    ResultsPage results = searchPage().searchFor(Hotels.GOA).sortBy("price-asc");

    assertThat(results.prices()).hasSizeGreaterThan(1).isSortedAccordingTo(Comparator.naturalOrder());
  }

  @Test(description = "Filter and sort work together")
  public void filterAndSortCombine() {
    ResultsPage results = searchPage().searchFor(Hotels.GOA).filterByStars(5).sortBy("price-asc").waitForCount("2 properties");

    assertThat(results.hotelNames()).containsExactly("Palm Grove Resort", "Coral Cove Villas");
  }
}
