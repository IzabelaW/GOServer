package GameTests;

import Game.*;
import Rules.Rule;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.ServerSocket;

import static org.junit.Assert.*;

/**
 * Created by Izabela on 2016-12-29.
 */
public class BotTest {

    Bot bot;

    @Before
    public void setUp() throws Exception {
        bot = new Bot();
    }

    @Test
    public void getLogin() throws Exception {
        String login = bot.getLogin().toString();
        Assert.assertNotNull(login);
        Assert.assertEquals("BOT",login);
    }
}