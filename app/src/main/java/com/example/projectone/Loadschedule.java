package com.example.projectone;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectone.Adapter.DataAdapter;
import com.example.projectone.Databases.ProjectTable;
import com.example.projectone.Helper.DatabaseHelper;

import java.text.DecimalFormat;
import java.util.List;

public class Loadschedule extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DataAdapter dataAdapter;
    private RelativeLayout rootLayout;
    private float mScaleFactor = 1.0f;
    private ScaleGestureDetector mScaleGestureDetector;
    private GestureDetector mGestureDetector;
    private float mLastTouchX;
    private float mLastTouchY;
    DatabaseHelper helper;
    DecimalFormat decimalFormat = new DecimalFormat("#0.00");
    TextView FEEDERWIREPASS, MAINWIREPASS, LAWEHIGHB, SAVEHIGHB,LAWEHIGHA, SAVEHIGHA,LAWEA, SaveA,UpdatedMainWire,FeederSize,FeederWireType,FeederWireSecond,FeederWireThird,FeederWireFourth,FeederWire,MainWire,totalone,totalVATextView,totalATextView,HighestA,HighestB,TotalB,UnderOneAndTwo,UnderThreeAndFour,TotalUnder,TopOneAndTwo,TopThreeAndFour,TotalTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadschedule);
        helper = DatabaseHelper.getInstance(this);
        recyclerView = findViewById(R.id.recylcer_view);
        helper.getAllProjectData();
        totalVATextView = findViewById(R.id.totalVA);
        totalATextView = findViewById(R.id.totalA);
        HighestA =findViewById(R.id.HIGHA);
        HighestB = findViewById(R.id.HighestB);
        totalone = findViewById(R.id.totalONE);
        TotalB = findViewById(R.id.TotalB);
        UnderOneAndTwo = findViewById(R.id.underoneandtwo);
        UnderThreeAndFour = findViewById(R.id.underthreeandfour);
        TotalUnder = findViewById(R.id.totalunder);
        TopOneAndTwo = findViewById(R.id.TopOneAndTwo);
        TopThreeAndFour = findViewById(R.id.TopThreeAndFour);
        TotalTop = findViewById(R.id.TotalTop);
        MainWire = findViewById(R.id.MainWire);
        FeederWire = findViewById(R.id.FeederWire);
        FeederWireSecond = findViewById(R.id.FeederWireSecond);
        FeederWireThird = findViewById(R.id.FeederWireThird);
        FeederWireFourth = findViewById(R.id.FeederWireTFourth);
        FeederWireType = findViewById(R.id.FeederWireType);
        FeederSize = findViewById(R.id.FeederSize);
        UpdatedMainWire = findViewById(R.id.UpdatedMainWire);
        SaveA = findViewById(R.id.saveA);
        MAINWIREPASS = findViewById(R.id.MainWirePass);
        FEEDERWIREPASS = findViewById(R.id.FeederWireTypePass);


        SAVEHIGHA = findViewById(R.id.SAVEHIGHA);
        LAWEHIGHA = findViewById(R.id.LAWEHIGHA);
        SAVEHIGHB = findViewById(R.id.SAVEHIGHB);
        LAWEHIGHB = findViewById(R.id.LAWEHIGHB);
        SharedPreferences sharedPreferences = getSharedPreferences("SharePref",MODE_PRIVATE);
        String UPDMT = sharedPreferences.getString("UMT","");
        String FDW = sharedPreferences.getString("UFWT","");
        Intent intent = getIntent();
        if (intent != null) {
            String PassMain = intent.getStringExtra("Value");
            String totalVA = intent.getStringExtra("TOTALVA");
            String totalA = intent.getStringExtra("TOTALA");
            String HIGHA = intent.getStringExtra("HIGHA");
            String Wiretype =intent.getStringExtra("WFG");
            String PVCUPDATED = intent.getStringExtra("NUMPVC");
            String WFGTYPE = intent.getStringExtra("WFG");


            if (totalA == null && HIGHA == null)
            {
                MainWire.setVisibility(View.GONE);
                MAINWIREPASS.setText(UPDMT);
                MAINWIREPASS.setVisibility(View.VISIBLE);

                if (UPDMT != null)
                {
                    FeederWireType.setText(WFGTYPE);
                }

                String SAVEA,SAVEHIGHAA;
                SAVEA = sharedPreferences.getString("TOTALA","");
                SAVEHIGHAA = sharedPreferences.getString("HIGHAA","");
                totalATextView.setText(SAVEA);
                HighestA.setText(SAVEHIGHAA);
                HighestB.setText(HighestA.getText().toString());
                totalone.setText(totalATextView.getText().toString());
                TotalB.setText(totalone.getText().toString());

            }


            if (totalVA != null) {
                totalVATextView.setText(totalVA);
            }



            if (totalA != null) {
                totalATextView.setText(totalA);
                totalone.setText(totalA);
                TotalB.setText(totalA);
            }



            if (HIGHA != null)
            {
                HighestA.setText(HIGHA);
                HighestB.setText(HIGHA);
            }

            if (Wiretype != null)
            {
                FeederWireType.setText(Wiretype);
            }
            if (PVCUPDATED != null)
            {
                FeederWireFourth.setText(PVCUPDATED);
            }

        }


        String SAVEA = totalATextView.getText().toString();
        String SaveHighA = HighestA.getText().toString();
        SaveA.setText(SAVEA);
        SAVEHIGHA.setText(SaveHighA);

        double totalOneValue = Double.parseDouble(totalone.getText().toString());
        double topOneAndTwoValue = totalOneValue * 0.80;
        TopOneAndTwo.setText(String.valueOf(topOneAndTwoValue));
        String formattedResult = decimalFormat.format(topOneAndTwoValue);
        TopOneAndTwo.setText(formattedResult);

        double highestAValue = Double.parseDouble(HighestA.getText().toString());
        double topThreeAndFourValue = highestAValue * 0.25;
        topThreeAndFourValue = Math.round(topThreeAndFourValue * 100.0) / 100.0;
        String formattedResulttwo = decimalFormat.format(topThreeAndFourValue);
        TopThreeAndFour.setText(formattedResulttwo);

        double totalTopValue = topOneAndTwoValue + topThreeAndFourValue;
        totalTopValue = Math.round(totalTopValue * 100.0) / 100.0;
        String formattedResultFinalTop = decimalFormat.format(totalTopValue);
        TotalTop.setText(formattedResultFinalTop);

        double totalBValue = Double.parseDouble(TotalB.getText().toString());
        double underOneAndTwoValue = totalBValue * 0.80;
        underOneAndTwoValue = Math.round(underOneAndTwoValue * 100.0) / 100.0;
        String formattedResultUnderOneAndTwo = decimalFormat.format(underOneAndTwoValue);
        UnderOneAndTwo.setText(formattedResultUnderOneAndTwo);

        double highestBValue = Double.parseDouble(HighestB.getText().toString());
        double underThreeAndFourValue = highestBValue * 1.5;
        underThreeAndFourValue = Math.round(underThreeAndFourValue * 100.0) / 100.0;
        String formattedResultUnderThreeAndFour = decimalFormat.format(underThreeAndFourValue);
        UnderThreeAndFour.setText(formattedResultUnderThreeAndFour);

        double totalUnderValue = underOneAndTwoValue + underThreeAndFourValue;
        totalUnderValue = Math.round(totalUnderValue * 100.0) / 100.0;
        String formattedResultTotalUnder = decimalFormat.format(totalUnderValue);
        TotalUnder.setText(formattedResultTotalUnder);



            if (totalUnderValue == 0) {
            MainWire.setText("20 AT, 50 AF, 2P, 230V, 60 GHZ");
              }
            if (totalUnderValue < 15) {
                MainWire.setText("20 AT, 50 AF, 2P, 230V, 60 GHZ");
            }
            if (totalUnderValue >= 16 && totalUnderValue <= 20) {
                MainWire.setText("20 AT, 50 AF, 2P, 230V, 60 GHZ");
            }
            if (totalUnderValue >= 21 && totalUnderValue <= 30) {
                MainWire.setText("30 AT, 50 AF, 2P, 230V, 60 GHZ");
            }
            if (totalUnderValue >= 31 && totalUnderValue <= 40) {
                MainWire.setText("40 AT, 50 AF, 2P, 230V, 60 GHZ");
            }
            if (totalUnderValue >= 41 && totalUnderValue <= 50) {
                MainWire.setText("50 AT, 50 AF, 2P, 230V, 60 GHZ");
            }
            if (totalUnderValue >= 50 && totalUnderValue <= 60) {
                MainWire.setText("60 AT, 100 AF, 2P, 230V, 60 GHZ");
            }

            if (totalUnderValue >= 71 && totalUnderValue <= 80) {
                MainWire.setText("80 AT, 100 AF, 2P, 230V, 60 GHZ");
            }
            if (totalUnderValue >= 81 && totalUnderValue <= 90) {
                MainWire.setText("90 AT, 100 AF, 2P, 230V, 60 GHZ");
            }
            if (totalUnderValue >= 91 && totalUnderValue <= 100) {
                MainWire.setText("100 AT, 100 AF, 2P, 230V, 60 GHZ");
            }
            if (totalUnderValue >= 101 && totalUnderValue <= 110) {
                MainWire.setText("110 AT, 225 AF, 2P, 230V, 60 GHZ");
            }
            if (totalUnderValue >= 111 && totalUnderValue <= 125) {
                MainWire.setText("125 AT, 225 AF, 2P, 230V, 60 GHZ");
            }
            if (totalUnderValue >= 125 && totalUnderValue <= 150) {
                MainWire.setText("150 AT, 225 AF, 2P, 230V, 60 GHZ");
            }
            if (totalUnderValue > 151) {
                MainWire.setText("175 AT, 225 AF, 2P, 230V, 60 GHZ");
            }
            String PassMainWire = MainWire.getText().toString();
        if (FDW != null)
        {
            MainWire.setText(PassMainWire);
        }

        if (totalUnderValue >= 1 && totalUnderValue < 25 )
        {
            FeederWire.setText("2 - 2.0mm.sq. THHN Cu. Wire");
        }
        if (totalUnderValue >= 25 && totalUnderValue < 30)
        {
            FeederWire.setText("2 - 3.5mm.sq. THHN Cu. Wire");
        }
        if (totalUnderValue >= 30 && totalUnderValue < 40)
        {
            FeederWire.setText("2 - 5.5mm.sq. THHN Cu. Wire");
        }
        if (totalUnderValue >= 40 && totalUnderValue < 55)
        {
            FeederWire.setText("2 - 8.0mm.sq. THHN Cu. Wire");
        }
        if (totalUnderValue >= 55 && totalUnderValue < 75)
        {
            FeederWire.setText("2 - 14.0mm.sq. THHN Cu. Wire");
        }
        if (totalUnderValue >= 75 && totalUnderValue < 95)
        {
            FeederWire.setText("2 - 22.0mm.sq. THHN Cu. Wire");
        }
        if (totalUnderValue >= 95 && totalUnderValue < 115)
        {
            FeederWire.setText("2 - 30.0mm.sq. THHN Cu. Wire");
        }
        if (totalUnderValue >= 115 && totalUnderValue < 130)
        {
            FeederWire.setText("2 - 38.0mm.sq. THHN Cu. Wire");
        }
        if (totalUnderValue >= 130 && totalUnderValue < 150)
        {
            FeederWire.setText("2 - 50.0mm.sq. THHN Cu. Wire");
        }
        if (totalUnderValue >= 150 && totalUnderValue < 170)
        {
            FeederWire.setText("2 - 60.0mm.sq. THHN Cu. Wire");
        }
        if (totalUnderValue >= 170 && totalUnderValue < 205)
        {
            FeederWire.setText("2 - 80.0mm.sq. THHN Cu. Wire");
        }
        if (totalUnderValue >= 205 && totalUnderValue < 240)
        {
            FeederWire.setText("2 - 100.0mm.sq. THHN Cu. Wire");
        }if (totalUnderValue >= 240 && totalUnderValue < 285)
        {
            FeederWire.setText("2 - 125.0mm.sq. THHN Cu. Wire");
        }if (totalUnderValue >= 285 && totalUnderValue < 320)
        {
            FeederWire.setText("2 - 150.0mm.sq. THHN Cu. Wire");
        }if (totalUnderValue >= 320 && totalUnderValue < 345)
        {
            FeederWire.setText("2 - 175.0mm.sq. THHN Cu. Wire");
        }if (totalUnderValue >= 345 && totalUnderValue < 360)
        {
            FeederWire.setText("2 - 200.0mm.sq. THHN Cu. Wire");
        }if (totalUnderValue >= 360 && totalUnderValue < 425)
        {
            FeederWire.setText("2 - 250.0mm.sq. THHN Cu. Wire");
        }
        if (totalUnderValue >= 425 && totalUnderValue < 490)
        {
            FeederWire.setText("2 - 325.0mm.sq. THHN Cu. Wire");
        }
        if (totalUnderValue >= 490 && totalUnderValue < 530)
        {
            FeederWire.setText("2 - 375.0mm.sq. THHN Cu. Wire");
        }
        if (totalUnderValue >= 530 && totalUnderValue < 535)
        {
            FeederWire.setText("2 - 400.0mm.sq. THHN Cu. Wire");
        }
        if (totalUnderValue >= 535 && totalUnderValue < 595)
        {
            FeederWire.setText("2 - 500.0mm.sq. THHN Cu. Wire");
        }


        if (totalUnderValue < 30)
        {
            FeederWireSecond.setText("+ 1 - 8.0 mm.sq.");
        }
        if (totalUnderValue < 30)
        {
            FeederWireSecond.setText("+ 1 - 8.0 mm.sq.");
        }
        else if (totalUnderValue >= 30 && totalUnderValue <= 50) {
            FeederWireSecond.setText("+ 1 - 14 mm.sq.");
        }
        else if (totalUnderValue >= 51 && totalUnderValue <= 80) {
            FeederWireSecond.setText("+ 1 - 22 mm.sq.");
        }
        else if (totalUnderValue >= 81 && totalUnderValue <= 175) {
            FeederWireSecond.setText("+ 1 - 30 mm.sq.");
        }
        else if (totalUnderValue >= 176 && totalUnderValue <= 325) {
            FeederWireSecond.setText("+ 1 - 50 mm.sq.");
        }
        else if (totalUnderValue >= 326 && totalUnderValue <= 500) {
            FeederWireSecond.setText("+ 1 - 60 mm.sq.");
        }



        String FeederS = FeederWireSecond.getText().toString().trim();
        if (FeederS.equals("2 - 2.0mm.sq. THHN Cu. Wire")) {
            FeederWireFourth.setText("(G)In 15 mmø IMC PIPE");
        }
        if (FeederS.equals("2 - 3.5mm.sq. THHN Cu. Wire")) {
            FeederWireFourth.setText("(G)In 15 mmø IMC PIPE");
        }
        if (FeederS.equals("2 - 5.5mm.sq. THHN Cu. Wire")) {
            FeederWireFourth.setText("(G)In 15 mmø IMC PIPE");
        }
        if (FeederS.equals("2 - 8.0mm.sq. THHN Cu. Wire")) {
            FeederWireFourth.setText("(G)In 20 mmø IMC PIPE");
        }
        if (FeederS.equals("2 - 14.0mm.sq. THHN Cu. Wire")) {
            FeederWireFourth.setText("(G)In 20 mmø IMC PIPE");
        }
        if (FeederS.equals("2 - 22.0mm.sq. THHN Cu. Wire")) {
            FeederWireFourth.setText("(G)In 25 mmø IMC PIPE");
        }
        if (FeederS.equals("2 - 30.0mm.sq. THHN Cu. Wire")) {
            FeederWireFourth.setText("(G)In 32 mmø IMC PIPE");
        }
        if (FeederS.equals("2 - 38.0mm.sq. THHN Cu. Wire")) {
            FeederWireFourth.setText("(G)In 32 mmø IMC PIPE");
        }
        if (FeederS.equals("2 - 50.0mm.sq. THHN Cu. Wire")) {
            FeederWireFourth.setText("(G)In 40 mmø IMC PIPE");
        }
        if (FeederS.equals("2 - 60.0mm.sq. THHN Cu. Wire")) {
            FeederWireFourth.setText("(G)In 40 mmø IMC PIPE");
        }
        if (FeederS.equals("2 - 80.0mm.sq. THHN Cu. Wire")) {
            FeederWireFourth.setText("(G)In 50 mmø IMC PIPE");
        }
        if (FeederS.equals("2 - 100.0mm.sq. THHN Cu. Wire")) {
            FeederWireFourth.setText("(G)In 50 mmø IMC PIPE");
        }
        if (FeederS.equals("2 - 125.0mm.sq. THHN Cu. Wire")) {
            FeederWireFourth.setText("(G)In 50 mmø IMC PIPE");
        }
        if (FeederS.equals("2 - 150.0mm.sq. THHN Cu. Wire")) {
            FeederWireFourth.setText("(G)In 65 mmø IMC PIPE");
        }
        if (FeederS.equals("2 - 175.0mm.sq. THHN Cu. Wire")) {
            FeederWireFourth.setText("(G)In 65 mmø IMC PIPE");
        }
        if (FeederS.equals("2 - 200.0mm.sq. THHN Cu. Wire")) {
            FeederWireFourth.setText("(G)In 65 mmø IMC PIPE");
        }
        if (FeederS.equals("2 - 250.0mm.sq. THHN Cu. Wire")) {
            FeederWireFourth.setText("(G)In 80  mmø IMC PIPE");
        }
        FeederWireFourth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChangePVC.class);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("TOTALA", SAVEA);
                editor.putString("HIGHAA",SaveHighA);
                editor.apply();
                startActivity(intent);
            }
        });

        FeederWireType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChangeFeederWire.class);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("TOTALA", SAVEA);
                editor.putString("HIGHAA",SaveHighA);
                editor.apply();
                startActivity(intent);
            }
        });
        MainWire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChangeMain.class);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("TOTALA", SAVEA);
                editor.putString("HIGHAA",SaveHighA);
                editor.apply();
                startActivity(intent);
            }
        });



        rootLayout = findViewById(R.id.zoom);
        mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
        mGestureDetector = new GestureDetector(this, new GestureListener());
    }
    public  void  setRecyclerView(List<ProjectTable> projectTableList)
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataAdapter = new DataAdapter(this, projectTableList);
        recyclerView.setAdapter(dataAdapter);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Pass touch events to ScaleGestureDetector and GestureDetector
        mScaleGestureDetector.onTouchEvent(event);
        mGestureDetector.onTouchEvent(event);
        return true;
    }






    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            // Adjust the scale factor
            mScaleFactor *= detector.getScaleFactor();

            // Limit the scale factor to a reasonable range
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));

            // Apply the scale factor to the root layout only
            rootLayout.setScaleX(mScaleFactor);
            rootLayout.setScaleY(mScaleFactor);

            return true;
        }
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            // Reset the scale factor to normal on double-tap
            mScaleFactor = 1.0f;

            // Reset the scale factor to the root layout
            rootLayout.setScaleX(mScaleFactor);
            rootLayout.setScaleY(mScaleFactor);

            // Reset the scale factor for the RecyclerView
            recyclerView.setScaleX(mScaleFactor);
            recyclerView.setScaleY(mScaleFactor);

            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            // Capture the initial touch coordinates
            mLastTouchX = e.getRawX();
            mLastTouchY = e.getRawY();
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            // Reverse the panning direction (mirror mode)
            rootLayout.setX(rootLayout.getX() + (e2.getRawX() - mLastTouchX));
            rootLayout.setY(rootLayout.getY() + (e2.getRawY() - mLastTouchY));

            // Update the last touch coordinates
            mLastTouchX = e2.getRawX();
            mLastTouchY = e2.getRawY();

            return true;
        }
    }
}
