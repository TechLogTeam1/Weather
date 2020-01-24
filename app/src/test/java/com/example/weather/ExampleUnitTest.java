package com.example.weather;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    //Έλενχος για την συνάρτηση μετατροπής θερμοκρασίας
    @Test
    public void Temperature_isCorrect() {
        FullscreenActivity.Global1.Units2 = "C";
        assertEquals(100, FullscreenActivity.ShowTemp(100), 0.0f);

        FullscreenActivity.Global1.Units2 = "K";
        assertEquals(373.15, FullscreenActivity.ShowTemp(100), 0.1f);

        FullscreenActivity.Global1.Units2 = "F";
        assertEquals(212, FullscreenActivity.ShowTemp(100), 0.1f);
    }

    //Έλενχος εισαγωγής θερμοκρασόας στο advanced search
    @Test
    public void TemperatureString_isCorrect() {

        //Σωστές τιμές
        assertEquals(true, AdvSearch.checkTemperature("273.15"));
        assertEquals(true, AdvSearch.checkTemperature("-273.15"));
        assertEquals(true, AdvSearch.checkTemperature("212.125"));
        assertEquals(true, AdvSearch.checkTemperature("0.052"));
        assertEquals(true, AdvSearch.checkTemperature("-0.123"));
        assertEquals(true, AdvSearch.checkTemperature("400.0"));

        //Λάθος τιμές
        assertEquals(false, AdvSearch.checkTemperature("-0.-123"));
        assertEquals(false, AdvSearch.checkTemperature("0.-123"));
        assertEquals(false, AdvSearch.checkTemperature("0.----"));
        assertEquals(false, AdvSearch.checkTemperature("Text"));
        assertEquals(false, AdvSearch.checkTemperature("12.52.52"));
        assertEquals(false, AdvSearch.checkTemperature("0000.123"));
        assertEquals(false, AdvSearch.checkTemperature("00.123"));

    }


    //Ελενχος μετατροπής UNIX θερμοκρασίας σε Date String
    @Test
    public void Unixday_isCorrect() {

        //1/1/20 00:00 = 1577836800
        //Αποτέλεσμα -2 ώρες GMT
        assertEquals("01-01-2020 02:00:00 EET", FullscreenActivity.ConvertUNIXtoDate(1577836800));
        //10/5/20 00:00 = 1589065200 (-2 ώρες GMT)
        //CHECK Here
        assertEquals("10-05-2020 02:00:00 EEST", FullscreenActivity.ConvertUNIXtoDate(1589065200));
        //1/1/21 00:00 = 1609459200
        assertEquals("01-01-2021 02:00:00 EET", FullscreenActivity.ConvertUNIXtoDate(1609459200));
        //10/3/21 00:00 = 1615334400
        assertEquals("10-03-2021 02:00:00 EET", FullscreenActivity.ConvertUNIXtoDate(1615334400));
        //5/5/20 10:00 = 1588662000
        //CHECK
        assertEquals("05-05-2020 10:00:00 EEST", FullscreenActivity.ConvertUNIXtoDate(1588662000));
    }

    //Έλενχος για σωστές συντεταγμένες στο coordinates activity
    @Test
    public void Coords_isCorrect()
    {
        //Σωστές τιμές
        assertEquals(true, Coords.checkcoords("50.12345,-20.32567"));
        assertEquals(true, Coords.checkcoords("5.32313,-3.12345"));
        assertEquals(true, Coords.checkcoords("-40.32313,30.231225"));
        assertEquals(true, Coords.checkcoords("0,0"));
        assertEquals(true, Coords.checkcoords("-0.12423,0.23233"));
        assertEquals(true, Coords.checkcoords("10.233,100.342"));

        //Λάθος τιμές
        assertEquals(false, Coords.checkcoords("0,123,0"));
        assertEquals(false, Coords.checkcoords("0.123,0..123"));
        assertEquals(false, Coords.checkcoords("0..123,50"));
        assertEquals(false, Coords.checkcoords(".1234,.1234"));
        assertEquals(false, Coords.checkcoords("13.-123,12-.123"));
        assertEquals(false, Coords.checkcoords("00.123,90.123"));
        assertEquals(false, Coords.checkcoords("--93.23,120.232"));
        assertEquals(false, Coords.checkcoords("93--23,32.44"));
        assertEquals(false, Coords.checkcoords("abcdefg"));
        assertEquals(false, Coords.checkcoords("10.232,f123"));


    }

    //Έλενχος για την default ώρα του service
    @Test
    public void ServiceTime_isCorrect()
    {

        //Σωστές τιμές
        assertEquals(true, Services.checktime("10:00"));
        assertEquals(true, Services.checktime("09:30"));
        assertEquals(true, Services.checktime("04:25"));
        assertEquals(true, Services.checktime("00:00"));

        //Λάθος τιμές
        assertEquals(false, Services.checktime("90:00"));
        assertEquals(false, Services.checktime("23:70"));
        assertEquals(false, Services.checktime("3:15"));
        assertEquals(false, Services.checktime("abcdef"));
        assertEquals(false, Services.checktime("10:ff"));
        assertEquals(false, Services.checktime("1010"));


    }

    //Έλενχος για την σωστή ημερομηνία στο search
    @Test
    public void SearchDate_isCorrect()
    {

        //Σωστές τιμές
        assertEquals(true, Search.checkdate("01-01-2020"));
        assertEquals(true, Search.checkdate("05-05-2021"));
        assertEquals(true, Search.checkdate("01-03-2022"));

        //Λάθος τιμές
        assertEquals(false, Search.checkdate("1-1-2020"));
        assertEquals(false, Search.checkdate("01-01-20"));
        assertEquals(false, Search.checkdate("50-01-2020"));
        assertEquals(false, Search.checkdate("03-25-2020"));
        assertEquals(false, Search.checkdate("03-25-220"));


    }
}