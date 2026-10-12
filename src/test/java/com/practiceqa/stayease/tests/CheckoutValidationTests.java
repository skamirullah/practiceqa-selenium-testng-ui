package com.practiceqa.stayease.tests;

import static org.assertj.core.api.Assertions.assertThat;

import com.practiceqa.stayease.base.BaseTest;
import com.practiceqa.stayease.data.Guest;
import com.practiceqa.stayease.data.Hotels;
import com.practiceqa.stayease.pages.CheckoutPage;
import io.qameta.allure.Feature;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Feature("Checkout validation")
public class CheckoutValidationTests extends BaseTest {

  private CheckoutPage checkout() {
    return searchPage().searchFor(Hotels.GOA).viewRooms(Hotels.PALM_GROVE_ID).selectRoom("standard").continueToBooking();
  }

  @Test(description = "Submitting an empty form lists what is missing")
  public void emptyFormShowsEveryError() {
    CheckoutPage checkout = checkout().submit();

    assertThat(checkout.errorSummary()).matches("Please fix \\d+ errors below\\.");
    assertThat(checkout.fieldError("guest-first-name")).isEqualTo("First name is required");
    assertThat(checkout.fieldError("guest-last-name")).isEqualTo("Last name is required");
    assertThat(checkout.fieldError("arrival-time")).isEqualTo("Choose an arrival time");
    assertThat(checkout.fieldError("accept-policy")).isEqualTo("Please accept the cancellation policy");
  }

  @DataProvider
  public Object[][] invalidGuests() {
    return new Object[][] {
        {"guest-email", Guest.valid().withEmail("ada-at-example"), "Enter a valid email"},
        {"guest-phone", Guest.valid().withPhone("12345"), "Phone must be 10 digits"},
    };
  }

  @Test(dataProvider = "invalidGuests", description = "An invalid contact detail is flagged on its own field")
  public void invalidContactDetailIsFlagged(String field, Guest guest, String message) {
    CheckoutPage checkout = checkout().fillGuest(guest).payAtHotel().acceptPolicy().submit();

    assertThat(checkout.fieldError(field)).isEqualTo(message);
    assertThat(checkout.errorSummary()).isEqualTo("Please fix 1 error below.");
  }

  @Test(description = "The cancellation policy must be accepted")
  public void policyMustBeAccepted() {
    CheckoutPage checkout = checkout().fillGuest(Guest.valid()).payAtHotel().submit();

    assertThat(checkout.fieldError("accept-policy")).isEqualTo("Please accept the cancellation policy");
  }
}
