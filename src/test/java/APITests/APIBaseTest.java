package APITests;

import com.atf.config.APIConfig;
import com.atf.utils.ConfigReader;
import com.jayway.restassured.RestAssured;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.io.FileNotFoundException;
import java.io.IOException;

public class APIBaseTest {

    private ConfigReader apiConfig;
    public static final String DATA_PATH=System.getProperty("user.dir")+"/src/test/resources/testdata/";

    @BeforeSuite
    public void beforeSuite(){

    }

    @BeforeClass(alwaysRun = true)
    @Step("Base setup for api")
    public void apiSetUp() throws Exception {

        RestAssured.baseURI= APIConfig.getBaseUrl();
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.port = Integer.parseInt(APIConfig.getPort());

    }

    @AfterClass(alwaysRun = true)
    @Step("clean up all the connections")
    public void apiCleanUp(){

    }
}
