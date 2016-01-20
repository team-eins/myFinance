package team1.myFinance.web.helper;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParser {

    /**
     * Parses the team1.myFinance.data string in instance of target
     * @param data the input string
     * @param target the class of the target type
     * @param <T> the target type
     * @return a instance of the target type or null
     */
    public static <T> T parse(String data, Class<T> target){
        ObjectMapper om = new ObjectMapper();
        T result = null;

        try {
            result = om.readValue(data, target);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String get(String data, String name){
        ObjectMapper mapper = new ObjectMapper();
        String result = "";

        try {
            result = mapper.readTree(data).get(name).asText();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
