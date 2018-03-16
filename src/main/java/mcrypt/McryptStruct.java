package mcrypt;

import java.util.Arrays;

public class McryptStruct {
    private final String algorithmName;
    private final int keyLength;
    private final String keyGeneratorName;
    private final String mode;
    private final byte[] salt;
    private final String checksumAlgorithm;
    private final byte[] initVector;
    private final byte[] encryptedDataWithChecksum;

    McryptStruct(String algorithmName, int keyLength, String keyGeneratorName, String mode, byte[] salt, String checksumAlgorithm, byte[] initVector, byte[] encryptedDataWithChecksum) {
        this.algorithmName = algorithmName;
        this.keyLength = keyLength;
        this.keyGeneratorName = keyGeneratorName;
        this.mode = mode;
        this.salt = salt;
        this.checksumAlgorithm = checksumAlgorithm;
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
                ", checksumAlgorithm='" + checksumAlgorithm + '\'' +
                ", initVector=" + Arrays.toString(initVector) +
                ", encryptedDataWithChecksum=" + Arrays.toString(encryptedDataWithChecksum) +
                '}';
    }
}
