package cucumber.features;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepDefinitions {
	
	//Ideally no webdriver code should be in this class - only links to methods, and asserts
	//Hard-coded URLs, IDs, xpath should be seperate, ideally part of a step argument transformation
	
	//Initialise driver 
	WebDriver driver = null;
	//Set up string to search for and add to cart
	String SearchItem = "Extreme Rings";
	
	@Given("^I have added an item to my cart$")
	public void LoadURLandSearchandAddToCart() throws Throwable {
	    // Start driver
		driver = new FirefoxDriver();
		//Navigate to URL (should be in a seperate method
		driver.navigate().to("http://www.allswim.co.uk");
		//Enter text in Search field
		driver.findElement(By.id("small-searchterms")).sendKeys(SearchItem);
		//Click Search button
		driver.findElement(By.xpath("//input[@value='Search']")).click();
		//Add first item in search results to cart
		driver.findElement(By.xpath("//input[@value='Add To Cart']")).click();
	}

	@When("^I view the contents of my cart$")
	public void ViewCart() throws Throwable {
	//Click on shopping cart link
	//Try alternative (top) link if banner link doesn't work
			try
			{
				driver.findElement(By.linkText("shopping cart")).click();
			}
			catch (Exception ignore)
			{
				driver.findElement(By.id("topcartlink")).click();;
			}
	}

	@Then("^I should see the contents of the cart inlcude the item$")
	public void CheckCartPageTitleAndContents() throws Throwable {
		//Check you've arrived at the shopping cart page
		Assert.assertTrue("Failed to arrive at Shopping Cart page", driver.getTitle().equals("Shopping Cart"));
		//Check that item is in the results table
		String xpathSearch = String.format("//table//*[text()[contains(.,'%s')]]", SearchItem);
		Assert.assertNotNull(driver.findElement(By.xpath(xpathSearch)));
		
		//Close browser - again, this shouldn't be in this class
		driver.quit();
	    
	}


}
