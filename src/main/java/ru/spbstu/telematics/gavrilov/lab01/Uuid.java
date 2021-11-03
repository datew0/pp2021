package ru.spbstu.telematics.gavrilov.lab01;

import java.security.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Uuid implements Comparable<Uuid> {

    private static final SecureRandom rng;
    private static final Pattern uuidRegexp;

    static {
         uuidRegexp = Pattern.compile("[0-9a-f]{8}\\-[0-9a-f]{4}\\-[0-9a-f]{4}\\-[0-9a-f]{4}\\-[0-9a-f]{12}");
         rng = new SecureRandom();
    }

    private final Long highBits;
    private final Long lowBits;

    private Uuid(long highBits, long lowBits) {
        this.highBits = highBits;
        this.lowBits = lowBits;
    }

    public Uuid(int grp1, short grp2, short grp3, short grp4, long grp5) throws RuntimeException {
        if ((grp5 & 0xFFFFL << 48) != 0) throw new IllegalArgumentException("Bytes 6-7 in grp5 must be zero");
        highBits = (long) grp1 << 32 | Short.toUnsignedLong(grp2) << 16 | Short.toUnsignedLong(grp3);
        lowBits = (long)grp4 << 48 | (grp5 << 2) >>> 2;
    }

    @Override
    public String toString() {
        String uuidStr = Long.toHexString(highBits) + Long.toHexString(lowBits);
        return uuidStr.substring(0,8) + "-" + uuidStr.substring(8,12) + "-" + uuidStr.substring(12,16) + "-" + uuidStr.substring(16,20) + "-" + uuidStr.substring(20,uuidStr.length());
    }

    @Override
    public int compareTo(Uuid other) {
        if(Long.compareUnsigned(highBits, other.highBits) > 0 ||
                (highBits.equals(other.highBits)) && (Long.compareUnsigned(lowBits, other.lowBits) > 0)) return 1;
        if (!equals(other)) return -1;
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj.getClass() == Uuid.class) {
            Uuid other = (Uuid) obj;
            return (highBits.equals(other.highBits)) && (lowBits.equals(other.lowBits));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (int) (highBits ^ (highBits >>> 32) ^ lowBits ^ (lowBits >>> 32));
    }

    public static Uuid generateUuid() {
        byte[] randomData = new byte[16];
        rng.nextBytes(randomData);
        randomData[6] = (byte)((randomData[6] & (byte)0x0f) | (byte)0x40);
        randomData[8] = (byte)((randomData[8] & (byte)0x3f) | (byte)0x80);
        long highBits = 0, lowBits = 0;

        for (int i = 0; i < Long.BYTES; i++) {
            highBits <<= Byte.SIZE;
            highBits |= randomData[i] & 0xFF;
            lowBits <<= Byte.SIZE;
            lowBits |= randomData[8+i] & 0xFF;
        }
        return new Uuid(highBits, lowBits);
    }

    public static boolean isUuid(String text){
        Matcher m = uuidRegexp.matcher(text);
        return m.matches();
    }
}
