# ThermalPrinter

Android library for communicating with thermal printers via Bluetooth.
This library is only compatible with [**androidx**](https://developer.android.com/jetpack/androidx/).

## Installation

Add jitpack.io to your root build.gradle:
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Finally add the dependency to your app build.gradle:
```gradle
dependencies {
    implementation "com.github.isradeleon:ThermalPrinter:$lastVersion"
}
```

## ThermalPrinter methods

| Method | Description |
|------------------------------------|--------------------------|
| **print()** | Prints all the written content |
| **write(text: String)** | Writes text to thermal printer |
| **write(text: String, alignment: PrintAlignment)** | Writes text and sets the text alignment |
| **write(text: String, alignment: PrintAlignment, font: PrintFont)** | Writes text, sets text alignment and font size |
| **write(key: String, value: String)** | Writes a line with key value pair |
| **write(key: String, value: String, separator: Char)** | Writes a line with key value pair and fills space with separator |
| **newLine()** | Writes a new line to thermal printer |
| **fillLineWith(char: Char)** | Writes a line filled with the specified char |

## License

This library is licensed under `MIT license`. View [license](LICENSE).