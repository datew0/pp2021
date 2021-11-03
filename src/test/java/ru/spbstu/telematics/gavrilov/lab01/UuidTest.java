package ru.spbstu.telematics.gavrilov.lab01;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UuidTest {

    Uuid concreteId;
    String concreteIdStr;

    @Before
    public void init() {
        concreteId = new Uuid(0xABCDEF00,(short)0xDCBA,(short)0x1122,(short)0x3344,0xBBCCDDEEFF11L);
        concreteIdStr = "abcdef00-dcba-1122-3344-bbccddeeff11";
    }

    @Test
    public void testToString() {
        String actual = concreteId.toString();
        Assert.assertEquals("toString() representation is not the same",concreteIdStr, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToString_WrongLastGrp() {
        Uuid concreteWrongId = new Uuid(0xABCDEF00,(short)0xDCBA,(short)0x1122,(short)0x3344,0x11AABBCCDDEEFF11L);
    }

    @Test
    public void compareTo() {
        Uuid greaterId =   new Uuid(0xBBCDEF00,(short)0xDCBA,(short)0x1122,(short)0x3344,0xBBCCDDEEFF11L);
        Uuid sameId =      new Uuid(0xABCDEF00,(short)0xDCBA,(short)0x1122,(short)0x3344,0xBBCCDDEEFF11L);
        Uuid smallerId =   new Uuid(0xABCDEF00,(short)0xDCBA,(short)0x1122,(short)0x3344,0xBBCCDDEEFF00L);

        Assert.assertTrue("greaterId is smaller than concreteId",greaterId.compareTo(concreteId) > 0);
        Assert.assertTrue("sameId is not equal to concreteId", sameId.compareTo(concreteId) == 0);
        Assert.assertTrue("smallerId is greater than concreteId", smallerId.compareTo(concreteId) < 0);
    }

    @Test
    public void testEquals() {
        Uuid sameId = new Uuid(0xABCDEF00,(short)0xDCBA,(short)0x1122,(short)0x3344,0xBBCCDDEEFF11L);
        Uuid differentId = new Uuid(0xAAAAAAAA,(short)0xBBBB,(short)0xCCCC,(short)0xDDDD,0xEEEEFFFF0000L);

        Assert.assertTrue("concreteId and sameId point at one Object", concreteId != sameId);
        Assert.assertEquals("concreteId and sameId are not equal",sameId,concreteId);

        Assert.assertFalse("concreteId and differentId are the same", differentId.equals(concreteId));
    }

    @Test
    public void testGenerateUuid() {
        Pattern uuidRegexp = Pattern.compile("[0-9a-f]{8}\\-[0-9a-f]{4}\\-[0-9a-f]{4}\\-[0-9a-f]{4}\\-[0-9a-f]{12}");
        Matcher m = uuidRegexp.matcher(Uuid.generateUuid().toString());
        Assert.assertTrue(m.matches());
    }

    @Test
    public void testIsUuid() {
        Assert.assertTrue("ConcreteIdStr doesn't match Uuid format",Uuid.isUuid(concreteIdStr));

        String expression = "Saint-Petersburg";
        Assert.assertFalse("Non-UUID expression matches UUID format",Uuid.isUuid(expression));
    }
}
