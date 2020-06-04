package APITests;

import com.jayway.restassured.response.Response;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.atf.utils.APIUtils.GET;
import static com.atf.utils.APIUtils.GETBasicAuth;

public class MockAPIService {


    protected static Logger logger = LoggerFactory.getLogger(MockAPIService.class);

    /**
     * Retrieves details with query string parameter
     */
    @Step("GET {key}={value} with status: {responseCode}")
    public Response sampleGet(int responseCode, String key, String value) {
        String url = "/get?" + key + "=" + value;
        Response res = GET(responseCode, url);
        return res;
    }

    /**
     * Retrieves details with basic auth
     */
    @Step("Authenticate request with {username}/{password} with response: {responseCode}")
    public Response sampleBasicAuth(int responseCode, String username, String password) {

        String url = "/basic-auth";
        Response res = GETBasicAuth(responseCode, url, username, password);
        return res;
    }


}
