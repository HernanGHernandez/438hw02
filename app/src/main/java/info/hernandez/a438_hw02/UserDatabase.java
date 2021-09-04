package info.hernandez.a438_hw02;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// class for database
@Database(entities = {UserEntity.class}, version = 3, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {


    public abstract UserDao userDao();


    private static final String dbName = "user";
    public static UserDatabase userDatabase;

    public static synchronized  UserDatabase getUserDatabase(Context context){
        if(userDatabase ==  null){
            userDatabase = Room.databaseBuilder(context, UserDatabase.class, dbName)
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return userDatabase;
    }
}
