import glm_.L
import glm_.b
import glm_.func.rad
import glm_.glm
import glm_.vec3.Vec3
import gln.glf.semantic
import main.*
import main.glfw_.*
import opengl.gl_h.*
import opengl.glext_h.*
import java.foreign.NativeTypes
import java.foreign.Scope
import java.foreign.memory.Array
import java.foreign.memory.Pointer
import kotlin.system.exitProcess

fun main() {

    glfw {

        if (!init()) {
            System.err.println("Failed to initialize GLFW")
            exitProcess(-1)
        }
        windowHint {
            resizable = false
            contextVersion = 33
            openglProfile = OpenGlProfile.CORE
        }
        Triangle(640, 480, "Hello GL")
    }.run()
}

class Triangle(
    width: Int, height: Int,
    title: String, monitor: GlfwMonitor? = null,
    share: GlfwWindow? = null
) : GlfwWindow(width, height, title, monitor, share) {

    val program: Int
    val uniformMVP: Int
    val pMVP: Pointer<Float>
    val vertexArray = scope.allocInt()

    val vertexBufferData: Array<Float> = scope.floatArrayOf(
            -1f, -1f, -1f, 0f, 0f, 1f,
            +1f, -1f, -1f, 0f, 1f, 0f,
            +0f, +1f, -1f, 1f, 0f, 0f)
    val indexBufferData = scope.shortArrayOf(0, 1, 2)

    init {
        makeContextCurrent()

        program = makeProgram()

        // Init MVP matrix
        uniformMVP = glGetUniformLocation(program, scope.alloc("mvp"))

        val proj = glm.perspective(45f.rad, aspect, 0.1f, 100f)
        val view = glm.lookAt(
            Vec3(0f, 0f, 3f),
            Vec3(),
            Vec3(0f, 1f, 0f)
        )
        val mvp = proj * view
        pMVP = scope.alloc(mvp.toFloatArray()).ptr

        // Init VAO
        val vertexBuffer = scope.allocInt()
        val indexBuffer = scope.allocInt()
        glGenVertexArrays(1, vertexArray)
        glGenBuffers(1, vertexBuffer)
        glGenBuffers(1, indexBuffer)

        glBindVertexArray(vertexArray())

        glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer())
        glBufferData(
            GL_ARRAY_BUFFER, vertexBufferData.bytesSize(),
            vertexBufferData.ptr, GL_STATIC_DRAW
        )

        glEnableVertexAttribArray(semantic.attr.POSITION)
        val p0 = scope.allocVoidOf(0)
        glVertexAttribPointer(semantic.attr.POSITION, Vec3.length, GL_FLOAT, GL_FALSE.b, Vec3.size * 2, p0)

        glEnableVertexAttribArray(semantic.attr.COLOR)
        val p1= scope.allocVoidOf(Vec3.size)
        glVertexAttribPointer(semantic.attr.COLOR, Vec3.length, GL_FLOAT, GL_FALSE.b, Vec3.size * 2, p1)

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer())
        glBufferData(
            GL_ELEMENT_ARRAY_BUFFER, indexBufferData.bytesSize(),
            indexBufferData.ptr, GL_STATIC_DRAW
        )

        glBindVertexArray(0)
        glDeleteBuffers(1, vertexBuffer)
        glDeleteBuffers(1, indexBuffer)
    }

    fun run() {

        do {
            glClearColor(1f, 0.5f, 0f, 1f)
            glClear(GL_COLOR_BUFFER_BIT)

            render()

            swapBuffers()
            glfw.pollEvents()
            checkError()

        } while (!isPressed(Key.ESCAPE) && !shouldClose)

        glDeleteVertexArrays(1, vertexArray)
        glDeleteProgram(program)
        glfw.terminate()
        exitProcess(0)
    }

    fun render() {
        glUseProgram(program)
        glUniformMatrix4fv(uniformMVP, 1, GL_FALSE.b, pMVP)
        glBindVertexArray(vertexArray())
        glDrawElementsBaseVertex(GL_TRIANGLES, 3, GL_UNSIGNED_SHORT, Pointer.ofNull<Short>(), 0)
        glBindVertexArray(0)
    }
}

val vertShader = """
        #version 330

        in vec3 vertex;
        in vec3 color;
        uniform mat4 mvp;
        out vec3 c;

        void main() {
            gl_Position = mvp * vec4(vertex, 1.0);
            c = color;
        }"""

val fragShader = """
        #version 330

        in vec3 c;
        out vec4 color;

        void main() {
            color = vec4(c, 1.0);
        }"""

fun makeProgram(): Int {
    val vertexShader = makeShader(GL_VERTEX_SHADER, vertShader)
    val fragmentShader = makeShader(GL_FRAGMENT_SHADER, fragShader)
    if (vertexShader == 0 || fragmentShader == 0)
        exitProcess(-1)
    val program = glCreateProgram()
    glAttachShader(program, vertexShader)
    glAttachShader(program, fragmentShader)

    scope {
        glBindAttribLocation(program, semantic.attr.POSITION, alloc("vertex"))
        glBindAttribLocation(program, semantic.attr.COLOR, alloc("color"))

        glLinkProgram(program)

        val programOk = allocInt()
        glGetProgramiv(program, GL_LINK_STATUS, programOk)
        if (programOk() == GL_FALSE) {
            System.err.println("Failed to link shader program:")
            showInfoLog(program)
            glDeleteShader(vertexShader); glDeleteShader(fragmentShader)
            glDeleteProgram(program)
            exitProcess(-1)
        }
    }
    glDetachShader(program, vertexShader)
    glDetachShader(program, fragmentShader)
    glDeleteShader(vertexShader)
    glDeleteShader(fragmentShader)
    return program
}

fun makeShader(type: Int, source: String): Int = scope {
    val shaderOk = allocInt()
    val shader = glCreateShader(type)
    val pSource = alloc(source)
    val ppSource = allocate(NativeTypes.INT8.pointer())
    ppSource.set(pSource)
    glShaderSource(shader, 1, ppSource, Pointer.ofNull())
    glCompileShader(shader)
    glGetShaderiv(shader, GL_COMPILE_STATUS, shaderOk)
    if (shaderOk.get() == GL_FALSE) {
        System.err.println("Failed to compile $source:")
        showInfoLog(shader)
        glDeleteShader(shader)
        return 0
    }
    shader
}

fun showInfoLog(shader: Int) = scope {
    val logLength = allocInt()
    glGetShaderiv(shader, GL_INFO_LOG_LENGTH, logLength)
    val i = logLength.get()
    val log = allocateArray(NativeTypes.INT8, i.L)
    glGetShaderInfoLog(shader, logLength(), Pointer.ofNull(), log.elementPointer())
    System.err.println(Pointer.toString(log.elementPointer()))
}

fun checkError() = when (glGetError()) {
    GL_NO_ERROR -> Unit
    GL_INVALID_ENUM -> System.err.println("GL error: Invalid enum")
    GL_INVALID_VALUE -> System.err.println("GL error: Invalid value")
    GL_INVALID_OPERATION -> System.err.println("GL error: Invalid operation")
    GL_STACK_OVERFLOW -> System.err.println("GL error: Stack overflow")
    GL_STACK_UNDERFLOW -> System.err.println("GL error: Stack underflow")
    GL_OUT_OF_MEMORY -> System.err.println("GL error: Out of memory")
    GL_TABLE_TOO_LARGE -> System.err.println("GL error: Table too large")
    else -> System.err.println("GL error: Unknown")
}