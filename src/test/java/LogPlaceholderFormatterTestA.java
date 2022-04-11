import com.github.julyss2019.nemolibrary.bukkit.common.logger.LogPlaceholderFormatter;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class LogPlaceholderFormatterTestA {
    @Test
    public void test() {
        assertEquals("{b a", LogPlaceholderFormatter.format("{b a", "a", "b"));
        assertEquals("b} a", LogPlaceholderFormatter.format("b} a", "a", "b"));
        assertEquals("{b} a", LogPlaceholderFormatter.format("{b} a", "a", "b"));

        assertEquals("{0} {1} a b", LogPlaceholderFormatter.format("{{0}} {{1}} {0} {1}", "a", "b"));
        assertEquals("{0} {{1} a b", LogPlaceholderFormatter.format("{{0}} {{1} {0} {1}", "a", "b"));
        assertEquals("{0} b} a b.", LogPlaceholderFormatter.format("{{0}} {1}} {0} {1}.", "a", "b"));
    }
}
