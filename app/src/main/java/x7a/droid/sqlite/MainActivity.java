package x7a.droid.sqlite;

import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    DatabaseHelper myDB;
    EditText name, surname, marks, id;
    Button save_btn, list_btn, update_btn, delete_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//
//        });
        //
        name = (EditText)findViewById(R.id.name_txt);
        surname = (EditText)findViewById(R.id.surname_txt);
        marks = (EditText)findViewById(R.id.marks_txt);
        id = (EditText)findViewById(R.id.id_txt);
        save_btn = (Button)findViewById(R.id.send_btn);
        list_btn = (Button)findViewById(R.id.students_btn);
        update_btn = (Button)findViewById(R.id.update_btn);
        delete_btn = (Button)findViewById(R.id.delete_btn);

        //Listener
        myDB = new DatabaseHelper(this);
        save_btn.setOnClickListener(this);
        list_btn.setOnClickListener(this);
        update_btn.setOnClickListener(this);
        delete_btn.setOnClickListener(this);
        myDB.list_student();

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //SAVED
            case R.id.send_btn:
                boolean result = myDB.save_student(name.getText().toString(),
                                                   surname.getText().toString(),
                                                   marks.getText().toString());
                if (result)
                Toast.makeText(MainActivity.this, "Succed Add Student", Toast.LENGTH_SHORT).show();
                else
                Toast.makeText(MainActivity.this, "Failed Add Student", Toast.LENGTH_SHORT).show();
                break;
            case R.id.students_btn:
                Cursor students = myDB.list_student();
                if (students.getCount()==0){
                alert_message("Message", "NO DATA STUDENTS FOUND");
                return;
                }

            //APPEND data student to buffer
                StringBuffer buffer = new StringBuffer();
                while (students.moveToNext()){
                buffer.append("Id : "+ students.getString(0) +"\n");
                buffer.append("Name : "+ students.getString(1) + "\n");
                buffer.append("Surname : "+ students.getString(2) +"\n");
                buffer.append("Marks : "+ students.getString(3) +"\n\n\n");
                }

            //SHOW DATA STUDENT
            alert_message("List Students", buffer.toString());
                break;

            //UPDATE
            case R.id.update_btn:
                boolean update_result = myDB.update_student(id.getText().toString(),
                                                    name.getText().toString(),
                                                    surname.getText().toString(),
                                                    marks.getText().toString());
                if(update_result)
                    Toast.makeText(MainActivity.this, "Succeed Update", Toast.LENGTH_SHORT).show();
                break;

            //DELETE
            case R.id.delete_btn:
                Integer delete_result = myDB.delete_student(id.getText().toString());
                if(delete_result>0)
                    Toast.makeText(MainActivity.this, "Succeed Delete", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Failed Delete", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //SHOW ALERT
    private void alert_message(String title, String message) {
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setCancelable(true);
        build.setTitle(title);
        build.setMessage(message);
        build.show();
    }
}
