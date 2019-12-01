package pl.karol202.uranium.swing.control.scrollpane

import pl.karol202.uranium.core.common.AutoKey
import pl.karol202.uranium.core.common.UProps
import pl.karol202.uranium.core.component.component
import pl.karol202.uranium.swing.control.scrollbar.ScrollBarAxis.HORIZONTAL
import pl.karol202.uranium.swing.control.scrollbar.ScrollBarAxis.VERTICAL
import pl.karol202.uranium.swing.native.SwingNativeComponent
import pl.karol202.uranium.swing.native.nativeComponent
import pl.karol202.uranium.swing.util.*
import pl.karol202.uranium.swing.util.update
import java.awt.Component
import javax.swing.JScrollPane
import javax.swing.ScrollPaneConstants
import javax.swing.ScrollPaneConstants.*
import javax.swing.border.Border

class SwingScrollPaneBidirectional(private val nativeComponent: JScrollPane,
                                   initialProps: Props) : SwingAbstractComponent<SwingScrollPaneBidirectional.Props>(initialProps)
{
	data class Props(override val key: Any = AutoKey,
	                 override val scrollPaneBaseProps: SwingScrollPaneBase.Props = SwingScrollPaneBase.Props(),
	                 val upperLeadingCorner: Prop<SwingElement<*>?> = Prop.NoValue,
	                 val upperTrailingCorner: Prop<SwingElement<*>?> = Prop.NoValue,
	                 val lowerLeadingCorner: Prop<SwingElement<*>?> = Prop.NoValue,
	                 val lowerTrailingCorner: Prop<SwingElement<*>?> = Prop.NoValue) : UProps,
	                                                                                   SwingNativeComponent.PropsProvider<Props>,
	                                                                                   SwingScrollPaneBase.PropsProvider<Props>,
	                                                                                   PropsProvider<Props>
	{
		override val swingProps = scrollPaneBaseProps.swingProps
		override val scrollPaneBidirectionalProps = this

		override fun withSwingProps(builder: Builder<SwingNativeComponent.Props>) =
				copy(scrollPaneBaseProps = scrollPaneBaseProps.withSwingProps(builder))

		override fun withScrollPaneBaseProps(builder: Builder<SwingScrollPaneBase.Props>) =
				copy(scrollPaneBaseProps = scrollPaneBaseProps.builder())

		override fun withScrollPaneBidirectionalProps(builder: Builder<Props>) = builder()
	}

	interface PropsProvider<S : PropsProvider<S>> : UProps
	{
		val scrollPaneBidirectionalProps: Props

		fun withScrollPaneBidirectionalProps(builder: Builder<Props>): S
	}

	private var upperLeadingCornerRenderer = EmbeddedRenderer()
	private var upperTrailingCornerRenderer = EmbeddedRenderer()
	private var lowerLeadingCornerRenderer = EmbeddedRenderer()
	private var lowerTrailingCornerRenderer = EmbeddedRenderer()

	override fun SwingRenderBuilder.render()
	{
		+ scrollPaneBase(nativeComponent = { nativeComponent }, props = props.scrollPaneBaseProps)
	}

	override fun onUpdate(previousProps: Props?) = nativeComponent.update {
		props.upperLeadingCorner.ifPresent { corner ->
			updateRenderer(upperLeadingCornerRenderer, corner, getCorner(UPPER_LEFT_CORNER)) { setCorner(UPPER_LEFT_CORNER, it) }
		}
		props.upperTrailingCorner.ifPresent { corner ->
			updateRenderer(upperTrailingCornerRenderer, corner, getCorner(UPPER_RIGHT_CORNER)) { setCorner(UPPER_RIGHT_CORNER, it) }
		}
		props.lowerLeadingCorner.ifPresent { corner ->
			updateRenderer(lowerLeadingCornerRenderer, corner, getCorner(LOWER_LEFT_CORNER)) { setCorner(LOWER_LEFT_CORNER, it) }
		}
		props.lowerTrailingCorner.ifPresent { corner ->
			updateRenderer(lowerTrailingCornerRenderer, corner, getCorner(LOWER_RIGHT_CORNER)) { setCorner(LOWER_RIGHT_CORNER, it) }
		}
	}

	private fun updateRenderer(renderer: EmbeddedRenderer, element: SwingElement<*>?,
	                           currentComponent: Component?, setter: (Component?) -> Unit)
	{
		if(element != null)
		{
			renderer.update(element)
			val component = renderer.component
			if(currentComponent != component) setter(component)
		}
		else if(currentComponent != null) setter(null)
	}
}

fun SwingRenderScope.scrollPaneBidirectional(nativeComponent: () -> JScrollPane = ::JScrollPane,
                                             key: Any = AutoKey,
                                             props: SwingScrollPaneBidirectional.Props = SwingScrollPaneBidirectional.Props(key)) =
		component({ SwingScrollPaneBidirectional(nativeComponent(), it) }, props)

private typealias SSPBIProvider<P> = SwingScrollPaneBidirectional.PropsProvider<P>
fun <P : SSPBIProvider<P>> SwingElement<P>.withScrollPaneBidirectionalProps(builder: Builder<SwingScrollPaneBidirectional.Props>) =
		withProps { withScrollPaneBidirectionalProps(builder) }
fun <P : SSPBIProvider<P>> SwingElement<P>.upperLeadingCorner(corner: SwingRenderScope.() -> SwingElement<*>) =
		withScrollPaneBidirectionalProps { copy(upperLeadingCorner = SwingEmptyRenderScope.corner().prop()) }
fun <P : SSPBIProvider<P>> SwingElement<P>.upperTrailingCorner(corner: SwingRenderScope.() -> SwingElement<*>) =
		withScrollPaneBidirectionalProps { copy(upperTrailingCorner = SwingEmptyRenderScope.corner().prop()) }
fun <P : SSPBIProvider<P>> SwingElement<P>.lowerLeadingCorner(corner: SwingRenderScope.() -> SwingElement<*>) =
		withScrollPaneBidirectionalProps { copy(lowerLeadingCorner = SwingEmptyRenderScope.corner().prop()) }
fun <P : SSPBIProvider<P>> SwingElement<P>.lowerTrailingCorner(corner: SwingRenderScope.() -> SwingElement<*>) =
		withScrollPaneBidirectionalProps { copy(lowerTrailingCorner = SwingEmptyRenderScope.corner().prop()) }
