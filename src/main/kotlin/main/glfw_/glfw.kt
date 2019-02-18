package main.glfw_

import glm_.BYTES
import glm_.L
import glm_.bool
import glm_.i
import glm_.vec2.Vec2i
import kool.stak
import libGlfw.glfw3
import libGlfw.glfw3.*
import libGlfw.glfw3_h.*
import main.*
import org.lwjgl.system.MemoryUtil
import org.lwjgl.system.MemoryUtil.*
import java.foreign.memory.Pointer

//import uno.kotlin.parseInt
//import vkk.VK_CHECK_RESULT
//import vkk.entities.VkSurface
//import vkk.VK_CHECK_RESULT
//import vkk.VkSurfaceKHR
//import vkk.adr

object glfw {

    /** Short version of:
     *  glfw.init()
     *  glfw.windowHint {
     *      context.version = "3.2"
     *      windowHint.profile = "core"
     *  }
     *  + default error callback
     */
//    @Throws(RuntimeException::class)
//    fun init(version: String, profile: Profile = Profile.core, installDefaultErrorCallback: Boolean = true) {
//        init(installDefaultErrorCallback)
//        windowHint {
//            windowHint.Context.version = version
//            val v = version[0].parseInt() * 10 + version[1].parseInt()
//            if (v >= 32) // The concept of a core profile does not exist prior to OpenGL 3.2
//                windowHint.profile = profile
//        }
//    }
//
//    @Throws(RuntimeException::class)
//    fun init(errorCallback: GLFWErrorCallbackT) {
//        glfw.errorCallback = errorCallback
//        init()
//    }
//
//    @Throws(RuntimeException::class)
//    fun init(installDefaultErrorCallback: Boolean) {
//        if (installDefaultErrorCallback)
//            errorCallback = defaultErrorCallback
//        init()
//    }
//
//    @Throws(RuntimeException::class)
//    fun init() {
//
//        if (!glfwInit())
//            throw RuntimeException("Unable to initialize GLFW")
//
//        // This window hint is required to use OpenGL 3.1+ on macOS
//        if (Platform.get() == Platform.MACOSX)
//            windowHint.forwardComp = true
//    }

    fun init(): Boolean = glfwInit() == GLFW_TRUE

    fun terminate() {
        glfwTerminate()
//        nErrorCallback.free()
//        glDebugCallback?.free()
    }

    fun <R> initHint(block: initHint.() -> R) = initHint.block()
//    fun <R> windowHint(block: windowHint.() -> R) = windowHint.block()

    fun getVersion(): IntArray = scope {
        val major = allocInt()
        val minor = allocInt()
        val rev = allocInt()
        glfwGetVersion(major, minor, rev)
        intArrayOf(major(), minor(), rev())
    }

    val version: String
        get() = glfwGetVersionString().asString

    val error: GlfwError
        get() = GlfwError.values().first { it.code == glfwGetError(Pointer.nullPointer()) }

    fun setErrorCallback(error: GlfwErrorFun?): GlfwErrorFun? = glfwSetErrorCallback(error)

    val monitors: Array<Pointer<GLFWmonitor>>?
        get() = scopeInt {
            val array = glfwGetMonitors(it).withSize(it().L)
            Array(it()) { array[it] }
        }

    val primaryMonitor: Pointer<GLFWmonitor>
        get() = glfwGetPrimaryMonitor()

    fun pollEvents() = glfwPollEvents()

    fun waitEvents() = glfwWaitEvents()

    fun waitEvents(timeout: Double) = glfwWaitEventsTimeout(timeout)

    fun postEmptyEvent() = glfwPostEmptyEvent()

    fun setMonitorCallback(cbfun: GlfwMonitorFun): GlfwMonitorFun? = glfwSetMonitorCallback(cbfun)

    fun createCursor(image: Pointer<GLFWimage>, hotspotPos: Vec2i) = createCursor(image, hotspotPos.x, hotspotPos.y)
    fun createCursor(image: Pointer<GLFWimage>, hotspotPosX: Int, hotspotPosY: Int) =
        glfwCreateCursor(image, hotspotPosX, hotspotPosY)


    fun createStandardCursor(shape: CursorShape): Pointer<GLFWcursor> = glfwCreateStandardCursor(shape.i)


    fun setJoystickCallback(cb: GlfwJoystickFun): GlfwJoystickFun? = glfwSetJoystickCallback(cb)


    fun updateGamepadMappings(mappings: String) = scope(mappings) { ::glfwUpdateGamepadMappings }


    var time: Double
        get() = glfwGetTime()
        set(value) = glfwSetTime(value)


    val timerValue: Long
        get() = glfwGetTimerValue()

    val timerFrequency: Long
        get() = glfwGetTimerFrequency()


    val currentContext: GlfwWindow
        get() = GlfwWindow(glfwGetCurrentContext())

    var swapInterval: VSync = VSync.ON
        set(value) {
            glfwSwapInterval(value.i)
            field = value
        }

    fun extensionSupported(extension: String) = scope(extension) { ::glfwExtensionSupported }

//    fun getProcAddress(procName: Pointer<Byte>): GLFWglproc = glfwGetProcAddress(procName)


    val vulkanSupported: Boolean
        get() = glfwVulkanSupported() == GLFW_TRUE


    val requiredInstanceExtensions: ArrayList<String>
        get() = scopeInt {
            val ppNames = glfwGetRequiredInstanceExtensions(it)
            if (ppNames.isNull)
                return@scopeInt arrayListOf()
            val res = ArrayList<String>(it())
            for (i in 0 until it())
                res += ppNames.offset(i.L)().asString // TODO check ASCII vs utf8
            return res
        }

//    GLFWAPI GLFWvkproc glfwGetInstanceProcAddress(VkInstance instance, const char* procname);
//    GLFWAPI int glfwGetPhysicalDevicePresentationSupport(VkInstance instance, VkPhysicalDevice device, uint32_t queuefamily);
//    GLFWAPI VkResult glfwCreateWindowSurface(VkInstance instance, GLFWwindow* window, const VkAllocationCallbacks* allocator, VkSurfaceKHR* surface);

//    /** videoMode of primaryMonitor */
//    val videoMode: GLFWvidmode
//        get() = glfwGetVideoMode(primaryMonitor)!!
//
//    fun videoMode(monitor: GlfwMonitor): GLFWVidMode? = glfwGetVideoMode(monitor)
//
//    val resolution: Vec2i
//        get() = Vec2i(videoMode.width, videoMode.height)


//    fun createWindowSurface(windowHandle: GlfwWindowHandle, instance: VkInstance): VkSurface =
//        VkSurface(stak.longAddress { surface ->
//            VK_CHECK_RESULT(GLFWVulkan.nglfwCreateWindowSurface(instance.adr, windowHandle.L, NULL, surface))
//        })

    // TODO glfwGetPhysicalDevicePresentationSupport
//    fun attachWin32Window(handle: HWND): GlfwWindowHandle =
//        GlfwWindowHandle(GLFWNativeWin32.glfwAttachWin32Window(handle.L, NULL))

    inline operator fun <R> invoke(block: glfw.() -> R): R = block()
}