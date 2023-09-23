package tech.amereta.core;

import java.io.IOException;
import java.io.OutputStream;

public interface SourceCodeWriter {

    void writeSourceTo(SourceCode sourceCode, OutputStream outputStream) throws IOException;
}
