package info.hernandez.a438_hw02;

import retrofit2.http.GET;
import retrofit2.http.Path;
import java.util.List;

import retrofit2.Call;

public interface JsonPlaceHolderApi {
    @GET("users/{id}/posts")
    Call<List<Post>> getPosts(@Path("id") int id);
}
