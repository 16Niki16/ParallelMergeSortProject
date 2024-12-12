package streams;

import exceptions.handler.ExceptionHandler;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class ReaderWriterCreator {

    public static Reader getRead(String directory) {
        try {
            return new FileReader(directory);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Could not use the directory for reading", e);
        }
    }

    public static Writer getAppend(String directory) {
        try {
            return new FileWriter(directory, true);
        } catch (IOException e) {
            throw new RuntimeException("Could not use the directory for writing", e);
        }
    }

    public static Writer getNotAppend(String directory) {
        try {
            return new FileWriter(directory, false);
        } catch (IOException e) {
            throw new RuntimeException("Could not use the directory for writing", e);
        }
    }
}
