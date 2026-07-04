package me.supcheg.javafile.filer;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.lang.model.element.NestingKind;
import javax.tools.FileObject;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

/** Minimal in-memory {@link Filer} test double: only {@code createSourceFile} is exercised by this library. */
final class FakeFiler implements Filer {

    String lastQualifiedName;
    Element[] lastOriginatingElements;
    final StringBuilder written = new StringBuilder();

    @Override
    public JavaFileObject createSourceFile(CharSequence name, Element... originatingElements) {
        this.lastQualifiedName = name.toString();
        this.lastOriginatingElements = originatingElements;
        return new JavaFileObject() {
            @Override
            public Writer openWriter() {
                return new Writer() {
                    @Override
                    public void write(char[] cbuf, int off, int len) {
                        written.append(cbuf, off, len);
                    }

                    @Override
                    public void flush() {}

                    @Override
                    public void close() {}
                };
            }

            @Override
            public JavaFileObject.Kind getKind() {
                return JavaFileObject.Kind.SOURCE;
            }

            @Override
            public boolean isNameCompatible(String simpleName, JavaFileObject.Kind kind) {
                return true;
            }

            @Override
            public NestingKind getNestingKind() {
                return null;
            }

            @Override
            public javax.lang.model.element.Modifier getAccessLevel() {
                return null;
            }

            @Override
            public java.net.URI toUri() {
                return java.net.URI.create("string:///" + lastQualifiedName);
            }

            @Override
            public String getName() {
                return lastQualifiedName;
            }

            @Override
            public java.io.InputStream openInputStream() {
                throw new UnsupportedOperationException();
            }

            @Override
            public OutputStream openOutputStream() {
                throw new UnsupportedOperationException();
            }

            @Override
            public Reader openReader(boolean ignoreEncodingErrors) {
                throw new UnsupportedOperationException();
            }

            @Override
            public CharSequence getCharContent(boolean ignoreEncodingErrors) {
                return written.toString();
            }

            @Override
            public long getLastModified() {
                return 0;
            }

            @Override
            public boolean delete() {
                return false;
            }
        };
    }

    @Override
    public JavaFileObject createClassFile(CharSequence name, Element... originatingElements) {
        throw new UnsupportedOperationException();
    }

    @Override
    public FileObject createResource(
            JavaFileManager.Location location,
            CharSequence pkg,
            CharSequence relativeName,
            Element... originatingElements) {
        throw new UnsupportedOperationException();
    }

    @Override
    public FileObject getResource(JavaFileManager.Location location, CharSequence pkg, CharSequence relativeName) {
        throw new UnsupportedOperationException();
    }
}
