package ke.co.nectar.api.controllers;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class BaseController {

    protected Map<String, Object> generateData(String key, Object value) {
        Map<String, Object> output = new LinkedHashMap<>();
        output.put(key, value);
        return output;
    }
}
