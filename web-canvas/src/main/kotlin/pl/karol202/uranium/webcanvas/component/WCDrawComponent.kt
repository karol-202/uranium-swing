package pl.karol202.uranium.webcanvas.component

import pl.karol202.uranium.core.common.AutoKey
import pl.karol202.uranium.core.common.UProps
import pl.karol202.uranium.core.element.component
import pl.karol202.uranium.core.render.URenderScope
import pl.karol202.uranium.webcanvas.WC
import pl.karol202.uranium.webcanvas.WCElement
import pl.karol202.uranium.webcanvas.WCRenderScope
import pl.karol202.uranium.webcanvas.native.WCDrawNativeLeaf
import pl.karol202.uranium.webcanvas.draw.DrawOperation

class WCDrawComponent(props: Props) : WCAbstractNativeLeafComponent<WCDrawComponent.Props>(props)
{
	data class Props(override val key: Any,
	                 val drawOperation: DrawOperation) : UProps

	override val native = WCDrawNativeLeaf { props.drawOperation(this) }
}

fun WCRenderScope.drawComponent(key: Any = AutoKey,
                                drawOperation: DrawOperation) =
		component(::WCDrawComponent, WCDrawComponent.Props(key, drawOperation))
