package org.lemon.repository.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FuckOffURIs {

    private static final List<String> URIsList = Arrays.asList(
            "/asshole/me",
            "/awesome/me",
            "/bag/me",
            "/because/me",
            "/bucket/me",
            "/bye/me",
            "/cool/me",
            "/cup/me",
            "/dense/me",
            "/diabetes/me",
            "/dumbledore/me",
            "/even/me",
            "/everyone/me",
            "/everything/me",
            "/family/me",
            "/fascinating/me",
            "/flying/me",
            "/ftfy/me",
            "/fyyff/me",
            "/give/me",
            "/holygrail/me"
    );

    public static String getRandomURI() {
        Random random = new Random();
        int randomNumber = random.nextInt(URIsList.size());

        return URIsList.get(randomNumber);
    }
}
