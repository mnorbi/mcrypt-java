package mcrypt;

public class McryptStructBuilder {
    static final int SALT_FLAG = 0b01000000;
    static final int INIT_VECTOR_FLAG = 0b10000000;
    private int saltIvFlag;
    private String algorithmName;
    private int keyLength;
    private String keyGeneratorName;
    private String mode;
    private byte[] salt;
    private String checksumAlgorithmName;
    private byte[] initVector;
    private byte[] encryptedDataWithChecksum;

    public McryptStructBuilder setSaltIvFlag(int saltIvFlag) {
        this.saltIvFlag = saltIvFlag;
        return this;
    }

    public McryptStructBuilder setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
        return this;
    }

    public McryptStructBuilder setKeyLength(int keyLength) {
        this.keyLength = keyLength;
        return this;
    }

    public McryptStructBuilder setMode(String mode) {
        this.mode = mode;
        return this;
    }

    public McryptStructBuilder setKeyGeneratorName(String keyGeneratorName) {
        this.keyGeneratorName = keyGeneratorName;
        return this;
    }

    public boolean hasSalt() {
        return ((saltIvFlag & SALT_FLAG) != 0);
    }

    public McryptStructBuilder setSalt(byte[] salt) {
        this.salt = salt;
        return this;
    }

    public McryptStructBuilder fromInput(byte[] input) {
        Parser parser = new Parser(input);

        checkHeader(parser.nextByteArray(3));

        setSaltIvFlag(parser.nextByte());
        setAlgorithmName(parser.nextString());
        setKeyLength(parser.nextLittleEndian());
        setMode(parser.nextString());
        setKeyGeneratorName(parser.nextString());

        if (hasSalt()) {
            setSalt(parseSalt(parser));
        }

        setChecksumAlgorithmName(parser.nextString());

        if (hasInitVector()) {
            setInitVector(parseInitVector());
        }

        setEncryptedDataWithChecksum(parser.nextByteArray());

        return this;
    }

    private byte[] parseInitVector() {
        throw new UnsupportedOperationException();
    }

    private boolean hasInitVector() {
        return (saltIvFlag & INIT_VECTOR_FLAG) != 0;
    }

    private void checkHeader(byte[] header) {
        if (header[0] != 0) throw new IllegalArgumentException();

        if (header[1] != 'm') throw new IllegalArgumentException();

        if (header[2] != 3) throw new IllegalArgumentException();
    }

    private byte[] parseSalt(Parser parser) {
        return parser.nextByteArray(parser.nextByte() -1);
    }

    public McryptStructBuilder setChecksumAlgorithmName(String checksumAlgorithmName) {
        this.checksumAlgorithmName = checksumAlgorithmName;
        return this;
    }

    public McryptStructBuilder setInitVector(byte[] initVector) {
        this.initVector = initVector;
        return this;
    }

    public McryptStructBuilder setEncryptedDataWithChecksum(byte[] encryptedDataWithChecksum) {
        this.encryptedDataWithChecksum = encryptedDataWithChecksum;
        return this;
    }

    public McryptStruct build() {
        return new McryptStruct(
                algorithmName,
                keyLength,
                keyGeneratorName,
                mode,
                salt,
                checksumAlgorithmName,
                initVector,
                encryptedDataWithChecksum
        );
    }

}
