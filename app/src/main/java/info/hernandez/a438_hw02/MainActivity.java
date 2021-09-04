package info.hernandez.a438_hw02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    // text view for api
    private TextView textViewResult;
    // button
    private EditText username, password;
    private Button loginBtn, regBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult  = findViewById(R.id.text_view_result);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        loginBtn = findViewById(R.id.loginBtn);
        regBtn = findViewById(R.id.regBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                final String usernameTxt =  username.getText().toString();
                final String passwordTxt = password.getText().toString();
                //no input
                if(usernameTxt.isEmpty() || passwordTxt.isEmpty()){
                    Toast.makeText(MainActivity.this, "Username/Password input missing", Toast.LENGTH_LONG);
                }
                // login
                else{
                    // do Query
                    UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                    final UserDao userDao = userDatabase.userDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run(){
                            UserEntity userEntity =  userDao.login(usernameTxt, passwordTxt);
                            if(userEntity == null){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run(){
                                        Toast.makeText(getApplicationContext(), "Invalid Credentials",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }else{
                                // CALL API
                                // TEST
                                String username = userEntity.username;
                                int id = userEntity.userId;
                                Intent intent = new Intent(MainActivity.this, WelcomeScreen.class)
                                        .putExtra("username", username)
                                        .putExtra("id", id);
                                startActivity(intent);

                            }
                        }
                    }).start();
                }
            }
        });


        // register data in room first
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        // login method
//        public void login(){
//            Call<Post> loginResponseCall = Api.getUser().userLogin();
//            userLogin userLogin = new userLogin();
//
//
//        }
//
//        // Simple get request with given rest API
//        // API Client
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://jsonplaceholder.typicode.com")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
//
//        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();
//
//        call.enqueue(new Callback<List<Post>>() {
//            @Override
//            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
//                if(!response.isSuccessful()){
//                    textViewResult.setText("Code: " + response.code());
//                    return;
//                }
//                List<Post> posts = response.body();
//                for(Post post : posts){
//                    String content = " ";
//                    content += "ID: " + post.getId() + "\n";
//                    content += "User ID: " + post.getUserId() + "\n";
//                    content += "Title: " + post.getTitle() + "\n";
//                    content +=  "Text: " + post.getText() +  "\n\n";
//
//                    textViewResult.append(content);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Post>> call, Throwable t) {
//                textViewResult.setText(t.getMessage());
//
//            }
//        });
    }

    // Login
}