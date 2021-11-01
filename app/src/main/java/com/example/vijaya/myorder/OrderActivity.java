package com.example.vijaya.myorder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ordersummary);
        final String orderSummaryText=getIntent().getStringExtra("orderSummary");
        final String name=getIntent().getStringExtra("name");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            TextView orderSummary=(TextView) findViewById(R.id.orderSummary);
            orderSummary.setText(orderSummaryText);
        }

        Button confirmOrder=(Button) findViewById(R.id.confirmOrder);
        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail(name, orderSummaryText);
            }
        });

    }
    public void sendEmail(String name, String output) {
        // Write the relevant code for triggering email

        // Hint to accomplish the task

/*
        Uri uri = Uri.parse("mailto:" + "tipparaju2010@gmail.com")
                .buildUpon()
                .appendQueryParameter("subject", "Order summary of "+name)
                .appendQueryParameter("body", output)
                .build();

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
        startActivity(Intent.createChooser(emailIntent, "chooserTitle"));*/

        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{"tipparaju2010@gmail.com"});
        email.putExtra(Intent.EXTRA_SUBJECT, "Order summary of "+name);
        email.putExtra(Intent.EXTRA_TEXT, output);
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "Send Mail Using :"));


            // Write the relevant code for triggering email

            // Hint to accomplish the task
/*
            Intent intent= new Intent(Intent.ACTION_SENDTO);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"tipparaju2010@gmail.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Order summary of "+name);
            intent.putExtra(Intent.EXTRA_TEXT, output);

            if(intent.resolveActivity(getPackageManager())!=null)
                startActivity(Intent.createChooser(intent, "chooserTitle"));*/

    }
}
