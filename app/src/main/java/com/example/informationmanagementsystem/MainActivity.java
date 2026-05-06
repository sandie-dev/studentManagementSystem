package com.example.informationmanagementsystem;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
  Button rgBtn, updateBtn, viewBtn, deleteBtn;
  EditText stName, stDpt, stPhone, stId;
  String nameIn, dptIN,phonStr, idStr;
  databaseHelper dh;
  SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
       rgBtn=findViewById(R.id.register);
       updateBtn=findViewById(R.id.update);
       viewBtn= findViewById(R.id.viewall);
       deleteBtn=findViewById(R.id.delete);
       stName= findViewById(R.id.stname);
       stDpt= findViewById(R.id.dpt);
       stPhone= findViewById(R.id.phone);
       stId=findViewById(R.id.identity);
        dh = new databaseHelper(this);
        db = dh.getWritableDatabase();

        rgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameIn = stName.getText().toString();
                dptIN= stDpt.getText().toString();
                phonStr =stPhone.getText().toString();
                idStr= stId.getText().toString();


                if (nameIn.isEmpty() ||dptIN.isEmpty() || phonStr.isEmpty() ||idStr.isEmpty()  ){
                    Toast.makeText(MainActivity.this, "No input field can be empty", Toast.LENGTH_SHORT).show();
                }else {
                     try {
                         int phoneIn = Integer.parseInt(phonStr);
                         int idIn= Integer.parseInt(idStr);
                         db.execSQL("INSERT INTO students (name, department, phone, id) VALUES ('" + nameIn + "', '"+ dptIN + "', " + phoneIn + ", " + idIn + ");");
                         Toast.makeText(MainActivity.this, "Student information successfully saved", Toast.LENGTH_SHORT).show();

                     }catch (Exception e){
                         Toast.makeText(MainActivity.this, "Phone and Identity should be Numbers", Toast.LENGTH_SHORT).show();

                     }
                }
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameIn = stName.getText().toString();
                dptIN= stDpt.getText().toString();
                phonStr =stPhone.getText().toString();
                idStr= stId.getText().toString();

                if (nameIn.isEmpty() ||dptIN.isEmpty() || phonStr.isEmpty() ||idStr.isEmpty()  ){
                    Toast.makeText(MainActivity.this, "Enter new information to be updated", Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        int phoneIn = Integer.parseInt(phonStr);
                        int idIn= Integer.parseInt(idStr);
                        db.execSQL("UPDATE students SET name = ?, department=?, phone=? WHERE id = ?", new Object[]{nameIn,dptIN, phoneIn, idIn});

                        Toast.makeText(MainActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();

                    }catch (Exception e){
                        Toast.makeText(MainActivity.this, "Invalid Identity try again", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });

        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = db.rawQuery("SELECT  * FROM students", null);
                String details ="";

                while(cursor.moveToNext()){
                    details+= " \nIdentity Number: " + "-" + cursor.getInt(0)+"\n" +
                               "Student Name :"  + "-" + cursor.getString(1) +"\n" +
                               "Department " + "-" +  cursor.getString(2) + "\n" +
                              "Phone Number" + "-" + cursor.getInt(3) +   "\n" ;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Student Information");
                builder.setMessage(details);
                builder.setPositiveButton("OK", null);
                builder.show();


            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idStr = stId.getText().toString();
                if (idStr.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter ID", Toast.LENGTH_SHORT).show();
                }else {
                    db.execSQL("DELETE FROM students WHERE id = ?", new Object[]{idStr});
                    Toast.makeText(MainActivity.this, "Delete Successful", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}