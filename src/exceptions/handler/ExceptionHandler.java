package exceptions.handler;

import streams.ReaderWriterCreator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class ExceptionHandler {
    private static final String directory = "files\\exceptions.txt";
    public static void exceptionHandler(String message) {
        String exception = LocalDateTime.now() + " " + message;
        try (BufferedWriter wr = new BufferedWriter(ReaderWriterCreator.getAppend(directory))) {
            wr.write(exception);
            wr.newLine();
            wr.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getExceptions() {
        try (BufferedReader r = new BufferedReader(ReaderWriterCreator.getRead(directory))) {
            StringBuilder build = new StringBuilder("Exceptions:\n");
            String line;
            while ((line = r.readLine()) != null) {
                build.append(line).append('\n');
            }
            return build.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
