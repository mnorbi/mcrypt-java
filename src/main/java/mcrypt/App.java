package mcrypt;

import java.nio.file.Files;
import java.nio.file.Paths;

public class App {
    public static void main(String[] args) throws Exception {
        String fileName = "src/test/resources/encrypted.nc";
        byte[] input = Files.readAllBytes(Paths.get(fileName));
        McryptStruct mcryptStruct = McryptStruct.createFrom(input);

        System.out.println(mcryptStruct);
    }

}
