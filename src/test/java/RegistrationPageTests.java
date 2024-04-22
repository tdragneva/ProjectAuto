import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import java.io.IOException;

public class RegistrationPageTests extends BrowserManager{

    @BeforeSuite
    public void setUpSuite() throws IOException {
        setupTestSuite();
    }

    @BeforeMethod
    public void setUpTests() {
        initializeDriver();
    }

    @AfterMethod
    public void tearDownTests(ITestResult testResult) {
        tearDownTest(testResult);
    }

    @AfterSuite
    public void tearDownSuite() throws IOException {
        deleteDownloadFiles();
    }

    @Test(priority = 1)
    public void registerLinkNavigation() {
        RegistrationPage registrationPage = new RegistrationPage(getDriver());
        registrationPage.navigateToLandingPage();
        Assert.assertTrue(registrationPage.isLandingPageLoaded(), "Landing page not loaded.");
        registrationPage.clickLoginLink();
        registrationPage.clickRegisterLink();
        Assert.assertTrue(registrationPage.isRegistrationUrlLoaded(), "Registration page not loaded");
    }

    @Test(priority = 2)
    public void registrationElementFieldsEnabled() {
        RegistrationPage registrationPage = new RegistrationPage(getDriver());
        registrationPage.navigateToRegistration();
        Assert.assertTrue(registrationPage.isRegistrationUrlLoaded(), "Registration page not loaded");

        Assert.assertTrue(RegistrationPage.usernameField.isEnabled(), "Username field not enabled.");
        Assert.assertTrue(RegistrationPage.emailField.isEnabled(), "Email field not enabled");
        Assert.assertTrue(RegistrationPage.passwordField.isEnabled(), "Password field not enabled.");
        Assert.assertTrue(RegistrationPage.confirmPasswordField.isEnabled(), "Confirm password field not enabled.");
    }

    @Test(priority = 3)
    public void usernameEmailFieldsDataSubmission() {
        RegistrationPage registrationPage = new RegistrationPage(getDriver());
        registrationPage.navigateToRegistration();
        Assert.assertTrue(registrationPage.isRegistrationUrlLoaded(), "Registration page not loaded");

        String username = RegistrationPage.generateRandomUsername();
        String email = RegistrationPage.generateRandomEmail();

        registrationPage.enterUsername(username);
        registrationPage.enterEmail(email);

        Assert.assertEquals(registrationPage.getUsernameFieldValue(), username, "The entered username does not match the filled in username.");
        Assert.assertEquals(registrationPage.getEmailFieldValue(), email, "The entered email does not match the filled in email.");
    }

    @Test(priority = 4)
    public void passwordsMatchValidation() {
        RegistrationPage registrationPage = new RegistrationPage(getDriver());
        registrationPage.navigateToRegistration();
        Assert.assertTrue(registrationPage.isRegistrationUrlLoaded(), "Registration page not loaded");

        String password = RegistrationPage.generateRandomPassword();

        registrationPage.enterPassword(password);
        registrationPage.confirmPassword(password);

        Assert.assertTrue(registrationPage.arePasswordsMatching(), "The entered password in the first field does not match the password in the second field.");
    }

    @Test(priority = 5)
    public void isSignInButtonEnabled() {
        RegistrationPage registrationPage = new RegistrationPage(getDriver());
        registrationPage.navigateToRegistration();
        Assert.assertTrue(registrationPage.isRegistrationUrlLoaded(), "Registration page not loaded");

        Assert.assertTrue(registrationPage.isSignInButtonEnabled(), "The Sign in button is not enabled.");
    }

    @Test(priority = 6)
    public void redirectionAfterRegistration() {
        RegistrationPage registrationPage = new RegistrationPage(getDriver());
        registrationPage.navigateToRegistration();
        Assert.assertTrue(registrationPage.isRegistrationUrlLoaded(), "Registration page not loaded");

        String username = RegistrationPage.generateRandomUsername();
        String email = RegistrationPage.generateRandomEmail();
        String password = RegistrationPage.generateRandomPassword();

        registrationPage.enterUsername(username);
        registrationPage.enterEmail(email);
        registrationPage.enterPassword(password);
        registrationPage.confirmPassword(password);

        registrationPage.clickSignInButton();

        Assert.assertTrue(registrationPage.isRedirectedAfterRegistration(), "Not redirected after registration");
    }
}
