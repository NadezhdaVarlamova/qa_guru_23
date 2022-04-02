package gmail.com.varlamvanadia1996.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import gmail.com.varlamvanadia1996.helpers.Attach;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
import static org.openqa.selenium.By.linkText;
import static org.openqa.selenium.By.partialLinkText;

public class IssuesTests {
    private static final String REPOSITORY = "NadezhdaVarlamova/qa_guru_6";
    private static final String TITLE = "Hi";

    @BeforeAll
    static void setUp() {        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        String browser = System.getProperty("browser", "chrome");
        String version = System.getProperty("version", "91");
        String remoteUrl = System.getProperty("remoteUrl", "selenoid.autotests.cloud/wd/hub");
        String login = System.getProperty("login", "user1");
        String pass = System.getProperty("pass", "1234");
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1920x1080";
        Configuration.browser = browser;
        Configuration.browserVersion = version;

        String url = "https://" + login + ":" + pass + "@" + remoteUrl;

        Configuration.remote = url;

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;

        Attach.attachAsText("Browser: ", browser);
        Attach.attachAsText("Version: ", version);
        Attach.attachAsText("Remote Url: ", remoteUrl);
    }

    @Test
    @AllureId("8139")
    @DisplayName("Проверяем наличие Issue с заголовком")
    @Tags({@Tag("crit"), @Tag("web")})
    @Owner("allure8")
    @Feature("GitIssue")
    public void lambdaStepsTestIssueName() {
        Allure.parameter("Репозиторий", REPOSITORY);
        Allure.parameter("Заголовок Issue", TITLE);
        step("Открываем страницу", () -> {
            open("https://github.com/");});
        step("Поиск репозитория " + REPOSITORY, ()->{
            $(".header-search-input").click();
            $(".header-search-input").setValue(REPOSITORY).pressEnter(); });
        step("Переходим в репозиторий " + REPOSITORY, ()->{
            $(linkText(REPOSITORY)).click(); });
        step("Открываем таб Issue", ()->{
            $(partialLinkText("Issue")).click(); });
        step("Проверяем наличие Issue с заголовком " + TITLE, ()->{
            $(withText(TITLE)).shouldBe(visible); });
    }

        @AfterEach
        void addAttachments() {
            Attach.screenshotAs("Last screenshot");
            Attach.pageSource();
            Attach.browserConsoleLogs();
            Attach.addVideo();
            closeWebDriver();
        }
}
