# uranium-swing

![Deployment](
https://github.com/karol-202/uranium-swing/workflows/Deployment/badge.svg
)
[ ![Download](https://api.bintray.com/packages/karol202/uranium/uranium-swing/images/download.svg) ](
https://bintray.com/karol202/uranium/uranium-swing/_latestVersion
)
[ ![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg) ](
https://opensource.org/licenses/MIT
)

uranium-swing is a Kotlin library allowing you to create Swing-based desktop
applications using a concise DSL in React way. For more details how the
uranium library works, visit the [Uranium page](https://github.com/karol-202/uranium).

## Contents
- [How to install?](#how-to-install)
- [Comparision with Swing](#comparision-with-swing)
- [State of the project](#state-of-the-project)
- [Contributing](#contributing)

## How to install?

uranium-swing is located in [jCenter](https://bintray.com/karol202/uranium/uranium-swing),
you can add it as a dependency to your Gradle or Maven project
by including following entry in `dependencies` section.

Gradle (Kotlin DSL):
```kotlin
dependencies {
    implementation("pl.karol202.uranium:uranium-swing:0.2")
}
```

Gradle (Groovy):
```groovy
dependencies {
    implementation "pl.karol202.uranium:uranium-swing:0.2"
}
```

Maven:
```xml
<dependency>
    <groupId>pl.karol202.uranium</groupId>
    <artifactId>uranium-swing</artifactId>
    <version>0.2</version>
</dependency>
```

Make sure that you have jCenter in your repositories `section`:
```groovy
repositories {
    jcenter()
}
```

## Comparision with Swing

Swing like many other UI libraries is based on the imperative paradigm.
That means, that in order to have layout, first you have to create a container,
then create components and then add the components to container.
Thing can get messy, when your layouts are nested. More problems arises,
when it comes to state management.

With Uranium instead of writing such code:
```kotlin
class NamePanel(initialName: String) : JPanel(), ActionListener
{
	private lateinit var textField: JTextField
	private lateinit var label: JLabel
	
	init
	{
		layout = BoxLayout(this, BoxLayout.Y_AXIS)

		val label = JLabel()

		val textField = JTextField(createText(initialName))
		textField.addActionListener(this)

		add(label)
		add(textField)
	}

	override fun actionPerformed(e: ActionEvent?)
	{
		label.text = createText(textField.text)
	}

	private fun createText(text: String) = "Hello, $text"
}
```
you can write:
```kotlin
class NameComponent(props: Props) : SwingAbstractAppComponent<NameComponent.Props>(props),
                                    UStateful<NameComponent.State>
{
    data class Props(override val key: Any,
                     val initialName: String) : UProps

    data class State(val name: String) : UState

    override var state by state(State(props.initialName))

    override fun SwingRenderScope.render() = boxLayout(axis = BoxAxis.Y) {
        + textField()
                .text(state.name)
                .onTextChange(::setName)
        + label()
                .text("Hello, ${state.name}")
    }

    private fun setName(text: String) = setState { copy(name = text) }
}
```

The latter one is much more convenient, scalable and easy to manage
when it comes to state management (thanks to the unidirectional data flow).

## State of the project

During the development of this project a lot of design flaws and inconsistent
behaviours of Swing has been discovered, so I'm not sure whether this library
should be still developed. Probably no further features will have been introduced 
and only little improvements and bug fixes will be done.

## Contributing

Contributions are highly welcome.

If you find a bug or would like have some feature implemented, file an issue.
You can also create a pull request if you have working solution for some issue.
