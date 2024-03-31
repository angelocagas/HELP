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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
import com.example.projectone.Databases.DatabaseClient;
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

import com.google.android.material.textfield.TextInputEditText;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.graphics.Bitmap;
import android.view.View;

public class Loadschedule extends AppCompatActivity {

    private double totalValue = 0.0;

    private double topOneAndTwoValue;

    private double topThreeAndFourValue;
    private double underOneAndTwoValue;

    private double underThreeAndFourValue;

    private RecyclerView recyclerView;
    private DataAdapter dataAdapter;
    private RelativeLayout rootLayout;
    private float mScaleFactor = 1.0f;
    private ScaleGestureDetector mScaleGestureDetector;
    private GestureDetector mGestureDetector;
    private float mLastTouchX;
    private float mLastTouchY;
    /* ------------ for next ls --------- */
    private int currentTableCount = 0;
    private DatabaseHelper databaseHelper;
    DecimalFormat df = new DecimalFormat("#.##");
    String lastDemand = null;
    private ProjectDAO projectDAO;
    DatabaseHelper helper;
    List<ProjectTable> projectTableList;

    DecimalFormat decimalFormat = new DecimalFormat("#0.00");
    TextView Pipetype, demandfactor1, demandfactor2, num8_4, num6_2, num6_3, num6_4, num6_5, num6_6, num8_1, num8_2, num8_3, num8_5, num8_6, num8_7, num8_8, num10_1, num10_2, num10_3, num10_4, num10_5, num10_6, num10_7, num10_8, num10_9, num10_10, num6_1, num12_1, num12_2, num12_3, num12_4, num12_5, num12_6, num12_7, num12_8, num12_9, num12_10, num12_11, num12_12, num14_1, num14_2, num14_3, num14_4, num14_5, num14_6, num14_7, num14_8, num14_9, num14_10, num14_11, num14_12, num14_13, num14_14, num16_1, num16_2, num16_3, num16_4, num16_5, num16_6, num16_7, num16_8, num16_9, num16_10, num16_11, num16_12, num16_13, num16_14, num16_15, num16_16, num18_1, num18_2, num18_3, num18_4, num18_5, num18_6, num18_7, num18_8, num18_9, num18_10, num18_11, num18_12, num18_13, num18_14, num18_15, num18_16, num18_17, num18_18, num20_1, num20_2, num20_3, num20_4, num20_5, num20_6, num20_7, num20_8, num20_9, num20_10, num20_11, num20_12, num20_13, num20_14, num20_15, num20_16, num20_17, num20_18, num20_19, num20_20, num22_1, num22_2, num22_3, num22_4, num22_5, num22_6, num22_7, num22_8, num22_9, num22_10, num22_11, num22_12, num22_13, num22_14, num22_15, num22_16, num22_17, num22_18, num22_19, num22_20, num22_21, num22_22, num24_1, num24_2, num24_3, num24_4, num24_5, num24_6, num24_7, num24_8, num24_9, num24_10, num24_11, num24_12, num24_13, num24_14, num24_15, num24_16, num24_17, num24_18, num24_19, num24_20, num24_21, num24_22, num24_23, num24_24, num26_1, num26_2, num26_3, num26_4, num26_5, num26_6, num26_7, num26_8, num26_9, num26_10, num26_11, num26_12, num26_13, num26_14, num26_15, num26_16, num26_17, num26_18, num26_19, num26_20, num26_21, num26_22, num26_23, num26_24, num26_25, num26_26, num28_1, num28_2, num28_3, num28_4, num28_5, num28_6, num28_7, num28_8, num28_9, num28_10, num28_11, num28_12, num28_13, num28_14, num28_15, num28_16, num28_17, num28_18, num28_19, num28_20, num28_21, num28_22, num28_23, num28_24, num28_25, num28_26, num28_27, num28_28, num30_1, num30_2, num30_3, num30_4, num30_5, num30_6, num30_7, num30_8, num30_9, num30_10, num30_11, num30_12, num30_13, num30_14, num30_15, num30_16, num30_17, num30_18, num30_19, num30_20, num30_21, num30_22, num30_23, num30_24, num30_25, num30_26, num30_27, num30_28, num30_29, num30_30, num4_a, num6_a, num8_a, num10_a, num12_a, num14_a, num16_a, num18_a, num20_a, num22_a, num24_a, num26_a, num28_a, num30_a, num4_top, num6_top, num8_top, num10_top, num12_top, num14_top, num16_top, num18_top, num20_top, num22_top, num24_top, num26_top, num28_top, num30_top, num6_bot, num8_bot, num10_bot, num12_bot, num14_bot, num16_bot, num18_bot, num20_bot, num22_bot, num24_bot, num26_bot, num28_bot, num30_bot, num4_bot, num4_1, num4_2, num4_3, num4_4, num4_a1, num4_a2, num4_a3, num4_a4, num6_a1, num6_a2, num6_a3, num6_a4, num6_a5, num6_a6, num8_a1, num8_a2, num8_a3, num8_a4, num8_a5, num8_a6, num8_a7, num8_a8, num10_a1, num10_a2, num10_a3, num10_a4, num10_a5, num10_a6, num10_a7, num10_a8, num10_a9, num10_a10, num12_a1, num12_a2, num12_a3, num12_a4, num12_a5, num12_a6, num12_a7, num12_a8, num12_a9, num12_a10, num12_a11, num12_a12, num14_a1, num14_a2, num14_a3, num14_a4, num14_a5, num14_a6, num14_a7, num14_a8, num14_a9, num14_a10, num14_a11, num14_a12, num14_a13, num14_a14, num16_a1, num16_a2, num16_a3, num16_a4, num16_a5, num16_a6, num16_a7, num16_a8, num16_a9, num16_a10, num16_a11, num16_a12, num16_a13, num16_a14, num16_a15, num16_a16, num18_a1, num18_a2, num18_a3, num18_a4, num18_a5, num18_a6, num18_a7, num18_a8, num18_a9, num18_a10, num18_a11, num18_a12, num18_a13, num18_a14, num18_a15, num18_a16, num18_a17, num18_a18, num20_a1, num20_a2, num20_a3, num20_a4, num20_a5, num20_a6, num20_a7, num20_a8, num20_a9, num20_a10, num20_a11, num20_a12, num20_a13, num20_a14, num20_a15, num20_a16, num20_a17, num20_a18, num20_a19, num20_a20, num22_a1, num22_a2, num22_a3, num22_a4, num22_a5, num22_a6, num22_a7, num22_a8, num22_a9, num22_a10, num22_a11, num22_a12, num22_a13, num22_a14, num22_a15, num22_a16, num22_a17, num22_a18, num22_a19, num22_a20, num22_a21, num22_a22, num24_a1, num24_a2, num24_a3, num24_a4, num24_a5, num24_a6, num24_a7, num24_a8, num24_a9, num24_a10, num24_a11, num24_a12, num24_a13, num24_a14, num24_a15, num24_a16, num24_a17, num24_a18, num24_a19, num24_a20, num24_a21, num24_a22, num24_a23, num24_a24, num26_a1, num26_a2, num26_a3, num26_a4, num26_a5, num26_a6, num26_a7, num26_a8, num26_a9, num26_a10, num26_a11, num26_a12, num26_a13, num26_a14, num26_a15, num26_a16, num26_a17, num26_a18, num26_a19, num26_a20, num26_a21, num26_a22, num26_a23, num26_a24, num26_a25, num26_a26, num28_a1, num28_a2, num28_a3, num28_a4, num28_a5, num28_a6, num28_a7, num28_a8, num28_a9, num28_a10, num28_a11, num28_a12, num28_a13, num28_a14, num28_a15, num28_a16, num28_a17, num28_a18, num28_a19, num28_a20, num28_a21, num28_a22, num28_a23, num28_a24, num28_a25, num28_a26, num28_a27, num28_a28, num30_a1, num30_a2, num30_a3, num30_a4, num30_a5, num30_a6, num30_a7, num30_a8, num30_a9, num30_a10, num30_a11, num30_a12, num30_a13, num30_a14, num30_a15, num30_a16, num30_a17, num30_a18, num30_a19, num30_a20, num30_a21, num30_a22, num30_a23, num30_a24, num30_a25, num30_a26, num30_a27, num30_a28, num30_a29, num30_a30, CTRtv, FEEDERWIREPASS, MAINWIREPASS, LAWEHIGHB, SAVEHIGHB, LAWEHIGHA, SAVEHIGHA, LAWEA, SaveA, UpdatedMainWire, FeederSize, FeederWireSecond, FeederWireThird, FeederWireFourth, FeederWire, MainWire, totalone, totalVATextView, totalATextView, HighestA, HighestB, TotalB, UnderOneAndTwo, UnderThreeAndFour, TotalUnder, TopOneAndTwo, TopThreeAndFour, TotalTop;

    RelativeLayout aaaa, RS30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        databaseHelper = new DatabaseHelper(this);




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadschedule);


        helper = DatabaseHelper.getInstance(this);
        recyclerView = findViewById(R.id.recylcer_view);
        helper.getAllProjectData();
        totalVATextView = findViewById(R.id.totalVA);
        totalATextView = findViewById(R.id.totalA);
        HighestA = findViewById(R.id.HIGHA);
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
        FeederSize = findViewById(R.id.FeederSize);
        UpdatedMainWire = findViewById(R.id.UpdatedMainWire);
        SaveA = findViewById(R.id.saveA);
        MAINWIREPASS = findViewById(R.id.MainWirePass);
        FEEDERWIREPASS = findViewById(R.id.FeederWireTypePass);
        CTRtv = findViewById(R.id.CTRtv);
        demandfactor1 = findViewById(R.id.demandfactor1);
        demandfactor2 = findViewById(R.id.demandfactor2);
        Pipetype = findViewById(R.id.Pipetype);


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
        num8_a = findViewById(R.id.num8_a);
        num10_a = findViewById(R.id.num10_a);
        num12_a = findViewById(R.id.num12_a);
        num14_a = findViewById(R.id.num14_a);
        num16_a = findViewById(R.id.num16_a);
        num18_a = findViewById(R.id.num18_a);
        num20_a = findViewById(R.id.num20_a);
        num22_a = findViewById(R.id.num22_a);
        num24_a = findViewById(R.id.num24_a);
        num26_a = findViewById(R.id.num26_a);
        num28_a = findViewById(R.id.num28_a);
        num30_a = findViewById(R.id.num30_a);


        num4_top = findViewById(R.id.num4_top);
        num6_top = findViewById(R.id.num6_top);
        num8_top = findViewById(R.id.num8_top);
        num10_top = findViewById(R.id.num10_top);
        num12_top = findViewById(R.id.num12_top);
        num14_top = findViewById(R.id.num14_top);
        num16_top = findViewById(R.id.num16_top);
        num18_top = findViewById(R.id.num18_top);
        num20_top = findViewById(R.id.num20_top);
        num22_top = findViewById(R.id.num22_top);
        num24_top = findViewById(R.id.num24_top);
        num26_top = findViewById(R.id.num26_top);
        num28_top = findViewById(R.id.num28_top);
        num30_top = findViewById(R.id.num30_top);

        num4_bot = findViewById(R.id.num4_bot);
        num6_bot = findViewById(R.id.num6_bot);
        num8_bot = findViewById(R.id.num8_bot);
        num10_bot = findViewById(R.id.num10_bot);
        num12_bot = findViewById(R.id.num12_bot);
        num14_bot = findViewById(R.id.num14_bot);
        num16_bot = findViewById(R.id.num16_bot);
        num18_bot = findViewById(R.id.num18_bot);
        num20_bot = findViewById(R.id.num20_bot);
        num22_bot = findViewById(R.id.num22_bot);
        num24_bot = findViewById(R.id.num24_bot);
        num26_bot = findViewById(R.id.num26_bot);
        num28_bot = findViewById(R.id.num28_bot);
        num30_bot = findViewById(R.id.num30_bot);

        RelativeLayout RS4 = findViewById(R.id.RS4);
        RelativeLayout RS6 = findViewById(R.id.RS6);
        RelativeLayout RS8 = findViewById(R.id.RS8);
        RelativeLayout RS10 = findViewById(R.id.RS10);
        RelativeLayout RS12 = findViewById(R.id.RS12);
        RelativeLayout RS14 = findViewById(R.id.RS14);
        RelativeLayout RS16 = findViewById(R.id.RS16);
        RelativeLayout RS18 = findViewById(R.id.RS18);
        RelativeLayout RS20 = findViewById(R.id.RS20);
        RelativeLayout RS22 = findViewById(R.id.RS22);
        RelativeLayout RS24 = findViewById(R.id.RS24);
        RelativeLayout RS26 = findViewById(R.id.RS26);
        RelativeLayout RS28 = findViewById(R.id.RS28);
        RelativeLayout RS30 = findViewById(R.id.RS30);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SAVEHIGHA = findViewById(R.id.SAVEHIGHA);
        LAWEHIGHA = findViewById(R.id.LAWEHIGHA);
        SAVEHIGHB = findViewById(R.id.SAVEHIGHB);
        LAWEHIGHB = findViewById(R.id.LAWEHIGHB);

        //updated
        SharedPreferences sharedPreferences = getSharedPreferences("SharePref", MODE_PRIVATE);

        String UPDMT = sharedPreferences.getString("UMT", "");
        String FDW = sharedPreferences.getString("UFWT", "");
        String TT = sharedPreferences.getString("TT", "");
        String YY = sharedPreferences.getString("YY", "");



        Intent intent = getIntent();


        final DatabaseHelper dbHelper = new DatabaseHelper(this);

//HIGHEST A
        startFindingHighestAValues();

        //TOTAL A
        dbHelper.getAllAsAndStartNextActivity(Loadschedule.this, new DatabaseHelper.OnItemsLoadedListener() {
            @Override
            public void onItemsLoaded(List<String> items) {
                // Reset total value before calculating
                totalValue = 0.0;

                // Iterate through the items and sum them up
                for (String item : items) {
                    try {
                        double value = Double.parseDouble(item);
                        totalValue += value;
                    } catch (NumberFormatException e) {
                        // Handle parsing errors if any
                        e.printStackTrace();
                    }
                }

                // Format the total to display with two decimal places
                String formattedTotalA = String.format("%.2f", totalValue);
                // Display a toast with the formatted total and a message
                totalATextView.setText(formattedTotalA);
                totalone.setText(formattedTotalA);
                TotalB.setText(formattedTotalA);

                // Call the interface method to pass the total value outside of the onItemsLoaded method
                onTotalValueCalculated(totalValue);
            }
        });



//TOTAL VA
        dbHelper.getAllVAsAndStartNextActivity(Loadschedule.this, new DatabaseHelper.OnItemsLoadedListener() {
            @Override
            public void onItemsLoaded(List<String> items) {
                double total = 0.0;

                // Iterate through the items and sum them up
                for (String item : items) {
                    try {
                        double value = Double.parseDouble(item);
                        total += value;
                    } catch (NumberFormatException e) {
                        // Handle parsing errors if any
                        e.printStackTrace();
                    }
                }

                // Format the total without decimal places
                String formattedTotalVA = String.format("%.0f", total);
                // Display a toast with the formatted total and a message
                totalVATextView.setText(formattedTotalVA);
            }
        });


//DISPLAY SA SKELETON NAME
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
     //FOR at DISPLAY SKEL
        dbHelper.getAllATsAndStartNextActivity(Loadschedule.this, new DatabaseHelper.OnItemsLoadedListener() {
            @Override
            public void onItemsLoaded(List<String> ASList) {
                if (ASList != null) {
                    switch (ASList.size()) {
                        case 4:
                            num4_a1.setText(ASList.get(0));
                            num4_a2.setText(ASList.get(1));
                            num4_a3.setText(ASList.get(2));
                            num4_a4.setText(ASList.get(3));
                            break;
                        case 6:
                            num6_a1.setText(ASList.get(0));
                            num6_a2.setText(ASList.get(1));
                            num6_a3.setText(ASList.get(2));
                            num6_a4.setText(ASList.get(3));
                            num6_a5.setText(ASList.get(4));
                            num6_a6.setText(ASList.get(5));
                            break;
                        case 8:
                            num8_a1.setText(ASList.get(0));
                            num8_a2.setText(ASList.get(1));
                            num8_a3.setText(ASList.get(2));
                            num8_a4.setText(ASList.get(3));
                            num8_a5.setText(ASList.get(4));
                            num8_a6.setText(ASList.get(5));
                            num8_a7.setText(ASList.get(6));
                            num8_a8.setText(ASList.get(7));
                            break;
                        case 10:
                            num10_a1.setText(ASList.get(0));
                            num10_a2.setText(ASList.get(1));
                            num10_a3.setText(ASList.get(2));
                            num10_a4.setText(ASList.get(3));
                            num10_a5.setText(ASList.get(4));
                            num10_a6.setText(ASList.get(5));
                            num10_a7.setText(ASList.get(6));
                            num10_a8.setText(ASList.get(7));
                            num10_a9.setText(ASList.get(8));
                            num10_a10.setText(ASList.get(9));
                            break;

                        case 12:
                            num12_a1.setText(ASList.get(0));
                            num12_a2.setText(ASList.get(1));
                            num12_a3.setText(ASList.get(2));
                            num12_a4.setText(ASList.get(3));
                            num12_a5.setText(ASList.get(4));
                            num12_a6.setText(ASList.get(5));
                            num12_a7.setText(ASList.get(6));
                            num12_a8.setText(ASList.get(7));
                            num12_a9.setText(ASList.get(8));
                            num12_a10.setText(ASList.get(9));
                            num12_a11.setText(ASList.get(10));
                            num12_a12.setText(ASList.get(11));


                            break;
                        case 14:
                            num14_a1.setText(ASList.get(0));
                            num14_a2.setText(ASList.get(1));
                            num14_a3.setText(ASList.get(2));
                            num14_a4.setText(ASList.get(3));
                            num14_a5.setText(ASList.get(4));
                            num14_a6.setText(ASList.get(5));
                            num14_a7.setText(ASList.get(6));
                            num14_a8.setText(ASList.get(7));
                            num14_a9.setText(ASList.get(8));
                            num14_a10.setText(ASList.get(9));
                            num14_a11.setText(ASList.get(10));
                            num14_a12.setText(ASList.get(11));
                            num14_a13.setText(ASList.get(11));
                            num14_a14.setText(ASList.get(13));
                            break;
                        case 16:
                            num16_a1.setText(ASList.get(0));
                            num16_a2.setText(ASList.get(1));
                            num16_a3.setText(ASList.get(2));
                            num16_a4.setText(ASList.get(3));
                            num16_a5.setText(ASList.get(4));
                            num16_a6.setText(ASList.get(5));
                            num16_a7.setText(ASList.get(6));
                            num16_a8.setText(ASList.get(7));
                            num16_a9.setText(ASList.get(8));
                            num16_a10.setText(ASList.get(9));
                            num16_a11.setText(ASList.get(10));
                            num16_a12.setText(ASList.get(11));
                            num16_a13.setText(ASList.get(11));
                            num16_a14.setText(ASList.get(13));
                            num16_a15.setText(ASList.get(14));
                            num16_a16.setText(ASList.get(15));
                            break;
                        case 18:
                            num18_a1.setText(ASList.get(0));
                            num18_a2.setText(ASList.get(1));
                            num18_a3.setText(ASList.get(2));
                            num18_a4.setText(ASList.get(3));
                            num18_a5.setText(ASList.get(4));
                            num18_a6.setText(ASList.get(5));
                            num18_a7.setText(ASList.get(6));
                            num18_a8.setText(ASList.get(7));
                            num18_a9.setText(ASList.get(8));
                            num18_a10.setText(ASList.get(9));
                            num18_a11.setText(ASList.get(10));
                            num18_a12.setText(ASList.get(11));
                            num18_a13.setText(ASList.get(11));
                            num18_a14.setText(ASList.get(13));
                            num18_a15.setText(ASList.get(14));
                            num18_a16.setText(ASList.get(15));
                            num18_a17.setText(ASList.get(16));
                            num18_a18.setText(ASList.get(17));

                            break;
                        case 20:
                            num20_a1.setText(ASList.get(0));
                            num20_a2.setText(ASList.get(1));
                            num20_a3.setText(ASList.get(2));
                            num20_a4.setText(ASList.get(3));
                            num20_a5.setText(ASList.get(4));
                            num20_a6.setText(ASList.get(5));
                            num20_a7.setText(ASList.get(6));
                            num20_a8.setText(ASList.get(7));
                            num20_a9.setText(ASList.get(8));
                            num20_a10.setText(ASList.get(9));
                            num20_a11.setText(ASList.get(10));
                            num20_a12.setText(ASList.get(11));
                            num20_a13.setText(ASList.get(11));
                            num20_a14.setText(ASList.get(13));
                            num20_a15.setText(ASList.get(14));
                            num20_a16.setText(ASList.get(15));
                            num20_a17.setText(ASList.get(16));
                            num20_a18.setText(ASList.get(17));
                            num20_a19.setText(ASList.get(18));
                            num20_a20.setText(ASList.get(19));


                            break;
                        case 22:
                            num22_a1.setText(ASList.get(0));
                            num22_a2.setText(ASList.get(1));
                            num22_a3.setText(ASList.get(2));
                            num22_a4.setText(ASList.get(3));
                            num22_a5.setText(ASList.get(4));
                            num22_a6.setText(ASList.get(5));
                            num22_a7.setText(ASList.get(6));
                            num22_a8.setText(ASList.get(7));
                            num22_a9.setText(ASList.get(8));
                            num22_a10.setText(ASList.get(9));
                            num22_a11.setText(ASList.get(10));
                            num22_a12.setText(ASList.get(11));
                            num22_a13.setText(ASList.get(11));
                            num22_a14.setText(ASList.get(13));
                            num22_a15.setText(ASList.get(14));
                            num22_a16.setText(ASList.get(15));
                            num22_a17.setText(ASList.get(16));
                            num22_a18.setText(ASList.get(17));
                            num22_a19.setText(ASList.get(18));
                            num22_a20.setText(ASList.get(19));
                            num22_a21.setText(ASList.get(20));
                            num22_a22.setText(ASList.get(21));

                            break;
                        case 24:
                            num24_a1.setText(ASList.get(0));
                            num24_a2.setText(ASList.get(1));
                            num24_a3.setText(ASList.get(2));
                            num24_a4.setText(ASList.get(3));
                            num24_a5.setText(ASList.get(4));
                            num24_a6.setText(ASList.get(5));
                            num24_a7.setText(ASList.get(6));
                            num24_a8.setText(ASList.get(7));
                            num24_a9.setText(ASList.get(8));
                            num24_a10.setText(ASList.get(9));
                            num24_a11.setText(ASList.get(10));
                            num24_a12.setText(ASList.get(11));
                            num24_a13.setText(ASList.get(11));
                            num24_a14.setText(ASList.get(13));
                            num24_a15.setText(ASList.get(14));
                            num24_a16.setText(ASList.get(15));
                            num24_a17.setText(ASList.get(16));
                            num24_a18.setText(ASList.get(17));
                            num24_a19.setText(ASList.get(18));
                            num24_a20.setText(ASList.get(19));
                            num24_a21.setText(ASList.get(20));
                            num24_a22.setText(ASList.get(21));
                            num24_a23.setText(ASList.get(22));
                            num24_a24.setText(ASList.get(23));

                            break;
                        case 26:
                            num26_a1.setText(ASList.get(0));
                            num26_a2.setText(ASList.get(1));
                            num26_a3.setText(ASList.get(2));
                            num26_a4.setText(ASList.get(3));
                            num26_a5.setText(ASList.get(4));
                            num26_a6.setText(ASList.get(5));
                            num26_a7.setText(ASList.get(6));
                            num26_a8.setText(ASList.get(7));
                            num26_a9.setText(ASList.get(8));
                            num26_a10.setText(ASList.get(9));
                            num26_a11.setText(ASList.get(10));
                            num26_a12.setText(ASList.get(11));
                            num26_a13.setText(ASList.get(11));
                            num26_a14.setText(ASList.get(13));
                            num26_a15.setText(ASList.get(14));
                            num26_a16.setText(ASList.get(15));
                            num26_a17.setText(ASList.get(16));
                            num26_a18.setText(ASList.get(17));
                            num26_a19.setText(ASList.get(18));
                            num26_a20.setText(ASList.get(19));
                            num26_a21.setText(ASList.get(20));
                            num26_a22.setText(ASList.get(21));
                            num26_a23.setText(ASList.get(22));
                            num26_a24.setText(ASList.get(23));
                            num26_a25.setText(ASList.get(24));
                            num26_a26.setText(ASList.get(25));

                            break;
                        case 28:
                            num28_a1.setText(ASList.get(0));
                            num28_a2.setText(ASList.get(1));
                            num28_a3.setText(ASList.get(2));
                            num28_a4.setText(ASList.get(3));
                            num28_a5.setText(ASList.get(4));
                            num28_a6.setText(ASList.get(5));
                            num28_a7.setText(ASList.get(6));
                            num28_a8.setText(ASList.get(7));
                            num28_a9.setText(ASList.get(8));
                            num28_a10.setText(ASList.get(9));
                            num28_a11.setText(ASList.get(10));
                            num28_a12.setText(ASList.get(11));
                            num28_a13.setText(ASList.get(11));
                            num28_a14.setText(ASList.get(13));
                            num28_a15.setText(ASList.get(14));
                            num28_a16.setText(ASList.get(15));
                            num28_a17.setText(ASList.get(16));
                            num28_a18.setText(ASList.get(17));
                            num28_a19.setText(ASList.get(18));
                            num28_a20.setText(ASList.get(19));
                            num28_a21.setText(ASList.get(20));
                            num28_a22.setText(ASList.get(21));
                            num28_a23.setText(ASList.get(22));
                            num28_a24.setText(ASList.get(23));
                            num28_a25.setText(ASList.get(24));
                            num28_a26.setText(ASList.get(25));
                            num28_a27.setText(ASList.get(26));
                            num28_a28.setText(ASList.get(27));

                            break;
                        case 30:
                            num30_a1.setText(ASList.get(0));
                            num30_a2.setText(ASList.get(1));
                            num30_a3.setText(ASList.get(2));
                            num30_a4.setText(ASList.get(3));
                            num30_a5.setText(ASList.get(4));
                            num30_a6.setText(ASList.get(5));
                            num30_a7.setText(ASList.get(6));
                            num30_a8.setText(ASList.get(7));
                            num30_a9.setText(ASList.get(8));
                            num30_a10.setText(ASList.get(9));
                            num30_a11.setText(ASList.get(10));
                            num30_a12.setText(ASList.get(11));
                            num30_a13.setText(ASList.get(11));
                            num30_a14.setText(ASList.get(13));
                            num30_a15.setText(ASList.get(14));
                            num30_a16.setText(ASList.get(15));
                            num30_a17.setText(ASList.get(16));
                            num30_a18.setText(ASList.get(17));
                            num30_a19.setText(ASList.get(18));
                            num30_a20.setText(ASList.get(19));
                            num30_a21.setText(ASList.get(20));
                            num30_a22.setText(ASList.get(21));
                            num30_a23.setText(ASList.get(22));
                            num30_a24.setText(ASList.get(23));
                            num30_a25.setText(ASList.get(24));
                            num30_a26.setText(ASList.get(25));
                            num30_a27.setText(ASList.get(26));
                            num30_a28.setText(ASList.get(27));
                            num30_a29.setText(ASList.get(28));
                            num30_a30.setText(ASList.get(29));
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
            public void onItemsLoaded(List<String> TSList) {
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
            String totalA = intent.getStringExtra("TOTALA");
            String HIGHA = intent.getStringExtra("HIGHA");
            String Wiretype = intent.getStringExtra("WFG");
            String PVCUPDATED = intent.getStringExtra("NUMPVC");
            String WFGTYPE = intent.getStringExtra("WFG");
            // Retrieving demand and mainpipe values from Intent
            String demand = intent.getStringExtra("DEMAND");
            String mainpipe = intent.getStringExtra("mainpipo");

// Retrieving saved demand value from SharedPreferences
            SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            String savedDemand = preferences.getString("demand", null);

            // Retrieving saved mainpipe value from SharedPreferences
            String savedMainpipe = preferences.getString("mainpipe", null);

// Using the retrieved demand value
            if (demand != null) {
                // If demand is provided in the intent, use it
                demandfactor1.setText(demand);
                demandfactor2.setText(demand);
            } else if (savedDemand != null) {
                // If demand is not provided in the intent but was previously saved in SharedPreferences, use the saved value
                demandfactor1.setText(savedDemand);
                demandfactor2.setText(savedDemand);
            } else {
                // If neither the demand is provided in the intent nor a saved value is available, set default values or handle the case as needed
                demandfactor1.setText("0.80");
                demandfactor2.setText("0.80");
            }

// Using the retrieved mainpipe value
            if (mainpipe != null) {
                // If mainpipe is provided in the intent, use it
                Pipetype.setText(mainpipe);
            } else if (savedMainpipe != null) {
                // If mainpipe is not provided in the intent but was previously saved in SharedPreferences, use the saved value
                Pipetype.setText(savedMainpipe);
            } else {
                // If neither the mainpipe is provided in the intent nor a saved value is available, set a default value or handle the case as needed
                Pipetype.setText("IMC PIPE");
            }

            if (totalA == null && HIGHA == null) {
                FeederSize.setText(TT);
                UpdatedMainWire.setText(YY);
                MainWire.setText(UPDMT);






            }
            // Assuming lastDemand is a class-level variable to store the last non-null demand value




            if (PVCUPDATED != null) {
                FeederWireFourth.setText(PVCUPDATED);
            }

        }


        double demand = Double.parseDouble(demandfactor1.getText().toString());

        double totalOneValue = totalValue;


        double topOneAndTwoValue = totalOneValue * demand;


        /* double highestAValue = Double.parseDouble(HighestA.getText().toString());
        double topThreeAndFourValue = highestAValue * 0.25;
        topThreeAndFourValue = Math.round(topThreeAndFourValue * 100.0) / 100.0;
        String formattedResulttwo = decimalFormat.format(topThreeAndFourValue);
        TopThreeAndFour.setText(formattedResulttwo); */

       /* double totalTopValue = topOneAndTwoValue + topThreeAndFourValue;
        totalTopValue = Math.round(totalTopValue * 100.0) / 100.0;
        String formattedResultFinalTop = decimalFormat.format(totalTopValue);
        TotalTop.setText(formattedResultFinalTop);  */

       /* double totalBValue = Double.parseDouble(TotalB.getText().toString());
        double underOneAndTwoValue = totalBValue * demand;
        underOneAndTwoValue = Math.round(underOneAndTwoValue * 100.0) / 100.0;
        String formattedResultUnderOneAndTwo = decimalFormat.format(underOneAndTwoValue);
        UnderOneAndTwo.setText(formattedResultUnderOneAndTwo);

        double highestBValue = Double.parseDouble(HighestB.getText().toString());
        double underThreeAndFourValue = highestBValue * 1.5;
        underThreeAndFourValue = Math.round(underThreeAndFourValue * 100.0) / 100.0;
        String formattedResultUnderThreeAndFour = decimalFormat.format(underThreeAndFourValue);
        UnderThreeAndFour.setText(formattedResultUnderThreeAndFour);*/

        /*double totalUnderValue = underOneAndTwoValue + underThreeAndFourValue;
        totalUnderValue = Math.round(totalUnderValue * 100.0) / 100.0;
        String formattedResultTotalUnder = decimalFormat.format(totalUnderValue);
        TotalUnder.setText(formattedResultTotalUnder);*/




        //display for skel

        String fullText = MainWire.getText().toString();
        String desiredSubstring;

// Assuming "175 AT" always appears at the beginning of the text and followed by a comma
        int commaIndex = fullText.indexOf(',');
        if (commaIndex != -1) {
            desiredSubstring = fullText.substring(0, commaIndex);
        } else {
            // If there's no comma, simply take the whole text
            desiredSubstring = fullText;
        }

        TextView[] numViews = {num4_a, num6_a, num8_a, num10_a, num12_a, num14_a, num16_a, num18_a, num20_a, num22_a, num24_a, num26_a, num28_a, num30_a};

        for (int i = 0; i < numViews.length; i++) {
            numViews[i].setText(desiredSubstring + ", 2P ");
        }

//first update


        FeederWire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inflate the layout containing the AutoCompleteTextView
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_feeder_wire, null);

                // Find the AutoCompleteTextView in the inflated layout
                AutoCompleteTextView autoCompleteTextView = dialogView.findViewById(R.id.feeder);

                // Define your list of feed options
                String[] feedOptions = new String[]{"2.0", "3.5", "5.5", "8.0", "14", "22", "30", "38", "50", "60", "80"};

                // Create ArrayAdapter to hold suggestions
                ArrayAdapter<String> adapter = new ArrayAdapter<>(Loadschedule.this, android.R.layout.simple_dropdown_item_1line, feedOptions);

                // Set the adapter to AutoCompleteTextView
                autoCompleteTextView.setAdapter(adapter);

                AlertDialog.Builder builder = new AlertDialog.Builder(Loadschedule.this);
                builder.setView(dialogView)
                        .setTitle("Update Feeder Wire")
                        .setPositiveButton("OK", null) // Set null initially, we'll enable/disable it later
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked Cancel button, dismiss the dialog
                                dialog.dismiss();
                            }
                        });

                final AlertDialog dialog = builder.create();
                dialog.show();

                // Get the button from the dialog after it's shown
                final Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

                // Set initial state for the OK button
                positiveButton.setEnabled(false);

                // Set a listener to enable/disable the OK button based on text input
                autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // Enable OK button when an item is selected
                        positiveButton.setEnabled(true);
                    }
                });

                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Get the selected item from the AutoCompleteTextView
                        String selectedFeederWire = autoCompleteTextView.getText().toString();

                        // Update the FeederWire with the new text
                        FeederWire.setText("2 - " + selectedFeederWire + "mm.sq. THHN Cu. Wire ");

                        String topText = "USE " + FeederWire.getText().toString() + FeederWireSecond.getText().toString() + FeederWireFourth.getText().toString() + " " + Pipetype.getText().toString();

                        TextView[] topViews = {num4_top, num6_top, num8_top, num10_top, num12_top, num14_top, num16_top, num18_top, num20_top, num22_top, num24_top, num26_top, num28_top, num30_top};

                        for (int i = 0; i < topViews.length; i++) {
                            topViews[i].setText(topText);
                        }

                        // Dismiss the dialog after OK button is clicked
                        dialog.dismiss();
                    }
                });
            }
        });


//second feeder wire update
        FeederWireSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inflate the layout containing the AutoCompleteTextView
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_pipe_wire, null);

                // Find the AutoCompleteTextView in the inflated layout
                AutoCompleteTextView autoCompleteTextView = dialogView.findViewById(R.id.auto_complete_pipe_wire);

                // Define your list of pipe wire options
                String[] pipeWireOptions = new String[]{"8", "14", "22", "30", "50", "60"};

                // Create ArrayAdapter to hold options
                ArrayAdapter<String> adapter = new ArrayAdapter<>(Loadschedule.this, android.R.layout.simple_dropdown_item_1line, pipeWireOptions);
                autoCompleteTextView.setAdapter(adapter);

                AlertDialog.Builder builder = new AlertDialog.Builder(Loadschedule.this);
                builder.setView(dialogView)
                        .setTitle("Update Feeder Wire")
                        .setPositiveButton("OK", null) // Set null initially, we'll enable/disable it later
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked Cancel button, dismiss the dialog
                                dialog.dismiss();
                            }
                        });

                final AlertDialog dialog = builder.create();
                dialog.show();

                // Get the button from the dialog after it's shown
                final Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

                // Set initial state for the OK button
                positiveButton.setEnabled(false);

                // Set a listener to enable/disable the OK button based on text input
                autoCompleteTextView.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        // Enable/disable OK button based on whether there is text entered
                        positiveButton.setEnabled(s.toString().trim().length() > 0);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });

                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Get the text from the AutoCompleteTextView
                        String selectedPipeWire = autoCompleteTextView.getText().toString();
                        // Update the FeederWire with the new text
                        String newTet = ("+ 1 - " + selectedPipeWire + " mm.sq. THHN Cu. Wire");
                        // Update TextViews
                        FeederWireSecond.setText(newTet);
                        String topText = "USE " + FeederWire.getText().toString() + newTet + FeederWireFourth.getText().toString() + " " + Pipetype.getText().toString();
                        String botText = "GEC: " + newTet;

                        TextView[] topViews = {num4_top, num6_top, num8_top, num10_top, num12_top, num14_top, num16_top, num18_top, num20_top, num22_top, num24_top, num26_top, num28_top, num30_top};
                        TextView[] botViews = {num4_bot, num6_bot, num8_bot, num10_bot, num12_bot, num14_bot, num16_bot, num18_bot, num20_bot, num22_bot, num24_bot, num26_bot, num28_bot, num30_bot};

                        for (int i = 0; i < topViews.length; i++) {
                            topViews[i].setText(topText);
                            botViews[i].setText(botText);
                        }

                        // Dismiss the dialog after OK button is clicked
                        dialog.dismiss();
                    }
                });
            }
        });


//last feeder wire
        FeederWireFourth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inflate the layout containing the AutoCompleteTextView
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_feeder_wire4, null);

                // Find the AutoCompleteTextView in the inflated layout
                final AutoCompleteTextView autoCompleteTextView = dialogView.findViewById(R.id.auto_complete_feeder_wire4);

                // Define your list of feeder wire options
                String[] feederWireOptions = new String[]{"2", "3.5", "5.5", "8.0", "14", "22", "30", "38", "50", "60", "80", "100", "125", "150", "175", "200", "250"};

                // Create ArrayAdapter to hold options
                ArrayAdapter<String> adapter = new ArrayAdapter<>(Loadschedule.this, android.R.layout.simple_dropdown_item_1line, feederWireOptions);
                autoCompleteTextView.setAdapter(adapter);

                AlertDialog.Builder builder = new AlertDialog.Builder(Loadschedule.this);
                builder.setView(dialogView)
                        .setTitle("Update Feeder Wire")
                        .setPositiveButton("OK", null) // Set null initially, we'll enable/disable it later
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked Cancel button, dismiss the dialog
                                dialog.dismiss();
                            }
                        });

                final AlertDialog dialog = builder.create();
                dialog.show();

                // Get the button from the dialog after it's shown
                final Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

                // Set initial state for the OK button
                positiveButton.setEnabled(false);

                // Set a listener to enable/disable the OK button based on text input
                autoCompleteTextView.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        // Enable/disable OK button based on whether there is text entered
                        positiveButton.setEnabled(s.toString().trim().length() > 0);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });

                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Get the selected item from the AutoCompleteTextView
                        String selectedFeederWire = autoCompleteTextView.getText().toString();
                        // Update the FeederWire with the new text
                        FeederWireFourth.setText(" (G)In " + selectedFeederWire + " mmø");

                        String topText = "USE " + FeederWire.getText().toString() + FeederWireSecond.getText().toString() + FeederWireFourth.getText().toString() + Pipetype.getText().toString();

                        TextView[] topViews = {num4_top, num6_top, num8_top, num10_top, num12_top, num14_top, num16_top, num18_top, num20_top, num22_top, num24_top, num26_top, num28_top, num30_top};

                        for (int i = 0; i < topViews.length; i++) {
                            topViews[i].setText(topText);
                        }

                        // Dismiss the dialog after OK button is clicked
                        dialog.dismiss();
                    }
                });
            }
        });


// main  wire update
        //last main wire update
        MainWire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inflate the layout containing the AutoCompleteTextViews
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_main_wire, null);

                // Find the AutoCompleteTextViews in the inflated layout
                final AutoCompleteTextView ATWire = dialogView.findViewById(R.id.auto_complete_AT_wire);
                final AutoCompleteTextView AFWire = dialogView.findViewById(R.id.auto_complete_AF_wire);

                // Define your list of AT and AF wire options
                String[] ATOptions = new String[]{"20", "30", "40", "50", "60", "70", "80", "90", "100", "110", "120", "125", "150", "175"};
                String[] AFOptions = new String[]{"50", "100", "225"};

                // Create ArrayAdapters to hold options
                ArrayAdapter<String> ATAdapter = new ArrayAdapter<>(Loadschedule.this, android.R.layout.simple_dropdown_item_1line, ATOptions);
                ArrayAdapter<String> AFAdapter = new ArrayAdapter<>(Loadschedule.this, android.R.layout.simple_dropdown_item_1line, AFOptions);
                ATWire.setAdapter(ATAdapter);
                AFWire.setAdapter(AFAdapter);

                AlertDialog.Builder builder = new AlertDialog.Builder(Loadschedule.this);
                builder.setView(dialogView)
                        .setTitle("Update AT and AF")
                        .setPositiveButton("OK", null) // Set null initially, we'll enable/disable it later
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked Cancel button, dismiss the dialog
                                dialog.dismiss();
                            }
                        });

                final AlertDialog dialog = builder.create();
                dialog.show();

                // Get the button from the dialog after it's shown
                final Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

                // Set initial state for the OK button
                positiveButton.setEnabled(false);

                // Set a listener to enable/disable the OK button based on text input
                TextWatcher watcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        // Enable/disable OK button based on whether there is text entered
                        String ATText = ATWire.getText().toString().trim();
                        String AFText = AFWire.getText().toString().trim();
                        positiveButton.setEnabled(!ATText.isEmpty() && !AFText.isEmpty());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                };

                ATWire.addTextChangedListener(watcher);
                AFWire.addTextChangedListener(watcher);

                // Disable AF input based on the selected AT value
                ATWire.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selectedAT = ATWire.getText().toString();
                        if (selectedAT.equals("20") || selectedAT.equals("30") || selectedAT.equals("40") || selectedAT.equals("50"))
                            AFWire.setText("50");
                        else if (selectedAT.equals("60") || selectedAT.equals("70") || selectedAT.equals("80") || selectedAT.equals("90") || selectedAT.equals("100"))
                            AFWire.setText("100");
                        else
                            AFWire.setText("225");

                        // Disable AF input
                        AFWire.setEnabled(false);
                    }
                });

                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Get the text from the AutoCompleteTextViews
                        String AT = ATWire.getText().toString();
                        String AF = AFWire.getText().toString();
                        // Update the MainWire with the new text
                        String newAfAt = (AT + " AT, " + AF + " AF, 2P, 230V, 60 HZ");
                        // Update TextViews
                        MainWire.setText(newAfAt);

                        TextView[] numViews = {num4_a, num6_a, num8_a, num10_a, num12_a, num14_a, num16_a, num18_a, num20_a, num22_a, num24_a, num26_a, num28_a, num30_a};

                        for (int i = 0; i < numViews.length; i++) {
                            numViews[i].setText(AT + " AT" + ", 2P ");
                        }

                        // Dismiss the dialog after OK button is clicked
                        dialog.dismiss();
                    }
                });
            }
        });





        rootLayout = findViewById(R.id.zoom);
        mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
        mGestureDetector = new GestureDetector(this, new GestureListener());


    }

    public void setRecyclerView(List<ProjectTable> projectTableList) {
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
    public boolean onCreateOptionsMenu(Menu menu) {
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add) {
            onBackPressed();
        }
        /* -------------------------- START OF PRINT ---------------------- */

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

            // Set the 'Next' button
            builder.setPositiveButton("Next", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (selectedItem[0] != -1) {
                        String paperSize = (String) paperSizes[selectedItem[0]];

                        // Dismiss the paper size selection dialog
                        dialog.dismiss();

                        // Show additional info dialog
                        showAdditionalInfoDialog(paperSize);
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


        /* -------------------------- START OF NEXT LOAD SCHEDULE ---------------------- */

// Initialize currentTableCount from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        currentTableCount = prefs.getInt("currentTableCount", 0);
        if (id == R.id.nextLS) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Loadschedule.this);
            LayoutInflater inflater = Loadschedule.this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_choose_main_pipe, null);
            final AutoCompleteTextView autoCompleteTextView = dialogView.findViewById(R.id.autoCompletepipe);

            // Set up AutoCompleteTextView with options
            String[] mainPipeOptions = {"EMT", "PVC", "IMC", "LTFMC"};
            ArrayAdapter<String> adapter = new ArrayAdapter<>(Loadschedule.this, android.R.layout.simple_dropdown_item_1line, mainPipeOptions);
            autoCompleteTextView.setAdapter(adapter);

            builder.setView(dialogView)
                    .setTitle("Choose Main Pipe")
                    .setPositiveButton("OK", null) // Initially set to null
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing if canceled
                        }
                    });
            final AlertDialog dialog = builder.create();

            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    positiveButton.setEnabled(false); // Initially disable the OK button

                    autoCompleteTextView.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {}

                        @Override
                        public void afterTextChanged(Editable s) {
                            positiveButton.setEnabled(!TextUtils.isEmpty(autoCompleteTextView.getText().toString()));
                        }
                    });

                    positiveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String selectedMainPipe = autoCompleteTextView.getText().toString();
                            if (currentTableCount < 3) {
                                // Increment the current table count
                                currentTableCount++;
                                Log.d("Debug", "Current table count: " + currentTableCount); // Debugging statement

                                // Save the updated currentTableCount using SharedPreferences
                                SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
                                editor.putInt("currentTableCount", currentTableCount);
                                editor.apply();

                                // Capture the contents of RelativeLayout and save as images in SharedPreferences
                                captureRelativeLayoutAsImage();
                            }

                            if (currentTableCount != 3) {
                                // Increment the current table count again if it's less than 3


                                Log.d("Debug", "Current table count: " + currentTableCount); // Debugging statement

                                // Save the updated currentTableCount using SharedPreferences
                                SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
                                editor.putInt("currentTableCount", currentTableCount);
                                editor.apply();

                                // Proceed with inputting data for the next table
                                // For example, to start a new activity for inputting data
                                Intent intent = new Intent(Loadschedule.this, Inputing.class);
                                intent.putExtra("currentTableCount", currentTableCount); // Pass currentTableCount as an extra
                                intent.putExtra("mainPipe", selectedMainPipe);
                                startActivity(intent);
                                databaseHelper.clearTable();

                                Toast.makeText(Loadschedule.this, "Table Saved. Num of tables: " + currentTableCount, Toast.LENGTH_SHORT).show();
                            } else {
                                // Handle what to do when the maximum number of tables is reached
                                Toast.makeText(Loadschedule.this, "Maximum number of tables reached", Toast.LENGTH_SHORT).show();
                                // You can handle the maximum limit scenario here
                            }
                            dialog.dismiss();
                        }
                    });

                }
            });

            dialog.show();
        }


        /* -------------------------- END OF NEXT LOAD SCHEDULE ---------------------- */


        if (id == R.id.resetLS) {
            Toast.makeText(this, "reset current load schedule button", Toast.LENGTH_SHORT).show();
            // Start the Inputing activity
            Intent intent = new Intent(this, Inputing.class);
            startActivity(intent);
            databaseHelper.clearTable();

        }
        if (id == R.id.discard) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Discard the project?");
            builder.setMessage("Are you sure you want to discard the project and return to home?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Disregard the load and return home
                    // Clear the currentTableCount from SharedPreferences to reset it to 0
                    SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
                    editor.clear(); // Removes all data from SharedPreferences
                    editor.apply();

// Update the currentTableCount variable to reflect the change
                    currentTableCount = 0;

                    Toast.makeText(Loadschedule.this, "Project discarded", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Loadschedule.this, com.example.projectone.Menu.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    databaseHelper.clearTable();
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

    // Method to capture the contents of RelativeLayout and save as images in SharedPreferences
    private void captureRelativeLayoutAsImage() {
        // Get the RelativeLayout and its child views
        RelativeLayout relativeLayout = findViewById(R.id.aaaa);


        // Store the original top margin
        RelativeLayout.LayoutParams originalLayoutParams = (RelativeLayout.LayoutParams) relativeLayout.getLayoutParams();
        int originalTopMargin = originalLayoutParams.topMargin;

        // Adjust the top margin temporarily
        originalLayoutParams.setMargins(originalLayoutParams.leftMargin, 0, originalLayoutParams.rightMargin, originalLayoutParams.bottomMargin);
        relativeLayout.setLayoutParams(originalLayoutParams);

        // Convert RelativeLayout to bitmap
        Bitmap bitmap = Bitmap.createBitmap(relativeLayout.getWidth(), relativeLayout.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        relativeLayout.draw(canvas);

        // Reset the original margins
        originalLayoutParams.setMargins(originalLayoutParams.leftMargin, originalTopMargin, originalLayoutParams.rightMargin, originalLayoutParams.bottomMargin);
        relativeLayout.setLayoutParams(originalLayoutParams);

        // Convert bitmap to byte array
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        // Save byte array in SharedPreferences
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String keyRela = "relativeLayout" + currentTableCount;
        editor.putString(keyRela, Base64.encodeToString(byteArray, Base64.DEFAULT));
        editor.apply();
    }

    private Bitmap convertViewToBitmap(TextView textView) {
        Bitmap bitmap = Bitmap.createBitmap(textView.getWidth(), textView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        textView.layout(0, 0, textView.getWidth(), textView.getHeight());
        textView.draw(canvas);
        return bitmap;
    }


    private void showAdditionalInfoDialog(String paperSize) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Loadschedule.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_additional_info, null);
        builder.setView(dialogView);

        EditText etEngineerName = dialogView.findViewById(R.id.etEngineerName);
        EditText etProposedProjName = dialogView.findViewById(R.id.etProposedProjName);
        EditText etLocation = dialogView.findViewById(R.id.etLocation);
        EditText etOwner = dialogView.findViewById(R.id.etOwner);
        EditText etAddress = dialogView.findViewById(R.id.etAddress);
        EditText etRevision1 = dialogView.findViewById(R.id.etRevision1);
        EditText etDesignedBy = dialogView.findViewById(R.id.etDesignedBy);
        EditText etCertifiedBy = dialogView.findViewById(R.id.etCertifiedBy);
        EditText etRevision2 = dialogView.findViewById(R.id.etRevision2);
        EditText etElectrical = dialogView.findViewById(R.id.etElectrical);
        EditText etSheetNo = dialogView.findViewById(R.id.etSheetNo);


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
                // Initialize TextViews
                TextView tvEngineerName = findViewById(R.id.tvEngineerName);
                TextView tvProposedProjName = findViewById(R.id.tvProposedProjName);
                TextView tvLocation = findViewById(R.id.tvLocation);
                TextView tvOwner = findViewById(R.id.tvOwner);
                TextView tvAddress = findViewById(R.id.tvAddress);
                TextView tvRevision1 = findViewById(R.id.tvRevision1);
                TextView tvDesignedBy = findViewById(R.id.tvDesignedBy);
                TextView tvCertifiedBy = findViewById(R.id.tvCertifiedBy);
                TextView tvRevision2 = findViewById(R.id.tvRevision2);
                TextView tvElectrical = findViewById(R.id.tvElectrical);
                TextView tvSheetNo = findViewById(R.id.tvSheetNo);

                TextView tvEngineerNameA3 = findViewById(R.id.tvEngineerNameA3);
                TextView tvProposedProjNameA3 = findViewById(R.id.tvProposedProjNameA3);
                TextView tvLocationA3 = findViewById(R.id.tvLocationA3);
                TextView tvOwnerA3 = findViewById(R.id.tvOwnerA3);
                TextView tvAddressA3 = findViewById(R.id.tvAddressA3);
                TextView tvRevision1A3 = findViewById(R.id.tvRevision1A3);
                TextView tvDesignedByA3 = findViewById(R.id.tvDesignedByA3);
                TextView tvCertifiedByA3 = findViewById(R.id.tvCertifiedByA3);
                TextView tvRevision2A3 = findViewById(R.id.tvRevision2A3);
                TextView tvElectricalA3 = findViewById(R.id.tvElectricalA3);
                TextView tvSheetNoA3 = findViewById(R.id.tvSheetNoA3);


                String engineerName = etEngineerName.getText().toString();
                String proposedProjName = etProposedProjName.getText().toString();
                String location = etLocation.getText().toString();
                String owner = etOwner.getText().toString();
                String address = etAddress.getText().toString();
                String revision1 = etRevision1.getText().toString();
                String designedBy = etDesignedBy.getText().toString();
                String certifiedBy = etCertifiedBy.getText().toString();
                String revision2 = etRevision2.getText().toString();
                String electrical = etElectrical.getText().toString();
                String sheetNo = etSheetNo.getText().toString();

// Check each string and assign a space if it's empty
                if (engineerName.isEmpty()) {
                    engineerName = " ";
                }
                if (proposedProjName.isEmpty()) {
                    proposedProjName = " ";
                }
                if (location.isEmpty()) {
                    location = " ";
                }
                if (owner.isEmpty()) {
                    owner = " ";
                }
                if (address.isEmpty()) {
                    address = " ";
                }
                if (revision1.isEmpty()) {
                    revision1 = " ";
                }
                if (designedBy.isEmpty()) {
                    designedBy = " ";
                }
                if (certifiedBy.isEmpty()) {
                    certifiedBy = " ";
                }
                if (revision2.isEmpty()) {
                    revision2 = " ";
                }
                if (electrical.isEmpty()) {
                    electrical = " ";
                }
                if (sheetNo.isEmpty()) {
                    sheetNo = " ";
                }


                // Set text to TextViews
                tvEngineerName.setText(engineerName.toUpperCase());
                tvProposedProjName.setText(proposedProjName.toUpperCase());
                tvLocation.setText(location.toUpperCase());
                tvOwner.setText(owner.toUpperCase());
                tvAddress.setText(address.toUpperCase());
                tvRevision1.setText(revision1.toUpperCase());
                tvDesignedBy.setText(designedBy.toUpperCase());
                tvCertifiedBy.setText(certifiedBy.toUpperCase());
                tvRevision2.setText(revision2.toUpperCase());
                tvElectrical.setText(electrical.toUpperCase());
                tvSheetNo.setText(sheetNo.toUpperCase());

                tvEngineerNameA3.setText(engineerName.toUpperCase());
                tvProposedProjNameA3.setText(proposedProjName.toUpperCase());
                tvLocationA3.setText(location.toUpperCase());
                tvOwnerA3.setText(owner.toUpperCase());
                tvAddressA3.setText(address.toUpperCase());
                tvRevision1A3.setText(revision1.toUpperCase());
                tvDesignedByA3.setText(designedBy.toUpperCase());
                tvCertifiedByA3.setText(certifiedBy.toUpperCase());
                tvRevision2A3.setText(revision2.toUpperCase());
                tvElectricalA3.setText(electrical.toUpperCase());
                tvSheetNoA3.setText(sheetNo.toUpperCase());






                if (currentTableCount < 3) {
                    currentTableCount++;
                    captureRelativeLayoutAsImage();
                }


                // Perform your save operation here
                // For example, you can save additionalInfo to a file or database

                // Dismiss the dialog
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

                                // Retrieve RelativeLayout images from SharedPreferences and add them to the PDF
                                SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                                for (int i = 1; i <= 3; i++) {
                                    String keyRela = "relativeLayout" + i;

                                    String relaImageBase64 = prefs.getString(keyRela, null);

                                    if (relaImageBase64 != null) {
                                        byte[] relaImageBytes = Base64.decode(relaImageBase64, Base64.DEFAULT);

                                        // Convert bytes to Bitmap
                                        Bitmap relaBitmap = BitmapFactory.decodeByteArray(relaImageBytes, 0, relaImageBytes.length);

                                        // Convert Bitmap to Image
                                        ByteArrayOutputStream relaStream = new ByteArrayOutputStream();
                                        relaBitmap.compress(Bitmap.CompressFormat.PNG, 100, relaStream);
                                        Image relaImage = Image.getInstance(relaStream.toByteArray());

                                        // Convert TextViews to Bitmaps
                                        Bitmap bitmapEngineerName = convertViewToBitmap(tvEngineerName);
                                        Bitmap bitmapProposedProjName = convertViewToBitmap(tvProposedProjName);
                                        Bitmap bitmapOwner = convertViewToBitmap(tvOwner);
                                        Bitmap bitmapElectrical = convertViewToBitmap(tvElectrical);
                                        Bitmap bitmapSheetNo = convertViewToBitmap(tvSheetNo);
                                        Bitmap bitmapDesignedBy = convertViewToBitmap(tvDesignedBy);
                                        Bitmap bitmapCertifiedBy = convertViewToBitmap(tvCertifiedBy);
                                        Bitmap bitmapRevision2 = convertViewToBitmap(tvRevision2);
                                        Bitmap bitmapLocation = convertViewToBitmap(tvLocation);
                                        Bitmap bitmapAddress = convertViewToBitmap(tvAddress);

                                        Bitmap bitmapEngineerNameA3 = convertViewToBitmap(tvEngineerNameA3);
                                        Bitmap bitmapProposedProjNameA3 = convertViewToBitmap(tvProposedProjNameA3);
                                        Bitmap bitmapOwnerA3 = convertViewToBitmap(tvOwnerA3);
                                        Bitmap bitmapElectricalA3 = convertViewToBitmap(tvElectricalA3);
                                        Bitmap bitmapSheetNoA3 = convertViewToBitmap(tvSheetNoA3);
                                        Bitmap bitmapDesignedByA3 = convertViewToBitmap(tvDesignedByA3);
                                        Bitmap bitmapCertifiedByA3 = convertViewToBitmap(tvCertifiedByA3);
                                        Bitmap bitmapRevision2A3 = convertViewToBitmap(tvRevision2A3);
                                        Bitmap bitmapLocationA3 = convertViewToBitmap(tvLocationA3);
                                        Bitmap bitmapAddressA3 = convertViewToBitmap(tvAddressA3);





                                        // Convert Bitmaps to Images
                                        ByteArrayOutputStream streamEngineerName = new ByteArrayOutputStream();
                                        bitmapEngineerName.compress(Bitmap.CompressFormat.PNG, 100, streamEngineerName);
                                        Image imageEngineerName = Image.getInstance(streamEngineerName.toByteArray());

                                        ByteArrayOutputStream streamProposedProjName = new ByteArrayOutputStream();
                                        bitmapProposedProjName.compress(Bitmap.CompressFormat.PNG, 100, streamProposedProjName);
                                        Image imageProposedProjName = Image.getInstance(streamProposedProjName.toByteArray());

                                        ByteArrayOutputStream streamOwner = new ByteArrayOutputStream();
                                        bitmapOwner.compress(Bitmap.CompressFormat.PNG, 100, streamOwner);
                                        Image imageOwner = Image.getInstance(streamOwner.toByteArray());

                                        ByteArrayOutputStream streamElectrical = new ByteArrayOutputStream();
                                        bitmapElectrical.compress(Bitmap.CompressFormat.PNG, 100, streamElectrical);
                                        Image imageElectrical = Image.getInstance(streamElectrical.toByteArray());

                                        ByteArrayOutputStream streamSheetNo = new ByteArrayOutputStream();
                                        bitmapSheetNo.compress(Bitmap.CompressFormat.PNG, 100, streamSheetNo);
                                        Image imageSheetNo = Image.getInstance(streamSheetNo.toByteArray());

                                        ByteArrayOutputStream streamDesignedBy = new ByteArrayOutputStream();
                                        bitmapDesignedBy.compress(Bitmap.CompressFormat.PNG, 100, streamDesignedBy);
                                        Image imageDesignedBy = Image.getInstance(streamDesignedBy.toByteArray());

                                        ByteArrayOutputStream streamCertifiedBy = new ByteArrayOutputStream();
                                        bitmapCertifiedBy.compress(Bitmap.CompressFormat.PNG, 100, streamCertifiedBy);
                                        Image imageCertifiedBy = Image.getInstance(streamCertifiedBy.toByteArray());

                                        ByteArrayOutputStream streamRevision2 = new ByteArrayOutputStream();
                                        bitmapRevision2.compress(Bitmap.CompressFormat.PNG, 100, streamRevision2);
                                        Image imageRevision2 = Image.getInstance(streamRevision2.toByteArray());

                                        ByteArrayOutputStream streamLocation = new ByteArrayOutputStream();
                                        bitmapLocation.compress(Bitmap.CompressFormat.PNG, 100, streamLocation);
                                        Image imageLocation = Image.getInstance(streamLocation.toByteArray());

                                        ByteArrayOutputStream streamAddress = new ByteArrayOutputStream();
                                        bitmapAddress.compress(Bitmap.CompressFormat.PNG, 100, streamAddress);
                                        Image imageAdress = Image.getInstance(streamAddress.toByteArray());


                                        ByteArrayOutputStream streamEngineerNameA3 = new ByteArrayOutputStream();
                                        bitmapEngineerNameA3.compress(Bitmap.CompressFormat.PNG, 100, streamEngineerNameA3);
                                        Image imageEngineerNameA3 = Image.getInstance(streamEngineerNameA3.toByteArray());

                                        ByteArrayOutputStream streamProposedProjNameA3 = new ByteArrayOutputStream();
                                        bitmapProposedProjNameA3.compress(Bitmap.CompressFormat.PNG, 100, streamProposedProjNameA3);
                                        Image imageProposedProjNameA3 = Image.getInstance(streamProposedProjNameA3.toByteArray());

                                        ByteArrayOutputStream streamOwnerA3 = new ByteArrayOutputStream();
                                        bitmapOwnerA3.compress(Bitmap.CompressFormat.PNG, 100, streamOwnerA3);
                                        Image imageOwnerA3 = Image.getInstance(streamOwnerA3.toByteArray());

                                        ByteArrayOutputStream streamElectricalA3 = new ByteArrayOutputStream();
                                        bitmapElectricalA3.compress(Bitmap.CompressFormat.PNG, 100, streamElectricalA3);
                                        Image imageElectricalA3 = Image.getInstance(streamElectricalA3.toByteArray());

                                        ByteArrayOutputStream streamSheetNoA3 = new ByteArrayOutputStream();
                                        bitmapSheetNoA3.compress(Bitmap.CompressFormat.PNG, 100, streamSheetNoA3);
                                        Image imageSheetNoA3 = Image.getInstance(streamSheetNoA3.toByteArray());

                                        ByteArrayOutputStream streamDesignedByA3 = new ByteArrayOutputStream();
                                        bitmapDesignedByA3.compress(Bitmap.CompressFormat.PNG, 100, streamDesignedByA3);
                                        Image imageDesignedByA3 = Image.getInstance(streamDesignedByA3.toByteArray());

                                        ByteArrayOutputStream streamCertifiedByA3 = new ByteArrayOutputStream();
                                        bitmapCertifiedByA3.compress(Bitmap.CompressFormat.PNG, 100, streamCertifiedByA3);
                                        Image imageCertifiedByA3 = Image.getInstance(streamCertifiedByA3.toByteArray());

                                        ByteArrayOutputStream streamRevision2A3 = new ByteArrayOutputStream();
                                        bitmapRevision2A3.compress(Bitmap.CompressFormat.PNG, 100, streamRevision2A3);
                                        Image imageRevision2A3 = Image.getInstance(streamRevision2A3.toByteArray());

                                        ByteArrayOutputStream streamLocationA3 = new ByteArrayOutputStream();
                                        bitmapLocationA3.compress(Bitmap.CompressFormat.PNG, 100, streamLocationA3);
                                        Image imageLocationA3 = Image.getInstance(streamLocationA3.toByteArray());

                                        ByteArrayOutputStream streamAddressA3 = new ByteArrayOutputStream();
                                        bitmapAddressA3.compress(Bitmap.CompressFormat.PNG, 100, streamAddressA3);
                                        Image imageAddressA3 = Image.getInstance(streamAddressA3.toByteArray());


                                        // Adjust positions and sizes based on paper size
                                        int desiredWidth = 0;
                                        int desiredHeight = 0;

                                        // Determine the total height and width of the page
                                        float pageHeight = pageSize.getHeight();
                                        float pageWidth = pageSize.getHeight();

                                        float xPosition = 0;
                                        float yPosition = 0;



                                        switch (paperSize.toLowerCase()) {
                                            case "a1":
                                                if(currentTableCount == 1) {
                                                    // Adjust as needed
                                                    desiredWidth = (int) (pageSize.getWidth() * 2); // Make it smaller by halving the width
                                                    desiredHeight = (int) (pageSize.getHeight() / 4); // Make it smaller by dividing the height by ...
                                                    xPosition = (pageWidth - desiredWidth) + 1500; // Center horizontally
                                                    yPosition = 1050 + (i - 1) * desiredHeight; // Adjust as needed
                                                }else if(currentTableCount == 2) {
                                                    // Adjust as needed
                                                    desiredWidth = (int) (pageSize.getWidth() * 2); // Make it smaller by halving the width
                                                    desiredHeight = (int) (pageSize.getHeight() / 4); // Make it smaller by dividing the height by ...
                                                    xPosition = (pageWidth - desiredWidth) + 1500; // Center horizontally
                                                    yPosition = 900 + (i - 1) * desiredHeight; // Adjust as needed
                                                }else if(currentTableCount == 3) {
                                                    // Adjust as needed
                                                    desiredWidth = (int) (pageSize.getWidth()); // Make it smaller by halving the width
                                                    desiredHeight = (int) (pageSize.getHeight() / 5.5); // Make it smaller by dividing the height by ...
                                                    xPosition = (pageWidth - desiredWidth) + 20;
                                                    yPosition = 750 + (i - 1) * desiredHeight; // Adjust as needed
                                                }

                                                imageEngineerName.setAbsolutePosition(500, 150);
                                                imageProposedProjName.setAbsolutePosition(775, 140);
                                                imageOwner.setAbsolutePosition(1180, 140);
                                                imageElectrical.setAbsolutePosition(2020, 130);
                                                imageSheetNo.setAbsolutePosition(2270, 100);
                                                imageDesignedBy.setAbsolutePosition(1800, 170);
                                                imageCertifiedBy.setAbsolutePosition(1800, 120);
                                                imageRevision2.setAbsolutePosition(1800, 85);
                                                imageAdress.setAbsolutePosition(1200, 85);
                                                imageLocation.setAbsolutePosition(850, 85);

                                                // Add the Images to the document
                                                document.add(imageEngineerName);
                                                document.add(imageProposedProjName);
                                                document.add(imageOwner);
                                                document.add(imageElectrical);
                                                document.add(imageSheetNo);
                                                document.add(imageDesignedBy);
                                                document.add(imageCertifiedBy);
                                                document.add(imageRevision2);
                                                document.add(imageAdress);
                                                document.add(imageLocation);

                                                break;


                                            case "a3":
                                                if(currentTableCount == 1) {
                                                    // Adjust as needed
                                                    desiredWidth = (int) (pageSize.getWidth() * 2);
                                                    desiredHeight = (int) (pageSize.getHeight() / 4);
                                                    xPosition = (pageWidth - desiredWidth) + 700;
                                                    yPosition = 580 + (i - 1) * desiredHeight; // Adjust as needed

                                                }else if(currentTableCount == 2) {
                                                    // Adjust as needed
                                                    desiredWidth = (int) (pageSize.getWidth() * 2);
                                                    desiredHeight = (int) (pageSize.getHeight() / 4);
                                                    xPosition = (pageWidth - desiredWidth) + 700;
                                                    yPosition = 450 + (i - 1) * desiredHeight; // Adjust as needed
                                                }else if(currentTableCount == 3) {
                                                    // Adjust as needed
                                                    desiredWidth = (int) (pageSize.getWidth());
                                                    desiredHeight = (int) (pageSize.getHeight() / 5.5);
                                                    xPosition = (pageWidth - desiredWidth) + 20;
                                                    yPosition = 390 + (i - 1) * desiredHeight; // Adjust as needed
                                                }

                                                imageEngineerNameA3.setAbsolutePosition(245, 75);
                                                imageProposedProjNameA3.setAbsolutePosition(445, 65);
                                                imageOwnerA3.setAbsolutePosition(650, 65);
                                                imageElectricalA3.setAbsolutePosition(1010, 65);
                                                imageSheetNoA3.setAbsolutePosition(1135, 50);
                                                imageDesignedByA3.setAbsolutePosition(890, 85);
                                                imageCertifiedByA3.setAbsolutePosition(890, 60);
                                                imageRevision2A3.setAbsolutePosition(890, 45);
                                                imageAddressA3.setAbsolutePosition(600, 44);
                                                imageLocationA3.setAbsolutePosition(415, 44);

                                                document.add(imageEngineerNameA3);
                                                document.add(imageProposedProjNameA3);
                                                document.add(imageOwnerA3);
                                                document.add(imageElectricalA3);
                                                document.add(imageSheetNoA3);
                                                document.add(imageDesignedByA3);
                                                document.add(imageCertifiedByA3);
                                                document.add(imageRevision2A3);
                                                document.add(imageAddressA3);
                                                document.add(imageLocationA3);


                                                break;


                                            case "20x30 inches":
                                                if(currentTableCount == 1) {
                                                    // Adjust as needed
                                                    desiredWidth = (int) (pageSize.getWidth() * 2); // Make it smaller by halving the width
                                                    desiredHeight = (int) (pageSize.getHeight() / 4); // Make it smaller by dividing the height by ...
                                                    xPosition = (pageWidth - desiredWidth) + 1200; // Center horizontally
                                                    yPosition = 1040 + (i - 1) * desiredHeight; // Adjust as needed
                                                } else if(currentTableCount == 2) {
                                                    // Adjust as needed
                                                    desiredWidth = (int) (pageSize.getWidth() * 2); // Make it smaller by halving the width
                                                    desiredHeight = (int) (pageSize.getHeight() / 4); // Make it smaller by dividing the height by ...
                                                    xPosition = (pageWidth - desiredWidth) + 1200; // Center horizontally
                                                    yPosition = 890 + (i - 1) * desiredHeight; // Adjust as needed
                                                } else if(currentTableCount == 3) {
                                                    // Adjust as needed
                                                    desiredWidth = (int) (pageSize.getWidth()); // Make it smaller by halving the width
                                                    desiredHeight = (int) (pageSize.getHeight() / 5.5); // Make it smaller by dividing the height by ...
                                                    xPosition = (pageWidth - desiredWidth) + 20;
                                                    yPosition = 740 + (i - 1) * desiredHeight; // Adjust as needed
                                                }

                                                imageEngineerName.setAbsolutePosition(460, 100);
                                                imageProposedProjName.setAbsolutePosition(710, 130 - 50);
                                                imageOwner.setAbsolutePosition(1070, 130 - 50);
                                                imageElectrical.setAbsolutePosition(1870, 80);
                                                imageSheetNo.setAbsolutePosition(2060, 50);
                                                imageDesignedBy.setAbsolutePosition(1650, 100);
                                                imageCertifiedBy.setAbsolutePosition(1650, 60);
                                                imageRevision2.setAbsolutePosition(1650, 35);
                                                imageAdress.setAbsolutePosition(1100, 37);
                                                imageLocation.setAbsolutePosition(750, 37);

                                                // Add the Images to the document
                                                document.add(imageEngineerName);
                                                document.add(imageProposedProjName);
                                                document.add(imageOwner);
                                                document.add(imageElectrical);
                                                document.add(imageSheetNo);
                                                document.add(imageDesignedBy);
                                                document.add(imageCertifiedBy);
                                                document.add(imageRevision2);
                                                document.add(imageAdress);
                                                document.add(imageLocation);

                                                break;
                                            default:
                                                // Default desired width and height
                                                desiredWidth = (int) (pageSize.getWidth() - 30);
                                                desiredHeight = (int) (pageSize.getHeight() / 4);
                                                xPosition = 30; // Adjust as needed
                                                yPosition = 30 + (i - 1) * desiredHeight; // Adjust as needed
                                                break;
                                        }

                                        // Scale the image to fit the desired width and height
                                        relaImage.scaleToFit((float) (desiredWidth * 1.5), desiredHeight);


                                        // Calculate the y-coordinate relative to the top of the page
                                        float yPositionFromTop = pageHeight - yPosition;

                                        // Subtract the desired height of the element to determine the y-coordinate relative to the top
                                        float topEdgeYPosition = yPositionFromTop - desiredHeight;


                                        // Add the RelativeLayout image to the PDF
                                        relaImage.setAbsolutePosition(xPosition, topEdgeYPosition);





                                        document.add(relaImage);
                                    }
                                }



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
            }
        });

        // Create and show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }











    @Override
    public void onBackPressed() {
        // Check if RS30 is not null and visible
        if (RS30 != null && RS30.getVisibility() == View.VISIBLE) {
            // If RS30 is visible, show an alert dialog indicating it can't go back
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("You cannot go back from this point.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        } else {
            // If RS30 is null or not visible, allow the default back button functionality
            super.onBackPressed();
        }
    }


    private void getHighestAValues() {
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(this);

        dbHelper.getAForACURefrigerator(this, new DatabaseHelper.OnItemsLoadedListener() {
            @Override
            public void onItemsLoaded(List<String> aList) {

                    double highestACUA = findHighestA(aList);
                    if (highestACUA > 0) {
                        updateUI(highestACUA);
                    }

            }
        });
    }



    private void updateUI(double highestValue) {
        if (highestValue > 0) {
            HighestA.setText(String.format("%.2f", highestValue));
            HighestB.setText(String.format("%.2f", highestValue));

            topThreeAndFourValue = highestValue * 0.25;
            underThreeAndFourValue = highestValue * 1.5;

            topThreeAndFourValue = Math.round(topThreeAndFourValue * 100.0) / 100.0;
            String formattedResulttwo = decimalFormat.format(topThreeAndFourValue);

            underThreeAndFourValue = Math.round(underThreeAndFourValue * 100.0) / 100.0;
            String formattedResulttwo2 = decimalFormat.format(underThreeAndFourValue);

            TopThreeAndFour.setText(formattedResulttwo);
            UnderThreeAndFour.setText(formattedResulttwo2);

        } else {
            HighestA.setText("0.00");
            HighestB.setText("0.00");
        }
    }



    // Method to find the highest A value from a list of A values
    private double findHighestA(List<String> aList) {
        double highestA = 0;
        for (String aString : aList) {
            double a = Double.parseDouble(aString);
            if (a > highestA) {
                highestA = a;
            }
        }
        return highestA;
    }

    // Call this method wherever you want to start the process of finding the highest A values
    private void startFindingHighestAValues() {
        getHighestAValues();

    }

    public void onTotalValueCalculated(double totalValue) {
        double demand = Double.parseDouble(demandfactor1.getText().toString());

        double totalOneValue = totalValue;


        topOneAndTwoValue = totalOneValue * demand;




        TopOneAndTwo.setText(String.valueOf(topOneAndTwoValue));
        String formattedResult = decimalFormat.format(topOneAndTwoValue);
        TopOneAndTwo.setText(formattedResult);
        UnderOneAndTwo.setText(formattedResult);
        sumOfLeftAndRightTop();

    }

    private void sumOfLeftAndRightTop() {
        double value1 = topOneAndTwoValue;
        double value2 = topThreeAndFourValue;
        double value3 = underThreeAndFourValue;



        double sum = value1 + value2;
        String resultsum = decimalFormat.format(sum);

        TotalTop.setText(String.valueOf(resultsum));


        double sum2 = value1 + value3;
        String resultsum2 = decimalFormat.format(sum2);

        TotalUnder.setText(String.valueOf(resultsum2));

        SharedPreferences sharedPreferences = getSharedPreferences("SharePref", MODE_PRIVATE);
        String FDW = sharedPreferences.getString("UFWT", "");

        if (sum2 == 0) {
            MainWire.setText("20 AT, 50 AF, 2P, 230V, 60 HZ");

        }
        if (sum2 < 15) {
            MainWire.setText("20 AT, 50 AF, 2P, 230V, 60 HZ");
        }
        if (sum2 >= 16 && sum2 <= 20) {
            MainWire.setText("20 AT, 50 AF, 2P, 230V, 60 HZ");

        }
        if (sum2 >= 21 && sum2 <= 30) {
            MainWire.setText("30 AT, 50 AF, 2P, 230V, 60 HZ");
        }
        if (sum2 >= 31 && sum2 <= 40) {
            MainWire.setText("40 AT, 50 AF, 2P, 230V, 60 HZ");
        }
        if (sum2 >= 41 && sum2 <= 50) {
            MainWire.setText("50 AT, 50 AF, 2P, 230V, 60 HZ");
        }
        if (sum2 >= 50 && sum2 <= 60) {
            MainWire.setText("60 AT, 100 AF, 2P, 230V, 60 HZ");
        }

        if (sum2 >= 71 && sum2 <= 80) {
            MainWire.setText("80 AT, 100 AF, 2P, 230V, 60 HZ");
        }
        if (sum2 >= 81 && sum2 <= 90) {
            MainWire.setText("90 AT, 100 AF, 2P, 230V, 60 HZ");
        }
        if (sum2 >= 91 && sum2 <= 100) {
            MainWire.setText("100 AT, 100 AF, 2P, 230V, 60 HZ");
        }
        if (sum2 >= 101 && sum2 <= 110) {
            MainWire.setText("110 AT, 225 AF, 2P, 230V, 60 HZ");
        }
        if (sum2 >= 111 && sum2 <= 125) {
            MainWire.setText("125 AT, 225 AF, 2P, 230V, 60 HZ");
        }
        if (sum2 >= 125 && sum2 <= 150) {
            MainWire.setText("150 AT, 225 AF, 2P, 230V, 60 HZ");
        }
        if (sum2 > 151) {
            MainWire.setText("175 AT, 225 AF, 2P, 230V, 60 HZ");
        }
        String PassMainWire = MainWire.getText().toString();


        if (FDW != null) {
            MainWire.setText(PassMainWire);
        }

        if (sum2 >= 1 && sum2 < 25) {
            FeederWire.setText("2 - 2.0mm.sq. THHN Cu. Wire");
        }
        if (sum2 >= 25 && sum2 < 30) {
            FeederWire.setText("2 - 3.5mm.sq. THHN Cu. Wire");
        }
        if (sum2 >= 30 && sum2 < 40) {
            FeederWire.setText("2 - 5.5mm.sq. THHN Cu. Wire");
        }
        if (sum2 >= 40 && sum2 < 55) {
            FeederWire.setText("2 - 8.0mm.sq. THHN Cu. Wire");
        }
        if (sum2 >= 55 && sum2 < 75) {
            FeederWire.setText("2 - 14.0mm.sq. THHN Cu. Wire");
        }
        if (sum2 >= 75 && sum2 < 95) {
            FeederWire.setText("2 - 22.0mm.sq. THHN Cu. Wire");
        }
        if (sum2 >= 95 && sum2 < 115) {
            FeederWire.setText("2 - 30.0mm.sq. THHN Cu. Wire");
        }
        if (sum2 >= 115 && sum2 < 130) {
            FeederWire.setText("2 - 38.0mm.sq. THHN Cu. Wire");
        }
        if (sum2 >= 130 && sum2 < 150) {
            FeederWire.setText("2 - 50.0mm.sq. THHN Cu. Wire");
        }
        if (sum2 >= 150 && sum2 < 170) {
            FeederWire.setText("2 - 60.0mm.sq. THHN Cu. Wire");
        }
        if (sum2 >= 170 && sum2 < 205) {
            FeederWire.setText("2 - 80.0mm.sq. THHN Cu. Wire");
        }
        if (sum2 >= 205 && sum2 < 240) {
            FeederWire.setText("2 - 100.0mm.sq. THHN Cu. Wire");
        }
        if (sum2 >= 240 && sum2 < 285) {
            FeederWire.setText("2 - 125.0mm.sq. THHN Cu. Wire");
        }
        if (sum2 >= 285 && sum2 < 320) {
            FeederWire.setText("2 - 150.0mm.sq. THHN Cu. Wire");
        }
        if (sum2 >= 320 && sum2 < 345) {
            FeederWire.setText("2 - 175.0mm.sq. THHN Cu. Wire");
        }
        if (sum2 >= 345 && sum2 < 360) {
            FeederWire.setText("2 - 200.0mm.sq. THHN Cu. Wire");
        }
        if (sum2 >= 360 && sum2 < 425) {
            FeederWire.setText("2 - 250.0mm.sq. THHN Cu. Wire");
        }
        if (sum2 >= 425 && sum2 < 490) {
            FeederWire.setText("2 - 325.0mm.sq. THHN Cu. Wire");
        }
        if (sum2 >= 490 && sum2 < 530) {
            FeederWire.setText("2 - 375.0mm.sq. THHN Cu. Wire");
        }
        if (sum2 >= 530 && sum2 < 535) {
            FeederWire.setText("2 - 400.0mm.sq. THHN Cu. Wire");
        }
        if (sum2 >= 535 && sum2 < 595) {
            FeederWire.setText("2 - 500.0mm.sq. THHN Cu. Wire");
        }


        String FeederW2 = FeederWire.getText().toString().trim();

//IF FEEDERWIRE IS 30 BELOW THE FEEDERWIRE SECOND 8.0 mm.sq.
        if (FeederW2.equals("2 - 2.0mm.sq. THHN Cu. Wire")) {
            FeederWireSecond.setText("+ 1 - 8.0  mm.sq. THHN Cu. Wire");
            FeederWireFourth.setText("(G)In 20 mmø");

        }
        if (FeederW2.equals("2 - 3.5mm.sq. THHN Cu. Wire")) {
            FeederWireSecond.setText("+ 1 - 8.0  mm.sq. THHN Cu. Wire");
            FeederWireFourth.setText("(G)In 20 mmø");
        }
        if (FeederW2.equals("2 - 5.5mm.sq. THHN Cu. Wire")) {
            FeederWireSecond.setText("+ 1 - 8.0  mm.sq. THHN Cu. Wire");
            FeederWireFourth.setText("(G)In 20 mmø");
        }
        if (FeederW2.equals("2 - 8.0mm.sq. THHN Cu. Wire")) {
            FeederWireSecond.setText("+ 1 - 8.0  mm.sq. THHN Cu. Wire");
            FeederWireFourth.setText("(G)In 20 mmø");
        }
        if (FeederW2.equals("2 - 14.0mm.sq. THHN Cu. Wire")) {
            FeederWireSecond.setText("+ 1 - 8.0  mm.sq. THHN Cu. Wire");
            FeederWireFourth.setText("(G)In 20 mmø");
        }
        if (FeederW2.equals("2 - 22.0mm.sq. THHN Cu. Wire")) {
            FeederWireSecond.setText("+ 1 - 8.0  mm.sq. THHN Cu. Wire");
            FeederWireFourth.setText("(G)In 25 mmø");
        }
        if (FeederW2.equals("2 - 30.0mm.sq. THHN Cu. Wire")) {
            FeederWireSecond.setText("+ 1 - 8.0  mm.sq. THHN Cu. Wire");
            FeederWireFourth.setText("(G)In 32 mmø");
        }
        //IF FEEDERWIRE IS 38 to 50  THE FEEDERWIRE SECOND 14.0 mm.sq.

        if (FeederW2.equals("2 - 38.0mm.sq. THHN Cu. Wire")) {
            FeederWireSecond.setText("+ 1 - 14.0  mm.sq. THHN Cu. Wire");
            FeederWireFourth.setText("(G)In 32 mmø");
        }
        if (FeederW2.equals("2 - 50.0mm.sq. THHN Cu. Wire")) {
            FeederWireSecond.setText("+ 1 - 14.0  mm.sq. THHN Cu. Wire");
            FeederWireFourth.setText("(G)In 40 mmø");
        }

        //IF FEEDERWIRE IS 60 80   =    22
        if (FeederW2.equals("2 - 60.0mm.sq. THHN Cu. Wire")) {
            FeederWireSecond.setText("+ 1 - 22.0  mm.sq. THHN Cu. Wire");
            FeederWireFourth.setText("(G)In 40 mmø");
        }
        if (FeederW2.equals("2 - 80.0mm.sq. THHN Cu. Wire")) {
            FeederWireSecond.setText("+ 1 - 22.0  mm.sq. THHN Cu. Wire");
            FeederWireFourth.setText("(G)In 50 mmø");
        }


        //IF FEEDERWIRE IS 100 to 175   = 30

        if (FeederW2.equals("2 - 100.0mm.sq. THHN Cu. Wire")) {
            FeederWireSecond.setText("+ 1 - 30.0  mm.sq. THHN Cu. Wire");
            FeederWireFourth.setText("(G)In 50 mmø");
        }
        if (FeederW2.equals("2 - 125.0mm.sq. THHN Cu. Wire")) {
            FeederWireSecond.setText("+ 1 - 30.0  mm.sq. THHN Cu. Wire");
            FeederWireFourth.setText("(G)In 50 mmø");
        }

        if (FeederW2.equals("2 - 150.0mm.sq. THHN Cu. Wire")) {
            FeederWireSecond.setText("+ 1 - 30.0  mm.sq. THHN Cu. Wire");
            FeederWireFourth.setText("(G)In 65 mmø");
        }

        if (FeederW2.equals("2 - 175.0mm.sq. THHN Cu. Wire")) {
            FeederWireSecond.setText("+ 1 - 30.0  mm.sq. THHN Cu. Wire");
            FeederWireFourth.setText("(G)In 65 mmø");
        }


        //IF FEEDERWIRE IS 200 to 325    50
        if (FeederW2.equals("2 - 200.0mm.sq. THHN Cu. Wire")) {
            FeederWireSecond.setText("+ 1 - 50.0  mm.sq. THHN Cu. Wire");
            FeederWireFourth.setText("(G)In 65 mmø");
        }
        if (FeederW2.equals("2 - 250.0mm.sq. THHN Cu. Wire")) {
            FeederWireSecond.setText("+ 1 - 50.0  mm.sq. THHN Cu. Wire");
            FeederWireFourth.setText("(G)In 80  mmø");
        }
        if (FeederW2.equals("2 - 325.0mm.sq. THHN Cu. Wire")) {
            FeederWireSecond.setText("+ 1 - 50.0  mm.sq. THHN Cu. Wire");
        }


        //IF FEEDERWIRE IS  375 to 500    600
        if (FeederW2.equals("2 - 375.0mm.sq. THHN Cu. Wire")) {
            FeederWireSecond.setText("+ 1 - 60.0  mm.sq. THHN Cu. Wire");
        }
        if (FeederW2.equals("2 - 400.0mm.sq. THHN Cu. Wire")) {
            FeederWireSecond.setText("+ 1 - 60.0  mm.sq. THHN Cu. Wire");
        }
        if (FeederW2.equals("2 - 500.0mm.sq. THHN Cu. Wire")) {
            FeederWireSecond.setText("+ 1 - 60.0  mm.sq. THHN Cu. Wire");
        }


        String Feeder2 = FeederWireSecond.getText().toString().trim();
        String Feeder3 = FeederWireFourth.getText().toString().trim();


//for the skeleton displays values
        if (FeederW2 != null) {
            String topText = "USE " + FeederW2 + Feeder2 + Feeder3 + " " + Pipetype.getText().toString();
            String botText = "GEC:" + Feeder2;

            TextView[] topViews = {num4_top, num6_top, num8_top, num10_top, num12_top, num14_top, num16_top, num18_top, num20_top, num22_top, num24_top, num26_top, num28_top, num30_top};
            TextView[] botViews = {num4_bot, num6_bot, num8_bot, num10_bot, num12_bot, num14_bot, num16_bot, num18_bot, num20_bot, num22_bot, num24_bot, num26_bot, num28_bot, num30_bot};

            for (int i = 0; i < topViews.length; i++) {
                topViews[i].setText(topText);
                botViews[i].setText(botText);
            }
        }
    }


}
