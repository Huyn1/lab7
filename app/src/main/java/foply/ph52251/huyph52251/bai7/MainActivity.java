package foply.ph52251.huyph52251.bai7;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db;
    Button btn1, btn2, btn3;
    TextView tv;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        db = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btn1 = findViewById(R.id.btn1);
        tv = findViewById(R.id.tv);
        btn1 = findViewById(R.id.btn1);

        btn1.setOnClickListener(v -> {
            insert(tv);
        });
        btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(v -> {
            update(tv);
        });
        btn3 = findViewById(R.id.btn3);
        btn3.setOnClickListener(v -> {
            delete(tv);
        });
        from(tv);

    }

    String id = "";
    todo todo = null;

    public void delete(TextView tv) {
        id = "title1";
        db.collection("todo").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                tv.setText("delete thanh cong");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                tv.setText(e.getMessage());
            }
        });
    }

    public void insert(TextView tv) {
        id = UUID.randomUUID().toString();// lay 1 uid bất kyg
        //tao doi tuong
        todo = new todo(id, "title2", "content2");
        //bien doi sang doi tuong co thethao tac voi firebase
        HashMap<String, Object> map = todo.convert();
        //insert
        db.collection("todo").document(id)
                .set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        tv.setText("insert thanh cong");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        tv.setText("insert that bai");
                    }
                });
    }

    ;

    public void update(TextView tv) {
        id = "title1";
        todo = new todo(id, " sua title1", " sua content1");
        db.collection("todo").document(todo.getId()).update(todo.convert()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                tv.setText("update thanh cong");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                tv.setText(e.getMessage());
            }
        });


    }

    String str = "";

    public ArrayList<todo> from(TextView tv) {
        ArrayList<todo> list = new ArrayList<>();
        db.collection("todo").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            str = "";
                            for(QueryDocumentSnapshot document : task.getResult()){
                                todo todo1 = document.toObject(todo.class);
                                str += "id: " + todo1.getId() +"\n";
                                list.add(todo1);

                            }
                            tv.setText(str);
                        }else {
                            tv.setText("doc du lieu that bai");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            tv.setText(e.getMessage());
            }
        });
        return list;
    }

}


