package com.example.pianoafrik.ormapp.api;

import android.util.Log;

import com.example.pianoafrik.ormapp.model.OnlineTeam;
import com.example.pianoafrik.ormapp.model.PhoneNumber;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidsdk.devless.io.devless.main.Devless;

/**
 * Created by Ruby on 4/26/17.
 */

public class Globall {

    public static final String APPURL = "http://newerapper.herokuapp.com";
    public static final String SERVICENAME = "auto_draft";
    public static final String DEVLESSTOKEN = "5c820eeb7b60679ad14bf2aa57aa2b95";
    public static final String AUTODRAFTTABLE = "auto_draft_name_list";


    public static String groupMembers (List<Object> list) {
        String result = "";

        for(int i =0; i < list.size(); i++){
            result += list.get(i).toString() + "\n";
        }

        return result;
    }

    public static OnlineTeam postTeamBody (List<PhoneNumber> names) {
        ArrayList<String> array = new ArrayList<>();
        for (int i =0; i< names.size(); i++){
            array.add(names.get(i).getNumber());
        }
        return new OnlineTeam(array);
    }

    public static String groupMembersNames (OnlineTeam list) {
        String result = "";

        for(int i =0; i < list.getList().size(); i++){
            result += list.getList().get(i) + ",";
        }

        return result.substring(0,result.length()-1);
    }

    public static void getNamesFromDevless(Devless devless, final OnNamesReturned onNames){
        final List<PhoneNumber> phoneNumbers = new ArrayList<>();
        devless.getData(AUTODRAFTTABLE, new Devless.RequestResponse() {
            @Override
            public void OnSuccess(String s) {

                try {
                    JSONObject JO = new JSONObject(s);
                    String payload = JO.getString("payload");
                    JSONObject payloadObject = new JSONObject(payload);
                    String result = payloadObject.getString("results");
                    JSONArray jsonArray = new JSONArray(result);

                    for(int i =0; i < jsonArray.length(); i++){
                        JSONObject resultObject = jsonArray.getJSONObject(i);
                        String  nameList = resultObject.getString("name_list");
                        String user_name = resultObject.getString("user");
                        String[] nameListArray = nameList.split(",");
                        if (user_name.equals("kofi")){
                            for(int j = 0; j < nameListArray.length; j++) {
                                //Log.e("namlistArray", nameListArray[j]);
                                PhoneNumber phoneNumber = new PhoneNumber(nameListArray[j]);
                                phoneNumbers.add(0, phoneNumber);
                            }


                        }


                    }

                    onNames.OnSuccessListReturned(phoneNumbers);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });

    }

    public interface OnNamesReturned {
        void OnSuccessListReturned(List<PhoneNumber> names);
    }

}
