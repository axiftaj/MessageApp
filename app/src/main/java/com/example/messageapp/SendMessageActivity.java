package com.example.messageapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.messageapp.SendMessageActivity.SMSUtils.sendSMS;

public class SendMessageActivity extends AppCompatActivity {

    private TextView message ;
    private Button sendMessage ;
    public final int SEND_SMS = 1 ;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);


        message = findViewById(R.id.et_message);
        sendMessage = findViewById(R.id.send_message);

        final String str_message = message.getText().toString();

        sendSmsPermission();

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMS(getApplicationContext() , "3434" , str_message) ;
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == SEND_SMS) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                // showRationale = false if user clicks Never Ask Again, otherwise true
                boolean showRationale = (boolean) shouldShowRequestPermissionRationale( Manifest.permission.SEND_SMS);

                if (showRationale) {

                } else {
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void sendSmsPermission() {

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.SEND_SMS)) {
            }

            requestPermissions(new String[]{Manifest.permission.SEND_SMS},
                    SEND_SMS);
        }
    }

    public static class SMSUtils extends BroadcastReceiver {

        public static final String SENT_SMS_ACTION_NAME = "SMS_SENT";
        public static final String DELIVERED_SMS_ACTION_NAME = "SMS_DELIVERED";

        @Override
        public void onReceive(Context context, Intent intent) {
            //Detect l'envoie de sms
            if (intent.getAction().equals(SENT_SMS_ACTION_NAME)) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK: // Sms sent
                        Toast.makeText(context,"sms_send", Toast.LENGTH_LONG).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE: // generic failure
                        Toast.makeText(context, "sms_not_send", Toast.LENGTH_LONG).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE: // No service
                        Toast.makeText(context,"sms_not_send_no_service", Toast.LENGTH_LONG).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU: // null pdu
                        Toast.makeText(context,"sms_not_send", Toast.LENGTH_LONG).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF: //Radio off
                        Toast.makeText(context, "sms_not_send_no_radio", Toast.LENGTH_LONG).show();
                        break;
                }
            }
            //detect la reception d'un sms
            else if (intent.getAction().equals(DELIVERED_SMS_ACTION_NAME)) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(context, "sms_receive", Toast.LENGTH_LONG).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(context, "sms_not_receive", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        }


        public static boolean canSendSMS(Context context) {
            return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TELEPHONY);
        }

        public static void sendSMS(final Context context, String phoneNumber, String message) {

            if (!canSendSMS(context)) {
                Toast.makeText(context, "cannot_send_sms", Toast.LENGTH_LONG).show();
                return;
            }

            PendingIntent sentPI = PendingIntent.getBroadcast(context, 0, new Intent(SENT_SMS_ACTION_NAME), 0);
            PendingIntent deliveredPI = PendingIntent.getBroadcast(context, 0, new Intent(DELIVERED_SMS_ACTION_NAME), 0);

            final SMSUtils smsUtils = new SMSUtils();
            //register for sending and delivery
            context.registerReceiver(smsUtils, new IntentFilter(SMSUtils.SENT_SMS_ACTION_NAME));
            context.registerReceiver(smsUtils, new IntentFilter(DELIVERED_SMS_ACTION_NAME));

            SmsManager sms = SmsManager.getDefault();
            ArrayList<String> parts = sms.divideMessage(message);

            ArrayList<PendingIntent> sendList = new ArrayList<>();
            sendList.add(sentPI);

            ArrayList<PendingIntent> deliverList = new ArrayList<>();
            deliverList.add(deliveredPI);

            sms.sendMultipartTextMessage(phoneNumber, null, parts, sendList, deliverList);

            //we unsubscribed in 10 seconds
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    context.unregisterReceiver(smsUtils);
                }
            }, 10000);

        }
    }

}
