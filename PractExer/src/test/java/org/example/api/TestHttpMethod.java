package org.example.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.*;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.*;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.*;

@ToString
@SuppressWarnings({"unused"})
@AllArgsConstructor
@Data
@NoArgsConstructor
class Pojo implements Comparable<Pojo>{
    @JsonProperty("id")
    private int id;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("avatar")
    private String avatar;
    @JsonProperty("email")
    private String email;

    @Override
    public int compareTo(Pojo o) {
        return this.id-o.id;
    }
}

public class TestHttpMethod {

    private ObjectMapper mapper;
    private RequestSpecification requestSpecification;
    private ResponseSpecification responseSpecification;

    @BeforeClass
    public void setUp() {

        requestSpecification =
                 given()
                .baseUri("https://reqres.in")
                .basePath("/api")
                .contentType(ContentType.JSON)
                .pathParams("path", "users")
                .queryParam("page", 2);

        responseSpecification =
                 expect()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema.json"));

        mapper = new ObjectMapper();

    }
    @SneakyThrows
    @Test
    public void testGetRequest() {

        Response response =
                requestSpecification
        .when()
                .get("/{path}")
        .then().
                spec(responseSpecification)
                .extract().
                response();


        System.out.println(response.asPrettyString());
        Object path = response.path("data[1]");
        System.out.println(path.toString());
        Pojo js=mapper.readValue(mapper.writeValueAsString(path),Pojo.class);
        System.out.println(js.toString());
    }
    @Test
    public void testPostRequest() {
        String str= "Krishna Hare";
        String collect = Arrays.stream(str.split(" ")).collect(Collectors.collectingAndThen(Collectors.toList(), (l) -> {
            Collections.reverse(l);
            return l;
        })).stream().collect(Collectors.joining(" "));
        System.out.println(collect);
        List<StringBuilder> collect1 = Arrays.stream(str.split(" ")).map(x -> new StringBuilder(x).reverse()).collect(Collectors.toList());
        System.out.println(collect1);
        Map<Integer,String> collect2 = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            collect2.put(i, "kjbkj"+i);
        }
        collect2.entrySet().stream().forEach(System.out::println);
        collect2.entrySet().stream().sorted((Map.Entry<Integer,String> m1,Map.Entry<Integer,String > m2)->m2.getValue().compareToIgnoreCase(m1.getValue())).forEach(System.out::println);
        List<Pojo> dummyData = new ArrayList<>();
        dummyData.add(new Pojo(6, "John", "Doe", "avatar1.png", "john.doe@example.com"));
        dummyData.add(new Pojo(2, "Jack", "Doe", "avatar2.png", "jane.doe@example.com"));
        dummyData.add(new Pojo(4, "Jim", "Beam", "avatar3.png", "jim.beam@example.com"));
        dummyData.add(new Pojo(2, "Jack", "Daniels", "avatar4.png", "jack.daniels@example.com"));
        dummyData.add(new Pojo(2, "Johnny", "Walker", "avatar5.png", "johnny.walker@example.com"));
        dummyData.sort(null);
        dummyData.forEach(System.out::println);
        Comparator<Pojo> com= new Comparator<Pojo>() {
            @Override
            public int compare(Pojo o1, Pojo o2) {
                return o1.getEmail().compareToIgnoreCase(o2.getEmail());
            }
        };
        dummyData.sort(Comparator.comparing((Pojo x)->x.getId()).thenComparing((x,y)->x.getFirstName().compareToIgnoreCase(y.getFirstName())).thenComparing(com));
        dummyData.forEach(System.out::println);
        List<Integer> list=List.of(1,45,34,21,67,99,3,8,12,23,66,16,100);
        list.stream().sorted((x,y)->y-x).skip(1).limit(1).forEach(System.out::println);
    }
}
