package com.example.pianoafrik.ormapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pianoafrik.ormapp.Adapter.FromGroupsAdapter;
import com.example.pianoafrik.ormapp.api.Globall;
import com.example.pianoafrik.ormapp.model.DaoSession;
import com.example.pianoafrik.ormapp.model.OnlineTeam;
import com.example.pianoafrik.ormapp.model.Team;
import com.example.pianoafrik.ormapp.model.PhoneNumber;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import androidsdk.devless.io.devless.main.Devless;


public class HomeActivity extends AppCompatActivity {

    MyApplication myApplication;
    TextView anotherPage;
    ListView groupList;
    EditText membersPerTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        groupList = (ListView)findViewById(R.id.group_list);

        myApplication = (MyApplication) getApplication();
        membersPerTeam = (EditText)findViewById(R.id.members_per_team);
        final List<PhoneNumber> names = myApplication.getDaoSession().getPhoneNumberDao().loadAll();


        //create an OnlineTeamObject
        final OnlineTeam onlineTeam = Globall.postTeamBody(names);

        //TREAT IT A STRING WITH
        String nameList  = Globall.groupMembersNames(onlineTeam);

        Button formGroups = (Button)findViewById(R.id.form_groupd);



        formGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int members =Integer.parseInt(membersPerTeam.getText().toString().trim());
                List<Team> groups = formTeams(names, members);
                FromGroupsAdapter adapter = new FromGroupsAdapter(v.getContext(), 0, groups);
                groupList.setAdapter(adapter);
            }
        });





    }






    private DaoSession getAppDaoSession() {
        return (myApplication.getDaoSession());
    }


    public List<Object> getAllNames(List<PhoneNumber> names) {

        List<Object> result = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            result.add(names.get(i).getNumber());
        }

        return result;
    }


    private List<Object> randomizeList(List<Object> toBeRandomized) {
        List<Object> result = new ArrayList<>();
        long seed = System.nanoTime();
        Collections.shuffle(toBeRandomized, new Random(seed));
        return result;

    }


    private  List<List<Object>> createBatch(List<Object> originalList,
                                                  int chunkSize) {

        List<List<Object>> listOfChunks = new ArrayList<List<Object>>();
        for (int i = 0; i < originalList.size() / chunkSize; i++) {
            listOfChunks.add(originalList.subList(i * chunkSize, i * chunkSize
                    + chunkSize));
        }
        if (originalList.size() % chunkSize != 0) {
            listOfChunks.add(originalList.subList(originalList.size()
                    - originalList.size() % chunkSize, originalList.size()));
        }
        return listOfChunks;
    }


    private List<List<Object>> createGroups(List<PhoneNumber> names, int size){

        List<Object> nameList = getAllNames(names);
        randomizeList(randomizeList(randomizeList(nameList)));
        return createBatch(nameList,size);
    }


    private List<Team> listMembers(List<List<Object>> groupList){

        List<Team> listmemner = new ArrayList<>();
        for (int i =0; i < groupList.size(); i++){

            listmemner.add( new Team("Group " + String.valueOf(i + 1), groupList.get(i)));
        }

        return listmemner;

    }


    private List<Team> formTeams (List<PhoneNumber> names, int size) {
       List<List<Object>> group = createGroups(names, size);
       return  listMembers(group);

    }





























    //List<PhoneNumber> numbers = getAppDaoSession().getPhoneNumberDao().loadAll();

    //Create a db
//        List<PhoneNumber> phoneNumbers = new ArrayList<>(Arrays.asList(
//                new PhoneNumber(null, "345467827272")
//
//        ));

    //save it
//        getAppDaoSession().getPhoneNumberDao().insertInTx(phoneNumbers);

    //show it
/*
    List<PhoneNumber> numbers = getAppDaoSession().getPhoneNumberDao().loadAll();
        for (PhoneNumber n :numbers) {

        Toast.makeText(HomeActivity.this, String.valueOf(n.getNumber()), Toast.LENGTH_LONG).show();
    }
    */
}
