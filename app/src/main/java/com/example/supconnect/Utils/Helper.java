package com.example.supconnect.Utils;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Base64;

import android.util.Log;

import com.example.supconnect.model.Student;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import static android.content.Context.MODE_PRIVATE;

public class Helper {

    public static String getStudentID(SharedPreferences sharedPreferences) {
        if(sharedPreferences.getString("role", "").equals("3")) {
            Gson gson = new Gson();
            String json = sharedPreferences.getString("studentOfParent", "");
            Student student = gson.fromJson(json, Student.class);
            return student.getStudentId();
        } else {
            return sharedPreferences.getString("user_id", "");
        }
    }

    public static String convertDateTime(String time) {
        SimpleDateFormat oldFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        SimpleDateFormat newFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        try {
            String reformattedStr = newFormat.format(oldFormat.parse(time));
            return reformattedStr;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String convertDate(String time) {
        SimpleDateFormat oldFormat = new SimpleDateFormat("yyyy-MM-dd");

        SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            String reformattedStr = newFormat.format(oldFormat.parse(time));
            return reformattedStr;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String convertToSaveDate(String time) {
        SimpleDateFormat oldFormat = new SimpleDateFormat("dd-MM-yyyy");

        SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String reformattedStr = newFormat.format(oldFormat.parse(time));
            return reformattedStr;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String changeTimeZone(String time) {
        SimpleDateFormat oldFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        oldFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        newFormat.setTimeZone(TimeZone.getTimeZone("GMT+07:00"));
        try {
            String reformattedStr = newFormat.format(oldFormat.parse(time));
            return reformattedStr;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String timeDifferent(String dataDate) {

        String convTime = "";

        String prefix = "Còn ";

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date pasTime = dateFormat.parse(dataDate);

            Date nowTime = new Date();
            long dateDiff = pasTime.getTime() - nowTime.getTime();
            if(dateDiff < 0) {
                dateDiff = -dateDiff;
                prefix = "Trễ ";
            }

            long second = TimeUnit.MILLISECONDS.toSeconds(dateDiff);
            long minute = TimeUnit.MILLISECONDS.toMinutes(dateDiff);
            long hour   = TimeUnit.MILLISECONDS.toHours(dateDiff);
            long day  = TimeUnit.MILLISECONDS.toDays(dateDiff);

            if (second < 60) {
                convTime = prefix + second + " giây";
            } else if (minute < 60) {
                convTime = prefix + minute + " phút";
            } else if (hour < 24) {
                convTime = prefix + hour + " giờ";
            } else if (day >= 7) {
                if (day > 360) {
                    convTime = prefix + (day / 360) + " năm";
                } else if (day > 30) {
                    convTime = prefix + (day / 30) + " tháng";
                } else {
                    convTime = prefix + (day / 7) + " tuần";
                }
            } else if (day < 7) {
                convTime = prefix + day+" ngày";
            }

        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("ConvTimeE", e.getMessage());
        }

        return convTime;
    }

    public static String bitmapToString(Bitmap bitmap) {
        if (bitmap!=null){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
            byte [] array = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(array,Base64.NO_WRAP);
        }

        return "";
    }

    public static String getMd5(String input)
    {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
