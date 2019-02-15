import glfw.glfw3
import glfw.glfw3_h.*
import opengl.gl_h.*
import org.lwjgl.system.MemoryUtil.memUTF8
import org.lwjgl.system.MemoryUtil.nmemFree
import java.foreign.Scope
import java.foreign.memory.Pointer

fun main() {

    // Initialize the library
    if (glfwInit() == 0)
        System.exit(-1)

    val sc = Scope.newNativeScope()

    // Create a windowed mode window and its OpenGL context
    val title: Pointer<Byte> = Pointer.fromByteBuffer(memUTF8("Hello World"))
    val window: Pointer<glfw3.GLFWwindow> =
        glfwCreateWindow(640, 480, title, Pointer.nullPointer(), Pointer.nullPointer())
    if (window.isNull) {
        glfwTerminate()
        System.exit(-1)
    }

    nmemFree(title.addr())

    // Make the window's context current
    glfwMakeContextCurrent(window)

    // Loop until the user closes the window
    while (glfwWindowShouldClose(window) == 0)    {

        glClearColor(1f, 0.5f, 0f, 1f)
        // Render here
        glClear(GL_COLOR_BUFFER_BIT)

        /* Swap front and back buffers */
        glfwSwapBuffers(window)

        /* Poll for and process events */
        glfwPollEvents()
    }

    glfwTerminate()
    System.exit(0)
}