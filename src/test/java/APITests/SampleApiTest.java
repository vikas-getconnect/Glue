package APITests;

import com.atf.constants.ResponseCode;
import com.atf.utils.ExcelDataProvider;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;

import static com.atf.utils.APIUtils.getJson;


public class SampleApiTest extends APIBaseTest{
    protected static Logger logger = LoggerFactory.getLogger(SampleApiTest.class);
    MockAPIService mockAPIService=new MockAPIService();
    ExcelDataProvider ex=new ExcelDataProvider();

    @DataProvider(name = "getParam")
    public Object[][] getParam(){
        String[][] params = ex.getTableArray(DATA_PATH + "users.xls", "params", "param");
        return params;
    }

    @Test(groups = {"sample","api"},dataProvider = "getParam")
    @Description("Test GET api with query params")
    @Severity(SeverityLevel.MINOR)
    @Story("Test GET api with query params")
    public void testGetWithQueryParam(String key,String value){
        Response response = mockAPIService.sampleGet(ResponseCode.OK,key,value);
        ResponseBody body = response.body();
        JsonPath jp=getJson(response);
        logger.info(jp.get("args").toString());
        HashMap hm=jp.get("args");
        Assert.assertEquals(hm.get(key),value);
    }

    @Test(groups = {"sample1","api"})
    @Description("Test GET api with username password")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Test GET api with username password")
    public void testGetWithBasicAuth(){
        Response response = mockAPIService.sampleBasicAuth(ResponseCode.OK,"postman","password");
        ResponseBody body = response.body();
        JsonPath jp=getJson(response);
        logger.info(jp.get("authenticated").toString());
        Assert.assertTrue(jp.get("authenticated"));
    }
}
