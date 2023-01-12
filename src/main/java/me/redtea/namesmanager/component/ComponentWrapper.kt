package me.redtea.namesmanager.component

import com.cryptomorin.xseries.XMaterial
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.Material
import org.bukkit.command.CommandSender

class ComponentWrapper(var value: MutableList<String>, val container: Container?) : TextComponent {
    constructor(string: String, container: Container) : this(mutableListOf(string), container)
    constructor(container: Container) : this(mutableListOf<String>(), container)
    constructor() : this(mutableListOf<String>(), null)

    var prefix = if (container != null && container.hasPrefix()) container.prefix else ""

    private val mm: MiniMessage = MiniMessage.miniMessage()

    fun toList(): List<String> = value

    override fun toString(): String = value.joinToString("\n")

    fun toParsedString(): String = serialize(toComponent())

    override fun toBuilder(): TextComponent.Builder = (toComponent() as TextComponent).toBuilder()

    override fun children(): MutableList<Component> = toComponent().children()

    override fun children(children: MutableList<out ComponentLike>): TextComponent =
        toComponent().children(children).also {
            value = serialize(it).split(serialize(Component.newline())).toMutableList()
        } as TextComponent

    override fun style(): Style = toComponent().style()

    override fun style(style: Style): TextComponent =
        toComponent().style(style).also {
            value = serialize(it).split(serialize(Component.newline())).toMutableList()
        } as TextComponent

    override fun content(): String = (toComponent() as TextComponent).content()

    override fun content(content: String): TextComponent = (toComponent() as TextComponent).content(content)


    fun toComponentList(withPrefix: Boolean, vararg resolvers: TagResolver): List<Component> = value.map {
        (if (withPrefix) prefix else "" + it)?.let { it1 ->
            mm.deserialize(
                it1,
                TagResolver.resolver(resolvers.asIterable())
            )
        } as TextComponent
    }

    fun toComponentList(): List<Component> = toComponentList(false)

    fun toComponent(withPrefix: Boolean = true, vararg resolvers: TagResolver): Component =
        ((if (withPrefix) prefix else "")?.let {
            mm.deserialize(
                it
            )
        } as TextComponent).toBuilder()
            .also { component ->
                value.map {
                    mm.deserialize(it, TagResolver.resolver(resolvers.asIterable()))
                }.mapIndexed { index, it ->
                    component.append(it)
                    if (value.size != 1 && value.lastIndex != index)
                        component.append(Component.newline())
                }
            }.build()

    fun toComponent(): Component = (prefix?.let { mm.deserialize(it) } as TextComponent).toBuilder()
        .also { component ->
            value.map {
                mm.deserialize(it)
            }.mapIndexed { index, it ->
                component.append(it)
                if (value.size != 1 && value.lastIndex != index)
                    component.append(Component.newline())
            }
        }.build()

    operator fun plusAssign(string: String) {
        value += string
    }

    operator fun plusAssign(component: Component) {
        value += serialize(component)
    }

    operator fun minusAssign(string: String) {
        value -= string
    }

    operator fun minusAssign(component: Component) {
        value -= serialize(component)
    }

    fun send(sender: CommandSender) {
        sender.sendMessage(toComponent())
    }

    fun send(sender: CommandSender, withPrefix: Boolean) {
        sender.sendMessage(toComponent(withPrefix = withPrefix))
    }

    fun replace(from: String, to: String): ComponentWrapper =
        ComponentWrapper(this.value.map { it.replace(from, to) }.toMutableList(), container)

    fun replace(from: String, to: Any): ComponentWrapper = replace(from, to.toString())

    fun toMaterial(): Material = XMaterial.valueOf(toString().uppercase()).parseMaterial()!!

    private fun serialize(component: Component): String = PlainTextComponentSerializer.plainText().serialize(component);
}