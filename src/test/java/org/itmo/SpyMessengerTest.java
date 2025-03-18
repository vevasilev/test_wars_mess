package org.itmo;


import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SpyMessengerTest {

    @Test
    void testSendMessage_nullSender() {
        SpyMessenger messenger = new SpyMessenger();
        Exception exception = assertThrows(Exception.class, () -> {
            messenger.sendMessage(null, "Bob", "Top Secret", "1234");
        });
    }

    @Test
    void testSendMessage_nullReceiver() {
        SpyMessenger messenger = new SpyMessenger();
        Exception exception = assertThrows(Exception.class, () -> {
            messenger.sendMessage("Alice", null, "Top Secret", "1234");
        });
    }

    @Test
    void testSendMessage_nullMessage() {
        SpyMessenger messenger = new SpyMessenger();
        Exception exception = assertThrows(Exception.class, () -> {
            messenger.sendMessage("Alice", "Bob", null, "1234");
        });
    }

    @Test
    void testSendMessage_nullPasscode() {
        SpyMessenger messenger = new SpyMessenger();
        Exception exception = assertThrows(Exception.class, () -> {
            messenger.sendMessage("Alice", "Bob", "Top Secret", null);
        });
    }

    @Test
    void testReadMessage_nullUser() {
        SpyMessenger messenger = new SpyMessenger();
        Exception exception = assertThrows(Exception.class, () -> {
            messenger.readMessage(null, "1223");
        });
    }

    @Test
    void testReadMessage_nullPasscode() {
        SpyMessenger messenger = new SpyMessenger();
        Exception exception = assertThrows(Exception.class, () -> {
            messenger.readMessage("Alice", null);
        });
    }

    @Test
    void testSendMessage_messagesNotExists() {
        SpyMessenger messenger = new SpyMessenger();
        assertNull(messenger.readMessage("Alice", "123"));
    }

    @Test
    void testSendMessage_messagesIsExists() {
        SpyMessenger messenger = new SpyMessenger();
        messenger.sendMessage("Alice", "Bob", "Top Secret", "1234");
        assertEquals("Top Secret", messenger.readMessage("Bob", "1234"));
    }

    @Test
    void testSendMessage_messagesMoreThan() {
        SpyMessenger messenger = new SpyMessenger();
        messenger.sendMessage("Alice", "Bob", "Top Secret", "1");
        messenger.sendMessage("Alice", "Bob", "Top Secret", "2");
        messenger.sendMessage("Alice", "Bob", "Top Secret", "3");
        messenger.sendMessage("Alice", "Bob", "Top Secret", "4");
        messenger.sendMessage("Alice", "Bob", "Top Secret", "5");
        messenger.sendMessage("Alice", "Bob", "Top Secret", "6");
        assertNull(messenger.readMessage("Bob", "1"));
    }

    @Test
    void testSendMessage_messagesMoreThanTime_clearMessage() throws InterruptedException {
        SpyMessenger messenger = new SpyMessenger();
        messenger.sendMessage("Alice", "Bob", "Top Secret", "1");
        TimeUnit.MILLISECONDS.sleep(2000);
        assertNull(messenger.readMessage("Bob", "1"));
    }

    @Test
    void testSendMessage_messagesMoreThanTime_clearMessages() throws InterruptedException {
        SpyMessenger messenger = new SpyMessenger();
        messenger.sendMessage("Alice", "Bob", "Top Secret", "1");
        messenger.sendMessage("Alice", "Bob", "Top Secret", "2");
        messenger.sendMessage("Alice", "Bob", "Top Secret", "3");
        TimeUnit.MILLISECONDS.sleep(3500);
        assertNull(messenger.readMessage("Bob", "1"));
        assertNull(messenger.readMessage("Bob", "2"));
        assertNull(messenger.readMessage("Bob", "3"));
    }

    @Test
    void testSendMessage_uncorrectReader() {
        SpyMessenger messenger = new SpyMessenger();
        messenger.sendMessage("Alice", "Bob", "Top Secret", "1");
        messenger.readMessage("Alex", "1");
        assertNull(messenger.readMessage("Bob", "1"));
    }
}