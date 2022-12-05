package net.kettlemc.gehirnfick;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        String programPath = args.length >= 1 ? args[0] : "";
        String defaultString = args.length >= 2 ? args[1] : "";

        if (programPath.isEmpty()) {
            System.err.println("Please specify what program to run using an argument! (java -jar brainfuck.jar program.txt abc)");
            return;
        }

        Path path = Paths.get(programPath);

        if (path.toFile().exists() && path.toFile().isFile()) {

            String content = getContentFromFile(path);
            BrainFuckInterpreter versteher = new BrainFuckInterpreter().setProgram(content);

            if (!defaultString.isEmpty()) {
                versteher.setDefaultBand(defaultString);
            }

            versteher.execute();

        } else {
            System.err.println("File doesn't exist!");
        }
    }

    public static String getContentFromFile(Path path) {
        try {
            return new String(Files.readAllBytes(path));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
