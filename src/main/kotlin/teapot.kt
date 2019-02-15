import opengl.freeglut_std
import opengl.freeglut_std_h.*
import opengl.gl_h.*
import java.foreign.NativeTypes
import java.foreign.Scope
import java.foreign.memory.Pointer

fun main() {
    Scope.newNativeScope().use { sc ->
        val argc = sc.allocate(NativeTypes.INT32)
        argc.set(0)
        glutInit(argc, Pointer.nullPointer())
        glutInitDisplayMode(GLUT_DOUBLE or GLUT_RGBA or GLUT_DEPTH)
        glutInitWindowSize(900, 900)
        glutCreateWindow(sc.allocateCString("Hello Panama!"))
        val teapot = TeapotK(sc)

        glutDisplayFunc(sc.allocateCallback(freeglut_std.FI2 { teapot.display() }))
        glutIdleFunc(sc.allocateCallback(freeglut_std.FI2 { teapot.onIdle() }))
        glutMainLoop()
    }
}

class TeapotK(sc: Scope) {

    internal var rot = 0f

    init {
        // Misc Parameters
        val pos = sc.allocateArray(NativeTypes.FLOAT, floatArrayOf(0.0f, 15.0f, -15.0f, 0f))
        val spec = sc.allocateArray(NativeTypes.FLOAT, floatArrayOf(1f, 1f, 1f, 0f))
        val shini = sc.allocateArray(NativeTypes.FLOAT, floatArrayOf(113f))

        // Reset Background
        glClearColor(0f, 0f, 0f, 0f)

        // Setup Lighting
        glShadeModel(GL_SMOOTH)
        glLightfv(GL_LIGHT0, GL_POSITION, pos.elementPointer())
        glLightfv(GL_LIGHT0, GL_AMBIENT, spec.elementPointer())
        glLightfv(GL_LIGHT0, GL_DIFFUSE, spec.elementPointer())
        glLightfv(GL_LIGHT0, GL_SPECULAR, spec.elementPointer())
        glMaterialfv(GL_FRONT, GL_SHININESS, shini.elementPointer())
        glEnable(GL_LIGHTING)
        glEnable(GL_LIGHT0)
        glEnable(GL_DEPTH_TEST)
    }

    internal fun display() {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        glPushMatrix()
        glRotatef(-20f, 1f, 1f, 0f)
        glRotatef(rot, 0f, 1f, 0f)
        glutSolidTeapot(0.5)
        glPopMatrix()
        glutSwapBuffers()
    }

    internal fun onIdle() {
        rot += 0.1f
        glutPostRedisplay()
    }
}

inline fun Scope.use(block: (Scope) -> Unit) {
    block(this)
    close()
}