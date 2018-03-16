package mcrypt;

public class McryptStructBuilder {
    private Parser parser;

    int saltIvFlag;
    String algorithmName;
    int keyLength;
    String keyGeneratorName;
    private String mode;
    private byte[] salt;
    private String checksumAlgorithm;
    private byte[] initVector;
    private byte[] encryptedDataWithChecksum;

    public McryptStructBuilder setSaltIvFlag(byte saltIvFlag) {
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
        return ((saltIvFlag & 0b01000000) != 0);
    }

    public McryptStructBuilder setSalt(byte[] salt) {
        this.salt = salt;
        return this;
    }

    public McryptStructBuilder withParser(Parser parser) {
        this.parser = parser;

        checkHeader(parser.nextByteArray(3));

        setSaltIvFlag(parser.nextByte());
        setAlgorithmName(parser.nextString());
        setKeyLength(parser.nextLittleEndian());
        setMode(parser.nextString());
        setKeyGeneratorName(parser.nextString());

        if (hasSalt()) {
            setSalt(parseSalt());
        }

        setChecksumAlgorithm(parser.nextString());

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
        return (saltIvFlag & 0b10000000) != 0;
    }

    private void checkHeader(byte[] header) {
        if (header[0] != 0) throw new IllegalArgumentException();

        if (header[1] != 'm') throw new IllegalArgumentException();

        if (header[2] != 3) throw new IllegalArgumentException();
    }

    private byte[] parseSalt() {
        return parser.nextByteArray(parser.nextByte() -1);
    }

    public McryptStructBuilder setChecksumAlgorithm(String checksumAlgorithm) {
        this.checksumAlgorithm = checksumAlgorithm;
        return this;
    }

    public McryptStructBuilder setInitVector(byte[] initVector) {
        this.initVector = initVector;
        return this;
    }

    public void setEncryptedDataWithChecksum(byte[] encryptedDataWithChecksum) {
        this.encryptedDataWithChecksum = encryptedDataWithChecksum;
    }

    public McryptStruct build() {
        return new McryptStruct(
                algorithmName,
                keyLength,
                keyGeneratorName,
                mode,
                salt,
                checksumAlgorithm,
                initVector,
                encryptedDataWithChecksum
        );
    }

}
