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
        String id = getIntent().getStringExtra("id");
        idOutput.setText(id);



        userInfo = findViewById(R.id.posts);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi api = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<Post>> call = api.getPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()){
                    userInfo.setText("Code: " + response.code());
                    return;
                }
                List<Post> posts = response.body();
                for(Post post : posts){
                    String content = " ";
                    content += "ID: " + post.getId() + "\n";
                    content += "User ID: " + post.getUserId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content +=  "Text: " + post.getText() +  "\n\n";

                    userInfo.append(content);
                }
            }

            @Override
            public void onFailure (Call<List<Post>> call, Throwable t) {
                userInfo.setText(t.getMessage());
            }
        });
    }
}