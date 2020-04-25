package APITests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jayway.restassured.response.Response;

import static com.atf.utils.APIUtils.*;

public class MockAPIService {


    protected static Logger logger = LoggerFactory.getLogger(MockAPIService.class);

    /**
     * Retrieves details with query string parameter
     */

    public Response sampleGet(int responseCode) {

        String url = "/get?foo1=bar1&foo2=bar2";
        Response res = GET(responseCode, url);
        return res;
    }


    public Response sampleBasicAuth(int responseCode,String username,String password) {

        String url = "/basic-auth";
        Response res = GETBasicAuth(responseCode,url,username,password);
        return res;
    }


    /**
     * Retrieves details with query string parameter
     */

//    public Response postWithForm(int responseCode) {
//
//        String url = "/get?foo1=bar1&foo2=bar2";
//        Response res = POST_with_formParameter(responseCode, url);
//        return res;
//    }

}
