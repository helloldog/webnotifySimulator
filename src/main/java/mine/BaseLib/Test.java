package mine.BaseLib;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * Created by weijl on 2019-12-10.
 */
public class Test {
    public static void main(String[] args) {
        Response response = given()
                .config((RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation())))
                .header("sessionid","d291d3mffd95bc")
                .get("https://entry-mch.platformcenter.net/channel/Cbi69ki59040b35/order?parkingId=Pbi69kh54524e67");
        System.out.println(response.asString());
    }

}
