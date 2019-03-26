package com.viit.uaha;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static java.lang.String.format;
import com.viit.uaha.PackageInfo;
public class MainActivity extends AppCompatActivity {


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

            switch (id){
                case R.id.action_help:
                    Toast.makeText(this, "Contact Customer Care", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.action_settings:
                    Toast.makeText(this, "To Be Implemented", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.action_exit:
                    final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Do you really want to Exit ?");
                    builder.setCancelable(true);
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });
                    android.support.v7.app.AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    break;
            }
            return true;
        }
        /*//noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "To be implemented", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.action_account) {

            Intent intent = new Intent(this, Login.class);
            startActivity(intent);

        }


        if (id == R.id.action_help) {
            Toast.makeText(this, "Visit developer page for help", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.action_exit) {
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
*/

    //navbar


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override


        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.action_Menu:

                    selectedFragment = new DailyMenu();

                    break;
                case R.id.action_PackageInfo:
                    selectedFragment = new PackageInfo();
                    break;
                case R.id.action_account:

                    selectedFragment = new Account();
                    break;


            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

            return true;
        }
    };


    public void Register(View view) {

        Intent intent = new Intent(this, form.class);
        startActivity(intent);

    }
public void showMessage(String title,StringBuffer Message)
    {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        //builder.show();
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setPositiveButton("Select", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
}


public void loadRecommend(View v) {

        DocumentReference Rec;
Rec=db.document("Customers/"+currentEmail());

final TextView tv=findViewById(R.id.textView14);
    Rec
            .get()
            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                       @Override
                                       public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                           if (task.isSuccessful()) {
                                               DocumentSnapshot document = task.getResult();
                                               if (document.exists()) {
                                                   Object recommended=document.get("recommended");

                                                   tv.setText(recommended.toString());
                                               }

                                               else {
                                                   Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                               }
                                           } else {
                                               Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                               task.getException();
                                           }


                                       }
            });

}
//connect current email with document here. find recommended package and return text
    //compare with cusines collection for info

    public void selectRecommend(View v)
    {
        TextView tv=findViewById(R.id.textView14);
        final String Package=tv.toString();
        DocumentReference m;
        m=db.document("cusines/"+Package);

        final StringBuffer buffer = new StringBuffer();

        m
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                int numb=1;
                                String pretty;
                                for(Object item : document.getData().values()) {

                                    buffer.append("\n\nDay "+numb+"\n\n");
                                    pretty=item.toString();
                                    pretty=pretty.replaceAll("\\{","");
                                    pretty=pretty.replaceAll("\\}","");
                                    pretty=pretty.replaceAll("\\,","\n");
                                    pretty =pretty.replaceAll("type1=","");
                                    pretty =pretty.replaceAll("type2=","");
                                    pretty =pretty.replaceAll("type3=","");

                                    buffer.append(pretty);

                                    numb++;
                                }
                                showMessage(Package, buffer);
                            }
                            else {
                                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            task.getException();
                        }


                    }
                });




    }


    public void loadMeals(View v) {

        DocumentReference m;
        final String Package;

        switch(v.getId())
        {
            //MAHARASHTRIAN
            case R.id.textView2:
                m = db.document("cusines/Maharashtrian-Nor");
                Package="Maharashtrian";
                break;
            case R.id.button10:
                m = db.document("cusines/Maharashtrian-Vegetarian");
                Package="Maharashtrian-Vegetarian";
                break;
            case R.id.button11:
                m = db.document("cusines/Maharashtrian-Vegetarian");
                Package="Maharashtrian-Jain";
                break;
            case R.id.button9:
                m = db.document("cusines/Maharashtrian-Vegetarian");
                Package="Maharashtrian-Vegan";
                break;



            //CHINESE
            case R.id.textView6:
                m = db.document("cusines/Chinese-Normal");
                Package="Chinese";
                break;
            case R.id.button12:
                m = db.document("cusines/Chinese-Vegetarian");
                Package="Chinese-Vegetarian";
                break;
            case R.id.button13:
                m = db.document("cusines/Chinese-Jain");
                Package="Chinese-Jain";
                break;
            case R.id.button14:
                m = db.document("cusines/Chinese-Vegan");
                Package="Chinese-Vegan";
                break;


            //LEBANESE
            case R.id.textView7:
                m = db.document("cusines/Lebanese-Normal");
                Package="Lebanese";
                break;
            case R.id.button15:
                m = db.document("cusines/Lebanese-Vegetarian");
                Package="Lebanese-Vegetarian";
                break;
            case R.id.button16:
                m = db.document("cusines/Lebanese-Vegetarian");
                Package="Lebanese-Jain";
                break;
            case R.id.button17:
                m = db.document("cusines/Lebanese-Vegetarian");
                Package="Lebanese-Vegan";
                break;

            //THAI
            case R.id.textView9:
                m = db.document("cusines/Thai-Nor");
                Package="Thai";
                break;
            case R.id.button18:
                m = db.document("cusines/Thai-Veg");
                Package="Thai-Vegetarian";
                break;
            case R.id.button19:
                m = db.document("cusines/Thai-Veg");
                Package="Thai-Jain";
                break;
            case R.id.button20:
                m = db.document("cusines/Thai-Veg");
                Package="Thai-Vegan";
                break;


            //WESTERN
            case R.id.textView10:
                m = db.document("cusines/Western Nor");
                Package="Western";
                break;
            case R.id.button21:
                m = db.document("cusines/Western-Veg");
                Package="Western-Vegetarian";
                break;
            case R.id.button22:
                m = db.document("cusines/Western-Veg");
                Package="Western-Jain";
                break;
            case R.id.button23:
                m = db.document("cusines/Western-Veg");
                Package="Western-Vegan";
                break;



            //PUNJABI
            case R.id.textViewPunjabi:
                m = db.document("cusines/Punjabi-Normal");
                Package="Punjabi";
                break;
            case R.id.button24:
                m = db.document("cusines/Punjabi-Vegetarian");
                Package="Punjabi-Vegetarian";
                break;
            case R.id.button25:
                m = db.document("cusines/Punjabi-Jain");
                Package="Punjabi-Jain";
                break;
            case R.id.button26:
                m = db.document("cusines/Punjabi-Vegan");
                Package="Punjabi-Vegan";
                break;



            default:
                m = db.document("cusines/Punjabi-Vegetarian");
                Package="Punjabi";

        }


        Date now = new Date();
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE"); // the day of the week spelled out completely
        //System.out.println(simpleDateformat.format(now));

        final StringBuffer buffer = new StringBuffer();


        m
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                int numb=1;
                                String pretty;
                                for(Object item : document.getData().values()) {

                                    buffer.append("\n\nDay "+numb+"\n\n");
                                    pretty=item.toString();
                                    pretty=pretty.replaceAll("\\{","");
                                    pretty=pretty.replaceAll("\\}","");
                                    pretty=pretty.replaceAll("\\,","\n");
                                    pretty =pretty.replaceAll("type1=","");
                                    pretty =pretty.replaceAll("type2=","");
                                    pretty =pretty.replaceAll("type3=","");

                                    buffer.append(pretty);

                                    numb++;
                                }
                                    showMessage(Package, buffer);
                                }
                             else {
                                Toast.makeText(MainActivity.this, "Error doc doesnt exisst", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            task.getException();
                        }


                    }
                });

    }


    public void menuDay(View v) {

        DocumentReference m = db.document("cusines/Punjabi-Normal");


        //today
        Date now = new Date();
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE"); // the day of the week spelled out completely
         final String day=simpleDateformat.format(now);
/*
        if(d=="Saturday"||d=="Sunday")
           d="Monday";
        final String day=d;*/


        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 4);
        dt = c.getTime();
        simpleDateformat = new SimpleDateFormat("EEEE"); // the day of the week spelled out completely
         final String dayTomorrow=simpleDateformat.format(dt);
        /* if(dayT=="Saturday")
            dayT="Monday";
        else if(dayT=="Sunday")
            dayT="Tuesday";

        final String dayTomorrow=dayT;
        */

        final TextView textToday=findViewById(R.id.textToday);
        final TextView textTom=findViewById(R.id.textTomorrow);

        final StringBuffer buffer = new StringBuffer();


                m
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {

                                String pretty;
                                Object item=document.getData().get(day);

                                        pretty = item.toString();
                                        pretty = pretty.replaceAll("\\{", "");
                                        pretty = pretty.replaceAll("\\}", "");
                                        pretty = pretty.replaceAll("\\,", "\n");
                                pretty =pretty.replaceAll("type1=","");
                                pretty =pretty.replaceAll("type2=","");
                                pretty =pretty.replaceAll("type3=","");

                                textToday.setText(pretty);

                            item=document.getData().get(dayTomorrow);

                           pretty = item.toString();
                            pretty = pretty.replaceAll("\\{", "");
                            pretty = pretty.replaceAll("\\}", "");
                            pretty = pretty.replaceAll("\\,", "\n");
                            pretty =pretty.replaceAll("type1=","");
                            pretty =pretty.replaceAll("type2=","");
                                pretty =pretty.replaceAll("type3=","");
                            textTom.setText(pretty);
                        }



                            else {
                                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            task.getException();
                        }


                    }
                });

    }





    public void SignOut(View view) {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
        finish();

    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DailyMenu()).commit();


    }
    private static final String TAG = "MainActivity";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference Ref = db.collection("Customers");
    private DocumentReference Doc=Ref.document(currentEmail());


    @Override
    public void onBackPressed(){
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Do you really want to Exit ?");
        builder.setCancelable(true);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        android.support.v7.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

        public String currentEmail()
        {
            FirebaseAuth mAuth;
            mAuth=FirebaseAuth.getInstance();
            FirebaseUser user=mAuth.getCurrentUser();

            return user.getEmail();
        }

        public void updateHW(View v)
        {   String emailAuth=currentEmail();

            EditText editTextH=findViewById(R.id.height);
            EditText editTextW=findViewById(R.id.weight);

           final String height=editTextH.getText().toString();
           final String weight=editTextW.getText().toString();
            float h=Float.parseFloat(height);
            float w=Float.parseFloat(weight);
            float bmi=w/((h/100)*(h/100));
            final String BMI= String.format("%.2f", bmi);

            Toast.makeText(MainActivity.this,"Updated", Toast.LENGTH_LONG).show();

            Ref.document(currentEmail()).update("height",height,"weight",weight,"BMI",BMI);

            Toast.makeText(MainActivity.this,"Updated", Toast.LENGTH_LONG).show();


        }




        public void loadNotes(View v)
        {
           final TextView bmi=findViewById(R.id.textView13);



                    Doc
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                                DbHandler note = documentSnapshot.toObject(DbHandler.class);
                                String BMI = note.getBMI();
                                String height=note.getHeight();
                                String weight=note.getWeight();

                                //Toast.makeText(MainActivity.this, currentEmail() + "  Your Bmi is :" + BMI, Toast.LENGTH_LONG).show();
                            bmi.setText("\t\t\t\tHeight:"+height+"\n\n\t\t\t\tWeight:"+weight+"\n\n\n\t\t\t\tYour Bmi is : " + BMI);
                            bmi.setTextSize(26);






                        }

                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
                        }
                    });

        }


}
