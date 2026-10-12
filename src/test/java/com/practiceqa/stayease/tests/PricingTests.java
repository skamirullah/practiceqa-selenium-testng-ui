package com.practiceqa.stayease.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import com.practiceqa.stayease.base.BaseTest;
import com.practiceqa.stayease.data.Hotels;
import com.practiceqa.stayease.pages.HotelPage;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;

@Feature("Room pricing")
public class PricingTests extends BaseTest {
  private static final double ONE_CENT = 0.01;

  private HotelPage palmGrove() {
    return searchPage().searchFor(Hotels.GOA).viewRooms(Hotels.PALM_GROVE_ID);
  }

  @Test(description = "A room must be chosen before the booking can continue")
  public void roomIsRequiredToContinue() {
    HotelPage hotel = palmGrove();

    assertThat(hotel.title()).contains(Hotels.PALM_GROVE_NAME);
    assertThat(hotel.promptsForARoom()).isTrue();
    assertThat(hotel.canContinue()).isFalse();
  }

  @Test(description = "The total is the room price plus 12% taxes")
  public void totalIsRoomPlusTaxes() {
    HotelPage hotel = palmGrove().selectRoom("standard");

    assertThat(hotel.taxes()).isCloseTo(hotel.roomTotal() * Hotels.TAX_RATE, within(ONE_CENT));
    assertThat(hotel.total()).isCloseTo(hotel.roomTotal() + hotel.taxes(), within(ONE_CENT));
    assertThat(hotel.canContinue()).isTrue();
  }

  @Test(description = "An add-on is added to the total and taxed")
  public void addonIsAddedAndTaxed() {
    HotelPage hotel = palmGrove().selectRoom("deluxe");
    double withoutAddon = hotel.total();

    hotel.addAddon("breakfast");

    assertThat(hotel.addonTotal()).isPositive();
    assertThat(hotel.taxes()).isCloseTo((hotel.roomTotal() + hotel.addonTotal()) * Hotels.TAX_RATE, within(ONE_CENT));
    assertThat(hotel.total()).isGreaterThan(withoutAddon)
        .isCloseTo(hotel.roomTotal() + hotel.addonTotal() + hotel.taxes(), within(ONE_CENT));
  }
}
