package serviceTest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;
import io.restassured.response.Response;

import static com.thoughtworks.selenium.SeleneseTestNgHelper.assertEquals;
import static io.restassured.RestAssured.given;

import java.net.HttpURLConnection;


public class RestTests {

    @Test(groups = {"rest"})
    public void getTest() throws Exception {

        String request = "https://reqres.in/api/users?page=2";

        Response response =
                given().when().get(request);

        System.out.println("Response is : " + response.asString());

        if (response.getStatusCode() != HttpURLConnection.HTTP_OK ) {
            System.out.println("GET API failed" + response.asString());
        }
        else {
            System.out.println("GET API success");
        }

        /* RESPONSE IS:
        {"page":2,"per_page":3,"total":12,"total_pages":4,"data":
        [{"id":4,"first_name":"Eve","last_name":"Holt","avatar":"https://s3.amazonaws.com/uifaces/faces/twitter/marcoramires/128.jpg"},
        {"id":5,"first_name":"Charles","last_name":"Morris","avatar":"https://s3.amazonaws.com/uifaces/faces/twitter/stephenmoon/128.jpg"},
        {"id":6,"first_name":"Tracey","last_name":"Ramos","avatar":"https://s3.amazonaws.com/uifaces/faces/twitter/bigmancho/128.jpg"}]}
         */

        JSONObject responseJSON = new JSONObject(response.asString());
        assertEquals("2", responseJSON.get("page"));
        assertEquals("3", responseJSON.get("per_page"));
        assertEquals("12", responseJSON.get("total"));
        assertEquals("4", responseJSON.get("total_pages"));

        JSONArray data = responseJSON.getJSONArray("data");
        System.out.println(data.toString());
        JSONObject datum = data.getJSONObject(0); //Fetching the first element
        System.out.println(datum.toString());
        assertEquals(4 , datum.get("id")); //Verifying id of first element
        //More validation here as per requirement
    }

    @Test(groups = {"rest"})
    public void postTest() throws Exception {

        String request = "https://reqres.in/api/users";

        JSONObject user = new JSONObject();
        user.put ("name", "user1");
        user.put("job", "QA");

        Response response =
                given().when().
                        body(user.toString()).
                        when().post(request);

        System.out.println(response.asString());

        if (response.getStatusCode() != HttpURLConnection.HTTP_CREATED ) {
            System.out.println("POST API failed" + response.asString());
        }
        else {
            System.out.println("POST API success");
        }
        //More validation here!! tips: perform a get as in the above test to validate the post
    }
}
