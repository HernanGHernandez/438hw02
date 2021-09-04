package info.hernandez.a438_hw02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WelcomeScreen extends AppCompatActivity {

    TextView userOutput, idOutput, userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        userOutput =findViewById(R.id.username);
        String username = getIntent().getStringExtra("username");
        userOutput.setText(username);

        idOutput = findViewById(R.id.userId);
        int id = Integer.parseInt(getIntent().getStringExtra("id"));
        idOutput.setText(id);



        userInfo = findViewById(R.id.posts);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi api = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<Post>> call = api.getPosts(id);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse (Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    userInfo.setText("Code: " + response.code());
                    return;
                }

                List<Post> posts = response.body();
                for (Post post : posts) {
                    StringBuilder str = new StringBuilder();

                    str.append("ID: ").append(post.getId()).append("\n");
                    str.append("Title: ").append(post.getTitle()).append("\n");
                    str.append("Text: ").append(post.getText()).append("\n\n");

                    userInfo.append(str.toString());
                }
            }

            @Override
            public void onFailure (Call<List<Post>> call, Throwable t) {
                userInfo.setText(t.getMessage());
            }
        });
    }
}