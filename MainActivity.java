package com.example.hendawy.metro.activites;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hendawy.metro.R;
import com.example.hendawy.metro.Utils.Constants;
import com.example.hendawy.metro.helper.StationManger;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static int flag;
    TextView currentStation, targetStation, infoText, totalStationText, totalCostText, totalTimeText;
    ImageView ticketImageView;
    LinearLayout instructionLayout, infoLayout;
    StationManger stationManger;
    int currentLine = 0;
    int destinationLine = 0;
    int numberOfTransaction = 0;
    int currentStationValue = 0;
    int targetStationValue = 0;
    int totalStations = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //layout
        instructionLayout = findViewById(R.id.instruct);
        infoLayout = findViewById(R.id.infolayout);
        // textviews
        currentStation = findViewById(R.id.currentStation);
        targetStation = findViewById(R.id.targetStation);
        infoText = findViewById(R.id.infoText);
        totalStationText = findViewById(R.id.totalStations);
        totalCostText = findViewById(R.id.totalCost);
        totalTimeText = findViewById(R.id.totalTime);
        //image
        ticketImageView = findViewById(R.id.ticketImage);
        // shared preferences
        stationManger = new StationManger(getApplicationContext());

        instructionLayout.setVisibility(View.VISIBLE);
        infoLayout.setVisibility(View.GONE);

        if (stationManger.getCurrentStationNumber() > 0) {
            currentStationValue = stationManger.getCurrentStationNumber();
            currentStation.setText(stationManger.getCurrentStationName());
        }

        if (stationManger.getDestinationStationNumber() > 0) {

            targetStationValue = stationManger.getDestinationStationNumber();
            targetStation.setText(stationManger.getDestinationStationName());
        }

        if (currentStationValue > 0 && targetStationValue > 0) {

            /*stationManger.removeCurrentStationNumber();
            stationManger.removeCurrentStationName();*/

            currentLine = getCurrentLineNumber(currentStationValue);
            destinationLine = getDestinationLineNumber(targetStationValue);

            numberOfTransaction = currentLine - destinationLine;

            if (numberOfTransaction < 0) {
                numberOfTransaction = Math.abs(numberOfTransaction);
            }

            for (int i = 0; i <= numberOfTransaction; i++) {
                if (currentLine == destinationLine) {
                    int totalStationsFromSameLine = targetStationValue - currentStationValue;
                    if (totalStationsFromSameLine < 0) {
                        totalStationsFromSameLine = Math.abs(totalStationsFromSameLine);
                    }
                    totalStations += totalStationsFromSameLine + 1;
                } else if (currentLine == 2 && destinationLine == 1) {
                    int stationsToShohda = Constants.lineTwoStationsWeights[7] - currentStationValue; //6
                    if (stationsToShohda < 0) {
                        stationsToShohda = Math.abs(stationsToShohda);
                    }
                    int stationsToSadat = Constants.lineTwoStationsWeights[10] - currentStationValue;
                    if (stationsToSadat < 0) {
                        stationsToSadat = Math.abs(stationsToSadat);
                    }
                    if (stationsToShohda < stationsToSadat) {
                        currentStationValue = Constants.lineOneStationsWeights[21];
                        totalStations += stationsToShohda + 1;
                    } else {
                        currentStationValue = Constants.lineOneStationsWeights[18];
                        totalStations += stationsToSadat + 1;
                    }
                    currentLine--;
                } else if (currentLine == 3 && destinationLine == 2) {
                    int stationsToAtaba = Constants.lineThreeStationsWeights[8] - currentStationValue; //6
                    if (stationsToAtaba < 0) {
                        stationsToAtaba = Math.abs(stationsToAtaba);
                    }
                    totalStations = stationsToAtaba + 1;
                    currentStationValue = Constants.lineTwoStationsWeights[8];
                    currentLine--;
                } else if (currentLine == 1 && destinationLine == 2) {
                    int stationsToShohda = Constants.lineOneStationsWeights[21] - currentStationValue; //6
                    if (stationsToShohda < 0) {
                        stationsToShohda = Math.abs(stationsToShohda);
                    }
                    int stationsToSadat = Constants.lineOneStationsWeights[18] - currentStationValue;
                    if (stationsToSadat < 0) {
                        stationsToSadat = Math.abs(stationsToSadat);
                    }
                    if (stationsToShohda < stationsToSadat) {
                        currentStationValue = Constants.lineTwoStationsWeights[7];
                        totalStations = stationsToShohda + 1;
                    } else {
                        currentStationValue = Constants.lineTwoStationsWeights[10];
                        totalStations = stationsToSadat + 1;
                    }
                    currentLine++;
                } else if (currentLine == 2 && destinationLine == 3) {
                    int stationsToAtaba = Constants.lineTwoStationsWeights[8] - currentStationValue; //6
                    if (stationsToAtaba < 0) {
                        stationsToAtaba = Math.abs(stationsToAtaba);
                    }
                    totalStations += stationsToAtaba;
                    currentStationValue = Constants.lineThreeStationsWeights[8];
                    currentLine++;
                } else if (currentLine == 3 && destinationLine == 1) {
                    int stationsToAtaba = Constants.lineThreeStationsWeights[8] - currentStationValue; //6
                    if (stationsToAtaba < 0) {
                        stationsToAtaba = Math.abs(stationsToAtaba);
                    }
                    totalStations = stationsToAtaba + 1;
                    currentStationValue = Constants.lineTwoStationsWeights[8];
                    currentLine--;
                } else if (currentLine == 1 && destinationLine == 3) {
                    int stationsToShohda = Constants.lineOneStationsWeights[21] - currentStationValue; //6
                    if (stationsToShohda < 0) {
                        stationsToShohda = Math.abs(stationsToShohda);
                    }
                    int stationsToSadat = Constants.lineOneStationsWeights[18] - currentStationValue;
                    if (stationsToSadat < 0) {
                        stationsToSadat = Math.abs(stationsToSadat);
                    }
                    if (stationsToShohda < stationsToSadat) {
                        currentStationValue = Constants.lineTwoStationsWeights[7];
                        totalStations = stationsToShohda + 1;
                    } else {
                        currentStationValue = Constants.lineTwoStationsWeights[10];
                        totalStations = stationsToSadat + 1;
                    }
                    currentLine++;
                }
            }
            instructionLayout.setVisibility(View.GONE);
            infoLayout.setVisibility(View.VISIBLE);
            chooseTheBestTicket(totalStations);
            currentStationValue = 0;
            targetStationValue = 0;

        }


        currentStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = 1;
                startActivity(new Intent(MainActivity.this, StationActivity.class));
            }
        });

        targetStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = 2;
                startActivity(new Intent(MainActivity.this, StationActivity.class));
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void chooseTheBestTicket(int totalStation) {

        if (totalStation <= 9) {

            ticketImageView.setImageResource(R.drawable.yellow_ticket);
            infoText.setText("Your Ticket :Yellow ticket");
            totalStationText.setText("" + totalStation);
            totalCostText.setText("3.00 LE");
            totalTimeText.setText("" + totalStation * 2 + "min");

        } else if (totalStation > 9 && totalStation < 17) {

            ticketImageView.setImageResource(R.drawable.green_ticket);
            infoText.setText("Your Ticket :Green ticket");
            totalStationText.setText("" + totalStation);
            totalCostText.setText("5.00 LE");
            totalTimeText.setText("" + totalStation * 2 + "min");

        } else {
            ticketImageView.setImageResource(R.drawable.red_ticket);
            infoText.setText("Your Ticket :Red ticket");
            totalStationText.setText("" + totalStation);
            totalCostText.setText("7.00 LE");
            totalTimeText.setText("" + totalStation * 2 + "min");
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v("TAG", "On Restart was called");
        Log.v("TAG", "Remove current station value , name and destination value , name");
        currentStation.setText("");
        targetStation.setText("");
        removeData();
        ticketImageView.setImageResource(R.drawable.background);
        instructionLayout.setVisibility(View.VISIBLE);
        infoLayout.setVisibility(View.GONE);


    }

    public int getCurrentLineNumber(int stationNumber) {
        int currentLine;
        if (stationNumber >= 1 && stationNumber <= 35) {
            currentLine = 1;
        } else if (stationNumber >= 36 && stationNumber <= 55) {
            currentLine = 2;
        } else {
            currentLine = 3;
        }
        return currentLine;
    }

    public int getDestinationLineNumber(int stationNumber) {
        int destinationLine;
        if (stationNumber >= 1 && stationNumber <= 35) {
            destinationLine = 1;
        } else if (stationNumber >= 36 && stationNumber <= 55) {
            destinationLine = 2;
        } else {
            destinationLine = 3;
        }
        return destinationLine;
    }

    private void removeData() {
        stationManger.removeCurrentStationNumber();
        stationManger.removeCurrentStationName();
        stationManger.removeDestinationStationNumber();
        stationManger.removeDestinationStationName();
    }


}
