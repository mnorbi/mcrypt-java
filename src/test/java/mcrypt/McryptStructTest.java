package mcrypt;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.nio.file.Paths;

import static java.nio.file.Files.readAllBytes;
import static mcrypt.McryptStructBuilder.INIT_VECTOR_FLAG;
import static mcrypt.McryptStructBuilder.SALT_FLAG;
import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class McryptStructTest {
    @Test
    public void testEqualsHashCode() {
        EqualsVerifier
                .forClass(McryptStruct.class)
                .verify();
    }

    @Test
    public void testToString() {
        //GIVEN
        McryptStruct subject = new McryptStructBuilder()
                .setSaltIvFlag(INIT_VECTOR_FLAG | SALT_FLAG)
                .setAlgorithmName("someAlgorithm")
                .setKeyLength(32)
                .setKeyGeneratorName("someKeyGenerator")
                .setMode("someMode")
                .setSalt(new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
                .setChecksumAlgorithmName("someChecksumAlgorithm")
                .setInitVector(new byte[]{-9, -8, -7, -6, -5, -4, -3, -2, -1, 0})
                .setEncryptedDataWithChecksum(new byte[]{0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5}).build();
        //WHEN
        String actual = subject.toString();

        //THEN
        String expected = "McryptStruct{" +
                "algorithmName='someAlgorithm', " +
                "keyLength=32, " +
                "keyGeneratorName='someKeyGenerator', " +
                "mode='someMode', " +
                "salt=[0, 1, 2, 3, 4, 5, 6, 7, 8, 9], " +
                "checksumAlgorithmName='someChecksumAlgorithm', " +
                "initVector=[-9, -8, -7, -6, -5, -4, -3, -2, -1, 0], " +
                "encryptedDataWithChecksum=[0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5]}";
        assertEquals(expected, actual);
    }

    @Test
    public void testCreateFrom() {
        //GIVEN
        String fileName = "src/test/resources/encrypted.nc";

        //WHEN
        McryptStruct actual = McryptStruct.createFrom(getBytesFrom(fileName));

        //THEN
        McryptStruct expected = new McryptStructBuilder()
                .setSaltIvFlag(SALT_FLAG)
                .setAlgorithmName("twofish")
                .setKeyLength(32)
                .setKeyGeneratorName("mcrypt-sha1")
                .setMode("cbc")
                .setSalt(new byte[]{-121, -101, -40, -102, -63, 5, 28, -59, 123, 77, 91, -41, -8, -71, 52, 89, -15, 71, 16, -62})
                .setChecksumAlgorithmName("sha1")
                .setInitVector(null)
                .setEncryptedDataWithChecksum(new byte[]{36, 14, 108, -38, 109, -120, -101, 106, -111, 66, 44, -3, 85, -50, 56, 40, 4, 29, -107, -128, -45, -31, -81, 63, -56, 64, -55, 5, -66, -106, 120, 11, -58, -10, 12, -42, -19, 70, 49, 71, 113, -49, 49, 80, -76, 116, 23, -33, -111, 108, 97, 78, -77, 49, -54, -59, -61, 86, 46, 110, -11, -125, 89, 0}).build();
        assertEquals(expected, actual);
    }

    private byte[] getBytesFrom(String fileName) {
        try {
            return readAllBytes(Paths.get(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
