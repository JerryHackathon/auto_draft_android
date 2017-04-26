package com.example.pianoafrik.ormapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.example.pianoafrik.ormapp.Adapter.PhoneNumberAdapter;
import com.example.pianoafrik.ormapp.api.Globall;
import com.example.pianoafrik.ormapp.model.DaoSession;
import com.example.pianoafrik.ormapp.model.OnlineTeam;
import com.example.pianoafrik.ormapp.model.PhoneNumber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidsdk.devless.io.devless.main.Devless;

public class TryActivity extends AppCompatActivity {

    MyApplication myApplication;
    ListView listView;
    List<PhoneNumber> numbers;
    EditText etPhoneNumber;
    Button btnPhoneNumber;
    Devless devless;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_try);

        devless = new Devless(this, Globall.APPURL, Globall.SERVICENAME, Globall.DEVLESSTOKEN);

        //Instance of myApplication which contains the daoSession
        myApplication = (MyApplication) getApplication();

        //Get listView
        listView = (ListView)findViewById(R.id.list_phone_number);


        getData(this);


        //set click for items in listView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //delete item at that position
                openDialogForEachElement(view.getContext(), position);


            }
        });







        //Get button and Edit Text
        etPhoneNumber =  (EditText)findViewById(R.id.etPhonNumber);
        btnPhoneNumber = (Button)findViewById(R.id.btnAddPhoneNumber);

        btnPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //set condition to run code only when there is an element present in edit text
                if (etPhoneNumber.getText().length() != 0) {

                    //create a new db element with textview value
                    addOneToPhoneDb(etPhoneNumber.getText().toString());

                    //refresh the list view to show whats been added
                    refreshListView(v.getContext());

                    //empty edit text
                    emptyEditText(etPhoneNumber);


                    //update Online table
                    upDateTable(TryActivity.this);


                } else{

                    Toast.makeText(v.getContext(), "Please enter a number on the nini", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                startActivity(new Intent(this, HomeActivity.class));
                return true;

            case R.id.action_clearDb:
                // User chose the "Clear All" action, mark the current item
                // as a favorite...
                openDialogForClearAll(this);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private DaoSession getAppDaoSession() {
        return myApplication.getDaoSession();
    }

    private  void refreshListView (Context context) {
        //get all that's in the db
        numbers = getAppDaoSession().getPhoneNumberDao().loadAll();

        numbers = invertArrayList(numbers);


        //Create an instance of the custom adapter
        PhoneNumberAdapter adapter = new PhoneNumberAdapter(context, 0, numbers);

        //set your adapter
        listView.setAdapter(adapter);
    }

    private void addChawToPhoneDb (List<PhoneNumber> phoneNumbers) {
        //save it
        getAppDaoSession().getPhoneNumberDao().insertInTx(phoneNumbers);

    }

    private void addOneToPhoneDb (String string) {
        //Create an entry
        PhoneNumber number = new PhoneNumber(null, string);
        //save it
        getAppDaoSession().getPhoneNumberDao().insert(number);

    }

    private void  emptyEditText (EditText editText){
        editText.setText("");
    }

    private void deleteOneFromDb(int position) {
        PhoneNumber number = numbers.get(position);
        getAppDaoSession().getPhoneNumberDao().delete(number);

        //refresh db
        refreshListView(this);
    }

    private void emptyDb (final Context context) {
        getAppDaoSession().getPhoneNumberDao().deleteAll();


        //Map
        Map<String, Object> changes = new HashMap<>();
        changes.put("name_list", "nil");

        //make an edit request
        devless.edit(Globall.AUTODRAFTTABLE, changes, "2", new Devless.RequestResponse() {
            @Override
            public void OnSuccess(String s) {
                //Toast.makeText(context, "updated successfully", Toast.LENGTH_LONG).show();
            }
        });
        //refresh db
        refreshListView(this);
    }

    private void openDialogForClearAll (final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are You sure you wanna empty The whole databse? "  );
        builder.setCancelable(true);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                emptyDb(context);
                dialog.cancel();

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    private void openDialogForEachElement (Context context, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are You sure you wanna delete this ? "  );
        builder.setCancelable(true);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteOneFromDb(position);
                dialog.cancel();

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    private List<PhoneNumber> invertArrayList (List<PhoneNumber> arrayList) {
        List<PhoneNumber> newArrayList = new ArrayList<>();
        for(int i = 0; i < arrayList.size(); i++){
            newArrayList.add(0, arrayList.get(i));
        }
        return newArrayList;
    }

    private void getData(final Context context){
        Globall.getNamesFromDevless(devless, new Globall.OnNamesReturned() {
            @Override
            public void OnSuccessListReturned(List<PhoneNumber> names) {
                //numbers = getAppDaoSession().getPhoneNumberDao().loadAll();

                List<PhoneNumber> newnames = invertArrayList(names);

                //delete whats in the db
                emptyDb(context);

                //populateDb
                addChawToPhoneDb(newnames);

                Toast.makeText(context, "db:loaded:successfully", Toast.LENGTH_LONG).show();

                //Create an instance of the custom adapter
                PhoneNumberAdapter adapter = new PhoneNumberAdapter(TryActivity.this, 0, names);
                listView.setAdapter(adapter);
            }
        });
    }


    public  void upDateTable(final Context context){
        //Update the db
        //create an OnlineTeamObject
        OnlineTeam onlineTeam = Globall.postTeamBody(myApplication.getDaoSession().getPhoneNumberDao().loadAll());

        //TREAT IT A STRING WITH
        String nameList  = Globall.groupMembersNames(onlineTeam);

        //Map
        Map<String, Object> changes = new HashMap<>();
        changes.put("name_list", nameList);

        //make an edit request
        devless.edit(Globall.AUTODRAFTTABLE, changes, "2", new Devless.RequestResponse() {
            @Override
            public void OnSuccess(String s) {
               // Toast.makeText(context, "updated successfully", Toast.LENGTH_LONG).show();
            }
        });
    }

    //TODO: correct the empty thingy{crushes whe ther is only one element} check it
}
