package com.gymwn.vertretungsplan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView toolbar_image;
    private NavigationView main_nav;
    private DrawerLayout main_drawer_layout;
    private TextView toolbar_text;
    private FrameLayout body_mainframe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar_image = findViewById(R.id.toolbar_image);
        main_nav = findViewById(R.id.main_nav);
        main_drawer_layout = findViewById(R.id.main_drawer_layout);
        toolbar_text = findViewById(R.id.toolbar_text);
        body_mainframe = findViewById(R.id.body_mainframe);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, main_drawer_layout, (Toolbar)findViewById(R.id.toolbar), 0, 0);
        main_drawer_layout.setDrawerListener(toggle);
        toggle.syncState();
        main_nav.setNavigationItemSelectedListener(this);
        setFragment(new Fragment_Plan());




    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id != R.id.github)
        toolbar_text.setText(item.getTitle());
        switch (id) {
            case R.id.menu_plan:
                setFragment(new Fragment_Plan());
                break;
            case R.id.menu_notification:
                setFragment(new Fragment_notification());
                break;
            case R.id.menu_info:
                setFragment(new Fragment_Info());
                break;
            case R.id.github:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/JakobRoehl/vertretungsplan"));
                startActivity(browserIntent);
                break;
            default:
        }
        main_drawer_layout.closeDrawer(Gravity.LEFT);
        return false;
    }
    private void setFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.body_mainframe, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}