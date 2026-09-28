package me.supcheg.javafile.typed;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.lang.constant.ClassDesc;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Stream;

/// Reads class files for an [Env].
@FunctionalInterface
public interface ClassSource {

    /// Reads the class file of `type`.
    ///
    /// @param type the class or interface
    /// @return the class file bytes, or empty if this source has none
    /// @throws UncheckedIOException if reading fails
    Optional<byte[]> read(ClassDesc type);

    /// Returns a source asking this one first, then `next`.
    ///
    /// @param next the fallback source
    /// @return the combined source
    default ClassSource or(ClassSource next) {
        return type -> read(type).or(() -> next.read(type));
    }

    /// Reads `internal/Name.class` resources of a class loader; JDK classes resolve through `jrt`.
    ///
    /// @param loader the class loader
    /// @return the source
    static ClassSource of(ClassLoader loader) {
        return type -> {
            try (InputStream in = loader.getResourceAsStream(resource(type))) {
                return in == null ? Optional.empty() : Optional.of(in.readAllBytes());
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        };
    }

    /// Reads `internal/Name.class` files under a root, e.g. a class output
    /// directory or the root of a jar opened with `FileSystems.newFileSystem(jar)`.
    ///
    /// @param root the root directory
    /// @return the source
    static ClassSource of(Path root) {
        return type -> read(root.resolve(resource(type)));
    }

    /// Reads the running JDK's classes from the `jrt:/` file system.
    ///
    /// @return the source
    static ClassSource jrt() {
        FileSystem jrt = FileSystems.getFileSystem(URI.create("jrt:/"));
        return type -> {
            Path packageDir = jrt.getPath("/packages", type.packageName());
            if (type.packageName().isEmpty() || !Files.isDirectory(packageDir)) {
                return Optional.empty();
            }
            try (Stream<Path> modules = Files.list(packageDir)) {
                Iterator<Path> it = modules.iterator();
                while (it.hasNext()) {
                    Optional<byte[]> bytes =
                            read(jrt.getPath("/modules", it.next().getFileName().toString(), resource(type)));
                    if (bytes.isPresent()) {
                        return bytes;
                    }
                }
                return Optional.empty();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        };
    }

    private static Optional<byte[]> read(Path file) {
        if (!Files.isRegularFile(file)) {
            return Optional.empty();
        }
        try {
            return Optional.of(Files.readAllBytes(file));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static String resource(ClassDesc type) {
        String descriptor = type.descriptorString();
        return descriptor.substring(1, descriptor.length() - 1) + ".class";
    }
}
