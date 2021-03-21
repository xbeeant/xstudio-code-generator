import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import io.github.xbeeant.javamerge.merger.JavaMerger;
import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author LIQIU
 */
public class MergeableShellCallback extends DefaultShellCallback {
    private static final Logger logger = LoggerFactory.getLogger(MergeableShellCallback.class);

    public MergeableShellCallback(boolean overwrite) {
        super(overwrite);
    }

    JavaParser javaParser = new JavaParser();

    @Override
    public boolean isMergeSupported() {
        return true;
    }

    @Override
    public String mergeJavaFile(String newFileSource, File existingFile, String[] javadocTags, String fileEncoding) throws ShellException {

        try {
            CompilationUnit newCu = javaParser.parse(newFileSource).getResult().get();
            logger.debug("new {}" , newCu.toString());
            CompilationUnit oldCu = javaParser.parse(existingFile).getResult().get();
            logger.debug("old {}", oldCu.toString());
            return JavaMerger.merge(oldCu, newCu, true).toString();
        } catch (Exception e) {
            throw new ShellException(e);
        }
    }
}
