package APITests;

import com.atf.constants.ResponseCode;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

import static com.atf.utils.APIUtils.getJson;


public class SampleApiTest extends APIBaseTest{
    protected static Logger logger = LoggerFactory.getLogger(SampleApiTest.class);
    MockAPIService mockAPIService=new MockAPIService();

    @Test
    public void testGetWithQueryParam(){
        Response response = mockAPIService.sampleGet(ResponseCode.OK);
        ResponseBody body = response.body();
        JsonPath jp=getJson(response);
        logger.info(jp.get("args").toString());
        HashMap hm=jp.get("args");
        Assert.assertEquals(hm.get("foo1"),"bar1");
        Assert.assertEquals(hm.get("foo2"),"bar2");
    }

    @Test
    public void testGetWithBasicAuth(){
        Response response = mockAPIService.sampleBasicAuth(ResponseCode.OK,"postman","password");
        ResponseBody body = response.body();
        JsonPath jp=getJson(response);
        logger.info(jp.get("authenticated").toString());
        Assert.assertEquals(jp.get("authenticated"),true);
    }
}
