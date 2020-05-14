package com.example.meetyourteacher;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class Profile extends AppCompatActivity
{

    @Override
    public void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        BottomNavigationView btmNavigationView = findViewById(R.id.navigationView);

        btmNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                switch (menuItem.getItemId())
                {
                    case R.id.navigation_profile:
                        break;
                    case R.id.navigation_search:
                        break;
                    case R.id.navigation_videocall:
                        break;
                }
            }
        });

        ImageButton btnCall = (ImageButton) findViewById(R.id.btnCall);
        btnCall.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:+51983746382"));
                if (ActivityCompat.checkSelfPermission(Profile.this, Manifest.permission.CALL_PHONE) !=
                        PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(Profile.this, "Calling Error....", Toast.LENGTH_LONG).show();
                    return;
                }

                startActivity(intCall);
            }
        });
    }

    public void Videocall(View view)
    {
        Intent intVideoCall= new Intent(getApplicationContext(), VideoCall.class);
        startActivity(intVideoCall);
    }


    public void GoogleMaps(View view) {
        Intent intMapsActvity= new Intent(getApplicationContext(), MapsActivity.class);
        startActivity(intMapsActvity);
    }
}



