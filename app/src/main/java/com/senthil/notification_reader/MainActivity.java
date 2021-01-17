package com.senthil.notification_reader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {
    Button Permission;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Permission = findViewById(R.id.Permission_button) ;
        Permission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    //service is enabled by calling...
                startActivity(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));

            }
        });
        LocalBroadcastManager.getInstance(this).registerReceiver(onNotice, new IntentFilter("Msg"));

    }
    private BroadcastReceiver onNotice= new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String pack = intent.getStringExtra("package");
            final String title = intent.getStringExtra("title");
            final String text = intent.getStringExtra("text");

            final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("notify").push();
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ref.child("pack").setValue(pack);
                    ref.child("tittle").setValue(title);
                    ref.child("text").setValue(text);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });


//            final long timeCountInMilliSeconds = 2000 * 60 * 1000;
//
//            new Timer().scheduleAtFixedRate(new TimerTask() {
//                @Override
//                public void run() {
//                    final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("notify").push();
//                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            ref.child("pack").setValue(pack);
//                            ref.child("tittle").setValue(title);
//                            ref.child("text").setValue(text);
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//
//                    });
//                }
//            },0,timeCountInMilliSeconds);
//
//


        }
    };
}