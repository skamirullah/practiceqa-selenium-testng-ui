package com.practiceqa.stayease.tests;

import static org.assertj.core.api.Assertions.assertThat;

import com.practiceqa.stayease.base.BaseTest;
import com.practiceqa.stayease.data.Hotels;
import com.practiceqa.stayease.pages.ResultsPage;
import com.practiceqa.stayease.pages.SearchPage;
import io.qameta.allure.Feature;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Feature("Searching for a stay")
public class SearchTests extends BaseTest {

  @Test(groups = "smoke", description = "A search with a city and a date lists stays")
  public void searchListsStays() {
    ResultsPage results = searchPage().searchFor(Hotels.GOA);

    assertThat(results.hotelNames()).contains(Hotels.PALM_GROVE_NAME);
  }

  @Test(description = "The form asks for a destination and a check-in date")
  public void emptySearchIsRejected() {
    SearchPage search = searchPage().submit();

    assertThat(search.fieldError("city")).isEqualTo("Choose a destination");
    assertThat(search.fieldError("checkin")).isEqualTo("Pick a check-in date");
  }

  @DataProvider(parallel = true)
  public Object[][] cities() {
    return new Object[][] {{"goa"}, {"dubai"}, {"paris"}, {"bali"}};
  }

  @Test(dataProvider = "cities", description = "The count line matches the number of stays listed")
  public void countMatchesTheListedStays(String city) {
    ResultsPage results = searchPage().searchFor(city);

    int listed = results.cardCount();

    assertThat(results.resultCount()).startsWith(listed + " propert");
  }
}
