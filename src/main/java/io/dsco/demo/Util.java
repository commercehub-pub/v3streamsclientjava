package io.dsco.demo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.dsco.stream.domain.Iso8601DateTime;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.TimeZone;

/**
 * utility methods needed by the demo application
 */
public class Util
{
    private static Gson _gson;

    public static Gson gson()
    {
        if (_gson == null) {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Iso8601DateTime.class, new Iso8601DateTimeAdapter());
            // builder.setPrettyPrinting();
            _gson = builder.create();
        }
        return _gson;
    }

    static class Iso8601DateTimeAdapter extends TypeAdapter<Iso8601DateTime>
    {
        @Override
        public void write(JsonWriter out, Iso8601DateTime value) throws IOException
        {
            out.value(value == null ? null : value.getDate());
        }

        @Override
        public Iso8601DateTime read(JsonReader reader) throws IOException
        {
            return new Iso8601DateTime(reader.nextString());
        }
    }

    //TODO: convert this to return an Iso8601DateTime object instead
    public static String dateToIso8601(@NotNull Date date)
    {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(tz);
        return df.format(date);
    }

//    public static Date iso8601ToDate(@NotNull String date)
//    {
//        ZonedDateTime from = ZonedDateTime.parse(date);
//        return Date.from(from.toInstant());
//    }

    public static @NotNull String getConsoleInput(@NotNull String prompt)
    {
        System.out.print(prompt);
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            return reader.readLine();
        } catch (Exception e) {
            return "";
        }
    }
}
