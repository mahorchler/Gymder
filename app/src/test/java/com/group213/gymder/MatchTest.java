package com.group213.gymder;

import static org.junit.Assert.assertEquals;

import com.group5.gymder.Match;
import com.group5.gymder.Message;
import com.group5.gymder.User;

import org.junit.*;
import org.mockito.*;

public class MatchTest {

    User u1 = new User();
    User u2 = new User();
    private Match m = new Match(u1, u2);

    @Test
    public void testMatchGetUser(){
        assertEquals(u1, m.getU1());
        assertEquals(u2, m.getU2());
    }

    @Test
    public void testAddMessage() {
        Message mess = new Message("Hello this is the first message", false, new User());
        m.addMessage(mess);
        assertEquals(m.getMessages().get(0), mess);
    }
}
