package APITests;

import com.atf.config.APIConfig;
import com.atf.utils.ConfigReader;
import com.jayway.restassured.RestAssured;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.io.*;

public class APIBaseTest {

    protected static Logger logger = LoggerFactory.getLogger(APIBaseTest.class);
    public static final String DATA_PATH=System.getProperty("user.dir")+"/src/test/resources/testdata/";


    @BeforeClass(alwaysRun = true)
    @Step("Base setup for api")
    public void apiSetUp() throws Exception {

        RestAssured.baseURI= APIConfig.getBaseUrl();
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.port = Integer.parseInt(APIConfig.getPort());
        generateEnvironmentFileAllure();

    }

    @AfterClass(alwaysRun = true)
    @Step("clean up all the connections")
    public void apiCleanUp(){

    }

    public void generateEnvironmentFileAllure() throws Exception{
        String env = System.getProperty("env");
        String type= System.getProperty("type");

        if (type==null || !(type.equals("ui") || type.equals("api"))){
            logger.error("Pass correct type as 'api' or 'ui'");
            System.exit(1);
        }
        if (env == null) {
            env = "local";
        }
        String propFile=null;
        if(env.equals("local"))
            propFile = System.getProperty("user.dir")+"/src/test/resources/configs/" + type +"/default.properties";
        else
            propFile = System.getProperty("user.dir")+"/src/test/resources/configs/" + type +"/"+env + ".properties";

        File fileToCopy = new File(propFile);
        File newFile = new File(System.getProperty("user.dir")+"allure-results/environment.properties");

        FileUtils.copyFile(fileToCopy, newFile);
    }
}
