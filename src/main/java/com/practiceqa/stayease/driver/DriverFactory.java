package com.practiceqa.stayease.driver;

import com.practiceqa.stayease.config.Config;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Map;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Creates the browser named in the configuration, locally or on a Selenium Grid.
 * Selenium Manager downloads the matching driver, so no driver binaries live in this repo.
 */
public final class DriverFactory {
  private static final Dimension WINDOW = new Dimension(1366, 900);

  private DriverFactory() {}

  public static WebDriver create() {
    Capabilities options = optionsFor(Config.browser());
    WebDriver driver = Config.gridUrl().isBlank() ? local(options) : remote(options);
    driver.manage().window().setSize(WINDOW);
    return driver;
  }

  private static Capabilities optionsFor(String browser) {
    return switch (browser.toLowerCase()) {
      case "chrome" -> chrome();
      case "firefox" -> firefox();
      case "edge" -> edge();
      default -> throw new IllegalArgumentException("Unsupported browser: " + browser + " (use chrome, firefox or edge)");
    };
  }

  private static ChromeOptions chrome() {
    ChromeOptions options = new ChromeOptions();
    if (Config.headless()) options.addArguments("--headless=new");
    // Chrome's password pop-ups steal focus from the page, so later typing would be lost.
    options.setExperimentalOption("prefs", Map.of(
        "credentials_enable_service", false,
        "profile.password_manager_enabled", false,
        "profile.password_manager_leak_detection", false));
    return options;
  }

  private static FirefoxOptions firefox() {
    FirefoxOptions options = new FirefoxOptions();
    if (Config.headless()) options.addArguments("-headless");
    return options;
  }

  private static EdgeOptions edge() {
    EdgeOptions options = new EdgeOptions();
    if (Config.headless()) options.addArguments("--headless=new");
    return options;
  }

  private static WebDriver local(Capabilities options) {
    if (options instanceof ChromeOptions chrome) return new ChromeDriver(chrome);
    if (options instanceof FirefoxOptions firefox) return new FirefoxDriver(firefox);
    return new EdgeDriver((EdgeOptions) options);
  }

  private static WebDriver remote(Capabilities options) {
    try {
      return new RemoteWebDriver(URI.create(Config.gridUrl()).toURL(), options);
    } catch (MalformedURLException e) {
      throw new IllegalArgumentException("grid.url is not a valid URL: " + Config.gridUrl(), e);
    }
  }
}
