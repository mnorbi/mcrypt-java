package mcrypt;

public class Parser {
    private final byte[] input;
    private int cur = -1;

    Parser(byte[] input) {
        this.input = input;
    }

    public byte nextByte() {
        return input[++cur];
    }

    public byte[] nextByteArray(int size) {
        byte[] ret = new byte[size];
        for (int a = 0; a < size; ++a) {
            ret[a] = nextByte();
        }
        return ret;
    }

    public String nextString() {
        int lo = cur;
        return new String(input, ++lo, toStringEnd() - lo);
    }

    private int toStringEnd() {
        while (nextByte() != 0);
        return cur;
    }

    public int nextLittleEndian() {
        byte lo = nextByte();
        byte hi = nextByte();

        return lo + (hi << 8);
    }

    public byte[] nextByteArray() {
        return nextByteArray(input.length-(cur+1));
    }
}
