import main.scope
import opengl.freeglut_std
import opengl.freeglut_std_h.*
import opengl.gl_h.*
import java.foreign.NativeTypes
import java.foreign.Scope
import java.foreign.memory.Pointer

fun main() {
    scope {
        val argc = allocate(NativeTypes.INT32)
        argc.set(0)
        glutInit(argc, Pointer.ofNull())
        glutInitDisplayMode(GLUT_DOUBLE or GLUT_RGBA or GLUT_DEPTH)
        glutInitWindowSize(900, 900)
        glutCreateWindow(allocateCString("Hello Panama!"))
        val teapot = Teapot(this)

        glutDisplayFunc(allocateCallback(freeglut_std.FI2 { teapot.display() }))
        glutIdleFunc(allocateCallback(freeglut_std.FI2 { teapot.onIdle() }))
        glutMainLoop()
    }
}

class Teapot(sc: Scope) {

    var rot = 0f

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

    fun display() {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        glPushMatrix()
        glRotatef(-20f, 1f, 1f, 0f)
        glRotatef(rot, 0f, 1f, 0f)
        glutSolidTeapot(0.5)
        glPopMatrix()
        glutSwapBuffers()
    }

    fun onIdle() {
        rot += 0.1f
        glutPostRedisplay()
    }
}