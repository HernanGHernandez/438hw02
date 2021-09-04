package info.hernandez.a438_hw02;

import android.content.Context;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class userDAO_Test {
    @Test
    public void userInsertTest(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

//        UserDao testDB = Room.databaseBuilder(appContext, UserDatabase.class, UserDatabase.userDatabase)
//                .allowMainThreadQueries()
//                .build()
//                .getUserDao();
    }
}
