package main.glfw_

import glm_.i
import glm_.vec2.Vec2
import glm_.vec2.Vec2d
import glm_.vec2.Vec2i
import glm_.vec4.Vec4i
import kool.stak
import libGlfw.glfw3.*
import libGlfw.glfw3_h.*
import main.*
import org.lwjgl.glfw.GLFW.GLFW_HOVERED
import org.lwjgl.system.MemoryStack
import java.foreign.memory.Pointer

/**
 * GLFW 3.2
 */


/*  TODO
    icon
    glfwGetJoystickHats, GLFW_JOYSTICK_HAT_BUTTONS
    glfwSetJoystickUserPointer
    glfwSetMonitorUserPointer
    glfwSetWindowMaximizeCallback
    glfwGetKeyScancode
    glfwGetWindowContentScale, glfwGetMonitorContentScale and glfwSetWindowContentScaleCallback
    glfwGetGamepadState function, GLFW_GAMEPAD_* and GLFWgamepadstate
    glfwGetJoystickGUID
    glfwGetGamepadName
    glfwJoystickIsGamepad
    glfwUpdateGamepadMappings
 */
open class GlfwWindow(var ptr: Pointer<GLFWwindow>) {

    constructor(
        width: Int, height: Int,
        title: String, monitor: GlfwMonitor? = null,
        share: GlfwWindow? = null
    ) : this(scope(title) {
        glfwCreateWindow(width, height, it, monitor?.ptr ?: Pointer.ofNull(), share?.ptr ?: Pointer.ofNull())
    })

//    @Throws(RuntimeException::class)
//    constructor(
//        windowSize: Vec2i,
//        title: String,
//        monitor: GlfwMonitor = NULL,
//        position: Vec2i = Vec2i(Int.MIN_VALUE),
//        installCallbacks: Boolean = true
//    ) : this(windowSize.x, windowSize.y, title, monitor, position, installCallbacks)
//
//    @Throws(RuntimeException::class)
//    constructor(
//        x: Int,
//        title: String,
//        monitor: GlfwMonitor = NULL,
//        position: Vec2i = Vec2i(Int.MIN_VALUE),
//        installCallbacks: Boolean = true
//    ) : this(x, x, title, monitor, position, installCallbacks)
//
//    @Throws(RuntimeException::class)
//    constructor(
//        width: Int, height: Int,
//        title: String,
//        monitor: GlfwMonitor = NULL,
//        position: Vec2i = Vec2i(Int.MIN_VALUE),
//        installCallbacks: Boolean = true
//    ) :
//            this(glfwCreateWindow(width, height, title, monitor, NULL)) {
//
//        this.title = title
//
//        if (position != Vec2i(Int.MIN_VALUE))
//            glfwSetWindowPos(ptr.L, position.x, position.y)
//
//        if (installCallbacks) {
//            installNativeCallbacks() // TODO default too?
//        }
//    }
//
//    init {
//        if (ptr.L == NULL) {
//            glfw.terminate()
//            throw RuntimeException("Failed to create the GLFW window")
//        }
//    }
//
//    fun installNativeCallbacks() {
//        glfwSetCharCallback(ptr.L, nCharCallback)
//        glfwSetCursorPosCallback(ptr.L, nCursorPosCallback)
//        glfwSetCursorEnterCallback(ptr.L, nCursorEnterCallback)
//        glfwSetFramebufferSizeCallback(ptr.L, nFramebufferSizeCallback)
//        glfwSetKeyCallback(ptr.L, nKeyCallback)
//        glfwSetMouseButtonCallback(ptr.L, nMouseButtonCallback)
//        glfwSetScrollCallback(ptr.L, nScrollCallback)
//        glfwSetWindowCloseCallback(ptr.L, nWindowCloseCallback)
//        glfwSetWindowContentScaleCallback(ptr.L, nWindowContentScaleCallback)
//    }
//
//    fun installDefaultCallbacks() {
//        cursorPosCallback = defaultCursorPosCallback
//        cursorEnterCallback = defaultCursorEnterCallback
//        framebufferSizeCallback = defaultFramebufferSizeCallback
//        keyCallback = defaultKeyCallback
//        mouseButtonCallback = defaultMouseButtonCallback
//        scrollCallback = defaultScrollCallback
//        windowCloseCallback = defaultWindowCloseCallback
//        windowContentScaleCallback = defaultWindowContentScaleCallback
//    }


    val isOpen: Boolean
        get() = !shouldClose

    var shouldClose: Boolean
        get() = glfwWindowShouldClose(ptr) == GLFW_TRUE
        set(value) = glfwSetWindowShouldClose(ptr, if (value) GLFW_TRUE else GLFW_FALSE)


    var title: String = ""
        set(value) = scope(value) {
            glfwSetWindowTitle(ptr, it)
            field = value
        }


    fun setIcon(count: Int, images: Pointer<GLFWimage>) = glfwSetWindowIcon(ptr, count, images)


    var pos: Vec2i
        get() = scope {
            val x = allocInt()
            val y = allocInt()
            glfwGetWindowPos(ptr, x, y)
            Vec2i(x(), y())
        }
        set(value) = glfwSetWindowPos(ptr, value.x, value.y)


    var size: Vec2i
        get() = scope {
            val x = allocInt()
            val y = allocInt()
            glfwGetWindowSize(ptr, x, y)
            Vec2i(x(), y())
        }
        set(value) = glfwSetWindowSize(ptr, value.x, value.y)


    fun setSizeLimit(width: IntRange, height: IntRange) =
        glfwSetWindowSizeLimits(ptr, width.start, height.start, width.endInclusive, height.endInclusive)

    fun setSizeLimit(minWidth: Int, minHeight: Int, maxWidth: Int, maxHeight: Int) =
        glfwSetWindowSizeLimits(ptr, minWidth, minHeight, maxWidth, maxHeight)


    var aspect: Float
        get() = size.aspect
        set(value) = glfwSetWindowAspectRatio(ptr, (value * 100).i, 100)

    var aspectRatio: Vec2i
        get() = Vec2i(size.x, size.y)
        set(value) = glfwSetWindowAspectRatio(ptr, value.x, value.y)


    val framebufferSize: Vec2i
        get() = scope {
            val x = allocInt()
            val y = allocInt()
            glfwGetFramebufferSize(ptr, x, y)
            Vec2i(x(), y())
        }


    val frameSize: Vec4i
        get() = scope {
            val x = allocInt()
            val y = allocInt()
            val z = allocInt()
            val w = allocInt()
            glfwGetWindowFrameSize(ptr, x, y, z, w)
            Vec4i(x(), y(), z(), w())
        }


    val contentScale: Vec2
        get() = scope {
            val x = allocFloat()
            val y = allocFloat()
            glfwGetWindowContentScale(ptr, x, y)
            Vec2(x(), y())
        }


    var opacity: Float
        get() = glfwGetWindowOpacity(ptr)
        set(value) = glfwSetWindowOpacity(ptr, value)


    fun iconify() = glfwIconifyWindow(ptr)
    fun restore() = glfwRestoreWindow(ptr)
    fun maximize() = glfwMaximizeWindow(ptr)
    fun show(show: Boolean = true) = if (show) glfwShowWindow(ptr) else glfwHideWindow(ptr)
    fun hide() = glfwHideWindow(ptr)
    fun focus() = glfwFocusWindow(ptr)
    fun requestAttention() = glfwRequestWindowAttention(ptr)


    val monitor: GlfwMonitor
        get() = GlfwMonitor(glfwGetWindowMonitor(ptr))

    fun setMonitor(monitor: GlfwMonitor, pos: Vec2i, size: Vec2i, refreshRate: Int = GLFW_DONT_CARE) =
        setMonitor(monitor, pos.x, pos.y, size.x, size.y, refreshRate)

    fun setMonitor(
        monitor: GlfwMonitor,
        posX: Int, posY: Int,
        sizeX: Int, sizeY: Int,
        refreshRate: Int = GLFW_DONT_CARE
    ) =
        glfwSetWindowMonitor(ptr, monitor.ptr, pos.x, pos.y, size.x, size.y, refreshRate)


    // Window related attributes

    val isFocused: Boolean
        get() = glfwGetWindowAttrib(ptr, GLFW_FOCUSED) == GLFW_TRUE
    val isIconified: Boolean
        get() = glfwGetWindowAttrib(ptr, GLFW_ICONIFIED) == GLFW_TRUE
    val isMaximized: Boolean
        get() = glfwGetWindowAttrib(ptr, GLFW_MAXIMIZED) == GLFW_TRUE
    val isVisible: Boolean
        get() = glfwGetWindowAttrib(ptr, GLFW_VISIBLE) == GLFW_TRUE
    val isHovered: Boolean
        get() = glfwGetWindowAttrib(ptr, GLFW_HOVERED) == GLFW_TRUE
    val isResizable: Boolean
        get() = glfwGetWindowAttrib(ptr, GLFW_RESIZABLE) == GLFW_TRUE
    val isDecorated: Boolean
        get() = glfwGetWindowAttrib(ptr, GLFW_DECORATED) == GLFW_TRUE
    val isFloating: Boolean
        get() = glfwGetWindowAttrib(ptr, GLFW_FLOATING) == GLFW_TRUE

    // Context related attributes

    val clientApi: ClientApi
        get() = ClientApi(glfwGetWindowAttrib(ptr, GLFW_CLIENT_API))

    val contextCreationApi: ContextCreationApi
        get() = ContextCreationApi(glfwGetWindowAttrib(ptr, GLFW_CONTEXT_CREATION_API))

    val contextVersionMajor: Int
        get() = glfwGetWindowAttrib(ptr, GLFW_CONTEXT_VERSION_MAJOR)

    val contextVersionMinor: Int
        get() = glfwGetWindowAttrib(ptr, GLFW_CONTEXT_VERSION_MINOR)

    // JVM custom
    val contextVersion: Int
        get() = contextVersionMajor * 10 + contextVersionMinor

    val openglForwardCompat: Boolean
        get() = glfwGetWindowAttrib(ptr, GLFW_OPENGL_FORWARD_COMPAT) == GLFW_TRUE

    val openglDebugContext: Boolean
        get() = glfwGetWindowAttrib(ptr, GLFW_OPENGL_DEBUG_CONTEXT) == GLFW_TRUE

    val openGlProfile: OpenGlProfile
        get() = OpenGlProfile(glfwGetWindowAttrib(ptr, GLFW_OPENGL_PROFILE))

    val contextRobustness: ContextRobustness
        get() = ContextRobustness(glfwGetWindowAttrib(ptr, GLFW_CONTEXT_ROBUSTNESS))


    var userPointer: Pointer<Void>
        get() = glfwGetWindowUserPointer(ptr)
        set(value) = glfwSetWindowUserPointer(ptr, value)


    fun setPosCallback(cb: GlfwWindowPosFun): GlfwWindowPosFun? = glfwSetWindowPosCallback(ptr, cb)

    fun setSizeCallback(cb: GlfwWindowSizeFun): GlfwWindowSizeFun? = glfwSetWindowSizeCallback(ptr, cb)

    fun setCloseCallback(cb: GlfwWindowCloseFun): GlfwWindowCloseFun? = glfwSetWindowCloseCallback(ptr, cb)

    fun setRefreshCallback(cb: GlfwWindowRefreshFun): GlfwWindowRefreshFun? = glfwSetWindowRefreshCallback(ptr, cb)

    fun setFocusCallback(cb: GlfwWindowFocusFun): GlfwWindowFocusFun? = glfwSetWindowFocusCallback(ptr, cb)

    fun setIconifyCallback(cb: GlfwWindowIconifyFun): GlfwWindowIconifyFun? = glfwSetWindowIconifyCallback(ptr, cb)

    fun setMaximizeCallback(cb: GlfwWindowMaximizeFun): GlfwWindowMaximizeFun? = glfwSetWindowMaximizeCallback(ptr, cb)

    fun setFramebufferSizeCallback(cb: GlfwFramebufferSizeFun): GlfwFramebufferSizeFun? =
        glfwSetFramebufferSizeCallback(ptr, cb)

    fun setContentScaleCallback(cb: GlfwWindowContentScaleFun): GlfwWindowContentScaleFun? =
        glfwSetWindowContentScaleCallback(ptr, cb)


    var cursorStatus: CursorStatus
        get() = CursorStatus(glfwGetInputMode(ptr, GLFW_CURSOR))
        set(value) = glfwSetInputMode(ptr, GLFW_CURSOR, value.i)

    var stickyKeys: Boolean
        get() = glfwGetInputMode(ptr, GLFW_STICKY_KEYS) == GLFW_TRUE
        set(value) = glfwSetInputMode(ptr, GLFW_STICKY_KEYS, if (value) GLFW_TRUE else GLFW_FALSE)

    var stickyMouseButtons: Boolean
        get() = glfwGetInputMode(ptr, GLFW_STICKY_MOUSE_BUTTONS) == GLFW_TRUE
        set(value) = glfwSetInputMode(ptr, GLFW_STICKY_MOUSE_BUTTONS, if (value) GLFW_TRUE else GLFW_FALSE)

    var lockKeyMods: Boolean
        get() = glfwGetInputMode(ptr, GLFW_LOCK_KEY_MODS) == GLFW_TRUE
        set(value) = glfwSetInputMode(ptr, GLFW_LOCK_KEY_MODS, if (value) GLFW_TRUE else GLFW_FALSE)


    infix fun get(key: Key) = glfwGetKey(ptr, key.i)
    infix fun isPressed(key: Key) = get(key) == GLFW_PRESS
    infix fun isReleased(key: Key) = get(key) == GLFW_RELEASE

    infix fun get(button: MouseButton) = glfwGetMouseButton(ptr, button.i)
    infix fun isPressed(button: MouseButton) = get(button) == GLFW_PRESS
    infix fun isRelease(button: MouseButton) = get(button) == GLFW_RELEASE


    var cursorPos: Vec2d
        get() = scope {
            val x = allocDouble()
            val y = allocDouble()
            glfwGetCursorPos(ptr, x, y)
            Vec2d(x(), y())
        }
        set(value) = glfwSetCursorPos(ptr, value.x, value.y)


    fun setCursor(cursor: Pointer<GLFWcursor>) = glfwSetCursor(ptr, cursor)


    fun setKeyCallback(cb: GlfwKeyFun) = glfwSetKeyCallback(ptr, cb)
    fun setCharCallback(cb: GlfwCharFun) = glfwSetCharCallback(ptr, cb)
    fun setCharModsCallback(cb: GlfwCharModsFun) = glfwSetCharModsCallback(ptr, cb)
    fun setMouseButtonCallback(cb: GlfwMouseButtonFun) = glfwSetMouseButtonCallback(ptr, cb)
    fun setCursorPosCallback(cb: GlfwCursorPosFun) = glfwSetCursorPosCallback(ptr, cb)
    fun setCursorEnterCallback(cb: GlfwCursorEnterFun) = glfwSetCursorEnterCallback(ptr, cb)
    fun setScrollCallback(cb: GlfwScrollFun) = glfwSetScrollCallback(ptr, cb)
    fun setDropCallback(cb: GlfwDropFun) = glfwSetDropCallback(ptr, cb)

    var clipboardString: String
        get() = glfwGetClipboardString(ptr).asString
        set(value) = scope(value) { glfwSetClipboardString(ptr, it) }


    fun makeContextCurrent() = glfwMakeContextCurrent(ptr)

    fun unmakeContextCurrent() = glfwMakeContextCurrent(Pointer.ofNull())


    fun swapBuffers() = glfwSwapBuffers(ptr)
    fun present() = swapBuffers()

//
//    var debugProc: Callback? = null
//
//    fun init(show: Boolean = true) {
//        makeContextCurrent()
//        /*  This line is critical for LWJGL's interoperation with GLFW's OpenGL context,
//            or any context that is managed externally.
//            LWJGL detects the context that is current in the current thread, creates the GLCapabilities instance and
//            makes the OpenGL bindings available for use. */
//        GL.createCapabilities()
//        show(show)
//        if (windowHint.debug) {
//            debugProc = GLUtil.setupDebugMessageCallback()
//            // turn off notifications only
//            GL43C.nglDebugMessageControl(
//                GlDebugSource.DONT_CARE.i,
//                GlDebugType.DONT_CARE.i,
//                GlDebugSeverity.NOTIFICATION.i,
//                0,
//                NULL,
//                false
//            )
//        }
//    }


    fun defaultHints() = glfwDefaultWindowHints()


    /** Free the window callbacks and destroy the window and reset its handle back to NULL */
    fun destroy() {
//        Callbacks.glfwFreeCallbacks(ptr.L)
//        debugProc?.free()
        glfwDestroyWindow(ptr)
//        ptr = GlfwWindowHandle(NULL)
    }


    // ------------------- Callbacks -------------------

//    val defaultKey = "0 - default"
//
//    var charCallback: CharCallbackT?
//        get() = charCallbacks.getOrfirst(defaultKey)
//        set(value) {
//            if (value != null)
//                charCallbacks[defaultKey] = value
//            else
//                charCallbacks -= defaultKey
//        }
//
//    val charCallbacks = sortedMapOf<String, CharCallbackT>()
//    val nCharCallback = GLFWCharCallbackI { _, codePoint -> charCallbacks.values.forEach { it(codePoint) } }
//
//
//    var cursorPosCallback: CursorPosCallbackT?
//        get() = cursorPosCallbacks.getOrfirst(defaultKey)
//        set(value) {
//            if (value != null)
//                cursorPosCallbacks[defaultKey] = value
//            else
//                cursorPosCallbacks -= defaultKey
//        }
//
//    val cursorPosCallbacks = sortedMapOf<String, CursorPosCallbackT>()
//    val nCursorPosCallback =
//        GLFWCursorPosCallbackI { _, xPos, yPos -> cursorPosCallbacks.values.forEach { it(Vec2(xPos, yPos)) } }
//
//
//    var cursorEnterCallback: CursorEnterCallbackT?
//        get() = cursorEnterCallbacks.getOrfirst(defaultKey)
//        set(value) {
//            if (value != null)
//                cursorEnterCallbacks[defaultKey] = value
//            else
//                cursorEnterCallbacks -= defaultKey
//        }
//
//    val cursorEnterCallbacks = sortedMapOf<String, CursorEnterCallbackT>()
//    val nCursorEnterCallback =
//        GLFWCursorEnterCallbackI { _, entered -> cursorEnterCallbacks.values.forEach { it(entered) } }
//
//
//    var framebufferSizeCallback: FramebufferSizeCallbackT?
//        get() = framebufferSizeCallbacks.getOrfirst(defaultKey)
//        set(value) {
//            if (value != null)
//                framebufferSizeCallbacks[defaultKey] = value
//            else
//                framebufferSizeCallbacks -= defaultKey
//        }
//    val framebufferSizeCallbacks = sortedMapOf<String, FramebufferSizeCallbackT>()
//    val nFramebufferSizeCallback = GLFWFramebufferSizeCallbackI { _, width, height ->
//        framebufferSizeCallbacks.values.forEach {
//            it(
//                Vec2i(
//                    width,
//                    height
//                )
//            )
//        }
//    }
//
//
//    var keyCallback: KeyCallbackT?
//        get() = keyCallbacks.getOrfirst(defaultKey)
//        set(value) {
//            if (value != null)
//                keyCallbacks[defaultKey] = value
//            else
//                keyCallbacks -= defaultKey
//        }
//    val keyCallbacks = sortedMapOf<String, KeyCallbackT>()
//    val nKeyCallback = GLFWKeyCallbackI { _, key, scanCode, action, mods ->
//        keyCallbacks.values.forEach {
//            it(
//                key,
//                scanCode,
//                action,
//                mods
//            )
//        }
//    }
//
//
//    var mouseButtonCallback: MouseButtonCallbackT?
//        get() = mouseButtonCallbacks.getOrfirst(defaultKey)
//        set(value) {
//            if (value != null)
//                mouseButtonCallbacks[defaultKey] = value
//            else
//                mouseButtonCallbacks -= defaultKey
//        }
//    val mouseButtonCallbacks = sortedMapOf<String, MouseButtonCallbackT>()
//    val nMouseButtonCallback = GLFWMouseButtonCallbackI { _, button, action, mods ->
//        mouseButtonCallbacks.values.forEach {
//            it(
//                button,
//                action,
//                mods
//            )
//        }
//    }
//
//
//    var scrollCallback: ScrollCallbackT?
//        get() = scrollCallbacks.getOrfirst(defaultKey)
//        set(value) {
//            if (value != null)
//                scrollCallbacks[defaultKey] = value
//            else
//                scrollCallbacks -= defaultKey
//        }
//    val scrollCallbacks = sortedMapOf<String, ScrollCallbackT>()
//    val nScrollCallback =
//        GLFWScrollCallbackI { _, xOffset, yOffset -> scrollCallbacks.values.forEach { it(Vec2d(xOffset, yOffset)) } }
//
//
//    var windowCloseCallback: WindowCloseCallbackT?
//        get() = windowCloseCallbacks.getOrfirst(defaultKey)
//        set(value) {
//            if (value != null)
//                windowCloseCallbacks[defaultKey] = value
//            else
//                windowCloseCallbacks -= defaultKey
//        }
//    val windowCloseCallbacks = sortedMapOf<String, WindowCloseCallbackT>()
//    val nWindowCloseCallback = GLFWWindowCloseCallbackI { windowCloseCallbacks.values.forEach { it() } }
//
//
//    var windowContentScaleCallback: WindowContentScaleCallbackT?
//        get() = windowContentScaleCallbacks.getOrfirst(defaultKey)
//        set(value) {
//            if (value != null)
//                windowContentScaleCallbacks[defaultKey] = value
//            else
//                windowContentScaleCallbacks -= defaultKey
//        }
//    val windowContentScaleCallbacks = sortedMapOf<String, WindowContentScaleCallbackT>()
//    val nWindowContentScaleCallback = GLFWWindowContentScaleCallbackI { _, xScale, yScale ->
//        windowContentScaleCallbacks.values.forEach {
//            it(
//                Vec2(
//                    xScale,
//                    yScale
//                )
//            )
//        }
//    }
//
//
//    val defaultKeyCallback: KeyCallbackT = { key, scanCode, action, mods -> onKey(Key of key, scanCode, action, mods) }
//    val defaultMouseButtonCallback: MouseButtonCallbackT =
//        { button, action, mods -> onMouse(MouseButton of button, action, mods) }
//    val defaultCursorPosCallback: CursorPosCallbackT = { pos -> onMouseMoved(pos) }
//    val defaultCursorEnterCallback: CursorEnterCallbackT =
//        { entered -> if (entered) onMouseEntered() else onMouseExited() }
//    val defaultScrollCallback: ScrollCallbackT = { scroll -> onMouseScrolled(scroll.y.f) }
//    val defaultWindowCloseCallback: WindowCloseCallbackT = ::onWindowClosed
//    val defaultWindowContentScaleCallback: WindowContentScaleCallbackT = { newScale -> onWindowContentScaled(newScale) }
//    val defaultFramebufferSizeCallback: FramebufferSizeCallbackT = { size -> onWindowResized(size) }

    //
    // Event handlers are called by the GLFW callback mechanism and should not be called directly
    //

    open fun onWindowResized(newSize: Vec2i) {}
    open fun onWindowClosed() {}

    // Keyboard handling
    open fun onKey(key: Key, scanCode: Int, action: Int, mods: Int) {
        when (action) {
            GLFW_PRESS -> onKeyPressed(key, mods)
            GLFW_RELEASE -> onKeyReleased(key, mods)
        }
    }

    open fun onKeyPressed(key: Key, mods: Int) {}
    open fun onKeyReleased(key: Key, mods: Int) {}

    // Mouse handling
    open fun onMouse(button: MouseButton, action: Int, mods: Int) {
        when (action) {
            GLFW_PRESS -> onMousePressed(button, mods)
            GLFW_RELEASE -> onMouseReleased(button, mods)
        }
    }

    open fun onMousePressed(button: MouseButton, mods: Int) {}
    open fun onMouseReleased(button: MouseButton, mods: Int) {}
    open fun onMouseMoved(newPos: Vec2) {}
    open fun onMouseEntered() {}
    open fun onMouseExited() {}
    open fun onMouseScrolled(delta: Float) {}

    open fun onWindowContentScaled(newScale: Vec2) {}


    var autoSwap = true

//    inline fun loop(block: (MemoryStack) -> Unit) = loop({ isOpen }, block)
//
//    /**
//     *  The `stack` passed to `block` will be automatically a stack frame in place
//     *  (i.e. it has been pushed exactly once, without popping).
//     *  So you can do any allocation on that frame without pushing/popping further
//     *  It's the user choice to pass it down the stacktrace to avoid TLS
//     */
//    inline fun loop(condition: () -> Boolean, block: (MemoryStack) -> Unit) {
//        while (condition()) {
//            glfwPollEvents()
//            stak {
//                block(it)
//                if (autoSwap)
//                    swapBuffers()
//            }
//        }
//    }

//    infix fun createSurface(instance: VkInstance) = glfw.createWindowSurface(handle, instance)


//    val hwnd: HWND
//        get() = HWND(GLFWNativeWin32.glfwGetWin32Window(ptr.L))

//    companion object {
//        infix fun fromWin32Window(hwnd: HWND): GlfwWindow = GlfwWindow(glfw.attachWin32Window(hwnd))
//        @JvmStatic
//        fun from(handle: Long) = GlfwWindow(GlfwWindowHandle(handle))
//    }
}