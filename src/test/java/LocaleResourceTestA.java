import com.github.julyss2019.nemolibrary.bukkit.common.locale.Locale;
import com.github.julyss2019.nemolibrary.bukkit.common.locale.resource.LocaleResource;
import com.github.julyss2019.nemolibrary.bukkit.common.locale.resource.impl.YamlLocaleResource;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocaleResourceTestA {
    @Test
    public void test() {
        Locale locale = Locale.ZH_CN;
        File folder = new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources");
        LocaleResource localeResource = new YamlLocaleResource(locale, folder, null, null);

        assertEquals("成功", localeResource.getString("msg.success"));
        assertEquals("失败", localeResource.getString("msg.failed"));
        assertEquals(Arrays.asList("1", "2"), localeResource.getStringList("msg.texts"));
        assertEquals("test", localeResource.getNode("node").getNode("a").getString("b"));
    }
}
