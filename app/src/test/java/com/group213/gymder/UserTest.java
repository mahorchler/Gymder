package com.group213.gymder;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.group5.gymder.User;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class UserTest {

    @Mock
    Drawable d;

    @Before
    public void setup(){
        d =  mock(Drawable.class);
    }
    User u = new User("id","email", "password");

    @Test
    public void testGetPassword(){
        assertEquals(u.getPassword(), "password");
    }

    @Test
    public void testSetAge(){
        u.setAge("30");
        assertEquals("30", u.getAge());
    }

    @Test
    public void testSetEmail(){
        u.setEmail("x@gmail.com");
        assertEquals(u.getEmail(), "x@gmail.com");
    }

    @Test
    public void testSetName(){
        u.setName("bob");
        assertEquals(u.getName(), "bob");
    }

    @Test
    public void testSetProfile(){
        u.setProfilePicture(d);
        assertEquals(d, u.getProfilePicture());
    }

}
