package com.maxx.eparchi.utils;


import com.maxx.eparchi.BuildConfig;

public class Constants {

    /**
     * API base Main URL
     * SIT URL = https://demo.merchantrademoney.com:9443
     * UAT URL = https://demo.merchantrademoney.com
     * Production URL = https://www.merchantrademoney.com
     */

    public static final String MAIN_URL = BuildConfig.MainUrl;
    public static final String BASE_URL = MAIN_URL + "/api/";
    public static final String API_VERSION = "7";

    public static final String REFRESH_REQUEST_URL = "mobile/token/refresh";

    public static final int APP_FORCE_UPDATE_ERROR_CODE = 420;
    public static final int APP_LANGUATE_UPDATE_CODE = 440;

    public static final String DEFAULT_TAG = "tag";
    public static final String VIEW_TYPE_INTENT = "view_type_intent";

    public static final String DEVICE_TYPE = "Android";


    public static  class DATE_FORMAT{
        public static final String APPLY_LEAVE = "MM/dd/yyyy";
    }

    public static class LEAVE_STATUS_CODE{
        public static final int APPROVED = 0;
        public static final int REJECTED = 1;
        public static final int PENDING = 2;
    }


    public static final String NotificationChannelId = "com.maxx.eattenance.ANDROID";
    public static final String NotificationChannelName = "ANDROID CHANNEL";
    public static final String NOTIFICATION_GROUP_NAME = "E ATTENDANCE NOTIFICATION GROUP";
    public static final int SUMMARY_NOTIFCATION_ID = 0;
    public static class NOTIFICATION_FCM {
        public static final String IMAGE = "image";
        public static final String MESSAGE = "message";
        public static final String TITLE = "title";
        public static final String TYPE = "type";
    }


    public static final String PASSCODE_INTENT = "passcodeIntent";
    public static final String PASSCODE_VALUE = "passcode_vlaue";
    public static final String PASSCODE_STATUS = "passcode_status";
    public static final String ADVERTISEMENT_INTENT_DATA = "advertisement_intent_data";
    public static final String OTP_INTENT = "otp_intent";
    public static final String NOTIFICATION_INTENT_DATA = "notification_intent_data";

    public static final String BALANCE_INFORMATION_INTENT_DATA = "balance_information_intent_data";
    public static final String CURRENCY_DETAIL_LIST_INTENT_DATA = "currency_detail_list_intent_data";
    public static final String CURRENCY_DETAIL_INTENT_DATA = "currency_detail_intent_data";


    public static final String HEADER_AUTHORIZATION = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6ImNzaWQ2IiwidXNlcm5hbWUiOiJlbXA2In0.PCs9Kq3YbqhEIhoWF4xOSsm11KzZKYyDbyxRqtC5exk";
}
