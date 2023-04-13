package org.test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class MyTest {

    @Test
    public void test1() {
        RestAssured.baseURI = "http://jsonplaceholder.typicode.com";

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        JSONObject param = new JSONObject();
        param.put("PostId", 1);
        param.put("Name", "Junaid");
        param.put("Email", "junzismail@gmail.com");
        param.put("Body", "13 April 2023");

        Response response = request.body(param.toJSONString()).post("/posts");
        int statusCode = response.getStatusCode();

        Assert.assertEquals("junzismail@gmail.com", JsonPath.from(response.asString()).get("Email"));
        Assert.assertEquals("13 April 2023", JsonPath.from(response.asString()).get("Body"));
        Assert.assertEquals(1, JsonPath.from(response.asString()).getInt("PostId"));
        Assert.assertEquals("Junaid", JsonPath.from(response.asString()).get("Name"));
    }
}
