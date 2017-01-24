package ke.co.stashare.dummylist.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ke.co.stashare.dummylist.R;
import ke.co.stashare.dummylist.adapters.MyAdapter;

public class MainActivity extends AppCompatActivity {

    private EditText name;
    private EditText id;
    private Button add;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            String name2 = bundle.getString("NAME");
            String id2= bundle.getString("ID");
            String error=bundle.getString("ERROR");

            if(error.equalsIgnoreCase("")) {
                String id_name = name2 + "," + id2 + " " + "added";

                Snackbar.make(findViewById(android.R.id.content), id_name, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                name.setText("");
                id.setText("");
            }
            else
            {
                Snackbar.make(findViewById(android.R.id.content), error, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar1) ;

        setSupportActionBar(toolbar);


        RecyclerView rv = (RecyclerView)findViewById(R.id.recycler);
        rv.setHasFixedSize(true);
        MyAdapter adapter = new MyAdapter(new String[]{"08:00", "08:01", "08:02", "02:09", "07:00" , "11:07" , "12:57"});
        rv.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        // Item Decorator:
        rv.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.divider)));

        rv.setLayoutManager(llm);

    }

    public void buttonClick(final View view) {
        Runnable runnable = new Runnable() {
        public void run() {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            String id_no = "";
            String name_text="";
            String error="";

            name = (EditText) findViewById(R.id.editName);
            id = (EditText) findViewById(R.id.editId);
            if((!name.getText().toString().equals("")&&!id.getText().toString().equals(""))) {


                //Getting values from edit texts
                id_no = id.getText().toString().trim();
                name_text = name.getText().toString().trim();
                bundle.putString("ID", id_no);
                bundle.putString("NAME",name_text);
                bundle.putString("ERROR","");

            }
            else
            {
                error="Please fill in all the fields";

                bundle.putString("ERROR",error);

            }

            msg.setData(bundle);
            handler.sendMessage(msg);
        }
    };

        Thread mythread = new Thread(runnable);
        mythread.start();
    }
}
