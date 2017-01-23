package rs.acs.uns.sw.govrs.client.fx.util;

import java.security.SecureRandom;
import java.math.BigInteger;

public final class RandomGenerator {
    private SecureRandom random = new SecureRandom();

    public String nextIdLarge() {
        return new BigInteger(130, random).toString(32).substring(0, 10);
    }

    public String nextIdSmall() {
        return new BigInteger(130, random).toString(32).substring(0, 5);
    }
}
