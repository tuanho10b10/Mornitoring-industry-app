package com.example.smart_apps;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Common {

    public static final String keyNhietDo = "temp";
    public static final String keyDoAmKhongKhi = "hum";
    public static final String keyChatLuongNuoc = "Water";
    public static final String keyGas = "ssCO2";
    public static final String keyVoltage = "Votage";
    public static final String keyPower = "Power";
    public static final String keyCurrent = "Current";
    public static final String keyDust = "dustDensity";
    public static final int backgroundChart = Color.WHITE; //parseColor("#B6DBD2");
    public static final int colorLine = Color.parseColor("#2B6C55");
    public static final float maxValueCO2 = 250;
    public static final float maxValueWalter = 100;

    // method init values spinner
    public static String[] initValueSpinner() {
        return new String[] {
                "0", "1", "2", "3", "4", "5",
                "6", "7", "8", "9", "10", "11",
                "12", "13", "14", "15", "16",
                "17", "18", "19", "20", "21",
                "22", "23"
        };
    }

    // method init adapter spinner
    public static ArrayAdapter<String> initAdaper(Context context, String[] listGios) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, listGios);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        return adapter;
    }

    // method update chart
    public static void updateChar(Context context, List<String> xLabel, float[] dataValues, CombinedChart mChart, String text, String kyHieu) {
        mChart.getDescription().setEnabled(false);
        mChart.setBackgroundColor(backgroundChart);
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);
        mChart.setHighlightFullBarEnabled(false);
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
//                Toast.makeText(context, "Value: "
//                        + e.getY()
//                        + ", Phut: "
//                        + h.getX()
//                        + ", DataSet index: "
//                        + h.getDataSetIndex(), Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "Phut: " + (int) h.getX() + " - Value: " + e.getY() + " " + kyHieu, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected() {}
        });

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f);
        rightAxis.setAxisMaximum(15f);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int x = 0;
                try {
                    x = (int) value % xLabel.size();
                } catch (ArithmeticException ex) {
                    //ex.printStackTrace();
                    return "";
                }
                return xLabel.get(x);
            }
        });

        LineData lineDatas = new LineData();
        lineDatas.addDataSet((ILineDataSet) dataChart(dataValues, text));

        CombinedData data = new CombinedData();
        data.setData(lineDatas);

        xAxis.setAxisMaximum(data.getXMax() + 0.25f);

        mChart.setData(data);
        mChart.invalidate();
    }

    private static DataSet dataChart(float[] data, String text) {
        LineData d = new LineData();
        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int index = 0; index < data.length; index++) {
            entries.add(new Entry(index, (float) data[index]));
        }

        LineDataSet set = new LineDataSet(entries, text);
        set.setColor(colorLine);
        set.setLineWidth(3f);
        set.setCircleColor(colorLine);
        set.setCircleRadius(5f);
        set.setFillColor(colorLine);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(colorLine);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return set;
    }

    // method thay doi mau
    public static void thayDoiMau(TextView txtHighTemp, ImageView iconHighTempLeft, ImageView iconHighTempRight, int colerCode) {
        txtHighTemp.setTextColor(colerCode);
        iconHighTempLeft.setColorFilter(colerCode);
        iconHighTempRight.setColorFilter(colerCode);
    }

    // method write email pass to firebase
    public static void writeEmailPassToFirebase(String email, String pass) {
        FirebaseDatabase.getInstance().getReference("user/email").setValue(email);
        FirebaseDatabase.getInstance().getReference("user/pass").setValue(pass);
    }

    // method read email pass to firebase
    public static void readEmailPassToFirebase(String path, TextView item) {
        FirebaseDatabase
                .getInstance()
                .getReference(path)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String noiDung = snapshot.getValue(String.class);
                        item.setText(noiDung);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    // method
    public static void hienThiChartTheoGio(Context context, String[] listGios, int i, String path, CombinedChart mChart, String text, String kyHieu) {
        String gio = listGios[i];
        int gioKieuInt = Integer.parseInt(gio);

        if (gioKieuInt > new Date().getHours()) {
            Toast.makeText(context, "Phải chọn giờ trước giờ hiện tại !!!", Toast.LENGTH_SHORT).show();
        } else {
            // lay du lieu tren firebase ve de hien thi ra chart
            FirebaseDatabase
                    .getInstance()
                    .getReference(path + "/" + gio)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.

                            Object ob = snapshot.getValue();
                            boolean isHashMap = ob instanceof HashMap;

                            List<String> dataLabels = new ArrayList<>();
                            float[] dataValuesFloat = null;

                            if (isHashMap) {
                                // truong hop nhan duoc hashmap
                                Map<String, String> map = (Map<String, String>) ob;

                                // tao mang chua phut va mang chua gia tri
                                dataLabels = new ArrayList<>(map.keySet());
                                dataValuesFloat = new float[dataLabels.size()];

                                // sap xep lai phut, do luc lay tren firebase ve khong sap xep theo dung thu tu
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    dataLabels.sort((key1, key2) -> {
                                        return key1.compareTo(key2);
                                    });
                                }

                                // lay gia tri ben map chuyen qua arrayfloat theo phut
                                for (int i = 0; i < dataLabels.size(); i++) {

                                    // lay nhan dau tien ra
                                    String label = dataLabels.get(i);

                                    // lay tung doi tuong ra de xu lý
                                    Object ob1 = map.get(label);
                                    float value = 0;

                                    // neu la Long thi xu ly 1 kieu, con ngươi lai thi la String
                                    if (ob1 instanceof Double) {
                                        double d = (Double) ob1;
                                        value = (float) d;
                                    } else if (ob1 instanceof Long) {
                                        Long l = (Long) ob1;
                                        value = (float) l;
                                    }  else {
                                        value = Float.parseFloat((String) ob1);
                                    }

                                    // gan lai mang de chuyen qua chart hien thi
                                    dataValuesFloat[i] = value;
                                }
                            } else {
                                //truong hop nhan duoc arraylist
                                List<Object> listValue = (ArrayList<Object>) ob;

                                // neu nhu co phut thi minh moi xu ly tiep, con chua co thi cho qua
                                if (listValue != null) {
                                    dataValuesFloat = new float[listValue.size()];

                                    // convert arraylist string to array float
                                    for (int j = 0; j < listValue.size(); j++) {
                                        if (listValue.get(j) == null) {
                                            dataValuesFloat[j] = 0;
                                        } else {

                                            Object ob3 = listValue.get(j);
                                            double d = 0;

                                            if (ob3 instanceof Double) {
                                                d = (Double) listValue.get(j);
                                            } else if (ob3 instanceof Long) {
                                                long l = (Long) listValue.get(j);
                                                d = (double) l;
                                            } else {
                                                d = Double.parseDouble((String)listValue.get(j));
                                            }

                                            dataValuesFloat[j] = (float) d;
                                        }
                                        dataLabels.add(j + "");
                                    }
                                } else {
                                    // truong hop ni không co data theo gio
                                    dataValuesFloat = new float[0];
                                }
                            }

                            Common.updateChar(context, dataLabels, dataValuesFloat, mChart, text, kyHieu);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Failed to read value
                            Log.w(TAG, "Failed to read value.", error.toException());
                        }
                    });
        }
    }

    public static void updateNow(String path, TextView txtValue, String kyHieu) {
        FirebaseDatabase
                .getInstance()
                .getReference(path + "/now")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        txtValue.setText(snapshot.getValue() + " " + kyHieu);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public static void updateNow(String path, TextView txtValue, TextView txtHigh, ImageView imgLeft, ImageView imgRight, String kyHieu, String loai) {
        FirebaseDatabase
                .getInstance()
                .getReference(path + "/now")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Object ob = snapshot.getValue();

                        float result = 0;

                        if (ob instanceof Double) {
                            double d = (Double) ob;
                            result = (float) d;
                        } if (ob instanceof Long) {
                            long l = (Long) ob;
                            result = (float) l;
                        } else {
                            result = Float.parseFloat((String) ob);
                        }

                        txtValue.setText(result + " " + kyHieu);

                        // =====================
                        float soSanh = loai.equals("water") ? Common.maxValueWalter : Common.maxValueCO2;

                        if (result >= soSanh) {
                            Common.thayDoiMau(txtHigh, imgLeft, imgRight, Color.RED);
                        } else {
                            Common.thayDoiMau(txtHigh, imgLeft, imgRight, Color.WHITE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public static void removeDataAfterNow(String path) {
        // thoi gian hien tai
        Date date = new Date();

        // xoa data theo phut
        FirebaseDatabase
                .getInstance()
                .getReference(path + "/" + date.getHours())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int phut = 0; // tương trưng cho phút ở trong list ở firebase lấy vế
                        for (DataSnapshot appleSnapshot : snapshot.getChildren()) {
                            if (phut > date.getMinutes()) {
                                appleSnapshot.getRef().removeValue(); // xoa gia tri tai doi tuong dc tham chieu
                            }
                            phut++; // phut = phut + 1
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG, "onCancelled", error.toException());
                    }
                });

        // xoa data sau gio
        for (int i = date.getHours() + 1; i < 24; i++) {
            FirebaseDatabase
                    .getInstance()
                    .getReference(path + "/" + i)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot appleSnapshot : snapshot.getChildren()) {
                                appleSnapshot.getRef().removeValue();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e(TAG, "onCancelled", error.toException());
                        }
                    });
        }
    }
}
