package com.viit.uaha;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class form extends AppCompatActivity {
    Button submit;
    EditText name,email,number,age,address,height,weight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        number=findViewById(R.id.number);
        age=findViewById(R.id.age);
        address=findViewById(R.id.address);
        height=findViewById(R.id.height);
        weight=findViewById(R.id.weight);
        submit=findViewById(R.id.button);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setting conditions
                String validemail = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +

                        "\\@" +

                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +

                        "(" +

                        "\\." +

                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +

                        ")+";

                String et_email=email.getText().toString();
                Matcher matcher= Pattern.compile(validemail).matcher(et_email);

                if (!matcher.matches())
                {
                    email.setError("Enter Valid Email");
                }


                if (name.length()==0)
                {
                    name.setError("Enter Name");
                }
                if (email.length()==0)
                {
                    email.setError("Enter Email");
                }

                if (number.length()<10 || number.length()>10)
                {
                    number.setError("Enter Valid Phone Number");
                }

                if (number.length()==0)
                {
                    number.setError("Enter Phone Number");
                }
                if (age.length()>2)
                {
                    age.setError("Age cannot exceed 99 years");
                }
                if (age.length()==0)
                {
                    age.setError("Enter Age");
                }
                if (address.length()==0)
                {
                    address.setError("Enter Address");
                }
                if (height.length()==0)
                {
                    height.setError("Enter Height");
                }
                if (weight.length()==0)
                {
                    weight.setError("Enter Weight");
                }
            }
        });




    }

}