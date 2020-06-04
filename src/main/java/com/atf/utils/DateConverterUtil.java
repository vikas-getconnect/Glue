package com.atf.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/***
 * This utility class to convert date to various formats
 *
 */
public class DateConverterUtil {

    Logger logger = LoggerFactory.getLogger(DateConverterUtil.class);


    public String currentDateInIST(String format) {

        DateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
        Calendar calendar = Calendar.getInstance();
        //get current date time with Date()
        Date date = calendar.getTime();
        return dateFormat.format(date);

    }


    public String epochToDateConvertor(String datejoined) {

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        long milliSeconds = Long.parseLong(datejoined);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        String joined_date = formatter.format(calendar.getTime());

        return joined_date;
    }

    public String epochToOnlyDateConvertor(String datejoined) {

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setTimeZone(TimeZone.getTimeZone("PST"));
        long milliSeconds = Long.parseLong(datejoined);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        String joined_date = formatter.format(calendar.getTime());

        return joined_date;
    }

    public String epochToOnlyDateConvertorIST(String datejoined) {

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setTimeZone(TimeZone.getTimeZone("IST"));
        long milliSeconds = Long.parseLong(datejoined);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        String joined_date = formatter.format(calendar.getTime());

        return joined_date;
    }

    public String epochToOnlyDateConvertorInMDYFormat(String datejoined) {

        DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        long milliSeconds = Long.parseLong(datejoined);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        String joined_date = formatter.format(calendar.getTime());

        return joined_date;
    }

    public String convertDateToCompleteFormat(String planEndDate) throws ParseException {
        DateFormat parseFormat = new SimpleDateFormat("MM-dd-yyyy");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("PST"));
        cal.setTime(parseFormat.parse(planEndDate));
        Date date = cal.getTime();
        return format.format(date);
    }

    public String convertDateToFormat(String planEndDate) throws ParseException {
        DateFormat parseFormat = new SimpleDateFormat("MM-dd-yyyy");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        cal.setTime(parseFormat.parse(planEndDate));
        Date date = cal.getTime();
        return format.format(date);
    }

    public String convertDateFormat(String planEndDate) throws ParseException {
        DateFormat parseFormat = new SimpleDateFormat("MM/dd/yyyy");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        cal.setTime(parseFormat.parse(planEndDate));
        Date date = cal.getTime();
        return format.format(date);
    }

    public String convertDateBySubtractingOneDayFrom(String trialEndDate) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(trialEndDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (cal.get(Calendar.HOUR) > 0 || cal.get(Calendar.MINUTE) > 0 || cal.get(Calendar.SECOND) > 0) {
            cal.add(Calendar.DATE, 1);
        }
        date = cal.getTime();
        trialEndDate = dateFormat.format(date);
        String trialEnd = format.format(dateFormat.parse(trialEndDate));
        return trialEnd;

    }

    public String convertMarketoDateToNormalDate(String marketo_planEndDate) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = format.parse(marketo_planEndDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        date = cal.getTime();
        marketo_planEndDate = dateFormat.format(date);
        return marketo_planEndDate;
    }

    public String generateRandomPlanEndDate(String FormatOfDateAndTime) {
        long time = System.currentTimeMillis();
        Random rand = new Random();
        long randomnum = rand.nextInt(1000000000) + 1000000000;
        time = time + randomnum;
        Date date = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat(FormatOfDateAndTime);
        String randomDate = formatter.format(date);
        return randomDate;
    }

    public String covertDatetoEpochForDefaultTimezone(String Datetoconvert, String format) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat(format);
        Date date = df.parse(Datetoconvert);
        long epoch = date.getTime();
        Datetoconvert = String.valueOf(epoch);
        return Datetoconvert;
    }

    public String convertMarketoSignUpDate(String marketo_planEndDate) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(marketo_planEndDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        date = cal.getTime();
        marketo_planEndDate = dateFormat.format(date);
        return marketo_planEndDate;
    }

    public String convertDateByYMD(String trialEndDate) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
        Date d = sdf.parse(trialEndDate);
        String formattedTime = output.format(d);
        return formattedTime;
    }

    public String convertDateByMDY(String trialEndDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat output = new SimpleDateFormat("MM-dd-yyyy");
        Date d = sdf.parse(trialEndDate);
        String formattedTime = output.format(d);
        return formattedTime;
    }

    public String todayDateInPSTFormat() {

        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        formatter.setTimeZone(TimeZone.getTimeZone("PST"));
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        String today_date = formatter.format(date);

        return today_date;
    }

    public String todayDateInPSTFormat(String format) {

        DateFormat formatter = new SimpleDateFormat(format);
        formatter.setTimeZone(TimeZone.getTimeZone("PST"));
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        String today_date = formatter.format(date);

        return today_date;
    }


    public String currentDatePST(String format) {

        DateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setTimeZone(TimeZone.getTimeZone("PST"));
        Calendar calendar = Calendar.getInstance();
        //get current date time with Date()
        Date date = calendar.getTime();
        return dateFormat.format(date);

    }


    public String addDaysToCurrentDate(int numberOfDays) {

        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        formatter.setTimeZone(TimeZone.getTimeZone("PST"));
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, numberOfDays);
        Date date = calendar.getTime();

        String today_date = formatter.format(date);

        return today_date;
    }

    public String addDaysToCurrentDateWithFormat(int numberOfDays, String format) {

        DateFormat formatter = new SimpleDateFormat(format);
        formatter.setTimeZone(TimeZone.getTimeZone("PST"));
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, numberOfDays);
        Date date = calendar.getTime();

        String today_date = formatter.format(date);

        return today_date;
    }

    public String todayDateInPSTFormatpattern() {

        DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        formatter.setTimeZone(TimeZone.getTimeZone("PST"));
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        String today_date = formatter.format(date);

        return today_date;
        //yyyy-MM-dd
    }

    public String todayDateInPSTFormatpattern1() {

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setTimeZone(TimeZone.getTimeZone("PST"));
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        String today_date = formatter.format(date);

        return today_date;

    }

    public Long convertDateToSeconds(String date) throws ParseException {
        Long seconds;
        SimpleDateFormat format = new SimpleDateFormat("YYYY-mm-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("PST"));
        Date date_converted = format.parse(date);
        seconds = (date_converted.getTime()) / (1000);

        return seconds;
    }


    public String subtractNdaysFromDate(String finaldate, int numberOfDays) throws ParseException {

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse(finaldate); // conversion from String
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -numberOfDays);
        return format.format(cal.getTime());
    }


    public String subtractNdaysFromDateWithDifferentFormat(String finaldate, int numberOfDays) throws ParseException {

        DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = format.parse(finaldate); // conversion from String
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -numberOfDays);
        return format.format(cal.getTime());
    }

    public String addNdaysFromDate(String givendate, int numberOfDays) throws ParseException {

        DateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        Date date = format.parse(givendate); // conversion from String
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, numberOfDays);
        return format.format(cal.getTime());

    }

    public String subtractNdaysFromDateFormat2(String finaldate, int numberOfDays, String formatt) throws ParseException {

        DateFormat format = new SimpleDateFormat(formatt);
        Date date = format.parse(finaldate); // conversion from String
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -numberOfDays);
        return format.format(cal.getTime());
    }

    public String reduceTimeByNHours(String date, int noofHoursToReduce) throws ParseException {
        Date date1 = null;
        String str = date;
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date1 = formatter.parse(str);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        calendar.add(Calendar.HOUR, -noofHoursToReduce);
        logger.info(calendar.getTime().toString());

        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        str = formatter.format(calendar.getTime());
        logger.info(str + "Check this with subtracted end date value*************************88");

        return str;
    }

    public String reduceTimeByNHoursDifferentFormat(String date, int noofHoursToReduce) throws ParseException {
        Date date1 = null;
        String str = date;
        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        date1 = formatter.parse(str);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        calendar.add(Calendar.HOUR, -noofHoursToReduce);
        logger.info(calendar.getTime().toString());

        formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        str = formatter.format(calendar.getTime());
        logger.info(str + "Check this with subtracted end date value*************************88");

        return str;
    }

    public Date convertStringToDate(String date, String format) throws ParseException {
        String string = date;
        DateFormat convertedformat = new SimpleDateFormat(format, Locale.ENGLISH);
        Date startDate = convertedformat.parse(string);
        return startDate;
    }
}
