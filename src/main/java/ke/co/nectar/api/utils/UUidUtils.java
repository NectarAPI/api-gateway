package ke.co.nectar.api.utils;

import java.util.UUID;

public class UUidUtils {

    public static String generateRef(){
        return UUID.randomUUID().toString();
    }

}
