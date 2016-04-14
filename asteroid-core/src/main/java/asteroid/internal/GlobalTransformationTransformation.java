package asteroid.internal;

import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.transform.GroovyASTTransformation;

import java.util.List;
import java.util.ArrayList;

import asteroid.global.GlobalTransformationImpl;
import asteroid.global.Transformer;

/**
 * This transformation makes easier to declare a given global transformation.
 *
 * @since 0.1.2
 */
@GroovyASTTransformation(phase = CompilePhase.CONVERSION)
public class GlobalTransformationTransformation extends GlobalTransformationImpl {

    @Override
    public List<Class<? extends Transformer>> getTransformers() {
        List<Class<? extends Transformer>> list = new ArrayList<>();
        list.add(TranslateToGlobalTransform.class);
        return list;
    }
}
