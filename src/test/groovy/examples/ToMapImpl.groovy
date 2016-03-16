package examples

import asteroid.A
import asteroid.LocalTransformation
import asteroid.LocalTransformationImpl

import groovy.transform.CompileStatic

import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.FieldNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.expr.MapExpression
import org.codehaus.groovy.ast.expr.MapEntryExpression
import org.codehaus.groovy.control.SourceUnit

@CompileStatic
@LocalTransformation(A.PHASE_LOCAL.INSTRUCTION_SELECTION)
class ToMapImpl extends LocalTransformationImpl<ToMap, ClassNode> {

    @Override
    void doVisit(AnnotationNode annotation, ClassNode node, SourceUnit source) {
        List<FieldNode> instanceFields   = A.UTIL.getInstancePropertyFields(node)
        List<MapEntryExpression> entries = instanceFields.collect(this.&fieldToMapEntry)
        MapExpression mapExpression      = new MapExpression(entries)
        MethodNode methodNode            = getToMapMethod(mapExpression)

        A.UTIL.addMethod(node, methodNode)
    }

    private MapEntryExpression fieldToMapEntry(final FieldNode field) {
        return new MapEntryExpression(A.EXPR.constX(field.name), A.EXPR.varX(field.name))
    }

    private MethodNode getToMapMethod(final MapExpression map) {
        return A.NODES
            .method('toMap')
            .modifiers(A.ACC.ACC_PUBLIC)
            .returnType(Map)
            .code(A.STMT.returnS(map))
            .build()
    }

}
