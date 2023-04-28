package com.group213.gymder;

import static org.junit.Assert.assertEquals;

import com.group5.gymder.Message;

import org.junit.*;

public class MessageTest {

    Message m = new Message("content", true);

    @Test
    public void testGetMessage() {
        assertEquals(m.getMessage(), "content");
    }
}
