import com.github.julyss2019.nemolibrary.bukkit.common.logger.Level;
import com.github.julyss2019.nemolibrary.bukkit.common.logger.MessageContext;
import com.github.julyss2019.nemolibrary.bukkit.common.logger.layout.impl.PatternLayout;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PatternLayoutTestA {
    @Test
    public void test() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Plugin plugin = mock(Plugin.class);
        MessageContext messageContext = new MessageContext(plugin, Level.DEBUG, "message", ChatColor.RED);

        assertEquals(sdf.format(new Date()) + " message", new PatternLayout("%d{yyyy/MM/dd} %m").format(messageContext));
        assertEquals("[DEBUG] [" + sdf.format(new Date()) + "] message", new PatternLayout("[%l] [%d{yyyy/MM/dd}] %m").format(messageContext));
    }
}
