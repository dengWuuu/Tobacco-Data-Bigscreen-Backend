package cn.com.jetshine;

import cn.hutool.crypto.SecureUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Wu
 * @date 2024年04月23日 17:27
 */
@SpringBootTest
public class Md5Test {
    @Test
    void testMd5() {
        System.out.println(SecureUtil.md5("admin"));
    }
}
