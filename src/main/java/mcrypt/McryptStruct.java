package mcrypt;

import java.util.Arrays;
import java.util.Objects;

public final class McryptStruct {
    private final String algorithmName;
    private final Integer keyLength;
    private final String keyGeneratorName;
    private final String mode;
    private final byte[] salt;
    private final String checksumAlgorithmName;
    private final byte[] initVector;
    private final byte[] encryptedDataWithChecksum;

    public String getAlgorithmName() {
        return algorithmName;
    }

    public int getKeyLength() {
        return keyLength;
    }

    public String getKeyGeneratorName() {
        return keyGeneratorName;
    }

    public String getMode() {
        return mode;
    }

    public byte[] getSalt() {
        return salt;
    }

    public String getChecksumAlgorithmName() {
        return checksumAlgorithmName;
    }

    public byte[] getInitVector() {
        return initVector;
    }

    public byte[] getEncryptedDataWithChecksum() {
        return encryptedDataWithChecksum;
    }

    McryptStruct(String algorithmName, int keyLength, String keyGeneratorName, String mode, byte[] salt, String checksumAlgorithmName, byte[] initVector, byte[] encryptedDataWithChecksum) {
        this.algorithmName = algorithmName;
        this.keyLength = keyLength;
        this.keyGeneratorName = keyGeneratorName;
        this.mode = mode;
        this.salt = salt;
        this.checksumAlgorithmName = checksumAlgorithmName;
        this.initVector = initVector;
        this.encryptedDataWithChecksum = encryptedDataWithChecksum;
    }

    public static McryptStruct createFrom(byte[] input) {
        return new McryptStructBuilder().fromInput(input).build();
    }

    @Override
    public String toString() {
        return "McryptStruct{" +
                "algorithmName='" + algorithmName + '\'' +
                ", keyLength=" + keyLength +
                ", keyGeneratorName='" + keyGeneratorName + '\'' +
                ", mode='" + mode + '\'' +
                ", salt=" + Arrays.toString(salt) +
                ", checksumAlgorithmName='" + checksumAlgorithmName + '\'' +
                ", initVector=" + Arrays.toString(initVector) +
                ", encryptedDataWithChecksum=" + Arrays.toString(encryptedDataWithChecksum) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        McryptStruct that = (McryptStruct) o;
        return Objects.equals(algorithmName, that.algorithmName) &&
                Objects.equals(keyLength, that.keyLength) &&
                Objects.equals(keyGeneratorName, that.keyGeneratorName) &&
                Objects.equals(mode, that.mode) &&
                Arrays.equals(salt, that.salt) &&
                Objects.equals(checksumAlgorithmName, that.checksumAlgorithmName) &&
                Arrays.equals(initVector, that.initVector) &&
                Arrays.equals(encryptedDataWithChecksum, that.encryptedDataWithChecksum);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(algorithmName, keyLength, keyGeneratorName, mode, checksumAlgorithmName);
        result = 31 * result + Arrays.hashCode(salt);
        result = 31 * result + Arrays.hashCode(initVector);
        result = 31 * result + Arrays.hashCode(encryptedDataWithChecksum);

        return result;
    }
}
