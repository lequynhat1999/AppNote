package com.example.baicuoiky;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Database database;
    ListView listViewCV;
    ArrayList<CongViec> listCV;
    CongViecAdapter congViecAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Mapping();

        congViecAdapter = new CongViecAdapter(this,R.layout.row_cv,listCV);
        listViewCV.setAdapter(congViecAdapter);

        // tao database
        database = new Database(this,"note.sqlite",null,1);

        //tao bang CongViec
        database.QueryData("CREATE TABLE IF NOT EXISTS CongViec(Id INTEGER PRIMARY KEY AUTOINCREMENT, TenCV VARCHAR(200))");

//        database.QueryData("INSERT INTO CongViec VALUES(null,'Làm bài tập Android')");
//        database.QueryData("INSERT INTO CongViec VALUES(null,'Làm bài tập ASP.NET')");

        GetDataCV();

    }
    private  void GetDataCV()
    {
        Cursor dataCV = database.GetData("SELECT * FROM CongViec");
        listCV.clear();
        while (dataCV.moveToNext())
        {
            String ten = dataCV.getString(1);
            int id = dataCV.getInt(0);
            listCV.add(new CongViec(id,ten));
        }

        congViecAdapter.notifyDataSetChanged();
    }

    private void Mapping() {
        listViewCV = findViewById(R.id.listViewCV);
        listCV = new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuAdd)
        {
            DialogAdd();
        }

        return super.onOptionsItemSelected(item);
    }

    private  void DialogAdd()
    {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_cv);
        dialog.setCanceledOnTouchOutside(false);

        EditText editTextTenCV;
        Button btnAdd,btnCancel;
        editTextTenCV = dialog.findViewById(R.id.editTextTenCV);
        btnAdd = dialog.findViewById(R.id.btnAdd);
        btnCancel = dialog.findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenCV = editTextTenCV.getText().toString();
                if(tenCV.equals(""))
                {
                    Toast.makeText(MainActivity.this, "Vui long nhap them du lieu", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    database.QueryData("INSERT INTO CongViec VALUES(null,'"+ tenCV+"')");
                    Toast.makeText(MainActivity.this, "Da them", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    GetDataCV();
                }
            }
        });


        dialog.show();
    }

    public void DialogEdit(String ten, int id)
    {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_edit_cv);
        dialog.setCanceledOnTouchOutside(false);

        EditText editTextEditTenCV = dialog.findViewById(R.id.editTextEditTenCV);
        Button btnEdit = dialog.findViewById(R.id.btnEdit);
        Button btnEditCancel = dialog.findViewById(R.id.btnEditCancel);
        editTextEditTenCV.setText(ten);

        btnEditCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextEditTenCV.getText().toString();
                database.QueryData("UPDATE CongViec SET TenCV = '"+name+"' WHERE Id = '"+id +"' ");
                Toast.makeText(MainActivity.this, "Da sua", Toast.LENGTH_SHORT).show();
                dialog.cancel();
                GetDataCV();
            }
        });

        dialog.show();
    }

    public void DialogDelete(String ten, int id)
    {
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(this);
        dialogDelete.setMessage("Ban co muon xoa cong viec  " + ten + "khong?");
        dialogDelete.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.QueryData("DELETE FROM CongViec WHERE Id = '"+id+"'");
                Toast.makeText(MainActivity.this, "Da xoa", Toast.LENGTH_SHORT).show();
                dialog.cancel();
                GetDataCV();
            }
        });

        dialogDelete.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialogDelete.show();
    }
}