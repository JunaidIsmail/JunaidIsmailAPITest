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
        Response response = post(request);
        validateStatusCode(response.getStatusCode());

        validateResponseBody(response);
    }

    public void validateStatusCode(int statusCode) {
        Assert.assertEquals(201, statusCode);
    }

    public JSONObject setParam(int postId, String name, String email, String body) {
        JSONObject param = new JSONObject();
        param.put("PostId", postId);
        param.put("Name", name);
        param.put("Email", email);
        param.put("Body", body);

        return param;
    }

    public Response post(RequestSpecification request) {
        Response response = request
                .body(setParam(1, "Junaid", "junzismail@gmail.com", "13 April 2023").toJSONString())
                .post("/posts");
        return response;
    }

    public void validateResponseBody(Response response) {
        Assert.assertEquals("junzismail@gmail.com", JsonPath.from(response.asString()).get("Email"));
        Assert.assertEquals("13 April 2023", JsonPath.from(response.asString()).get("Body"));
        Assert.assertEquals(1, JsonPath.from(response.asString()).getInt("PostId"));
        Assert.assertEquals("Junaid", JsonPath.from(response.asString()).get("Name"));
    }
}
