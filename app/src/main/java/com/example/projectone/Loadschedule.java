package com.example.projectone;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.projectone.Adapter.DataAdapter;
import com.example.projectone.Databases.ProjectDAO;
import com.example.projectone.Databases.ProjectDatabase;
import com.example.projectone.Databases.ProjectTable;
import com.example.projectone.Helper.DatabaseHelper;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import android.content.DialogInterface;
import android.os.Environment;
import android.widget.Toast;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.graphics.Bitmap;
import android.view.View;

public class Loadschedule extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DataAdapter dataAdapter;
    private RelativeLayout rootLayout;
    private float mScaleFactor = 1.0f;
    private ScaleGestureDetector mScaleGestureDetector;
    private GestureDetector mGestureDetector;
    private float mLastTouchX;
    private float mLastTouchY;
    private ProjectDAO projectDAO;
    DatabaseHelper helper;
    List<ProjectTable> projectTableList;

    DecimalFormat decimalFormat = new DecimalFormat("#0.00");
    TextView num8_4, num6_2, num6_3, num6_4, num6_5, num6_6, num8_1, num8_2, num8_3, num8_5, num8_6, num8_7, num8_8, num10_1, num10_2, num10_3, num10_4, num10_5, num10_6, num10_7, num10_8, num10_9, num10_10, num6_1, num12_1, num12_2, num12_3, num12_4, num12_5, num12_6, num12_7, num12_8, num12_9, num12_10, num12_11, num12_12, num14_1, num14_2, num14_3, num14_4, num14_5, num14_6, num14_7, num14_8, num14_9, num14_10, num14_11, num14_12, num14_13, num14_14,   num16_1, num16_2, num16_3, num16_4, num16_5, num16_6, num16_7, num16_8, num16_9, num16_10, num16_11, num16_12, num16_13, num16_14, num16_15, num16_16, num18_1, num18_2, num18_3, num18_4, num18_5, num18_6, num18_7, num18_8, num18_9, num18_10, num18_11, num18_12, num18_13, num18_14, num18_15, num18_16, num18_17, num18_18, num20_1, num20_2, num20_3, num20_4, num20_5, num20_6, num20_7, num20_8, num20_9, num20_10, num20_11, num20_12, num20_13, num20_14, num20_15, num20_16, num20_17, num20_18, num20_19, num20_20, num22_1, num22_2, num22_3, num22_4, num22_5, num22_6, num22_7, num22_8, num22_9, num22_10, num22_11, num22_12, num22_13, num22_14, num22_15, num22_16, num22_17, num22_18, num22_19, num22_20, num22_21, num22_22,num24_1, num24_2, num24_3, num24_4, num24_5, num24_6, num24_7, num24_8, num24_9, num24_10, num24_11, num24_12, num24_13, num24_14, num24_15, num24_16, num24_17, num24_18, num24_19, num24_20, num24_21, num24_22, num24_23, num24_24, num26_1, num26_2, num26_3, num26_4, num26_5, num26_6, num26_7, num26_8, num26_9, num26_10, num26_11, num26_12, num26_13, num26_14, num26_15, num26_16, num26_17, num26_18, num26_19, num26_20, num26_21, num26_22, num26_23, num26_24, num26_25, num26_26, num28_1, num28_2, num28_3, num28_4, num28_5, num28_6, num28_7, num28_8, num28_9, num28_10, num28_11, num28_12, num28_13, num28_14, num28_15, num28_16, num28_17, num28_18, num28_19, num28_20, num28_21, num28_22, num28_23, num28_24, num28_25, num28_26, num28_27, num28_28,num30_1, num30_2, num30_3, num30_4, num30_5, num30_6, num30_7, num30_8, num30_9, num30_10, num30_11, num30_12, num30_13, num30_14, num30_15, num30_16, num30_17, num30_18, num30_19, num30_20, num30_21, num30_22, num30_23, num30_24, num30_25, num30_26, num30_27, num30_28, num30_29, num30_30,num4_a, num6_a, num8_a, num10_a, num12_a, num14_a, num16_a, num18_a, num20_a, num22_a, num24_a, num26_a, num28_a, num30_a, num4_top, num6_top, num8_top, num10_top, num12_top, num14_top, num16_top, num18_top, num20_top, num22_top, num24_top, num26_top, num28_top, num30_top, num6_bot, num8_bot, num10_bot, num12_bot, num14_bot, num16_bot, num18_bot, num20_bot, num22_bot, num24_bot, num26_bot, num28_bot, num30_bot, num4_bot, num4_1, num4_2, num4_3, num4_4, num4_a1, num4_a2, num4_a3, num4_a4, num6_a1, num6_a2, num6_a3, num6_a4, num6_a5, num6_a6, num8_a1, num8_a2, num8_a3, num8_a4, num8_a5, num8_a6, num8_a7, num8_a8, num10_a1, num10_a2, num10_a3, num10_a4, num10_a5, num10_a6, num10_a7, num10_a8, num10_a9, num10_a10, num12_a1, num12_a2, num12_a3, num12_a4, num12_a5, num12_a6, num12_a7, num12_a8, num12_a9, num12_a10, num12_a11, num12_a12, num14_a1, num14_a2, num14_a3, num14_a4, num14_a5, num14_a6, num14_a7, num14_a8, num14_a9, num14_a10, num14_a11, num14_a12, num14_a13, num14_a14, num16_a1, num16_a2, num16_a3, num16_a4, num16_a5, num16_a6, num16_a7, num16_a8, num16_a9, num16_a10, num16_a11, num16_a12, num16_a13, num16_a14, num16_a15, num16_a16, num18_a1, num18_a2, num18_a3, num18_a4, num18_a5, num18_a6, num18_a7, num18_a8, num18_a9, num18_a10, num18_a11, num18_a12, num18_a13, num18_a14, num18_a15, num18_a16, num18_a17, num18_a18, num20_a1, num20_a2, num20_a3, num20_a4, num20_a5, num20_a6, num20_a7, num20_a8, num20_a9, num20_a10, num20_a11, num20_a12, num20_a13, num20_a14, num20_a15, num20_a16, num20_a17, num20_a18, num20_a19, num20_a20, num22_a1, num22_a2, num22_a3, num22_a4, num22_a5, num22_a6, num22_a7, num22_a8, num22_a9, num22_a10, num22_a11, num22_a12, num22_a13, num22_a14, num22_a15, num22_a16, num22_a17, num22_a18, num22_a19, num22_a20, num22_a21, num22_a22, num24_a1, num24_a2, num24_a3, num24_a4, num24_a5, num24_a6, num24_a7, num24_a8, num24_a9, num24_a10, num24_a11, num24_a12, num24_a13, num24_a14, num24_a15, num24_a16, num24_a17, num24_a18, num24_a19, num24_a20, num24_a21, num24_a22, num24_a23, num24_a24, num26_a1, num26_a2, num26_a3, num26_a4, num26_a5, num26_a6, num26_a7, num26_a8, num26_a9, num26_a10, num26_a11, num26_a12, num26_a13, num26_a14, num26_a15, num26_a16, num26_a17, num26_a18, num26_a19, num26_a20, num26_a21, num26_a22, num26_a23, num26_a24, num26_a25, num26_a26, num28_a1, num28_a2, num28_a3, num28_a4, num28_a5, num28_a6, num28_a7, num28_a8, num28_a9, num28_a10, num28_a11, num28_a12, num28_a13, num28_a14, num28_a15, num28_a16, num28_a17, num28_a18, num28_a19, num28_a20, num28_a21, num28_a22, num28_a23, num28_a24, num28_a25, num28_a26, num28_a27, num28_a28, num30_a1, num30_a2, num30_a3, num30_a4, num30_a5, num30_a6, num30_a7, num30_a8, num30_a9, num30_a10, num30_a11, num30_a12, num30_a13, num30_a14, num30_a15, num30_a16, num30_a17, num30_a18, num30_a19, num30_a20, num30_a21, num30_a22, num30_a23, num30_a24, num30_a25, num30_a26, num30_a27, num30_a28, num30_a29, num30_a30, CTRtv, FEEDERWIREPASS, MAINWIREPASS, LAWEHIGHB, SAVEHIGHB,LAWEHIGHA, SAVEHIGHA,LAWEA, SaveA, UpdatedMainWire,FeederSize,FeederWireType,FeederWireSecond,FeederWireThird,FeederWireFourth,FeederWire,MainWire,totalone,totalVATextView,totalATextView,HighestA,HighestB,TotalB,UnderOneAndTwo,UnderThreeAndFour,TotalUnder,TopOneAndTwo,TopThreeAndFour,TotalTop;

    private DatabaseHelper databaseHelper;
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
        FeederWireFourth = findViewById(R.id.FeederWireFourth);
        FeederWireType = findViewById(R.id.FeederWireType);
        FeederSize = findViewById(R.id.FeederSize);
        UpdatedMainWire = findViewById(R.id.UpdatedMainWire);
        SaveA = findViewById(R.id.saveA);
        MAINWIREPASS = findViewById(R.id.MainWirePass);
        FEEDERWIREPASS = findViewById(R.id.FeederWireTypePass);
        CTRtv = findViewById(R.id.CTRtv);


        // Call the method to get all items list
     ///   databaseHelper.getAllItemsList();

         num4_1 = findViewById(R.id.num4_1);
         num4_2 = findViewById(R.id.num4_2);
         num4_3 = findViewById(R.id.num4_3);
         num4_4 = findViewById(R.id.num4_4);

        num6_1 = findViewById(R.id.num6_1);
        num6_2 = findViewById(R.id.num6_2);
        num6_3 = findViewById(R.id.num6_3);
        num6_4 = findViewById(R.id.num6_4);
        num6_5 = findViewById(R.id.num6_5);
        num6_6 = findViewById(R.id.num6_6);

        num8_1 = findViewById(R.id.num8_1);
        num8_2 = findViewById(R.id.num8_2);
        num8_3 = findViewById(R.id.num8_3);
        num8_4 = findViewById(R.id.num8_4);
        num8_5 = findViewById(R.id.num8_5);
        num8_6 = findViewById(R.id.num8_6);
        num8_7 = findViewById(R.id.num8_7);
        num8_8 = findViewById(R.id.num8_8);



        num10_1 = findViewById(R.id.num10_1);
        num10_2 = findViewById(R.id.num10_2);
        num10_3 = findViewById(R.id.num10_3);
        num10_4 = findViewById(R.id.num10_4);
        num10_5 = findViewById(R.id.num10_5);
        num10_6 = findViewById(R.id.num10_6);
        num10_7 = findViewById(R.id.num10_7);
        num10_8 = findViewById(R.id.num10_8);
        num10_9 = findViewById(R.id.num10_9);
        num10_10 = findViewById(R.id.num10_10);

        num12_1 = findViewById(R.id.num12_1);
        num12_2 = findViewById(R.id.num12_2);
        num12_3 = findViewById(R.id.num12_3);
        num12_4 = findViewById(R.id.num12_4);
        num12_5 = findViewById(R.id.num12_5);
        num12_6 = findViewById(R.id.num12_6);
        num12_7 = findViewById(R.id.num12_7);
        num12_8 = findViewById(R.id.num12_8);
        num12_9 = findViewById(R.id.num12_9);
        num12_10 = findViewById(R.id.num12_10);
        num12_11 = findViewById(R.id.num12_11);
        num12_12 = findViewById(R.id.num12_12);

        num14_1 = findViewById(R.id.num14_1);
        num14_2 = findViewById(R.id.num14_2);
        num14_3 = findViewById(R.id.num14_3);
        num14_4 = findViewById(R.id.num14_4);
        num14_5 = findViewById(R.id.num14_5);
        num14_6 = findViewById(R.id.num14_6);
        num14_7 = findViewById(R.id.num14_7);
        num14_8 = findViewById(R.id.num14_8);
        num14_9 = findViewById(R.id.num14_9);
        num14_10 = findViewById(R.id.num14_10);
        num14_11 = findViewById(R.id.num14_11);
        num14_12 = findViewById(R.id.num14_12);
        num14_13 = findViewById(R.id.num14_13);
        num14_14 = findViewById(R.id.num14_14);

        num16_1 = findViewById(R.id.num16_1);
        num16_2 = findViewById(R.id.num16_2);
        num16_3 = findViewById(R.id.num16_3);
        num16_4 = findViewById(R.id.num16_4);
        num16_5 = findViewById(R.id.num16_5);
        num16_6 = findViewById(R.id.num16_6);
        num16_7 = findViewById(R.id.num16_7);
        num16_8 = findViewById(R.id.num16_8);
        num16_9 = findViewById(R.id.num16_9);
        num16_10 = findViewById(R.id.num16_10);
        num16_11 = findViewById(R.id.num16_11);
        num16_12 = findViewById(R.id.num16_12);
        num16_13 = findViewById(R.id.num16_13);
        num16_14 = findViewById(R.id.num16_14);
        num16_15 = findViewById(R.id.num16_15);
        num16_16 = findViewById(R.id.num16_16);


        num18_1 = findViewById(R.id.num18_1);
        num18_2 = findViewById(R.id.num18_2);
        num18_3 = findViewById(R.id.num18_3);
        num18_4 = findViewById(R.id.num18_4);
        num18_5 = findViewById(R.id.num18_5);
        num18_6 = findViewById(R.id.num18_6);
        num18_7 = findViewById(R.id.num18_7);
        num18_8 = findViewById(R.id.num18_8);
        num18_9 = findViewById(R.id.num18_9);
        num18_10 = findViewById(R.id.num18_10);
        num18_11 = findViewById(R.id.num18_11);
        num18_12 = findViewById(R.id.num18_12);
        num18_13 = findViewById(R.id.num18_13);
        num18_14 = findViewById(R.id.num18_14);
        num18_15 = findViewById(R.id.num18_15);
        num18_16 = findViewById(R.id.num18_16);
        num18_17 = findViewById(R.id.num18_17);
        num18_18 = findViewById(R.id.num18_18);


        num20_1 = findViewById(R.id.num20_1);
        num20_2 = findViewById(R.id.num20_2);
        num20_3 = findViewById(R.id.num20_3);
        num20_4 = findViewById(R.id.num20_4);
        num20_5 = findViewById(R.id.num20_5);
        num20_6 = findViewById(R.id.num20_6);
        num20_7 = findViewById(R.id.num20_7);
        num20_8 = findViewById(R.id.num20_8);
        num20_9 = findViewById(R.id.num20_9);
        num20_10 = findViewById(R.id.num20_10);
        num20_11 = findViewById(R.id.num20_11);
        num20_12 = findViewById(R.id.num20_12);
        num20_13 = findViewById(R.id.num20_13);
        num20_14 = findViewById(R.id.num20_14);
        num20_15 = findViewById(R.id.num20_15);
        num20_16 = findViewById(R.id.num20_16);
        num20_17 = findViewById(R.id.num20_17);
        num20_18 = findViewById(R.id.num20_18);
        num20_19 = findViewById(R.id.num20_19);
        num20_20 = findViewById(R.id.num20_20);


        num22_1 = findViewById(R.id.num22_1);
        num22_2 = findViewById(R.id.num22_2);
        num22_3 = findViewById(R.id.num22_3);
        num22_4 = findViewById(R.id.num22_4);
        num22_5 = findViewById(R.id.num22_5);
        num22_6 = findViewById(R.id.num22_6);
        num22_7 = findViewById(R.id.num22_7);
        num22_8 = findViewById(R.id.num22_8);
        num22_9 = findViewById(R.id.num22_9);
        num22_10 = findViewById(R.id.num22_10);
        num22_11 = findViewById(R.id.num22_11);
        num22_12 = findViewById(R.id.num22_12);
        num22_13 = findViewById(R.id.num22_13);
        num22_14 = findViewById(R.id.num22_14);
        num22_15 = findViewById(R.id.num22_15);
        num22_16 = findViewById(R.id.num22_16);
        num22_17 = findViewById(R.id.num22_17);
        num22_18 = findViewById(R.id.num22_18);
        num22_19 = findViewById(R.id.num22_19);
        num22_20 = findViewById(R.id.num22_20);
        num22_21 = findViewById(R.id.num22_21);
        num22_22 = findViewById(R.id.num22_22);



        num24_1 = findViewById(R.id.num24_1);
        num24_2 = findViewById(R.id.num24_2);
        num24_3 = findViewById(R.id.num24_3);
        num24_4 = findViewById(R.id.num24_4);
        num24_5 = findViewById(R.id.num24_5);
        num24_6 = findViewById(R.id.num24_6);
        num24_7 = findViewById(R.id.num24_7);
        num24_8 = findViewById(R.id.num24_8);
        num24_9 = findViewById(R.id.num24_9);
        num24_10 = findViewById(R.id.num24_10);
        num24_11 = findViewById(R.id.num24_11);
        num24_12 = findViewById(R.id.num24_12);
        num24_13 = findViewById(R.id.num24_13);
        num24_14 = findViewById(R.id.num24_14);
        num24_15 = findViewById(R.id.num24_15);
        num24_16 = findViewById(R.id.num24_16);
        num24_17 = findViewById(R.id.num24_17);
        num24_18 = findViewById(R.id.num24_18);
        num24_19 = findViewById(R.id.num24_19);
        num24_20 = findViewById(R.id.num24_20);
        num24_21 = findViewById(R.id.num24_21);
        num24_22 = findViewById(R.id.num24_22);
        num24_23 = findViewById(R.id.num24_23);
        num24_24 = findViewById(R.id.num24_24);




        num26_1 = findViewById(R.id.num26_1);
        num26_2 = findViewById(R.id.num26_2);
        num26_3 = findViewById(R.id.num26_3);
        num26_4 = findViewById(R.id.num26_4);
        num26_5 = findViewById(R.id.num26_5);
        num26_6 = findViewById(R.id.num26_6);
        num26_7 = findViewById(R.id.num26_7);
        num26_8 = findViewById(R.id.num26_8);
        num26_9 = findViewById(R.id.num26_9);
        num26_10 = findViewById(R.id.num26_10);
        num26_11 = findViewById(R.id.num26_11);
        num26_12 = findViewById(R.id.num26_12);
        num26_13 = findViewById(R.id.num26_13);
        num26_14 = findViewById(R.id.num26_14);
        num26_15 = findViewById(R.id.num26_15);
        num26_16 = findViewById(R.id.num26_16);
        num26_17 = findViewById(R.id.num26_17);
        num26_18 = findViewById(R.id.num26_18);
        num26_19 = findViewById(R.id.num26_19);
        num26_20 = findViewById(R.id.num26_20);
        num26_21 = findViewById(R.id.num26_21);
        num26_22 = findViewById(R.id.num26_22);
        num26_23 = findViewById(R.id.num26_23);
        num26_24 = findViewById(R.id.num26_24);
        num26_25 = findViewById(R.id.num26_25);
        num26_26 = findViewById(R.id.num26_26);



        num28_1 = findViewById(R.id.num28_1);
        num28_2 = findViewById(R.id.num28_2);
        num28_3 = findViewById(R.id.num28_3);
        num28_4 = findViewById(R.id.num28_4);
        num28_5 = findViewById(R.id.num28_5);
        num28_6 = findViewById(R.id.num28_6);
        num28_7 = findViewById(R.id.num28_7);
        num28_8 = findViewById(R.id.num28_8);
        num28_9 = findViewById(R.id.num28_9);
        num28_10 = findViewById(R.id.num28_10);
        num28_11 = findViewById(R.id.num28_11);
        num28_12 = findViewById(R.id.num28_12);
        num28_13 = findViewById(R.id.num28_13);
        num28_14 = findViewById(R.id.num28_14);
        num28_15 = findViewById(R.id.num28_15);
        num28_16 = findViewById(R.id.num28_16);
        num28_17 = findViewById(R.id.num28_17);
        num28_18 = findViewById(R.id.num28_18);
        num28_19 = findViewById(R.id.num28_19);
        num28_20 = findViewById(R.id.num28_20);
        num28_21 = findViewById(R.id.num28_21);
        num28_22 = findViewById(R.id.num28_22);
        num28_23 = findViewById(R.id.num28_23);
        num28_24 = findViewById(R.id.num28_24);
        num28_25 = findViewById(R.id.num28_25);
        num28_26 = findViewById(R.id.num28_26);
        num28_27 = findViewById(R.id.num28_27);
        num28_28 = findViewById(R.id.num28_28);


        num30_1 = findViewById(R.id.num30_1);
        num30_2 = findViewById(R.id.num30_2);
        num30_3 = findViewById(R.id.num30_3);
        num30_4 = findViewById(R.id.num30_4);
        num30_5 = findViewById(R.id.num30_5);
        num30_6 = findViewById(R.id.num30_6);
        num30_7 = findViewById(R.id.num30_7);
        num30_8 = findViewById(R.id.num30_8);
        num30_9 = findViewById(R.id.num30_9);
        num30_10 = findViewById(R.id.num30_10);
        num30_11 = findViewById(R.id.num30_11);
        num30_12 = findViewById(R.id.num30_12);
        num30_13 = findViewById(R.id.num30_13);
        num30_14 = findViewById(R.id.num30_14);
        num30_15 = findViewById(R.id.num30_15);
        num30_16 = findViewById(R.id.num30_16);
        num30_17 = findViewById(R.id.num30_17);
        num30_18 = findViewById(R.id.num30_18);
        num30_19 = findViewById(R.id.num30_19);
        num30_20 = findViewById(R.id.num30_20);
        num30_21 = findViewById(R.id.num30_21);
        num30_22 = findViewById(R.id.num30_22);
        num30_23 = findViewById(R.id.num30_23);
        num30_24 = findViewById(R.id.num30_24);
        num30_25 = findViewById(R.id.num30_25);
        num30_26 = findViewById(R.id.num30_26);
        num30_27 = findViewById(R.id.num30_27);
        num30_28 = findViewById(R.id.num30_28);
        num30_29 = findViewById(R.id.num30_29);
        num30_30 = findViewById(R.id.num30_30);

        num4_a1 = findViewById(R.id.num4_a1);
        num4_a2 = findViewById(R.id.num4_a2);
        num4_a3 = findViewById(R.id.num4_a3);
        num4_a4 = findViewById(R.id.num4_a4);

        num6_a1 = findViewById(R.id.num6_a1);
        num6_a2 = findViewById(R.id.num6_a2);
        num6_a3 = findViewById(R.id.num6_a3);
        num6_a4 = findViewById(R.id.num6_a4);
        num6_a5 = findViewById(R.id.num6_a5);
        num6_a6 = findViewById(R.id.num6_a6);

        num8_a1 = findViewById(R.id.num8_a1);
        num8_a2 = findViewById(R.id.num8_a2);
        num8_a3 = findViewById(R.id.num8_a3);
        num8_a4 = findViewById(R.id.num8_a4);
        num8_a5 = findViewById(R.id.num8_a5);
        num8_a6 = findViewById(R.id.num8_a6);
        num8_a7 = findViewById(R.id.num8_a7);
        num8_a8 = findViewById(R.id.num8_a8);

        num10_a1 = findViewById(R.id.num10_a1);
        num10_a2 = findViewById(R.id.num10_a2);
        num10_a3 = findViewById(R.id.num10_a3);
        num10_a4 = findViewById(R.id.num10_a4);
        num10_a5 = findViewById(R.id.num10_a5);
        num10_a6 = findViewById(R.id.num10_a6);
        num10_a7 = findViewById(R.id.num10_a7);
        num10_a8 = findViewById(R.id.num10_a8);
        num10_a9 = findViewById(R.id.num10_a9);
        num10_a10 = findViewById(R.id.num10_a10);

        num12_a1 = findViewById(R.id.num12_a1);
        num12_a2 = findViewById(R.id.num12_a2);
        num12_a3 = findViewById(R.id.num12_a3);
        num12_a4 = findViewById(R.id.num12_a4);
        num12_a5 = findViewById(R.id.num12_a5);
        num12_a6 = findViewById(R.id.num12_a6);
        num12_a7 = findViewById(R.id.num12_a7);
        num12_a8 = findViewById(R.id.num12_a8);
        num12_a9 = findViewById(R.id.num12_a9);
        num12_a10 = findViewById(R.id.num12_a10);
        num12_a11 = findViewById(R.id.num12_a11);
        num12_a12 = findViewById(R.id.num12_a12);

        num14_a1 = findViewById(R.id.num14_a1);
        num14_a2 = findViewById(R.id.num14_a2);
        num14_a3 = findViewById(R.id.num14_a3);
        num14_a4 = findViewById(R.id.num14_a4);
        num14_a5 = findViewById(R.id.num14_a5);
        num14_a6 = findViewById(R.id.num14_a6);
        num14_a7 = findViewById(R.id.num14_a7);
        num14_a8 = findViewById(R.id.num14_a8);
        num14_a9 = findViewById(R.id.num14_a9);
        num14_a10 = findViewById(R.id.num14_a10);
        num14_a11 = findViewById(R.id.num14_a11);
        num14_a12 = findViewById(R.id.num14_a12);
        num14_a13 = findViewById(R.id.num14_a13);
        num14_a14 = findViewById(R.id.num14_a14);

        num16_a1 = findViewById(R.id.num16_a1);
        num16_a2 = findViewById(R.id.num16_a2);
        num16_a3 = findViewById(R.id.num16_a3);
        num16_a4 = findViewById(R.id.num16_a4);
        num16_a5 = findViewById(R.id.num16_a5);
        num16_a6 = findViewById(R.id.num16_a6);
        num16_a7 = findViewById(R.id.num16_a7);
        num16_a8 = findViewById(R.id.num16_a8);
        num16_a9 = findViewById(R.id.num16_a9);
        num16_a10 = findViewById(R.id.num16_a10);
        num16_a11 = findViewById(R.id.num16_a11);
        num16_a12 = findViewById(R.id.num16_a12);
        num16_a13 = findViewById(R.id.num16_a13);
        num16_a14 = findViewById(R.id.num16_a14);
        num16_a15 = findViewById(R.id.num16_a15);
        num16_a16 = findViewById(R.id.num16_a16);

        num18_a1 = findViewById(R.id.num18_a1);
        num18_a2 = findViewById(R.id.num18_a2);
        num18_a3 = findViewById(R.id.num18_a3);
        num18_a4 = findViewById(R.id.num18_a4);
        num18_a5 = findViewById(R.id.num18_a5);
        num18_a6 = findViewById(R.id.num18_a6);
        num18_a7 = findViewById(R.id.num18_a7);
        num18_a8 = findViewById(R.id.num18_a8);
        num18_a9 = findViewById(R.id.num18_a9);
        num18_a10 = findViewById(R.id.num18_a10);
        num18_a11 = findViewById(R.id.num18_a11);
        num18_a12 = findViewById(R.id.num18_a12);
        num18_a13 = findViewById(R.id.num18_a13);
        num18_a14 = findViewById(R.id.num18_a14);
        num18_a15 = findViewById(R.id.num18_a15);
        num18_a16 = findViewById(R.id.num18_a16);
        num18_a17 = findViewById(R.id.num18_a17);
        num18_a18 = findViewById(R.id.num18_a18);

        num20_a1 = findViewById(R.id.num20_a1);
        num20_a2 = findViewById(R.id.num20_a2);
        num20_a3 = findViewById(R.id.num20_a3);
        num20_a4 = findViewById(R.id.num20_a4);
        num20_a5 = findViewById(R.id.num20_a5);
        num20_a6 = findViewById(R.id.num20_a6);
        num20_a7 = findViewById(R.id.num20_a7);
        num20_a8 = findViewById(R.id.num20_a8);
        num20_a9 = findViewById(R.id.num20_a9);
        num20_a10 = findViewById(R.id.num20_a10);
        num20_a11 = findViewById(R.id.num20_a11);
        num20_a12 = findViewById(R.id.num20_a12);
        num20_a13 = findViewById(R.id.num20_a13);
        num20_a14 = findViewById(R.id.num20_a14);
        num20_a15 = findViewById(R.id.num20_a15);
        num20_a16 = findViewById(R.id.num20_a16);
        num20_a17 = findViewById(R.id.num20_a17);
        num20_a18 = findViewById(R.id.num20_a18);
        num20_a19 = findViewById(R.id.num20_a19);
        num20_a20 = findViewById(R.id.num20_a20);

        num22_a1 = findViewById(R.id.num22_a1);
        num22_a2 = findViewById(R.id.num22_a2);
        num22_a3 = findViewById(R.id.num22_a3);
        num22_a4 = findViewById(R.id.num22_a4);
        num22_a5 = findViewById(R.id.num22_a5);
        num22_a6 = findViewById(R.id.num22_a6);
        num22_a7 = findViewById(R.id.num22_a7);
        num22_a8 = findViewById(R.id.num22_a8);
        num22_a9 = findViewById(R.id.num22_a9);
        num22_a10 = findViewById(R.id.num22_a10);
        num22_a11 = findViewById(R.id.num22_a11);
        num22_a12 = findViewById(R.id.num22_a12);
        num22_a13 = findViewById(R.id.num22_a13);
        num22_a14 = findViewById(R.id.num22_a14);
        num22_a15 = findViewById(R.id.num22_a15);
        num22_a16 = findViewById(R.id.num22_a16);
        num22_a17 = findViewById(R.id.num22_a17);
        num22_a18 = findViewById(R.id.num22_a18);
        num22_a19 = findViewById(R.id.num22_a19);
        num22_a20 = findViewById(R.id.num22_a20);
        num22_a21 = findViewById(R.id.num22_a21);
        num22_a22 = findViewById(R.id.num22_a22);

        num24_a1 = findViewById(R.id.num24_a1);
        num24_a2 = findViewById(R.id.num24_a2);
        num24_a3 = findViewById(R.id.num24_a3);
        num24_a4 = findViewById(R.id.num24_a4);
        num24_a5 = findViewById(R.id.num24_a5);
        num24_a6 = findViewById(R.id.num24_a6);
        num24_a7 = findViewById(R.id.num24_a7);
        num24_a8 = findViewById(R.id.num24_a8);
        num24_a9 = findViewById(R.id.num24_a9);
        num24_a10 = findViewById(R.id.num24_a10);
        num24_a11 = findViewById(R.id.num24_a11);
        num24_a12 = findViewById(R.id.num24_a12);
        num24_a13 = findViewById(R.id.num24_a13);
        num24_a14 = findViewById(R.id.num24_a14);
        num24_a15 = findViewById(R.id.num24_a15);
        num24_a16 = findViewById(R.id.num24_a16);
        num24_a17 = findViewById(R.id.num24_a17);
        num24_a18 = findViewById(R.id.num24_a18);
        num24_a19 = findViewById(R.id.num24_a19);
        num24_a20 = findViewById(R.id.num24_a20);
        num24_a21 = findViewById(R.id.num24_a21);
        num24_a22 = findViewById(R.id.num24_a22);
        num24_a23 = findViewById(R.id.num24_a23);
        num24_a24 = findViewById(R.id.num24_a24);

        num26_a1 = findViewById(R.id.num26_a1);
        num26_a2 = findViewById(R.id.num26_a2);
        num26_a3 = findViewById(R.id.num26_a3);
        num26_a4 = findViewById(R.id.num26_a4);
        num26_a5 = findViewById(R.id.num26_a5);
        num26_a6 = findViewById(R.id.num26_a6);
        num26_a7 = findViewById(R.id.num26_a7);
        num26_a8 = findViewById(R.id.num26_a8);
        num26_a9 = findViewById(R.id.num26_a9);
        num26_a10 = findViewById(R.id.num26_a10);
        num26_a11 = findViewById(R.id.num26_a11);
        num26_a12 = findViewById(R.id.num26_a12);
        num26_a13 = findViewById(R.id.num26_a13);
        num26_a14 = findViewById(R.id.num26_a14);
        num26_a15 = findViewById(R.id.num26_a15);
        num26_a16 = findViewById(R.id.num26_a16);
        num26_a17 = findViewById(R.id.num26_a17);
        num26_a18 = findViewById(R.id.num26_a18);
        num26_a19 = findViewById(R.id.num26_a19);
        num26_a20 = findViewById(R.id.num26_a20);
        num26_a21 = findViewById(R.id.num26_a21);
        num26_a22 = findViewById(R.id.num26_a22);
        num26_a23 = findViewById(R.id.num26_a23);
        num26_a24 = findViewById(R.id.num26_a24);
        num26_a25 = findViewById(R.id.num26_a25);
        num26_a26 = findViewById(R.id.num26_a26);

        num28_a1 = findViewById(R.id.num28_a1);
        num28_a2 = findViewById(R.id.num28_a2);
        num28_a3 = findViewById(R.id.num28_a3);
        num28_a4 = findViewById(R.id.num28_a4);
        num28_a5 = findViewById(R.id.num28_a5);
        num28_a6 = findViewById(R.id.num28_a6);
        num28_a7 = findViewById(R.id.num28_a7);
        num28_a8 = findViewById(R.id.num28_a8);
        num28_a9 = findViewById(R.id.num28_a9);
        num28_a10 = findViewById(R.id.num28_a10);
        num28_a11 = findViewById(R.id.num28_a11);
        num28_a12 = findViewById(R.id.num28_a12);
        num28_a13 = findViewById(R.id.num28_a13);
        num28_a14 = findViewById(R.id.num28_a14);
        num28_a15 = findViewById(R.id.num28_a15);
        num28_a16 = findViewById(R.id.num28_a16);
        num28_a17 = findViewById(R.id.num28_a17);
        num28_a18 = findViewById(R.id.num28_a18);
        num28_a19 = findViewById(R.id.num28_a19);
        num28_a20 = findViewById(R.id.num28_a20);
        num28_a21 = findViewById(R.id.num28_a21);
        num28_a22 = findViewById(R.id.num28_a22);
        num28_a23 = findViewById(R.id.num28_a23);
        num28_a24 = findViewById(R.id.num28_a24);
        num28_a25 = findViewById(R.id.num28_a25);
        num28_a26 = findViewById(R.id.num28_a26);
        num28_a27 = findViewById(R.id.num28_a27);
        num28_a28 = findViewById(R.id.num28_a28);

        num30_a1 = findViewById(R.id.num30_a1);
        num30_a2 = findViewById(R.id.num30_a2);
        num30_a3 = findViewById(R.id.num30_a3);
        num30_a4 = findViewById(R.id.num30_a4);
        num30_a5 = findViewById(R.id.num30_a5);
        num30_a6 = findViewById(R.id.num30_a6);
        num30_a7 = findViewById(R.id.num30_a7);
        num30_a8 = findViewById(R.id.num30_a8);
        num30_a9 = findViewById(R.id.num30_a9);
        num30_a10 = findViewById(R.id.num30_a10);
        num30_a11 = findViewById(R.id.num30_a11);
        num30_a12 = findViewById(R.id.num30_a12);
        num30_a13 = findViewById(R.id.num30_a13);
        num30_a14 = findViewById(R.id.num30_a14);
        num30_a15 = findViewById(R.id.num30_a15);
        num30_a16 = findViewById(R.id.num30_a16);
        num30_a17 = findViewById(R.id.num30_a17);
        num30_a18 = findViewById(R.id.num30_a18);
        num30_a19 = findViewById(R.id.num30_a19);
        num30_a20 = findViewById(R.id.num30_a20);
        num30_a21 = findViewById(R.id.num30_a21);
        num30_a22 = findViewById(R.id.num30_a22);
        num30_a23 = findViewById(R.id.num30_a23);
        num30_a24 = findViewById(R.id.num30_a24);
        num30_a25 = findViewById(R.id.num30_a25);
        num30_a26 = findViewById(R.id.num30_a26);
        num30_a27 = findViewById(R.id.num30_a27);
        num30_a28 = findViewById(R.id.num30_a28);
        num30_a29 = findViewById(R.id.num30_a29);
        num30_a30 = findViewById(R.id.num30_a30);







        num4_a = findViewById(R.id.num4_a);
        num6_a = findViewById(R.id.num6_a);
        num8_a = findViewById(R.id. num8_a);
        num10_a = findViewById(R.id. num10_a);
        num12_a = findViewById(R.id. num12_a);
        num14_a = findViewById(R.id. num14_a);
        num16_a = findViewById(R.id. num16_a);
        num18_a = findViewById(R.id. num18_a);
        num20_a = findViewById(R.id. num20_a);
        num22_a = findViewById(R.id. num22_a);
        num24_a = findViewById(R.id. num24_a);
        num26_a = findViewById(R.id. num26_a);
        num28_a = findViewById(R.id. num28_a);
        num30_a = findViewById(R.id. num30_a);





          num4_top = findViewById(R.id.num4_top);
         num6_top = findViewById(R.id.num6_top);
         num8_top = findViewById(R.id. num8_top);
         num10_top = findViewById(R.id. num10_top);
         num12_top = findViewById(R.id. num12_top);
         num14_top = findViewById(R.id. num14_top);
         num16_top = findViewById(R.id. num16_top);
         num18_top = findViewById(R.id. num18_top);
         num20_top = findViewById(R.id. num20_top);
         num22_top = findViewById(R.id. num22_top);
         num24_top = findViewById(R.id. num24_top);
         num26_top = findViewById(R.id. num26_top);
         num28_top = findViewById(R.id. num28_top);
         num30_top = findViewById(R.id. num30_top);

        num4_bot = findViewById(R.id.num4_bot);
        num6_bot = findViewById(R.id.num6_bot);
        num8_bot = findViewById(R.id. num8_bot);
        num10_bot = findViewById(R.id. num10_bot);
        num12_bot = findViewById(R.id. num12_bot);
        num14_bot = findViewById(R.id. num14_bot);
        num16_bot = findViewById(R.id. num16_bot);
        num18_bot = findViewById(R.id. num18_bot);
        num20_bot = findViewById(R.id. num20_bot);
        num22_bot = findViewById(R.id. num22_bot);
        num24_bot = findViewById(R.id. num24_bot);
        num26_bot = findViewById(R.id. num26_bot);
        num28_bot = findViewById(R.id. num28_bot);
        num30_bot = findViewById(R.id. num30_bot);

        RelativeLayout RS4  = findViewById(R.id.RS4);
        RelativeLayout RS6  = findViewById(R.id.RS6);
        RelativeLayout RS8  = findViewById(R.id.RS8);
        RelativeLayout RS10  = findViewById(R.id.RS10);
        RelativeLayout RS12  = findViewById(R.id.RS12);
        RelativeLayout RS14  = findViewById(R.id.RS14);
        RelativeLayout RS16  = findViewById(R.id.RS16);
        RelativeLayout RS18  = findViewById(R.id.RS18);
        RelativeLayout RS20  = findViewById(R.id.RS20);
        RelativeLayout RS22  = findViewById(R.id.RS22);
        RelativeLayout RS24  = findViewById(R.id.RS24);
        RelativeLayout RS26  = findViewById(R.id.RS26);
        RelativeLayout RS28  = findViewById(R.id.RS28);
        RelativeLayout RS30  = findViewById(R.id.RS30);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SAVEHIGHA = findViewById(R.id.SAVEHIGHA);
        LAWEHIGHA = findViewById(R.id.LAWEHIGHA);
        SAVEHIGHB = findViewById(R.id.SAVEHIGHB);
        LAWEHIGHB = findViewById(R.id.LAWEHIGHB);

        //updated
        SharedPreferences sharedPreferences = getSharedPreferences("SharePref",MODE_PRIVATE);
        String UPDMT = sharedPreferences.getString("UMT","");
        String FDW = sharedPreferences.getString("UFWT","");



        Intent intent = getIntent();

        final DatabaseHelper dbHelper = new DatabaseHelper(this);

        dbHelper.getAllItemsAndStartNextActivity(Loadschedule.this, new DatabaseHelper.OnItemsLoadedListener() {
            @Override
            public void onItemsLoaded(List<String> itemSList) {
                switch (itemSList.size()) {
                    case 4:
                        num4_1.setText(itemSList.get(0));
                        num4_2.setText(itemSList.get(1));
                        num4_3.setText(itemSList.get(2));
                        num4_4.setText(itemSList.get(3));
                        break;
                    case 6:
                        num6_1.setText(itemSList.get(0));
                        num6_2.setText(itemSList.get(1));
                        num6_3.setText(itemSList.get(2));
                        num6_4.setText(itemSList.get(3));
                        num6_5.setText(itemSList.get(4));
                        num6_6.setText(itemSList.get(5));
                        break;
                    case 8:
                        num8_1.setText(itemSList.get(0));
                        num8_2.setText(itemSList.get(1));
                        num8_3.setText(itemSList.get(2));
                        num8_4.setText(itemSList.get(3));
                        num8_5.setText(itemSList.get(4));
                        num8_6.setText(itemSList.get(5));
                        num8_7.setText(itemSList.get(6));
                        num8_8.setText(itemSList.get(7));
                        break;
                    case 10:
                        num10_1.setText(itemSList.get(0));
                        num10_2.setText(itemSList.get(1));
                        num10_3.setText(itemSList.get(2));
                        num10_4.setText(itemSList.get(3));
                        num10_5.setText(itemSList.get(4));
                        num10_6.setText(itemSList.get(5));
                        num10_7.setText(itemSList.get(6));
                        num10_8.setText(itemSList.get(7));
                        num10_9.setText(itemSList.get(8));
                        num10_10.setText(itemSList.get(9));
                        break;

                    case 12:
                        num12_1.setText(itemSList.get(0));
                        num12_2.setText(itemSList.get(1));
                        num12_3.setText(itemSList.get(2));
                        num12_4.setText(itemSList.get(3));
                        num12_5.setText(itemSList.get(4));
                        num12_6.setText(itemSList.get(5));
                        num12_7.setText(itemSList.get(6));
                        num12_8.setText(itemSList.get(7));
                        num12_9.setText(itemSList.get(8));
                        num12_10.setText(itemSList.get(9));
                        num12_11.setText(itemSList.get(10));
                        num12_12.setText(itemSList.get(11));


                        break;
                    case 14:
                        num14_1.setText(itemSList.get(0));
                        num14_2.setText(itemSList.get(1));
                        num14_3.setText(itemSList.get(2));
                        num14_4.setText(itemSList.get(3));
                        num14_5.setText(itemSList.get(4));
                        num14_6.setText(itemSList.get(5));
                        num14_7.setText(itemSList.get(6));
                        num14_8.setText(itemSList.get(7));
                        num14_9.setText(itemSList.get(8));
                        num14_10.setText(itemSList.get(9));
                        num14_11.setText(itemSList.get(10));
                        num14_12.setText(itemSList.get(11));
                        num14_13.setText(itemSList.get(11));
                        num14_14.setText(itemSList.get(13));
                        break;
                    case 16:
                        num16_1.setText(itemSList.get(0));
                        num16_2.setText(itemSList.get(1));
                        num16_3.setText(itemSList.get(2));
                        num16_4.setText(itemSList.get(3));
                        num16_5.setText(itemSList.get(4));
                        num16_6.setText(itemSList.get(5));
                        num16_7.setText(itemSList.get(6));
                        num16_8.setText(itemSList.get(7));
                        num16_9.setText(itemSList.get(8));
                        num16_10.setText(itemSList.get(9));
                        num16_11.setText(itemSList.get(10));
                        num16_12.setText(itemSList.get(11));
                        num16_13.setText(itemSList.get(11));
                        num16_14.setText(itemSList.get(13));
                        num16_15.setText(itemSList.get(14));
                        num16_16.setText(itemSList.get(15));
                        break;
                    case 18:
                        num18_1.setText(itemSList.get(0));
                        num18_2.setText(itemSList.get(1));
                        num18_3.setText(itemSList.get(2));
                        num18_4.setText(itemSList.get(3));
                        num18_5.setText(itemSList.get(4));
                        num18_6.setText(itemSList.get(5));
                        num18_7.setText(itemSList.get(6));
                        num18_8.setText(itemSList.get(7));
                        num18_9.setText(itemSList.get(8));
                        num18_10.setText(itemSList.get(9));
                        num18_11.setText(itemSList.get(10));
                        num18_12.setText(itemSList.get(11));
                        num18_13.setText(itemSList.get(11));
                        num18_14.setText(itemSList.get(13));
                        num18_15.setText(itemSList.get(14));
                        num18_16.setText(itemSList.get(15));
                        num18_17.setText(itemSList.get(16));
                        num18_18.setText(itemSList.get(17));

                        break;
                    case 20:
                        num20_1.setText(itemSList.get(0));
                        num20_2.setText(itemSList.get(1));
                        num20_3.setText(itemSList.get(2));
                        num20_4.setText(itemSList.get(3));
                        num20_5.setText(itemSList.get(4));
                        num20_6.setText(itemSList.get(5));
                        num20_7.setText(itemSList.get(6));
                        num20_8.setText(itemSList.get(7));
                        num20_9.setText(itemSList.get(8));
                        num20_10.setText(itemSList.get(9));
                        num20_11.setText(itemSList.get(10));
                        num20_12.setText(itemSList.get(11));
                        num20_13.setText(itemSList.get(11));
                        num20_14.setText(itemSList.get(13));
                        num20_15.setText(itemSList.get(14));
                        num20_16.setText(itemSList.get(15));
                        num20_17.setText(itemSList.get(16));
                        num20_18.setText(itemSList.get(17));
                        num20_19.setText(itemSList.get(18));
                        num20_20.setText(itemSList.get(19));


                        break;
                    case 22:
                        num22_1.setText(itemSList.get(0));
                        num22_2.setText(itemSList.get(1));
                        num22_3.setText(itemSList.get(2));
                        num22_4.setText(itemSList.get(3));
                        num22_5.setText(itemSList.get(4));
                        num22_6.setText(itemSList.get(5));
                        num22_7.setText(itemSList.get(6));
                        num22_8.setText(itemSList.get(7));
                        num22_9.setText(itemSList.get(8));
                        num22_10.setText(itemSList.get(9));
                        num22_11.setText(itemSList.get(10));
                        num22_12.setText(itemSList.get(11));
                        num22_13.setText(itemSList.get(11));
                        num22_14.setText(itemSList.get(13));
                        num22_15.setText(itemSList.get(14));
                        num22_16.setText(itemSList.get(15));
                        num22_17.setText(itemSList.get(16));
                        num22_18.setText(itemSList.get(17));
                        num22_19.setText(itemSList.get(18));
                        num22_20.setText(itemSList.get(19));
                        num22_21.setText(itemSList.get(20));
                        num22_22.setText(itemSList.get(21));

                        break;
                    case 24:
                        num24_1.setText(itemSList.get(0));
                        num24_2.setText(itemSList.get(1));
                        num24_3.setText(itemSList.get(2));
                        num24_4.setText(itemSList.get(3));
                        num24_5.setText(itemSList.get(4));
                        num24_6.setText(itemSList.get(5));
                        num24_7.setText(itemSList.get(6));
                        num24_8.setText(itemSList.get(7));
                        num24_9.setText(itemSList.get(8));
                        num24_10.setText(itemSList.get(9));
                        num24_11.setText(itemSList.get(10));
                        num24_12.setText(itemSList.get(11));
                        num24_13.setText(itemSList.get(11));
                        num24_14.setText(itemSList.get(13));
                        num24_15.setText(itemSList.get(14));
                        num24_16.setText(itemSList.get(15));
                        num24_17.setText(itemSList.get(16));
                        num24_18.setText(itemSList.get(17));
                        num24_19.setText(itemSList.get(18));
                        num24_20.setText(itemSList.get(19));
                        num24_21.setText(itemSList.get(20));
                        num24_22.setText(itemSList.get(21));
                        num24_23.setText(itemSList.get(22));
                        num24_24.setText(itemSList.get(23));

                        break;
                    case 26:
                        num26_1.setText(itemSList.get(0));
                        num26_2.setText(itemSList.get(1));
                        num26_3.setText(itemSList.get(2));
                        num26_4.setText(itemSList.get(3));
                        num26_5.setText(itemSList.get(4));
                        num26_6.setText(itemSList.get(5));
                        num26_7.setText(itemSList.get(6));
                        num26_8.setText(itemSList.get(7));
                        num26_9.setText(itemSList.get(8));
                        num26_10.setText(itemSList.get(9));
                        num26_11.setText(itemSList.get(10));
                        num26_12.setText(itemSList.get(11));
                        num26_13.setText(itemSList.get(11));
                        num26_14.setText(itemSList.get(13));
                        num26_15.setText(itemSList.get(14));
                        num26_16.setText(itemSList.get(15));
                        num26_17.setText(itemSList.get(16));
                        num26_18.setText(itemSList.get(17));
                        num26_19.setText(itemSList.get(18));
                        num26_20.setText(itemSList.get(19));
                        num26_21.setText(itemSList.get(20));
                        num26_22.setText(itemSList.get(21));
                        num26_23.setText(itemSList.get(22));
                        num26_24.setText(itemSList.get(23));
                        num26_25.setText(itemSList.get(24));
                        num26_26.setText(itemSList.get(25));

                        break;
                    case 28:
                        num28_1.setText(itemSList.get(0));
                        num28_2.setText(itemSList.get(1));
                        num28_3.setText(itemSList.get(2));
                        num28_4.setText(itemSList.get(3));
                        num28_5.setText(itemSList.get(4));
                        num28_6.setText(itemSList.get(5));
                        num28_7.setText(itemSList.get(6));
                        num28_8.setText(itemSList.get(7));
                        num28_9.setText(itemSList.get(8));
                        num28_10.setText(itemSList.get(9));
                        num28_11.setText(itemSList.get(10));
                        num28_12.setText(itemSList.get(11));
                        num28_13.setText(itemSList.get(11));
                        num28_14.setText(itemSList.get(13));
                        num28_15.setText(itemSList.get(14));
                        num28_16.setText(itemSList.get(15));
                        num28_17.setText(itemSList.get(16));
                        num28_18.setText(itemSList.get(17));
                        num28_19.setText(itemSList.get(18));
                        num28_20.setText(itemSList.get(19));
                        num28_21.setText(itemSList.get(20));
                        num28_22.setText(itemSList.get(21));
                        num28_23.setText(itemSList.get(22));
                        num28_24.setText(itemSList.get(23));
                        num28_25.setText(itemSList.get(24));
                        num28_26.setText(itemSList.get(25));
                        num28_27.setText(itemSList.get(26));
                        num28_28.setText(itemSList.get(27));

                        break;
                    case 30:
                        num30_1.setText(itemSList.get(0));
                        num30_2.setText(itemSList.get(1));
                        num30_3.setText(itemSList.get(2));
                        num30_4.setText(itemSList.get(3));
                        num30_5.setText(itemSList.get(4));
                        num30_6.setText(itemSList.get(5));
                        num30_7.setText(itemSList.get(6));
                        num30_8.setText(itemSList.get(7));
                        num30_9.setText(itemSList.get(8));
                        num30_10.setText(itemSList.get(9));
                        num30_11.setText(itemSList.get(10));
                        num30_12.setText(itemSList.get(11));
                        num30_13.setText(itemSList.get(11));
                        num30_14.setText(itemSList.get(13));
                        num30_15.setText(itemSList.get(14));
                        num30_16.setText(itemSList.get(15));
                        num30_17.setText(itemSList.get(16));
                        num30_18.setText(itemSList.get(17));
                        num30_19.setText(itemSList.get(18));
                        num30_20.setText(itemSList.get(19));
                        num30_21.setText(itemSList.get(20));
                        num30_22.setText(itemSList.get(21));
                        num30_23.setText(itemSList.get(22));
                        num30_24.setText(itemSList.get(23));
                        num30_25.setText(itemSList.get(24));
                        num30_26.setText(itemSList.get(25));
                        num30_27.setText(itemSList.get(26));
                        num30_28.setText(itemSList.get(27));
                        num30_29.setText(itemSList.get(28));
                        num30_30.setText(itemSList.get(29));
                        break;
                    default:
                        // Handle other cases if necessary
                }
            }
        });

        dbHelper.getAllAsAndStartNextActivity(Loadschedule.this, new DatabaseHelper.OnItemsLoadedListener() {
            @Override
            public void onItemsLoaded(List<String>ASList) {
                if (ASList != null) {
                    switch (ASList.size()) {
                        case 4:
                            num4_a1.setText(ASList.get(0) + " A");
                            num4_a2.setText(ASList.get(1) + " A");
                            num4_a3.setText(ASList.get(2) + " A");
                            num4_a4.setText(ASList.get(3) + " A");
                            break;
                        case 6:
                            num6_a1.setText(ASList.get(0) + " A");
                            num6_a2.setText(ASList.get(1) + " A");
                            num6_a3.setText(ASList.get(2) + " A");
                            num6_a4.setText(ASList.get(3) + " A");
                            num6_a5.setText(ASList.get(4) + " A");
                            num6_a6.setText(ASList.get(5) + " A");
                            break;
                        case 8:
                            num8_a1.setText(ASList.get(0) + " A");
                            num8_a2.setText(ASList.get(1) + " A");
                            num8_a3.setText(ASList.get(2) + " A");
                            num8_a4.setText(ASList.get(3) + " A");
                            num8_a5.setText(ASList.get(4) + " A");
                            num8_a6.setText(ASList.get(5) + " A");
                            num8_a7.setText(ASList.get(6) + " A");
                            num8_a8.setText(ASList.get(7) + " A");
                            break;
                        case 10:
                            num10_a1.setText(ASList.get(0) + " A");
                            num10_a2.setText(ASList.get(1) + " A");
                            num10_a3.setText(ASList.get(2) + " A");
                            num10_a4.setText(ASList.get(3) + " A");
                            num10_a5.setText(ASList.get(4) + " A");
                            num10_a6.setText(ASList.get(5) + " A");
                            num10_a7.setText(ASList.get(6) + " A");
                            num10_a8.setText(ASList.get(7) + " A");
                            num10_a9.setText(ASList.get(8) + " A");
                            num10_a10.setText(ASList.get(9) + " A");
                            break;

                        case 12:
                            num12_a1.setText(ASList.get(0) + " A");
                            num12_a2.setText(ASList.get(1) + " A");
                            num12_a3.setText(ASList.get(2) + " A");
                            num12_a4.setText(ASList.get(3) + " A");
                            num12_a5.setText(ASList.get(4) + " A");
                            num12_a6.setText(ASList.get(5) + " A");
                            num12_a7.setText(ASList.get(6) + " A");
                            num12_a8.setText(ASList.get(7) + " A");
                            num12_a9.setText(ASList.get(8) + " A");
                            num12_a10.setText(ASList.get(9) + " A");
                            num12_a11.setText(ASList.get(10) + " A");
                            num12_a12.setText(ASList.get(11) + " A");


                            break;
                        case 14:
                            num14_a1.setText(ASList.get(0) + " A");
                            num14_a2.setText(ASList.get(1) + " A");
                            num14_a3.setText(ASList.get(2) + " A");
                            num14_a4.setText(ASList.get(3) + " A");
                            num14_a5.setText(ASList.get(4) + " A");
                            num14_a6.setText(ASList.get(5) + " A");
                            num14_a7.setText(ASList.get(6) + " A");
                            num14_a8.setText(ASList.get(7) + " A");
                            num14_a9.setText(ASList.get(8) + " A");
                            num14_a10.setText(ASList.get(9) + " A");
                            num14_a11.setText(ASList.get(10) + " A");
                            num14_a12.setText(ASList.get(11) + " A");
                            num14_a13.setText(ASList.get(11) + " A");
                            num14_a14.setText(ASList.get(13) + " A");
                            break;
                        case 16:
                            num16_a1.setText(ASList.get(0) + " A");
                            num16_a2.setText(ASList.get(1) + " A");
                            num16_a3.setText(ASList.get(2) + " A");
                            num16_a4.setText(ASList.get(3) + " A");
                            num16_a5.setText(ASList.get(4) + " A");
                            num16_a6.setText(ASList.get(5) + " A");
                            num16_a7.setText(ASList.get(6) + " A");
                            num16_a8.setText(ASList.get(7) + " A");
                            num16_a9.setText(ASList.get(8) + " A");
                            num16_a10.setText(ASList.get(9) + " A");
                            num16_a11.setText(ASList.get(10) + " A");
                            num16_a12.setText(ASList.get(11) + " A");
                            num16_a13.setText(ASList.get(11) + " A");
                            num16_a14.setText(ASList.get(13) + " A");
                            num16_a15.setText(ASList.get(14) + " A");
                            num16_a16.setText(ASList.get(15) + " A");
                            break;
                        case 18:
                            num18_a1.setText(ASList.get(0) + " A");
                            num18_a2.setText(ASList.get(1) + " A");
                            num18_a3.setText(ASList.get(2) + " A");
                            num18_a4.setText(ASList.get(3) + " A");
                            num18_a5.setText(ASList.get(4) + " A");
                            num18_a6.setText(ASList.get(5) + " A");
                            num18_a7.setText(ASList.get(6) + " A");
                            num18_a8.setText(ASList.get(7) + " A");
                            num18_a9.setText(ASList.get(8) + " A");
                            num18_a10.setText(ASList.get(9) + " A");
                            num18_a11.setText(ASList.get(10) + " A");
                            num18_a12.setText(ASList.get(11) + " A");
                            num18_a13.setText(ASList.get(11) + " A");
                            num18_a14.setText(ASList.get(13) + " A");
                            num18_a15.setText(ASList.get(14) + " A");
                            num18_a16.setText(ASList.get(15) + " A");
                            num18_a17.setText(ASList.get(16) + " A");
                            num18_a18.setText(ASList.get(17) + " A");

                            break;
                        case 20:
                            num20_a1.setText(ASList.get(0) + " A");
                            num20_a2.setText(ASList.get(1) + " A");
                            num20_a3.setText(ASList.get(2) + " A");
                            num20_a4.setText(ASList.get(3) + " A");
                            num20_a5.setText(ASList.get(4) + " A");
                            num20_a6.setText(ASList.get(5) + " A");
                            num20_a7.setText(ASList.get(6) + " A");
                            num20_a8.setText(ASList.get(7) + " A");
                            num20_a9.setText(ASList.get(8) + " A");
                            num20_a10.setText(ASList.get(9) + " A");
                            num20_a11.setText(ASList.get(10) + " A");
                            num20_a12.setText(ASList.get(11) + " A");
                            num20_a13.setText(ASList.get(11) + " A");
                            num20_a14.setText(ASList.get(13) + " A");
                            num20_a15.setText(ASList.get(14) + " A");
                            num20_a16.setText(ASList.get(15) + " A");
                            num20_a17.setText(ASList.get(16) + " A");
                            num20_a18.setText(ASList.get(17) + " A");
                            num20_a19.setText(ASList.get(18) + " A");
                            num20_a20.setText(ASList.get(19) + " A");


                            break;
                        case 22:
                            num22_a1.setText(ASList.get(0) + " A");
                            num22_a2.setText(ASList.get(1) + " A");
                            num22_a3.setText(ASList.get(2) + " A");
                            num22_a4.setText(ASList.get(3) + " A");
                            num22_a5.setText(ASList.get(4) + " A");
                            num22_a6.setText(ASList.get(5) + " A");
                            num22_a7.setText(ASList.get(6) + " A");
                            num22_a8.setText(ASList.get(7) + " A");
                            num22_a9.setText(ASList.get(8) + " A");
                            num22_a10.setText(ASList.get(9) + " A");
                            num22_a11.setText(ASList.get(10) + " A");
                            num22_a12.setText(ASList.get(11) + " A");
                            num22_a13.setText(ASList.get(11) + " A");
                            num22_a14.setText(ASList.get(13) + " A");
                            num22_a15.setText(ASList.get(14) + " A");
                            num22_a16.setText(ASList.get(15) + " A");
                            num22_a17.setText(ASList.get(16) + " A");
                            num22_a18.setText(ASList.get(17) + " A");
                            num22_a19.setText(ASList.get(18) + " A");
                            num22_a20.setText(ASList.get(19) + " A");
                            num22_a21.setText(ASList.get(20) + " A");
                            num22_a22.setText(ASList.get(21) + " A");

                            break;
                        case 24:
                            num24_a1.setText(ASList.get(0) + " A");
                            num24_a2.setText(ASList.get(1) + " A");
                            num24_a3.setText(ASList.get(2) + " A");
                            num24_a4.setText(ASList.get(3) + " A");
                            num24_a5.setText(ASList.get(4) + " A");
                            num24_a6.setText(ASList.get(5) + " A");
                            num24_a7.setText(ASList.get(6) + " A");
                            num24_a8.setText(ASList.get(7) + " A");
                            num24_a9.setText(ASList.get(8) + " A");
                            num24_a10.setText(ASList.get(9) + " A");
                            num24_a11.setText(ASList.get(10) + " A");
                            num24_a12.setText(ASList.get(11) + " A");
                            num24_a13.setText(ASList.get(11) + " A");
                            num24_a14.setText(ASList.get(13) + " A");
                            num24_a15.setText(ASList.get(14) + " A");
                            num24_a16.setText(ASList.get(15) + " A");
                            num24_a17.setText(ASList.get(16) + " A");
                            num24_a18.setText(ASList.get(17) + " A");
                            num24_a19.setText(ASList.get(18) + " A");
                            num24_a20.setText(ASList.get(19) + " A");
                            num24_a21.setText(ASList.get(20) + " A");
                            num24_a22.setText(ASList.get(21) + " A");
                            num24_a23.setText(ASList.get(22) + " A");
                            num24_a24.setText(ASList.get(23) + " A");

                            break;
                        case 26:
                            num26_a1.setText(ASList.get(0) + " A");
                            num26_a2.setText(ASList.get(1) + " A");
                            num26_a3.setText(ASList.get(2) + " A");
                            num26_a4.setText(ASList.get(3) + " A");
                            num26_a5.setText(ASList.get(4) + " A");
                            num26_a6.setText(ASList.get(5) + " A");
                            num26_a7.setText(ASList.get(6) + " A");
                            num26_a8.setText(ASList.get(7) + " A");
                            num26_a9.setText(ASList.get(8) + " A");
                            num26_a10.setText(ASList.get(9) + " A");
                            num26_a11.setText(ASList.get(10) + " A");
                            num26_a12.setText(ASList.get(11) + " A");
                            num26_a13.setText(ASList.get(11) + " A");
                            num26_a14.setText(ASList.get(13) + " A");
                            num26_a15.setText(ASList.get(14) + " A");
                            num26_a16.setText(ASList.get(15) + " A");
                            num26_a17.setText(ASList.get(16) + " A");
                            num26_a18.setText(ASList.get(17) + " A");
                            num26_a19.setText(ASList.get(18) + " A");
                            num26_a20.setText(ASList.get(19) + " A");
                            num26_a21.setText(ASList.get(20) + " A");
                            num26_a22.setText(ASList.get(21) + " A");
                            num26_a23.setText(ASList.get(22) + " A");
                            num26_a24.setText(ASList.get(23) + " A");
                            num26_a25.setText(ASList.get(24) + " A");
                            num26_a26.setText(ASList.get(25) + " A");

                            break;
                        case 28:
                            num28_a1.setText(ASList.get(0) + " A");
                            num28_a2.setText(ASList.get(1) + " A");
                            num28_a3.setText(ASList.get(2) + " A");
                            num28_a4.setText(ASList.get(3) + " A");
                            num28_a5.setText(ASList.get(4) + " A");
                            num28_a6.setText(ASList.get(5) + " A");
                            num28_a7.setText(ASList.get(6) + " A");
                            num28_a8.setText(ASList.get(7) + " A");
                            num28_a9.setText(ASList.get(8) + " A");
                            num28_a10.setText(ASList.get(9) + " A");
                            num28_a11.setText(ASList.get(10) + " A");
                            num28_a12.setText(ASList.get(11) + " A");
                            num28_a13.setText(ASList.get(11) + " A");
                            num28_a14.setText(ASList.get(13) + " A");
                            num28_a15.setText(ASList.get(14) + " A");
                            num28_a16.setText(ASList.get(15) + " A");
                            num28_a17.setText(ASList.get(16) + " A");
                            num28_a18.setText(ASList.get(17) + " A");
                            num28_a19.setText(ASList.get(18) + " A");
                            num28_a20.setText(ASList.get(19) + " A");
                            num28_a21.setText(ASList.get(20) + " A");
                            num28_a22.setText(ASList.get(21) + " A");
                            num28_a23.setText(ASList.get(22) + " A");
                            num28_a24.setText(ASList.get(23) + " A");
                            num28_a25.setText(ASList.get(24) + " A");
                            num28_a26.setText(ASList.get(25) + " A");
                            num28_a27.setText(ASList.get(26) + " A");
                            num28_a28.setText(ASList.get(27) + " A");

                            break;
                        case 30:
                            num30_a1.setText(ASList.get(0) + " A");
                            num30_a2.setText(ASList.get(1) + " A");
                            num30_a3.setText(ASList.get(2) + " A");
                            num30_a4.setText(ASList.get(3) + " A");
                            num30_a5.setText(ASList.get(4) + " A");
                            num30_a6.setText(ASList.get(5) + " A");
                            num30_a7.setText(ASList.get(6) + " A");
                            num30_a8.setText(ASList.get(7) + " A");
                            num30_a9.setText(ASList.get(8) + " A");
                            num30_a10.setText(ASList.get(9) + " A");
                            num30_a11.setText(ASList.get(10) + " A");
                            num30_a12.setText(ASList.get(11) + " A");
                            num30_a13.setText(ASList.get(11) + " A");
                            num30_a14.setText(ASList.get(13) + " A");
                            num30_a15.setText(ASList.get(14) + " A");
                            num30_a16.setText(ASList.get(15) + " A");
                            num30_a17.setText(ASList.get(16) + " A");
                            num30_a18.setText(ASList.get(17) + " A");
                            num30_a19.setText(ASList.get(18) + " A");
                            num30_a20.setText(ASList.get(19) + " A");
                            num30_a21.setText(ASList.get(20) + " A");
                            num30_a22.setText(ASList.get(21) + " A");
                            num30_a23.setText(ASList.get(22) + " A");
                            num30_a24.setText(ASList.get(23) + " A");
                            num30_a25.setText(ASList.get(24) + " A");
                            num30_a26.setText(ASList.get(25) + " A");
                            num30_a27.setText(ASList.get(26) + " A");
                            num30_a28.setText(ASList.get(27) + " A");
                            num30_a29.setText(ASList.get(28) + " A");
                            num30_a30.setText(ASList.get(29) + " A");
                            break;
                        default:
                            // Handle other cases if necessary
                    }

                }
            }
        });
     ///   List<String> itemSList = getIntent().getStringArrayListExtra("itemListS");

        //for display skeleton
        dbHelper.getAllATsAndStartNextActivity(Loadschedule.this, new DatabaseHelper.OnItemsLoadedListener() {
            @Override
            public void onItemsLoaded(List<String>TSList) {
                if (TSList != null) {
                    switch (TSList.size()) {
                        case 4:
                           RS4.setVisibility(View.VISIBLE);
                            RS6.setVisibility(View.GONE);
                            RS8.setVisibility(View.GONE);
                            RS10.setVisibility(View.GONE);
                            RS12.setVisibility(View.GONE);
                            RS14.setVisibility(View.GONE);
                            RS16.setVisibility(View.GONE);
                            RS18.setVisibility(View.GONE);
                            RS20.setVisibility(View.GONE);
                            RS22.setVisibility(View.GONE);
                            RS24.setVisibility(View.GONE);
                            RS26.setVisibility(View.GONE);
                            RS28.setVisibility(View.GONE);
                            RS30.setVisibility(View.GONE);

                            break;
                        case 6:
                            RS4.setVisibility(View.GONE);
                            RS6.setVisibility(View.VISIBLE);
                            RS8.setVisibility(View.GONE);
                            RS10.setVisibility(View.GONE);
                            RS12.setVisibility(View.GONE);
                            RS14.setVisibility(View.GONE);
                            RS16.setVisibility(View.GONE);
                            RS18.setVisibility(View.GONE);
                            RS20.setVisibility(View.GONE);
                            RS22.setVisibility(View.GONE);
                            RS24.setVisibility(View.GONE);
                            RS26.setVisibility(View.GONE);
                            RS28.setVisibility(View.GONE);
                            RS30.setVisibility(View.GONE);


                            break;
                        case 8:
                            RS4.setVisibility(View.GONE);
                            RS6.setVisibility(View.GONE);
                            RS8.setVisibility(View.VISIBLE);
                            RS10.setVisibility(View.GONE);
                            RS12.setVisibility(View.GONE);
                            RS14.setVisibility(View.GONE);
                            RS16.setVisibility(View.GONE);
                            RS18.setVisibility(View.GONE);
                            RS20.setVisibility(View.GONE);
                            RS22.setVisibility(View.GONE);
                            RS24.setVisibility(View.GONE);
                            RS26.setVisibility(View.GONE);
                            RS28.setVisibility(View.GONE);
                            RS30.setVisibility(View.GONE);


                            break;
                        case 10:
                            RS4.setVisibility(View.GONE);
                            RS6.setVisibility(View.GONE);
                            RS8.setVisibility(View.GONE);
                            RS10.setVisibility(View.VISIBLE);
                            RS12.setVisibility(View.GONE);
                            RS14.setVisibility(View.GONE);
                            RS16.setVisibility(View.GONE);
                            RS18.setVisibility(View.GONE);
                            RS20.setVisibility(View.GONE);
                            RS22.setVisibility(View.GONE);
                            RS24.setVisibility(View.GONE);
                            RS26.setVisibility(View.GONE);
                            RS28.setVisibility(View.GONE);
                            RS30.setVisibility(View.GONE);



                            break;

                        case 12:
                            RS4.setVisibility(View.GONE);
                            RS6.setVisibility(View.GONE);
                            RS8.setVisibility(View.GONE);
                            RS10.setVisibility(View.GONE);
                            RS12.setVisibility(View.VISIBLE);
                            RS14.setVisibility(View.GONE);
                            RS16.setVisibility(View.GONE);
                            RS18.setVisibility(View.GONE);
                            RS20.setVisibility(View.GONE);
                            RS22.setVisibility(View.GONE);
                            RS24.setVisibility(View.GONE);
                            RS26.setVisibility(View.GONE);
                            RS28.setVisibility(View.GONE);
                            RS30.setVisibility(View.GONE);

                            break;
                        case 14:
                            RS4.setVisibility(View.GONE);
                            RS6.setVisibility(View.GONE);
                            RS8.setVisibility(View.GONE);
                            RS10.setVisibility(View.GONE);
                            RS12.setVisibility(View.GONE);
                            RS14.setVisibility(View.VISIBLE);
                            RS16.setVisibility(View.GONE);
                            RS18.setVisibility(View.GONE);
                            RS20.setVisibility(View.GONE);
                            RS22.setVisibility(View.GONE);
                            RS24.setVisibility(View.GONE);
                            RS26.setVisibility(View.GONE);
                            RS28.setVisibility(View.GONE);
                            RS30.setVisibility(View.GONE);


                            break;
                        case 16:
                            RS4.setVisibility(View.GONE);
                            RS6.setVisibility(View.GONE);
                            RS8.setVisibility(View.GONE);
                            RS10.setVisibility(View.GONE);
                            RS12.setVisibility(View.GONE);
                            RS14.setVisibility(View.GONE);
                            RS16.setVisibility(View.VISIBLE);
                            RS18.setVisibility(View.GONE);
                            RS20.setVisibility(View.GONE);
                            RS22.setVisibility(View.GONE);
                            RS24.setVisibility(View.GONE);
                            RS26.setVisibility(View.GONE);
                            RS28.setVisibility(View.GONE);
                            RS30.setVisibility(View.GONE);


                            break;
                        case 18:
                            RS4.setVisibility(View.GONE);
                            RS6.setVisibility(View.GONE);
                            RS8.setVisibility(View.GONE);
                            RS10.setVisibility(View.GONE);
                            RS12.setVisibility(View.GONE);
                            RS14.setVisibility(View.GONE);
                            RS16.setVisibility(View.GONE);
                            RS18.setVisibility(View.VISIBLE);
                            RS20.setVisibility(View.GONE);
                            RS22.setVisibility(View.GONE);
                            RS24.setVisibility(View.GONE);
                            RS26.setVisibility(View.GONE);
                            RS28.setVisibility(View.GONE);
                            RS30.setVisibility(View.GONE);


                            break;
                        case 20:
                            RS4.setVisibility(View.GONE);
                            RS6.setVisibility(View.GONE);
                            RS8.setVisibility(View.GONE);
                            RS10.setVisibility(View.GONE);
                            RS12.setVisibility(View.GONE);
                            RS14.setVisibility(View.GONE);
                            RS16.setVisibility(View.GONE);
                            RS18.setVisibility(View.GONE);
                            RS20.setVisibility(View.VISIBLE);
                            RS22.setVisibility(View.GONE);
                            RS24.setVisibility(View.GONE);
                            RS26.setVisibility(View.GONE);
                            RS28.setVisibility(View.GONE);
                            RS30.setVisibility(View.GONE);



                            break;
                        case 22:
                            RS4.setVisibility(View.GONE);
                            RS6.setVisibility(View.GONE);
                            RS8.setVisibility(View.GONE);
                            RS10.setVisibility(View.GONE);
                            RS12.setVisibility(View.GONE);
                            RS14.setVisibility(View.GONE);
                            RS16.setVisibility(View.GONE);
                            RS18.setVisibility(View.GONE);
                            RS20.setVisibility(View.GONE);
                            RS22.setVisibility(View.VISIBLE);
                            RS24.setVisibility(View.GONE);
                            RS26.setVisibility(View.GONE);
                            RS28.setVisibility(View.GONE);
                            RS30.setVisibility(View.GONE);

                            break;
                        case 24:
                            RS4.setVisibility(View.GONE);
                            RS6.setVisibility(View.GONE);
                            RS8.setVisibility(View.GONE);
                            RS10.setVisibility(View.GONE);
                            RS12.setVisibility(View.GONE);
                            RS14.setVisibility(View.GONE);
                            RS16.setVisibility(View.GONE);
                            RS18.setVisibility(View.GONE);
                            RS20.setVisibility(View.GONE);
                            RS22.setVisibility(View.GONE);
                            RS24.setVisibility(View.VISIBLE);
                            RS26.setVisibility(View.GONE);
                            RS28.setVisibility(View.GONE);
                            RS30.setVisibility(View.GONE);

                            break;
                        case 26:
                            RS4.setVisibility(View.GONE);
                            RS6.setVisibility(View.GONE);
                            RS8.setVisibility(View.GONE);
                            RS10.setVisibility(View.GONE);
                            RS12.setVisibility(View.GONE);
                            RS14.setVisibility(View.GONE);
                            RS16.setVisibility(View.GONE);
                            RS18.setVisibility(View.GONE);
                            RS20.setVisibility(View.GONE);
                            RS22.setVisibility(View.GONE);
                            RS24.setVisibility(View.GONE);
                            RS26.setVisibility(View.VISIBLE);
                            RS28.setVisibility(View.GONE);
                            RS30.setVisibility(View.GONE);

                            break;
                        case 28:
                            RS4.setVisibility(View.GONE);
                            RS6.setVisibility(View.GONE);
                            RS8.setVisibility(View.GONE);
                            RS10.setVisibility(View.GONE);
                            RS12.setVisibility(View.GONE);
                            RS14.setVisibility(View.GONE);
                            RS16.setVisibility(View.GONE);
                            RS18.setVisibility(View.GONE);
                            RS20.setVisibility(View.GONE);
                            RS22.setVisibility(View.GONE);
                            RS24.setVisibility(View.GONE);
                            RS26.setVisibility(View.GONE);
                            RS28.setVisibility(View.VISIBLE);
                            RS30.setVisibility(View.GONE);


                            break;
                        case 30:
                            RS4.setVisibility(View.GONE);
                            RS6.setVisibility(View.GONE);
                            RS8.setVisibility(View.GONE);
                            RS10.setVisibility(View.GONE);
                            RS12.setVisibility(View.GONE);
                            RS14.setVisibility(View.GONE);
                            RS16.setVisibility(View.GONE);
                            RS18.setVisibility(View.GONE);
                            RS20.setVisibility(View.GONE);
                            RS22.setVisibility(View.GONE);
                            RS24.setVisibility(View.GONE);
                            RS26.setVisibility(View.GONE);
                            RS28.setVisibility(View.GONE);
                            RS30.setVisibility(View.VISIBLE);

                            break;
                        default:
                            // Handle other cases if necessary
                    }

                }
            }
        });





        if (intent != null) {
            String PassMain = intent.getStringExtra("Value");
            String totalVA = intent.getStringExtra("TOTALVA");
            String totalA = intent.getStringExtra("TOTALA");
            String HIGHA = intent.getStringExtra("HIGHA");
            String Wiretype =intent.getStringExtra("WFG");
            String PVCUPDATED = intent.getStringExtra("NUMPVC");
            String WFGTYPE = intent.getStringExtra("WFG");
            String CTR = intent.getStringExtra("CTR");



//minimize the space for skeleton display




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

            if (CTR != null) {
                CTRtv.setText(CTR);
            }

            if (totalA != null) {
                totalATextView.setText(totalA);
                totalone.setText(totalA);
                TotalB.setText(totalA);
                ///for total A in skel
                TextView[] numViews = {num4_a, num6_a, num8_a, num10_a, num12_a, num14_a, num16_a, num18_a, num20_a, num22_a, num24_a, num26_a, num28_a, num30_a};

                for (int i = 0; i < numViews.length; i++) {
                    numViews[i].setText(totalA + " A");
                }
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


        String FeederW2 = FeederWire.getText().toString().trim();

//IF FEEDERWIRE IS 30 BELOW THE FEEDERWIRE SECOND 8.0 mm.sq.
        if (FeederW2.equals("2 - 2.0mm.sq. THHN Cu. Wire")) {
            FeederWireSecond.setText("+ 1 - 8.0 mm.sq.");
            FeederWireFourth.setText("(G)In 20 mmø IMC PIPE");

        }
        if (FeederW2.equals("2 - 3.5mm.sq. THHN Cu. Wire")) {
            FeederWireSecond.setText("+ 1 - 8.0 mm.sq.");
            FeederWireFourth.setText("(G)In 20 mmø IMC PIPE");
        }
        if (FeederW2.equals("2 - 5.5mm.sq. THHN Cu. Wire")) {
            FeederWireSecond.setText("+ 1 - 8.0 mm.sq.");
            FeederWireFourth.setText("(G)In 20 mmø IMC PIPE");
        }
        if (FeederW2.equals("2 - 8.0mm.sq. THHN Cu. Wire")) {
            FeederWireSecond.setText("+ 1 - 8.0 mm.sq.");
            FeederWireFourth.setText("(G)In 20 mmø IMC PIPE");
        }
        if (FeederW2.equals("2 - 14.0mm.sq. THHN Cu. Wire")) {
            FeederWireSecond.setText("+ 1 - 8.0 mm.sq.");
            FeederWireFourth.setText("(G)In 20 mmø IMC PIPE");
        }
        if (FeederW2.equals("2 - 22.0mm.sq. THHN Cu. Wire")) {
            FeederWireSecond.setText("+ 1 - 8.0 mm.sq.");
            FeederWireFourth.setText("(G)In 25 mmø IMC PIPE");
        }
        if (FeederW2.equals("2 - 30.0mm.sq. THHN Cu. Wire")) {
            FeederWireSecond.setText("+ 1 - 8.0 mm.sq.");
            FeederWireFourth.setText("(G)In 32 mmø IMC PIPE");
        }
        //IF FEEDERWIRE IS 38 to 50  THE FEEDERWIRE SECOND 14.0 mm.sq.

        if (FeederW2.equals("2 - 38.0mm.sq. THHN Cu. Wire")) {
            FeederWireSecond.setText("+ 1 - 14.0 mm.sq.");
            FeederWireFourth.setText("(G)In 32 mmø IMC PIPE");
        }
        if (FeederW2.equals("2 - 50.0mm.sq. THHN Cu. Wire")) {
            FeederWireSecond.setText("+ 1 - 14.0 mm.sq.");
            FeederWireFourth.setText("(G)In 40 mmø IMC PIPE");
        }

        //IF FEEDERWIRE IS 60 80   =    22
        if (FeederW2.equals("2 - 60.0mm.sq. THHN Cu. Wire")) {
            FeederWireSecond.setText("+ 1 - 22.0 mm.sq.");
            FeederWireFourth.setText("(G)In 40 mmø IMC PIPE");
        }
        if (FeederW2.equals("2 - 80.0mm.sq. THHN Cu. Wire")) {
            FeederWireSecond.setText("+ 1 - 22.0 mm.sq.");
            FeederWireFourth.setText("(G)In 50 mmø IMC PIPE");
        }


        //IF FEEDERWIRE IS 100 to 175   = 30

        if (FeederW2.equals("2 - 100.0mm.sq. THHN Cu. Wire")) {
            FeederWireSecond.setText("+ 1 - 30.0 mm.sq.");
            FeederWireFourth.setText("(G)In 50 mmø IMC PIPE");
        }
        if (FeederW2.equals("2 - 125.0mm.sq. THHN Cu. Wire")) {
            FeederWireSecond.setText("+ 1 - 30.0 mm.sq.");
            FeederWireFourth.setText("(G)In 50 mmø IMC PIPE");
        }

        if (FeederW2.equals("2 - 150.0mm.sq. THHN Cu. Wire")) {
            FeederWireSecond.setText("+ 1 - 30.0 mm.sq.");
            FeederWireFourth.setText("(G)In 65 mmø IMC PIPE");
        }

        if (FeederW2.equals("2 - 175.0mm.sq. THHN Cu. Wire")) {
            FeederWireSecond.setText("+ 1 - 30.0 mm.sq.");
            FeederWireFourth.setText("(G)In 65 mmø IMC PIPE");
        }


        //IF FEEDERWIRE IS 200 to 325    50
        if (FeederW2.equals("2 - 200.0mm.sq. THHN Cu. Wire")) {
            FeederWireSecond.setText("+ 1 - 50.0 mm.sq.");
            FeederWireFourth.setText("(G)In 65 mmø IMC PIPE");
        }
        if (FeederW2.equals("2 - 250.0mm.sq. THHN Cu. Wire")) {
            FeederWireSecond.setText("+ 1 - 50.0 mm.sq.");
            FeederWireFourth.setText("(G)In 80  mmø IMC PIPE");
        }
        if (FeederW2.equals("2 - 325.0mm.sq. THHN Cu. Wire")) {
            FeederWireSecond.setText("+ 1 - 50.0 mm.sq.");
        }


        //IF FEEDERWIRE IS  375 to 500    600
        if (FeederW2.equals("2 - 375.0mm.sq. THHN Cu. Wire")) {
            FeederWireSecond.setText("+ 1 - 60.0 mm.sq.");
        }
        if (FeederW2.equals("2 - 400.0mm.sq. THHN Cu. Wire")) {
            FeederWireSecond.setText("+ 1 - 60.0 mm.sq.");
        }
        if (FeederW2.equals("2 - 500.0mm.sq. THHN Cu. Wire")) {
            FeederWireSecond.setText("+ 1 - 60.0 mm.sq.");
        }

        String Feeder2 = FeederWireSecond.getText().toString().trim();
        String Feeder3 = FeederWireFourth.getText().toString().trim();



//for the skeleton displays values
        if (FeederW2 != null) {
            String topText = "USE " + FeederW2 + Feeder2 + " THHN Cu. Wire " + Feeder3;
            String botText = "GEC:" + Feeder2 + " THHN Cu. Wire ";

            TextView[] topViews = {num4_top, num6_top, num8_top, num10_top, num12_top, num14_top, num16_top, num18_top, num20_top, num22_top, num24_top, num26_top, num28_top, num30_top};
            TextView[] botViews = {num4_bot, num6_bot, num8_bot, num10_bot, num12_bot, num14_bot, num16_bot, num18_bot, num20_bot, num22_bot, num24_bot, num26_bot, num28_bot, num30_bot};

            for (int i = 0; i < topViews.length; i++) {
                topViews[i].setText(topText);
                botViews[i].setText(botText);
            }
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
@Override
public  boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }
    /* --------------------------
     @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("WARNING!");
        builder.setMessage("You can't go back.");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing when OK is clicked
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    ---------------------- */


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int id = item.getItemId();
        if (id == R.id.add){
            onBackPressed();
        }
        /* -------------------------- PRINT ---------------------- */
        if (id == R.id.save) {
            // Array of choices
            final CharSequence[] paperSizes = {"A1", "A3", "20x30 inches"};

            // Create the AlertDialog Builder
            AlertDialog.Builder builder = new AlertDialog.Builder(Loadschedule.this);
            builder.setTitle("Choose paper size");

            // To be used to store index of selectedItem
            final int[] selectedItem = {-1};

            // 'checkedItem' is used to indicate which item is currently selected
            builder.setSingleChoiceItems(paperSizes, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    selectedItem[0] = which;
                }
            });

            // Set the Cancel button
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            // Set the 'Save Now' button
            builder.setPositiveButton("Save Now", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (selectedItem[0] != -1) {
                        String paperSize = (String) paperSizes[selectedItem[0]];

                        // Dismiss the paper size selection dialog
                        dialog.dismiss();

                        // Show progress dialog
                        ProgressDialog progressDialog = new ProgressDialog(Loadschedule.this);
                        progressDialog.setMessage("Saving PDF...");
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        // Handle PDF creation and saving in a background thread
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    // Get the current date and time
                                    String currentDateAndTime = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());

                                    // Create the filename
                                    String filename = "HELP_" + paperSize + "_" + currentDateAndTime + ".pdf";

                                    // Define your output stream here
                                    File pdf = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filename);
                                    FileOutputStream outputStream = new FileOutputStream(pdf);

                                    // Specify paper size for PDF
                                    Rectangle pageSize = null;
                                    switch (paperSize.toLowerCase()) {
                                        case "a1":
                                            pageSize = PageSize.A1;
                                            break;
                                        case "a3":
                                            pageSize = new Rectangle(841.68f, 1190.5f); // dimensions in points for 297 x 420 mm
                                            break;
                                        case "20x30 inches":
                                            pageSize = new Rectangle(1441f, 2163f); // dimensions in points for 508 x 762 mm
                                            break;
                                    }

                                    if (pageSize != null) {
                                        Document document = new Document(pageSize.rotate());
                                        PdfWriter.getInstance(document, outputStream);
                                        document.open();
                                        document.add(new Paragraph(paperSize)); // You can add anything to the PDF file here

                                        // Add image to PDF based on paper size
                                        try {
                                            // Get the image from drawable resources
                                            int id;
                                            if ("20x30 inches".equals(paperSize)) {
                                                id = getResources().getIdentifier("a20x30border", "drawable", getPackageName());
                                            } else {
                                                id = getResources().getIdentifier(paperSize.toLowerCase() + "border", "drawable", getPackageName());
                                            }
                                            if (id != 0) {
                                                BitmapFactory.Options options = new BitmapFactory.Options();
                                                options.inJustDecodeBounds = true;
                                                BitmapFactory.decodeResource(getResources(), id, options);

                                                // Compute the inSampleSize
                                                options.inSampleSize = calculateInSampleSize(options, 1000, 1000);
                                                options.inJustDecodeBounds = false;

                                                // Decode the image with calculated inSampleSize
                                                Bitmap bmp = BitmapFactory.decodeResource(getResources(), id, options);

                                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                                Image image = Image.getInstance(stream.toByteArray());

                                                if ("20x30 inches".equals(paperSize)) {
                                                    // Scale the image to cover the whole page and set position to bottom-left corner
                                                    float widthPercentage = (pageSize.getWidth() / image.getWidth()) * 150;
                                                    float heightPercentage = (pageSize.getHeight() / image.getHeight()) * 66;

                                                    image.scalePercent(widthPercentage, heightPercentage);
                                                    image.setAbsolutePosition(0, 0);
                                                } else {
                                                    // Scale the image to cover the whole page and set position to bottom-left corner
                                                    float widthPercentage = (pageSize.getWidth() / image.getWidth()) * 140;
                                                    float heightPercentage = (pageSize.getHeight() / image.getHeight()) * 70;

                                                    image.scalePercent(widthPercentage, heightPercentage);
                                                    image.setAbsolutePosition(0, 0);
                                                }

                                                // Add the image to the document
                                                document.add(image);
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        // Convert RelativeLayouts to Bitmaps and add them to the PDF
                                        RelativeLayout relativeLayout = findViewById(R.id.rela);
                                        RelativeLayout skeletonLayout = findViewById(R.id.skeleton);

                                        // Define the new position for the images
                                        float xPosition = 0; // Default position
                                        float yPosition = 0; // Default position

                                        // Calculate desired width and height for "rela" and "skeleton" RelativeLayouts based on paper size
                                        int desiredWidth;
                                        int desiredHeight;

                                        int skeletonDesiredWidth = 0;
                                        int skeletonDesiredHeight = 0;

                                        switch (paperSize.toLowerCase()) {
                                            case "a1":
                                                desiredWidth = (int) (pageSize.getWidth() - 20); // Adjust as needed
                                                desiredHeight = (int) (relativeLayout.getHeight() - 200); // Adjust as needed

                                                skeletonDesiredWidth = (int) (skeletonLayout.getWidth() / 2.5); // Adjust as needed
                                                skeletonDesiredHeight = (int) (skeletonLayout.getHeight() / 2.5); // Adjust as needed

                                                xPosition = 70; // Adjust as needed (horizontal position)
                                                yPosition = 850; // Adjust as needed (vertical position)
                                                break;
                                            case "a3":
                                                desiredWidth = (int) (pageSize.getWidth() - 20); // Adjust as needed
                                                desiredHeight = (int) (relativeLayout.getHeight() / 2.7); // Adjust as needed

                                                skeletonDesiredWidth = (int) (skeletonLayout.getWidth() / 5.1); // Adjust as needed
                                                skeletonDesiredHeight = (int) (skeletonLayout.getHeight() / 5.1); // Adjust as needed

                                                xPosition = 35; // Adjust as needed (horizontal position)
                                                yPosition = 425; // Adjust as needed (vertical position);

                                                break;
                                            case "20x30 inches":
                                                desiredWidth = (int) (pageSize.getWidth() - 20); // Adjust as needed
                                                desiredHeight = (int) (relativeLayout.getHeight() - 200); // Adjust as needed

                                                skeletonDesiredWidth = (int) (skeletonLayout.getWidth() / 3); // Adjust as needed
                                                skeletonDesiredHeight = (int) (skeletonLayout.getHeight() / 3); // Adjust as needed

                                                xPosition = 70; // Adjust as needed (horizontal position)
                                                yPosition = 810; // Adjust as needed (vertical position)
                                                break;
                                            default:
                                                // Default desired width and height
                                                desiredWidth = relativeLayout.getWidth() - 100; // Adjust as needed
                                                desiredHeight = relativeLayout.getHeight() - 100; // Adjust as needed
                                                break;
                                        }

                                        // Create a bitmap from the "rela" RelativeLayout
                                        Bitmap originalBitmap = Bitmap.createBitmap(relativeLayout.getWidth(), relativeLayout.getHeight(), Bitmap.Config.ARGB_8888);
                                        Canvas canvas = new Canvas(originalBitmap);
                                        relativeLayout.draw(canvas);

                                        // Scale down the bitmap for "rela" RelativeLayout
                                        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, desiredWidth, desiredHeight, true);

                                        // Add "rela" RelativeLayout as Bitmap to the PDF
                                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                        scaledBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                        Image relLayoutImage = Image.getInstance(stream.toByteArray());

                                        // Determine the total height of the page
                                        float pageHeight = pageSize.getHeight();

                                        // Calculate the y-coordinate relative to the top of the page
                                        float yPositionFromTop = pageHeight - yPosition;

                                        // Subtract the desired height of the element to determine the y-coordinate relative to the top
                                        float topEdgeYPosition = yPositionFromTop - desiredHeight;

                                        // Set the position of the element using the calculated y-coordinate relative to the top
                                        relLayoutImage.setAbsolutePosition(xPosition, topEdgeYPosition);

                                        // Add the "rela" RelativeLayout as Bitmap to the PDF document
                                        document.add(relLayoutImage);

                                        // Calculate the position of "skeleton" RelativeLayout
                                        float skeletonXPosition = xPosition + desiredWidth; // Right of "rela"
                                        float skeletonYPosition = topEdgeYPosition; // Same top position as "rela"

                                        // Create a bitmap from the "skeleton" RelativeLayout
                                        Bitmap skeletonOriginalBitmap = Bitmap.createBitmap(skeletonLayout.getWidth(), skeletonLayout.getHeight(), Bitmap.Config.ARGB_8888);
                                        Canvas skeletonCanvas = new Canvas(skeletonOriginalBitmap);
                                        skeletonLayout.draw(skeletonCanvas);

                                        // Scale down the bitmap for "skeleton" RelativeLayout
                                        Bitmap skeletonScaledBitmap = Bitmap.createScaledBitmap(skeletonOriginalBitmap, skeletonDesiredWidth, skeletonDesiredHeight, true);

                                        // Add "skeleton" RelativeLayout as Bitmap to the PDF
                                        ByteArrayOutputStream skeletonStream = new ByteArrayOutputStream();
                                        skeletonScaledBitmap.compress(Bitmap.CompressFormat.PNG, 100, skeletonStream);
                                        Image skeletonImage = Image.getInstance(skeletonStream.toByteArray());

                                        // Set the absolute position of the "skeleton" RelativeLayout
                                        skeletonImage.setAbsolutePosition(skeletonXPosition, skeletonYPosition - 10);

                                        // Add the "skeleton" RelativeLayout as Bitmap to the PDF document
                                        document.add(skeletonImage);

                                        // closing the document
                                        document.close();
                                        outputStream.close();

                                        // Show dialog to tell the user that the PDF has been saved
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                new AlertDialog.Builder(Loadschedule.this)
                                                        .setTitle("PDF Saved")
                                                        .setMessage("Your PDF file has been saved. Please check your Downloads folder.")
                                                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                dialog.dismiss();
                                                            }
                                                        })
                                                        .show();
                                            }
                                        });
                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getApplicationContext(), "Please select a paper size", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(), "Error occurred while saving PDF", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } finally {
                                    // Dismiss progress dialog
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog.dismiss();
                                        }
                                    });
                                }
                            }
                        }).start();
                    } else {
                        Toast.makeText(getApplicationContext(), "Please select a paper size", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            // Create and show the AlertDialog
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        /* -------------------------- END OF PRINT ---------------------- */



        if (id == R.id.nextLS){
            Toast.makeText(this, "next button", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.resetLS){
            Toast.makeText(this, "reset current load schedule button", Toast.LENGTH_SHORT).show();
            // Start the Inputing activity
            Intent intent = new Intent(this, Inputing.class);
            startActivity(intent);

        }
        if (id == R.id.discard) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Discard the project?");
            builder.setMessage("Are you sure you want to discard the project and return to home?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Disregard the load and return home
                    Toast.makeText(Loadschedule.this, "Project discarded", Toast.LENGTH_SHORT).show();
                     Intent intent = new Intent(Loadschedule.this, com.example.projectone.Menu.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                     startActivity(intent);
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing, close the dialog
                    dialog.dismiss();
                }
            });
            builder.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // ------- Helper Method ------------
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
