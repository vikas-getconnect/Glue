package APITests;

import com.atf.utils.APIConfigReader;
import com.jayway.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.io.FileNotFoundException;
import java.io.IOException;

public class APIBaseTest {

    private APIConfigReader apiConfig;

    @BeforeSuite(alwaysRun = true)
    public void readConfig() throws FileNotFoundException {

        apiConfig = new APIConfigReader();
        String baseUrl = apiConfig.getValue("baseUrl");
        RestAssured.baseURI=baseUrl;
        RestAssured.useRelaxedHTTPSValidation();
        int port = Integer.parseInt(apiConfig.getValue("servicePort"));

        if (port != 443) {
            RestAssured.port = Integer.parseInt(apiConfig.getValue("servicePort"));
        }
        String defaultUserName = apiConfig.getValue("defaultUserName");
        String defaultUserEmail = apiConfig.getValue("defaultPassword");

    }

    @BeforeClass(alwaysRun = true)
    public void setUp() throws IOException {
    }
}
