package info.hernandez.a438_hw02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    // note swithed name with username
    EditText userId, password, username;
    Button register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userId = findViewById(R.id.userId);
        password = findViewById(R.id.RegPassword);
        username = findViewById(R.id.username);


        register = findViewById(R.id.regBtn);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View  v){
                // create user entity
                final UserEntity userEntity = new UserEntity();
                // username/password
                userEntity.setUsername(username.getText().toString());
                userEntity.setPassword(password.getText().toString());
                // get id
                userEntity.setUserId(Integer.parseInt(userId.getText().toString()));


                if(validateReg(userEntity)){
                    //insert
                    UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                    final UserDao userDao = userDatabase.userDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run(){
                            // register
                            userDao.registerUser(userEntity);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "User Registered!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            });
                        }
                    }).start();
                }else{
                    Toast.makeText(getApplicationContext(), "Error, must fill", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private Boolean validateReg(UserEntity userEntity){
        if(userEntity.getUsername().isEmpty() || userEntity.getPassword().isEmpty() || userEntity.getUsername().isEmpty()){
            return false;
        }
        return true;
    }
}