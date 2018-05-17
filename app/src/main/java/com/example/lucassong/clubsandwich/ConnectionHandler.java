package com.example.lucassong.clubsandwich;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.content.Context;

import com.example.lucassong.clubsandwich.club.Club;
import com.example.lucassong.clubsandwich.club.ClubViewModel;
import com.example.lucassong.clubsandwich.club_add.AddClubViewModel;
import com.example.lucassong.clubsandwich.event.Event;
import com.example.lucassong.clubsandwich.event_add.AddEventViewModel;
import com.example.lucassong.clubsandwich.post.Post;
import com.example.lucassong.clubsandwich.post.PostViewModel;
import com.example.lucassong.clubsandwich.post_add.AddPostViewModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import static com.example.lucassong.clubsandwich.ConnectionHandler.ObjectType.CLUB;
import static com.example.lucassong.clubsandwich.ConnectionHandler.ObjectType.EVENT;
import static com.example.lucassong.clubsandwich.ConnectionHandler.ObjectType.POST;

public class ConnectionHandler {
    enum ObjectType {CLUB, POST, EVENT};

    public static void downloadData(final Activity activity, final Context context, final FragmentActivity fragmentActivity) {
        getJSON("https://clubsatfcuc.xyz/club.php", activity, context, fragmentActivity, CLUB);
        getJSON("https://clubsatfcuc.xyz/event.php", activity, context, fragmentActivity, EVENT);
        getJSON("https://clubsatfcuc.xyz/post.php", activity, context, fragmentActivity, POST);
    }

    private static void getJSON(final String urlWebService, final Activity activity, final Context context,
                                final FragmentActivity fragmentActivity, final ObjectType objectType) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                if (s != null)
                {
                    try {
                        parseJson(s, activity, context, fragmentActivity, objectType);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            protected String doInBackground(Void... voids) {

                //VERY UNSAFE IMPLEMENTATION, USE ONLY FOR TESTING
                TrustManager[] trustAllCerts = new TrustManager[]{
                        new X509TrustManager() {
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return null;
                            }
                            public void checkClientTrusted(
                                    java.security.cert.X509Certificate[] certs, String authType) {
                            }
                            public void checkServerTrusted(
                                    java.security.cert.X509Certificate[] certs, String authType) {
                            }
                        }
                };

                try {
                    SSLContext sc = SSLContext.getInstance("SSL");
                    sc.init(null, trustAllCerts, new java.security.SecureRandom());
                    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                }
                catch (Exception e) {
                }
                //VERY UNSAFE IMPLEMENTATION, USE ONLY FOR TESTING

                try {
                    URL url = new URL(urlWebService);
                    HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    return bufferedReader.readLine();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    return null;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }

        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private static void parseJson(String rawJson, Activity activity, Context context, FragmentActivity fragmentActivity, ObjectType objectType) throws JSONException, ParseException {
        ObjectMapper mapper = new ObjectMapper();

        if (objectType == CLUB) {
            AddClubViewModel addClubViewModel = ViewModelProviders.of(fragmentActivity).get(AddClubViewModel.class);
            ClubViewModel clubViewModel = ViewModelProviders.of(fragmentActivity).get(ClubViewModel.class);

            clubViewModel.deleteAllClubs();

            JSONArray jsonArray = new JSONArray(rawJson);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                addClubViewModel.addClub(new Club(
                        jsonObject.get("clubName").toString(),
                        jsonObject.get("bio").toString(),
                        jsonObject.get("meetingLocation").toString(),
                        dateFormat.parse(jsonObject.get("firstMeetingDate").toString()),
                        timeFormat.parse(jsonObject.get("meetingTime").toString()),
                        jsonObject.get("recurrenceRule").toString(),
                        jsonObject.get("clubContactNumber").toString(),
                        jsonObject.get("clubEmail").toString()
                ));
            }
        }
        else if (objectType == POST) {
            AddPostViewModel addPostViewModel = ViewModelProviders.of(fragmentActivity).get(AddPostViewModel.class);
            PostViewModel postViewModel = ViewModelProviders.of(fragmentActivity).get(PostViewModel.class);

            postViewModel.deleteAllPosts();

            JSONArray jsonArray = new JSONArray(rawJson);

            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                addPostViewModel.addPost(new Post(
                        jsonObject.get("clubName").toString(),
                        dateTimeFormat.parse(jsonObject.get("timeStamp").toString()),
                        jsonObject.get("text").toString(),
                        jsonObject.get("photo").toString(),
                        0,
                        0,
                        false,
                        true
                ));
            }
        }
        else if (objectType == EVENT) {
            JSONArray jsonArray = new JSONArray(rawJson);

            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (CalendarContractHandler.hasCalendarPermissions(activity, context)) {
                        CalendarContractHandler.addEventToCalendar(
                                activity,
                                context,
                                jsonObject.get("clubName").toString(),
                                jsonObject.get("eventName").toString(),
                                jsonObject.get("eventNotes").toString(),
                                jsonObject.get("eventLocation").toString(),
                                dateTimeFormat.parse(jsonObject.get("eventStartDateTime").toString()),
                                dateTimeFormat.parse(jsonObject.get("eventEndDateTime").toString()),
                                jsonObject.get("recurrenceRule").toString()
                        );
                }
            }
        }
    }

    public static void insertEventIntoServer(final Event event) {
        class SendEventData extends AsyncTask<Void, Void, Void> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Void s) {
                super.onPostExecute(s);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                //VERY UNSAFE IMPLEMENTATION, USE ONLY FOR TESTING
                TrustManager[] trustAllCerts = new TrustManager[]{
                        new X509TrustManager() {
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return null;
                            }
                            public void checkClientTrusted(
                                    java.security.cert.X509Certificate[] certs, String authType) {
                            }
                            public void checkServerTrusted(
                                    java.security.cert.X509Certificate[] certs, String authType) {
                            }
                        }
                };

                try {
                    SSLContext sc = SSLContext.getInstance("SSL");
                    sc.init(null, trustAllCerts, new java.security.SecureRandom());
                    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                }
                catch (Exception e) {
                }
                //VERY UNSAFE IMPLEMENTATION, USE ONLY FOR TESTING

                String result = "";
                try {
                    URL url = new URL("https://clubsatfcuc.xyz/insert_event.php");
                    HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                    httpsURLConnection.setRequestMethod("POST");
                    httpsURLConnection.setDoOutput(true);
                    OutputStream os = httpsURLConnection.getOutputStream();

                    java.sql.Timestamp sqlStartDateTime = new java.sql.Timestamp(event.getEventStartDateTime().getTime());
                    java.sql.Timestamp sqlEndDateTime = new java.sql.Timestamp(event.getEventEndDateTime().getTime());
                    String startDateTime = sqlStartDateTime.toString();
                    String endDateTime = sqlEndDateTime.toString();
                    String recurrenceRule = "";
                    if (event.getRecurrenceRule() != null) {
                        recurrenceRule = event.getRecurrenceRule();
                    }

                    String data =
                            URLEncoder.encode("clubName", "UTF-8") + "=" + URLEncoder.encode(event.getClubName(), "UTF-8") + "&" +
                            URLEncoder.encode("eventName", "UTF-8") + "=" + URLEncoder.encode(event.getEventName(), "UTF-8") + "&" +
                            URLEncoder.encode("eventNotes", "UTF-8") + "=" + URLEncoder.encode(event.getEventDetails(), "UTF-8") + "&" +
                            URLEncoder.encode("eventStartDateTime", "UTF-8") + "=" + URLEncoder.encode(startDateTime, "UTF-8") + "&" +
                            URLEncoder.encode("eventEndDateTime", "UTF-8") + "=" + URLEncoder.encode(endDateTime, "UTF-8") + "&" +
                            URLEncoder.encode("recurrenceRule", "UTF-8") + "=" + URLEncoder.encode(recurrenceRule, "UTF-8") + "&" +
                            URLEncoder.encode("eventLocation", "UTF-8") + "=" + URLEncoder.encode(event.getEventLocation(), "UTF-8");

                    httpsURLConnection.connect();

                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    bufferedWriter.write(data);
                    bufferedWriter.flush();

                    BufferedReader in = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()), 8192);
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        result = result.concat(inputLine);
                    }
                    Log.d("HTTPS RESULT", "Connection sent back: " + result);
                    in.close();
                }
                catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                catch (ProtocolException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }
        }

        SendEventData sendEventData = new SendEventData();
        sendEventData.execute();
    }

    public static void insertClubIntoServer(final Club club) {
        class SendClubData extends AsyncTask<Void, Void, Void> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Void s) {
                super.onPostExecute(s);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                //VERY UNSAFE IMPLEMENTATION, USE ONLY FOR TESTING
                TrustManager[] trustAllCerts = new TrustManager[]{
                        new X509TrustManager() {
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return null;
                            }
                            public void checkClientTrusted(
                                    java.security.cert.X509Certificate[] certs, String authType) {
                            }
                            public void checkServerTrusted(
                                    java.security.cert.X509Certificate[] certs, String authType) {
                            }
                        }
                };

                try {
                    SSLContext sc = SSLContext.getInstance("SSL");
                    sc.init(null, trustAllCerts, new java.security.SecureRandom());
                    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                }
                catch (Exception e) {
                }
                //VERY UNSAFE IMPLEMENTATION, USE ONLY FOR TESTING

                String result = "";
                try {
                    URL url = new URL("https://clubsatfcuc.xyz/insert_club.php");
                    HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                    httpsURLConnection.setRequestMethod("POST");
                    httpsURLConnection.setDoOutput(true);
                    OutputStream os = httpsURLConnection.getOutputStream();

                    java.sql.Date sqlDate = new java.sql.Date(club.getFirstMeetingDate().getTime());
                    java.sql.Time sqlTime = new java.sql.Time(club.getMeetingTime().getTime());
                    String firstMeetingDate = sqlDate.toString();
                    String meetingTime = sqlTime.toString();

                    String data =
                            URLEncoder.encode("clubName", "UTF-8") + "=" + URLEncoder.encode(club.getClubName(), "UTF-8") + "&" +
                            URLEncoder.encode("bio", "UTF-8") + "=" + URLEncoder.encode(club.getBio(), "UTF-8") + "&" +
                            URLEncoder.encode("meetingLocation", "UTF-8") + "=" + URLEncoder.encode(club.getMeetingLocation(), "UTF-8") + "&" +
                            URLEncoder.encode("firstMeetingDate", "UTF-8") + "=" + URLEncoder.encode(firstMeetingDate, "UTF-8") + "&" +
                            URLEncoder.encode("meetingTime", "UTF-8") + "=" + URLEncoder.encode(meetingTime, "UTF-8") + "&" +
                            URLEncoder.encode("recurrenceRule", "UTF-8") + "=" + URLEncoder.encode(club.getRecurrenceRule(), "UTF-8") + "&" +
                            URLEncoder.encode("clubContactNumber", "UTF-8") + "=" + URLEncoder.encode(club.getClubContactNumber(), "UTF-8") + "&" +
                            URLEncoder.encode("clubEmail", "UTF-8") + "=" + URLEncoder.encode(club.getClubEmail(), "UTF-8");

                    httpsURLConnection.connect();

                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    bufferedWriter.write(data);
                    bufferedWriter.flush();

                    BufferedReader in = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()), 8192);
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        result = result.concat(inputLine);
                    }
                    Log.d("HTTPS RESULT", "Connection sent back: " + result);
                    in.close();
                }
                catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                catch (ProtocolException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }
        }

        SendClubData sendClubData = new SendClubData();
        sendClubData.execute();
    }

    public static void insertPostIntoServer(final Post post) {
        class SendPostData extends AsyncTask<Void, Void, Void> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Void s) {
                super.onPostExecute(s);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                //VERY UNSAFE IMPLEMENTATION, USE ONLY FOR TESTING
                TrustManager[] trustAllCerts = new TrustManager[]{
                        new X509TrustManager() {
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return null;
                            }
                            public void checkClientTrusted(
                                    java.security.cert.X509Certificate[] certs, String authType) {
                            }
                            public void checkServerTrusted(
                                    java.security.cert.X509Certificate[] certs, String authType) {
                            }
                        }
                };

                try {
                    SSLContext sc = SSLContext.getInstance("SSL");
                    sc.init(null, trustAllCerts, new java.security.SecureRandom());
                    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                }
                catch (Exception e) {
                }
                //VERY UNSAFE IMPLEMENTATION, USE ONLY FOR TESTING

                String result = "";
                try {
                    URL url = new URL("https://clubsatfcuc.xyz/insert_post.php");
                    HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                    httpsURLConnection.setRequestMethod("POST");
                    httpsURLConnection.setDoOutput(true);
                    OutputStream os = httpsURLConnection.getOutputStream();

                    java.sql.Timestamp timestamp = new java.sql.Timestamp(post.getTimestamp().getTime());
                    String timestampStr = timestamp.toString();

                    String data =
                            URLEncoder.encode("clubName", "UTF-8") + "=" + URLEncoder.encode(post.getClubName(), "UTF-8") + "&" +
                            URLEncoder.encode("timeStamp", "UTF-8") + "=" + URLEncoder.encode(timestampStr, "UTF-8") + "&" +
                            URLEncoder.encode("text", "UTF-8") + "=" + URLEncoder.encode(post.getTextContent(), "UTF-8") + "&" +
                            URLEncoder.encode("photo", "UTF-8") + "=" + URLEncoder.encode(post.getPhotoContent(), "UTF-8");

                    httpsURLConnection.connect();

                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    bufferedWriter.write(data);
                    bufferedWriter.flush();

                    BufferedReader in = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()), 8192);
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        result = result.concat(inputLine);
                    }
                    Log.d("HTTPS RESULT", "Connection sent back: " + result);
                    in.close();
                }
                catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                catch (ProtocolException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }
        }

        SendPostData sendPostData = new SendPostData();
        sendPostData.execute();
    }
}
