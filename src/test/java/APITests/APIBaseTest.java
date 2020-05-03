package APITests;

import com.atf.config.APIConfig;
import com.atf.utils.ConfigReader;
import com.jayway.restassured.RestAssured;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.io.FileNotFoundException;
import java.io.IOException;

public class APIBaseTest {

    private ConfigReader apiConfig;

    @BeforeSuite(alwaysRun = true)
    public void suiteSetup() {

    }

    @BeforeClass(alwaysRun = true)
    public void apiSetUp() throws Exception {

        RestAssured.baseURI= APIConfig.getBaseUrl();
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.port = Integer.parseInt(APIConfig.getPort());

    }

    @AfterClass(alwaysRun = true)
    public void apiCleanUp(){

    }
}
