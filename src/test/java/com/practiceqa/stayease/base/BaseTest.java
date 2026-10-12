package com.practiceqa.stayease.base;

import com.practiceqa.stayease.driver.DriverManager;
import com.practiceqa.stayease.pages.SearchPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/** Gives every test its own fresh browser and closes it afterwards, pass or fail. */
public abstract class BaseTest {

  @BeforeMethod(alwaysRun = true)
  public void startBrowser() {
    DriverManager.start();
  }

  @AfterMethod(alwaysRun = true)
  public void closeBrowser() {
    DriverManager.quit();
  }

  protected SearchPage searchPage() {
    return new SearchPage().open();
  }
}
