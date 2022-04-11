import com.github.julyss2019.nemolibrary.bukkit.common.text.PlaceholderContainer;
import com.github.julyss2019.nemolibrary.bukkit.common.text.Texts;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextsTestA {
    @Test
    public void testPlaceholder() {
        PlaceholderContainer placeholderContainer = new PlaceholderContainer()
                .put("key1", "value1")
                .put("key2", "value2");

        assertEquals("value1value2", Texts.setPlaceholders("${key1}${key2}", placeholderContainer));
        assertEquals("value2value1", Texts.setPlaceholders("${key2}${key1}", placeholderContainer));
        assertEquals("${key2}value1", Texts.setPlaceholders("$${key2}${key1}", placeholderContainer));
        assertEquals("${key1", Texts.setPlaceholders("${key1", placeholderContainer));
        assertEquals("$key1}", Texts.setPlaceholders("$key1}", placeholderContainer));
        assertEquals("${key3}", Texts.setPlaceholders("${key3}", placeholderContainer));
        assertEquals("$w", Texts.setPlaceholders("$w", placeholderContainer));
        assertEquals("$", Texts.setPlaceholders("$", placeholderContainer));

        assertEquals("$$$", Texts.setPlaceholders("$$$$$$", new PlaceholderContainer()));

        long l = System.currentTimeMillis();

        for (int i = 0; i < 10000; i++) {
            Texts.setPlaceholders("$value2value1", placeholderContainer);
        }

        System.out.println(System.currentTimeMillis() - l);
    }
}
