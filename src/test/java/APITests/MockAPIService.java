package APITests;

import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jayway.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static com.atf.utils.APIUtils.*;

public class MockAPIService {


    protected static Logger logger = LoggerFactory.getLogger(MockAPIService.class);

    /**
     * Retrieves details with query string parameter
     */
    @Step("GET {key}={value} with status: {responseCode}")
    public Response sampleGet(int responseCode,String key,String value) {
        String url = "/get?"+key+"="+value;
        Response res = GET(responseCode, url);
        return res;
    }

    @Step("Authenticate request with {username}/{password} with response: {responseCode}")
    public Response sampleBasicAuth(int responseCode,String username,String password) {

        String url = "/basic-auth";
        Response res = GETBasicAuth(responseCode,url,username,password);
        return res;
    }


}
