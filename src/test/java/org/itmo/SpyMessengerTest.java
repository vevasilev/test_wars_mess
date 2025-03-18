package org.itmo;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.concurrent.TimeUnit;

class SpyMessengerTest {
    @Test
    void testMessageSelfDestructionAfterReading() {
        SpyMessenger messenger = new SpyMessenger();
        messenger.sendMessage("Alice", "Bob", "Top Secret", "1234");

        // Первое чтение — сообщение должно быть доступно
        assertEquals("Top Secret", messenger.readMessage("Bob", "1234"));
    }

}