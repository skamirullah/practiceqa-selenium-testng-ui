package com.practiceqa.stayease.tests;

import static org.assertj.core.api.Assertions.assertThat;

import com.practiceqa.stayease.base.BaseTest;
import com.practiceqa.stayease.data.Card;
import com.practiceqa.stayease.data.Guest;
import com.practiceqa.stayease.data.Hotels;
import com.practiceqa.stayease.pages.CheckoutPage;
import com.practiceqa.stayease.pages.ConfirmationPage;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;

@Feature("Booking a stay")
public class BookingTests extends BaseTest {

  private CheckoutPage checkoutFor(String room) {
    return searchPage().searchFor(Hotels.GOA).viewRooms(Hotels.PALM_GROVE_ID).selectRoom(room).continueToBooking();
  }

  @Test(groups = "smoke", description = "A guest books a deluxe room with breakfast and pays by card")
  public void booksAStayAndPaysByCard() {
    CheckoutPage checkout = searchPage().searchFor(Hotels.GOA)
        .filterByStars(5)
        .sortBy("price-asc")
        .viewRooms(Hotels.PALM_GROVE_ID)
        .selectRoom("deluxe")
        .addAddon("breakfast")
        .continueToBooking();

    ConfirmationPage confirmation = checkout.fillGuest(Guest.valid()).payNow(Card.valid()).acceptPolicy().confirm();

    assertThat(confirmation.isConfirmed()).isTrue();
    assertThat(confirmation.reservationId()).matches("SE-\\w+");
    assertThat(confirmation.room()).isEqualTo("Deluxe Room");
  }

  @Test(description = "A guest can book without a card by paying at the hotel")
  public void booksAStayPayingAtTheHotel() {
    ConfirmationPage confirmation = checkoutFor("standard").fillGuest(Guest.valid()).payAtHotel().acceptPolicy().confirm();

    assertThat(confirmation.isConfirmed()).isTrue();
    assertThat(confirmation.room()).isEqualTo("Standard Room");
  }

  @Test(description = "The confirmed total is the total shown at checkout")
  public void confirmedTotalMatchesCheckout() {
    CheckoutPage checkout = checkoutFor("suite");
    double quoted = checkout.total();

    ConfirmationPage confirmation = checkout.fillGuest(Guest.valid()).payAtHotel().acceptPolicy().confirm();

    assertThat(confirmation.total()).isEqualTo(quoted);
  }
}
